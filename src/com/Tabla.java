package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

// import javax.lang.model.type.NullType;

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
    private boolean esta_ordenado; //para cuando haya que poner que se reordene si ya lo estaba cada vez que agrego una fila

    /**
     * Constructor de una tabla desde una Lista de Strings
     * 
     * @param tiposDato  String[] indicando los tipos de dato
     * @param datos      List<String> Con los datos para cargar en tabla
     * @param hasHeaders boolean de si datos tiene headers o no
     *                   ESTO NO FUNCIONA
     */
    public Tabla(String[] tiposDato, List<List<String>> datos, boolean hasHeaders) {
        // List<String> lineas = null;
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        String[][] data;
        if (!datos.isEmpty()) {
            if (hasHeaders) {
                List<String> headers = datos.get(0);
            }
            for (List<String> row : datos) {
                data = parserCSV(row, datos.size(), ",");
                try {

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
                } catch (InvalidDataTypeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }

    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders) {
        List<String> lineas = null;
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();
        this.tiposDato = tiposDato;
        try {
            lineas = leerCSV(fileName, hasHeaders);
            if (lineas.get(0).split(",").length != tiposDato.length) {
                throw new IncorrectHeaderCountException("Mismatch between data type array and columns in csv");
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
                List<String> headers = new ArrayList<>();
                for (int j = 0; j < lineas.get(0).split(",").length; j++) {
                    colLabels.put(String.valueOf(j), j);
                    headers.add(String.valueOf(j));
                }
                this.headers = headers;
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

            for (int k = 0; k < tabla.get(0).size(); k++) {
                rowLabels.put(String.valueOf(k), k);
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
        }
    }

    public List<String> leerCSV(String fileName, boolean hasHeaders) throws IOException {
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
        /**
         * Llena la Tabla
         */
        for (int index_columna = 0; index_columna < tiposDato.length; index_columna++) {
            Columna columna = tabla.get(index_columna);
            for (int index_fila = 0; index_fila < datos.length; index_fila++) {
                Celda celda = columna.getCelda(index_fila);
                String valor = datos[index_fila][index_columna];

                if (valor != null && tiposDato[index_columna].equals("Boolean")) {
                    // Convierte el valor a boolean y lo asigna a la celda
                    boolean booleanValue = Boolean.parseBoolean(valor);
                    celda.setContenido(booleanValue);

                } else if (tiposDato[index_columna].equals("Number")) {
                    if (valor != null) {
                        try {
                            if (valor.contains(".")) {
                                Double valorDouble = Double.parseDouble(valor);
                                celda.setContenido(valorDouble);
                            } else {
                                Integer valorInteger = Integer.parseInt(valor);
                                celda.setContenido(valorInteger);
                            }
                        } catch (NumberFormatException e) {
                            throw new InvalidDataTypeException("Value: " + valor + " Tried to be parsed and failed. ");
                        }
                    }
                } else if (tiposDato[index_columna].equals("String")) {
                    // Asigna el valor directamente a la celda
                    celda.setContenido(valor);
                }
            }
        }

    }

    @Override
    // TODO: This instead of mostrarTabla()
    public String toString() {
        return "Hay tabla:\n" + tabla + "\nHeaders=" + headers + "\nCantidad de filas " + rowLabels.size();
    }

    public void mostrarTabla() {
        /**
         * Muestra la tabla en Sysout
         * TODO: Transformar esto en un override de mostrar el objeto en
         * pantalla (ver ejemplo en Matriz.java que se vio en clase)
         */
        for (int fila = 0; fila < tabla.get(0).getCeldas().size(); fila++) {
            for (int columna = 0; columna < tabla.size(); columna++) {
                Celda celda = tabla.get(columna).getCelda(fila);
                System.out.print(celda.getContenido() + "\t");
            }
            System.out.println();
        }
    }

//--METODOS UTILES--------------------------------------------------------------------------------------------------
    private int cantFilas() {
        /**
         * Returns int count of rows in the set
         */
        return tabla.get(0).size();
    }


//--GETTERS--------------------------------------------------------------------------------------------------

    /**
     * Returns the Columna with the specified key.
     *
     * @param key Label of the column
     * @return the Columna with the specified key
     * @throws IllegalArgumentException If there is no label
     */
    public Columna getColumna(String key) {

        if (colLabels.containsKey(key)) {
            return tabla.get(colLabels.get(key));
        } else {
            throw new IllegalArgumentException("No existe una columna con esa clave");
        }
    }

    /**
     * Returns the Fila with the specified key.
     *
     * @param key Label of the row
     * @return the Fila with the specified key
     * @throws IllegalArgumentException If there is no label
     */

    public Fila getFila(String key) {
        if (colLabels.containsKey(key)) {
            Fila fila = new Fila();
            
            for (Columna col : tabla){
                fila.add((Celda) col.getCelda(colLabels.get(key)).getContenido());
            }
            return fila;
        } else {
            throw new IllegalArgumentException("No hay fila con esa key");
        }
    }

    /**
     * Returns the Celda with the specified key.
     *
     * @param keyFila Label of the row
     * @param keyColumna Label of the column
     * @return the Celda with the specified keys
     * @throws IllegalArgumentException If there is no label
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

//--SETTERS--------------------------------------------------------------------------------------------------
    public void setCelda(String keyFila, String keyColumna, Object value) {
        /**
         * Reemplaza el contenido de una celda
         */
        getCelda(keyFila, keyColumna).setContenido(value);
    }

    public void setColumna(Columna newColumna, String key) {
        /**
         * Reemplaza el contenido de una columna, manteniendo el header
         */
        if (newColumna.size() == cantFilas()){
            tabla.set(colLabels.get(key), newColumna);
        } else {
            throw new LengthMismatchException("El tamaño de la columna es incorrecto");
        }
    }
    
    public void setColumna(Columna newColumna, String oldKey, String newKey) {
        /**
         * Reemplaza el contenido de una columna por una nueva, cambia el header
         */
        if (newColumna.size() == cantFilas()){
            tabla.set(colLabels.get(oldKey), newColumna);
            colLabels.put(newKey, colLabels.get(oldKey));
            colLabels.remove(oldKey);
            for(int i = 0; i < tabla.size(); i++){
                if (headers.get(i) == oldKey){
                    headers.set(i,newKey);
                }
            }
        } else {
            throw new LengthMismatchException("El tamaño de la columna es incorrecto");
        }
    }

    public void setFila(Fila fila, String key) {
        /**
         * Reemplaza el contenido de una fila por una nueva, manteniendo label 
         */
        if (fila.size() == tabla.size()){
            if (rowLabels.containsKey(key)){
                for (int i = 0; i < fila.size(); i++){
                    tabla.get(i).getCeldas().get(rowLabels.get(key)).setContenido(key);
                }
            } else {
                throw new IllegalLabelException("Este encabezado no existe");
            }
        } else {
            throw new LengthMismatchException("La fila no tiene el tamaño correcto");
        }
    }

    //TODO: setFila(Fila fila, String oldKey, String newKey)


//--MODIFIERS--------------------------------------------------------------------------------------------------
    public void addColumna(Columna nuevaCol) {
        /**
         * Agrega una nueva columna
         */
        tabla.add(nuevaCol);
        colLabels.put(String.valueOf(tabla.size()), tabla.size());
        headers.add(String.valueOf(tabla.size()));
        }

    public void addColumna(Columna nuevaCol, String label) {
        if (colLabels.containsKey(label)) {
            tabla.add(nuevaCol);
            colLabels.put(label, tabla.size());
            headers.add(label);
        }
    }

    public void addFila(Fila nuevaFila) {
        for (int i = 0; i < tabla.size(); i++) {
            tabla.get(i).addCelda(nuevaFila.getFila().get(i));
        }
    }

    public void removeColumna(String key){
        if (colLabels.containsKey(key)){
            int index = colLabels.get(key);
            tabla.remove(index);
            
            for (String header : headers){
                if (colLabels.get(header) > index){
                    colLabels.put(header, colLabels.get(header) - 1);
                }
            }
            headers.remove(index);

        } else {
            throw new IllegalLabelException("Este encabezado no existe");
        }
    }

    public void removeFila(String key){
        if (rowLabels.containsKey(key)){
            for (Columna col : tabla){
                col.removeCelda(rowLabels.get(key));

                for (String row : order){
                    if (rowLabels.get(row) > rowLabels.get(key)){
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

        /* Lo que esperamos es que salga algo asi
         #   nombreColumna     Non-Null Count   tipoDato
        ---  -------------     --------------   -------
         0   int_col            5 non-null      Number
         1   text_col           5 non-null      String
         2   col_boolean        5 non-null      boolean

         Number[] indiceColumnas = [0, 1, 2] tipo Number
         nombreColumna = [int_col, text_col, col_boolean] tipo String
         nonNullCount = [5, 5, 5] tipo Number
         tipoDato = [Number,String,boolean] tipo String
         y despues imprimir la tabla que generamos
*/
        List<String> indiceColumna = new ArrayList<>();
        List<String> nombreColumna = new ArrayList<>();
        List<String> cantidadNonNull = new ArrayList<>();

        for (int index_columna = 0; index_columna < tabla.size(); index_columna++) {
            indiceColumna.add(String.valueOf(index_columna));
        }

        for (int index_columna = 0; index_columna < tabla.size(); index_columna++) {
            nombreColumna.add(headers.get(index_columna)); /* despues se cambia para usar el metodo para darle headers */
        }

        for (int index_columna = 0; index_columna < tabla.size(); index_columna++) {
            Columna miColumna = tabla.get(index_columna); /* esta mugre fue porque Columna no es iterable? */
            int celdasCompletas = 0;
            List<Celda> miLista = miColumna.getCeldas();
                for (Celda celda: miLista) {
                    if (celda.isNA() == false) {
                        celdasCompletas ++;
                    }
                }
            cantidadNonNull.add(String.valueOf(celdasCompletas));
        }

        /* DE ACA PARA ARRIBA NO SE TOCA
         * TODO: headers */

        String[] indCol = indiceColumna.toArray(new String[0]);
        String[] nomCol = nombreColumna.toArray(new String[0]);
        String[] noNulo = cantidadNonNull.toArray(new String[0]);
        String[] tipoDeDato = {"String","String","String","String"};

        String[][] datos = {indCol, nomCol, noNulo, tiposDato};
        try {
            llenarTabla(datos, tipoDeDato);
        } catch (InvalidDataTypeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}