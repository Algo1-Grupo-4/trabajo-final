package excepciones;

public class MismatchedDataException extends RuntimeException {

    public MismatchedDataException(){
        super();
    }

    public MismatchedDataException(String error_message){
        super(error_message);
    }

}
