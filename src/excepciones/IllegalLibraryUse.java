package excepciones;

public class IllegalLibraryUse extends RuntimeException {
    public IllegalLibraryUse(){
        super();
    }

    public IllegalLibraryUse(String error_message){
        super(error_message);
    }
}
