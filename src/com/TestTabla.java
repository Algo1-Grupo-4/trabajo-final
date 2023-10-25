package com;
import java.io.IOException;


public class TestTabla {

    public static void main(String[] args) throws IOException {
        // Especifico tipos de dato
        String[] tiposDato = {"Boolean", "Boolean"};

        // Ruta del archivo
        String fileName = "C:/Users/naiar/Downloads/df_booleanos.csv";

        Tabla tabla = new Tabla(tiposDato, fileName);
        // Mostrar una fila de la tabla
        System.out.println(tabla.getFila(0));
    }
}
    

    
