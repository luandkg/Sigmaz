package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Loop {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Loop(Analisador eAnalisador, Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco = eAnalisador_Bloco;

    }

    public void analisarLoop(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("BODY")) {
                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST, mAlocados, true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST, mAlocados, true);
                    }
                }
            }

        }
    }

}
