package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProgramInfo {
    private Integer id;
    private String name;
    private String city;
    private String description;
    private double price;
    private String guide;
    private List<String> participantNames;
    private double minimumNumberOfParticipants;
    private double maximumNumberOfParticipants;
}
