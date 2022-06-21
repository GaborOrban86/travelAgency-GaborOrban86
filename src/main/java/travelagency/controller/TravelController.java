package travelagency.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import travelagency.dto.TravelCreateCommand;
import travelagency.dto.TravelInfo;
import travelagency.dto.TravelModifyCommand;
import travelagency.service.TravelService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/travels")
@Tag(name = "Operations on travels")
@Slf4j
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    @Operation(summary = "Save travel", description = "Save travel.")
    @ResponseStatus(HttpStatus.CREATED)
    public TravelInfo save(@Valid @RequestBody TravelCreateCommand command) {
        log.info("Http request, POST /api/travels, body: " + command.toString());
        return travelService.saveTravel(command);
    }

    @GetMapping("/{travelId}")
    @Operation(summary = "Find travel by id", description = "Find travel by id.")
    @ResponseStatus(HttpStatus.OK)
    public TravelInfo getTravelById(@PathVariable("travelId") Integer travelId) {
        log.info("Http request, GET /api/travels/{travelId}, parameter: " + travelId);
        return travelService.findTravelById(travelId);
    }

    @GetMapping
    @Operation(summary = "List all travels", description = "List all travels.")
    @ResponseStatus(HttpStatus.OK)
    public List<TravelInfo> findAll() {
        log.info("Http request, GET /api/travels");
        return travelService.findAllTravels();
    }

    @PutMapping("/{travelId}")
    @Operation(summary = "Modify travel dates", description = "Modify travel dates.")
    @ResponseStatus(HttpStatus.OK)
    public TravelInfo modifyTravel(@PathVariable("travelId") Integer travelId,
                                   @RequestBody @Valid TravelModifyCommand updateCommand) {
        log.info("Http request, PUT /api/travels/{travelId}, body: " + updateCommand.toString());
        return travelService.updateTravelDates(travelId, updateCommand);
    }

    @DeleteMapping("/{travelId}")
    @Operation(summary = "Delete travel", description = "Delete travel.")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTravel(@PathVariable("travelId") Integer travelId) {
        log.info("Http request, DELETE /api/travels/{travelId}, parameter: "
                + travelId);
        travelService.deleteTravel(travelId);
    }
}

