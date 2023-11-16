package com;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import excepciones.InvalidDataTypeException;

public class Columna implements Cloneable {
    private List<Celda> columna;
    private String tipo;

    public Columna(String tipoDato, int lengthColumna) {
        columna = new ArrayList<>();

        // Agregar las celdas según el tipo de dato
        if (tipoDato.equals("Boolean")) {
            for (int i = 0; i < lengthColumna; i++) {
                columna.add(new CeldaBoolean());
            }
        } else if (tipoDato.equals("String")) {
            for (int i = 0; i < lengthColumna; i++) {
                columna.add(new CeldaString());
            }
        } else if (tipoDato.equals("Number")) {
            for (int i = 0; i < lengthColumna; i++) {
                columna.add(new CeldaNumber());
            }
        }
        this.tipo = tipoDato;
    }

    public Columna(String tipoDato, Object[] valores){
        //no admite datos faltantes, tiene sentido pero importante mencionarlo
        columna = new ArrayList<>();

        // Agregar las celdas según el tipo de dato
        if (tipoDato.equals("Boolean")) {
            for (int i = 0; i < valores.length; i++) {
                Celda celda = new CeldaBoolean();
                celda.setContenido(valores[i]);
                columna.add(celda);
            }
        } else if (tipoDato.equals("String")) {
            for (int i = 0; i < valores.length; i++) {
                Celda celda = new CeldaString();
                celda.setContenido(valores[i]);
                columna.add(celda);
            }
        } else if (tipoDato.equals("Number")) {
            for (int i = 0; i < valores.length; i++) {
                Celda celda = new CeldaNumber();
                celda.setContenido(valores[i]);
                columna.add(celda);
            }
        }
        this.tipo = tipoDato;
    }

    public Celda getCelda(int index) {
        if (index >= 0 && index < columna.size()) {
            return columna.get(index);
        } else {
            throw new IndexOutOfBoundsException(index);
        }
    }

    /**
     * Devuelve una lista de celdas en la columna.
     */
    public List<Celda> getCeldas() {
        return columna;
    }

    /**
     * Elimina una celda de la columna.
     */
    public void removeCelda(int index) {
        if (index >= 0 && index < columna.size()) {
            columna.remove(index);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index + ".");
        }
    }

    /**
     * Agrega una celda a la columna.
     */
    public void addCelda(Celda valor) {
        columna.add(valor);
    }

    /**
     * Devuelve el tamaño de la columna.
     */
    public int size() {
        return columna.size();
    }

    /**
     * Comprueba si todas las celdas de la columna son del mismo tipo.
     */
    public boolean sonMismosTipos() {
        Class<? extends Celda> tipoCelda = getCelda(0).getClass();
        for (Celda celda : columna) {
            if (celda == null) {
                continue;
            }
            if (!tipoCelda.isInstance(celda)) {
                return false;
            }
        }
        return true;
    }

    /**
     * LLena los valores faltantes de una columna con NA.
     */
    public void fillNA() {
        for (Celda celda : columna) {
            if (celda.isNA()) {
                celda.setContenido("NA");
            }
        }
    }

    public void ordenarColumna() throws InvalidDataTypeException {
        Comparator<Celda> comparator;
        if (this.tipo == "Number") {
            comparator = (n1, n2) -> {
                if (n1.getContenido() == null && n2.getContenido() == null) {
                    return 0; // Both are null, consider them equal
                } else if (n1.getContenido() == null) {
                    return -1; // n1 is null, consider it less than n2
                } else if (n2.getContenido() == null) {
                    return 1; // n2 is null, consider it greater than n1
                } else {
                    // Compare non-null values using doubleValue()
                    return Double.compare(
                            ((Number) n1.getContenido()).doubleValue(),
                            ((Number) n2.getContenido()).doubleValue());
                }
            };
        } else if (this.tipo == "Boolean") {
            comparator = (n1, n2) -> {
                if (n1.getContenido() == null && n2.getContenido() == null) {
                    return 0; // Both are null, consider them equal
                } else if (n1.getContenido() == null) {
                    return -1; // n1 is null, consider it less than n2
                } else if (n2.getContenido() == null) {
                    return 1; // n2 is null, consider it greater than n1
                } else {
                    // Compare non-null values using doubleValue()
                    return Boolean.compare(
                            ((Boolean) n1.getContenido()).booleanValue(),
                            ((Boolean) n2.getContenido()).booleanValue());
                }
            };
        } else if (this.tipo == "String") {
            comparator = (n1, n2) -> {
                if (n1.getContenido() == null && n2.getContenido() == null) {
                    return 0; // Both are null, consider them equal
                } else if (n1.getContenido() == null) {
                    return -1; // n1 is null, consider it less than n2
                } else if (n2.getContenido() == null) {
                    return 1; // n2 is null, consider it greater than n1
                } else {
                    // Compare non-null values using doubleValue()
                    return (n1.getContenido().toString()).compareTo(n2.getContenido().toString());
                }
            };
        } else {
            throw new InvalidDataTypeException("Tipo de dato invalido");
        }
        Collections.sort(columna, comparator);

    }

    public void ordenarColumna(Comparator<Celda> comparator) {
        columna.sort(comparator);
    }

    @Override
    public String toString() {
        return "Tipo de dato " + tipo + "\nValores: " + columna + "\n";
    }

    @Override
    protected Object clone() {
        Columna c = new Columna(this.tipo, this.columna.size());
        List<Celda> nc = new ArrayList<>();
        c.tipo = this.tipo;
        for (Celda cell : this.columna) {
            try {
                nc.add((Celda) cell.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                // TODO: fix error message
                System.out.println("Error al clonar celda");
            }
        }
        c.columna = nc;
        return c;
    }

}