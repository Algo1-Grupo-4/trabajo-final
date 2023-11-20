package com;

import java.util.function.Predicate;
import java.util.Scanner;

public class Main {
        private static void showSlide(String slide, Scanner s) {
                System.out.println(slide);
                System.out.println("\nMandale enter.");
                s.nextLine();
        }

        public static void main(String[] args) {
                Scanner user = new Scanner(System.in);

                showSlide("Hola. Este es el demo del TP de grupo 4."
                                + "\nLa idea es ir mostrando funcionalidades mientras vendemos."
                                + "\nDespués de mostrar cada cosa esperamos un enter para seguir"
                                + "\n¡Hablame de interactividad!", user);

                showSlide("Bueno ahora cargamos los datos desde un CSV" +
                                "\nPresenter notes: mostrar el fromCSV()", user);

                Tabla booleana = Tablas.fromCSV(
                                new String[] { "Number", "String", "Number", "Number", "Boolean", "String", "Number" },
                                "/Users/ngiorgetti/repos/personal/trabajo-final/res/seguros.csv", true);
                System.out.println(booleana);

                showSlide("El comportamiento del toString de la clase Tabla detecta que"
                                + "\nDe ser muchas filas, va a 'cortar' el medio para mejorar la visibilidad", user);

                showSlide("Ahora brevemente mostramos la importacion desde un Array de Arrays", user);

                String[][] example = { { "Nombre", "Edad" }, { "Nico", "35" }, { "Iva", "35" } };
                Tabla e = Tablas.fromMatriz(new String[] { "String", "Number" }, example, true);
                System.out.println(e);

                showSlide("Ahora volvamos con la tabla importada desde un CSV", user);
                showSlide("Como vimos, si hacemos un sysout de la Tabla, usa el metodo to String" +
                                "\nPero si quisieramos ver info básica" +
                                " de la tabla podemos usar infobásica", user);
                booleana.infoBasica();
                showSlide("Bueno, y si ahora queremos exportar esto a un csv?" +
                                "\nPresenter notes: bueno ahora mostrar el toCSV()", user);

                System.out.println("Dame un delimitador!");
                String deli = user.nextLine();

                Tablas.toCSV(booleana, "./output.csv", true, deli);
                showSlide("\nBueno muy lindo todo, ahora probemos Getters", user);

                Columna col3 = booleana.getColumna("smoker");
                System.out.println(col3.toString());
                showSlide("Aca probamos un getColumna con 'dame columna smoker'" +
                                "\nEsto nos devuelve un objeto Columna, que podemos mostrar en pantalla usando" +
                                " toString" +
                                "\nVamos a tener el Tipo de Columna y los valores", user);

                Fila fila5 = booleana.getFila("4");
                System.out.println(fila5.toString());
                showSlide("Acá probamos el getFila" +
                                "\n El ToString nos muestra sus valores" +
                                "\n Si quisieramos podemos hacer getCeldas()" +
                                "\nY obtener las Celdas en una lista", user);

                Celda celda = booleana.getCelda("2", "children");
                System.out.println(celda.toString());
                showSlide("Acá probamos un getCelda, donde le pasamos una fila y una columna" +
                                "\nY nos da el contenido\n" +
                                "Bueno muy lindo todo.\nAhora vemos los setters", user);

                Columna newCol = new Columna("String",
                                new String[] { "Luchi", "Nai" });
                e.setColumna(newCol, "Nombre");
                System.out.println(e.toString());
                showSlide("Acá cambiamos los valores de la columna 1 con el" +
                                " setColumna\nEl set también permite cambiar la key de la columna", user);
                e.setColumna(newCol, "Nombre", "Swifties");
                System.out.println(e.toString());

                Fila newFila = new Fila(new Object[] { "Cami", "21" });
                e.setFila(newFila, String.valueOf(e.cantFilas() - 1));
                System.out.println(e.toString());
                showSlide("\nAca cambiamos una fila", user);

                Celda c = new CeldaString();
                c.setContenido("Nico");
                e.setCelda("0", "Swifties", c);
                System.out.println(e.toString());
                showSlide("\nAca cambiamos una celda", user);

                showSlide("\nBueno hasta ahora esperemos que ande bien" +
                                "\nCualquier problema fue porque el Scrum Lead no hizo un buen laburo" +
                                "\nVeamos de agregar una columna", user);

                Columna newCol2 = new Columna("Boolean", new Boolean[] { false, false });
                e.addColumna(newCol2, "Fue al recital? ");
                System.out.println(e);
                showSlide("\nVeamos ahora de sacar la columna edad", user);
                e.removeColumna("Edad");
                System.out.println(e);
                showSlide("Aver como es la info de tabla...", user);
                e.infoBasica();
                showSlide("Agregamos una fila?", user);
                Fila fila = new Fila(new Object[] { "Luchi", true });
                e.addFila(fila);
                System.out.println(e);
                showSlide("Nico no es swiftie (no le digan a nadie)" +
                                "\nVamos a sacarlo del grupo :(", user);
                e.removeFila("0");
                System.out.println(e);
                showSlide("Volviendo a la tabla importada del CSV, podemos hacer heads y tails", user);
                booleana.head(30);
                showSlide("^^^ esto fue un head de 30. Si no ponemos un valor nos da las primeras 10", user);
                booleana.tail(5);
                showSlide("^^^^ Esto fue un tail de 5", user);

                showSlide("\nProbamos el SELECT\nPrimero, el select de FILA", user);
                Tabla t = booleana.seleccionarFilas(new String[] { "4", "0", "2", "29", "1337" });
                System.out.println(t);
                showSlide("Aca mostramos las filas 4,0,2,29 y 1337 (leet!)\n" +
                                "Ahora veamos el de Columnas", user);

                t = booleana.seleccionarColumnas(new String[] { "sex", "region", "charges" });
                t.head();
                showSlide("Aca vemos un head de las columnas seleccionadas, sex, region y charges" +
                                "\nPor último, un select de las dos (filas y columnas)", user);

                t = booleana.seleccionar(new String[] { "smoker", "charges" },
                                new String[] { "0", "1", "666", "667", "777" });
                System.out.println(t);
                showSlide("\n Aca arriba vemos un select de dos columnas y un par de filas" +
                                "\nPiola.\nAhora, supongamos que quisieramos ordenar por age y charges la tabla", user);
                t = booleana.sort(new String[] { "age", "charges" });
                System.out.println(t);
                showSlide("Un sort por age y luego por charges" +
                                "Bueno, ahora a concatenar", user);
                Tabla toAdd = Tablas.fromCSV(
                                new String[] { "Number", "String", "Number", "Number", "Boolean", "String", "Number" },
                                "/Users/ngiorgetti/repos/personal/trabajo-final/res/concatenate.csv", true);
                Tabla tablaConcatenada = booleana.concatenarTabla(toAdd);
                System.out.println(tablaConcatenada);
                tablaConcatenada.infoBasica();
                showSlide("Una tabla concatenada se ve así ahora\n" +
                                "Vamos a ver Filtrado", user);

                // Predicate<Fila> condicion3 = fila1 -> {
                // Celda celda1 = fila1.getCelda("bmi", booleana);
                // return celda1 != null & celda.getContenido() instanceof Number
                // && ((Number) celda1.getContenido()).doubleValue() > 0;
                // };
                t = booleana.filtrar(TablaUtils.moreThan(booleana, "bmi", 30));
                System.out.println(t);
                showSlide("Aca filtramos BMI > 30", user);
                // Tabla t = booleana.filtrar(condicion3.and(condicion2));
                // t = booleana.filtrar(condicion1.and(condicion2));
                // System.out.println(t);
                showSlide("\nPara buscar un sample de la tabla", user);
                t = booleana.sample(40);
                System.out.println(t);

                showSlide("\nY ahora un summarize de una columna", user);
                TablaUtils.summarize(booleana, "bmi");
        }
}
