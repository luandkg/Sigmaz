package Sigmaz.S05_PosProcessamento;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.UUID;

public class PosProcessador {

    private ArrayList<AST> mASTS;
    private ArrayList<AST> mRequisicoes;

    private ArrayList<AST> mPacotes;

    private Mensageiro mMensageiro;

    private boolean mDebug_Requisidor;
    private boolean mDebug_Alocador;
    private boolean mDebug_Modelador;
    private boolean mDebug_Estagiador;
    private boolean mDebug_Tipador;
    private boolean mDebug_Valorador;
    private boolean mDebug_Cast;
    private boolean mDebug_UnionType;
    private boolean mDebug_Heranca;
    private boolean mDebug_Referenciador;
    private boolean mDebug_Argumentador;
    private boolean mDebug_Opcionador;

    private String mFase;
    private String mLocalLibs;

    private int mPosInt;
    private int mPosMax;
    private boolean mTerminou;

    public PosProcessador() {

        mASTS = new ArrayList<>();

        mMensageiro = new Mensageiro();

        mPacotes = new ArrayList<AST>();
        mRequisicoes = new ArrayList<AST>();

        mDebug_Requisidor = true;
        mDebug_Alocador = true;
        mDebug_Modelador = true;
        mDebug_Estagiador = true;
        mDebug_Tipador = true;
        mDebug_Valorador = true;
        mDebug_Cast = true;
        mDebug_UnionType = true;
        mDebug_Heranca = true;
        mDebug_Referenciador = true;
        mDebug_Argumentador = true;
        mDebug_Opcionador = true;

        mFase = "";

    }


    public void setDebug_Requisidor(boolean e) {
        mDebug_Requisidor = e;
    }

    public void setDebug_Alocador(boolean e) {
        mDebug_Alocador = e;
    }


    public void setDebug_Modelador(boolean e) {
        mDebug_Modelador = e;
    }


    public void setDebug_Estagiador(boolean e) {
        mDebug_Estagiador = e;
    }

    public void setDebug_Tipador(boolean e) {
        mDebug_Tipador = e;
    }

    public void setDebug_Valorador(boolean e) {
        mDebug_Valorador = e;
    }

    public void setDebug_Cast(boolean e) {
        mDebug_Cast = e;
    }

    public void setDebug_UnionType(boolean e) {
        mDebug_UnionType = e;
    }

    public void setDebug_Heranca(boolean e) {
        mDebug_Heranca = e;
    }

    public void setDebug_Referenciador(boolean e) {
        mDebug_Referenciador = e;
    }

    public void setDebug_Argumentador(boolean e) {
        mDebug_Argumentador = e;
    }

    public void setDebug_Opcionador(boolean e) {
        mDebug_Opcionador = e;
    }

    public boolean getDebug_Requeridor() {
        return mDebug_Requisidor;
    }

    public boolean getDebug_Alocador() {
        return mDebug_Alocador;
    }

    public boolean getDebug_Modelador() {
        return mDebug_Modelador;
    }

    public boolean getDebug_Estagiador() {
        return mDebug_Estagiador;
    }

    public boolean getDebug_Tipador() {
        return mDebug_Tipador;
    }

    public boolean getDebug_Valorador() {
        return mDebug_Valorador;
    }

    public boolean getDebug_Cast() {
        return mDebug_Cast;
    }

    public boolean getDebug_UnionType() {
        return mDebug_UnionType;
    }

    public boolean getDebug_Heranca() {
        return mDebug_Heranca;
    }

    public boolean getDebug_Referenciador() {
        return mDebug_Referenciador;
    }

    public boolean getDebug_Argumentador() {
        return mDebug_Argumentador;
    }

    public boolean getDebug_Opcionador() {
        return mDebug_Opcionador;
    }


    public ArrayList<AST> getRequisicoes() {
        return mRequisicoes;
    }


    public void init(ArrayList<AST> eASTs, String eLocalLibs) {

        initPassos(eASTs, eLocalLibs);


        while (!getTerminou()) {
            continuar();
        }


    }


