package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccommodationInfo {
    private Integer id;
    private String name;
    private String type;
    private String catering;
    private Integer travelId;
    private Integer price;
}
