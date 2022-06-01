package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Traveller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    private String Name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;


    private Integer yearOfBirth;
    private Destination destination;

    @ManyToMany(mappedBy = "participants", fetch = FetchType.LAZY)
    private List<Program> programs;
    private double allFees;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;
}
