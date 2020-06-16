package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Value {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mTipo;

    private String mPrimitivo;
    private Object mObjeto;

    private String mRetornoTipo;

    public Run_Value(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mTipo = "";
        mPrimitivo = "";
        mObjeto = null;

    }

    public String getTipo() {
        return mTipo;
    }

    public String getPrimitivo() {
        return mPrimitivo;
    }

    public Object getObjeto() {
        return mObjeto;
    }

    public boolean getIsNulo() {
        return mTipo.contentEquals("NULL");
    }

    public boolean getIsPrimitivo() {
        return mTipo.contentEquals("PRIMITIVE");
    }

    public boolean getIsStruct() {
        return mTipo.contentEquals("STRUCT");
    }

    public String getRetornoTipo() {
        return mRetornoTipo;
    }


    public void init(AST ASTCorrente, String eReturne) {

        mRetornoTipo = eReturne;

        boolean isPrimitivo = mRunTime.isPrimitivo(eReturne);


        if (ASTCorrente.mesmoValor("NULL")) {

            mTipo = "NULL";

        } else if (ASTCorrente.mesmoValor("COMPARATOR")) {

            mRetornoTipo="bool";

            AST eModo = ASTCorrente.getBranch("MODE");

          //  System.out.println(" AST VALUE : " + ASTCorrente.getValor() + " : " + eModo.getNome() + " -> " + eReturne);


            Run_Value mRun_Esquerda = new Run_Value(mRunTime, mEscopo);
            mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), "<<ANY>>");

            if(mRunTime.getErros().size()>0){
                return;
            }

            Run_Value mRun_Direita = new Run_Value(mRunTime, mEscopo);
            mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), "<<ANY>>");

            if(mRunTime.getErros().size()>0){
                return;
            }

         //   System.out.println(" Tipagem : " + mRun_Esquerda.getRetornoTipo() + " : " + mRun_Direita.getRetornoTipo());
          //  System.out.println(" Comparando " + eModo.getNome() + " : " + mRun_Esquerda.getPrimitivo() + " : " + mRun_Direita.getPrimitivo() + " -> " + mPrimitivo);


            if (eModo.mesmoNome("MATCH")) {

                verificarIgualdade(mRun_Esquerda, mRun_Direita);


            } else if (eModo.mesmoNome("MISMATCH")) {

                verificarDesIgualdade(mRun_Esquerda, mRun_Direita);

            } else {
                mRunTime.getErros().add("Comparador Desconhecido : " + eModo.getNome());
            }


            mTipo = "PRIMITIVE";

        } else {

            if (isPrimitivo) {

                mTipo = "PRIMITIVE";


                if (ASTCorrente.getValor().contentEquals("Text")) {
                    mPrimitivo = ASTCorrente.getNome();
                    mRetornoTipo = "string";

                } else if (ASTCorrente.getValor().contentEquals("Num")) {
                    mPrimitivo = ASTCorrente.getNome();
                    mRetornoTipo = "num";

                } else if (ASTCorrente.getValor().contentEquals("ID")) {

                    if (ASTCorrente.mesmoNome("null")) {
                        mTipo = "NULL";
                    } else if (ASTCorrente.mesmoNome("true") || ASTCorrente.mesmoNome("false")) {

                        mPrimitivo = ASTCorrente.getNome();
                        mRetornoTipo = "bool";

                    } else {
                        mPrimitivo = mEscopo.getDefinido(ASTCorrente.getNome());
                        mRetornoTipo = mEscopo.getDefinidoTipo(ASTCorrente.getNome());

                    }


                } else if (ASTCorrente.getValor().contentEquals("FUNCT")) {

                    Run_Func mAST = new Run_Func(mRunTime, mEscopo);
                    mAST.init(ASTCorrente, eReturne);

                    if(mRunTime.getErros().size()>0){
                      return;
                    }

                    mRetornoTipo = mAST.getRetornoFunction();

                    if (mAST.getIsNulo()) {
                        mTipo = "NULL";
                    } else if (mAST.getIsPrimitivo()) {
                        mPrimitivo = mAST.getPrimitivo();
                    } else {
                        mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");
                    }


                }


            } else {

                mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");


            }


        }

    }


    public void verificarIgualdade(Run_Value mRun_Esquerda, Run_Value mRun_Direita) {

        if (mRun_Esquerda.getRetornoTipo().contentEquals(mRun_Direita.getRetornoTipo())) {

            if (mRun_Esquerda.getRetornoTipo().contentEquals("num")) {

                float f1 = Float.parseFloat(mRun_Esquerda.getPrimitivo());
                float f2 = Float.parseFloat(mRun_Direita.getPrimitivo());
                if (f1 == f2) {
                    mPrimitivo = "true";
                } else {
                    mPrimitivo = "false";
                }
            } else {

                if (mRun_Esquerda.getIsNulo() && mRun_Direita.getIsNulo()) {
                    mPrimitivo = "true";
                } else {

                    if (mRun_Esquerda.getPrimitivo().contentEquals(mRun_Direita.getPrimitivo())) {
                        mPrimitivo = "true";

                    } else {
                        mPrimitivo = "false";
                    }

                }

            }

        } else {

            mPrimitivo = "false";

        }

    }


    public void verificarDesIgualdade(Run_Value mRun_Esquerda, Run_Value mRun_Direita) {

        if (mRun_Esquerda.getRetornoTipo().contentEquals(mRun_Direita.getRetornoTipo())) {

            if (mRun_Esquerda.getRetornoTipo().contentEquals("num")) {

                float f1 = Float.parseFloat(mRun_Esquerda.getPrimitivo());
                float f2 = Float.parseFloat(mRun_Direita.getPrimitivo());
                if (f1 == f2) {
                    mPrimitivo = "false";
                } else {
                    mPrimitivo = "true";
                }
            } else {

                if (mRun_Esquerda.getIsNulo() && mRun_Direita.getIsNulo()) {
                    mPrimitivo = "false";
                } else {

                    if (mRun_Esquerda.getPrimitivo().contentEquals(mRun_Direita.getPrimitivo())) {
                        mPrimitivo = "false";

                    } else {
                        mPrimitivo = "true";
                    }

                }

            }

        } else {

            mPrimitivo = "true";

        }

    }
}
