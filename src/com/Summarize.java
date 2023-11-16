package com;

public interface Summarize {
    double sum(Columna columna);
    double max(Columna columna);
    double min(Columna columna);
    int count(Columna columna);
    double mean(Columna columna);
    double variance(Columna columna);
    double standardDeviation(Columna columna);
}
