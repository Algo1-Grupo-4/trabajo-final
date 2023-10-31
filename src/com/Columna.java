package com;
import java.util.ArrayList;
import java.util.List;

public class Columna {
    private List<Celda> columna;
    private String tipo;

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
        this.tipo = tipoDato;
    }

    public Celda getCelda(int index) {
        if (index >= 0 && index < columna.size()) {
            return columna.get(index);
        } else {
            throw new IndexOutOfBoundsException(String.valueOf(index));
        }
    }

    /**
     * Devuelve una lista de celdas en la columna.
    */
    public List<Celda> getCeldas() {
        return columna;
    }

    /**
     * Elimina una celda de la columna.
    */
    public void removeCelda(int index) {
        columna.remove(index);
    }

    /**
     * Agrega una celda a la columna.
    */
    public void addCelda(Celda valor) {
        columna.add(valor);
    }

    /**
     * Devuelve el tamaño de la columna.
    */
    public int size() {
        return columna.size();
    }

    /**
     * Comprueba si todas las celdas de la columna son del mismo tipo.
    */
    public boolean sonMismosTipos() {
        Class<? extends Celda> tipoCelda = getCelda(0).getClass();
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
     * LLena los valores faltantes de una columna con NA.
    */
    public void fillNA() {
        for (Celda celda : columna) {
            if (celda.isNA()) {
                celda.setContenido("NA");
            }
        }
    }

    @Override
    public String toString() {
        return "Tipo de dato " + tipo + "\nValores: " + columna + "\n";
    }
}
