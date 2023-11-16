package com;

public class Main {
    public static void main(String[] args) {
        //LO QUE APARECE EN ESTE MAIN FUNCIONA PERFECTAMENTE

        Tabla booleana = new Tabla(new String[]{"Boolean", "Boolean", "Number"}, "C:/Users/Usuario/Downloads/df_booleanos.csv", true);
        System.out.println(booleana.toString());

        Columna col3 = booleana.getColumna("columna3");
        System.out.println(col3.toString());

        Celda celda = booleana.getCelda("2", "columna3");
        System.out.println(celda.toString());

        Columna newCol = new Columna("String", new String[]{"hola","false","Todo","ah","h","3","!!!!","si","no","blanco","negro","ya no se","que paja","odio testear","se la banca"});
        System.out.println(newCol);

        // booleana.setColumna(newCol, "columna1");
        // System.out.println(booleana.toString());

        booleana.setColumna(newCol, "columna1", "columnaNueva");
        System.out.println(booleana.toString());

        // Fila newFila = new Fila(new Object[]{true,true,2});
        // System.out.println(newFila);

        // booleana.setFila(newFila, "8");
        // System.out.println(booleana.toString());

        // booleana.setCelda("0", "columnaNueva", "test");
        // System.out.println(booleana.toString());

        // Columna newCol2 = new Columna("String", new String[]{"hola","false","Todo","ah","h","3","!!!!","si","no","blanco","negro","ya no se","que paja","odio testear","se la banca"});
        // booleana.addColumna(newCol2, "columna4");
        // System.out.println(booleana);

        // booleana.removeColumna("columna");
        // Fila fila = new Fila(new Object[]{"buenasss", true, 22, "wow"});
        // booleana.addFila(fila);
        // System.out.println(booleana);

        // booleana.removeFila("15");
        // System.out.println(booleana);

    }
}
