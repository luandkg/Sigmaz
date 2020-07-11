package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Try {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Try(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }

    public void init(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("BODY")) {


                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST, mAlocadosAntes, true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST, mAlocadosAntes, true);
                    }
                }


            }
        }


    }
}

