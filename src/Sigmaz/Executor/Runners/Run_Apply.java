package Sigmaz.Executor.Runners;

import Sigmaz.Executor.*;
import Sigmaz.Utils.AST;

public class Run_Apply {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Apply(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

    }


    public void init(AST ASTCorrente) {

        AST mSettable = ASTCorrente.getBranch("SETTABLE");
        AST mValue = ASTCorrente.getBranch("VALUE");


        Run_Value SettableValue = new Run_Value(mRunTime, mEscopo);
        SettableValue.init(mSettable, "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

         // System.out.println("Settable -->> " + SettableValue.getRetornoTipo());

        //   System.out.println("Settable -->>" + SettableValue.getRetornoTipo() +  " ::: " +  SettableValue.getReferencia().getNome());

        //   System.out.println("Erros : " + mRunTime.getErros().size());


        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValue, SettableValue.getRetornoTipo());

        if (SettableValue.getReferencia() == null) {
            mRunTime.getErros().add("Referencia nao encontrada !");
        } else {

            aplicar(SettableValue.getReferencia(), mAST);

            // if (SettableValue.getReferencia().getTipo().contentEquals(mAST.getRetornoTipo())) {
            //     SettableValue.getReferencia().setValor(mAST.getConteudo());
            //     SettableValue.getReferencia().setNulo(mAST.getIsNulo());
            // } else {
            // mRunTime.getErros().add("Retorno incompativel  : " + SettableValue.getReferencia().getTipo() + " x " + mAST.getRetornoTipo());
            // }
        }


    }


    //  System.out.println("Settable : "+ SettableValue.getReferencia().getNome() + " = " + mAST.getConteudo() + " ::: " + mAST.getRetornoTipo());

    public void aplicar(Item receber, Run_Value aplicar) {

        String mTipagem = receber.getTipo();

        receber.setValor(aplicar.getConteudo());
        receber.setNulo(aplicar.getIsNulo());

        if (aplicar.getIsNulo()) {

           // if (mTipagem.contentEquals(aplicar.getRetornoTipo())) {
                receber.setNulo(aplicar.getIsNulo());
           // } else {
           //     mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " sx " + aplicar.getRetornoTipo());
          //  }

        } else if (aplicar.getIsPrimitivo()) {

            if (mTipagem.contentEquals(aplicar.getRetornoTipo())) {
                receber.setValor(aplicar.getConteudo());
                receber.setNulo(aplicar.getIsNulo());
            } else {

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                //  System.out.println("Retorno ESQ : " + eTipado);
                // System.out.println("Retorno DIR x : " + mAST.getRetornoTipo());

                //    System.out.println("CASTING : " + mAST.getRetornoTipo() + " -> " + mTipagem);

                if (mEscopo.existeCast(mTipagem)) {


                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarGetterCast(mTipagem, aplicar.getRetornoTipo(), aplicar.getConteudo());

                    if (res == null) {

                        receber.setValor(aplicar.getConteudo());
                        receber.setNulo(aplicar.getIsNulo());
                        receber.setTipo(mTipagem);

                    } else {
                        receber.setValor(aplicar.getConteudo());
                        receber.setNulo(aplicar.getIsNulo());
                        receber.setTipo(mTipagem);
                    }

                } else if (mEscopo.existeCast(aplicar.getRetornoTipo())) {

                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarSetterCast(aplicar.getRetornoTipo(), mTipagem, aplicar.getConteudo());

                    if (res == null) {
                        receber.setValor(aplicar.getConteudo());
                        receber.setNulo(aplicar.getIsNulo());
                        receber.setTipo(aplicar.getRetornoTipo());
                    } else {
                        receber.setValor(aplicar.getConteudo());
                        receber.setNulo(aplicar.getIsNulo());
                        receber.setTipo(aplicar.getRetornoTipo());
                    }

                } else {

                    mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " vx " + aplicar.getRetornoTipo());


                }

            }
        } else if (aplicar.getIsStruct()) {

            if (mTipagem.contentEquals(aplicar.getRetornoTipo())) {

                receber.setValor(aplicar.getConteudo());
                receber.setNulo(aplicar.getIsNulo());
                receber.setTipo(aplicar.getRetornoTipo());

            } else {
                mRunTime.getErros().add("Retorno incompativel  : " + mTipagem + " x " + aplicar.getRetornoTipo());
            }

        } else {

            mRunTime.getErros().add("Retorno incompativel  f : " + mTipagem + " x " + aplicar.getRetornoTipo());

        }


    }


}

