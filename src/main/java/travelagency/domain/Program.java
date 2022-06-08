package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Program {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
    private String guide;
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    private boolean deleted;
}
