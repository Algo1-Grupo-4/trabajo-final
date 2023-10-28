package com;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestTabla {
    public static void main(String[] args) throws IOException {
        // links luchi, agregar los de ustedes abajo
        // Especifico tipos de dato
        // String[] tiposDato = {"Boolean", "Boolean"};
        // String[] tiposDato = {"String", "String"};
        // String[] tiposDato = {"Number", "Number"};
        String[] tiposDato = { "Number", "Boolean", "Number" };

        // Ruta del archivo
        // String fileName = "C:\\Users\\Usuario\\Downloads\\df_booleanos.csv";
        // String fileName = "C:\\Users\\Usuario\\Downloads\\df_string.csv";
        // String fileName = "C:\\Users\\Usuario\\Downloads\\df_number.csv";
        // String fileName = "C:\\Users\\Usuario\\Downloads\\df_prueba.csv";
        // System.getProperty("user.dir") te ahorra tener que escribir todo el path
        // hasta "trabajo-final"
        Path fileName = Paths.get(System.getProperty("user.dir"), "res", "df_booleanos.csv");
        // String fileName = System.getProperty("user.dir") + "/res/df_booleanos.csv";

        Tabla tabla;
        tabla = new Tabla(tiposDato, fileName.toString(), true);
        tabla.mostrarTabla();
    }
}
