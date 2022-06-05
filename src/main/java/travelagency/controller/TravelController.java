package travelagency.controller;

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
@Slf4j
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TravelInfo save(@RequestBody TravelCreateCommand command) {
        log.info("Http request, POST /api/travels, body: " + command.toString());
        return travelService.saveTravel(command);
    }

    @GetMapping("/{travelId}")
    @ResponseStatus(HttpStatus.OK)
    public TravelInfo getTravelById(@PathVariable("travelId") Integer travelId) {
        log.info("Http request, GET /api/travels/{travelId}, parameter: " + travelId);
        return travelService.findTravelById(travelId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TravelInfo> findAll() {
        return travelService.findAllTravels();
    }

    @PostMapping("/{travelId}")
    @ResponseStatus(HttpStatus.OK)
    public TravelInfo modifyTravel(@PathVariable("travelId") Integer travelId,
                                         @RequestBody @Valid TravelModifyCommand updateCommand) {
        log.info("Http request, POST /api/travels/{travelId}, body: " + updateCommand.toString());
        return travelService.updateTravelDates(travelId, updateCommand);
    }

    @DeleteMapping("/{travelId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTravel(@PathVariable("travelId") Integer travelId) {
        log.info("Http request, DELETE /api/travels/{travelId}, parameter: "
                + travelId);
        travelService.deleteTravel(travelId);
    }
}

