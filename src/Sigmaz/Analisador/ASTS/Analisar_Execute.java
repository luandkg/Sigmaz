package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

public class Analisar_Execute {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Execute(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;
    }

    public void analisar_Execute(AST ASTPai) {


        if (mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().contains(ASTPai.getNome()) && ASTPai.mesmoValor("FUNCT")) {

        } else if (mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().contains(ASTPai.getNome()) && ASTPai.mesmoValor("FUNCT")) {

        } else if (ASTPai.mesmoValor("STRUCT")) {

        } else if (ASTPai.mesmoValor("STRUCT_EXTERN")) {

        } else {
          //  mAnalisador.getErros().add(mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().toString());

            mAnalisador.getErros().add("ACTION " + ASTPai.getNome() + " : Nao encontrada !");

        }

    }

}
