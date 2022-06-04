package travelagency.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.repository.TravelRepository;
import travelagency.repository.TravellerRepository;

@Service
@Transactional
public class TravellerService {
    private final TravellerRepository travellerRepository;
    private final TravelRepository travelRepository;

    public TravellerService(TravellerRepository travellerRepository, TravelRepository travelRepository) {
        this.travellerRepository = travellerRepository;
        this.travelRepository = travelRepository;
    }
}
