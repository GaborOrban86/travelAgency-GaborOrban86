package travelagency.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Accommodation;
import travelagency.domain.Destination;
import travelagency.domain.Travel;
import travelagency.dto.TravelCreateCommand;
import travelagency.dto.TravelInfo;
import travelagency.dto.TravelModifyCommand;
import travelagency.exceptionhandling.DestinationNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.repository.DestinationRepository;
import travelagency.repository.TravelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class TravelService {

    private final TravelRepository travelRepository;
    private final DestinationRepository destinationRepository;
    private final ModelMapper modelMapper;

    public TravelService(TravelRepository travelRepository, DestinationRepository destinationRepository, ModelMapper modelMapper) {
        this.travelRepository = travelRepository;
        this.destinationRepository = destinationRepository;
        this.modelMapper = modelMapper;
    }

    public TravelInfo saveTravel(TravelCreateCommand command) {
        Travel toSave = new Travel();
        Destination destinationForTravel = destinationRepository.findByName(command.getCityOfDestination());
        if (destinationForTravel == null) {
            throw new DestinationNotFoundException(command.getCityOfDestination());
        }
        toSave.setDestination(destinationForTravel);

        toSave.setStartDate(command.getStartDate());
        toSave.setEndDate(command.getEndDate());
        toSave.setAccommodation(new Accommodation());
        toSave.setTravellers(new ArrayList<>());
        toSave.setPrograms(new ArrayList<>());
        toSave.setDays((int) DAYS.between(command.getStartDate(), command.getEndDate()));
        toSave.setWholePrice(toSave.getDestination().getPrice() * toSave.getDays());

        Travel saved = travelRepository.save(toSave);
        return modelMapper.map(saved, TravelInfo.class);
    }

    public TravelInfo findTravelById(Integer id) {
        Travel travelFound = travelRepository.findById(id);
        if (travelFound == null) {
            throw new TravelNotFoundException(id);
        }
        return modelMapper.map(travelFound, TravelInfo.class);
    }

    public List<TravelInfo> findAllTravels() {
        List<Travel> travels = travelRepository.findAll();
        return travels.stream()
                .map(travel -> modelMapper.map(travel, TravelInfo.class))
                .collect(Collectors.toList());
    }

    public TravelInfo updateTravelDates(int id, TravelModifyCommand command) {
        Travel travelFound = travelRepository.findById(id);
        if (travelFound == null) {
            throw new TravelNotFoundException(id);
        }
        travelFound.setStartDate(command.getStartDate());
        travelFound.setEndDate(command.getEndDate());
        int newDays = ((int) DAYS.between(command.getStartDate(), command.getEndDate()));
        travelFound.setWholePrice(travelFound.getWholePrice() / travelFound.getDays() * newDays);
        travelFound.setDays(newDays);
        return modelMapper.map(travelFound, TravelInfo.class);
    }

    public void deleteTravel(int id) {
        Travel travelFound = travelRepository.findById(id);
        if (travelFound == null) {
            throw new TravelNotFoundException(id);
        }
        travelRepository.delete(travelFound);
    }
}
