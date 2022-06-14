package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import travelagency.dto.AccommodationModifyCommand;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.repository.AccommodationRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class AccommodationServiceTest {

    @Mock
    AccommodationRepository accommodationRepository;

    @InjectMocks
    AccommodationService accommodationService;

    @Test
    void testFindAll_emptyList() {

        when(accommodationRepository.findAll()).thenReturn(List.of());
        assertThat(accommodationService.findAllAccommodations()).isEmpty();
    }

    @Test
    void testAccommodationNotFound_exception() {
        when(accommodationRepository.findById(1)).thenReturn(null);
        assertThrows(AccommodationNotFoundException.class, () ->
                accommodationService.modifyAccommodation(1, new AccommodationModifyCommand()));
    }

}
