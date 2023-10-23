package com;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Importador implements ParseCSV {
    @Override
    public Tabla leerCSV (String fileName, String separador, boolean hasHeaders) {
        Tabla tabla = new Tabla();

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
                        //columna.addCelda(celda);
                        tabla.addColumna(columna);
                    }
                    hasHeaders = false;
                } else {
                    // Agregar filas a la tabla
                    tabla.addFila(fila);
                }

            }    

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tabla;
    }
}

