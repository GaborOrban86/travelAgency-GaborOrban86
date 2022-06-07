package travelagency.exceptionhandling;

public class DestinationNotFoundException extends RuntimeException{
    private final Integer destinationNotFound;

    public DestinationNotFoundException(Integer destinationNotFound) {
        this.destinationNotFound = destinationNotFound;
    }

    public Integer getDestinationNotFound() {
        return destinationNotFound;
    }
}
