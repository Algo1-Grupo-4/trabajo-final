package com;

public abstract class Celda<T> {
  private T contenido;

  public abstract T getContenido();

  public abstract void setContenido(T objeto);

  public abstract void addContenido(T objeto);

  public abstract void removerContenido(T objecto);

  public abstract boolean isNA(T objeto);

  public abstract T fillNA(T objeto);

}
