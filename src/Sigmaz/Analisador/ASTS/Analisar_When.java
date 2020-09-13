package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_When {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_When(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

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
