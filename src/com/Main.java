package com;
import java.util.function.Predicate;

public class Main {
    public static void main(String[] args) {
        //LO QUE APARECE EN ESTE MAIN FUNCIONA PERFECTAMENTE

        Tabla booleana = new Tabla(new String[]{"Boolean", "Boolean", "Number"}, "C:/Users/Usuario/Downloads/df_booleanos.csv", true);
        //System.out.println(booleana);

        // Columna col3 = booleana.getColumna("columna3");
        // System.out.println(col3.toString());

        // Celda celda = booleana.getCelda("2", "columna3");
        // System.out.println(celda.toString());

        // Columna newCol = new Columna("String", new String[]{"hola","false","Todo","ah","h","3","!!!!","si","no","blanco","negro","ya no se","que paja","odio testear","se la banca"});
        // System.out.println(newCol);

        // booleana.setColumna(newCol, "columna1");
        // System.out.println(booleana.toString());

        // booleana.infoBasica();

        // booleana.setColumna(newCol, "columna1", "columnaNueva");
        // System.out.println(booleana.toString());

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

        Fila nuevaFila = new Fila(new Object[]{true, false, 300});
        booleana.addFila(nuevaFila);
        System.out.println(booleana);

//-------TEST GETTERS Y SETTERS
        // booleana.setFila(nuevaFila, "12", "aca");
        // booleana.setFila(nuevaFila, "10", "here");
        // booleana.setFila(nuevaFila, "0");
        // booleana.setFila(nuevaFila, "0", "j");

        // Columna newCol = new Columna("String", new String[]{"hola","false","Todo","ah","h","3","!!!!","si","no","blanco","negro","ya no se","que paja","odio testear","se la banca", "fin"});
        // booleana.setColumna(newCol, "columna1");
        // Columna newCol2 = new Columna("String", new String[]{"pene","false","Todo","ah","h","3","!!!!","si","no","blanco","negro","ya no se","que paja","odio testear","se la banca", "fin"});
        // booleana.setColumna(newCol2, "columna2");
        // Columna newCol3 = new Columna("String", new String[]{"modic","false","Todo","ah","h","3","!!!!","si","no","blanco","negro","ya no se","que paja","odio testear","se la banca", "fin"});
        // booleana.setColumna(newCol3, "columna1", "cacataa");
        // System.out.println(booleana);
        // Celda celda = new CeldaString();
        // celda.setContenido("exito");
        // booleana.setCelda("j", "cacataa", celda);
        // Celda celda2 = new CeldaNumber();
        // celda2.setContenido(1000);
        // booleana.setCelda("j", "columna3", celda2);
        // System.out.println(booleana);   

// -----SELECCIONAR
        // booleana.seleccionarFilas(new String[] {"0", "1", "2", "9", "14"});
        // System.out.println(booleana);

        // booleana.seleccionarColumnas(new String[] {"columna2", "columna3"});
        // System.out.println(booleana);

        // booleana.seleccionar(new String[] {"columna2", "columna3"}, new String[] {"0", "1", "2", "9", "14"});
        // System.out.println(booleana);
    
// --------SORT
        // booleana.sort(new String[] {"columna3"});
        // System.out.println(booleana);

// -------CONCATENAR
        // Tabla tablaConcatenada = booleana.concatenarTabla(booleana);
        // System.out.println(tablaConcatenada);

// ------- FILTRADO PRUEBA
        // Predicate<Fila> condicion1 = fila -> { 
        //     Celda celda = fila.getCelda("columna1", booleana);
        //     return celda != null & Boolean.TRUE.equals(celda.getContenido()); // La celda es true
        // };

        // Predicate<Fila> condicion2 = fila -> { 
        //     Celda celda = fila.getCelda("columna2", booleana);
        //     return celda != null & Boolean.FALSE.equals(celda.getContenido()); // La celda es false
        // };

        // Predicate<Fila> condicion3 = fila -> {
        //     Celda celda = fila.getCelda("columna3", booleana);
        //     return celda != null & celda.getContenido() instanceof Number && ((Number) celda.getContenido()).doubleValue() > 0;
        // };

        // booleana.filtrar(condicion3);
        // booleana.filtrar(condicion1.and(condicion2));
        // System.out.println(booleana);
    }
}
