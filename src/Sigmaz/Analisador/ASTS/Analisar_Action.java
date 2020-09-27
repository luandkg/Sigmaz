package Sigmaz.Analisador.ASTS;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Analisador.Complementar.Analisador_Bloco;

import java.util.ArrayList;
import Sigmaz.Utils.AST;

public class Analisar_Action {

    private Analisador mAnalisador;

    private ArrayList<String> mAlocadoAqui;

    private Analisador_Bloco mAnalisador_Bloco;

    public Analisar_Action(Analisador eAnalisador, Analisador_Bloco eAnalisador_Bloco) {

        mAnalisador = eAnalisador;

        mAlocadoAqui = new ArrayList<String>();
        mAnalisador_Bloco = eAnalisador_Bloco;

    }

    public void resolucao_nome(String eNome, String erro) {

        if (mAlocadoAqui.contains(eNome)) {
            mAnalisador.getErros().add("Alocacao : " + eNome + " : Duplicada !");
        } else {
            mAlocadoAqui.add(eNome);
        }


        if (mAnalisador.getProibidos().contains(eNome)) {
            mAnalisador.getErros().add(erro);
        }


    }


    public void analisarDentroAction(AST ASTPai, ArrayList<String> mAlocadosAntes, boolean laco) {


        if (ASTPai.mesmoTipo("DEF")) {

            resolucao_nome(ASTPai.getNome(), "Def : " + ASTPai.getNome() + " : Nome Proibido !");


            mAlocadosAntes.add(ASTPai.getNome());


            if (ASTPai.getBranch("TYPE").mesmoNome("auto")) {
                mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().add(ASTPai.getNome());
            } else if (ASTPai.getBranch("TYPE").mesmoNome("functor")) {
                mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().add(ASTPai.getNome());
            }

            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);

        } else if (ASTPai.mesmoTipo("MOC")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            } else {
                mAlocadoAqui.add(ASTPai.getNome());
            }

            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Moc : " + ASTPai.getNome() + " : Nome Proibido !");
            }

            if (ASTPai.getBranch("TYPE").mesmoNome("auto")) {
                mAnalisador_Bloco.getAnalisar_Outros().getActions_ApenasNomes().add(ASTPai.getNome());
            } else if (ASTPai.getBranch("TYPE").mesmoNome("functor")) {
                mAnalisador_Bloco.getAnalisar_Outros().getFunctions_ApenasNomes().add(ASTPai.getNome());
            }

            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);

        } else if (ASTPai.mesmoTipo("LET")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            } else {
                mAlocadoAqui.add(ASTPai.getNome());
            }


            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Let : " + ASTPai.getNome() + " : Nome Proibido !");
            }


            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);
        } else if (ASTPai.mesmoTipo("VOZ")) {

            if (mAlocadoAqui.contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Alocacao : " + ASTPai.getNome() + " : Duplicada !");
            } else {
                mAlocadoAqui.add(ASTPai.getNome());
            }


            mAlocadosAntes.add(ASTPai.getNome());

            if (mAnalisador.getProibidos().contains(ASTPai.getNome())) {
                mAnalisador.getErros().add("Let : " + ASTPai.getNome() + " : Nome Proibido !");
            }


            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);


        } else if (ASTPai.mesmoTipo("MUT")) {

            resolucao_nome(ASTPai.getNome(), "Mut : " + ASTPai.getNome() + " : Nome Proibido !");

            mAlocadosAntes.add(ASTPai.getNome());


            mAnalisador.analisarAlocacao(ASTPai, mAlocadosAntes);
            mAnalisador_Bloco.analisarValoracao(ASTPai, mAlocadosAntes);

            mAnalisador_Bloco.getAnalisar_Outros().analisarTipagem(ASTPai);

        } else if (ASTPai.mesmoTipo("INVOKE")) {
        } else if (ASTPai.mesmoTipo("APPLY")) {

            mAnalisador_Bloco.getAnalisar_Apply().analisar_Apply(ASTPai, mAlocadosAntes);

        } else if (ASTPai.mesmoTipo("EXECUTE")) {

            mAnalisador_Bloco.getAnalisar_Execute().analisar_Execute(ASTPai);

        } else if (ASTPai.mesmoTipo("WHEN")) {

            mAnalisador_Bloco.getAnalisar_When().analisar_When(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("DAZ")) {

            mAnalisador_Bloco.getAnalisar_All().analisar_All(ASTPai, mAlocadosAntes, false);


        } else if (ASTPai.mesmoTipo("RETURN")) {
            mAnalisador.getErros().add("Action " + ASTPai.getNome() + " : Nao pode ter RETORNO !");
        } else if (ASTPai.mesmoTipo("IF")) {
            mAnalisador_Bloco.getAnalisar_Condition().analisarCondicao(ASTPai, mAlocadosAntes, false, laco);
        } else if (ASTPai.mesmoTipo("WHILE")) {

            mAnalisador_Bloco.getAnalisar_While().analisarWhile(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("LOOP")) {

            mAnalisador_Bloco.getAnalisar_Loop().analisarLoop(ASTPai, mAlocadosAntes, false);

        } else if (ASTPai.mesmoTipo("STEP")) {
            mAnalisador_Bloco.getAnalisar_Step().analisarStep(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("STEPDEF")) {
            mAnalisador_Bloco.getAnalisar_Step().analisarStepDef(ASTPai, mAlocadosAntes, false);
        } else if (ASTPai.mesmoTipo("TRY")) {
            mAnalisador_Bloco.getAnalisar_Try().init(ASTPai, mAlocadosAntes, false);
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
        } else if (ASTPai.mesmoTipo("VALUE")) {
        } else if (ASTPai.mesmoTipo("EXTERN_REFERED")) {

        } else if (ASTPai.mesmoTipo("LOCAL")) {
        } else if (ASTPai.mesmoTipo("EXECUTE_LOCAL")) {
        } else if (ASTPai.mesmoTipo("EXECUTE_AUTO")) {
        } else if (ASTPai.mesmoTipo("EXECUTE_FUNCTOR")) {
        } else if (ASTPai.mesmoTipo("CHANGE")) {
        } else if (ASTPai.mesmoTipo("USING")) {

        } else {
            mAnalisador.getErros().add("AST : " + ASTPai.getTipo());
        }

    }


    public void analisarAction(AST ASTPai, ArrayList<String> mAlocadosAntes) {

        ArrayList<String> mAlocados = mAnalisador.copiarAlocados(mAlocadosAntes);


        String mParametragem = ASTPai.getNome() + " ( " + mAnalisador_Bloco.getAnalisar_Argumentos().analisarArguments(ASTPai.getBranch("ARGUMENTS"), mAlocados) + ") ";


        for (AST mAST : ASTPai.getBranch("BODY").getASTS()) {

            analisarDentroAction(mAST, mAlocados, false);


        }


    }


}
