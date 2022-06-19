package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import travelagency.domain.*;
import travelagency.dto.TravellerCreateCommand;
import travelagency.dto.TravellerInfo;
import travelagency.dto.TravellerModifyCommand;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravellerNotFoundException;
import travelagency.repository.TravelRepository;
import travelagency.repository.TravellerRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class TravellerServiceTest {

    @Mock
    TravellerRepository travellerRepository;

    @Mock
    TravelRepository travelRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private TravellerService travellerService;
    private Traveller traveller;
    private Travel travel;

    @BeforeEach
    void start() {
        setUp();
    }

    @Test
    void testListFindAll_EmptyList() {
        when(travellerRepository.findAll()).thenReturn(List.of());
        assertThat(travellerService.findAllTravellers()).isEmpty();
    }

    @Test
    void testSaveTraveller_Success() {

        when(travellerRepository.save(traveller)).thenReturn(traveller);
        when(travelRepository.findById(1)).thenReturn(travel);

        TravellerInfo result = travellerService.saveTraveller(new TravellerCreateCommand(
                "Tim Travel", "tim@travel.hu", LocalDate.of(2000, Month.AUGUST, 1), 1));

        when(travellerRepository.findById(1)).thenReturn(traveller);

        Traveller byId = travellerRepository.findById(1);
        TravellerInfo mappedTraveller = modelMapper.map(byId, TravellerInfo.class);
        assertEquals(mappedTraveller, result);
    }

    @Test
    void testSaveTraveller_Exception() {
        when(travelRepository.findById(1)).thenReturn(null);

        assertThrows(TravelNotFoundException.class, () ->
                travellerService.saveTraveller(new TravellerCreateCommand("Tim Travel", "tim@travel.hu",
                        LocalDate.of(2000, Month.AUGUST, 1), 1)));
    }

    @Test
    void testFindTravellerById_Success() {
        when(travellerRepository.findById(1)).thenReturn(traveller);
        TravellerInfo infoFromTraveller = modelMapper.map(traveller, TravellerInfo.class);
        TravellerInfo result = travellerService.findTravellerById(1);
        assertEquals(infoFromTraveller, result);
    }

    @Test
    void testTravellerNotFound_Exception() {
        when(travellerRepository.findById(1)).thenReturn(null);
        assertThrows(TravellerNotFoundException.class, () ->
                travellerService.modifyTravellerNameAndEmail(1, new TravellerModifyCommand()));
    }

    @Test
    void testTravellerFeesSetter_Success() {
        int result = travellerService.travellerFeesSetter(12, 20000, 1.0, 0.5);
        assertEquals(10000, result);

        int result2 = travellerService.travellerFeesSetter(2, 20000, 1.0, 0.5);
        assertEquals(0, result2);
    }

    @Test
    void testModifyTraveller_Success() {
        when(travellerRepository.findById(1)).thenReturn(traveller);

        TravellerInfo result = travellerService.modifyTravellerNameAndEmail(1,
                new TravellerModifyCommand("Little Tim", "tim@little.com"));
        Traveller byId = travellerRepository.findById(1);
        TravellerInfo mappedTraveller = modelMapper.map(byId, TravellerInfo.class);
        assertEquals(mappedTraveller, result);
    }

    @Test
    void testModifyTraveller_Exception() {
        when(travellerRepository.findById(1)).thenReturn(null);

        assertThrows(TravellerNotFoundException.class, () ->
                travellerService.modifyTravellerNameAndEmail(1,
                        new TravellerModifyCommand("Little Tim", "tim@little.com")));
    }

    @Test
    void testDeleteTraveller_Success() {
        when(travellerRepository.findById(1)).thenReturn(traveller);
        travellerService.deleteTraveller(1);
    }

    @Test
    void testDeleteTraveller_Exception() {
        when(travellerRepository.findById(1)).thenReturn(null);
        assertThrows(TravellerNotFoundException.class, () ->
                travellerService.deleteTraveller(1));
    }

    public void setUp() {
        travellerService = new TravellerService(travellerRepository, travelRepository, modelMapper);
        Accommodation accommodation = new Accommodation();
        accommodation.setName("Hilton Hotel");
        accommodation.setType("SOLO");
        accommodation.setCatering("FULL");
        accommodation.setTravel(travel);
        accommodation.setPrice(10000);
        accommodation.setDeleted(false);

        Program program = new Program();
        program.setName("Hide and Seek");
        program.setDescription("It's a good game");
        program.setGuide("Guide Richie");
        program.setPrice(5000);
        program.setTravel(travel);
        program.setDeleted(false);

        travel = new Travel();
        travel.setDestination(new Destination(1, "Naples", 20000));
        travel.setAccommodation(accommodation);
        travel.setStartDate(LocalDate.of(2022, Month.JULY, 10));
        travel.setEndDate(LocalDate.of(2022, Month.JULY, 12));
        List<Program> programs = new ArrayList<>();
        programs.add(program);
        travel.setPrograms(programs);
        travel.setTravellers(new ArrayList<>());
        travel.setDays(2);
        travel.setWholePrice(20000);
        travel.setFullIncome(0);
        travel.setDeleted(false);

        traveller = new Traveller();
        traveller.setName("Tim Travel");
        traveller.setEmail("tim@travel.hu");
        traveller.setBirthday(LocalDate.of(2000, Month.AUGUST, 1));
        traveller.setAge(21);
        traveller.setTravel(travel);
        traveller.setAllFees(20000);
        traveller.setDeleted(false);
    }
}