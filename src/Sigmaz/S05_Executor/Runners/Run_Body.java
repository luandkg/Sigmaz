package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Processor.Run_Proc;
import Sigmaz.S05_Executor.Processor.Run_Reg;

public class Run_Body {

    private RunTime mRunTime;
    private Escopo mEscopo;


    private String mLocal;

    private Item mItem;
    private String mEsperaRetornar;

    public Run_Body(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


        mLocal = "Run_Body";
        mEsperaRetornar = "<<ANY>>";

    }

    public Item getRetorno() {
        return mItem;
    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
    }

    public boolean getRetornado() {
        return mEscopo.getRetornado();
    }

    public void Cancelar() {
        mEscopo.setCancelar(true);
        //  System.out.println("\n  -->> Cancelando : " + mEscopo.getNome());
    }

    public void Continuar() {
        mEscopo.setContinuar(true);
        // System.out.println("\n  -->> Continuando : " + mEscopo.getNome());
    }

    public void Retornar() {
        mEscopo.setRetornado(true);
        // System.out.println("\n  -->> Continuando : " + mEscopo.getNome());
    }

    public void Retorne(Item eItem) {
        mEscopo.retorne(eItem);
        mItem = eItem;
    }


    public void esperaRetornar(String eRetorno) {
        mEsperaRetornar = eRetorno;
    }

