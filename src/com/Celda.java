package com;


public abstract class Celda {
  /**
   * Clase abstracta para la Celda
   **/
  public abstract Object getContenido();

  public abstract void setContenido(Object objeto);

  public abstract void removeContenido();

  public abstract boolean isNA();
}
