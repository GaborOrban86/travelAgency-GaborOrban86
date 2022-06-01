package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String city;
    private String description;
    private double price;
    private String guide;

    @ManyToMany(mappedBy = "programs", fetch = FetchType.LAZY)
    private List<Traveller> participants;
    private double minimumNumberOfParticipants;
    private double maximumNumberOfParticipants;
}
