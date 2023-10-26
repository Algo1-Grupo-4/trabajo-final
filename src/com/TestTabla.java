package com;
import java.io.IOException;


public class TestTabla {
    public static void main(String[] args) throws IOException {
        // Especifico tipos de dato
        //String[] tiposDato = {"Boolean", "Boolean"};
        //String[] tiposDato = {"String", "String"};
        //String[] tiposDato = {"Number", "Number"};
        String[] tiposDato = {"String", "Number", "Boolean"};

        // Ruta del archivo
        //String fileName = "C:\\Users\\Usuario\\Downloads\\df_booleanos.csv";
        //String fileName = "C:\\Users\\Usuario\\Downloads\\df_string.csv";
        //String fileName = "C:\\Users\\Usuario\\Downloads\\df_number.csv";
        String fileName = "C:\\Users\\Usuario\\Downloads\\df_prueba.csv";

        Tabla tabla = new Tabla(tiposDato, fileName, true);
        tabla.mostrarTabla();
    }
}   
