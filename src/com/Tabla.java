package com;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import excepciones.*;

/**
 * @author Grupo4 -
 * 
 */

public class Tabla {
    private List<Columna> tabla;
    private List<String> headers;
    private List<String> order;
    private Map<String, Integer> colLabels = new HashMap<>();
    private Map<String, Integer> rowLabels = new HashMap<>();
    private String[] tiposDato;
    private List<String> lineas = null;
    private boolean esta_ordenado; // para cuando haya que poner que se reordene si ya lo estaba cada vez que
                                   // agrego una fila

    protected Tabla() {
        this.tabla = new ArrayList<>();
        this.headers = new ArrayList<>();
        this.order = new ArrayList<>();
        this.colLabels = new HashMap<>();
        this.rowLabels = new HashMap<>();
        this.lineas = new ArrayList<>();
    }

    private void _setOrdenado(Boolean o) {
        this.esta_ordenado = o;
    }

    private void _setTiposDato(String[] t) {
        this.tiposDato = t;
    }

    // Helpers?
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

    protected String[] _dameTiposDato() {
        return this.tiposDato;
    }

    protected List<String> _dameLineas() {
        return this.lineas;
    }

    protected Boolean _dameEstaOrdenado() {
        return this.esta_ordenado;
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
        Map<String, Integer> colT = new HashMap<>();
        Map<String, Integer> rowT = new HashMap<>();
        String[] tdt;
        List<String> lt = new ArrayList<>();
        Boolean eot;
        // Genero tabla
        for (Columna c : t._dameTabla()) {
            ct.add((Columna) c.clone());
        }
        this.tabla = ct;
        // Genero headers
        for (String h : t._dameHeaders()) {
            ht.add(h.toString());
        }
        this.headers = ht;
        // Genero order
        for (String h : t._dameOrder()) {
            ot.add(h.toString());
        }
        this.order = ot;
        // Genero collabels
        for (Map.Entry<String, Integer> entry : t._dameColLabels().entrySet()) {
            colT.put(entry.getKey(), entry.getValue());
        }
        this.colLabels = colT;
        // Genero rowlabels
        for (Map.Entry<String, Integer> entry : t._dameRowLabels().entrySet()) {
            rowT.put(entry.getKey(), entry.getValue());
        }
        this.rowLabels = rowT;
        // Genero tiposDato
        tdt = Arrays.copyOf(t._dameTiposDato(), t._dameTiposDato().length);
        this.tiposDato = tdt;
        // Genero lineas
        for (String l : t._dameLineas()) {
            lt.add(l.toString());
        }
        this.lineas = lt;
        // Genero esta ordenado
        eot = t._dameEstaOrdenado();
        this.esta_ordenado = eot;

    }

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
            if (data.length > 0) {
                tabla = new ArrayList<>();
                for (String tipoDato : tiposDato) {
                    Columna columna = new Columna(tipoDato, data.length);
                    tabla.add(columna);
                }
            } else {
                throw new IllegalArgumentException(
                        "No se encontraron datos en el archivo CSV.");
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
        this.tiposDato = tiposDato;
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
        this.tiposDato = tiposDato;
        this.esta_ordenado = false;

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

            if (datos.length > 0) {
                tabla = new ArrayList<>();
                for (String tipoDato : tiposDato) {
                    Columna columna = new Columna(tipoDato, datos.length);
                    tabla.add(columna);
                }
            } else {
                throw new IllegalArgumentException(
                        "No se encontraron datos en el archivo CSV.");
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
     * @param datos      List<String> Con los datos para cargar en tabla
     * @param hasHeaders boolean de si el archivo tiene headers o no
     * 
     */
    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders) {
        this.tiposDato = tiposDato;
        this.esta_ordenado = false;
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

            if (datos.length > 0) {
                tabla = new ArrayList<>();
                for (String tipoDato : tiposDato) {
                    Columna columna = new Columna(tipoDato, datos.length);
                    tabla.add(columna);
                }
            } else {
                throw new IllegalArgumentException(
                        "No se encontraron datos en el archivo CSV.");
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

    // TODO: Expandir
    /**
     * Crea una tabla pero tiene hasRowKey
     * 
     * @param tiposDato
     * @param fileName
     * @param hasHeaders
     * @param hasRowKey
     * @param columna_key
     */
    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders, boolean hasRowKey, int columna_key) {
        this.tiposDato = tiposDato;
        this.esta_ordenado = false;

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

            if (datos.length > 0) {
                tabla = new ArrayList<>();
                for (String tipoDato : tiposDato) {
                    Columna columna = new Columna(tipoDato, datos.length);
                    tabla.add(columna);
                }
            } else {
                throw new IllegalArgumentException("No se encontraron datos en el archivo CSV.");
            }

            llenarTabla(datos, tiposDato);

            if (hasRowKey) {
                List<String> order = new ArrayList<>();
                int i = 0;
                for (Celda celda : tabla.get(columna_key).getCeldas()) {
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
     * Constructor de una tabla desde otra tabla
     * 
     * @param tabla Tabla origen
     * 
     */
    // public Tabla(Tabla tabla){
    // this.esta_ordenado = false;
    // this.tabla = tabla.tabla;
    // this.headers = tabla.headers;
    // this.order = tabla.order;
    // this.colLabels = tabla.colLabels;
    // this.rowLabels = tabla.rowLabels;
    // this.tiposDato = tabla.tiposDato;
    // this.lineas = tabla.lineas;
    // }

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

    // Método toString para mostrar la tabla
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();

        // Auto-detección del ancho de columna
        int[] columnWidths = new int[headers.size()];
        Arrays.fill(columnWidths, 0);

        // Obtener el orden de las filas
        List<String> orderFilas = order;

        // Agregar labels de columna si hay
        for (int i = 0; i < headers.size(); i++) {
            String header = headers.get(i);
            out.append(String.format("%-" + (columnWidths[i] + 4) + "s", centerText(header))); // +4 para espacio
                                                                                               // adicional
        }
        out.append("\n");

        // Agregar divisiones entre las columnas
        for (int i = 0; i < headers.size(); i++) {
            out.append(String.format("%-" + (columnWidths[i] + 8) + "s", "").replace(' ', '-'));
        }
        out.append("\n");

        // Iterar y agregar filas en el orden especificado
        for (String filaKey : orderFilas) {
            Map<String, Integer> rowLabels = this.rowLabels;
            if (!rowLabels.containsKey(filaKey)) {
                throw new IllegalArgumentException("La fila con la clave " + filaKey + " no existe en la tabla.");
            }

            int rowIndex = rowLabels.get(filaKey);

            for (int i = 0; i < headers.size(); i++) {
                String header = headers.get(i);
                int columnIndex = colLabels.get(header); // Obtener el índice de la columna a partir del header
                Celda celda = tabla.get(columnIndex).getCelda(rowIndex);
                String contenido = (celda.getContenido() == null) ? "NA" : String.valueOf(celda.getContenido());
                out.append(String.format("%-" + (columnWidths[i] + 6) + "s", centerText(contenido)));
            }
            out.append("\n");
        }

        return out.toString();
    }

    // Método para centrar el texto en una columna
    private String centerText(String text) {
        int totalWidth = 15; // Puedes ajustar el ancho deseado
        int padding = (totalWidth - text.length()) / 2;
        return String.format("%" + (padding + text.length()) + "s", text);
    }

    /**
     * Gives a string representation of the table
     * 
     * @param headers   true if you want headers
     * @param delimiter the delimiter of the csv. "defaults" to ,
     * @return a String representation
     */
    public String toString(boolean headers, String delimiter) {
        // TODO: discutir si este catch es util
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

    public int size() {
        return tabla.size();
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

    // --GETTERS--------------------------------------------------------------------------------------------------

    /**
     * Devuelve la columna con la etiqueta especificada.
     */
    public Columna getColumna(String key) {
        if (colLabels.containsKey(key)) {
            return tabla.get(colLabels.get(key));
        } else {
            throw new IllegalArgumentException("No existe una columna con esa clave");
        }
    }

    /**
     * Devuelve la fila con la etiqueta especificada.
     */
    public Fila getFila(String key) {
        if (rowLabels.containsKey(key)) {
            Fila fila = new Fila();
            for (Columna col : tabla) {
                Celda celda = col.getCelda(rowLabels.get(key));
                fila.addCelda(celda);
            }
            return fila;
        } else {
            throw new IllegalArgumentException("No hay fila con esa key");
        }
    }

    /**
     * Devuelve la celda con la etiqueta especificada.
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

    // --SETTERS--------------------------------------------------------------------------------------------------
    public void setColumna(Columna newColumna, String key) {
        /**
         * Reemplaza el contenido de una columna por una nueva, mantiene la label.
         */
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

    public void setColumna(Columna newColumna, String oldKey, String newKey) { // 
        /**
         * Reemplaza el contenido de una columna por una nueva, cambia la label.
         */
        if (newColumna.size() == cantFilas()) {
            if (colLabels.containsKey(oldKey)) {
                if (!colLabels.containsKey(newKey)) {
                    tabla.add(newColumna);
                    colLabels.put(newKey, ultimoIndice());
                    headers.set(colLabels.get(oldKey) , newKey);
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

    public void setFila(Fila newFila, String key) {
        /**
         * Reemplaza el contenido de una fila por una nueva, mantiene la label.
         */
        if (newFila.size() == tabla.size()) {
            if (rowLabels.containsKey(key)) {
                for (int i = 0; i < newFila.size(); i++) {
                    tabla.get(i).getCeldas().add(newFila.getCelda(i));
                }
                rowLabels.put(key, cantFilas() -1);
                
            } else {
                throw new IllegalLabelException("La fila especificada no existe");
            }
        } else {
            throw new LengthMismatchException("El tamaño de la fila nueva no coincide con la cantidad de columnas.");
        }
    }

    // public void setFila(Fila newFila, String oldKey, String newKey) { // Funciona pero no lo imprime bien
    //     /**
    //      * Reemplaza el contenido de una fila por una nueva, cambia la label sirve solo
    //      * para rowkeys no numericas, sino no tiene sentido.
    //      */
    //     if (newFila.size() == tabla.size()) {
    //         if (rowLabels.containsKey(oldKey)) {
    //             if (!rowLabels.containsKey(newKey)) {
    //                 for (int i = 0; i < newFila.size(); i++) {
    //                     tabla.get(i).getCeldas().set(rowLabels.get(oldKey), newFila.getCelda(i));
    //                 }
    //                 rowLabels.put(newKey, rowLabels.get(oldKey));
    //                 rowLabels.remove(oldKey);
    //             } else {
    //                 throw new IllegalLabelException(
    //                         "La nueva etiqueta ya corresponde a otra fila y no puede ser duplicada.");
    //             }
    //         } else {
    //             throw new IllegalLabelException("La fila especificada no existe.");
    //         }
    //     } else {
    //         throw new LengthMismatchException("El tamaño de la fila nueva no coincide con la cantidad de columnas.");
    //     }
    // }

    public void setCelda(String keyFila, String keyColumna, Object value) throws InvalidDataTypeException {
        /**
         * Reemplaza el contenido de una celda
         */
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

    // --MODIFICADORES--------------------------------------------------------------------------------------------------
    public void addColumna(Columna nuevaCol) {
        // sirve solo para cosas sin headers, habria que poner alguna verificacion, ni se si es necesario este constructor
        /**
         * Agrega una nueva columna para tablas sin encabezado.
         */
        tabla.add(nuevaCol);
        colLabels.put(String.valueOf(ultimoIndice()), ultimoIndice());
        headers.add(String.valueOf(tabla.size()));
    }

    public void addColumna(Columna nuevaCol, String label) {
        /**
         * Agrega una nueva columna para tablas con encabezado.
         */
        if (!colLabels.containsKey(label)) {
            tabla.add(nuevaCol);
            colLabels.put(label, ultimoIndice());
            headers.add(label);
        } else {
            throw new IllegalLabelException("Ya existe una columna con ese nombre");
        }
    }

    public void removeColumna(String key) {
        /**
         * Elimina una columna.
         */
        if (colLabels.containsKey(key)) {
            int index = colLabels.get(key);
            tabla.remove(index);

            for (String header : headers) {
                if (colLabels.get(header) > index) {
                    colLabels.put(header, colLabels.get(header) - 1);
                }
            }
            headers.remove(index);

        } else {
            throw new IllegalLabelException("Este encabezado no existe");
        }
    }

    public void addFila(Fila nuevaFila) {
        /**
         * Agrega una nueva fila.
         */
        if (!contieneFila(nuevaFila)) {
            for (int i = 0; i < tabla.size(); i++) {
                tabla.get(i).addCelda(nuevaFila.getFila().get(i));
                rowLabels.put(String.valueOf(cantFilas() - 1), cantFilas() - 1);
                order.add(String.valueOf(cantFilas() - 1));
            }
        } else {
            throw new IllegalLibraryUse("No se permite duplicar las filas");
        }

    }

    public void removeFila(String key) {
        /**
         * Elimina una fila
         */
        if (rowLabels.containsKey(key)) {
            for (Columna col : tabla) {
                col.removeCelda(rowLabels.get(key));

                for (String row : order) {
                    if (rowLabels.get(row) > rowLabels.get(key)) {
                        rowLabels.put(row, rowLabels.get(row) - 1);
                    }
                }

                order.remove(key);
            }
        } else {
            throw new IllegalLabelException("No existe fila con esa key");
        }
    }

    //// ----NO--REFACTORIZADO----------------------------------------------------------------------------------------------------

    public void infoBasica() {
        /*
         * Lo que esperamos es que salga algo asi
         * # nombreColumna Non-Null Count tipoDato
         * --- ------------- -------------- -------
         * 0 int_col 5 non-null Number
         * 1 text_col 5 non-null String
         * 2 col_boolean 5 non-null boolean
         * 
         * Number[] indiceColumnas = [0, 1, 2] tipo Number
         * nombreColumna = [int_col, text_col, col_boolean] tipo String
         * nonNullCount = [5, 5, 5] tipo Number
         * tipoDato = [Number,String,boolean] tipo String
         * y despues imprimir la tabla que generamos
         */
        // { "A", "B", "C" },
        // { "D", "E", "F" },
        // { "G", "H", "I" }
        // };
        List<String> nombreColumna = new ArrayList<>();
        List<String> cantidadNonNull = new ArrayList<>();

        for (int index_columna = 0; index_columna < tabla.size(); index_columna++) {
            nombreColumna.add(headers.get(index_columna)); /*
                                                            * despues se cambia para usar el metodo para darle headers
                                                            */
        }

        for (int index_columna = 0; index_columna < tabla.size(); index_columna++) {
            Columna miColumna = tabla.get(index_columna); /* esta mugre fue porque Columna no es iterable? */
            int celdasCompletas = 0;
            List<Celda> miLista = miColumna.getCeldas();
            for (Celda celda : miLista) {
                if (celda.isNA() == false) {
                    celdasCompletas++;
                }
            }
            cantidadNonNull.add(String.valueOf(celdasCompletas));
        }

        /*
         * DE ACA PARA ARRIBA NO SE TOCA
         * TODO: headers
         */
        String[] headers = { "Nombre", "NonNull", "TipoDato" };
        String[] tipoDeDato = { "String", "String", "String" };
        String[] nomCol = nombreColumna.toArray(new String[0]);
        String[] noNulo = cantidadNonNull.toArray(new String[0]);
        List<String[]> data_fila = new ArrayList<>();
        data_fila.add(headers);
        for (int i = 0; i < headers.length; i++) {
            String[] row = { nomCol[i], noNulo[i], this.tiposDato[i] };
            data_fila.add(row);
        }
        // String[][] datos = {
        // indCol,
        // nomCol,
        // noNulo,
        // tiposDato };
        String[][] datos = new String[data_fila.size()][data_fila.get(0).length];
        for (int i = 0; i < data_fila.size(); i++) {
            String[] row = data_fila.get(i);
            System.arraycopy(row, 0, datos[i], 0, data_fila.get(0).length);
        }
        Tabla infoTabla = new Tabla(tipoDeDato, datos, true);
        System.out.println(infoTabla.toString());
    }

    /* Copy */
    /**
     * Copia la tabla en otra tabla. La nueva tabla es independiente de
     * la original
     * entonces
     * copia != original == true
     * 
     * @return Tabla
     */
    public Tabla copy() {
        return new Tabla(this);
    }

    public Tabla shallowCopy() {
        Tabla t = new Tabla();
        t.tabla = this.tabla;
        t.headers = this.headers;
        t.order = this.order;
        t.colLabels = this.colLabels;
        t.rowLabels = this.rowLabels;
        t.lineas = this.lineas;
        t._setTiposDato(this.tiposDato);
        t._setOrdenado(this.esta_ordenado);
        return t;
    }
    /* Sort */

    public Tabla Sort() {
        Tabla sortedTabla = new Tabla();
        return sortedTabla;
    }
}
