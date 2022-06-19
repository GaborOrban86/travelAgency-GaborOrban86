package travelagency.controller;

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
public class ProgramControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @BeforeEach
    void putTravelAndProgram() {
        restTemplate.postForObject("/api/travels",
                new TravelCreateCommand(2, LocalDate.of(2022, Month.SEPTEMBER, 10),
                        LocalDate.of(2022, Month.SEPTEMBER, 13)), TravelInfo.class);
        restTemplate.postForObject("/api/programs",
                new ProgramCreateCommand("Hide and Seek", "Funny Game", "Peter Program",
                        12000, 1), ProgramCreateCommand.class);
    }

    @Test
    void testGetProgramById_Success() {
        ProgramInfo programInfo = restTemplate.getForObject("/api/programs/1", ProgramInfo.class);
        assertThat(programInfo)
                .extracting(ProgramInfo::getGuide)
                .isEqualTo("Peter Program");
    }

    @Test
    void testGetProgramById_BadRequest() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/programs/10", String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testModifyProgram_Success() {
        restTemplate.put("/api/programs/1",
                new ProgramModifyCommand("Dodgeball", "Another funny game.", "Duck Do", 4000),
                ProgramModifyCommand.class);

        ProgramInfo modified = restTemplate.getForObject("/api/programs/1", ProgramInfo.class);
        assertThat(modified)
                .extracting(ProgramInfo::getPrice)
                .isEqualTo(4000);
    }

    @Test
    void testPutMoreProgramWithTraveller_BadRequest() {
        restTemplate.postForObject("/api/accommodations",
                new AccommodationCreateCommand("Hilton", "SOLO", "FULL", 1, 10000),
                AccommodationInfo.class);

        restTemplate.postForObject("/api/travellers",
                new TravellerCreateCommand("Pityu", "pityu@pityu.hu",
                        LocalDate.of(1986, Month.JUNE, 9), 1), TravellerCreateCommand.class);

        ResponseEntity<String> response = restTemplate.postForEntity("/api/programs",
                new ProgramCreateCommand("Hide and Seek", "Funny Game", "Peter Program",
                        12000, 1), String.class);
        assertThat(response.getStatusCodeValue()).isEqualTo(400);
    }

    @Test
    void testDeleteProgram_Success() {
        ResponseEntity<ProgramInfo[]> response =
                restTemplate.getForEntity("/api/programs", ProgramInfo[].class);
        List<ProgramInfo> programInfos = List.of(Objects.requireNonNull(response.getBody()));

        int sizeBeforeDelete = programInfos.size();
        assertThat(sizeBeforeDelete).isEqualTo(1);

        restTemplate.delete("/api/programs/1");

        ResponseEntity<ProgramInfo[]> responseAfterDelete =
                restTemplate.getForEntity("/api/programs", ProgramInfo[].class);
        List<ProgramInfo> programInfosAfterDelete = List.of(Objects.requireNonNull(responseAfterDelete.getBody()));
        int sizeAfterDelete = programInfosAfterDelete.size();

        assertThat(sizeAfterDelete).isEqualTo(0);
    }
}
