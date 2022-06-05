package travelagency.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Accommodation;
import travelagency.domain.Destination;
import travelagency.domain.Program;
import travelagency.domain.Travel;
import travelagency.dto.*;
import travelagency.exceptionhandling.AccommodationNotFoundException;
import travelagency.exceptionhandling.DestinationNotFoundException;
import travelagency.exceptionhandling.ProgramNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.repository.AccommodationRepository;
import travelagency.repository.DestinationRepository;
import travelagency.repository.ProgramRepository;
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

    public ProgramInfo saveProgram(ProgramCreateCommand command) {
        Program toSave = new Program();

        Travel travelForProgram = travelRepository.findById(command.getTravelId());

        if (travelForProgram == null) {
            throw new TravelNotFoundException(command.getTravelId());
        }

        toSave.setName(command.getName());
        toSave.setDescription(command.getDescription());
        toSave.setPrice(command.getPrice());
        toSave.setGuide(command.getGuide());


        travelForProgram.setWholePrice(travelForProgram.getWholePrice() + toSave.getPrice());

        toSave.setTravel(travelForProgram);
        travelForProgram.getPrograms().add(toSave);


        Program saved = programRepository.save(toSave);
        return modelMapper.map(saved, ProgramInfo.class);
    }

    public ProgramInfo findProgramById(Integer id) {
        Program programFound = programRepository.findById(id);
        if (programFound == null) {
            throw new ProgramNotFoundException(id);
        }
        return modelMapper.map(programFound, ProgramInfo.class);
    }

    public List<ProgramInfo> findAllPrograms() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .map(program -> modelMapper.map(program, ProgramInfo.class))
                .collect(Collectors.toList());
    }

    public ProgramInfo modifyProgram(Integer id, ProgramModifyCommand command) {
        Program toModify = programRepository.findById(id);
        if (toModify == null) {
            throw new ProgramNotFoundException(id);
        }
        Travel travelOfProgram = toModify.getTravel();
        travelOfProgram.setWholePrice(
                (travelOfProgram.getWholePrice() - toModify.getPrice() + command.getPrice()));

        toModify.setName(command.getName());
        toModify.setDescription(command.getDescription());
        toModify.setPrice(command.getPrice());
        toModify.setGuide(command.getGuide());

        return modelMapper.map(toModify, ProgramInfo.class);
    }

    public void deleteProgram(int id) {
        Program programFound = programRepository.findById(id);
        if (programFound == null) {
            throw new ProgramNotFoundException(id);
        }

        programRepository.delete(programFound);
    }
}
