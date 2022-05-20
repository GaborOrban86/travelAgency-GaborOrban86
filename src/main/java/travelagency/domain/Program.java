package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Program {
    private int id;
    private String name;
    private String city;
    private double price;
    private String guide;
    private List<Traveller> participants;
    private String description;
    private double minimumNumberOfParticipants;
    private double maximumNumberOfParticipants;
}
