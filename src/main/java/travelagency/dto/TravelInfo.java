package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TravelInfo {
    private Integer id;
    private Destination destination;
    private List<TravellerInfo> travellers;
    private double wholePrice;
}
