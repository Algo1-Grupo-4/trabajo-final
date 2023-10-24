package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tabla {
    private List<Columna> elementos;

    public Tabla(String[] tipo_columna, int cant_filas){
        elementos = new ArrayList<>(tipo_columna.length);

        for (int i = 0; i < tipo_columna.length; i++) {
            elementos.set(i, new Columna(tipo_columna[i], cant_filas));
        // parsea archivo.
        }
   
    }

    public Tabla(){
        //
    }

    public void addFila(List<Celda> fila) {
        //recorre columnas, agrega una celda en cada columna y lo rellena con la fila que se le pasa
    }

    public void addColumna(Columna columna) {
        // agrega columna
    }
}
