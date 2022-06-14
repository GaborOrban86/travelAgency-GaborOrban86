package travelagency.controller;

import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import travelagency.dto.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AccommodationControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void putTravelAndAccommodation() {
        restTemplate.postForObject("/api/travels",
                new TravelCreateCommand(2, LocalDate.of(2022, Month.SEPTEMBER, 10),
                        LocalDate.of(2022, Month.SEPTEMBER, 13)), TravelInfo.class);
        restTemplate.postForObject("/api/accommodations",
                new AccommodationCreateCommand("Hilton", "SOLO", "FULL", 1, 10000),
                AccommodationInfo.class);
    }

    @Test
    void testGetAccommodationById_Success() {

        AccommodationInfo accommodationInfo = restTemplate.getForObject("/api/accommodations/1", AccommodationInfo.class);
        assertThat(accommodationInfo)
                .extracting(AccommodationInfo::getPrice)
                .isEqualTo(10000);
    }

    @Test
    void testGetTravelById_BadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/accommodations/10", String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testModifyAccommodation_Success() {

        restTemplate.put("/api/accommodations/1",
                new AccommodationModifyCommand("Bubu Hotel", "FAMILY", "FULL", 5000),
                AccommodationModifyCommand.class);

        AccommodationInfo modified = restTemplate.getForObject("/api/accommodations/1", AccommodationInfo.class);
        assertThat(modified)
                .extracting(AccommodationInfo::getName)
                .isEqualTo("Bubu Hotel");
    }

    @Test
    void testAccommodationSaveForTheSameTravel_BadRequest() {
        ResponseEntity<String> response = restTemplate.postForEntity("/api/accommodations",
                new AccommodationCreateCommand("Hilton", "SOLO", "FULL", 1, 10000),
                String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }


    @Test
    void testDeleteAccommodation_Success() {

        ResponseEntity<AccommodationInfo[]> response =
                restTemplate.getForEntity("/api/accommodations", AccommodationInfo[].class);
        List<AccommodationInfo> accommodationInfos = List.of(Objects.requireNonNull(response.getBody()));

        int sizeBeforeDelete = accommodationInfos.size();
        assertThat(sizeBeforeDelete).isEqualTo(1);

        restTemplate.delete("/api/accommodations/1");

        ResponseEntity<AccommodationInfo[]> responseAfterDelete =
                restTemplate.getForEntity("/api/accommodations", AccommodationInfo[].class);
        List<AccommodationInfo> accommodationInfosAfterDelete = List.of(Objects.requireNonNull(responseAfterDelete.getBody()));
        int sizeAfterDelete = accommodationInfosAfterDelete.size();

        assertThat(sizeAfterDelete).isEqualTo(0);
    }

}
