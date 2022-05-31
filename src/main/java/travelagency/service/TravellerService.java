package travelagency.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TravellerService {

    private final TravellerService travellerService;
    private final ModelMapper modelMapper;

    public TravellerService(TravellerService travellerService, ModelMapper modelMapper) {
        this.travellerService = travellerService;
        this.modelMapper = modelMapper;
    }
}
