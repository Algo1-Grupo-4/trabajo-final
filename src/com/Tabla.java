package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tabla {
    private List<Columna> tabla;
    // protected HashMap(String) mapa1;
    // protected HashMap(Number) mapa2;

    public Tabla(String[] tiposDato, String fileName) {
        tabla = new ArrayList<>(tiposDato.length);

        for (String tipoDato : tiposDato) {
            Columna columna = new Columna(tipoDato, tiposDato.length);
            tabla.add(columna);
        }

        // Llenamos tabla con los datos del archivo CSV
        List<String> lineas = null;
        try {
            lineas = leerCSV(fileName, ",", false);
            String[][] datos = parserCSV(lineas);
            llenarTabla(datos, tiposDato);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void llenarTabla(String[][] datos, String[] tiposDato) {
        /*if (tiposDato.length != datos.length) { //no es datos.length
            throw new IllegalArgumentException("La cantidad de columnas no coincide con la tabla.");
        }*/
        for (int fila = 0; fila < datos[0].length; fila++) {
            for (int columna = 0; columna < tabla.size(); columna++) {
                String tipoDato = tiposDato[columna];
                Celda celda = crearCelda(tipoDato, datos[columna][fila]);
                tabla.get(columna).getCelda(fila).setContenido(celda);

            }
            
        }
        
    }
    

    public Celda crearCelda (String tipoDato, String valor) {

        if (tipoDato.equals("Boolean")) {
            CeldaBoolean celdaBoolean = new CeldaBoolean();
            celdaBoolean.setContenido(Boolean.parseBoolean(valor));
            return celdaBoolean;
        } else if (tipoDato.equals("String")) {
            CeldaString celdaString = new CeldaString();
            celdaString.setContenido(valor);
            return celdaString;
        } else if (tipoDato.equals("Number")) {
            CeldaNumber celdaNumber = new CeldaNumber();
            celdaNumber.setContenido(Double.parseDouble(valor));
            return celdaNumber;
        } else {
            throw new IllegalArgumentException("Tipo de dato no válido: " + tipoDato);
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
        String [][] celdas = null;
        for (int i = 0; i < lineas.size(); i++) {
            String linea = lineas.get(i);
            String[] campos = linea.split(",");
            if (celdas == null) {
                celdas = new String[filas][campos.length];
            }
            if (celdas[0].length != campos.length) {
                throw new IllegalArgumentException();
            }
            for (int j = 0; j < campos.length; j++) {
                celdas[i][j] = campos[j]; 
            }
        }
        return celdas;
    }

    public static void mostrarCeldas(String[][] celdas ) {
        String separador = " | ";
        for (String[] fila : celdas) {
            for (String celda : fila) {
                System.out.print(celda + separador);
            }
            System.out.println("");
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

    /*public Columna getColumna(int index) {
        Columna columna = new Columna();
        try {
            for (Columna columna : tabla) {
                Celda celda = columna.getCelda(index);
                fila.add(celda);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();;
        }
        return columna; 
    }

    public Celda getCelda(int index_i, int index_j) {
        Celda celda = new Celda();
        try {
            for (Columna columna : tabla) {
                celda = columna.getCelda([index_i][index_j]);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();;
    }
    
    return celda; 
    }*/
    


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