    public void init(AST ASTCorrente) {

        // System.out.println("\n  -->> Em Corpo para Retornar : " + eReturne + " :: " + ASTCorrente.getASTS().size());

        //  System.out.println("\n  -->> Run_Body :: " + mEscopo.getNome());


        for (AST fAST : ASTCorrente.getASTS()) {

            // System.out.println("\n  -->> EX :: " + fAST.getTipo() + " :: " + mRunTime.getErros().size());

            // System.out.println("BODY - 0 " + fAST.getTipo() + " = " + mRunTime.getErros().size());

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


                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("MOC")) {

                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("LET")) {


                Run_Let mAST = new Run_Let(mRunTime, mEscopo);
                mAST.initLet(fAST);

            } else if (fAST.mesmoTipo("VOZ")) {

                Run_Voz mAST = new Run_Voz(mRunTime, mEscopo);
                mAST.initVoz(fAST);

            } else if (fAST.mesmoTipo("MUT")) {

                Run_Mut mAST = new Run_Mut(mRunTime, mEscopo);
                mAST.init(fAST);


            } else if (fAST.mesmoTipo("APPLY")) {

                Run_Apply mAST = new Run_Apply(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("IF")) {

                Run_Condition mAST = new Run_Condition(mRunTime, mEscopo);

                mAST.init(fAST);

                if (mRunTime.getErros().size() > 0) {
                    break;
                }

                if (mAST.getCancelado()) {
                    Cancelar();
                }
                if (mAST.getContinuar()) {
                    Continuar();
                }

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("WHILE")) {

                Run_While mAST = new Run_While(mRunTime, mEscopo);
                mAST.init(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("LOOP")) {

                Run_Loop mAST = new Run_Loop(mRunTime, mEscopo);
                mAST.init(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("STEP")) {

                Run_Step mAST = new Run_Step(mRunTime, mEscopo);
                mAST.init(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("STEPDEF")) {

                Run_Step mAST = new Run_Step(mRunTime, mEscopo);
                mAST.initDef(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("STEPLET")) {

                Run_Step mAST = new Run_Step(mRunTime, mEscopo);
                mAST.initLet(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }
            } else if (fAST.mesmoTipo("STEPMUT")) {

                Run_Step mAST = new Run_Step(mRunTime, mEscopo);
                mAST.initMut(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("WHEN")) {

                Run_When mAST = new Run_When(mRunTime, mEscopo);

                mAST.init(fAST);
                if (mAST.getCancelado()) {
                    Cancelar();
                }
                if (mAST.getContinuar()) {
                    Continuar();
                }

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("DAZ")) {

                Run_Daz mAST = new Run_Daz(mRunTime, mEscopo);

                mAST.init(fAST);
                if (mAST.getCancelado()) {
                    Cancelar();
                }
                if (mAST.getContinuar()) {
                    Continuar();
                }

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("RETURN")) {


                Run_Return mRun_Return = new Run_Return(mRunTime, mEscopo);

                mItem = mRun_Return.init(fAST, mEsperaRetornar);

                Retornar();

                break;


            } else if (fAST.mesmoTipo("EXECUTE")) {


                Run_Execute mAST = new Run_Execute(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("TRY")) {


                Run_Try mAST = new Run_Try(mRunTime, mEscopo);
                mAST.init(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("EACH")) {


                Run_Each mAST = new Run_Each(mRunTime, mEscopo);
                mAST.init(fAST);

                if (mAST.getRetornado()) {
                    Retorne(mAST.getRetorno());
                    break;
                }

            } else if (fAST.mesmoTipo("CANCEL")) {
                Cancelar();
                break;

            } else if (fAST.mesmoTipo("CONTINUE")) {
                Continuar();
                break;

            } else if (fAST.mesmoTipo("EXCEPTION")) {


                Run_Exception mAST = new Run_Exception(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("VALUE")) {


                Run_Value mAST = new Run_Value(mRunTime, mEscopo);
                mAST.init(fAST, "<<ANY>>");

            } else if (fAST.mesmoTipo("EXECUTE_INIT")) {


                Run_Any mAST = new Run_Any(mRunTime);
                mAST.init_inits(fAST, mEscopo, mEscopo, mEscopo.getAO().getInitsCompleto());


            } else if (fAST.mesmoTipo("EXTERN_REFERED")) {

                Run_ExplicitRefered mRER = new Run_ExplicitRefered(mRunTime, mEscopo);
                mRER.init(fAST);


            } else if (fAST.mesmoTipo("LOCAL")) {

                mEscopo.definirLocal(fAST);

            } else if (fAST.mesmoTipo("EXECUTE_LOCAL")) {

                Run_ExecuteLocal mRun_ExecuteLocal = new Run_ExecuteLocal(mRunTime, mEscopo);
                mRun_ExecuteLocal.initSemRetorno(fAST);

            } else if (fAST.mesmoTipo("EXECUTE_AUTO")) {

                Run_ExecuteAuto mSubRun = new Run_ExecuteAuto(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("CHANGE")) {


                Run_Change mSubRun = new Run_Change(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("USING")) {


                Run_Using mSubRun = new Run_Using(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("SCOPE")) {

                Run_SubScope mSubRun = new Run_SubScope(mRunTime, mEscopo);
                mSubRun.init(fAST);

                if (mSubRun.getRetornado()) {
                    break;
                }

            } else if (fAST.mesmoTipo("REG")) {


                Run_Reg mSubRun = new Run_Reg(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("PROC")) {


                Run_Proc mSubRun = new Run_Proc(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("MOVE_DATA")) {

                Run_MoveData mSubRun = new Run_MoveData(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("STAGE_GET")) {

                Run_Stages mSubRun = new Run_Stages(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("DEBUG")) {

                Run_Debug mSubRun = new Run_Debug(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("DELETE")) {

                Run_Delete mSubRun = new Run_Delete(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("STRUCT_SETTER")) {

                Run_Setter mSubRun = new Run_Setter(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else if (fAST.mesmoTipo("IN_DEBUG")) {


            } else if (fAST.mesmoTipo("ASSERTIVE")) {


                Run_Assertive mSubRun = new Run_Assertive(mRunTime, mEscopo);
                mSubRun.init(fAST);

            } else {

                mRunTime.errar(mLocal, "Dentro do Escopo  : " + fAST.getTipo());

            }


        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        mRunTime.getHeap().limparEscopo(mEscopo);


    }

}
