package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import travelagency.dto.TravelModifyCommand;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.repository.TravelRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class TravelServiceTest {

    @Mock
    TravelRepository travelRepository;

    @InjectMocks
    TravelService travelService;

    @Test
    void testListFindAll_emptyList() {

        when(travelRepository.findAll()).thenReturn(List.of());
        assertThat(travelService.findAllTravels()).isEmpty();
    }

    @Test
    void testTravelNotFound_exception() {
        when(travelRepository.findById(1)).thenReturn(null);
        assertThrows(TravelNotFoundException.class, () -> travelService.updateTravelDates(1, new TravelModifyCommand()));
    }

}
