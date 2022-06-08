package travelagency.exceptionhandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DestinationNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleDestinationNotFoundException(DestinationNotFoundException exception) {
        ValidationError validationError = new ValidationError("destinationId",
                "no destination found with id:" + exception.getDestinationNotFound());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TravelNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleTravelNotFoundException(TravelNotFoundException exception) {
        ValidationError validationError = new ValidationError("travelId",
                "no travel found with id:" + exception.getIdNotFound());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccommodationNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleAccommodationNotFoundException(AccommodationNotFoundException exception) {
        ValidationError validationError = new ValidationError("accommodationId",
                "no accommodation found with id:" + exception.getIdNotFound());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccommodationAlreadyExistsException.class)
    public ResponseEntity<List<ValidationError>> handleAccommodationAlreadyExistsException(AccommodationAlreadyExistsException exception) {
        ValidationError validationError = new ValidationError("accommodationId",
                "accommodation found with id" + exception.getIdFound());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProgramNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleProgramNotFoundException(ProgramNotFoundException exception) {
        ValidationError validationError = new ValidationError("programId",
                "no program found with id:" + exception.getIdNotFound());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TravellerNotFoundException.class)
    public ResponseEntity<List<ValidationError>> handleTravellerNotFoundException(TravellerNotFoundException exception) {
        ValidationError validationError = new ValidationError("travellerId",
                "no traveller found with id:" + exception.getIdNotFound());
        return new ResponseEntity<>(List.of(validationError), HttpStatus.BAD_REQUEST);
    }

}
