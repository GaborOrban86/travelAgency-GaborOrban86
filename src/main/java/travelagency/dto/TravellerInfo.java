package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TravellerInfo {
    private Integer id;
    private String Name;
    private String email;
    private String accommodationNameAndType;
    private Integer yearOfBirth;
    private String travelCity;
    private double allFees;
}
