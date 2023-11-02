package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Tablas {

  /**
   * Lee un Archivo CSV
   * 
   * @param fileName Path al archivo csv.
   * @return Una lista de Strings con el contenido del CSV
   * @throws IOException Si no encuentra el archivo
   */
  public static List<String> leerCSV(String fileName) throws IOException {
    /**
     * Lee un archivo CSV
     * Devuelve List<String>
     */
    try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
      String linea;
      List<String> lineas = new LinkedList<>();
      while ((linea = br.readLine()) != null) {
        lineas.add(linea);
      }
      return lineas;
    } catch (IOException e) {
      throw new FileNotFoundException(fileName + " Not found!");
    }
  }

  /**
   * Parsea un csv.
   * Permite hacer la conversión List<String> -> String[][]
   * 
   * @param lineas       Una lista de strings
   * @param cantColumnas La Cantidad de Columnas
   * @param separador    El separador
   * @return una String[][] con los datos parseados
   */
  public static String[][] parserCSV(List<String> lineas, int cantColumnas, String separador) {
    int filas = lineas.size();
    String[][] datos = null;
    for (int i = 0; i < lineas.size(); i++) {
      String linea = lineas.get(i);
      String[] campos = linea.split(separador);
      if (datos == null) {
        datos = new String[filas][campos.length];
      }
      for (int j = 0; j < campos.length; j++) {
        if (campos[j] != "") {
          datos[i][j] = campos[j];
        }
      }
    }
    return datos;
  }

  /**
   * Exporta la tabla a un archivo csv
   * 
   * @param pathArchivo    donde se va a guardar el archivo (y con que nombre)
   * @param incluirHeaders ...eso
   * @param delimitador    caracter que separa los campos
   * 
   */
  public static void exportar(Tabla table, String pathArchivo, boolean incluirHeaders, String delimitador) {
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(pathArchivo));
      writer.write(table.toString(incluirHeaders, delimitador));
      writer.newLine();
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("estallo");
    }
  }

}
