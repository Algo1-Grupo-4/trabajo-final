package com;

public abstract class Celda<T> {
  private T contenido;

  public abstract T getContenido();

  public abstract void setContenido(T objeto);

  public abstract void removerContenido();

  public abstract boolean isNA();

  public abstract void fillNA(T objeto);

}
