package com;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Fila {
    private List<Celda> fila;

    public Fila() {
        this.fila = new ArrayList<Celda>();
    }

    public Fila(Object[] valores){
        fila = new ArrayList<>();
        for (Object valor : valores){
            if (valor instanceof Boolean) {
                Celda celda = new CeldaBoolean();
                celda.setContenido(valor);
                fila.add(celda);
            } else if (valor instanceof String) {
                Celda celda = new CeldaString();
                celda.setContenido(valor);
                fila.add(celda);
            } else if (valor instanceof Number) {
                Celda celda = new CeldaNumber();
                celda.setContenido(valor);
                fila.add(celda);
            } else {
                throw new IllegalArgumentException("Tipo de valor no admitido: " + valor.getClass().getName());
            } 
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fila otraFila = (Fila) o;
        return Objects.equals(fila, otraFila.fila);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fila);
    }

}