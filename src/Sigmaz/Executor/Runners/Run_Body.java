package Sigmaz.Executor.Runners;

import Sigmaz.Executor.*;
import Sigmaz.Utils.AST;

public class Run_Body {

    private RunTime mRunTime;
    private Escopo mEscopo;


    private boolean mIsNulo;
    private boolean mIsPrimitivo;


    private String mConteudo;
    private Object mObjeto;

    private String mRetornoTipo;
    private String mLocal;

    public Run_Body(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mIsNulo = false;
        mIsPrimitivo = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;
        mLocal = "Run_Body";

    }

    public String getConteudo() {
        return mConteudo;
    }

    public Object getObjeto() {
        return mObjeto;
    }

    public boolean getIsNulo() {
        return mIsNulo;
    }

    public boolean getIsPrimitivo() {
        return mIsPrimitivo;
    }

    public boolean getIsStruct() {
        return !mIsPrimitivo;
    }

    public String getRetornoTipo() {
        return mRetornoTipo;
    }

    public void setNulo(boolean eNulo) {
        mIsNulo = eNulo;
    }

    public void setPrimitivo(boolean ePrimitivo) {
        mIsPrimitivo = ePrimitivo;
    }

    public void setRetornoTipo(String eRetornoTipo) {
        mRetornoTipo = eRetornoTipo;
    }

    public void setConteudo(String eConteudo) {
        mConteudo = eConteudo;
    }

    public String getRetornoFunction() {
        return mRetornoTipo;
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


    public void init(AST ASTCorrente) {

        // System.out.println("\n  -->> Em Corpo para Retornar : " + eReturne + " :: " + ASTCorrente.getASTS().size());

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

            } else   if (fAST.mesmoTipo("LET")) {


                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.initLet(fAST);

            } else if (fAST.mesmoTipo("VOZ")) {

                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                mAST.initVoz(fAST);

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

            } else if (fAST.mesmoTipo("STEPDEF")) {

                Run_Step mAST = new Run_Step(mRunTime, mEscopo);
                mAST.initDef(fAST);

            } else if (fAST.mesmoTipo("WHEN")) {

                Run_When mAST = new Run_When(mRunTime, mEscopo);

                mAST.init(fAST);
                if (mAST.getCancelado()) {
                    Cancelar();
                }
                if (mAST.getContinuar()) {
                    Continuar();
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


            } else if (fAST.mesmoTipo("RETURN")) {

                //   System.out.println("Iniciando Retorando de Corpo ");

                Run_Value mAST = new Run_Value(mRunTime, mEscopo);
                mAST.init(fAST, "<<ANY>>");

                // System.out.println("Retorando de Corpo -> " + mAST.getConteudo() + "  Tipo : " + mAST.getRetornoTipo());


                mIsNulo = mAST.getIsNulo();
                mIsPrimitivo = mAST.getIsPrimitivo();
                mConteudo = mAST.getConteudo();
                mRetornoTipo = mAST.getRetornoTipo();

            } else if (fAST.mesmoTipo("EXECUTE")) {


                Run_Execute mAST = new Run_Execute(mRunTime, mEscopo);
                mAST.init(fAST);
            } else if (fAST.mesmoTipo("TRY")) {


                Run_Try mAST = new Run_Try(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("EACH")) {


                Run_Each mAST = new Run_Each(mRunTime, mEscopo);
                mAST.init(fAST);

            } else if (fAST.mesmoTipo("CANCEL")) {
                Cancelar();
            } else if (fAST.mesmoTipo("CONTINUE")) {
                Continuar();


            } else if (fAST.mesmoTipo("EXCEPTION")) {


                Run_Exception mAST = new Run_Exception(mRunTime, mEscopo);
                mAST.init(fAST);

            } else {

                System.out.println("Dentro do Escopo  : " + fAST.getTipo());

            }


        }

    }

}
