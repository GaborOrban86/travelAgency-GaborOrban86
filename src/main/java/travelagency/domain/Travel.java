package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Travel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Destination destination;

    @OneToOne
    private Accommodation accommodation;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY)
    private List<Traveller> travellers;

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY)
    private List<Program> programs;

    private Integer days;
    private Integer wholePrice;

    private boolean deleted;
}
