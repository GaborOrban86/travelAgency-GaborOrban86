package travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Travel;
import travelagency.domain.Traveller;
import travelagency.dto.TravellerCreateCommand;
import travelagency.dto.TravellerInfo;
import travelagency.dto.TravellerModifyCommand;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravelWithoutAccommodationAndProgramsException;
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

    @Value("${discount.baby}")
    private Float babyDiscount;

    @Value("${discount.child}")
    private Float childDiscount;

    public TravellerService(TravellerRepository travellerRepository, TravelRepository travelRepository, ModelMapper modelMapper) {
        this.travellerRepository = travellerRepository;
        this.travelRepository = travelRepository;
        this.modelMapper = modelMapper;
    }

    public TravellerInfo saveTraveller(TravellerCreateCommand command) {
        Traveller toSave = new Traveller();

        Travel travelForTraveller = travelRepository.findById(command.getTravelId());

        if (travelForTraveller == null || travelForTraveller.isDeleted()) {
            throw new TravelNotFoundException(command.getTravelId());
        }
        if (travelForTraveller.getAccommodation() == null || travelForTraveller.getPrograms().isEmpty()) {
            throw new TravelWithoutAccommodationAndProgramsException();
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
        if (travellerFound == null || travellerFound.isDeleted()) {
            throw new TravellerNotFoundException(id);
        }
        return modelMapper.map(travellerFound, TravellerInfo.class);
    }

    public List<TravellerInfo> findAllTravellers() {
        List<Traveller> travellers = travellerRepository.findAll();
        return travellers.stream()
                .filter(traveller -> !traveller.isDeleted())
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
        if (travellerFound == null || travellerFound.isDeleted()) {
            throw new TravellerNotFoundException(id);
        }
        Travel travelOfTraveller = travellerFound.getTravel();
        travelOfTraveller.getTravellers().remove(travellerFound);
        travellerFound.setTravel(null);
        travellerFound.setAllFees(0);
        travellerRepository.delete(travellerFound);
    }


    public int travellerFeesSetter(long age, int wholePrice) {
        double result = wholePrice;
        if (age <= 4) {
            result = wholePrice - (wholePrice * babyDiscount);
        } else if (age <= 12) {
            result = wholePrice - (wholePrice * childDiscount);
        }

        return (int) result;
    }
}
