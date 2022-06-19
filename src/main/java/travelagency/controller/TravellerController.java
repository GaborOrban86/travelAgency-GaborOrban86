package travelagency.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import travelagency.dto.TravellerCreateCommand;
import travelagency.dto.TravellerInfo;
import travelagency.dto.TravellerModifyCommand;
import travelagency.service.TravellerService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/travellers")
@Tag(name = "Operations on travellers")
@Slf4j
public class TravellerController {

    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @PostMapping
    @Operation(summary = "Save traveller", description = "Save traveller.")
    @ResponseStatus(HttpStatus.CREATED)
    public TravellerInfo save(@Valid @RequestBody TravellerCreateCommand command) {
        log.info("Http request, POST /api/travellers, body: " + command.toString());
        return travellerService.saveTraveller(command);
    }

    @GetMapping("/{travellerId}")
    @Operation(summary = "Find traveller by id", description = "Find traveller by id.")
    @ResponseStatus(HttpStatus.OK)
    public TravellerInfo getTravellerById(@PathVariable("travellerId") Integer travellerId) {
        log.info("Http request, GET /api/travels/{travellerId}, parameter: " + travellerId);
        return travellerService.findTravellerById(travellerId);
    }

    @GetMapping
    @Operation(summary = "List all travellers", description = "List all travellers.")
    @ResponseStatus(HttpStatus.OK)
    public List<TravellerInfo> findAll() {
        return travellerService.findAllTravellers();
    }

    @PutMapping("/{travellerId}")
    @Operation(summary = "Modify traveller's name and email", description = "Modify traveller's name and email.")
    @ResponseStatus(HttpStatus.OK)
    public TravellerInfo modifyTraveller(@PathVariable("travellerId") Integer travellerId,
                                         @RequestBody @Valid TravellerModifyCommand updateCommand) {
        log.info("Http request, POST /api/travellers/{travellerId}, body: " + updateCommand.toString());
        return travellerService.modifyTravellerNameAndEmail(travellerId, updateCommand);
    }

    @DeleteMapping("/{travellerId}")
    @Operation(summary = "Delete traveller", description = "Delete traveller.")
    @ResponseStatus(HttpStatus.OK)
    public void deleteTraveller(@PathVariable("travellerId") Integer travellerId) {
        log.info("Http request, DELETE /api/travellers/{travellerId}, parameter: "
                + travellerId);
        travellerService.deleteTraveller(travellerId);
    }
}