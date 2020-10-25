package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Processor.Run_Proc;
import Sigmaz.S07_Executor.Processor.Run_Reg;

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

            // System.out.println("\n  -->> IN :: " + fAST.getTipo());

        }

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


                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.initLet(fAST);

            } else if (fAST.mesmoTipo("VOZ")) {

                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
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


                Run_Value mAST = new Run_Value(mRunTime, mEscopo);
                mAST.init(fAST.getBranch("VALUE"), mEsperaRetornar);

                if (mRunTime.getErros().size() > 0) {
                    break;
                }

                mItem = new Item("RETURNABLE");

                mItem.setNulo(mAST.getIsNulo());
                mItem.setPrimitivo(mAST.getIsPrimitivo());
                mItem.setIsEstrutura(mAST.getIsStruct());
                mItem.setValor(mAST.getConteudo(), mRunTime, mEscopo);
                mItem.setTipo(mAST.getRetornoTipo());
                mEscopo.setRetornado(true);

                if (mItem.getIsEstrutura()) {
                    if (mItem.getTemValor()) {
                        mRunTime.getHeap().aumentar(mItem.getValor(mRunTime, mEscopo));
                    }
                }


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


                Run_ExecuteAuto mRun_ExecuteAuto = new Run_ExecuteAuto(mRunTime, mEscopo);
                mRun_ExecuteAuto.init(fAST);

            } else if (fAST.mesmoTipo("CHANGE")) {


                Run_Change nRun_Change = new Run_Change(mRunTime, mEscopo);
                nRun_Change.init(fAST);

            } else if (fAST.mesmoTipo("USING")) {


                Run_Using mRun_Using = new Run_Using(mRunTime, mEscopo);
                mRun_Using.init(fAST);

            } else if (fAST.mesmoTipo("SCOPE")) {

                Escopo eNovoEscopo = new Escopo(mRunTime, mEscopo);
                eNovoEscopo.setNome("SUBSCOPE::" + mEscopo.getNome());

                Run_Body mAST = new Run_Body(mRunTime, eNovoEscopo);
                mAST.init(fAST);

                if (mAST.getRetornado()) {

                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }

                    mEscopo.retorne(mAST.getRetorno());

                    break;

                }

            } else if (fAST.mesmoTipo("REG")) {


                Run_Reg mRun_Reg = new Run_Reg(mRunTime, mEscopo);
                mRun_Reg.init(fAST);
            } else if (fAST.mesmoTipo("PROC")) {


                Run_Proc mRun_Proc = new Run_Proc(mRunTime, mEscopo);
                mRun_Proc.init(fAST);

            } else if (fAST.mesmoTipo("MOVE_DATA")) {

                Run_MoveData mAST = new Run_MoveData(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("STAGE_GET")) {

                Run_Stages mAST = new Run_Stages(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("DEBUG")) {

                Run_Debug mRun_Debug = new Run_Debug(mRunTime, mEscopo);
                mRun_Debug.init(fAST);

            } else if (fAST.mesmoTipo("DELETE")) {

                Run_Delete mRun_Delete = new Run_Delete(mRunTime, mEscopo);
                mRun_Delete.init(fAST);

            } else if (fAST.mesmoTipo("STRUCT_SETTER")) {

                Run_Setter mRun_Setter = new Run_Setter(mRunTime, mEscopo);
                mRun_Setter.init(fAST);


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
