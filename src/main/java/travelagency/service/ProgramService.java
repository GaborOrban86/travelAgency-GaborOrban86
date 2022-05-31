package travelagency.service;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import travelagency.repository.ProgramRepository;

@Service
@Transactional
public class ProgramService {

    private final ProgramRepository programRepository;
    private final ModelMapper modelMapper;

    public ProgramService(ProgramRepository programRepository, ModelMapper modelMapper) {
        this.programRepository = programRepository;
        this.modelMapper = modelMapper;
    }
}
