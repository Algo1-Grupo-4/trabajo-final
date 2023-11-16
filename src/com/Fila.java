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

    public Celda getCelda(String etiquetaColumna, Tabla tabla) {
        Integer indiceColuma = tabla.getColLabels().get(etiquetaColumna);
        if (indiceColuma != null && indiceColuma < fila.size()) {
            return fila.get(indiceColuma);
        } else {
            throw new IllegalArgumentException("La columna '" + etiquetaColumna + "' no existe.");
        }
    }

    /**
     * Devuelve una lista de celdas en la fila.
     */
    public List<Celda> getCeldas() {
        return fila;
    }

    /**
     * Elimina una celda de la fila.
     */
    public void removeCelda(int index) {
        fila.remove(index);
    }

    /**
     * Agrega una celda a la fila.
     */
    public void addCelda(Celda valor) {
        fila.add(valor);
    }

    /**
     * Devuelve el tamaño de la fila.
     */
    public int size() {
        return fila.size();
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