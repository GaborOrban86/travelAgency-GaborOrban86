package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TravellerInfo {
    private Integer id;
    private String Name;
    private Integer yearOfBirth;
    private Destination destination;
    private List<String> programNames;
    private String email;
    private String accommodationName;
    private double allFees;
}
