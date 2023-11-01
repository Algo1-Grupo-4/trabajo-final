package com;

import java.util.ArrayList;
import java.util.List;

public class Fila {
    private List<Celda> fila;

    public Fila() {
        fila = new ArrayList<Celda>();
    }

    public Celda getCelda(int index) {
        if (index >= 0 && index < fila.size()) {
            return fila.get(index);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * Devuelve una lista de celdas en la fila.
     */
    public List<Celda> getCeldas() {
        return fila;
    }

    /**
     * Elimina una celda de la columna.
     */
    public void removeCelda(int index) {
        fila.remove(index);
    }

    /**
     * Agrega una celda a la columna.
     */
    public void addCelda(Celda valor) {
        fila.add(valor);
    }

    /**
     * Devuelve el tamaño de la columna.
     */
    public int size() {
        return fila.size();
    }

    public List<Celda> getFila() {
        return fila;
    }

    @Override
    public String toString() {
        return "\nValores: " + fila + "\n";
    }

}