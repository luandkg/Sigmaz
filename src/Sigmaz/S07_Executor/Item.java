package Sigmaz.S07_Executor;

import Sigmaz.S07_Executor.Runners.Run_Arrow;
import Sigmaz.S00_Utilitarios.AST;

public class Item {

    private String mNome;
    private boolean mNulo;
    private String mTipo;
    private int mModo;
    private boolean mPrimitivo;
    private boolean mEstrutura;

    private String mValor;

    private boolean mIsReferenciavel;
    private Item mReferencia;

    private String mReferStruct;
    private String mReferCampo;


    public Item(String eNome) {
        this.mNome = eNome;
        mValor = "";
        mModo = 0;
        mTipo = "";
        mNulo = false;
        mPrimitivo = true;
        mEstrutura = false;

        mIsReferenciavel = false;
        mReferencia = null;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }


    public boolean getNulo() {
        return mNulo;
    }

    public void setNulo(boolean eNulo) {
        mNulo = eNulo;
    }

    public void setModo(int eModo) {
        mModo = eModo;
    }

    public int getModo() {
        return mModo;
    }

    public void setValor(String eValor, RunTime eRunTime, Escopo eEscopo) {

        if (this.getModo() == 5) {
            //  System.out.println("SET EXTERNAMENTE -->> " + this.getNome() + " para " + this.mReferStruct + " -> " + mReferCampo);

            //   LEFT -> XA : STRUCT_EXTERN
            //   INTERNAL -> mValor : STRUCT_OBJECT

            AST mV = new AST("VALUE");
            mV.setNome(mReferStruct);
            mV.setValor("STRUCT_EXTERN");
            AST mInternal = mV.criarBranch("INTERNAL");
            mInternal.setNome(mReferCampo);
            mInternal.setValor("STRUCT_OBJECT");

            Run_Arrow mRun_Arrow = new Run_Arrow(eRunTime);
            mRun_Arrow.operadorSeta(mV, eEscopo, this.getTipo()).setValor(eValor, eRunTime, eEscopo);

            mValor = eValor;

        } else if (this.getModo() == 6) {
            eRunTime.getErros().add("O valor de uma CONSTANTE REFERED nao pode ser alterado : " + this.getNome() + " :: " + this.mReferStruct + "->" + this.mReferCampo);
        } else {
            mValor = eValor;
        }

    }

    public String getValor(RunTime eRunTime, Escopo eEscopo) {

        // System.out.println("RET :: " + this.getModo() + " ->> " + this.getNome());

        if (this.getModo() == 5 || this.getModo() == 6) {
            // System.out.println("GET EXTERNAMENTE -->> " + this.getNome() + " para " + this.mReferStruct + " -> " + mReferCampo);

            AST mV = new AST("VALUE");
            mV.setNome(mReferStruct);
            mV.setValor("STRUCT_EXTERN");
            AST mInternal = mV.criarBranch("INTERNAL");
            mInternal.setNome(mReferCampo);
            mInternal.setValor("STRUCT_OBJECT");

            Run_Arrow mRun_Arrow = new Run_Arrow(eRunTime);
            return mRun_Arrow.operadorSeta(mV, eEscopo, this.getTipo()).getValor(eRunTime, eEscopo);




        } else {
            return mValor;
        }


    }

    public String getNome() {
        return mNome;
    }

    public void setTipo(String eTipo) {
        mTipo = eTipo;
    }

    public String getTipo() {
        return mTipo;
    }

    public void setPrimitivo(boolean ePrimitivo) {
        mPrimitivo = ePrimitivo;
    }

    public boolean getPrimitivo() {
        return mPrimitivo;
    }

    public void setIsEstrutura(boolean eEstrutura) {
        mEstrutura = eEstrutura;
    }

    public boolean getIsEstrutura() {
        return mEstrutura;
    }


    public boolean getIsReferenciavel() {
        return mIsReferenciavel;
    }

    public void setIsReferenciavel(boolean eIsReferenciavel) {
        mIsReferenciavel = eIsReferenciavel;
    }

    public Item getReferencia() {
        return mReferencia;
    }

    public void setReferencia(Item eReferencia) {
        mReferencia = eReferencia;
    }


    public void setRefer(String eStruct, String eCampo) {
        this.mReferStruct = eStruct;
        this.mReferCampo = eCampo;
        this.setModo(5);
    }

    public void setReferConst(String eStruct, String eCampo) {
        this.mReferStruct = eStruct;
        this.mReferCampo = eCampo;
        this.setModo(6);
    }


    public void setImplicit(String eStruct, String eCampo) {
        this.mReferStruct = eStruct;
        this.mReferCampo = eCampo;
        this.setModo(7);
    }

    public void setImplicitConst(String eStruct, String eCampo) {
        this.mReferStruct = eStruct;
        this.mReferCampo = eCampo;
        this.setModo(8);
    }

}