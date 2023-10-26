package com;
import java.util.ArrayList;
import java.util.List;

public class Columna {
    private List<Celda> columna;

    public Columna(String tipoDato, int lengthColumna) {
        columna = new ArrayList<>();

        // Agregar las celdas según el tipo de dato
        if (tipoDato.equals("Boolean")) {
            for (int i = 0; i < lengthColumna; i++) {
                columna.add(new CeldaBoolean());
            }
        } else if (tipoDato.equals("String")) {
            for (int i = 0; i < lengthColumna; i++) {
                columna.add(new CeldaString());
            }
        } else if (tipoDato.equals("Number")) {
            for (int i = 0; i < lengthColumna; i++) {
                columna.add(new CeldaNumber());
            }
        } 
    }

    public Celda getCelda(int index) {
        if (index >= 0 && index < columna.size()) {
            return columna.get(index);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    public List<Celda> getCeldas() {
        return columna;
    }

    public int size(){
        return columna.size();
    }

        public boolean isColumna() {
        Class <? extends Celda> tipoCelda = ((Columna) columna).getCelda(0).getClass();
        for (Celda celda : columna){
            if (celda == null){
                continue;
            }
            if (!tipoCelda.isInstance(celda)){
                return false;
            }
        }
        return true;
    }
    
}
