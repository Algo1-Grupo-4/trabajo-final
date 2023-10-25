package com;

public abstract class Celda {
  /**
   * Clase abstracta para la Celda
   **/
  public abstract Object getContenido();

  public abstract void setContenido(Object objeto);

  public abstract void removerContenido();

  public abstract boolean isNA();

  //public abstract void fillNA(Object objeto);
}
