package travelagency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class TravelInfo {
    private Integer id;
    private DestinationInfo destination;
    private AccommodationInfo accommodation;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<TravellerInfo> travellers;
    private List<ProgramInfo> programs;
    private Integer days;
    private Integer wholePrice;
}
