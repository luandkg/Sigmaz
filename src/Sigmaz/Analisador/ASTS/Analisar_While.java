package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_While {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_While(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }

    public void analisarWhile(AST ASTPai, ArrayList<String> mAlocadosAntes,boolean dentroFunction) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("CONDITION")) {

                if (mAST.getValor().length() == 0) {
                    mAnalisador.getErros().add("É necessario uma condição !");
                }

            } else if (mAST.mesmoTipo("BODY")) {
                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,true);
                    }
                }
            }

        }
    }

}
