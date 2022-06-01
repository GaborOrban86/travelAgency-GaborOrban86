package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DestinationInfo {
    private Integer id;
    private String name;
    private Integer price;
}
