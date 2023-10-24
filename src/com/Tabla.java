package com;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Tabla implements ParseCSV {
    private List<Columna> tabla;
    // protected HashMap(String) mapa1;
    // protected HashMap(Number) mapa2;

    public Tabla(String[] tiposDato, String fileName) {
        tabla = new ArrayList<>(tiposDato.length);

        for (int i = 0; i < tiposDato.length; i++) {
            Columna columna = new Columna(i);
            tabla.add(i, columna);
        }
    }

    public Tabla leerCSV(String[] tiposDato, String fileName, String separador, boolean hasHeaders) {
        Tabla tabla = new Tabla(tiposDato, fileName);

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] valores = line.split(separador);
                //Creamos una nueva fila para cada linea
                List<Celda> fila = new ArrayList<>();

                for (String valor : valores) {
                    Celda celda = new CeldaString();
                    celda.setContenido(valor);
                    fila.add(celda);
                }

                if (hasHeaders) { //chequear esto
                    for (Celda celda : fila) {
                        Columna columna = new Columna();
                        columna.addCelda(celda);
                        tabla.addColumna(columna);
                    }
                    hasHeaders = false;
                } else {
                    // Agregar filas a la tabla
                    tabla.addFila(fila);
                }

            }    

        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabla;
    }

    public Tabla parserCSV 



    public List<Celda> getFila(int index) {
        List<Celda> fila = new ArrayList<>();
        try {
            for (Columna columna : tabla) {
                Celda celda = columna.getCelda(index);
                fila.add(celda);
            }
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();;
        }
        return fila; 
    }


    // para los hash
    public List<Celda> getFila() {

    }

    public Columna getColumna(int index) {
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

    // para los hash
    public Columna getColumna() {

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
    }
    


    public void setColumna() {

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
    }

    public boolean checkType(Columna columna) {
        for (i = 0; i < columna.size() - 1; i++) { // esta mal igual
            if (columna.get(i).getClass().equals(columna.get(i + 1).getClass())) {
                return false;
            }
        }
        return True;
    }
}
