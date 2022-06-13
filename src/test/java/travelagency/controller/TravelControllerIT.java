package travelagency.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import travelagency.dto.TravelCreateCommand;
import travelagency.dto.TravelInfo;
import travelagency.dto.TravelModifyCommand;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase

public class TravelControllerIT {


    @Autowired
    TravelController travelController;

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetTravelById_BADREQUEST() throws Exception {

        mockMvc.perform(get("/api/travels/10"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testFindAllStatus_OK() throws Exception {
        mockMvc.perform(get("/api/travels"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindAll_EmptyList_Success() {
        List<TravelInfo> travels = travelController.findAll();
        assertThat(travels).hasSize(0);
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    void TestModifyTravel_Success() {
        travelController.save(new TravelCreateCommand(2, LocalDate.of(2022,
                Month.SEPTEMBER, 10), LocalDate.of(2022, Month.SEPTEMBER, 12)));
        TravelInfo travel = travelController.getTravelById(1);
        int result = travel.getWholePrice();
        assertThat(result).isEqualTo(50000);

        travelController.modifyTravel(1, new TravelModifyCommand(LocalDate.of(2022,
                Month.SEPTEMBER, 10), LocalDate.of(2022, Month.SEPTEMBER, 11)));

        TravelInfo newTravel = travelController.getTravelById(1);
        int newResult = newTravel.getWholePrice();
        assertThat(newResult).isEqualTo(25000);
    }

    @Test
    void testDelete() {
        travelController.save(new TravelCreateCommand(2, LocalDate.of(2022,
                Month.SEPTEMBER, 10), LocalDate.of(2022, Month.SEPTEMBER, 12)));
        int sizeBeforeDelete = travelController.findAll().size();
        assertThat(sizeBeforeDelete).isEqualTo(1);

        travelController.deleteTravel(1);

        int sizeAfterDelete = travelController.findAll().size();
        assertThat(sizeAfterDelete).isEqualTo(0);
    }

}
