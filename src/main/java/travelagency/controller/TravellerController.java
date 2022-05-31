package travelagency.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import travelagency.service.TravellerService;

@RestController
@RequestMapping("/api/travellers")
public class TravellerController {

    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }
}
