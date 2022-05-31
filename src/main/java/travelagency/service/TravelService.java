package travelagency.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.repository.TravelRepository;

@Service
@Transactional
public class TravelService {

    private TravelRepository travelRepository;
    private ModelMapper modelMapper;
}
