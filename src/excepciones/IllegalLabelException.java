package excepciones;

public class IllegalLabelException extends RuntimeException{
    public IllegalLabelException(String error_message){
        super(error_message);
    }
}
