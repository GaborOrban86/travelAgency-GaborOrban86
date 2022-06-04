package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class TravelInfo {
    private Integer id;
    private DestinationInfo destination;
    private AccommodationInfo accommodation;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<TravellerInfo> travellers;
    private List<ProgramInfo> programs;
    private Integer days;
    private double wholePrice;
}
