package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.Destination;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public abstract class Traveller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String Name;
    private String email;
    private Accommodation accommodation;
    protected Integer yearOfBirth;
    protected Destination destination;
    protected List<Program> programs;
    private double allFees;

//    @ManyToOne
//    @JoinColumn(name = "travel_id")
    private Travel travel;
}
