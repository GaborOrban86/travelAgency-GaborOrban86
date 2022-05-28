package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.Accommodation;
import travelagency.domain.OtherTraveller;
import travelagency.domain.Program;
import travelagency.domain.Traveller;
import travelagency.domain.enums.Destination;

import java.util.List;

@Data
@NoArgsConstructor
public class MainTravellerInfo {
    private Integer id;
    private String Name;
    private Integer yearOfBirth;
    private Destination destination;
    private List<ProgramInfo> programs;
    private String email;
    private Accommodation accommodation;
    private List<OtherTravellerInfo> otherTravellers;
    private double allFees;
}
