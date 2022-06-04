package travelagency.exceptionhandling;

public class DestinationNotFoundException extends RuntimeException{
    private final String destinationNotFound;

    public DestinationNotFoundException(String destinationNotFound) {
        this.destinationNotFound = destinationNotFound;
    }

    public String getDestinationNotFound() {
        return destinationNotFound;
    }
}
