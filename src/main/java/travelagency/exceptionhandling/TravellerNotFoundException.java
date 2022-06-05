package travelagency.exceptionhandling;

public class TravellerNotFoundException extends RuntimeException {

    private final int idNotFound;

    public TravellerNotFoundException(int idNotFound) {
        this.idNotFound = idNotFound;
    }

    public int getIdNotFound() {
        return idNotFound;
    }
}
