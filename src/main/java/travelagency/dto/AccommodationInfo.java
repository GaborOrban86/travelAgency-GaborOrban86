package travelagency.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.AccommodationType;
import travelagency.domain.enums.Service;

@Data
@NoArgsConstructor
public class AccommodationInfo {
    private Integer id;
    private String name;
    private AccommodationType type;
    private Integer nights;
    private Service service;
    private double price;
}