    public void initPassos(ArrayList<AST> eASTs, String eLocalLibs) {

        mFase = "";
        mPosInt = 0;
        mPosMax = 11;
        mTerminou = false;


        mASTS = eASTs;
        mLocalLibs = eLocalLibs;
        mPacotes.clear();
        mMensageiro.limpar();
        mRequisicoes.clear();


        mFase = "Init";
    }

    public void continuar() {

        if (mPosInt > mPosMax) {
            mTerminou = true;
        } else {

            if (mPosInt == 0) {

                mFase = "Requeridor";

                if (tudoOK()) {
                    Requeridor mRequeridor = new Requeridor(this);
                    mRequeridor.init(mASTS, mLocalLibs);
                    mRequisicoes = mRequeridor.getRequisicoes();
                }

            } else if (mPosInt == 1) {

                mFase = "Alocador";

                if (tudoOK()) {
                    Alocador mAlocador = new Alocador(this);
                    mAlocador.init(mASTS);
                }
            } else if (mPosInt == 2) {

                mFase = "Castificador";

                if (tudoOK()) {
                    Castificador mCastificador = new Castificador(this);
                    mCastificador.init(mASTS);
                }
            } else if (mPosInt == 3) {

                mFase = "Unificador";

                if (tudoOK()) {

                    Unificador mUnificador = new Unificador(this);
                    mUnificador.init(mASTS);
                }
            } else if (mPosInt == 4) {

                mFase = "Heranca";

                if (tudoOK()) {
                    Heranca mHeranca = new Heranca(this);
                    mHeranca.init(mASTS);
                }

            } else if (mPosInt == 5) {

                mFase = "Modelador";

                if (tudoOK()) {

                    Modelador mModelador = new Modelador(this);
                    mModelador.init(mASTS);
                }
            } else if (mPosInt == 6) {
                mFase = "Estagiador";

                if (tudoOK()) {
                    Estagiador mEstagiador = new Estagiador(this);
                    mEstagiador.init(mASTS);
                }
            } else if (mPosInt == 7) {
                mFase = "Tipador";

                if (tudoOK()) {
                    Tipador mTipador = new Tipador(this);
                    mTipador.init(mASTS);
                }

            } else if (mPosInt == 8) {

                mFase = "Referenciador";

                if (tudoOK()) {
                    Referenciador mReferenciador = new Referenciador(this);
                    mReferenciador.init(mASTS);
                }
            } else if (mPosInt == 9) {

                mFase = "Argumentador";
                if (tudoOK()) {
                    Argumentador mArgumentador = new Argumentador(this);
                    mArgumentador.init(mASTS);
                }
            } else if (mPosInt == 10) {

                mFase = "Opcionador";
                if (tudoOK()) {
                    Opcionador mOpcionador = new Opcionador(this);
                    mOpcionador.init(mASTS);

                }
            } else if (mPosInt == 11) {

                mFase = "Valorador";

                if (tudoOK()) {
                    Valorador mValorador = new Valorador(this);
                    mValorador.init(mASTS, mRequisicoes);
                }


            }


            mPosInt += 1;
        }

    }

    public String getFase() {
        return mFase;
    }

    public boolean getTerminou() {
        return mTerminou;
    }


    public boolean temErros() {
        return mMensageiro.temErros();
    }

    public boolean tudoOK() {
        return mMensageiro.tudoOK();
    }

    public Mensageiro getMensageiro() {
        return mMensageiro;
    }


    public ArrayList<String> getErros() {
        return mMensageiro.getErros();
    }

    public void mensagem(String eMensagem) {
        mMensageiro.mensagem(eMensagem);
    }

    public void errar(String eErro) {
        mMensageiro.errar(eErro);
    }

    public ArrayList<String> getMensagens() {
        return mMensageiro.getMensagens();
    }


    public void MostrarMensagens() {

        System.out.println("\n\t MENSAGENS DE POS-PROCESSAMENTO : ");

        for (String Erro : getMensagens()) {
            System.out.println("\t" + Erro);
        }

    }

}
