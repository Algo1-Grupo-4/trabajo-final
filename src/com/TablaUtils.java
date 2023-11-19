package com;

import excepciones.*;
import java.util.ArrayList;

import java.util.List;

public class TablaUtils {

  public static void doBasic(Tabla t) {
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

  public static void doSort(Tabla t, String[] columnas) {
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
}