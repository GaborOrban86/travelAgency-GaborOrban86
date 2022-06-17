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
import travelagency.dto.ProgramCreateCommand;
import travelagency.dto.ProgramInfo;
import travelagency.dto.ProgramModifyCommand;
import travelagency.exceptionhandling.ProgramNotFoundException;
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

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ProgramService programService;

    private Program program;
    private Travel travel;

    @BeforeEach
    void setUp() {
        travel = new Travel();
        travel.setDestination(new Destination(1, "Naples", 20000));
        travel.setAccommodation(new Accommodation());
        travel.setStartDate(LocalDate.of(2022, Month.JULY, 10));
        travel.setEndDate(LocalDate.of(2022, Month.JULY, 12));
        travel.setPrograms(new ArrayList<>());
        travel.setTravellers(new ArrayList<>());
        travel.setDays(2);
        travel.setWholePrice(20000);
        travel.setDeleted(false);

        program = new Program();
        program.setName("Hide and Seek");
        program.setDescription("It's a good game");
        program.setGuide("Guide Richie");
        program.setPrice(5000);
        program.setTravel(travel);
        program.setDeleted(false);
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
        Assertions.assertEquals(mappedProgram, result);
    }

    @Test
    void testFindProgramById_Success() {
        when(programRepository.findById(1)).thenReturn(program);

        ProgramInfo infoFromProgram = modelMapper.map(program, ProgramInfo.class);
        ProgramInfo result = programService.findProgramById(1);
        Assertions.assertEquals(infoFromProgram, result);
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
    void testDeleteProgram_Success() {
        when(programRepository.findById(1)).thenReturn(program);
        programService.deleteProgram(1);
    }
}
