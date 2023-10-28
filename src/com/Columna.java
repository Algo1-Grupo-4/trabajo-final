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

    // @Override
    // TODO: THIS OVERRIDE
    // public String toString() {
    // return "Columna [columna=" + columna + "]";
    // }

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

    /**
     * Removes a cell from the column
     * 
     * @param index
     */
    public void removeCelda(int index) {
        columna.remove(index);
    }

    /**
     * Returns the size of the column
     * 
     * @return a int with the size of the column
     */
    public int size() {
        return columna.size();
    }

    /**
     * No se que hace isColumna
     * 
     * @return
     */
    public boolean isColumna() {
        Class<? extends Celda> tipoCelda = ((Columna) columna).getCelda(0).getClass();
        for (Celda celda : columna) {
            if (celda == null) {
                continue;
            }
            if (!tipoCelda.isInstance(celda)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Adds a celda to the column
     * 
     * @param valor the Cell value to be added to the column
     */
    public void addCelda(Celda valor) {
        columna.add(valor);
    }

}
