package travelagency.controller;

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
@Slf4j
public class ProgramController {

    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProgramInfo save(@Valid @RequestBody ProgramCreateCommand command) {
        log.info("Http request, POST /api/programs, body: " + command.toString());
        return programService.saveProgram(command);
    }

    @GetMapping("/{programId}")
    @ResponseStatus(HttpStatus.OK)
    public ProgramInfo getProgramById(@PathVariable("programId") Integer programId) {
        log.info("Http request, GET /api/travels/{programId}, parameter: " + programId);
        return programService.findProgramById(programId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProgramInfo> findAll() {
        return programService.findAllPrograms();
    }

    @PutMapping("/{programId}")
    @ResponseStatus(HttpStatus.OK)
    public ProgramInfo modifyProgram(@PathVariable("programId") Integer programId,
                                     @RequestBody @Valid ProgramModifyCommand updateCommand) {
        log.info("Http request, POST /api/programs/{programId}, body: " + updateCommand.toString());
        return programService.modifyProgram(programId, updateCommand);
    }

    @DeleteMapping("/{programId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProgram(@PathVariable("programId") Integer programId) {
        log.info("Http request, DELETE /api/programs/{programId}, parameter: "
                + programId);
        programService.deleteProgram(programId);
    }
}
