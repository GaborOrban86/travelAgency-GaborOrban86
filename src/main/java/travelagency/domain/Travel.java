package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Travel {
    private int id;
    private String destination;
    private List<Accommodation> accommodations;
    private List<Traveller> travellers;
    private String description;
    private int minimumNumberOfTravellers;
    private double wholePrice;
}
