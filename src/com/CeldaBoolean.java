package com;

public class CeldaBoolean extends Celda {
    // Para una Celda Booleana, usamos esta celda

    private Boolean contenido;

    public CeldaBoolean() {
        this.contenido = null;
    }

    @Override
    public Boolean getContenido() {
        return this.contenido;
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

    @Override
    public void setContenido(Object objeto) {
        if (objeto instanceof Boolean) {
            this.contenido = (boolean) objeto;
        }
    }

    /*
     * @Override
     * public void fillNA(Object objeto) {
     * if (this.isNA()) {
     * this.setContenido(objeto.toString());
     * }
     * }
     */
}
