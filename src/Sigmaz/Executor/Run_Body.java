package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Body {

    private RunTime mRunTime;
    private Escopo mEscopo;



    public Run_Body(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void initAction(AST ASTCorrente) {

        for (AST fAST : ASTCorrente.getASTS()) {


            if (fAST.mesmoTipo("DEF")) {

                mEscopo.definirVariavel(fAST);
            } else         if (fAST.mesmoTipo("MOC")) {

                mEscopo.definirConstante(fAST);


            } else if (fAST.mesmoTipo("INVOKE")) {

                Run_Invoke mAST = new Run_Invoke(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("ACTION")) {

                Run_Action mAST = new Run_Action(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("APPLY")) {

                Run_Apply mAST = new Run_Apply(mRunTime, mEscopo);
                mAST.init(fAST);

            } else {

                System.out.println("Dentro do Escopo  : " + fAST.getTipo());

            }


        }

    }

    public void initFunction(AST ASTCorrente,Run_Func eRun_Func,String eReturne) {

        for (AST fAST : ASTCorrente.getASTS()) {


            if (fAST.mesmoTipo("DEF")) {

                mEscopo.definirVariavel(fAST);

            } else if (fAST.mesmoTipo("INVOKE")) {

                Run_Invoke mAST = new Run_Invoke(mRunTime, mEscopo);
                mAST.init(fAST);
            } else if (fAST.mesmoTipo("APPLY")) {

                Run_Apply mAST = new Run_Apply(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("RETURN")) {


                Run_Value mAST = new Run_Value(mRunTime, mEscopo);
                mAST.init(fAST, eReturne);

                if (mAST.getIsNulo()) {
                   // mTipo = "NULL";
                    eRun_Func.setTipo("NULL");
                } else if (mAST.getIsPrimitivo()) {

                  //  mTipo = "PRIMITIVE";
                  //  mPrimitivo = mAST.getPrimitivo();

                    eRun_Func.setTipo("PRIMITIVE");
                    eRun_Func.setPrimitivo(mAST.getPrimitivo());

                } else {
                    mRunTime.getErros().add("AST_Value com problemas !");
                }
            } else {

                System.out.println("Dentro do Escopo  : " + fAST.getTipo());

            }


        }

    }

}
