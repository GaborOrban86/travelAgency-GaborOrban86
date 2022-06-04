package travelagency.exceptionhandling;

public class TravelNotFoundException extends RuntimeException {

    private final int idNotFound;

    public TravelNotFoundException(int idNotFound) {
        this.idNotFound = idNotFound;
    }

    public int getIdNotFound() {
        return idNotFound;
    }
}
