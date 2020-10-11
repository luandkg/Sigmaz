package Sigmaz.S05_PosProcessamento;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S05_PosProcessamento.Processadores.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.UUID;

public class PosProcessador {

    private ArrayList<AST> mASTS;

    private ArrayList<AST> mPacotes;

    private Mensageiro mMensageiro;

    public PosProcessador() {

        mASTS = new ArrayList<>();

        mMensageiro = new Mensageiro();

        mPacotes = new ArrayList<AST>();

    }


    public void init(Cabecalho mCabecalho, ArrayList<AST> eASTs, String mLocalLibs) {

        mASTS = eASTs;
        mPacotes.clear();
        mMensageiro.limpar();

        mensagem("");

        ArrayList<AST> mRequisicoes = new ArrayList<AST>();

        if (tudoOK()) {
            Requeridor mRequeridor = new Requeridor(this);
            mRequeridor.init(mASTS,mLocalLibs,mRequisicoes);
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
            mValorador.init(mASTS,mRequisicoes);
        }

        gravarCabecalho(mCabecalho, mASTS);


    }

    public boolean temErros() {
        return mMensageiro.temErros();
    }

    public boolean tudoOK() {
        return mMensageiro.tudoOK();
    }

    public Mensageiro getMensageiro(){return mMensageiro;}

        public void gravarCabecalho(Cabecalho eCabecalho, ArrayList<AST> mASTS) {

        UUID mUUID = new UUID();


        for (AST eAST : mASTS) {


            AST mCabecalho = new AST("HEADER");
            mCabecalho.setNome("1");
            mCabecalho.setValor("Num");

            AST ma = mCabecalho.criarBranch("AUTHORES");

            for (String eAutor : eCabecalho.getAutores()) {

                AST tmpA = ma.criarBranch("AUTHOR");
                tmpA.setValor(eAutor);

            }

            AST AVersao = mCabecalho.criarBranch("VERSION");
            AVersao.setValor(eCabecalho.getVersao());

            AST AC = mCabecalho.criarBranch("COMPANY");
            AC.setValor(eCabecalho.getCompanhia());

            AST ePrivado = mCabecalho.criarBranch("PRIVATE");
            ePrivado.setValor(mUUID.getUUID());

            AST ePublico = mCabecalho.criarBranch("PUBLIC");
            ePublico.setValor(mUUID.getUUID());

            AST eShared = mCabecalho.criarBranch("SHARED");
            eShared.setValor(mUUID.getUUID());

            AST eDevelopment = mCabecalho.criarBranch("DEVELOPMENT");

            AST ePre = eDevelopment.criarBranch("PREPROCESSOR");
            ePre.setNome(eCabecalho.getPreProcessor());
            ePre.setValor(eCabecalho.getPreProcessor_UUID());


            AST eLexer = eDevelopment.criarBranch("LEXER");
            eLexer.setNome(eCabecalho.getLexer());
            eLexer.setValor(eCabecalho.getLexer_UUID());

            AST eParser = eDevelopment.criarBranch("PARSER");
            eParser.setNome(eCabecalho.getParser());
            eParser.setValor(eCabecalho.getParser_UUID());

            AST eCompiler = eDevelopment.criarBranch("COMPILER");
            eCompiler.setNome(eCabecalho.getCompiler());
            eCompiler.setValor(eCabecalho.getCompiler_UUID());

            AST ePosProcessor = eDevelopment.criarBranch("POSPROCESSOR");
            ePosProcessor.setNome(eCabecalho.getPosProcessor());
            ePosProcessor.setValor(eCabecalho.getPosProcessor_UUID());

            eAST.getASTS().add(0, mCabecalho);

        }


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
