package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tabla {
    private List<Columna> tabla;

    public Tabla(String[] tiposDato, String fileName) {
        List<String> lineas = null;
        try {
            lineas = leerCSV(fileName, ",", false);
            String[][] datos = parserCSV(lineas);
    
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    public List<String> leerCSV (String fileName, String separador, boolean hasHeaders) throws IOException{
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

    public String[][] parserCSV(List<String> lineas) {
        int filas = lineas.size();
        String [][] datos = null;
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            String[] campos = linea.split(",");
            if (datos == null) {
                datos = new String[filas][campos.length];
            }
            if (datos[0].length != campos.length) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < campos.length; j++) {
                datos[i][j] = campos[j]; 
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
    
                if (tiposDato[index_columna].equals("Boolean")) {
                    // Convierte el valor a boolean y lo asigna a la celda
                    boolean booleanValue = Boolean.parseBoolean(valor);
                    celda.setContenido(booleanValue);
                } else if (tiposDato[index_columna].equals("Number")) {
                    // Convierte el valor a número y lo asigna a la celda
                    try {
                        double numberValue = Double.parseDouble(valor);
                        celda.setContenido(numberValue);
                    } catch (NumberFormatException e) {
                        // Maneja la excepción si el valor no es un número
                        celda.setContenido(null);
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
        for (Columna columna : tabla) {
            Celda celda = columna.getCelda(index);
            fila.add((Celda) celda.getContenido());
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

    public Celda getCelda(int fila, int columna) {
        if (fila >= 0 && fila < tabla.get(0).getCeldas().size() && columna >= 0 && columna < tabla.size()) {
            Columna col = tabla.get(columna);
            return col.getCelda(fila);
        } else {
            throw new IndexOutOfBoundsException("Fila: " + fila + ", Columna: " + columna);
        }
    }
    
    
    /*public void setColumna() {

    }

    public void setCelda() {

    }

    public void setFila() {

    }

    public void addColumna(Columna nuevaCol) {

    }

    public void addFila(List<Celda> nuevaFila) {

    }

    public void removeColumna() {
        // uno con index y otro con el Map
    }

    public void removeFila() {
        // uno por index y otros por map
    }*/

}