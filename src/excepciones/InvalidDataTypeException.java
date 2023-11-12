package excepciones;

public class InvalidDataTypeException extends RuntimeException {
  public InvalidDataTypeException(String errorMessage) {
    super(errorMessage);
  }

}
