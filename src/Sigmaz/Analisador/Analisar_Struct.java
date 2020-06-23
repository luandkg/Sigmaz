package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Struct {

    private Analisador mAnalisador;
    private Analisar_Argumentos mAnalisar_Argumentos;

    public Analisar_Struct(Analisador eAnalisador) {

        mAnalisador = eAnalisador;
        mAnalisar_Argumentos = new Analisar_Argumentos(mAnalisador);

    }

    public void init_Struct(AST ASTPai, ArrayList<String> mAlocadosAntes) {


        AST ASTInits = ASTPai.getBranch("INITS");
        ArrayList<String> mInitListagem = new ArrayList<String>();

        for (AST ASTInit : ASTInits.getASTS()) {

            String mParametragem = ASTPai.getNome() + " ( " + mAnalisar_Argumentos.analisarArguments(ASTInit.getBranch("ARGUMENTS")) + ") ";


            if (!mInitListagem.contains(mParametragem)) {
                mInitListagem.add(mParametragem);
            } else {
                mAnalisador.getErros().add("Init Duplicada : " + mParametragem);
            }

            if (!getModo(ASTInit).contentEquals("ALL")) {
                mAnalisador.getErros().add("Init Invalida : " + getModo(ASTInit) + " " + mParametragem);
            }
        }


        AST mCorpo = ASTPai.getBranch("BODY");

        for (AST mAST : mCorpo.getASTS()) {

            if (mAST.mesmoTipo("OPERATION")) {

                String mAssinatura = getModo(mAST)  + " " + ASTPai.getNome() + " ( " + mAnalisar_Argumentos.analisarArguments(mAST.getBranch("ARGUMENTS")) + ") -> " + mAST.getValor();



            }

        }

    }


    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }

}
