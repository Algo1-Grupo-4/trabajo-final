package com;

import java.util.ArrayList;
import java.util.List;

public class Columna {
    private List<Celda> elementos;

    public Columna(String tipo_columna, int lenght) {
        elementos = new ArrayList<>(lenght);

        switch (tipo_columna){
            case "String":
                for (int i = 0; i < lenght; i++) {
                    elementos.set(i, new CeldaString())  ;
                }
                break;
            case "Number": 
                for (int i = 0; i < lenght; i++) {
                    elementos.set(i, new CeldaNumber());
                }
                break;
            case "Boolean":
                for (int i = 0; i < lenght; i++) {
                    elementos.set(i, new CeldaBoolean());
                }
                break;
            default:
                throw new IllegalArgumentException("Tipo no válido: " + tipo_columna);
        }
    } 

    public void addCelda(){
        
    }
}
