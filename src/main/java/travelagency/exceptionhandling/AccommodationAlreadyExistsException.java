package travelagency.exceptionhandling;

public class AccommodationAlreadyExistsException extends RuntimeException {

    private final int idFound;

    public AccommodationAlreadyExistsException(int idNotFound) {
        this.idFound = idNotFound;
    }

    public int getIdFound() {
        return idFound;
    }
}
