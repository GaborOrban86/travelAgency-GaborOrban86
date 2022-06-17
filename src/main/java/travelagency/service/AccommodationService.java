package travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Accommodation;
import travelagency.domain.Travel;
import travelagency.domain.enums.AccommodationCatering;
import travelagency.domain.enums.AccommodationType;
import travelagency.dto.AccommodationCreateCommand;
import travelagency.dto.AccommodationInfo;
import travelagency.dto.AccommodationModifyCommand;
import travelagency.exceptionhandling.AccommodationAlreadyExistsException;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravelWithTravellersException;
import travelagency.repository.AccommodationRepository;
import travelagency.repository.TravelRepository;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@Transactional
public class AccommodationService {

    AccommodationRepository accommodationRepository;
    TravelRepository travelRepository;
    ModelMapper modelMapper;

    public AccommodationService(AccommodationRepository accommodationRepository, TravelRepository travelRepository, ModelMapper modelMapper) {
        this.accommodationRepository = accommodationRepository;
        this.travelRepository = travelRepository;
        this.modelMapper = modelMapper;
    }

    public AccommodationInfo saveAccommodation(AccommodationCreateCommand command) {
        Accommodation toSave = new Accommodation();

        Travel travelForAccommodation = travelRepository.findById(command.getTravelId());

        if (travelForAccommodation == null || travelForAccommodation.isDeleted()) {
            throw new TravelNotFoundException(command.getTravelId());
        }
        if (travelForAccommodation.getAccommodation() != null) {
            throw new AccommodationAlreadyExistsException(travelForAccommodation.getAccommodation().getId());
        }

        toSave.setName(command.getName());
        toSave.setType(command.getType().toUpperCase(Locale.ROOT));
        toSave.setCatering(command.getCatering().toUpperCase(Locale.ROOT));
        toSave.setPrice(command.getPrice());


        travelForAccommodation.setWholePrice(travelForAccommodation.getWholePrice() +
                (toSave.getPrice() * travelForAccommodation.getDays()));

        toSave.setTravel(travelForAccommodation);
        Accommodation saved = accommodationRepository.save(toSave);
        travelForAccommodation.setAccommodation(saved);
        return modelMapper.map(saved, AccommodationInfo.class);
    }

    public AccommodationInfo findAccommodationById(Integer id) {
        Accommodation accommodationFound = accommodationRepository.findById(id);
        if (accommodationFound == null || accommodationFound.isDeleted()) {
            throw new AccommodationNotFoundException(id);
        }
        return modelMapper.map(accommodationFound, AccommodationInfo.class);
    }

    public List<AccommodationInfo> findAllAccommodations() {
        List<Accommodation> accommodations = accommodationRepository.findAll();
        return accommodations.stream()
                .filter(accommodation -> !accommodation.isDeleted())
                .map(accommodation -> modelMapper.map(accommodation, AccommodationInfo.class))
                .collect(Collectors.toList());
    }

    public AccommodationInfo modifyAccommodation(Integer id, AccommodationModifyCommand command) {
        Accommodation toModify = accommodationRepository.findById(id);
        if (toModify == null) {
            throw new AccommodationNotFoundException(id);
        }
        Travel travelOfAccommodation = toModify.getTravel();
        if (!travelOfAccommodation.getTravellers().isEmpty()) {
            throw new TravelWithTravellersException();
        }
        travelOfAccommodation.setWholePrice(
                (travelOfAccommodation.getWholePrice() - (toModify.getPrice() * travelOfAccommodation.getDays())) +
                        (command.getPrice() * travelOfAccommodation.getDays()));

        toModify.setName(command.getName());
        toModify.setType(command.getType().toUpperCase(Locale.ROOT));
        toModify.setCatering(command.getCatering().toUpperCase(Locale.ROOT));
        toModify.setPrice(command.getPrice());

        return modelMapper.map(toModify, AccommodationInfo.class);
    }

    public void deleteAccommodation(int id) {
        Accommodation accommodationFound = accommodationRepository.findById(id);
        if (accommodationFound == null || accommodationFound.isDeleted()) {
            throw new AccommodationNotFoundException(id);
        }
        Travel travelOfAccommodation = accommodationFound.getTravel();
        if (!travelOfAccommodation.getTravellers().isEmpty()) {
            throw new TravelWithTravellersException();
        }

        Integer allDaysAccommodationPrice = accommodationFound.getPrice() * travelOfAccommodation.getDays();
        travelOfAccommodation.setWholePrice(travelOfAccommodation.getWholePrice() - allDaysAccommodationPrice);
        travelOfAccommodation.setAccommodation(null);
        accommodationFound.setTravel(null);
        accommodationRepository.delete(accommodationFound);
    }
}
