package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TravellerInfo {
    private Integer id;
    private String Name;
    private String email;
    private LocalDate birthday;
    private Integer travelId;
    private Integer allFees;
}
