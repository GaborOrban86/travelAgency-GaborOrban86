package travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Accommodation;
import travelagency.domain.Travel;
import travelagency.dto.AccommodationCreateCommand;
import travelagency.dto.AccommodationInfo;
import travelagency.dto.AccommodationModifyCommand;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.repository.AccommodationRepository;
import travelagency.repository.TravelRepository;

import java.util.List;
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

        if (travelForAccommodation == null) {
            throw new TravelNotFoundException(command.getTravelId());
        }

        toSave.setName(command.getName());
        toSave.setType(command.getType());
        toSave.setCatering(command.getCatering());
        toSave.setPrice(command.getPrice());


        travelForAccommodation.setWholePrice(travelForAccommodation.getWholePrice() +
                (toSave.getPrice() * travelForAccommodation.getDays()));

        toSave.setTravel(travelForAccommodation);
        Accommodation saved = accommodationRepository.save(toSave);
        return modelMapper.map(saved, AccommodationInfo.class);
    }

    public AccommodationInfo findAccommodationById(Integer id) {
        Accommodation accommodationFound = accommodationRepository.findById(id);
        if (accommodationFound == null) {
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
        if (toModify == null) {
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
        Accommodation accommodationFound = accommodationRepository.findById(id);
        if (accommodationFound == null) {
            throw new AccommodationNotFoundException(id);
        }

        accommodationRepository.delete(accommodationFound);
    }
}
