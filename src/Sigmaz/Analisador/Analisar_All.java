package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_All {

    private Analisador mAnalisador;

    public Analisar_All(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void condicao(AST ASTPai) {

        if (ASTPai.getValor().length() == 0) {
            mAnalisador.getErros().add("É necessario uma condição para ALL !");
        }

    }

    public void analisar_All_Item(AST ASTPai,ArrayList<String> mAlocadosAntes, boolean dentroFunction) {

        if (ASTPai.getASTS().size() != 2) {
            mAnalisador.getErros().add("Problema com a condição ALL !");
        }

        for (AST mAST : ASTPai.getASTS()) {

            if (mAST.mesmoTipo("CONDITION")) {

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
                        mAnalisador.analisarDentroFunction(sAST,mAlocadosAntes, true);
                    }
                } else {
                    for (AST sAST : mAST.getASTS()) {
                        mAnalisador.analisarDentroAction(sAST,mAlocadosAntes, true);
                    }
                }


            } else {

                mAnalisador.getErros().add("Problema com a condição ALL !");

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
            mAnalisador.getErros().add("Problema com ALL : So pode existir um OTHERS !");
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
            mAnalisador.getErros().add("Problema com ALL : Nao existe condicao !");
        }
        if(!mExisteCases){
            mAnalisador.getErros().add("Problema com ALL : Nao existem casos !");
        }



    }

}
