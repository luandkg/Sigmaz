package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_When {

    private Analisador mAnalisador;

    public Analisar_When(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void analisar_When(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        int outros = 0;

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("OTHERS")) {
                outros += 1;

            }

        }

        if(outros>1){
            mAnalisador.getErros().add("Problema com WHEN : So pode existir um OTHERS !");
        }


    }


}
