package com;

public class CeldaString extends Celda {
   //Para una Celda String, usamos esta celda

  private String contenido;

  public CeldaString() {
    this.contenido = null;
  }

  @Override
  public String getContenido() {
    return this.contenido;
  }

  @Override
  public void setContenido(Object objeto) {
    if (objeto instanceof String){
      this.contenido = (String) objeto;
    } else if (objeto == null){
      this.contenido = null;
    } else {
      this.contenido = objeto.toString() ; //fuerza lo que haya a String
    }
  }

  @Override
  public void removeContenido() {
    this.contenido = null;
  }

  @Override
  public boolean isNA() {
    return contenido == null;
  }

  /*@Override
  public void fillNA(Object objeto) {
    if (this.isNA()) {
      this.setContenido(objeto.toString());
    }
  }*/
}
