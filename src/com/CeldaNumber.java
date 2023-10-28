package com;

public class CeldaNumber extends Celda {
    // Para una Celda Number, usamos esta celda
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
        if (objeto instanceof Number) {
            this.contenido = (Number) objeto;
        }
    }

    @Override
    public void removeContenido() {
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

    /*
     * @Override
     * public void fillNA(Object objeto) {
     * if (this.contenido == null) {
     * setContenido(objeto);
     * } else {
     * //lanzar excepcion;
     * }
     * }
     */

}
