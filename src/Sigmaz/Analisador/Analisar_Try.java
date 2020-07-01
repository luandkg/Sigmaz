package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Try {

    private Analisador mAnalisador;

    public Analisar_Try(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void init(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("BODY")) {


                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Function().analisarDentroFunction(sAST, mAlocadosAntes, true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Action().analisarDentroAction(sAST, mAlocadosAntes, true);
                    }
                }


            }
        }


    }
}

