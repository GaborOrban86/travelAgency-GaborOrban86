package travelagency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import travelagency.dto.ProgramCreateCommand;
import travelagency.dto.ProgramInfo;
import travelagency.dto.ProgramModifyCommand;
import travelagency.service.ProgramService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/programs")
@Tag(name = "Operations on programs")
@Slf4j
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    @Operation(summary = "Save program", description = "Save program.")
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramInfo save(@Valid @RequestBody ProgramCreateCommand command) {
        log.info("Http request, POST /api/programs, body: " + command.toString());
        return programService.saveProgram(command);
    }

    @GetMapping("/{programId}")
    @Operation(summary = "Find program by id", description = "Find program by id.")
    @ResponseStatus(HttpStatus.OK)
    public ProgramInfo getProgramById(@PathVariable("programId") Integer programId) {
        log.info("Http request, GET /api/travels/{programId}, parameter: " + programId);
        return programService.findProgramById(programId);
    }

    @GetMapping
    @Operation(summary = "List all programs", description = "List all programs.")
    @ResponseStatus(HttpStatus.OK)
    public List<ProgramInfo> findAll() {
        return programService.findAllPrograms();
    }

    @PutMapping("/{programId}")
    @Operation(summary = "Modify program details", description = "Modify program details.")
    @ResponseStatus(HttpStatus.OK)
    public ProgramInfo modifyProgram(@PathVariable("programId") Integer programId,
                                     @RequestBody @Valid ProgramModifyCommand updateCommand) {
        log.info("Http request, POST /api/programs/{programId}, body: " + updateCommand.toString());
        return programService.modifyProgram(programId, updateCommand);
    }

    @DeleteMapping("/{programId}")
    @Operation(summary = "Delete program", description = "Delete program.")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProgram(@PathVariable("programId") Integer programId) {
        log.info("Http request, DELETE /api/programs/{programId}, parameter: "
                + programId);
        programService.deleteProgram(programId);
    }
}
