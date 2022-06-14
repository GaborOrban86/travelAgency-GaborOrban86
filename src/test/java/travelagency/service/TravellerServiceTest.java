package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import travelagency.dto.TravellerModifyCommand;
import travelagency.exceptionhandling.TravellerNotFoundException;
import travelagency.repository.TravellerRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class TravellerServiceTest {

    @Mock
    TravellerRepository travellerRepository;

    @InjectMocks
    TravellerService travellerService;

    @Test
    void testListFindAll_emptyList() {

        when(travellerRepository.findAll()).thenReturn(List.of());
        assertThat(travellerService.findAllTravellers()).isEmpty();
    }

    @Test
    void testTravellerNotFound_exception() {
        when(travellerRepository.findById(1)).thenReturn(null);
        assertThrows(TravellerNotFoundException.class, () ->
                travellerService.modifyTravellerNameAndEmail(1, new TravellerModifyCommand()));
    }
}
