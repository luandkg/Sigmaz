package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Argumentos {

    private Analisador mAnalisador;

    public Analisar_Argumentos(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public String getDefinicao(AST ASTPai) {

        String mParametrizando = "";

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {


                    mParametrizando += "<" + getTipagem(mAST.getBranch("TYPE")) + "> ";

                   mAnalisador.getAnalisar_Outros().analisandoDefinesParam(mAST);

            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        String mParametragem = ASTPai.getNome() + " ( " + mParametrizando + ") ";

        return mParametragem;
    }

    public String analisarArguments(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mNomes = new ArrayList<String>();

        String mParametragem = "";

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {

                mAnalisador.analisarAlocacao(mAST, mAlocadosAntes);

                if (!mNomes.contains(mAST.getNome())) {
                    mNomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametragem += "<" + getTipagem(mAST.getBranch("TYPE")) + "> ";

                mAnalisador.getAnalisar_Outros().analisandoDefinesParam(mAST);

            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        return mParametragem;
    }

    public String getTipagem(AST eAST) {
        String mTipagem = "";
        if (eAST != null) {
            mTipagem = eAST.getNome();

            if (eAST.mesmoValor("GENERIC")) {

                for (AST eTipando : eAST.getASTS()) {
                    mTipagem += "<" + getTipagem(eTipando) + ">";
                }

            }
        }


        return mTipagem;

    }

    public String analisarArguments(AST ASTPai) {

        ArrayList<String> mNomes = new ArrayList<String>();

        String mParametragem = "";

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {


                if (!mNomes.contains(mAST.getNome())) {
                    mNomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametragem += "<" + getTipagem(mAST.getBranch("TYPE")) + "> ";


            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        return mParametragem;
    }

}
