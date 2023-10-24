package com;

public interface ParseCSV {
    public Tabla leerCSV(String[] tiposDato, String fileName, String separador, boolean hasHeaders);
}




public Tabla(String[] tiposDato, String fileName) {
        tabla = new ArrayList<>(tiposDato.length);

        for (int i = 0; i < tiposDato.length; i++) {
            Columna columna = new Columna(i);
            tabla.add(i, columna);
        }
    }



