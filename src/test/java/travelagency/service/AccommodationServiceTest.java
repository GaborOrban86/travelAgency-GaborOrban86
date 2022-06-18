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
import travelagency.domain.Traveller;
import travelagency.dto.AccommodationCreateCommand;
import travelagency.dto.AccommodationInfo;
import travelagency.dto.AccommodationModifyCommand;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravelWithTravellersException;
import travelagency.repository.AccommodationRepository;
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
public class AccommodationServiceTest {

    @Mock
    AccommodationRepository accommodationRepository;

    @Mock
    TravelRepository travelRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    AccommodationService accommodationService;

    private Accommodation accommodation;
    private Travel travel;
    private Traveller traveller;

    @BeforeEach
    void start() {
        setUp();
    }

    @Test
    void testFindAll_EmptyList() {
        when(accommodationRepository.findAll()).thenReturn(List.of());
        assertThat(accommodationService.findAllAccommodations()).isEmpty();
    }

    @Test
    void testSaveAccommodation_Success() {
        when(accommodationRepository.save(accommodation)).thenReturn(accommodation);
        when(travelRepository.findById(1)).thenReturn(travel);

        AccommodationInfo result = accommodationService.saveAccommodation(new AccommodationCreateCommand(
                "Hilton Hotel", "SOLO", "FULL", 1, 10000));

        when(accommodationRepository.findById(1)).thenReturn(accommodation);

        Accommodation byId = accommodationRepository.findById(1);
        AccommodationInfo mappedAccommodation = modelMapper.map(byId, AccommodationInfo.class);

        Assertions.assertEquals(mappedAccommodation, result);
    }

    @Test
    void testSaveAccommodation_Exception() {
        when(travelRepository.findById(1)).thenReturn(null);

        assertThrows(TravelNotFoundException.class, () ->
                accommodationService.saveAccommodation((new AccommodationCreateCommand(
                        "Hilton Hotel", "SOLO", "FULL", 1, 10000))));
    }

    @Test
    void testFindAccommodationById_Success() {
        when(accommodationRepository.findById(1)).thenReturn(accommodation);

        AccommodationInfo infoFromAccommodation = modelMapper.map(accommodation, AccommodationInfo.class);
        AccommodationInfo result = accommodationService.findAccommodationById(1);
        Assertions.assertEquals(infoFromAccommodation, result);
    }

    @Test
    void testFindAccommodationById_Exception() {
        when(accommodationRepository.findById(1)).thenReturn(null);
        assertThrows(AccommodationNotFoundException.class, () ->
                accommodationService.modifyAccommodation(1, new AccommodationModifyCommand()));
    }

    @Test
    void testModifyAccommodation_Success() {
        when(accommodationRepository.findById(1)).thenReturn(accommodation);

        AccommodationInfo result = accommodationService.modifyAccommodation(1, new AccommodationModifyCommand(
                "Hilltop Hotel", "FAMILY", "FULL", 15000));
        Accommodation byId = accommodationRepository.findById(1);
        AccommodationInfo mappedAccommodation = modelMapper.map(byId, AccommodationInfo.class);
        assertEquals(mappedAccommodation, result);
    }

    @Test
    void testModifyAccommodation_Exception() {
        travel.getTravellers().add(traveller);
        when(accommodationRepository.findById(1)).thenReturn(accommodation);

        assertThrows(TravelWithTravellersException.class, () ->
                accommodationService.modifyAccommodation(1, new AccommodationModifyCommand(
                        "Hilltop Hotel", "FAMILY", "FULL", 15000)));
    }

    @Test
    void testDeleteAccommodation_Success() {
        when(accommodationRepository.findById(1)).thenReturn(accommodation);
        accommodationService.deleteAccommodation(1);
    }

    @Test
    void testDeleteAccommodation_Exception() {
        when(accommodationRepository.findById(1)).thenReturn(null);
        assertThrows(AccommodationNotFoundException.class, () ->
                accommodationService.deleteAccommodation(1));

        accommodation.getTravel().getTravellers().add(traveller);
        when(accommodationRepository.findById(1)).thenReturn(accommodation);
        assertThrows(TravelWithTravellersException.class, () ->
                accommodationService.deleteAccommodation(1));
    }


    public void setUp() {
        travel = new Travel();
        travel.setDestination(new Destination(1, "Naples", 20000));
        travel.setAccommodation(accommodation);
        travel.setStartDate(LocalDate.of(2022, Month.JULY, 10));
        travel.setEndDate(LocalDate.of(2022, Month.JULY, 12));
        travel.setPrograms(new ArrayList<>());
        travel.setTravellers(new ArrayList<>());
        travel.setDays(2);
        travel.setWholePrice(20000);
        travel.setDeleted(false);

        traveller = new Traveller();
        traveller.setName("Tim Travel");
        traveller.setEmail("tim@travel.hu");
        traveller.setBirthday(LocalDate.of(2000, Month.AUGUST, 1));
        traveller.setAge(21);
        traveller.setTravel(travel);
        traveller.setAllFees(20000);
        traveller.setDeleted(false);

        accommodation = new Accommodation();
        accommodation.setName("Hilton Hotel");
        accommodation.setType("SOLO");
        accommodation.setCatering("FULL");
        accommodation.setTravel(travel);
        accommodation.setPrice(10000);
        accommodation.setDeleted(false);
    }
}
