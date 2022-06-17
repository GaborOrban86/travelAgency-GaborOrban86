package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import travelagency.domain.Accommodation;
import travelagency.domain.Destination;
import travelagency.domain.Travel;
import travelagency.dto.AccommodationInfo;
import travelagency.dto.AccommodationModifyCommand;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.repository.AccommodationRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class AccommodationServiceTest {

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    AccommodationService accommodationService;

    private Accommodation accommodation;

    @BeforeEach
    void setUp() {
        Travel travel = new Travel();
        travel.setId(1);
        travel.setDestination(new Destination());
        travel.setAccommodation(new Accommodation());
        travel.setStartDate(LocalDate.of(2022, Month.JULY, 10));
        travel.setEndDate(LocalDate.of(2022, Month.JULY, 12));
        travel.setPrograms(new ArrayList<>());
        travel.setTravellers(new ArrayList<>());
        travel.setDays(2);
        travel.setWholePrice(20000);
        travel.setDeleted(false);

        accommodation = new Accommodation();
        accommodation.setId(1);
        accommodation.setName("Hilton Hotel");
        accommodation.setType("SOLO");
        accommodation.setCatering("FULL");
        accommodation.setTravel(travel);
        accommodation.setPrice(10000);
        accommodation.setDeleted(false);
    }

    @Test
    void testFindAll_EmptyList() {
        when(accommodationRepository.findAll()).thenReturn(List.of());
        assertThat(accommodationService.findAllAccommodations()).isEmpty();
    }

    @Test
    void testFindAccommodationById_Success() {
        when(accommodationRepository.findById(1)).thenReturn(accommodation);

        AccommodationInfo infoFromAccommodation = modelMapper.map(accommodation, AccommodationInfo.class);
        AccommodationInfo result = accommodationService.findAccommodationById(1);
        Assertions.assertEquals(infoFromAccommodation, result);
    }

    @Test
    void testAccommodationNotFound_Exception() {
        when(accommodationRepository.findById(1)).thenReturn(null);
        assertThrows(AccommodationNotFoundException.class, () ->
                accommodationService.modifyAccommodation(1, new AccommodationModifyCommand()));
    }

    @Test
    void testDeleteAccommodation_Success() {
        when(accommodationRepository.findById(1)).thenReturn(accommodation);
        accommodationService.deleteAccommodation(1);
    }
}
