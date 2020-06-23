package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Def {

    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Def(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public void init(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        String eTipado = eAST.getValor();

       // System.out.println("Valorando  -> Def " + eAST.getNome());

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, eAST.getValor());


       // System.out.println("Retornou  -> Def " + eAST.getNome() + " : " + mAST.getRetornoTipo() + " = " + mAST.getConteudo() + " -> " + mAST.getModulante());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getIsNulo()) {
            mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
        } else if (mAST.getIsPrimitivo()) {

            if (eAST.getValor().contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarDefinicao(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());
            } else {

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

              //  System.out.println("Retorno ESQ : " + eTipado);
               // System.out.println("Retorno DIR x : " + mAST.getRetornoTipo());

                if (mEscopo.existeCast(eTipado)) {


                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarGetterCast(eTipado, mAST.getRetornoTipo(), mAST.getConteudo());

                    if (res == null) {
                        mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
                    } else {
                        mEscopo.criarDefinicao(eAST.getNome(), eAST.getValor(), res);
                    }

                } else if (mEscopo.existeCast(mAST.getRetornoTipo())) {

                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarSetterCast(mAST.getRetornoTipo(), eTipado, mAST.getConteudo());

                    if (res == null) {
                        mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
                    } else {
                        mEscopo.criarDefinicao(eAST.getNome(), eAST.getValor(), res);
                    }

                } else {

                    mRunTime.getErros().add("Retorno incompativel  : " + mAST.getRetornoTipo());


                }

            }
        } else if (mAST.getIsStruct()) {


            mEscopo.criarDefinicaoStruct(eAST.getNome(), eAST.getValor(), mAST.getConteudo());

        } else {

            mRunTime.getErros().add("Retorno incompativel  : " + mAST.getRetornoTipo());

        }


    }


}


