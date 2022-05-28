package travelagency.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import travelagency.domain.enums.Destination;

import java.time.LocalDateTime;
import java.util.List;

public abstract class Traveller {
    protected Integer id;
    protected String Name;
    protected Integer yearOfBirth;
    protected Destination destination;
    protected List<Program> programs;
}
