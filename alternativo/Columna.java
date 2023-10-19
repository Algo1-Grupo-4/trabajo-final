
public class Columna {
    private List<Celda> elementos;

    public Columna(){
        elementos = new List<Celda>();
    }

    public Columna(int lenght){
        elementos = new List<Celda>(lenght);
    }

    public Celda getCelda(int index){
        if (index > 0 && index < elementos.size()){
        return elementos.get(index);
        } else {
            return new Celda(); 
        }
    }
    
    // hay que castearlo en la aplicacion
    public Object getContenidoCelda(int index){
        return getCelda(index).getContenido();
    }

}
