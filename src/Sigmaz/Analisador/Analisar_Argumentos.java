package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Argumentos {

    private Analisador mAnalisador;

    public Analisar_Argumentos(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public String analisarArguments(AST ASTPai,ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mNomes = new ArrayList<String>();

        String mParametragem = "";

        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {

               // mAnalisador.analisarAlocacao(mAST,mAlocadosAntes);

                if (!mNomes.contains(mAST.getNome())) {
                    mNomes.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametragem += "<" + mAST.getValor() + "> ";

                mAnalisador.analisandoDefines(mAST);

            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        return mParametragem;
    }

}
