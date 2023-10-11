package com;

public class CeldaString extends Celda<String> {
  private String contenido;

  @Override
  public void setContenido(String objeto) {
    this.contenido = objeto;
  }

  @Override
  public void addContenido(String objeto) {
    // TODO
  }

  @Override
  public void removerContenido(String objecto) {
    // TODO
  }

  @Override
  public String getContenido() {
    return this.contenido;
  }

  @Override
  public boolean isNA(String objeto) {
    if (objeto != null && objeto.isEmpty()) {
      return true;
    }
    return false;
    // lo saqué de
    // https://stackoverflow.com/questions/8970008/can-we-rely-on-string-isempty-for-checking-null-condition-on-a-string-in-java
  }

  @Override
  public String fillNA(String objeto) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fillNA'");
  }
}
