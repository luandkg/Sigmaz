package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Body {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Body(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
    }

    public void Cancelar() {
        mEscopo.setCancelar(true);
        //  System.out.println("\n  -->> Cancelando : " + mEscopo.getNome());


    }

    public void Continuar() {
        mEscopo.setContinuar(true);
        // System.out.println("\n  -->> Continuando : " + mEscopo.getNome());
    }


    public void init(AST ASTCorrente, Run_Func eRun_Func, String eReturne) {

        for (AST fAST : ASTCorrente.getASTS()) {


            if (mRunTime.getErros().size() > 0) {
                break;
            }
            if (mEscopo.getCancelar()) {

                break;
            }
            if (mEscopo.getContinuar()) {

                break;
            }


            if (fAST.mesmoTipo("DEF")) {

                mEscopo.definirVariavel(fAST);

            } else if (fAST.mesmoTipo("MOC")) {

                mEscopo.definirConstante(fAST);

            } else if (fAST.mesmoTipo("INVOKE")) {

                Run_Invoke mAST = new Run_Invoke(mRunTime, mEscopo);
                mAST.init(fAST);
            } else if (fAST.mesmoTipo("APPLY")) {

                Run_Apply mAST = new Run_Apply(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("IF")) {

                Run_Condition mAST = new Run_Condition(mRunTime, mEscopo);

                mAST.init(fAST);
                if (mAST.getCancelado()) {
                    Cancelar();
                }
                if (mAST.getContinuar()) {


                    Continuar();
                }

            } else if (fAST.mesmoTipo("WHILE")) {

                Run_While mAST = new Run_While(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("STEP")) {

                Run_Step mAST = new Run_Step(mRunTime, mEscopo);
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
            } else if (fAST.mesmoTipo("CANCEL")) {
                Cancelar();
            } else if (fAST.mesmoTipo("CONTINUE")) {
                Continuar();
            } else if (fAST.mesmoTipo("EXECUTE")) {

                Run_Execute mAST = new Run_Execute(mRunTime, mEscopo);
                mAST.init(fAST);

            } else {

                System.out.println("Dentro do Escopo  : " + fAST.getTipo());

            }


        }

    }

}
