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

    public PosProcessador() {

        mASTS = new ArrayList<>();

        mMensageiro = new Mensageiro();

        mPacotes = new ArrayList<AST>();
        mRequisicoes = new ArrayList<AST>();

    }

    public ArrayList<AST> getRequisicoes(){ return mRequisicoes;}


    public void init(Cabecalho mCabecalho, ArrayList<AST> eASTs, String mLocalLibs) {

        mASTS = eASTs;
        mPacotes.clear();
        mMensageiro.limpar();
        mRequisicoes.clear();

        mensagem("");


        if (tudoOK()) {
            Requeridor mRequeridor = new Requeridor(this);
            mRequeridor.init(mASTS, mLocalLibs);
            mRequisicoes = mRequeridor.getRequisicoes();
        }

        if (tudoOK()) {
            Alocador mAlocador = new Alocador(this);
            mAlocador.init(mASTS);
        }

        if (tudoOK()) {
            Castificador mCastificador = new Castificador(this);
            mCastificador.init(mASTS);
        }
        if (tudoOK()) {

            Unificador mUnificador = new Unificador(this);
            mUnificador.init(mASTS);
        }
        if (tudoOK()) {
            Heranca mHeranca = new Heranca(this);
            mHeranca.init(mASTS);
        }

        if (tudoOK()) {

            Modelador mModelador = new Modelador(this);
            mModelador.init(mASTS);
        }

        if (tudoOK()) {
            Estagiador mEstagiador = new Estagiador(this);
            mEstagiador.init(mASTS);
        }

        if (tudoOK()) {
            Tipador mTipador = new Tipador(this);
            mTipador.init(mASTS);
        }


        if (tudoOK()) {
            Referenciador mReferenciador = new Referenciador(this);
            mReferenciador.init(mASTS);
        }

        if (tudoOK()) {
            Argumentador mArgumentador = new Argumentador(this);
            mArgumentador.init(mASTS);
        }

        if (tudoOK()) {
            Opcionador mOpcionador = new Opcionador(this);
            mOpcionador.init(mASTS);

        }


        if (tudoOK()) {
            Valorador mValorador = new Valorador(this);
            mValorador.init(mASTS, mRequisicoes);
        }



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
