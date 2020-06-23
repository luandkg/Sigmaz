package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Action {

    private Analisador mAnalisador;

    public Analisar_Action(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }


    public void analisarDentroAction(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean laco) {

        if (ASTPai.mesmoTipo("DEF")) {

            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador. analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador.getAnalisar_Outros().analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("MOC")) {

            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador.getAnalisar_Outros().analisandoDefines(ASTPai);
        } else if (ASTPai.mesmoTipo("INVOKE")) {
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisador.getAnalisar_Apply().analisar_Apply(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("EXECUTE")) {

            mAnalisador.getAnalisar_Execute().analisar_Execute(ASTPai);

        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisador.getAnalisar_When().analisar_When(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("ALL")) {

            mAnalisador.getAnalisar_All().analisar_All(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("RETURN")) {
            mAnalisador.getErros().add("Action " + ASTPai.getNome() + " : Nao pode ter RETORNO !");
        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisador.getAnalisar_Condition().analisarCondicao(ASTPai, mAlocadosAntes, false, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            mAnalisador.getAnalisar_While().analisarWhile(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisador.getAnalisar_Step().analisarStep(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisador.getAnalisar_Step().analisarStepDef(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("CANCEL")) {
            if (!laco) {
                mAnalisador.getErros().add("CANCEL so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("CONTINUE")) {
            if (!laco) {
                mAnalisador.getErros().add("CONTINUE so pode existir dentro de um laco !");
            }
        } else {
            mAnalisador.getErros().add("AST : " + ASTPai.getTipo());
        }

    }

    public void analisarAction(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


        String mParametrizando = "";

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {


                if (!mAlocados.contains(mAST.getNome())) {
                    mAlocados.add(mAST.getNome());
                } else {
                    mAnalisador.getErros().add("Argumento Duplicado : " + mAST.getNome());
                }

                mParametrizando += "<" + mAST.getValor() + "> ";

                mAnalisador.getAnalisar_Outros().analisandoDefines(mAST);

            } else {
                mAnalisador.getErros().add("Tipo Desconhecido : " + mAST.getTipo());
            }
        }

        String mParametragem = ASTPai.getNome() + " ( " + mAnalisador.getAnalisar_Argumentos().analisarArguments(ASTPai.getBranch("ARGUMENTS")) + ") ";


        if (!mAnalisador.getActions_Nomes().contains(mParametragem)) {
            mAnalisador.getActions_Nomes().add(mParametragem);
        } else {
            mAnalisador.getErros().add("Action Duplicada : " + mParametragem);
        }



        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {

            analisarDentroAction(mAST, mAlocados, false);


        }

    }

}
