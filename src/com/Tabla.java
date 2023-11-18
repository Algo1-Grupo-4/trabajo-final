package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import excepciones.*;

/**
 * La clase Tabla permite generar una estructura de datos.
 * 
 * @author Grupo4 - Giorgetti, Nicolás - Molena, Ivana - Sosa, Naiara - Strika,
 *         Camila - Vazquez, Lucía
 * @version 0.9
 * @see <a href=https://www.taylorswift.com/ /> It's me, hi, I'm the Dataframe,
 *      it's me </a>
 */

public class Tabla implements Summarize {
    private List<Columna> tabla;
    private List<String> headers;
    private List<String> order;
    private Map<String, Integer> colLabels = new LinkedHashMap<>();
    private Map<String, Integer> rowLabels = new LinkedHashMap<>();
    private List<String> lineas = null;

    /**
     * Genera una tabla desde una Lista de Lista de Strings.
     * Digamos, una Matriz de Strings.
     * 
     * @param tiposDato  String[][] indicando los tipos de dato
     * @param datos      List\<String\> con los datos para cargar en tabla
     * @param hasHeaders boolean de si datos tiene headers o no.
     *                   Ejemplo:
     * 
     *                   <pre>
     *                   String[] tiposDato = { "String", "String", "String" };
     *                   String[][] array = {
     *                           { "A", "B", "C" },
     *                           { "D", "E", "F" },
     *                           { "G", "H", "I" }
     *                   };
     * 
     *                   </pre>
     */
    public Tabla(String[] tiposDato, String[][] datos, boolean hasHeaders) {
        List<String> lineas = new LinkedList<>();
        StringBuffer sb;
        for (String[] c : datos) {
            sb = new StringBuffer();
            for (int i = 0; i < c.length; i++) {
                sb.append(c[i] + ","); // no me juzguen
            }
            String str = sb.toString();
            lineas.add(str);
        }
        try {
            if (hasHeaders) {
                if (!lineas.isEmpty()) {
                    String[] encabezados = lineas.get(0).split(",");
                    List<String> headers = new ArrayList<>();
                    for (int j = 0; j < encabezados.length; j++) {
                        headers.add(encabezados[j]);
                        colLabels.put(headers.get(j), j);
                    }
                    lineas.remove(0);
                    this.headers = headers;
                }
            } else {
                List<String> headers = new ArrayList<>();
                for (int j = 0; j < lineas.get(0).split(",").length; j++) {
                    colLabels.put(String.valueOf(j), j);
                    headers.add(String.valueOf(j));
                }
                this.headers = headers;
            }
            String[][] data = Tablas.parserCSV(lineas, tiposDato.length, ",");
            if (data.length <= 0) {
                throw new IllegalArgumentException(
                        "No se encontraron datos en el archivo CSV.");
            }
            tabla = new ArrayList<>();
            for (String tipoDato : tiposDato) {
                Columna columna = new Columna(tipoDato, data.length);
                tabla.add(columna);
            }
            llenarTabla(data, tiposDato);
            List<String> order = new ArrayList<>();
            for (int k = 0; k < tabla.get(0).size(); k++) {
                rowLabels.put(String.valueOf(k), k);
                order.add(String.valueOf(k));
            }
            this.order = order;
        } catch (InvalidDataTypeException e) {
            e.printStackTrace();
            System.err.println("Chequear los tipos de datos.");
            System.exit(1);
        } catch (IllegalConstructorException e) {
            e.printStackTrace();
            System.err.println("Check for aproppiate constructor");
            System.exit(1);
        }
        this.lineas = lineas;
    }

