package travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.domain.Program;
import travelagency.domain.Travel;
import travelagency.dto.ProgramCreateCommand;
import travelagency.dto.ProgramInfo;
import travelagency.dto.ProgramModifyCommand;
import travelagency.exceptionhandling.ProgramNotFoundException;
import travelagency.exceptionhandling.TravelNotFoundException;
import travelagency.exceptionhandling.TravelWithTravellersException;
import travelagency.repository.ProgramRepository;
import travelagency.repository.TravelRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProgramService {

    ProgramRepository programRepository;
    TravelRepository travelRepository;
    ModelMapper modelMapper;

    public ProgramService(ProgramRepository programRepository, TravelRepository travelRepository, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.travelRepository = travelRepository;
        this.modelMapper = modelMapper;
    }

    public ProgramInfo saveProgram(ProgramCreateCommand command) {
        Program toSave = new Program();

        Travel travelForProgram = travelRepository.findById(command.getTravelId());

        if (travelForProgram == null) {
            throw new TravelNotFoundException(command.getTravelId());
        }

        if (!travelForProgram.getTravellers().isEmpty()) {
            throw new TravelWithTravellersException();
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
        if (programFound == null || programFound.isDeleted()) {
            throw new ProgramNotFoundException(id);
        }
        return modelMapper.map(programFound, ProgramInfo.class);
    }

    public List<ProgramInfo> findAllPrograms() {
        List<Program> programs = programRepository.findAll();
        return programs.stream()
                .filter(program -> !program.isDeleted())
                .map(program -> modelMapper.map(program, ProgramInfo.class))
                .collect(Collectors.toList());
    }

    public ProgramInfo modifyProgram(Integer id, ProgramModifyCommand command) {
        Program toModify = programRepository.findById(id);
        if (toModify == null || toModify.isDeleted()) {
            throw new ProgramNotFoundException(id);
        }
        Travel travelOfProgram = toModify.getTravel();

        if (!travelOfProgram.getTravellers().isEmpty()) {
            throw new TravelWithTravellersException();
        }

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
        if (programFound == null || programFound.isDeleted()) {
            throw new ProgramNotFoundException(id);
        }

        Travel travelOfProgram = programFound.getTravel();

        if (!travelOfProgram.getTravellers().isEmpty()) {
            throw new TravelWithTravellersException();
        }

        travelOfProgram.setWholePrice(
                (travelOfProgram.getWholePrice() - programFound.getPrice()));
        travelOfProgram.getPrograms().remove(programFound);
        programFound.setTravel(null);
        programRepository.delete(programFound);
    }
}
