package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class Traveller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String Name;
    private String email;

    private LocalDate birthday;
    private long age;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private Integer allFees;

    private boolean deleted;


}
