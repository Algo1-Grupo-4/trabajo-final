package excepciones;

public class IncorrectHeaderCountException extends Exception {
  public IncorrectHeaderCountException(String errorMessage) {
    super(errorMessage);
  }
}
