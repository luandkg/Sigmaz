package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Cast {

    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Cast(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public String realizarGetterCast(String mCAST_NOME, String eRetorno, String eConteudo) {

        // mRunTime.getErros().add("Realizar CAST GETTER : " + mCAST_NOME + " :: " + rRetorno);

        AST mCast = mEscopo.getCast(mCAST_NOME);
        boolean enc = false;
        String ret = null;

        for (AST mGetter : mCast.getASTS()) {
            if (mGetter.mesmoTipo("GETTER") && mGetter.mesmoValor(eRetorno)) {

                Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
                mEscopoInterno.criarParametro(mGetter.getNome(), mGetter.getValor(), eConteudo);

                Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
                mAST.init(mGetter);

                if (mAST.getIsNulo()) {
                    ret = null;
                } else if (mAST.getIsPrimitivo()) {
                    ret = mAST.getConteudo();
                }


                enc = true;
                break;
            }
            // mRunTime.getErros().add(" - Casting de " + mGetter.getValor());


        }

        if (!enc) {
            mRunTime.getErros().add("CASTING GETTER " + eRetorno + " -> " + mCAST_NOME + " : IMPOSSIVEL !");
        }

        return ret;

    }

    public String realizarSetterCast(String mCAST_NOME, String eRetorno, String eConteudo) {

        // mRunTime.getErros().add("Realizar CAST SETTER : " + mCAST_NOME + " :: " + rRetorno);

        AST mCast = mEscopo.getCast(mCAST_NOME);
        boolean enc = false;
        String ret = null;

        for (AST mGetter : mCast.getASTS()) {
            if (mGetter.mesmoTipo("SETTER") && mGetter.mesmoValor(eRetorno)) {

                Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
                mEscopoInterno.criarParametro(mGetter.getNome(), mGetter.getValor(), eConteudo);

                Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
                mAST.init(mGetter);

                if (mAST.getIsNulo()) {
                    ret = null;
                } else if (mAST.getIsPrimitivo()) {
                    ret = mAST.getConteudo();
                }


                enc = true;
                break;
            }
            // mRunTime.getErros().add(" - Casting de " + mGetter.getValor());


        }

        if (!enc) {
            mRunTime.getErros().add("CASTING SETTER " + eRetorno + " -> " + mCAST_NOME + " : IMPOSSIVEL !");
        }

        return ret;

    }


}
