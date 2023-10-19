public class CeldaString extends Celda<String> {
  private String contenido;

  public CeldaString(){
    this.contenido = null;
  }

  @Override
  public void setContenido(String objeto) {
    this.contenido = objeto;
  }

  @Override
  public void removerContenido() {
    this.contenido = null;
  }

  @Override
  public String getContenido() {
    return this.contenido;
  }

  @Override
  public boolean isNA() {
    ////CON String Objeto
    //if (objeto != null && objeto.isEmpty()) {
      //return true;
   // }
    //return false;
    // lo saqué de
    // https://stackoverflow.com/questions/8970008/can-we-rely-on-string-isempty-for-checking-null-condition-on-a-string-in-java
  
  if (this.contenido == null){
    return true;
  } else {
    return false;
  }
  
  }

  @Override
  public void fillNA(String objeto) {
    if (this.contenido == null){
      this.contenido = objeto;
  } else {
      System.out.println("La celda ya tiene contenido");
  }
  }
}
