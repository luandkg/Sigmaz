package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

public class Analisar_Execute {

    private Analisador mAnalisador;

    public Analisar_Execute(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void analisar_Execute(AST ASTPai) {


        if (mAnalisador.getActions_Nomes().contains(ASTPai.getNome()) && ASTPai.mesmoValor("FUNCT")) {

        } else if (mAnalisador.getFunctions_Nomes().contains(ASTPai.getNome()) && ASTPai.mesmoValor("FUNCT")) {

        } else if (ASTPai.mesmoValor("STRUCT")) {

        } else {
            mAnalisador.getErros().add("ACTION " + ASTPai.getNome() + " : Nao encontrada !");

        }

    }

}
