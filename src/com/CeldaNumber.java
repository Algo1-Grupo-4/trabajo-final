package com;

public class CeldaNumber extends Celda {
    public static Object convertToPrimitive(Number number) {
        if (number instanceof Double || number instanceof Float) {
            return number.doubleValue();
        } else if (number instanceof Long || number instanceof Integer || number instanceof Short
                || number instanceof Byte) {
            return number.longValue();
        } else {
            // Handle other cases or throw an exception
            throw new IllegalArgumentException("Unsupported Number type: " + number.getClass());
        }
    }

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
            if (objeto instanceof Integer) {
                this.contenido = (Integer) objeto;
            } else if (objeto instanceof Double) {
                this.contenido = (Double) objeto;
            } else if (objeto instanceof Float) {
                this.contenido = (Float) objeto;
            }
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
        CeldaNumber c = (CeldaNumber) o;
        return ((Double) this.contenido.doubleValue()).compareTo(c.getContenido().doubleValue());
    }

    @Override
    protected Object clone() {
        CeldaNumber cn = new CeldaNumber();
        cn.setContenido(this.getContenido());
        return cn;
    }

}
