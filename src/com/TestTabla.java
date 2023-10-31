package com;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestTabla {
  public static void main(String[] args) throws IOException {
    String[] tiposDato = { "String", "Number" };

    // System.getProperty("user.dir") te ahorra tener que escribir todo el path
    // hasta "trabajo-final" (o hasta una anterior)
    // Si usamos esto nos evitamos poner tantas \ o / (y haecemos que no importe el
    // SO)
    Path fileName = Paths.get(System.getProperty("user.dir"), "res", "df_string.csv");
    // String fileName = System.getProperty("user.dir") + "/res/df_booleanos.csv";

    // Tabla tabla;
    // tabla = new Tabla(tiposDato, fileName.toString(), true);

    Tabla tabla;
    tabla = new Tabla(tiposDato, fileName.toString(), true);
    // List<List<String>> example;
    // example = new ArrayList<>();
    // String[][] a = { { "columna1", "columna2" }, { "true", "Nico" }, { "false",
    // "Iva" } };
    // for (String[] r : a) {
    // example.add(Arrays.asList(r));
    // }
    // tabla = new Tabla(tiposDato, example, true);
    // System.out.println(tabla);
    // tabla.mostrarTabla();
    // tabla.exportar("./hola.csv", true, ",");
    // Columna miColumna = tabla.getColumna("cadena");
    // System.out.println(miColumna.sonMismosTipos());
    // System.out.println(miColumna.getCeldas());
    System.out.println(tabla);
    tabla.exportar("./hola.csv", false, null);
  }
}
