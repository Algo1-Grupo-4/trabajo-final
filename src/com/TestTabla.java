package com;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestTabla {
    public static void main(String[] args) throws IOException {
        String[] tiposDato = { "Boolean", "String", "Number" };

        // System.getProperty("user.dir") te ahorra tener que escribir todo el path
        // hasta "trabajo-final" (o hasta una anterior)
        // Si usamos esto nos evitamos poner tantas \ o / (y haecemos que no importe el
        // SO)
        Path fileName = Paths.get(System.getProperty("user.dir"), "res", "df_col1.csv");
        // String fileName = System.getProperty("user.dir") + "/res/df_booleanos.csv";

        Tabla tabla;
        tabla = new Tabla(tiposDato, fileName.toString(), true);
        tabla.mostrarTabla();
    }
}
