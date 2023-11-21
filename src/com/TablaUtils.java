package com;

import excepciones.*;
import java.util.ArrayList;
import java.util.Collections;
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
      for (Celda c : t.getColumna(encabezado).getCeldas()) {
        if (!c.isNA()) {
          tipoDato.add(c.getContenido().getClass().getSimpleName());
          break;
        }
      }
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

    for (int i = 0; i < t._dameTabla().size(); i++) {
      String[] row = { nomCol[i], noNulo[i], tipoDatoDetectado[i] };
      data_fila.add(row);
    }

    String[][] datos = new String[data_fila.size()][data_fila.get(0).length];
    for (int i = 0; i < data_fila.size(); i++) {
      String[] row = data_fila.get(i);
      System.arraycopy(row, 0, datos[i], 0, data_fila.get(0).length);
    }
    Tabla infoTabla = new Tabla(tipoDeDatoHeaders, datos, true, ",");
    System.out.println("Cantidad de columnas: " + t._dameHeaders().size());
    System.out.println("Cantidad de filas: " + t.cantFilas());
    System.out.println();
    System.out.println(infoTabla.toString());
  }

  protected static Tabla doSort(Tabla t, String[] columnas) {
    Tabla nuevaTabla = t.deepCopy();
    for (String etiquetaColumna : columnas) {
      if (!nuevaTabla._dameColLabels().containsKey(etiquetaColumna)) {
        throw new IllegalLabelException("La columna '" + etiquetaColumna + "' no existe en la tabla original.");
      }
    }
    nuevaTabla._dameOrder().sort((fila1, fila2) -> {
      for (String header : columnas) {
        Celda celda1 = nuevaTabla.getFila(fila1).getCelda(t._dameColLabels().get(header));
        Celda celda2 = nuevaTabla.getFila(fila2).getCelda(t._dameColLabels().get(header));

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
    return nuevaTabla;
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
    head(t, 10);
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

  protected static void tail(Tabla t) {
    tail(t, 10);
  }

  protected static void tail(Tabla t, int n) {
    if (t.cantFilas() < n) {
      throw new IllegalArgumentException("La tabla tiene "
          + t.cantFilas() + " filas, escriba un número menor");
    } else {
      int[] f = IntStream.range(t.cantFilas() - n, t.cantFilas()).toArray();
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

  protected static Tabla concatenate(Tabla left, Tabla right) {
    // Verifico que coincidan las columnas
    List<String> tablaHeaders = left._dameHeaders();
    List<String> otherHeaders = right._dameHeaders();
    if (!tablaHeaders.equals(otherHeaders)) {
      throw new MismatchedDataException("Las columnas de ambas tablas no coinciden.");
    }

    // Verifico que coincidan los tipos de datos
    for (String header : tablaHeaders) {
      Celda celdaThis = left.getCelda("0", header);
      Celda celdaOther = right.getCelda("0", header);

      if (!celdaThis.getClass().equals(celdaOther.getClass())) {
        throw new InvalidDataTypeException("No coinciden los tipos de datos en la columna " + header + ".");
      }
    }
    Tabla newTabla = left.deepCopy();
    // Agregar filas de la otra tabla
    for (String etiquetaFila : right._dameOrder()) {
      Fila filaAgregar = right.getFila(etiquetaFila);
      newTabla.addFila(filaAgregar);
    }
    return newTabla;
  }

  protected static Tabla doSample(Tabla t, int porcentaje) {
    Tabla copia = t.deepCopy();
    if (porcentaje <= 0 || porcentaje > 100) {
      throw new IllegalArgumentException("El porcentaje debe estar entre 1 y 100.");
    }
    // Mezcla el orden de la tabla y toma una sublista aleatoria
    Collections.shuffle(copia._dameOrder());
    int cantidadMuestras = (int) Math.ceil(t.cantFilas() * (porcentaje / 100.0));
    String[] muestras = copia._dameOrder().subList(0, cantidadMuestras).toArray(new String[0]);

    // Filtrar la tabla para incluir solo las muestras
    Tabla nuevaTabla = copia.seleccionarFilas(muestras);
    return nuevaTabla;
  }

  public static Predicate<Fila> moreThan(Tabla t, String columnaKey, Number valor) {
    Predicate<Fila> condicion = fila1 -> {
      Celda celda = fila1.getCelda(columnaKey, t);
      return celda != null & celda.getContenido() instanceof Number
          && ((Number) celda.getContenido()).doubleValue() > valor.doubleValue();
    };
    return condicion;
  }

  public static void summarize(Tabla t, String columnaKey) {
    Columna c = t.getColumna(columnaKey);
    String output = "Columna: " + columnaKey + "\n" +
        "Tipo de Columna: " + c.getTipo() + "\n" +
        "sum: " + String.valueOf(c.sum(c)) + "\n" +
        "max: " + String.valueOf(c.max(c)) + "\n" +
        "min: " + String.valueOf(c.min(c)) + "\n" +
        "count: " + String.valueOf(c.count(c)) + "\n" +
        "media: " + String.valueOf(c.media(c)) + "\n" +
        "varianza: " + String.valueOf(c.varianza(c)) + "\n" +
        "desvío estándar " + String.valueOf(c.desvioEstandar(c)); // TODO: podrian ser estaticos

    System.out.println(output);
  }

  public static void mostrarTodo(Tabla t) {
    StringBuilder out = new StringBuilder();

    // detecto del ancho de columna
    int[] anchoColumna = new int[t._dameHeaders().size()];

    // Obtener el orden de las filas
    List<String> orderFilas = t._dameOrder();

    // Calcular la longitud máxima de cada columna
    for (int i = 0; i < t._dameHeaders().size(); i++) {
      String header = t._dameHeaders().get(i);
      anchoColumna[i] = Math.max(anchoColumna[i], header.length());
    }

    // Agregar labels de columna si hay
    for (int i = 0; i < t._dameHeaders().size(); i++) {
      String header = t._dameHeaders().get(i);
      out.append(String.format("%" + (anchoColumna[i] + 6) + "s", t.centrarTexto(header)));
    }
    out.append("\n");

    // Agregar divisiones entre las columnas
    for (int i = 0; i < t._dameHeaders().size(); i++) {
      out.append(String.format("%-" + (anchoColumna[i] + 8) + "s", "").replace(' ', '-'));
    }
    out.append("\n");
    for (String filaKey : orderFilas) {
      if (!t._dameRowLabels().containsKey(filaKey)) {
        throw new IllegalArgumentException("La fila con la clave " + filaKey + " no existe en la tabla.");
      }

      int rowIndex = t._dameRowLabels().get(filaKey);

      // Agregar la etiqueta de fila
      out.append(String.format("%-" + 8 + "s", filaKey));

      for (int i = 0; i < t._dameHeaders().size(); i++) {
        String header = t._dameHeaders().get(i);
        int columnIndex = t._dameColLabels().get(header); // Obtener el índice de la columna a partir del header
        Celda celda = t._dameTabla().get(columnIndex).getCelda(rowIndex);
        String contenido = (celda.getContenido() == null) ? "NA" : String.valueOf(celda.getContenido());
        contenido = contenido.length() > 40 ? contenido.substring(0, 37) + "..." : contenido;
        out.append(String.format("%-" + (anchoColumna[i] + 6) + "s", contenido));
      }
      out.append("\n");
    }
    System.out.println(out);
  }

}