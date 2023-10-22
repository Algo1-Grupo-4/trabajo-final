package com;

public class CeldaString extends Celda {
  /*
   * Para una Celda String, usamos esta celda
   */

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
      this.contenido = (String) contenido;
    } else if (objeto == null){
      this.contenido = null;
    } else {
      this.contenido = (String) contenido; //fuerza lo que haya a String
    }
  }

  @Override
  public void removerContenido() {
    this.contenido = null;
  }

  @Override
  public boolean isNA() {
    return contenido == null;
  }

  @Override
  public void fillNA(Object objeto) {
    if (this.isNA()) {
      this.setContenido((String) objeto);
  }
  }
}
