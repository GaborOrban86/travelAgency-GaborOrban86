package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Catering;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private AccommodationType type;
    private Catering catering;

    @OneToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private Integer price;
}
