package excepciones;

public class IllegalConstructorException extends RuntimeException{
    public IllegalConstructorException(String error_message){
        super(error_message);
    }
}
