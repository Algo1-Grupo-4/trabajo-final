package com;

public class CeldaNumber extends Celda<Number> {
    private Number contenido;

    @Override
    public Number getContenido() {
        return this.contenido;
    }

    @Override
    public void setContenido(Number objeto) {
        this.contenido = objeto;
    }


    @Override
    public void removerContenido() {
        this.contenido = null;
    }

    @Override
    public boolean isNA() {
        if (this.contenido == null){
            return true;
          } else {
            return false;
          }
    }

    @Override
    public void fillNA(Number objeto) {
        if (this.contenido == null){
            this.contenido = objeto;
        } else {
            System.out.println("La celda ya tiene contenido");
        }
    }
    
}
