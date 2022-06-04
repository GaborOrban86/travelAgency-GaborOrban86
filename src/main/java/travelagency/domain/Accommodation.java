package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Service;

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
    private Service service;

    @OneToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private double price;
}
