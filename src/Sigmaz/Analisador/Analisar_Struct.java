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

    public void init_Struct(AST ASTPai,ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);

        AST ASTInits = ASTPai.getBranch("INITS");
         ArrayList<String> mInitListagem = new ArrayList<String>();

        for (AST ASTInit : ASTInits.getASTS()) {

            String mParametragem = ASTPai.getNome() + " ( " + mAnalisar_Argumentos.analisarArguments(ASTInit.getBranch("ARGUMENTS"), mAlocados) + ") ";


            if (!mInitListagem.contains(mParametragem)) {
                mInitListagem.add(mParametragem);
            } else {
                mAnalisador.getErros().add("Init Duplicada : " + mParametragem);
            }

        }


    }

}
