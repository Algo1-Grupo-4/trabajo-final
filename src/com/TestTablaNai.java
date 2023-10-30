package com;
import java.io.IOException;


public class TestTablaNai {
    public static void main(String[] args) throws IOException {
        String[] tiposDato_df1 = {"Boolean", "Boolean", "Boolean"};
        //String[] tiposDato_df2 = {"Boolean", "Boolean"};
        //String[] tiposDato_df3 = {"Number", "Number"};
        //String[] tiposDato_df4 = {"String", "Number", "Boolean"};
        //String[] tiposDato_df5 = {"String", "String"};

        String df1_booleano_comp = "C:/Users/naiar/Downloads/df_booleanos_completo.csv";
        //String df2_booleano = "C:/Users/naiar/Downloads/df_booleanos.csv";
        //String df3_number = "C:/Users/naiar/Downloads/df_number.csv";
        //String df4_mixto = "C:/Users/naiar/Downloads/df_prueba.csv";
        //String df5_raro = "C:/Users/naiar/Downloads/df_string.csv";
    
        Tabla tabla = new Tabla(tiposDato_df1, df1_booleano_comp, false);
        //Tabla tabla = new Tabla(tiposDato_df2, df2_booleano, true);
        //Tabla tabla = new Tabla(tiposDato_df3, df3_number, true);
        //Tabla tabla = new Tabla(tiposDato_df4, df4_mixto, true);
        //Tabla tabla = new Tabla(tiposDato_df5, df5_raro, true);
        tabla.mostrarTabla();
        tabla.getFila(2);
    }
}   
