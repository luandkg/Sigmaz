package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Value {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private boolean mIsNulo;
    private boolean mIsPrimitivo;


    private String mConteudo;
    private Object mObjeto;

    private String mRetornoTipo;

    public Run_Value(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mIsNulo = false;
        mIsPrimitivo = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;

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

    public void init(AST ASTCorrente, String eRetorno) {

        mRetornoTipo = eRetorno;


        if (ASTCorrente.mesmoValor("NULL")) {

            mIsNulo = true;

        } else if (ASTCorrente.mesmoValor("Text")) {

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "string";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Num")) {

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "num";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("ID")) {


            if (ASTCorrente.mesmoNome("true") || ASTCorrente.mesmoNome("false")) {

                mIsNulo = false;
                mIsPrimitivo = true;
                mRetornoTipo = "bool";
                mConteudo = ASTCorrente.getNome();

            } else if (ASTCorrente.mesmoNome("null")) {

                // System.out.println("ANULANDO INIT  -> " + ASTCorrente.getNome());

                mIsNulo = true;
                mRetornoTipo = eRetorno;

            } else {

                Item mItem = mEscopo.getItem(ASTCorrente.getNome());

                if (mItem != null) {

                    mIsNulo =mItem.getNulo();
                    mRetornoTipo = mItem.getTipo();
                    mConteudo = mItem.getValor();


                    if (mRetornoTipo.contentEquals("bool")) {
                        mIsPrimitivo = true;
                    } else if (mRetornoTipo.contentEquals("num")) {
                        mIsPrimitivo = true;
                    } else if (mRetornoTipo.contentEquals("string")) {
                        mIsPrimitivo = true;
                    }

                }



                if (mRunTime.getErros().size() > 0) {
                    return;
                }



            }


        } else if (ASTCorrente.mesmoValor("BLOCK")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);

        } else if (ASTCorrente.mesmoValor("COMPARATOR")) {

            mRetornoTipo = "bool";
            mIsPrimitivo = true;

            AST eModo = ASTCorrente.getBranch("MODE");


            Run_Value mRun_Esquerda = new Run_Value(mRunTime, mEscopo);
            mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            Run_Value mRun_Direita = new Run_Value(mRunTime, mEscopo);
            mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }


            if (eModo.mesmoNome("MATCH")) {

                verificarIgualdade(mRun_Esquerda, mRun_Direita);


            } else if (eModo.mesmoNome("MISMATCH")) {

                verificarDesIgualdade(mRun_Esquerda, mRun_Direita);


            } else {
                mRunTime.getErros().add("Comparador Desconhecido : " + eModo.getNome());
            }


        } else if (ASTCorrente.getValor().contentEquals("FUNCT")) {

            //  System.out.println("FUNCT INIT  -> " + ASTCorrente.getNome());

            Run_Func mAST = new Run_Func(mRunTime, mEscopo);
            mAST.init(ASTCorrente, eRetorno);


            if (mRunTime.getErros().size() > 0) {
             return;
              }


            mIsNulo = mAST.getIsNulo();
            mIsPrimitivo = mAST.getIsPrimitivo();
            mConteudo = mAST.getConteudo();
            mRetornoTipo = mAST.getRetornoFunction();

            //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + mAST.getConteudo() + " P : " + mIsPrimitivo + " N : " + mIsNulo);


        } else {

            //   System.out.println("PROBLEMA  -> " + ASTCorrente.getValor());

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");


        }

    }


    public void verificarIgualdade(Run_Value mRun_Esquerda, Run_Value mRun_Direita) {

        if (mRun_Esquerda.getRetornoTipo().contentEquals(mRun_Direita.getRetornoTipo())) {

            if (mRun_Esquerda.getRetornoTipo().contentEquals("num")) {

                float f1 = Float.parseFloat(mRun_Esquerda.getConteudo());
                float f2 = Float.parseFloat(mRun_Direita.getConteudo());
                if (f1 == f2) {
                    mConteudo = "true";
                } else {
                    mConteudo = "false";
                }
            } else {

                if (mRun_Esquerda.getIsNulo() && mRun_Direita.getIsNulo()) {
                    mConteudo = "true";
                } else {

                    if (mRun_Esquerda.getConteudo().contentEquals(mRun_Direita.getConteudo())) {
                        mConteudo = "true";

                    } else {
                        mConteudo = "false";
                    }

                }

            }

        } else {

            mConteudo = "false";

        }

    }


    public void verificarDesIgualdade(Run_Value mRun_Esquerda, Run_Value mRun_Direita) {

        if (mRun_Esquerda.getRetornoTipo().contentEquals(mRun_Direita.getRetornoTipo())) {

            if (mRun_Esquerda.getRetornoTipo().contentEquals("num")) {

                float f1 = Float.parseFloat(mRun_Esquerda.getConteudo());
                float f2 = Float.parseFloat(mRun_Direita.getConteudo());
                if (f1 == f2) {
                    mConteudo = "false";
                } else {
                    mConteudo = "true";
                }
            } else {

                if (mRun_Esquerda.getIsNulo() && mRun_Direita.getIsNulo()) {
                    mConteudo = "false";
                } else {

                    if (mRun_Esquerda.getConteudo().contentEquals(mRun_Direita.getConteudo())) {
                        mConteudo = "false";

                    } else {
                        mConteudo = "true";
                    }

                }

            }

        } else {

            mConteudo = "true";

        }

    }
}
