package travelagency.controller;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import travelagency.dto.TravelCreateCommand;
import travelagency.dto.TravelInfo;
import travelagency.dto.TravelModifyCommand;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TravelControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void putTravel() {
        restTemplate.postForObject("/api/travels",
                new TravelCreateCommand(2, LocalDate.of(2023, Month.SEPTEMBER, 10),
                        LocalDate.of(2023, Month.SEPTEMBER, 12)), TravelInfo.class);
    }

    @Test
    void testFindAll_ListSize_Success() {
        ResponseEntity<TravelInfo[]> response =
                restTemplate.getForEntity("/api/travels", TravelInfo[].class);
        List<TravelInfo> travels = List.of(Objects.requireNonNull(response.getBody()));
        assertThat(travels.size()).isEqualTo(1);
    }

    @Test
    void testGetTravelById_BadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/travels/10", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void TestModifyTravel_Success() {
        int daysBeforeModify = restTemplate.getForObject("/api/travels/1", TravelInfo.class).getDays();
        assertThat(daysBeforeModify).isEqualTo(2);

        restTemplate.put("/api/travels/1", new TravelModifyCommand(
                LocalDate.of(2023, Month.SEPTEMBER, 10),
                LocalDate.of(2023, Month.SEPTEMBER, 11)), TravelModifyCommand.class);

        int daysAfterModify = restTemplate.getForObject("/api/travels/1", TravelInfo.class).getDays();
        assertThat(daysAfterModify).isEqualTo(1);
    }

    @Test
    void TestSaveTravelWithWrongDestination_BadRequest() {
        ResponseEntity<String> response = restTemplate.postForEntity("/api/travels",
                new TravelCreateCommand(33, LocalDate.of(2022, Month.SEPTEMBER, 10),
                        LocalDate.of(2022, Month.SEPTEMBER, 12)), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testDeleteTravel_Success() {
        ResponseEntity<TravelInfo[]> response =
                restTemplate.getForEntity("/api/travels", TravelInfo[].class);
        int sizeBeforeDelete = List.of(Objects.requireNonNull(response.getBody())).size();

        assertThat(sizeBeforeDelete).isEqualTo(1);

        restTemplate.delete("/api/travels/1");

        ResponseEntity<TravelInfo[]> responseAfterDelete =
                restTemplate.getForEntity("/api/travels", TravelInfo[].class);
        int sizeAfterDelete = List.of(Objects.requireNonNull(responseAfterDelete.getBody())).size();

        assertThat(sizeAfterDelete).isEqualTo(0);
    }

}
