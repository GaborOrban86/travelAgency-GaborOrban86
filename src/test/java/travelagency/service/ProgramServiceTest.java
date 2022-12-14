package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import travelagency.domain.*;
import travelagency.dto.ProgramCreateCommand;
import travelagency.dto.ProgramInfo;
import travelagency.dto.ProgramModifyCommand;
import travelagency.exceptionhandling.ProgramNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravelWithTravellersException;
import travelagency.repository.ProgramRepository;
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
public class ProgramServiceTest {

    @Mock
    ProgramRepository programRepository;

    @Mock
    TravelRepository travelRepository;

    private final ModelMapper modelMapper = new ModelMapper();

    private ProgramService programService;
    private Program program;
    private Travel travel;
    private Traveller traveller;

    @BeforeEach
    void start() {
        setUp();
    }

    @Test
    void testListFindAll_EmptyList() {
        when(programRepository.findAll()).thenReturn(List.of());
        assertThat(programService.findAllPrograms()).isEmpty();
    }

    @Test
    void testSaveProgram_Success() {
        when(programRepository.save(program)).thenReturn(program);
        when(travelRepository.findById(1)).thenReturn(travel);

        ProgramInfo result = programService.saveProgram(new ProgramCreateCommand(
                "Hide and Seek", "It's a good game", "Guide Richie", 5000, 1));

        when(programRepository.findById(1)).thenReturn(program);

        Program byId = programRepository.findById(1);
        ProgramInfo mappedProgram = modelMapper.map(byId, ProgramInfo.class);
        assertEquals(mappedProgram, result);
    }

    @Test
    void testSaveProgram_Exception() {
        when(travelRepository.findById(1)).thenReturn(null);

        assertThrows(TravelNotFoundException.class, () ->
                programService.saveProgram((new ProgramCreateCommand(
                        "Hide and Seek", "It's a good game", "Guide Richie", 5000, 1))));
    }

    @Test
    void testFindProgramById_Success() {
        when(programRepository.findById(1)).thenReturn(program);

        ProgramInfo infoFromProgram = modelMapper.map(program, ProgramInfo.class);
        ProgramInfo result = programService.findProgramById(1);
        assertEquals(infoFromProgram, result);
    }

    @Test
    void testFindProgramById_Exception() {
        when(programRepository.findById(1)).thenReturn(null);
        assertThrows(ProgramNotFoundException.class, () ->
                programService.modifyProgram(1, new ProgramModifyCommand()));
    }

    @Test
    void testModifyProgram_Success() {
        when(programRepository.findById(1)).thenReturn(program);

        ProgramInfo result = programService.modifyProgram(1, new ProgramModifyCommand("name",
                "description", "guide", 1));
        Program byId = programRepository.findById(1);
        ProgramInfo mappedProgram = modelMapper.map(byId, ProgramInfo.class);
        assertEquals(mappedProgram, result);
    }

    @Test
    void testModifyProgram_Exception() {
        travel.getTravellers().add(traveller);
        when(programRepository.findById(1)).thenReturn(program);

        assertThrows(TravelWithTravellersException.class, () ->
                programService.modifyProgram(1, new ProgramModifyCommand(
                        "name", "description", "guide", 1)));
    }

    @Test
    void testDeleteProgram_Success() {
        when(programRepository.findById(1)).thenReturn(program);
        programService.deleteProgram(1);
    }

    @Test
    void testDeleteProgram_Exception() {
        when(programRepository.findById(1)).thenReturn(null);
        assertThrows(ProgramNotFoundException.class, () ->
                programService.deleteProgram(1));

        program.getTravel().getTravellers().add(traveller);
        when(programRepository.findById(1)).thenReturn(program);
        assertThrows(TravelWithTravellersException.class, () ->
                programService.deleteProgram(1));
    }

    public void setUp() {
        programService = new ProgramService(programRepository, travelRepository, modelMapper);
        travel = new Travel();
        travel.setDestination(new Destination(1, "Naples", 20000));
        travel.setAccommodation(new Accommodation());
        travel.setStartDate(LocalDate.of(2022, Month.JULY, 10));
        travel.setEndDate(LocalDate.of(2022, Month.JULY, 12));
        travel.setPrograms(new ArrayList<>());
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

        program = new Program();
        program.setName("Hide and Seek");
        program.setDescription("It's a good game");
        program.setGuide("Guide Richie");
        program.setPrice(5000);
        program.setTravel(travel);
        program.setDeleted(false);
    }
}
