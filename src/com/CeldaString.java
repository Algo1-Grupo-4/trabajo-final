package com;

public class CeldaString extends Celda {
  // Para una Celda String, usamos esta celda

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
    if (objeto instanceof String) {
      this.contenido = (String) objeto;
    } else if (objeto == null) {
      this.contenido = null;
    } else {
      this.contenido = objeto.toString(); // fuerza lo que haya a String
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
    return this.getContenido().compareTo(
        o.getContenido().toString());
  }

  @Override
  protected Object clone() {
    CeldaString cs = new CeldaString();
    cs.setContenido(this.getContenido());
    return cs;
  }

}
