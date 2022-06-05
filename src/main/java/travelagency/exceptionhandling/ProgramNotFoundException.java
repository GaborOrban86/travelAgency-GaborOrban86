package travelagency.exceptionhandling;

public class ProgramNotFoundException extends RuntimeException {

    private final int idNotFound;

    public ProgramNotFoundException(int idNotFound) {
        this.idNotFound = idNotFound;
    }

    public int getIdNotFound() {
        return idNotFound;
    }
}
