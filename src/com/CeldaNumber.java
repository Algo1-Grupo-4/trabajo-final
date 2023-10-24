package com;

public class CeldaNumber extends Celda {
    private Number contenido;

    public CeldaNumber() {
        this.contenido = null;
    }

    @Override
    public Number getContenido() {
        return this.contenido;
    }

    @Override
    public void setContenido(Object objeto) {
        this.contenido = (Number) objeto;
    }

    @Override
    public void removerContenido() {
        this.contenido = null;
    }

    @Override
    public boolean isNA() {
        if (this.contenido == null) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public void fillNA(Object objeto) {
        if (this.contenido == null) {
            this.contenido = (Number) objeto;
        }
    }
}
