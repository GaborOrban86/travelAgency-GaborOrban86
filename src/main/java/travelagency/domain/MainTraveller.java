package travelagency.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class MainTraveller extends Traveller{
    private String email;
    private Accommodation accommodation;
    private List<OtherTraveller> otherTravellers;
    private double allFees;
}
