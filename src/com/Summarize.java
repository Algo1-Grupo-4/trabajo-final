package com;

public interface Summarize {
    double sum(Columna columna);

    double max(Columna columna);

    double min(Columna columna);

    int count(Columna columna);

    double media(Columna columna);

    double varianza(Columna columna);

    double desvioEstandar(Columna columna);
}
