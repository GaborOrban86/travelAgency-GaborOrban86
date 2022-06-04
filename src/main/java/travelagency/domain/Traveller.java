package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Traveller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Name;
    private String email;

    @OneToOne
    private Accommodation accommodation;

    private Integer yearOfBirth;

    private double allFees;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

}
