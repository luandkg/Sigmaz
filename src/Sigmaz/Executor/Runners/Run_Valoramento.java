package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Valoramento {

    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Valoramento(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public Run_Value init(String eNome,AST mValor, String mTipagem) {

        // System.out.println("Valorando  -> Def " + eAST.getNome());

        // System.out.println("Pas Retorno : " + mTipagem);

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, mTipagem);

        // System.out.println("Def Retorno : " + mAST.getRetornoTipo());

        if ( mAST.getRetornoTipo().contentEquals("<<ANY>>")){
            mAST.setRetornoTipo(mTipagem);
        }

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        if (mAST.getIsNulo()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {

            } else {
                mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " x1 " + mAST.getRetornoTipo());
            }

        } else if (mAST.getIsPrimitivo()) {


            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {


                if (mAST.getRetornoTipo().contentEquals("auto")) {

                    Run_Anonymous mRun_Anonymous = new Run_Anonymous(mRunTime,mEscopo);
                    mRun_Anonymous.criarAuto(eNome,mAST,mValor);

                } else if (mAST.getRetornoTipo().contentEquals("functor")) {


                    Run_Anonymous mRun_Anonymous = new Run_Anonymous(mRunTime,mEscopo);
                    mRun_Anonymous.criarFunctor(eNome,mAST,mValor);

                }


            } else {

                if (mRunTime.getErros().size() > 0) {
                    return null;
                }


                Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);

                String res = mCast.realizarCast(mTipagem, mAST.getRetornoTipo(), mAST.getConteudo());

                if (mRunTime.getErros().size() > 0) {
                    return null;
                }

                if (res == null) {
                    mAST.setNulo(true);
                }

                mAST.setRetornoTipo(mTipagem);

            }
        } else if (mAST.getIsStruct()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {


            } else {
                mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " x3 " + mAST.getRetornoTipo());
            }

        } else {

            mRunTime.getErros().add("Retorno incompativel  f : " + mTipagem + " x " + mAST.getRetornoTipo());

        }

        mAST.setRetornoTipo(mTipagem);

        return mAST;

    }
}
