package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_While {

    private Analisador mAnalisador;

    public Analisar_While(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

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
                        mAnalisador.analisarDentroFunction(sAST,mAlocados,true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.analisarDentroAction(sAST,mAlocados,true);
                    }
                }
            }

        }
    }

}
