package travelagency.exceptionhandling;

public class AccommodationNotFoundException extends RuntimeException {

    private final int idNotFound;

    public AccommodationNotFoundException(int idNotFound) {
        this.idNotFound = idNotFound;
    }

    public int getIdNotFound() {
        return idNotFound;
    }
}
