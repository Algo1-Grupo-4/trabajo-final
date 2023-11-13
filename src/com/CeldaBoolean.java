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

    @Override
    public String toString() {
        if (this.contenido == null) {
            return "NA";
        } else {
            return this.contenido.toString();
        }
    }

    @Override
    public int compareTo(Celda o) {
        return this.getContenido().compareTo(
                (Boolean) o.getContenido());
    }

    @Override
    protected Object clone() {
        CeldaBoolean cb = new CeldaBoolean();
        cb.setContenido(this.getContenido());
        return cb;
    }

}
