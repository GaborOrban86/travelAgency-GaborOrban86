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
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @OneToOne
    @JoinColumn(name = "accommodation_id")
    private Accommodation accommodation;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY)
    private List<Traveller> travellers;

    @OneToMany(mappedBy = "travel", fetch = FetchType.LAZY)
    private List<Program> programs;

    private Integer days;
    private Integer wholePrice;
    private Integer fullIncome;
    private boolean deleted;
}
