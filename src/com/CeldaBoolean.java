package com;

public class CeldaBoolean extends Celda<Boolean> {
    private Boolean contenido;

    @Override
    public Boolean getContenido() {
        return this.contenido;
    }

    @Override
    public void setContenido(Boolean cont) {
        this.contenido = cont;
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
    public void fillNA(Boolean objeto) {
        if (this.contenido == null){
            this.contenido = objeto;
        } else {
            System.out.println("La celda ya tiene contenido");
        }
    }
}
