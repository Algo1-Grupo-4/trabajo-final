package com;

public class CeldaBoolean extends Celda {
    /*
     * Para una Celda Booleana, usamos esta celda
     */
    private Boolean contenido;

    public CeldaBoolean() {
        this.contenido = null;
    }

    @Override
    public Boolean getContenido() {
        return this.contenido;
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
    public void setContenido(Object objeto) {
        if (objeto instanceof Boolean) {
            this.contenido = (Boolean) objeto;
        } else if (objeto == null) {
            throw new IllegalArgumentException("I need a value.");
        } // no estoy seguro si es necesario esto (Object == null?)
    }

    @Override
    public void fillNA(Object objeto) {
        if (this.isNA()) {
            this.setContenido(objeto);
        }
    }
}
