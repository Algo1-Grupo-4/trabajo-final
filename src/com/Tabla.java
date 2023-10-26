package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Tabla {
    private List<Columna> tabla;
    private List<String> headers;
    private Map<String, Integer> colLabels;
    private Map<String, Integer> rowLabels;

    public Tabla(String[] tiposDato, String fileName, boolean hasHeaders) {
        List<String> lineas = null;
        colLabels = new HashMap<>();
        rowLabels = new HashMap<>();

        try {
            lineas = leerCSV(fileName, hasHeaders);

            if (hasHeaders){
                if (!lineas.isEmpty()){
                    String[] encabezados = lineas.get(0).split(",");
                    List<String> headers = new ArrayList<>();

                    for (int j = 0; j < encabezados.length ; j++){
                        headers.add(encabezados[j]);
                        colLabels.put(headers.get(j), j);
                    }
                    lineas.remove(0);
                    this.headers = headers;
                }
            } else {
                List<String> headers = new ArrayList<>();
                for (int j = 0; j < lineas.get(0).split(",").length ; j++){
                        colLabels.put(String.valueOf(j), j);
                        headers.add( String.valueOf(j));
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

            for (int k = 0; k < tabla.get(0).size(); k++){
                rowLabels.put(String.valueOf(k), k);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public List<String> leerCSV (String fileName, boolean hasHeaders) throws IOException{ 
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
        String linea;
        List<String> lineas = new LinkedList<>();
        while ((linea = br.readLine()) != null) {
            lineas.add(linea);
        }
        return lineas;
        } catch (IOException e) {
            throw e;
        }
    }

    public String[][] parserCSV(List<String> lineas, int cantColumnas, String separador) { 
        int filas = lineas.size();
        String [][] datos = null;
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            String[] campos = linea.split(separador); 
            if (datos == null) {
                datos = new String[filas][campos.length];
            }
            for (int j = 0; j < campos.length; j++) {
                if (campos[j] != ""){
                    datos[i][j] = campos[j]; 
                } 
            }
        }
        return datos;
    }

    public void llenarTabla(String[][] datos, String[] tiposDato) {
        if (tabla.size() != tiposDato.length) {
            throw new IllegalArgumentException("Los datos y los tipos de dato no coinciden.");
        }
        for (int index_columna = 0; index_columna < tiposDato.length; index_columna++) {
            Columna columna = getColumna(index_columna);
            for (int index_fila = 0; index_fila < datos.length; index_fila++) {
                Celda celda = columna.getCelda(index_fila);
                String valor = datos[index_fila][index_columna];
    
                if (valor != null && tiposDato[index_columna].equals("Boolean")) {
                    // Convierte el valor a boolean y lo asigna a la celda
                    boolean booleanValue = Boolean.parseBoolean(valor);
                    celda.setContenido(booleanValue);
                    
                } else if (tiposDato[index_columna].equals("Number")) {
                    if (valor != null){
                        if (valor.contains(".")){
                            try {
                                Double valorDouble = Double.parseDouble(valor);
                                celda.setContenido(valorDouble);                            
                            } catch (NumberFormatException e){
                            } 
                        } else {
                            try {
                                Integer valorInteger = Integer.parseInt(valor);
                                celda.setContenido(valorInteger);
                            } catch (NumberFormatException e){
                            } 
                        }
                    }

                } else if (tiposDato[index_columna].equals("String")) {
                    // Asigna el valor directamente a la celda
                    celda.setContenido(valor);
                }
            }
        }
    }

    public void mostrarTabla() {
        for (int fila = 0; fila < tabla.get(0).getCeldas().size(); fila++) {
            for (int columna = 0; columna < tabla.size(); columna++) {
                Celda celda = tabla.get(columna).getCelda(fila);
                System.out.print(celda.getContenido() + "\t");
            }
            System.out.println();
        }
    }    

    public List<Celda> getFila(int index) {
        List<Celda> fila = new ArrayList<>();
        if (index >= 0 && index < cantFilas()){
           for (Columna columna : tabla) {
            Celda celda = columna.getCelda(index);
            fila.add((Celda) celda.getContenido());
            }
        } else {
            throw new IndexOutOfBoundsException("No hay una fila con ese indice");
        }
        return fila; 
    }

    public List<Celda> getFila(String key) {
        List<Celda> fila = new ArrayList<>();
        if (rowLabels.containsKey(key)){
            int index = rowLabels.get(key);
            for (int i = 0; i < tabla.size(); i++) {
                Celda celda = tabla.get(i).getCelda(index);
                fila.add((Celda) celda.getContenido());
            }
        }
        return fila; 
    }

    public Columna getColumna(int index) {
        if (index >= 0 && index < tabla.size()) {
            return tabla.get(index);
        } else {
            throw new IndexOutOfBoundsException("Columna: " + index);
        }
    }

    public Columna getColumna(String key) {
        if (colLabels.containsKey(key)){
            return tabla.get(colLabels.get(key));
        } else {
            throw new IllegalArgumentException("No existe una columna con esa clave");
        }
    }

    public Celda getCelda(int fila, int columna) {
        if (fila >= 0 && fila < cantFilas() && columna >= 0 && columna < tabla.size()) {
            Columna col = tabla.get(columna);
            return col.getCelda(fila);
        } else {
            throw new IndexOutOfBoundsException("Fila: " + fila + ", Columna: " + columna);
        }
    }

    public Celda getCelda(int fila, String keyColumna) {
        if (fila >= 0 && fila < cantFilas()){
            if (colLabels.containsKey(keyColumna)){
              return tabla.get(colLabels.get(keyColumna)).getCelda(fila);  
            } else {
                throw new IllegalArgumentException("La columna no existe");
            }
        } else {
            throw new IndexOutOfBoundsException("la fila no existe");
        }
    }

    public Celda getCelda(String keyFila, int columna) {
        if (columna >= 0 && columna < tabla.size()){
            if (rowLabels.containsKey(keyFila)){
              return tabla.get(columna).getCelda(rowLabels.get(keyFila));  
            } else {
                throw new IllegalArgumentException("La fila no existe");
            }
        } else {
            throw new IndexOutOfBoundsException("la columna no existe");
        }
    }

    public Celda getCelda(String keyFila, String keyColumna) {
        if (colLabels.containsKey(keyColumna)){
            if (rowLabels.containsKey(keyFila)){
                return tabla.get(colLabels.get(keyColumna)).getCelda(rowLabels.get(keyFila));
            } else {
                throw new IllegalArgumentException("No existe esa fila");
            }
        } else {
            throw new IllegalArgumentException("No existe esa Columna");
        }
    }
    
    private int cantFilas(){
        return tabla.get(0).size();
    }

// reemplazar columna
    public void setColumna(Columna newColumna, int index, String header) {
        if (newColumna.size() != tabla.get(0).size()){
            throw new IllegalArgumentException("La nueva columna debe tener la misma longitud que las columnas existentes.");
        } 
        
        if (index < tabla.size() && index > 0){
            if (newColumna.isColumna()){
                tabla.set(index, newColumna);
                colLabels.remove(headers.get(index));
                headers.set(index, header);
                colLabels.put(header,index);

            } else {
                throw new IllegalArgumentException("Para ser una columna debe contener celdas del mismo tipo");
            }

        } else {
            throw new IndexOutOfBoundsException("No existe una columna con ese indice");
        }
    }

    public void setColumna(Columna newColumna, int index) {
        setColumna(newColumna, index, String.valueOf(index));
    }

    public void setColumna(Columna newColumna, String clave, String newClave) {
        int index =  colLabels.get(clave);
        setColumna(newColumna, index, newClave);
    }

    public void setColumna(Columna newColumna, String clave) {
        int index =  colLabels.get(clave);
        setColumna(newColumna, index, String.valueOf(index));
    }

// reemplazar celda
    public void setCelda(int fila, int columna, Object value) {
        getCelda(fila, columna).setContenido(value);
    }

    public void setCelda(int fila, String keyColumna, Object value) {
        getCelda(fila, keyColumna).setContenido(value);
    }

    public void setCelda(String keyFila, int columna, Object value) {
        getCelda(keyFila, columna).setContenido(value);
    }

    public void setCelda(String keyFila, String keyColumna, Object value) {
        getCelda(keyFila, keyColumna).setContenido(value);
    }

// reemplazar fila

    public void setFila(List<Celda> fila, int index) {
        if (index > 0 && index < cantFilas()){
            if (fila.size() == tabla.size()){
                for (int i = 0; i < tabla.size(); i++){
                    try {
                        setCelda(index, i, fila.get(i));
                    } catch (IllegalArgumentException e) {
                        // no se como mas armarlo
                    }
                }
            }else {
                throw new IllegalArgumentException("La fila no tiene el tamaño de la tabla");
            }
        } else {
            throw new IndexOutOfBoundsException("No hay una fila con ese indice");
        }
    }


    // public void addColumna(Columna nuevaCol) {

    // }

    // public void addFila(List<Celda> nuevaFila) {

    // }

    public void removeColumna(int index) {
        if (index >= 0 && index < tabla.size()){
            tabla.remove(index);
            colLabels.remove(headers.get(index));

            for (String key : headers){
                if (colLabels.get(key) > index){
                    colLabels.put(key, colLabels.get(key)-1);
                }
            }
        }else {
            throw new IndexOutOfBoundsException("No existe columna con ese indice");
        }
    }

    public void removeColumna(String keyHeader){
        if (colLabels.containsKey(keyHeader)){
            int index = colLabels.get(keyHeader);
            removeColumna(index);

        } else {
            throw new IllegalArgumentException("No existe una columna con ese encabezado");
        }
    }

    public void removeFila(int index) {
        if (index >= 0 && index < cantFilas()){

            for (int i = 0; i < tabla.size(); i++){
                tabla.get(i).removeCelda(index);
            }
            rowLabels.remove(String.valueOf(index));

            for (String key : rowLabels.keySet()){
                if (rowLabels.get(key) > index){
                    rowLabels.put(key, rowLabels.get(key) - 1);
                }
            }

        } else {
            throw new IndexOutOfBoundsException("No hay una fila con ese indice");
        } 
    }

    public void removeFila(String key){
        if (rowLabels.containsKey(key)){
            int index = rowLabels.get(key);

            removeFila(index);

        } else {
            throw new IllegalArgumentException("No hay una fila con esa clave");
        }

    }
}