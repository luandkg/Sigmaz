package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Daz {

    private Analisador mAnalisador;

    public Analisar_Daz(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void condicao(AST ASTPai) {

        if (ASTPai.getValor().length() == 0) {
            mAnalisador.getErros().add("É necessario uma condição para DAZ !");
        }

    }

    public void analisar_All_Item(AST ASTPai,ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        if (ASTPai.getASTS().size() != 2) {
            mAnalisador.getErros().add("Problema com a condição DAZ !");
        }

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("ARGUMENTS")) {

                if (mAST.getASTS().size() == 2) {

                    for (AST mgST : mAST.getASTS()) {
                        condicao(mgST);
                    }

                } else {

                    mAnalisador.getErros().add("Sao necessarias 2 condicoes para uma instrucao " + ASTPai.getTipo() + " !");

                }


            } else if (mAST.mesmoTipo("BODY")) {


                if (dentroFunction) {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Function().analisarDentroFunction(sAST,mAlocadosAntes, true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.getAnalisar_Action().analisarDentroAction(sAST,mAlocadosAntes, true);
                    }
                }


            } else {

                mAnalisador.getErros().add("Problema com a condição DAZ !");

            }

        }


    }

    public void analisar_All(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        int mQuantidade = 0;

        boolean mExisteChoosable = false;
        boolean mExisteCases = false;



        int outros = 0;

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("OTHERS")) {
                outros += 1;

            }

        }

        if(outros>1){
            mAnalisador.getErros().add("Problema com DAZ : So pode existir um OTHERS !");
        }

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("CHOOSABLE")) {

                mExisteChoosable=true;


                if (mAST.getValor().length() == 0) {
                    mAnalisador.getErros().add("É necessario uma condição !");
                }

            } else if (mAST.mesmoTipo("CASES")) {

                mExisteCases=true;

                for (AST mgST : mAST.getASTS()) {


                    analisar_All_Item(mgST,mAlocados, dentroFunction);
                }

            }

        }

        if(!mExisteChoosable){
            mAnalisador.getErros().add("Problema com DAZ : Nao existe condicao !");
        }
        if(!mExisteCases){
            mAnalisador.getErros().add("Problema com DAZ : Nao existem casos !");
        }



    }

}
