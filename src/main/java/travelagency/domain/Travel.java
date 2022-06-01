package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Destination destination;

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY)
    private List<Traveller> travellers;
    private double wholePrice;
}
