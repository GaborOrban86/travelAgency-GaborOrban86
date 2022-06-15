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
import travelagency.dto.ProgramInfo;
import travelagency.dto.ProgramModifyCommand;
import travelagency.exceptionhandling.ProgramNotFoundException;
import travelagency.repository.ProgramRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class ProgramServiceTest {

    @Mock
    ProgramRepository programRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    ProgramService programService;

    private Program program;

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

        program = new Program();
        program.setId(1);
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
    void testDeleteProgram_Success() {
        when(programRepository.findById(1)).thenReturn(program);
        programService.deleteProgram(1);
    }
}
