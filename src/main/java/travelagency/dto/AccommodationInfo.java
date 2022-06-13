package travelagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationCatering;
import travelagency.domain.enums.AccommodationType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationInfo {
    private Integer id;
    private String name;
    private AccommodationType type;
    private AccommodationCatering catering;
    private Integer travelId;
    private Integer price;
}