    /**
     * Constructor de una tabla desde un archivo CSV sin encabezados.
     * 
     * @param tiposDato String[] indicando los tipos de dato
     * @param datos     List<String> Con los datos para cargar en tabla
     * 
     */
    public Tabla(String[] tiposDato, String filename) {
        try {
            lineas = Tablas.leerCSV(filename);
            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException(
                        "La cantidad de columnas y tipos de datos no coinciden.");
            }
            List<String> headers = new ArrayList<>();
            for (int j = 0; j < lineas.get(0).split(",").length; j++) {
                colLabels.put(String.valueOf(j), j);
                headers.add(String.valueOf(j));
            }
            this.headers = headers;
            for (int j = 0; j < lineas.get(0).split(",").length; j++) {
                colLabels.put(String.valueOf(j), j);
                headers.add(String.valueOf(j));
            }
            this.headers = headers;
            String[][] datos = Tablas.parserCSV(lineas, tiposDato.length, ",");
            if (datos.length <= 0) {
                throw new IllegalArgumentException(
                        "No se encontraron datos en el archivo CSV.");
            }
            tabla = new ArrayList<>();
            for (String tipoDato : tiposDato) {
                Columna columna = new Columna(tipoDato, datos.length);
                tabla.add(columna);
            }
            llenarTabla(datos, tiposDato);
            List<String> order = new ArrayList<>();
            for (int k = 0; k < tabla.get(0).size(); k++) {
                rowLabels.put(String.valueOf(k), k);
                order.add(String.valueOf(k));
            }
            this.order = order;
        } catch (IOException e) {
            System.err.println(e + " \nNo puedo avanzar sin archivo");
            System.exit(1);
        } catch (InvalidDataTypeException e) {
            e.printStackTrace();
            System.err.println("Chequear los tipos de datos.");
            System.exit(1);
        } catch (IncorrectHeaderCountException e) {
            e.printStackTrace();
            System.err.println("Check data type array");
            System.exit(1);
        }
    }

    /**
     * Constructor de una tabla desde un archivo CSV que puede o no tener
     * encabezados.
     * 
     * @param tiposDato  String[] indicando los tipos de dato
     * @param fileName   String con el path hacia el archivo
     * @param hasHeaders boolean de si el archivo tiene headers o no
     */
    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders) {
        try {
            lineas = Tablas.leerCSV(fileName);
            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException(
                        "La cantidad de columnas y tipos de datos no coinciden.");
            }
            if (hasHeaders) {
                if (!lineas.isEmpty()) {
                    String[] encabezados = lineas.get(0).split(",");
                    List<String> headers = new ArrayList<>();

                    for (int j = 0; j < encabezados.length; j++) {
                        headers.add(encabezados[j]);
                        colLabels.put(headers.get(j), j);
                    }
                    lineas.remove(0);
                    this.headers = headers;
                }
            } else {
                throw new IllegalConstructorException(
                        "Si no tiene headers, use el constructor apropiado");
            }
            String[][] datos = Tablas.parserCSV(lineas, tiposDato.length, ",");
            if (datos.length <= 0) {
                throw new IllegalArgumentException(
                        "No se encontraron datos en el archivo CSV.");
            }
            tabla = new ArrayList<>();
            for (String tipoDato : tiposDato) {
                Columna columna = new Columna(tipoDato, datos.length);
                tabla.add(columna);
            }
            llenarTabla(datos, tiposDato);
            List<String> order = new ArrayList<>();
            for (int k = 0; k < tabla.get(0).size(); k++) {
                rowLabels.put(String.valueOf(k), k);
                order.add(String.valueOf(k));
            }
            this.order = order;
        } catch (IOException e) {
            System.err.println(e + " \nNo puedo avanzar sin archivo");
            System.exit(1);
        } catch (InvalidDataTypeException e) {
            e.printStackTrace();
            System.err.println("Chequear los tipos de datos.");
            System.exit(1);
        } catch (IncorrectHeaderCountException e) {
            e.printStackTrace();
            System.err.println("Check data type array");
            System.exit(1);
        } catch (IllegalConstructorException e) {
            e.printStackTrace();
            System.err.println("Check for aproppiate constructor");
            System.exit(1);
        }
    }

    // TODO: Responder esta pregunta: ¿Cuándo se usa esta tabla?
    // TODO: Vamos a dejarla publica?
    /**
     * Crea una tabla cuando
     * 
     * @param tiposDato  lista de tipos de datos
     * @param fileName   path al archivo csv
     * @param hasHeaders si el archivo tiene headers
     * @param hasRowKey  si las filas del archivo tienen una clave
     * @param columnaKey la key de la columna que debe ser tratada como clave
     */
    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders, boolean hasRowKey, int columnaKey) {

        try {
            lineas = Tablas.leerCSV(fileName);
            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException(
                        "La cantidad de columnas y tipos de datos no coinciden.");
            }
            if (hasHeaders) {
                if (!lineas.isEmpty()) {
                    String[] encabezados = lineas.get(0).split(",");
                    List<String> headers = new ArrayList<>();

                    for (int j = 0; j < encabezados.length; j++) {
                        headers.add(encabezados[j]);
                        colLabels.put(headers.get(j), j);
                    }
                    lineas.remove(0);
                    this.headers = headers;
                }
            } else {
                throw new IllegalConstructorException(
                        "Si no tiene headers, use el constructor apropiado.");
            }
            String[][] datos = Tablas.parserCSV(lineas, tiposDato.length, ",");
            if (datos.length <= 0) {
                throw new IllegalArgumentException("No se encontraron datos en el archivo CSV.");
            }
            tabla = new ArrayList<>();
            for (String tipoDato : tiposDato) {
                Columna columna = new Columna(tipoDato, datos.length);
                tabla.add(columna);
            }
            llenarTabla(datos, tiposDato);
            if (hasRowKey) {
                List<String> order = new ArrayList<>();
                int i = 0;
                for (Celda celda : tabla.get(columnaKey).getCeldas()) {
                    rowLabels.put(String.valueOf(celda.getContenido()), i);
                    order.add(String.valueOf(celda.getContenido()));
                }
                if (order.size() == rowLabels.keySet().size()) {
                    this.order = order;
                } else {
                    throw new IllegalLabelException("La columna dada no es una key");
                }
            } else {
                throw new IllegalConstructorException("Si no tiene clave, use el constructor apropiado");
            }
        } catch (IOException e) {
            System.err.println(e + " \nNo puedo avanzar sin archivo");
            System.exit(1);
        } catch (InvalidDataTypeException e) {
            e.printStackTrace();
            System.err.println("Check data types");
            System.exit(1);
        } catch (IncorrectHeaderCountException e) {
            e.printStackTrace();
            System.err.println("Check data type array");
            System.exit(1);
        } catch (IllegalConstructorException e) {
            e.printStackTrace();
            System.err.println("Check for aproppiate constructor");
            System.exit(1);
        } catch (IllegalLabelException e) {
            e.printStackTrace();
            System.err.println("Check if the given column is actually a key");
            System.exit(1);
        }
    }

    /**
     * Genera una tabla vacía
     * Esto es utilizado para un shallowCopy
     * 
     */
    protected Tabla() {
        this.tabla = new ArrayList<>();
        this.headers = new ArrayList<>();
        this.order = new ArrayList<>();
        this.colLabels = new LinkedHashMap<>();
        this.rowLabels = new LinkedHashMap<>();
        this.lineas = new ArrayList<>();
    }

    /**
     * Crea una tabla usando una tabla (un deep copy)
     * 
     * @param t tabla a copiar
     */
    private Tabla(Tabla t) {
        List<Columna> ct = new ArrayList<>();
        List<String> ht = new ArrayList<>();
        List<String> ot = new ArrayList<>();
        Map<String, Integer> colT = new LinkedHashMap<>();
        Map<String, Integer> rowT = new LinkedHashMap<>();
        List<String> lt = new ArrayList<>();
        for (Columna c : t._dameTabla()) {
            ct.add((Columna) c.clone());
        }
        this.tabla = ct;
        for (String h : t._dameHeaders()) {
            ht.add(h.toString());
        }
        this.headers = ht;
        for (String h : t._dameOrder()) {
            ot.add(h.toString());
        }
        this.order = ot;
        for (Map.Entry<String, Integer> entry : t._dameColLabels().entrySet()) {
            colT.put(entry.getKey(), entry.getValue());
        }
        this.colLabels = colT;
        for (Map.Entry<String, Integer> entry : t._dameRowLabels().entrySet()) {
            rowT.put(entry.getKey(), entry.getValue());
        }
        this.rowLabels = rowT;
        for (String l : t._dameLineas()) {
            lt.add(l.toString());
        }
        this.lineas = lt;
    }

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        // detecti del ancho de columna
        int[] anchoColumna = new int[headers.size()];

        // Obtener el orden de las filas
        List<String> orderFilas = order;

        // Calcular la longitud máxima de cada columna
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            anchoColumna[i] = Math.max(anchoColumna[i], header.length());
        }

        // Agregar labels de columna si hay
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            out.append(String.format("%" + (anchoColumna[i] + 6) + "s", centrarTexto(header))); // +4 para espacio
                                                                                                // adicional
        }
        out.append("\n");

        // Agregar divisiones entre las columnas
        for (int i = 0; i < headers.size(); i++) {
            out.append(String.format("%-" + (anchoColumna[i] + 8) + "s", "").replace(' ', '-'));
        }
        out.append("\n");
        if (cantFilas() > 20) {

            // Iterar y agregar filas en el orden especificado
            for (int rowIndex = 0; rowIndex < 5; rowIndex++) {
                String filaKey = orderFilas.get(rowIndex);
                if (!rowLabels.containsKey(filaKey)) {
                    throw new IllegalArgumentException("La fila con la clave " + filaKey + " no existe en la tabla.");
                }
                // Agregar la etiqueta de fila
                out.append(String.format("%-" + 8 + "s", filaKey));
                for (int i = 0; i < headers.size(); i++) {
                    String header = headers.get(i);
                    int columnIndex = colLabels.get(header);
                    Celda celda = tabla.get(columnIndex).getCelda(rowIndex);
                    String contenido = (celda.getContenido() == null) ? "NA" : String.valueOf(celda.getContenido());
                    out.append(String.format("%-" + (anchoColumna[i] + 6) + "s", contenido));
                }
                out.append("\n");
            }
        // Si hay más de 20 filas, agregar tres puntos suspensivos y mostrar las últimas 5 filas
        out.append("... (y otras " + (cantFilas() - 10) + " filas)\n");
        
        for (int rowIndex = cantFilas() - 5; rowIndex < cantFilas(); rowIndex++) {
            String filaKey = orderFilas.get(rowIndex);
            
            if (!rowLabels.containsKey(filaKey)) {
                throw new IllegalArgumentException("La fila con la clave " + filaKey + " no existe en la tabla.");
            }
            // Agregar la etiqueta de fila
            out.append(String.format("%-" + 8 + "s", filaKey));
            
            for (int i = 0; i < headers.size(); i++) {
                String header = headers.get(i);
                int columnIndex = colLabels.get(header);
                Celda celda = tabla.get(columnIndex).getCelda(rowIndex);
                String contenido = (celda.getContenido() == null) ? "NA" : String.valueOf(celda.getContenido());
                out.append(String.format("%-" + (anchoColumna[i] + 6) + "s", contenido));
            }
            out.append("\n");
    }
    } else {

           for (String filaKey : orderFilas) {
            if (!rowLabels.containsKey(filaKey)) {
                throw new IllegalArgumentException("La fila con la clave " + filaKey + " no existe en la tabla.");
            }
            
            int rowIndex = rowLabels.get(filaKey);
            
            // Agregar la etiqueta de fila
            out.append(String.format("%-" + 8 + "s", filaKey));
            
            for (int i = 0; i < headers.size(); i++) {
                String header = headers.get(i);
                int columnIndex = colLabels.get(header); // Obtener el índice de la columna a partir del header
                Celda celda = tabla.get(columnIndex).getCelda(rowIndex);
                String contenido = (celda.getContenido() == null) ? "NA" : String.valueOf(celda.getContenido());
                contenido = contenido.length() > 40 ? contenido.substring(0, 37) + "..." : contenido;
                out.append(String.format("%-" + (anchoColumna[i] + 6) + "s", contenido));
            }
            out.append("\n");
        }
    }
        return out.toString();
    }

    /**
     * Da una representación de la tabla en una string
     * 
     * @param headers   true if you want headers
     * @param delimiter the delimiter of the csv. "defaults" to ,
     * @return a String representation
     */
    public String toString(boolean headers, String delimiter) {
        if (delimiter == null) {
            delimiter = ",";
        }
        StringBuilder out = new StringBuilder();
        int count = 0;
        if (headers == true) {
            for (String label : colLabels.keySet()) {
                count++;
                if (count < colLabels.keySet().size()) {
                    out.append(label).append(delimiter);
                } else {
                    out.append(label);
                }
            }
            out.append("\n");
        }
        for (int fila = 0; fila < cantFilas(); fila++) {
            for (int columna = 0; columna < tabla.size(); columna++) {
                Celda celda = tabla.get(columna).getCelda(fila);
                if (columna != tabla.size() - 1) {
                    out.append(celda.getContenido()).append(delimiter);
                } else {
                    out.append(celda.getContenido());
                }
            }
            out.append("\n");
        }
        return out.toString();
    }

    /**
     * Devuelve el size de tabla
     * 
     * @return int value del tamaño de la tabla
     */
    public int size() {
        return tabla.size();
    }

    /**
     * Devuelve la columna con la etiqueta especificada.
     * 
     * @param key String -> la clave de la Columna
     */
    public Columna getColumna(String key) {
        if (!colLabels.containsKey(key)) {
            throw new IllegalArgumentException("No existe una columna con esa clave");
        }
        return tabla.get(colLabels.get(key));
    }

    /**
     * Devuelve la fila con la etiqueta especificada.
     * 
     * @param key String -> la clave de la Fila
     */
    public Fila getFila(String key) {
        if (!rowLabels.containsKey(key)) {
            throw new IllegalArgumentException("No hay fila con esa key");
        }
        Fila fila = new Fila();
        for (Columna col : tabla) {
            Celda celda = col.getCelda(rowLabels.get(key));
            fila.addCelda(celda);
        }
        return fila;
    }

    /**
     * Devuelve la celda con las etiqueta especificada.
     * 
     * @param keyFila    String key de la fila
     * @param keyColumna String key de la columna
     */
    public Celda getCelda(String keyFila, String keyColumna) {
        if (colLabels.containsKey(keyColumna)) {
            if (rowLabels.containsKey(keyFila)) {
                return tabla.get(colLabels.get(keyColumna)).getCelda(rowLabels.get(keyFila));
            } else {
                throw new IllegalArgumentException("No existe esa fila");
            }
        } else {
            throw new IllegalArgumentException("No existe esa columna");
        }
    }

    public Map<String, Integer> getColLabels() {
        return colLabels;
    }

    public Map<String, Integer> getRowLabels() {
        return colLabels;
    }

    // --SETTERS--------------------------------------------------------------------------------------------------
    /**
     * Setea una columna nueva, manteniendo la label
     * 
     * @param newColumna
     * @param key
     */
    public void setColumna(Columna newColumna, String key) {
        if (newColumna.size() == cantFilas()) {
            if (colLabels.containsKey(key)) {
                tabla.add(newColumna);
                colLabels.put(key, ultimoIndice());
            } else {
                throw new IllegalLabelException("La columna especificada no existe.");
            }
        } else {
            throw new LengthMismatchException("El tamaño de la columna nueva no coincide con la cantidad de filas.");
        }
    }

    /**
     * Setea una columan, generando una key nueva
     * 
     * @param newColumna
     * @param oldKey
     * @param newKey
     */
    public void setColumna(Columna newColumna, String oldKey, String newKey) {
        if (newColumna.size() == cantFilas()) {
            if (colLabels.containsKey(oldKey)) {
                if (!colLabels.containsKey(newKey)) {
                    int index = colLabels.get(oldKey);
                    tabla.add(newColumna);
                    colLabels.put(newKey, ultimoIndice());
                    headers.set(index, newKey);
                } else {
                    throw new IllegalLabelException(
                            "La nueva etiqueta ya corresponde a otra columna y no puede ser duplicada.");
                }
            } else {
                throw new IllegalLabelException("La columna especificada no existe.");
            }
        } else {
            throw new LengthMismatchException("El tamaño de la columna nueva no coincide con la cantidad de filas.");
        }
    }

    /**
     * Setea la fila manteniendo la label
     * 
     * @param newFila
     * @param key
     */
    public void setFila(Fila newFila, String key) {
        if (newFila.size() == tabla.size()) {
            if (rowLabels.containsKey(key)) {
                for (int i = 0; i < newFila.size(); i++) {
                    tabla.get(i).getCeldas().add(newFila.getCelda(i));
                }
                rowLabels.put(key, cantFilas() - 1);

            } else {
                throw new IllegalLabelException("La fila especificada no existe");
            }
        } else {
            throw new LengthMismatchException("El tamaño de la fila nueva no coincide con la cantidad de columnas.");
        }
    }

    /**
     * setFila para reemplazar keys
     * Reemplaza el contenido de una fila por una nueva, cambia la label sirve solo
     * para rowkeys no numericas, sino no tiene sentido
     * 
     * @param newFila
     * @param oldKey
     * @param newKey
     */
    public void setFila(Fila newFila, String oldKey, String newKey) { // Funciona pero no lo imprime bien
        if (newFila.size() == tabla.size()) {
            if (rowLabels.containsKey(oldKey)) {
                if (!rowLabels.containsKey(newKey)) {
                    for (int i = 0; i < newFila.size(); i++) {
                        tabla.get(i).getCeldas().set(rowLabels.get(oldKey), newFila.getCelda(i));
                    }
                    rowLabels.put(newKey, rowLabels.get(oldKey));
                    rowLabels.remove(oldKey);
                } else {
                    throw new IllegalLabelException(
                            "La nueva etiqueta ya corresponde a otra fila y no puede ser duplicada.");
                }
            } else {
                throw new IllegalLabelException(
                        "La fila especificada no existe.");
            }
        } else {
            throw new LengthMismatchException(
                    "El tamaño de la fila nueva no coincide con la cantidad de columnas.");
        }
    }

    /**
     * Reemplaza el contenido de una celda
     * 
     * @param keyFila    String key de la Fila
     * @param keyColumna String key de la Columna
     * @param value      valor a ser reemplazado
     * @throws InvalidDataTypeException
     *                                  Si el tipo de dato de value no corresponde
     *                                  con el de la columna, tirra este error
     */
    public void setCelda(String keyFila, String keyColumna, Object value) throws InvalidDataTypeException {
        Celda celda = getCelda(keyFila, keyColumna);
        if (celda.getContenido() instanceof Boolean) {
            if (value.equals(true) || value.equals(false)) {
                celda.setContenido(value);
            } else {
                throw new InvalidDataTypeException("el valor no es booleano");
            }

        }
        if (celda.getContenido() instanceof Number) {
            if (value instanceof Number) {
                celda.setContenido(value);
            } else {
                throw new InvalidDataTypeException("El valor no es de tipo Number.");
            }
        }
        if (celda.getContenido() instanceof String) {
            if (value instanceof String) {
                celda.setContenido(value);
            } else {
                throw new InvalidDataTypeException("El valor no es de tipo String.");
            }
        }
    }

    /**
     * Agrego una columna al final de la tabla sin header
     * 
     * @param nuevaCol
     */
    public void addColumna(Columna nuevaCol) {
        // TODO: deprecar?
        // sirve solo para cosas sin headers, habria que poner alguna verificacion, ni
        // se si es necesario este constructor
        tabla.add(nuevaCol);
        colLabels.put(String.valueOf(ultimoIndice()), ultimoIndice());
        headers.add(String.valueOf(tabla.size()));
    }

    /**
     * Agrego una columna al final de la tabla con label (header)
     * 
     * @param nuevaCol Columna a agregar
     * @param label    label de la columna
     */
    public void addColumna(Columna nuevaCol, String label) {
        if (colLabels.containsKey(label)) {
            throw new IllegalLabelException("Ya existe una columna con ese nombre");
        }
        tabla.add(nuevaCol);
        colLabels.put(label, ultimoIndice());
        headers.add(label);
    }

    /**
     * Borro una columna dada una key
     * 
     * @param key key de la Columna a eliminar
     */
    public void removeColumna(String key) {
        if (!colLabels.containsKey(key)) {
            throw new IllegalLabelException("Este encabezado no existe");
        }
        int index = colLabels.get(key);
        tabla.remove(index);
        for (String header : headers) {
            if (colLabels.get(header) > index) {
                colLabels.put(header, colLabels.get(header) - 1);
            }
        }
        headers.remove(index);
    }

    /**
     * Agrego una fila a la tabla
     * 
     * @param nuevaFila Fila a agregar
     */
    public void addFila(Fila nuevaFila) {
        if (nuevaFila.size() != tabla.size()) {
            throw new IllegalArgumentException(
                    "La cantidad de datos de la fila no corresponde con el tamaño de la tabla.");
        }
        if (!contieneFila(nuevaFila)) {
            for (int i = 0; i < tabla.size(); i++) {
                tabla.get(i).addCelda(nuevaFila.getCeldas().get(i));
            }
            rowLabels.put(String.valueOf(cantFilas() - 1), cantFilas() - 1);
            order.add(String.valueOf(cantFilas() - 1));
        } else {
            throw new IllegalLibraryUse("No se permite duplicar las filas.");
        }
    }

    /**
     * Borro una fila dada una key
     * 
     * @param key Key de la fila a eliminar
     */
    public void removeFila(String key) {
        if (!rowLabels.containsKey(key)) {
            throw new IllegalArgumentException("No existe la fila " + key + ".");
        }
        int rowIndex = rowLabels.get(key);
        // Elimino celdas de cada columna
        for (Columna col : tabla) {
            col.removeCelda(rowLabels.get(key));

            for (String row : order) {
                if (rowLabels.get(row) > rowIndex) {
                    rowLabels.put(row, rowLabels.get(row) - 1);
                }
            }
            order.remove(key);
        }
    }

    /**
     * Muestra en stdout una tabla con información
     * "Nombre", "NonNull", "TipoDato"
     * Adicionalmente Muestra la cantidad de columnas y cantidad de filas
     */
    public void infoBasica() {
        List<String> tipoDato = new ArrayList<>();
        for (String encabezado : _dameHeaders()) {
            tipoDato.add(getColumna(encabezado).getCelda(0).getContenido().getClass().getSimpleName());

        }

        String[] tipoDatoDetectado = tipoDato.toArray(new String[0]);

        List<String> cantidadNonNull = new ArrayList<>();
        for (String encabezado : _dameHeaders()) {
            Columna col = getColumna(encabezado);
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
        String[] nomCol = _dameHeaders().toArray(new String[0]);
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
        System.out.println("Cantidad de columnas: " + _dameHeaders().size());
        System.out.println("Cantidad de filas: " + cantFilas());
        System.out.println();
        System.out.println(infoTabla.toString());
    }

    /**
     * Copia la tabla en otra tabla. La nueva tabla es independiente de
     * la original
     * entonces
     * copia != original == true
     * 
     * @return Tabla nueva
     */
    public Tabla copy() {
        return new Tabla(this);
    }

    /**
     * Devuelve una copia superficial de la tabla
     * 
     * @return Tabla nueva (copia superficial)
     */
    public Tabla shallowCopy() {
        Tabla t = new Tabla();
        t.tabla = this.tabla;
        t.headers = this.headers;
        t.order = this.order;
        t.colLabels = this.colLabels;
        t.rowLabels = this.rowLabels;
        t.lineas = this.lineas;
        return t;
    }

    /**
     * Ordena la tabla dada una cantidad de columnas
     * 
     * @param columnas
     * @return Una tabla ordenada
     */
    public Tabla Sort(String[] columnas) {
        // habria que agregar check de que esten y bla bla
        Tabla sortedTabla = this.shallowCopy();
        Tabla reducida = sortedTabla.select(columnas);
        int n = reducida.cantFilas();
        boolean huboCambio;
        do {
            huboCambio = false;
            for (int i = 1; i < n; i++) {

                Fila filaPrevia = reducida.getFila(order.get(i - 1));
                Fila filaActual = reducida.getFila(order.get(i));
                int comparacion = 0;

                for (String header : columnas) {
                    Celda celdaPrevia = filaPrevia.getCelda(colLabels.get(header));
                    Celda celdaActual = filaActual.getCelda(colLabels.get(header));
                    comparacion = celdaPrevia.compareTo(celdaActual);
                    if (comparacion != 0) {
                        break;
                    }
                }
                if (comparacion > 0) {
                    String etiqueta = sortedTabla.order.get(i - 1);
                    sortedTabla.order.set(i - 1, order.get(i));
                    sortedTabla.order.set(i, etiqueta);
                    huboCambio = true;
                }
            }
            n--;
        } while (huboCambio);
        return sortedTabla;
    }

    /**
     * Genera rowlabels dado un filto
     * 
     * @param filas
     */
    // TODO: mejorar javadoc
    public void generarRowLabelsFiltrado(List<String> filas) {
        Map<String, Integer> nuevasRowLabels = new LinkedHashMap<>();
        List<String> nuevoOrder = new ArrayList<>();

        for (String fila : filas) {
            nuevasRowLabels.put(fila, rowLabels.get(fila));
            nuevoOrder.add(fila);
        }
        // Ordenar las etiquetas de fila
        nuevoOrder.sort(Comparator.comparingInt(rowLabels::get));

        rowLabels.clear();
        rowLabels = nuevasRowLabels;
        order.clear();
        order = nuevoOrder;
    }

    /**
     * Filtra
     * 
     * @param condicion
     * @return Tabla filtrada
     */
    public Tabla filtrar(Predicate<Fila> condicion) {
        List<String> salida = new ArrayList<>();

        if (condicion == null) {
            throw new IllegalArgumentException(
                    "Se debe proporcionar una condición válida.");
        }
        for (String etiquetaFila : rowLabels.keySet()) {
            Fila filaAComparar = getFila(etiquetaFila);
            if (condicion.test(filaAComparar)) {
                salida.add(etiquetaFila);
            }
        }
        Tabla tablaFiltrada = new Tabla(this);
        tablaFiltrada.generarRowLabelsFiltrado(salida);
        return tablaFiltrada;
    }

    /**
     * Select
     * 
     * @param columnas
     * @return
     */
    public Tabla select(String[] columnas) {
        Tabla reducida = new Tabla(this);
        for (int i = 0; i < columnas.length; i++) {
            reducida.addColumna(tabla.get(1), columnas[i]);
            reducida.colLabels.put(columnas[i], i);
            reducida.order.add(columnas[i]);
        }

        return reducida;
    }

    /**
     * Concatena
     * 
     * @param other
     * @return
     */
    public Tabla concat(Tabla other) {
        // si es una tabla con rowkeys no numericas habria que chequear que no se
        // repitan

        Tabla newTabla = new Tabla(this);

        for (String header : newTabla._dameHeaders()) {
            if (!other._dameHeaders().contains(header)) {
                throw new MismatchedDataException("Un encabezado de la tabla a agregar no existe en la tabla actual");
            }
        }

        for (String header : other._dameHeaders()) {
            if (!newTabla._dameHeaders().contains(header)) {
                throw new MismatchedDataException("Un encabezado de la tabla actual no existe en la tabla a agregar");
            }
        }

        Tabla other_ordenada = other.select((String[]) newTabla._dameHeaders().toArray());

        for (String header : newTabla._dameHeaders()) {
            Celda cNT = newTabla.getCelda("0", header);
            Celda cO = other_ordenada.getCelda("0", header);

            if (!(cNT.getClass() == cO.getClass() || (cNT.getContenido() != null && cO.getContenido() != null
                    && !cNT.getContenido().getClass().equals(cO.getContenido().getClass())))) {
                throw new InvalidDataTypeException("No coinciden los tipos de datos en las columnas " + header);
            }

        }

        for (String rowKey : other_ordenada._dameOrder()) {

            Fila filaAgregar = other_ordenada.getFila(rowKey);
            newTabla.addFila(filaAgregar);

        }
        return newTabla;
    }

    public Tabla sample(int cantidad_datos) {

        if (cantidad_datos > cantFilas()) {
            throw new LengthMismatchException("El sample debe ser menor a la cantidad de filas");
        }

        Tabla sample = new Tabla(this);
        Collections.shuffle(sample._dameOrder());
        // sample.head(cantidad_datos);
        return sample;
    }

    @Override
    public double sum(Columna columna) {
        if (columna.getCelda(0) instanceof CeldaNumber) {
            double acumulado = 0.0;
            for (Celda celda : columna.getCeldas()) {
                if (celda.getContenido() != null) {
                    Number contenido = (Number) celda.getContenido();
                    acumulado += contenido.doubleValue();
                }

            }
            return acumulado;
        } else {
            throw new InvalidDataTypeException("No se pueden sumar columnas no numericas");
        }

    }

    @Override
    public double max(Columna columna) {
        if (columna.getCelda(0) instanceof CeldaNumber) {
            double maximo = Double.NaN;
            boolean encontrado = false;

            for (Celda celda : columna.getCeldas()) {
                Number contenido = (Number) celda.getContenido();

                if (contenido != null) {
                    if (!encontrado) {
                        maximo = contenido.doubleValue();
                        encontrado = true;
                    } else if (contenido.doubleValue() > maximo) {
                        maximo = contenido.doubleValue();
                    }
                }
            }
            if (encontrado) {
                return maximo;
            } else {
                throw new IllegalStateException("No se encontraron valores no nulos en la columna");
            }
        } else {
            throw new InvalidDataTypeException("No se puede obtener el máximo en columnas no numéricas");
        }
    }

    //// ----NO--REFACTORIZADO----------------------------------------------------------------------------------------------------

    @Override
    public double min(Columna columna) {
        if (columna.getCelda(0) instanceof CeldaNumber) {
            double minimo = Double.NaN;
            boolean encontrado = false;

            for (Celda celda : columna.getCeldas()) {
                Number contenido = (Number) celda.getContenido();

                if (contenido != null) {
                    if (!encontrado) {
                        minimo = contenido.doubleValue();
                        encontrado = true;
                    } else if (contenido.doubleValue() < minimo) {
                        minimo = contenido.doubleValue();
                    }
                }
            }
            if (encontrado) {
                return minimo;
            } else {
                throw new IllegalStateException("No se encontraron valores no nulos en la columna");
            }
        } else {
            throw new InvalidDataTypeException("No se puede obtener el minimo en columnas no numéricas");
        }
    }

    @Override
    public int count(Columna columna) {
        // debe tirar/ imprimir warning si hay valores nulos (NA)
        return columna.size();
    }

    @Override
    public double mean(Columna columna) {
        return sum(columna) / count(columna);
    }

    @Override
    public double variance(Columna columna) {
        if (columna.getCelda(0) instanceof CeldaNumber) {
            double mean = mean(columna);
            double acumulado = 0;

            for (Celda celda : columna.getCeldas()) {
                if (celda.getContenido() != null) {
                    Number contenido = (Number) celda.getContenido();
                    acumulado += Math.pow(contenido.doubleValue() - mean, 2);
                }
            }
            return acumulado / count(columna);

        } else {
            throw new InvalidDataTypeException("No se puede calcular la varianza en columnas no numericas");
        }
    }

    @Override
    public double standardDeviation(Columna columna) {
        return Math.sqrt(variance(columna));
    }

    public Tabla groupBy(String[] columnas) {
        Tabla tabla_agrupada = new Tabla(this);
        return tabla_agrupada;
    }

    public Tabla seleccionar(String[] etiquetaFilas) {
        Tabla nueva = this.copy(); // reemplazar esto por shallow copy cuando esté listo
        nueva.rowLabels = new HashMap<>();
        nueva.order = new ArrayList<>();
        // nueva.lineas = new LinkedList<>();
        // nueva.tabla = new ArrayList<>();

        for (String e : etiquetaFilas) {
            // String s = _dameLineas().get(Integer.valueOf(e));
            // nueva.lineas.add(s);
            int v = _dameRowLabels().get(e);
            nueva.rowLabels.put(e, v);
            nueva.order.add(e);
        }

        return nueva;
    }

    protected List<Columna> _dameTabla() {
        return this.tabla;
    }

    protected List<String> _dameHeaders() {
        return this.headers;
    }

    protected List<String> _dameOrder() {
        return this.order;
    }

    protected Map<String, Integer> _dameColLabels() {
        return this.colLabels;
    }

    protected Map<String, Integer> _dameRowLabels() {
        return this.rowLabels;
    }

    protected List<String> _dameLineas() {
        return this.lineas;
    }

    private void llenarTabla(String[][] datos, String[] tiposDato) throws InvalidDataTypeException {

        for (int index_columna = 0; index_columna < tiposDato.length; index_columna++) {
            Columna columna = tabla.get(index_columna);

            // Verificar si los tipos de las celdas son iguales NO FUNCIONA
            if (!columna.sonMismosTipos()) {
                throw new InvalidDataTypeException("Los tipos de datos en la columna no coinciden.");
            }

            for (int index_fila = 0; index_fila < datos.length; index_fila++) {
                Celda celda = columna.getCelda(index_fila);
                String valor = datos[index_fila][index_columna];
                if (valor != null) {
                    // Si el tipo de dato es Boolean lo convierte y asigna a una celda.
                    if (tiposDato[index_columna].equals("Boolean")) {
                        valor = valor.replaceAll("\\s", ""); // remove whitespace
                        boolean valorBoolean = Boolean.parseBoolean(valor);
                        celda.setContenido(valorBoolean);
                        // Si el tipo de dato es Number lo convierte y asigna a una celda.
                    } else if (tiposDato[index_columna].equals("Number")) {
                        valor = valor.replaceAll("\\s", ""); // remove whitespaces on Numbers
                        try {
                            if (valor.contains(".")) {
                                Double valorDouble = Double.parseDouble(valor);
                                celda.setContenido(valorDouble);
                            } else {
                                Integer valorInteger = Integer.parseInt(valor);
                                celda.setContenido(valorInteger);
                            }
                        } catch (NumberFormatException e) {
                            throw new InvalidDataTypeException("Valor: " + valor + " no pudo ser parseado.");
                        }
                        // Si el tipo de dato es String lo convierte y asigna a una celda.
                    } else if (tiposDato[index_columna].equals("String")) {
                        celda.setContenido(valor);
                    }

                    // Verificar si los tipos de las celdas son iguales NO FUNCIONA
                    if (!columna.sonMismosTipos()) {
                        throw new InvalidDataTypeException("Los tipos de datos en la columna no coinciden.");
                    }

                } else {
                    columna.fillNA();
                }
            }
        }
    }

    // Método para centrar el texto en una columna
    private String centrarTexto(String texto) {
        int anchoTotal = 15; // Puedes ajustar el ancho deseado
        int padding = (anchoTotal - texto.length()) / 2;
        return String.format("%" + (padding + texto.length()) + "s", texto);
    }

    // --METODOS
    // UTILES--------------------------------------------------------------------------------------------------
    private int cantFilas() {
        /**
         * Devuelve la cantidad de filas en la tabla.
         */
        return tabla.get(0).size();
    }

    private int ultimoIndice() {
        return tabla.size() - 1;
    }

    private boolean contieneFila(Fila fila) {
        for (String rowKey : order) {
            Fila row = getFila(rowKey);
            if (fila.equals(row)) {
                return true;
            }
        }
        return false;
    }

}
