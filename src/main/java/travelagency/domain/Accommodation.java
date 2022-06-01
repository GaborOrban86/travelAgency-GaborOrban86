package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Nights;
import travelagency.domain.enums.Service;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Accommodation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private AccommodationType type;
    private Nights nights;
    private Service service;

    @OneToMany(mappedBy = "accommodation")
    private List<Traveller> guests;

    private double price;
}
