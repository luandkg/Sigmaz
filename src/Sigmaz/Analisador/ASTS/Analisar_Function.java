package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Analisador_Bloco;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Analisar_Function {


    private Analisador mAnalisador;
    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Function(Analisador eAnalisador,Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;
        mAnalisador_Bloco=eAnalisador_Bloco;

    }


        public void analisarFunction(AST ASTPai, ArrayList<String> mAlocadosAntes) {


        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);


        String mParametragem = ASTPai.getNome() + " ( " + mAnalisador_Bloco.getAnalisar_Argumentos().analisarArguments(ASTPai.getBranch("ARGUMENTS"), mAlocados) + ") ";

        //  System.out.println(mParametragem);




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

            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Def : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("MOC")) {

            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Moc : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);


        } else if (ASTPai.mesmoTipo("INVOKE")) {

        } else if (ASTPai.mesmoTipo("RETURN")) {
            retornou = true;
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisador_Bloco.getAnalisar_Apply().analisar_Apply(ASTPai, mAlocadosAntes);


        } else if (ASTPai.mesmoTipo("EXECUTE")) {

            mAnalisador_Bloco.getAnalisar_Execute().analisar_Execute(ASTPai);

        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisador_Bloco.getAnalisar_When().analisar_When(ASTPai, mAlocadosAntes, true);


        } else if (ASTPai.mesmoTipo("DAZ")) {

            mAnalisador_Bloco.getAnalisar_All().analisar_All(ASTPai, mAlocadosAntes, true);

        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisador_Bloco.getAnalisar_Condition().analisarCondicao(ASTPai, mAlocadosAntes, true, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {
            mAnalisador_Bloco.getAnalisar_While().analisarWhile(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisador_Bloco.getAnalisar_Step().analisarStep(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisador_Bloco.getAnalisar_Step().analisarStepDef(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("TRY")) {
            mAnalisador_Bloco.getAnalisar_Try().init(ASTPai, mAlocadosAntes, true);
        } else if (ASTPai.mesmoTipo("CANCEL")) {
            if (!laco) {
                mAnalisador.getErros().add("CANCEL so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("CONTINUE")) {
            if (!laco) {
                mAnalisador.getErros().add("CONTINUE so pode existir dentro de um laco !");
            }
        } else if (ASTPai.mesmoTipo("EXCEPTION")) {

        } else if (ASTPai.mesmoTipo("EACH")) {

        } else {
            mAnalisador.getErros().add("AST : " + ASTPai.getTipo());
        }

        return retornou;
    }


}
