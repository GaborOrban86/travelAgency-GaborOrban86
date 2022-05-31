package travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.repository.AccommodationRepository;

@Service
@Transactional
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;
    private final ModelMapper modelMapper;

    public AccommodationService(AccommodationRepository accommodationRepository, ModelMapper modelMapper) {
        this.accommodationRepository = accommodationRepository;
        this.modelMapper = modelMapper;
    }
}
