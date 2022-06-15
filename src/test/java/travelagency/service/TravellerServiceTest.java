package travelagency.service;

import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import travelagency.domain.Travel;
import travelagency.domain.Traveller;
import travelagency.dto.TravellerInfo;
import travelagency.dto.TravellerModifyCommand;
import travelagency.exceptionhandling.TravellerNotFoundException;
import travelagency.repository.TravellerRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SoftAssertionsExtension.class})
public class TravellerServiceTest {

    @Mock
    TravellerRepository travellerRepository;

    @Mock
    ModelMapper modelMapper;

    @InjectMocks
    TravellerService travellerService;

    private Traveller traveller;

    @BeforeEach
    void setUp() {
        traveller = new Traveller();
        traveller.setId(1);
        traveller.setName("Tim Travel");
        traveller.setEmail("tim@travel.hu");
        traveller.setBirthday(LocalDate.of(2010, Month.AUGUST, 1));
        traveller.setAge(22);
        traveller.setTravel(new Travel());
        traveller.setAllFees(20000);
        traveller.setDeleted(false);
    }

    @Test
    void testListFindAll_EmptyList() {
        when(travellerRepository.findAll()).thenReturn(List.of());
        assertThat(travellerService.findAllTravellers()).isEmpty();
    }

    @Test
    void testFindTravellerById_Success() {
        when(travellerRepository.findById(1)).thenReturn(traveller);
        TravellerInfo infoFromTraveller = modelMapper.map(traveller, TravellerInfo.class);
        TravellerInfo result = travellerService.findTravellerById(1);
        Assertions.assertEquals(infoFromTraveller, result);
    }

    @Test
    void testTravellerNotFound_Exception() {
        when(travellerRepository.findById(1)).thenReturn(null);
        assertThrows(TravellerNotFoundException.class, () ->
                travellerService.modifyTravellerNameAndEmail(1, new TravellerModifyCommand()));
    }

    @Test
    void testTravellerFeesSetter() {
        int result = travellerService.travellerFeesSetter(35, 20000);
        Assertions.assertEquals(20000, result);
    }
}
