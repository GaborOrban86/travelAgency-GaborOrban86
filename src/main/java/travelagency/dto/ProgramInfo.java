package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProgramInfo {
    private Integer id;
    private String name;
    private String description;
    private String guide;
    private Integer travelId;
    private Integer price;
}
