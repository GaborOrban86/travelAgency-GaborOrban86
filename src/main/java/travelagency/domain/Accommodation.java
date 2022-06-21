package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationCatering;
import travelagency.domain.enums.AccommodationType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Accommodation {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private AccommodationType type;

    @Enumerated(value = EnumType.STRING)
    private AccommodationCatering catering;

    @OneToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private Integer price;
    private boolean deleted;
}
