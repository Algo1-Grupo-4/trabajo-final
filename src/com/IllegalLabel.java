package com;

public class IllegalLabel extends RuntimeException{
    public IllegalLabel(){
        super("Ya existe ese encabezado");
    }
}
