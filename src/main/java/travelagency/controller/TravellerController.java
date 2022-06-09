package travelagency.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import travelagency.dto.*;
import travelagency.service.TravelService;
import travelagency.service.TravellerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/travellers")
@Slf4j
public class TravellerController {

    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TravellerInfo save(@Valid @RequestBody TravellerCreateCommand command) {
        log.info("Http request, POST /api/travellers, body: " + command.toString());
        return travellerService.saveTraveller(command);
    }

    @GetMapping("/{travellerId}")
    @ResponseStatus(HttpStatus.OK)
    public TravellerInfo getTravelById(@PathVariable("travellerId") Integer travellerId) {
        log.info("Http request, GET /api/travels/{travellerId}, parameter: " + travellerId);
        return travellerService.findTravellerById(travellerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TravellerInfo> findAll() {
        return travellerService.findAllTravellers();
    }

    @PutMapping("/{travellerId}")
    @ResponseStatus(HttpStatus.OK)
    public TravellerInfo modifyTravel(@PathVariable("travellerId") Integer travellerId,
                                   @RequestBody @Valid TravellerModifyCommand updateCommand) {
        log.info("Http request, POST /api/travellers/{travellerId}, body: " + updateCommand.toString());
        return travellerService.modifyTravellerNameAndEmail(travellerId, updateCommand);
    }

    @DeleteMapping("/{travellerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTravel(@PathVariable("travellerId") Integer travellerId) {
        log.info("Http request, DELETE /api/travellers/{travellerId}, parameter: "
                + travellerId);
        travellerService.deleteTraveller(travellerId);
    }
}