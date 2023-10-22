import java.util.HashMap;

public class Tabla {
    private List<Columna> elementos;
    // protected HashMap(String) mapa1;
    // protected HashMap(Number) mapa2;
    
    public Lista getFila(int index){

    }

    //para los hash
    public Lista getFila(){

    }

    public Columna getColumna(int index){

    }

    // para los hash
    public Columna getColumna(){

    }

    public Celda getCelda(){

    }

    public Object getContenidoCelda(){

    }

    public void setColumna(){

    }

    public void setCelda(){

    }

    public void addColumna(Columna newColumna){

    }

    public void addFila(List newFila){

    }

    public void removeColumna(){
        // uno con index y otro con el Map
    }

    public void removeFila(){
        // uno por index y otros por map
    }

    public boolean checkType(Columna columna){
        for (i = 0; i < columna.size() - 1; i++){ // esta mal igual 
            if (columna.get(i).getClass().equals(columna.get(i+1).getClass())){
                return false;
            }
        } 
        return True;
    }
}
