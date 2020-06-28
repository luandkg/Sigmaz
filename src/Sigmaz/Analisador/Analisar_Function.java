package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Function {


    private Analisador mAnalisador;

    public Analisar_Function(Analisador eAnalisador) {

        mAnalisador = eAnalisador;

    }

    public void analisarFunction(AST ASTPai, ArrayList<String> mAlocadosAntes) {


        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


        mAnalisador.getAnalisar_Outros().analisarTipagem(ASTPai);


        String mParametragem = ASTPai.getNome() + " ( " + mAnalisador.getAnalisar_Argumentos().analisarArguments(ASTPai.getBranch("ARGUMENTS"), mAlocados) + ") ";

      //  System.out.println(mParametragem);

        if (!mAnalisador.getFunctions_Nomes().contains(mParametragem)) {
            mAnalisador.getFunctions_Nomes().add(mParametragem);
        } else {
            mAnalisador.getErros().add("Function Duplicada : " + mParametragem);
        }


        boolean retornou = false;

        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {
            boolean ret = analisarDentroFunction(mAST, mAlocados, false);
            if (ret) {
                retornou = true;
            }
        }

        if (retornou == false) {
            mAnalisador.getErros().add("Function " + ASTPai.getNome() + " : Nao possui retorno !");
        }

    }

    public boolean analisarDentroFunction(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean laco) {

        boolean retornou = false;

        if (ASTPai.mesmoTipo("DEF")) {

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())){
                mAnalisador.getErros().add("Def : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            mAnalisador.getAnalisar_Outros().analisarTipagem(ASTPai);
            mAnalisador.analisarValoracao(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("MOC")) {

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())){
                mAnalisador.getErros().add("Moc : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            mAnalisador.getAnalisar_Outros().analisarTipagem(ASTPai);
            mAnalisador.analisarValoracao(ASTPai, mAlocadosAntes);


        } else if (ASTPai.mesmoTipo("INVOKE")) {

        } else if (ASTPai.mesmoTipo("RETURN")) {
            retornou = true;
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisador.getAnalisar_Apply().analisar_Apply(ASTPai, mAlocadosAntes);


        } else if (ASTPai.mesmoTipo("EXECUTE")) {

            mAnalisador.getAnalisar_Execute().analisar_Execute(ASTPai);

        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisador.getAnalisar_When().analisar_When(ASTPai, mAlocadosAntes, true);


        } else if (ASTPai.mesmoTipo("DAZ")) {

            mAnalisador.getAnalisar_All().analisar_All(ASTPai, mAlocadosAntes, true);

        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisador.getAnalisar_Condition().analisarCondicao(ASTPai, mAlocadosAntes, true, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            mAnalisador.getAnalisar_While().analisarWhile(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisador.getAnalisar_Step().analisarStep(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisador.getAnalisar_Step().analisarStepDef(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("CANCEL")) {
            if (!laco) {
                mAnalisador.getErros().add("CANCEL so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("CONTINUE")) {
            if (!laco) {
                mAnalisador.getErros().add("CONTINUE so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("EXCEPTION")) {

        } else {
            mAnalisador.getErros().add("AST : " + ASTPai.getTipo());
        }

        return retornou;
    }


}
