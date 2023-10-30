package com;

import java.util.ArrayList;
import java.util.List;

public class Fila {
    private List<Celda> fila;

    public Fila(){
        this.fila = new ArrayList<Celda>();
    }

    public List<Celda> getFila() {
        return fila;
    }

    public void setFila(List<Celda> fila) {
        this.fila = fila;
    }

    public void add(Celda contenido) {
        fila.add(contenido);
    }

    public int size(){
        return fila.size();
    }

}
