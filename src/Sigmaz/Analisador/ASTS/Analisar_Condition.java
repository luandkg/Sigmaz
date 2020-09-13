package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Condition {


    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Condition(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }


    public void analisarCondicao(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction, boolean laco) {



        int condition = 0;
        int other = 0;
        int others = 0;

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CONDITION")) {
                condition += 1;

                if (mAST.getValor().length() == 0) {
                    mAnalisador.getErros().add("É necessario uma condição !");
                }
            } else if (mAST.mesmoTipo("OTHER")) {
                other += 1;
                if (others > 0) {
                    mAnalisador.getErros().add("A condicao OTHERS deve ser a ultima ! !");
                }

                ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

                if (dentroFunction) {
                    for (AST sAST : mAST.getBranch("BODY").getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,laco);
                    }
                } else {
                    for (AST sAST : mAST.getBranch("BODY").getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,laco);
                    }
                }


            } else if (mAST.mesmoTipo("OTHERS")) {
                others += 1;

                ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,laco);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,laco);
                    }
                }

            } else if (mAST.mesmoTipo("BODY")) {

                ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,laco);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador_Bloco.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,laco);
                    }
                }
            }


        }

        if (condition == 0) {
            mAnalisador.getErros().add("É necessario uma condição !");
        }

        if (others > 1) {
            mAnalisador.getErros().add("Existe mais de um OTHERS na condição !");
        }

    }

}
