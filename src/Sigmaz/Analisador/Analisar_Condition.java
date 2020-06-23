package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Condition {


    private Analisador mAnalisador;

    public Analisar_Condition(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

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
                        mAnalisador.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,laco);
                    }
                } else {
                    for (AST sAST : mAST.getBranch("BODY").getASTS()) {
                        mAnalisador.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,laco);
                    }
                }


            } else if (mAST.mesmoTipo("OTHERS")) {
                others += 1;

                ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,laco);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,laco);
                    }
                }

            } else if (mAST.mesmoTipo("BODY")) {

                ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Function().analisarDentroFunction(sAST,mAlocados,laco);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Action().analisarDentroAction(sAST,mAlocados,laco);
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
