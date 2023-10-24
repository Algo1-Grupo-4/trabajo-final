package com;

public class TestCSVReader {
    public static void main(String[] args) {
   
        String fileName = "C:/Users/naiar/Downloads/df_booleanos.csv";
        String separador = ",";
        boolean hasHeaders = false; // Ajusta esto según tu archivo CSV

        try {
            Importador importador = new Importador();
            Tabla tabla = importador.leerCSV(fileName, separador, hasHeaders);
            System.out.println(tabla.getFila(0));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
    

    
