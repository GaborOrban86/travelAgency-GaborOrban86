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
public class TravellerControllerIT {

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
        restTemplate.postForObject("/api/programs",
                new ProgramCreateCommand("Hide and Seek", "Funny Game", "Peter Program",
                        12000, 1), ProgramCreateCommand.class);
        restTemplate.postForObject("/api/travellers",
                new TravellerCreateCommand("Pityu", "pityu@pityu.hu",
                        LocalDate.of(1986, Month.JUNE, 9), 1), TravellerCreateCommand.class);
    }

    @Test
    void testFindAll_ListSize_Success() {
        ResponseEntity<TravellerInfo[]> response =
                restTemplate.getForEntity("/api/travellers", TravellerInfo[].class);
        List<TravellerInfo> travellers = List.of(Objects.requireNonNull(response.getBody()));
        AssertionsForClassTypes.assertThat(travellers.size()).isEqualTo(1);
    }

    @Test
    void testGetTravellerById_BadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/travellers/10", String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testModifyTraveller_Success() {

        restTemplate.put("/api/travellers/1",
                new TravellerModifyCommand("John Bull", "jhon@jhonny.com"),
                TravellerModifyCommand.class);

        TravellerInfo modified = restTemplate.getForObject("/api/travellers/1", TravellerInfo.class);
        assertThat(modified)
                .extracting(TravellerInfo::getEmail)
                .isEqualTo("jhon@jhonny.com");
    }

    @Test
    void testSaveTravellerWithoutProgram_BadRequest() {

        restTemplate.delete("/api/travellers/1");
        restTemplate.delete("/api/programs/1");

        ResponseEntity<String> response = restTemplate.postForEntity("/api/travellers",
                new TravellerCreateCommand("Pityu", "pityu@pityu.hu",
                        LocalDate.of(1986, Month.JUNE, 9), 1), String.class);
        AssertionsForClassTypes.assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testDeleteTraveller_Success() {

        ResponseEntity<TravellerInfo[]> response =
                restTemplate.getForEntity("/api/travellers", TravellerInfo[].class);
        List<TravellerInfo> travellerInfos = List.of(Objects.requireNonNull(response.getBody()));

        int sizeBeforeDelete = travellerInfos.size();
        assertThat(sizeBeforeDelete).isEqualTo(1);

        restTemplate.delete("/api/travellers/1");

        ResponseEntity<TravellerInfo[]> responseAfterDelete =
                restTemplate.getForEntity("/api/travellers", TravellerInfo[].class);
        List<TravellerInfo> travellersAfterDelete = List.of(Objects.requireNonNull(responseAfterDelete.getBody()));
        int sizeAfterDelete = travellersAfterDelete.size();

        assertThat(sizeAfterDelete).isEqualTo(0);
    }
}
