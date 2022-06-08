package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Catering;

@Data
@NoArgsConstructor
public class AccommodationInfo {
    private Integer id;
    private String name;
    private AccommodationType type;
    private Catering catering;
    private Integer travelId;
    private Integer price;
}
