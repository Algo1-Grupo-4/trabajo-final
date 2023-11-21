package com;

public class Tablas {

  /**
   * Guarda la Tabla en un CSV
   * 
   * @param table          Tabla a exportar en csv
   * @param pathArchivo    Path del archivo. Debe incluir el nombre
   * @param incluirHeaders Si hay que incluir headers en el csv
   * @param delimitador    Delimiter del CSV. Si no es especificado, se utilizará
   *                       ","
   */
  public static void toCSV(Tabla table, String pathArchivo, boolean incluirHeaders, String delimitador) {
    CSVUtils.exportar(table, pathArchivo, incluirHeaders, delimitador);
  }

  public static Tabla fromCSV(String[] tiposDato, String fileName, boolean hasHeaders, String separador) {
    if (hasHeaders) {
      return new Tabla(tiposDato, fileName, true, separador);
    }
    return new Tabla(tiposDato, fileName, false, separador);
  }

  /**
   * Genera una tabla dado un CSV
   * 
   * @param tiposDato  Array con los tipos de datos de la columna
   * @param fileName   Path al archivo CSV
   * @param hasHeaders Si el csv contiene headers en la fila 1 o no. Si es false,
   *                   se crearán headers con números secuenciales
   * @return Tabla con los valores del archivo
   */
  public static Tabla fromCSV(String[] tiposDato, String fileName, boolean hasHeaders) {
    if (hasHeaders) {
      return fromCSV(tiposDato, fileName, hasHeaders, ",");
    }
    return fromCSV(tiposDato, fileName, false, ",");
  }

  /**
   * Genera una tabla desde una Lista de Lista de Strings.
   * Digamos, una Matriz de Strings.
   * 
   * @param tiposDato  String[][] indicando los tipos de dato
   * @param datos      Array de Array de Strings con los datos para cargar en
   *                   tabla
   * @param hasHeaders boolean de si datos tiene headers o no.
   */
  public static Tabla fromMatriz(String[] tiposDato, String[][] datos, boolean hasHeaders, String delimiter) {
    return new Tabla(tiposDato, datos, hasHeaders, delimiter);
  }

}
