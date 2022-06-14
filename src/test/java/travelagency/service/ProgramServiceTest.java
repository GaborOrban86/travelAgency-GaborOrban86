package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import travelagency.dto.ProgramModifyCommand;
import travelagency.exceptionhandling.ProgramNotFoundException;
import travelagency.repository.ProgramRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class ProgramServiceTest {

    @Mock
    ProgramRepository programRepository;

    @InjectMocks
    ProgramService programService;

    @Test
    void testListFindAll_emptyList() {

        when(programRepository.findAll()).thenReturn(List.of());
        assertThat(programService.findAllPrograms()).isEmpty();
    }

    @Test
    void testProgramNotFound_exception() {
        when(programRepository.findById(1)).thenReturn(null);
        assertThrows(ProgramNotFoundException.class, () ->
                programService.modifyProgram(1, new ProgramModifyCommand()));
    }
}
