package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.Traveller;
import travelagency.domain.enums.Destination;

import java.util.List;

@Data
@NoArgsConstructor
public class ProgramInfo {
    private Integer id;
    private String name;
    private Destination city;
    private String description;
    private double price;
    private String guide;
    private List<TravellerInfo> participants;
    private double minimumNumberOfParticipants;
    private double maximumNumberOfParticipants;
}
