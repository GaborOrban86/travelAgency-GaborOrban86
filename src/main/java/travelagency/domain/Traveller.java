package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Traveller {
    private int id;
    private String Name;
    private String email;
    private String cityOfAddress;
    private String streetOfAddress;
    private int numberOfAddress;
    private String postCodeOfAddress;
    private String typeOfTravel;
    private int Nights;
    private String catering;
    private List<Program> programs;
    private double wholePriceToPay;
}
