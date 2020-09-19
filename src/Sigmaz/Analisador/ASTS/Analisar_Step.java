package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;

import java.util.ArrayList;
import Sigmaz.Utils.AST;

public class Analisar_Step {

    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Step(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }

    public void analisarStepDef(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        mAnalisador.analisarAlocacao(ASTPai, mAlocados);

        analisarStep(ASTPai,mAlocados,dentroFunction);
    }

    public void analisarStep(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        int a = 0;

        for (AST sAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (sAST.mesmoTipo("ARGUMENT")) {
                a += 1;
            }
        }

        if (a == 3) {

        } else {
            mAnalisador.getErros().add("O Step deve ser formado por 3 ARGUMENTS !");
        }

        if (dentroFunction) {
            for (AST sAST : ASTPai.getBranch("BODY").getASTS()) {
                mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,true);
            }
        } else {
            for (AST sAST : ASTPai.getBranch("BODY").getASTS()) {
                mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,true);
            }
        }


    }

}
