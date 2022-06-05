package travelagency.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Accommodation;
import travelagency.domain.Destination;
import travelagency.domain.Travel;
import travelagency.dto.*;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.exceptionhandling.DestinationNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.repository.AccommodationRepository;
import travelagency.repository.DestinationRepository;
import travelagency.repository.ProgramRepository;
import travelagency.repository.TravelRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
public class TravelService {

    private final TravelRepository travelRepository;
    private final DestinationRepository destinationRepository;
    private final AccommodationRepository accommodationRepository;
    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;

    public TravelService(TravelRepository travelRepository, DestinationRepository destinationRepository,
                         AccommodationRepository accommodationRepository, ProgramRepository programRepository, ModelMapper modelMapper) {
        this.travelRepository = travelRepository;
        this.destinationRepository = destinationRepository;
        this.accommodationRepository = accommodationRepository;
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }

    public TravelInfo saveTravel(TravelCreateCommand command) {
        Travel toSave = new Travel();
        Optional<Destination> destinationForTravel = destinationRepository.findByName(command.getCityOfDestination());
        if (destinationForTravel.isEmpty()) {
            throw new DestinationNotFoundException(command.getCityOfDestination());
        }
        toSave.setDestination(destinationForTravel.get());

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
        Optional<Travel> travelFound = Optional.of(travelRepository.findById(id));
        if (travelFound.isEmpty()) {
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

    public TravelInfo updateTravelDates(int id, TravelDatesModifyCommand command) {
        Optional<Travel> travelFound = Optional.of(travelRepository.findById(id));
        if (travelFound.isEmpty()) {
            throw new TravelNotFoundException(id);
        }
        Travel travel = travelFound.get();
        travel.setStartDate(command.getStartDate());
        travel.setEndDate(command.getEndDate());
        int newDays = ((int) DAYS.between(command.getStartDate(), command.getEndDate()));
        travel.setWholePrice(travel.getWholePrice() / travel.getDays() * newDays);
        travel.setDays(newDays);
        return modelMapper.map(travel, TravelInfo.class);
    }

    public void deleteTravel(int id) {
        Optional<Travel> travelFound = Optional.of(travelRepository.findById(id));
        if (travelFound.isEmpty()) {
            throw new TravelNotFoundException(id);
        }
        Travel foundTravel = travelFound.get();
        travelRepository.delete(foundTravel);
    }

    public AccommodationInfo saveAccommodation(AccommodationCreateCommand command) {
        Accommodation toSave = new Accommodation();

        Optional<Travel> travelForAccommodation = Optional.of(travelRepository.findById(command.getTravelId()));

        if (travelForAccommodation.isEmpty()) {
            throw new TravelNotFoundException(command.getTravelId());
        }

        toSave.setName(command.getName());
        toSave.setType(command.getType());
        toSave.setCatering(command.getCatering());
        toSave.setPrice(command.getPrice());


        Travel travelFound = travelForAccommodation.get();
        toSave.setTravel(travelFound);
        travelFound.setWholePrice(travelFound.getWholePrice() + (toSave.getPrice() * travelFound.getDays()));

        Accommodation saved = accommodationRepository.save(toSave);
        return modelMapper.map(saved, AccommodationInfo.class);
    }

    public AccommodationInfo findAccommodationById(Integer id) {
        Optional<Accommodation> accommodationFound = Optional.of(accommodationRepository.findById(id));
        if (accommodationFound.isEmpty()) {
            throw new AccommodationNotFoundException(id);
        }
        return modelMapper.map(accommodationFound, AccommodationInfo.class);
    }

    public List<AccommodationInfo> findAllAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .map(accommodation -> modelMapper.map(accommodation, AccommodationInfo.class))
                .collect(Collectors.toList());
    }

    public AccommodationInfo modifyAccommodation(Integer id, AccommodationModifyCommand command) {
        Accommodation toModify = accommodationRepository.findById(id);
        Optional<Accommodation> accommodationFoundOptional = Optional.of(toModify);
        if (accommodationFoundOptional.isEmpty()) {
            throw new AccommodationNotFoundException(id);
        }
        Travel travelOfAccommodation = toModify.getTravel();
        travelOfAccommodation.setWholePrice(
                (travelOfAccommodation.getWholePrice() - (toModify.getPrice() * travelOfAccommodation.getDays())) +
                        (command.getPrice() * travelOfAccommodation.getDays()));

        toModify.setName(command.getName());
        toModify.setType(command.getType());
        toModify.setCatering(command.getCatering());
        toModify.setPrice(command.getPrice());

        return modelMapper.map(toModify, AccommodationInfo.class);
    }

    public void deleteAccommodation(int id) {
        Optional<Accommodation> accommodationFound = Optional.of(accommodationRepository.findById(id));
        if (accommodationFound.isEmpty()) {
            throw new AccommodationNotFoundException(id);
        }
        Accommodation foundAccommodation = accommodationFound.get();
        accommodationRepository.delete(foundAccommodation);
    }



}
