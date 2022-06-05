package travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Travel;
import travelagency.domain.Traveller;
import travelagency.dto.TravellerCreateCommand;
import travelagency.dto.TravellerInfo;
import travelagency.dto.TravellerModifyCommand;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravellerNotFoundException;
import travelagency.repository.TravelRepository;
import travelagency.repository.TravellerRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TravellerService {
    private final TravellerRepository travellerRepository;
    private final TravelRepository travelRepository;
    private final ModelMapper modelMapper;

    public TravellerService(TravellerRepository travellerRepository, TravelRepository travelRepository, ModelMapper modelMapper) {
        this.travellerRepository = travellerRepository;
        this.travelRepository = travelRepository;
        this.modelMapper = modelMapper;
    }

    public TravellerInfo saveTraveller(TravellerCreateCommand command) {
        Traveller toSave = new Traveller();

        Travel travelForTraveller = travelRepository.findById(command.getTravelId());

        if (travelForTraveller == null) {
            throw new TravelNotFoundException(command.getTravelId());
        }

        toSave.setName(command.getName());
        toSave.setEmail(command.getEmail());
        toSave.setBirthday(command.getBirthday());
        toSave.setAge(ChronoUnit.YEARS.between(command.getBirthday(), LocalDate.now()));
        toSave.setTravel(travelForTraveller);
        toSave.setAllFees(travellerFeesSetter(toSave.getAge(), travelForTraveller.getWholePrice()));

        toSave.setTravel(travelForTraveller);

        travelForTraveller.getTravellers().add(toSave);


        Traveller saved = travellerRepository.save(toSave);
        return modelMapper.map(saved, TravellerInfo.class);
    }

    public TravellerInfo findTravellerById(Integer id) {
        Traveller travellerFound = travellerRepository.findById(id);
        if (travellerFound == null) {
            throw new TravellerNotFoundException(id);
        }
        return modelMapper.map(travellerFound, TravellerInfo.class);
    }

    public List<TravellerInfo> findAllTravellers() {
        List<Traveller> travellers = travellerRepository.findAll();
        return travellers.stream()
                .map(traveller -> modelMapper.map(traveller, TravellerInfo.class))
                .collect(Collectors.toList());
    }

    public TravellerInfo modifyTravellerNameAndEmail(Integer id, TravellerModifyCommand command) {
        Traveller toModify = travellerRepository.findById(id);
        if (toModify == null) {
            throw new TravellerNotFoundException(id);
        }

        toModify.setName(command.getName());
        toModify.setEmail(command.getEmail());

        return modelMapper.map(toModify, TravellerInfo.class);
    }

    public void deleteTraveller(int id) {
        Traveller travellerFound = travellerRepository.findById(id);
        if (travellerFound == null) {
            throw new TravellerNotFoundException(id);
        }

        travellerRepository.delete(travellerFound);
    }

    public double travellerFeesSetter(long age, double wholePrice) {
        double result = wholePrice;
        if (age <= 4) {
            result = 0;
        } else if (age <= 12) {
            result = wholePrice * 0.5;
        }

        return result;
    }
}
