package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProgramInfo {
    private Integer id;
    private String name;
    private String description;
    private String guide;
    private Integer price;
}
