package com;

import excepciones.*;
import java.util.ArrayList;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class TablaUtils {

  protected static void doBasic(Tabla t) {
    List<String> tipoDato = new ArrayList<>();
    for (String encabezado : t._dameHeaders()) {
      tipoDato.add(t.getColumna(encabezado).getCelda(0).getContenido().getClass().getSimpleName());
    }
    String[] tipoDatoDetectado = tipoDato.toArray(new String[0]);
    List<String> cantidadNonNull = new ArrayList<>();
    for (String encabezado : t._dameHeaders()) {
      Columna col = t.getColumna(encabezado);
      int celdasNoNulas = 0;
      for (Celda celda : col.getCeldas()) {
        if (!celda.isNA()) {
          celdasNoNulas++;
        }
      }
      cantidadNonNull.add(String.valueOf(celdasNoNulas));
    }
    String[] encabezados = { "Nombre", "NonNull", "TipoDato" };
    String[] tipoDeDatoHeaders = { "String", "String", "String" };
    String[] nomCol = t._dameHeaders().toArray(new String[0]);
    String[] noNulo = cantidadNonNull.toArray(new String[0]);

    List<String[]> data_fila = new ArrayList<>();
    data_fila.add(encabezados);

    for (int i = 0; i < encabezados.length; i++) {
      String[] row = { nomCol[i], noNulo[i], tipoDatoDetectado[i] };
      data_fila.add(row);
    }

    String[][] datos = new String[data_fila.size()][data_fila.get(0).length];
    for (int i = 0; i < data_fila.size(); i++) {
      String[] row = data_fila.get(i);
      System.arraycopy(row, 0, datos[i], 0, data_fila.get(0).length);
    }
    Tabla infoTabla = new Tabla(tipoDeDatoHeaders, datos, true);
    System.out.println("Cantidad de columnas: " + t._dameHeaders().size());
    System.out.println("Cantidad de filas: " + t.cantFilas());
    System.out.println();
    System.out.println(infoTabla.toString());
  }

  protected static void doSort(Tabla t, String[] columnas) {
    for (String etiquetaColumna : columnas) {
      if (!t._dameColLabels().containsKey(etiquetaColumna)) {
        throw new IllegalLabelException("La columna '"
            + etiquetaColumna + "' no existe en la tabla original.");
      }
    }
    t._dameOrder().sort((fila1, fila2) -> {
      for (String header : columnas) {
        Celda celda1 = t.getFila(fila1).getCelda(t._dameColLabels().get(header));
        Celda celda2 = t.getFila(fila2).getCelda(t._dameColLabels().get(header));

        if (celda1.getContenido() == null && celda2.getContenido() == null) {
          continue; // Ambos valores son nulos entonces sigue
        } else if (celda1.getContenido() == null) {
          return 1; // El valor de fila1 es nulo entonces lo pone despues de fila1
        } else if (celda2.getContenido() == null) {
          return -1; // El valor de fila2 es nulo entonces lo pone despues de fila1
        }

        int comparacion = celda1.compareTo(celda2);
        if (comparacion != 0) {
          return comparacion;
        }
      }
      return 0; // Las filas son iguales en todas las columnas especificadas
    });
  }

  /**
   * Genera rowlabels para el filtrado
   * 
   * @param filas
   */
  private static void generarRowLabelsFiltrado(Tabla t, List<String> filas) {
    Map<String, Integer> nuevasRowLabels = new LinkedHashMap<>();
    List<String> nuevoOrder = new ArrayList<>();

    for (String fila : filas) {
      nuevasRowLabels.put(fila, t._dameRowLabels().get(fila));
      nuevoOrder.add(fila);
    }
    // Ordenar las etiquetas de fila
    nuevoOrder.sort(Comparator.comparingInt(t._dameRowLabels()::get));
    t._dameRowLabels().clear();
    t.setRowLabels(nuevasRowLabels);
    t._dameOrder().clear();
    t.setOrder(nuevoOrder);
  }

  /**
   * Filtra dado un preodicado
   * 
   * @param condicion
   * @return Tabla filtrada
   */
  protected static Tabla filtrar(Tabla t, Predicate<Fila> condicion) {
    Tabla nuevaTabla = t.deepCopy();
    List<String> salida = new ArrayList<>();
    for (String etiquetaFila : t._dameRowLabels().keySet()) {
      Fila filaAComparar = t.getFila(etiquetaFila);

      if (condicion.test(filaAComparar)) {
        salida.add(etiquetaFila);
      }
    }
    generarRowLabelsFiltrado(nuevaTabla, salida);
    return nuevaTabla;
  }

  protected static void head(Tabla t) {
    if (t.cantFilas() > 10) {
      int[] f = IntStream.range(0, 10).toArray();
      String[] fStrings = new String[f.length];
      for (int i = 0; i < f.length; i++) {
        fStrings[i] = String.valueOf(f[i]);
      }
      System.out.println(t.seleccionarFilas(fStrings).toString());
    } else {
      System.out.println(t.toString());
    }
  }

  protected static void head(Tabla t, int n) {
    if (t.cantFilas() < n) {
      throw new IllegalArgumentException("La tabla tiene "
          + t.cantFilas() + " filas, escriba un número menor");
    } else {
      int[] f = IntStream.range(0, n).toArray();
      String[] fStrings = new String[f.length];
      for (int i = 0; i < f.length; i++) {
        fStrings[i] = String.valueOf(f[i]);
      }
      System.out.println(t.seleccionarFilas(fStrings).toString());
    }
  }

  protected static Tabla seleccionar(Tabla t, String[] etiquetaColumnas, String[] etiquetaFilas) {
    Tabla seleccionColumnas = seleccionarColumnas(t, etiquetaColumnas);
    Tabla seleccionFinal = seleccionColumnas.seleccionarFilas(etiquetaFilas);
    return seleccionFinal;
  }

  protected static Tabla seleccionarColumnas(Tabla t, String[] etiquetaColumnas) {
    Tabla nuevaTabla = t.deepCopy();
    Map<String, Integer> newColLabels = new LinkedHashMap<>();
    List<String> newHeaders = new ArrayList<>();

    for (String etiqueta : etiquetaColumnas) {
      if (nuevaTabla._dameColLabels().containsKey(etiqueta)) {
        int valor = nuevaTabla._dameColLabels().get(etiqueta);
        newColLabels.put(etiqueta, valor);
        newHeaders.add(etiqueta);
      } else {
        throw new IllegalArgumentException("La columna '"
            + etiqueta + "' no existe en la tabla original.");
      }
    }
    nuevaTabla.setColLabels(newColLabels);
    nuevaTabla.setHeaders(newHeaders);
    return nuevaTabla;
  }

  protected static Tabla seleccionarFilas(Tabla t, String[] etiquetaFilas) {
    Tabla nuevaTabla = t.deepCopy();
    Map<String, Integer> newRowLabels = new LinkedHashMap<>();
    List<String> newOrder = new ArrayList<>();

    for (String etiqueta : etiquetaFilas) {
      if (nuevaTabla._dameRowLabels().containsKey(etiqueta)) {
        int valor = nuevaTabla._dameRowLabels().get(etiqueta);
        newRowLabels.put(etiqueta, valor);
        newOrder.add(etiqueta);
      } else {
        throw new IllegalArgumentException("La fila '"
            + etiqueta + "' no existe en la tabla original.");
      }
    }
    // Actualizar las etiquetas y el orden después de verificar todas las filas
    nuevaTabla.setRowLabels(newRowLabels);
    nuevaTabla.setOrder(newOrder);
    return nuevaTabla;
  }
}