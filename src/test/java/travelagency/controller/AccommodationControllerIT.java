package travelagency.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import travelagency.dto.*;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccommodationControllerIT {

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    AccommodationController accommodationController;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void putTravel() {
        restTemplate.postForObject("/api/travels",
                new TravelCreateCommand(2, LocalDate.of(2022, Month.SEPTEMBER, 10),
                        LocalDate.of(2022, Month.SEPTEMBER, 13)), TravelInfo.class);
    }

    @Test
    void testGetAccommodationByIdAndSave_OneAccommodation_Success() {
        restTemplate.postForObject("/api/accommodations",
                new AccommodationCreateCommand("Hilton", "SOLO", "FULL", 1, 10000),
                AccommodationInfo.class);

        AccommodationInfo accommodationInfo = restTemplate.getForObject("/api/accommodations/1", AccommodationInfo.class);
        assertThat(accommodationInfo)
                .extracting(AccommodationInfo::getPrice)
                .isEqualTo(10000);
    }

    @Test
    void putNewAccommodation_BADREQUEST() throws Exception {

        accommodationController.save(new AccommodationCreateCommand(
                "Hilton", "SOLO", "FULL", 1, 10000));

        mockMvc.perform(post("/api/accommodations", new AccommodationCreateCommand(
                        "Hilton", "SOLO", "FULL", 1, 10000)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testModifyAccommodation_SUCCESS() {
        accommodationController.save(new AccommodationCreateCommand(
                "Hilton", "SOLO", "FULL", 1, 10000));

        AccommodationInfo modified = accommodationController.modifyAccommodation(1,
                new AccommodationModifyCommand("Bubu Hotel", "FAMILY", "FULL", 5000));
        assertThat(modified)
                .extracting(AccommodationInfo::getName)
                .isEqualTo("Bubu Hotel");

    }

    @Test
    void testFindAllStatus_OK() throws Exception {
        mockMvc.perform(get("/api/accommodations"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete() {
        accommodationController.save(new AccommodationCreateCommand(
                "Hilton", "SOLO", "FULL", 1, 10000));
        int sizeBeforeDelete = accommodationController.findAll().size();
        assertThat(sizeBeforeDelete).isEqualTo(1);

        accommodationController.deleteAccommodation(1);

        int sizeAfterDelete = accommodationController.findAll().size();
        assertThat(sizeAfterDelete).isEqualTo(0);
    }

}
