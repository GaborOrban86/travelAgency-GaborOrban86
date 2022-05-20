package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Accommodation {
    private int id;
    private String name;
    private String destination;
    private String type;
    private int price;
}
