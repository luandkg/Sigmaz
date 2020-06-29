package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

public class Analisar_Cast {

    private Analisador mAnalisador;

    public Analisar_Cast(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void init(AST ASTPai) {


        for (AST mAST : ASTPai.getASTS()) {


            if (mAnalisador.getPrimitivos().contains(mAST.getValor())){

            }else{
                mAnalisador.getErros().add("CAST " + ASTPai.getNome() + " de " + mAST.getValor() + " : Impossivel !");
            }

        }


    }

}
