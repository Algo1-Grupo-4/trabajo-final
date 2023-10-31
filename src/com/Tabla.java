package com;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import excepciones.*;

/**
 * @author Grupo4 -
 */

public class Tabla {
    /**
     * Tabla es, bueno, la tabla
     */
    private List<Columna> tabla;
    private List<String> headers;
    private List<String> order;
    private Map<String, Integer> colLabels;
    private Map<String, Integer> rowLabels;
    private String[] tiposDato;
    private boolean esta_ordenado; // para cuando haya que poner que se reordene si ya lo estaba cada vez que
                                   // agrego una fila

    /**
     * Constructor de una tabla desde una Lista de Lista de Strings.
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
     *                   tabla = new Tabla(tiposDato, array, false);
     *                   </pre>
     */
    public Tabla(String[] tiposDato, String[][] datos, boolean hasHeaders) {
        List<String> lineas = new LinkedList<>();
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        StringBuffer sb;
        for (String[] c : datos) {
            sb = new StringBuffer();
            for (int i = 0; i < c.length; i++) {
                sb.append(c[i] + ","); // no me juzguen
            }
            String str = sb.toString();
            lineas.add(str);
            // for (String r : c) {
            // lineas.add(r);
            // }
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
            String[][] data = parserCSV(lineas, tiposDato.length, ",");
            if (data.length > 0) {
                tabla = new ArrayList<>();
                for (String tipoDato : tiposDato) {
                    Columna columna = new Columna(tipoDato, data.length);
                    tabla.add(columna);
                }
            } else {
                throw new IllegalArgumentException("No se encontraron datos en el archivo CSV.");
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

    }

    /**
     * Constructor de una tabla desde un archivo CSV sin encabezados.
     * 
     * @param tiposDato String[] indicando los tipos de dato
     * @param datos     List<String> Con los datos para cargar en tabla
     * 
     */
    public Tabla(String[] tiposDato, String filename) {
        List<String> lineas = null;
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        this.tiposDato = tiposDato;
        this.esta_ordenado = false;

        try {
            lineas = leerCSV(filename);

            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException("La cantidad de columnas y tipos de datos no coinciden.");
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

            String[][] datos = parserCSV(lineas, tiposDato.length, ",");

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
        List<String> lineas = null;
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        this.tiposDato = tiposDato;
        this.esta_ordenado = false;
        try {
            lineas = leerCSV(fileName);
            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException("La cantidad de columnas y tipos de datos no coinciden.");
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
                throw new IllegalConstructorException("Si no tiene headers, use el constructor apropiado");
            }

            String[][] datos = parserCSV(lineas, tiposDato.length, ",");

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

    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders, boolean hasRowKey, int columna_key) {
        List<String> lineas = null;
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        this.tiposDato = tiposDato;
        this.esta_ordenado = false;

        try {
            lineas = leerCSV(fileName);
            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException("La cantidad de columnas y tipos de datos no coinciden.");
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
                throw new IllegalConstructorException("Si no tiene headers, use el constructor apropiado.");
            }

            String[][] datos = parserCSV(lineas, tiposDato.length, ",");

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

    // --LECTURA Y
    // CARGA--------------------------------------------------------------------------------------------------

    public List<String> leerCSV(String fileName) throws IOException {
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

    public String[][] parserCSV(List<String> lineas, int cantColumnas, String separador) {
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

    private void llenarTabla(String[][] datos, String[] tiposDato) throws InvalidDataTypeException {

        for (int index_columna = 0; index_columna < tiposDato.length; index_columna++) {
            Columna columna = tabla.get(index_columna);

            // Verificar si los tipos de las celdas son iguales NO FUNCIONA
            if (!columna.sonMismosTipos()) {
                throw new InvalidDataTypeException("Los tipos de datos en la columna no coinciden.");
            }
            try {
                for (int index_fila = 0; index_fila < datos.length; index_fila++) {
                    Celda celda = columna.getCelda(index_fila);
                    String valor = datos[index_fila][index_columna];
                    if (valor != null) {
                        // Si el tipo de dato es Boolean lo convierte y asigna a una celda.
                        if (tiposDato[index_columna].equals("Boolean")) {
                            boolean valorBoolean = Boolean.parseBoolean(valor);
                            celda.setContenido(valorBoolean);
                            // Si el tipo de dato es Number lo convierte y asigna a una celda.
                        } else if (tiposDato[index_columna].equals("Number")) {
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
                    } else {
                        columna.fillNA();
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Aca paso algo raro");
            }
        }
    }

    // Método toString para mostrar la tabla (hay que despues cambiar para que use
    // las labels)
    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        // Agregar encabezados de columna si están disponibles
        if (headers != null) {
            // out.append(" | ");
            for (String label : colLabels.keySet()) {
                out.append(label).append("\t");
            }
            out.append("\n");

        }
        // Iterar a través de las filas y columnas para agregar el contenido de la tabla
        for (int fila = 0; fila < cantFilas(); fila++) {
            for (int columna = 0; columna < tabla.size(); columna++) {
                Celda celda = tabla.get(columna).getCelda(fila);
                out.append(celda.getContenido()).append("\t");
            }
            out.append("\n");
        }
        return out.toString();
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
        if (colLabels.containsKey(key)) {
            Fila fila = new Fila();

            for (Columna col : tabla) {
                fila.add((Celda) col.getCelda(colLabels.get(key)).getContenido());
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
            throw new IllegalArgumentException("No existe esa Columna");
        }
    }

    // --SETTERS--------------------------------------------------------------------------------------------------
    public void setCelda(String keyFila, String keyColumna, Object value) {
        /**
         * Reemplaza el contenido de una celda
         */
        getCelda(keyFila, keyColumna).setContenido(value);
    }

    public void setColumna(Columna newColumna, String key) {
        /**
         * Reemplaza el contenido de una columna, manteniendo el encabezado.
         */
        if (newColumna.size() == cantFilas()) {
            tabla.set(colLabels.get(key), newColumna);
        } else {
            throw new LengthMismatchException("El tamaño de la columna es incorrecto");
        }
    }

    public void setColumna(Columna newColumna, String oldKey, String newKey) {
        /**
         * Reemplaza el contenido de una columna por una nueva, cambia el encabezado.
         */
        if (newColumna.size() == cantFilas()) {
            tabla.set(colLabels.get(oldKey), newColumna);
            colLabels.put(newKey, colLabels.get(oldKey));
            colLabels.remove(oldKey);
            for (int i = 0; i < tabla.size(); i++) {
                if (headers.get(i) == oldKey) {
                    headers.set(i, newKey);
                }
            }
        } else {
            throw new LengthMismatchException("El tamaño de la columna es incorrecto");
        }
    }

    public void setFila(Fila fila, String key) {
        /**
         * Reemplaza el contenido de una fila por una nueva, manteniendo laetiqueta..
         */
        if (fila.size() == tabla.size()) {
            if (rowLabels.containsKey(key)) {
                for (int i = 0; i < fila.size(); i++) {
                    tabla.get(i).getCeldas().get(rowLabels.get(key)).setContenido(key);
                }
            } else {
                throw new IllegalLabelException("Este encabezado no existe");
            }
        } else {
            throw new LengthMismatchException("La fila no tiene el tamaño correcto");
        }
    }

    // TODO: setFila(Fila fila, String oldKey, String newKey)

    // --MODIFICADORES--------------------------------------------------------------------------------------------------
    public void addColumna(Columna nuevaCol) {
        /**
         * Agrega una nueva columna para tablas sin encabezado.
         */
        tabla.add(nuevaCol);
        colLabels.put(String.valueOf(tabla.size()), tabla.size());
        headers.add(String.valueOf(tabla.size()));
    }

    public void addColumna(Columna nuevaCol, String label) {
        /**
         * Agrega una nueva columna para tablas con encabezado.
         */
        if (colLabels.containsKey(label)) {
            tabla.add(nuevaCol);
            colLabels.put(label, tabla.size());
            headers.add(label);
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
        for (int i = 0; i < tabla.size(); i++) {
            tabla.get(i).addCelda(nuevaFila.getFila().get(i));
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
        List<String> indiceColumna = new ArrayList<>();
        List<String> nombreColumna = new ArrayList<>();
        List<String> cantidadNonNull = new ArrayList<>();

        for (int index_columna = 0; index_columna < tabla.size(); index_columna++) {
            indiceColumna.add(String.valueOf(index_columna));
        }

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

        String[] indCol = indiceColumna.toArray(new String[0]);
        String[] nomCol = nombreColumna.toArray(new String[0]);
        String[] noNulo = cantidadNonNull.toArray(new String[0]);
        String[] tipoDeDato = { "String", "String", "String", "String" };

        String[][] datos = { indCol, nomCol, noNulo, tiposDato };
        try {
            llenarTabla(datos, tipoDeDato);
        } catch (InvalidDataTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Exporta la tabla a un archivo csv
     * 
     * @param pathArchivo    donde se va a guardar el archivo (y con que nombre)
     * @param incluirHeaders ...eso
     * @param delimitador    caracter que separa los campos
     * 
     */
    public void exportar(String pathArchivo, boolean incluirHeaders, String delimitador) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(pathArchivo));
            writer.write(this.toString(true, ";"));
            writer.newLine();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("estallo");
        }
    }
}