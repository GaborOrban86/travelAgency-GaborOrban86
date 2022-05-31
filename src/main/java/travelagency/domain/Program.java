package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.Destination;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

@Data
@NoArgsConstructor
//@Entity
public class Program {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Destination city;
    private String description;
    private double price;
    private String guide;
    private List<Traveller> participants;
    private double minimumNumberOfParticipants;
    private double maximumNumberOfParticipants;
}
