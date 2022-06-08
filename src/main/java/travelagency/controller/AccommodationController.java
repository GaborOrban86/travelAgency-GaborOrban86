package travelagency.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import travelagency.dto.AccommodationCreateCommand;
import travelagency.dto.AccommodationInfo;
import travelagency.dto.AccommodationModifyCommand;
import travelagency.service.AccommodationService;

import javax.validation.Valid;
import java.util.List;

@RestController
    @RequestMapping("/api/accommodations")
@Slf4j
public class AccommodationController {

    private final AccommodationService accommodationService;

    public AccommodationController(AccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccommodationInfo save(@Valid @RequestBody AccommodationCreateCommand command) {
        log.info("Http request, POST /api/accommodations, body: " + command.toString());
        return accommodationService.saveAccommodation(command);
    }

    @GetMapping("/{accommodationId}")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationInfo getAccommodationById(@PathVariable("accommodationId") Integer accommodationId) {
        log.info("Http request, GET /api/travels/{accommodationId}, parameter: " + accommodationId);
        return accommodationService.findAccommodationById(accommodationId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AccommodationInfo> findAll() {
        return accommodationService.findAllAccommodations();
    }

    @PostMapping("/{accommodationId}")
    @ResponseStatus(HttpStatus.OK)
    public AccommodationInfo modifyAccommodation(@PathVariable("accommodationId") Integer accommodationId,
                                                 @RequestBody @Valid AccommodationModifyCommand updateCommand) {
        log.info("Http request, POST /api/accommodations/{accommodationId}, body: " + updateCommand.toString());
        return accommodationService.modifyAccommodation(accommodationId, updateCommand);
    }

    @DeleteMapping("/{accommodationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteAccommodation(@PathVariable("accommodationId") Integer accommodationId) {
        log.info("Http request, DELETE /api/accommodations/{accommodationId}, parameter: "
                + accommodationId);
        accommodationService.deleteAccommodation(accommodationId);
    }
}
