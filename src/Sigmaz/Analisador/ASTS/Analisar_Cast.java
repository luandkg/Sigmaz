package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

public class Analisar_Cast {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Cast(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }

    public void init(AST ASTPai) {


        for (AST mAST : ASTPai.getASTS()) {


            if (mAnalisador_Bloco.getPrimitivos().contains(mAST.getValor())){

            }else{
                mAnalisador.getErros().add("CAST " + ASTPai.getNome() + " de " + mAST.getValor() + " : Impossivel !");
            }

        }


    }

}
