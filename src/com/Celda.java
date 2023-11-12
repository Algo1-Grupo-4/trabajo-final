package com;

public abstract class Celda implements Comparable<Celda>, Cloneable {
  /**
   * Clase abstracta para la Celda
   **/
  public abstract Object getContenido();

  public abstract void setContenido(Object objeto);

  public abstract void removeContenido();

  public abstract boolean isNA();

  @Override
  protected Object clone() throws CloneNotSupportedException {
    // TODO Auto-generated method stub
    return super.clone();
  }

}
