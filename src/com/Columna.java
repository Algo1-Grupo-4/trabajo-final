package com;
import java.util.ArrayList;
import java.util.List;

public class Columna<T>{
    private List<Celda<T>> elementos;

    public Columna(){
       elementos = new ArrayList<Celda<T>>();
    }

    public Columna(int length){
       elementos = new ArrayList<Celda<T>>(length);
    }

    // public Celda<T> getCelda(int index){
    //     if (index > 0 && index < elementos.size()){
    //     return elementos.get(index);
    //     } else {
    //         return Celda<T>; 
    //     }
    // }
    
    // public T getContenidoCelda(int index){
    //     return getCelda(index).getContenido();
    // }

    //ni se si vale la pena o si va aca 
    public void setContenido(List<Celda<T>> columna){
        this.elementos = columna;
    }
    
    public void setContenidoCelda(int index, T nuevo_contenido){
        elementos.get(index).setContenido(nuevo_contenido);
    }

    public void addContenido(T contenido){
        //ampliar lista
        //set contenido al final 

    }

    public void removeContenido(int index){
        if (index > 0 && index < elementos.size()){

        }
    }
}
