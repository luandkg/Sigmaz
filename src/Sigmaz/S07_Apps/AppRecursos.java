package Sigmaz.S07_Apps;

import Sigmaz.S08_Utilitarios.Recursador.Recursos;

import java.awt.*;


public class AppRecursos {


    public void init(String eLocal) {

        System.out.println("");
        System.out.println("################ RECURSOS ################");
        System.out.println("");
        System.out.println("\t - Local : " + eLocal);
        System.out.println("");

        Recursos mRecursos = new Recursos();


        mRecursos.process_compiler(eLocal + "compiler.png");
        System.out.println("\t" + getEspacado(" - Processo de Compilacao", 30, " : OK"));

        mRecursos.escopo_01(eLocal + "escopo_01.png");
        System.out.println("\t" + getEspacado(" - Escopo 01", 30, " : OK"));

        mRecursos.escopo_02(eLocal + "escopo_02.png");
        System.out.println("\t" + getEspacado(" - Escopo 02", 30, " : OK"));

    }


    public String getEspacado(String eAntes, int tamanho, String eDepois) {

        String eFrase = eAntes;

        while (eFrase.length() < tamanho) {
            eFrase += " ";
        }

        eFrase +=eDepois;

        return eFrase;
    }

    public Color getColorHexa(String colorStr) {
        return new Color(
                Integer.valueOf(colorStr.substring(1, 3), 16),
                Integer.valueOf(colorStr.substring(3, 5), 16),
                Integer.valueOf(colorStr.substring(5, 7), 16));
    }


}
