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
import travelagency.domain.Program;
import travelagency.domain.Travel;
import travelagency.dto.*;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.repository.DestinationRepository;
import travelagency.repository.TravelRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class TravelServiceTest {

    @Mock
    TravelRepository travelRepository;

    @Mock
    DestinationRepository destinationRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    TravelService travelService;

    private Travel travel;
    private Destination destination;

    @BeforeEach
    void setUp() {
        destination = new Destination();
        destination.setName("Naples");
        destination.setPrice(20000);

        travel = new Travel();
        travel.setDestination(destination);
        travel.setAccommodation(new Accommodation());
        travel.setStartDate(LocalDate.of(2022, Month.JULY, 10));
        travel.setEndDate(LocalDate.of(2022, Month.JULY, 12));
        travel.setPrograms(new ArrayList<>());
        travel.setTravellers(new ArrayList<>());
        travel.setDays(2);
        travel.setWholePrice(20000);
        travel.setDeleted(false);
    }

    @Test
    void testListFindAll_EmptyList() {
        when(travelRepository.findAll()).thenReturn(List.of());
        assertThat(travelService.findAllTravels()).isEmpty();
    }

    @Test
    void testSaveProgram_Success() {
        when(destinationRepository.findById(1)).thenReturn(destination);
        when(travelRepository.findById(1)).thenReturn(travel);

        TravelInfo result = travelService.saveTravel(new TravelCreateCommand(1,
                LocalDate.of(2022, Month.JULY, 10), LocalDate.of(2022, Month.JULY, 12)));

        Travel byId = travelRepository.findById(1);
        TravelInfo mappedTravel = modelMapper.map(byId, TravelInfo.class);
        Assertions.assertEquals(mappedTravel, result);
    }

    @Test
    void testFindTravelById_Success() {
        when(travelRepository.findById(1)).thenReturn(travel);
        TravelInfo infoFromTravel = modelMapper.map(travel, TravelInfo.class);
        TravelInfo result = travelService.findTravelById(1);

        Assertions.assertEquals(infoFromTravel, result);
    }

    @Test
    void testTravelNotFound_Exception() {
        when(travelRepository.findById(1)).thenReturn(null);
        assertThrows(TravelNotFoundException.class, () -> travelService.updateTravelDates(1, new TravelModifyCommand()));
    }

    @Test
    void testModifyTravel_Success() {
        when(travelRepository.findById(1)).thenReturn(travel);

        TravelInfo result = travelService.updateTravelDates(1, new TravelModifyCommand(
                LocalDate.of(2022, Month.JULY, 10), LocalDate.of(2022, Month.JULY, 11)));
        Travel byId = travelRepository.findById(1);
        TravelInfo mappedTravel = modelMapper.map(byId, TravelInfo.class);
        assertEquals(mappedTravel, result);
    }

    @Test
    void testDeleteTravel_Success() {
        when(travelRepository.findById(1)).thenReturn(travel);
        travelService.deleteTravel(1);
    }
}
