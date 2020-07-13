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

        AST mGENERIC = eAST.getBranch("GENERIC");

        Run_GetType mRun_GetType = new Run_GetType(mRunTime,mEscopo);

        String mTipagem = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

     //   System.out.println("Tipagem : " + mTipagem);


        AST mValor = eAST.getBranch("VALUE");


        // System.out.println("Valorando  -> Def " + eAST.getNome());

       // System.out.println("Pas Retorno : " + mTipagem);

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, mTipagem);

     // System.out.println("Def Retorno : " + mAST.getRetornoTipo());




        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getIsNulo()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
            } else {
                mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " x "+ mAST.getRetornoTipo()); }

        } else if (mAST.getIsPrimitivo()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarDefinicao(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());
            } else {

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                if (mEscopo.existeCast(mTipagem)) {


                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarGetterCast(mTipagem, mAST.getRetornoTipo(), mAST.getConteudo());

                    if (res == null) {
                        mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
                    } else {
                        mEscopo.criarDefinicao(eAST.getNome(), mTipagem, res);
                    }

                } else if (mEscopo.existeCast(mAST.getRetornoTipo())) {

                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarSetterCast(mAST.getRetornoTipo(), mTipagem, mAST.getConteudo());

                    if (res == null) {
                        mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
                    } else {
                        mEscopo.criarDefinicao(eAST.getNome(), mTipagem, res);
                    }

                } else {

                    mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " vx "+ mAST.getRetornoTipo());


                }

            }
        } else if (mAST.getIsStruct()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {

                mEscopo.criarDefinicaoStruct(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            } else {
                mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " x "+ mAST.getRetornoTipo());
            }

        } else {

            mRunTime.getErros().add("Retorno incompativel  f : " + mTipagem + " x "+ mAST.getRetornoTipo());

        }


    }


}


