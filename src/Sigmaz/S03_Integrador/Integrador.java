package Sigmaz.S03_Integrador;

import Sigmaz.S00_Utilitarios.Contador;
import Sigmaz.S00_Utilitarios.Mensageiro;
import Sigmaz.S00_Utilitarios.Visualizador.SigmazPackage;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.RunTime;

import java.io.File;
import java.util.ArrayList;

public class Integrador {

    private Mensageiro mMensageiro;
    private ArrayList<AST> mRequisicoes;
    private boolean mDebug;

    public Integrador() {
        mMensageiro = new Mensageiro();
        mRequisicoes = new ArrayList<AST>();
        mDebug = true;

    }

    public void setDebug(boolean e) {
        mDebug = e;
    }

    public void mensagem(String e) {
        if (mDebug) {
            mMensageiro.mensagem(e);
        }
    }

    public void errar(String e) {
        mMensageiro.errar(e);
    }

    public ArrayList<String> getErros() {
        return mMensageiro.getErros();
    }

    public ArrayList<String> getMensagens() {
        return mMensageiro.getMensagens();
    }

    public void init(ArrayList<AST> mTodos, String eLocalLibs) {


        mensagem("");
        mensagem("############################## INTEGRADOR ##############################");
        mensagem("");


        requisitar(mTodos, eLocalLibs);


        Simbolismo mSimbolismo = new Simbolismo(this);
        Valoramento mValoramento = new Valoramento(this);
        Referenciamento mReferenciamento = new Referenciamento(this);

        ArrayList<SigmazPackage> mPacotes = new ArrayList<SigmazPackage>();


        for (AST mAST : mTodos) {

            if (mAST.mesmoTipo("SIGMAZ")) {


                Integralizante mIntegralizante = new Integralizante();


                for (AST mRequisicao : mRequisicoes) {

                    mensagem("Biblioteca Externa");


                    mSimbolismo.realizarSimbolismoExterno("\t", mRequisicao, mIntegralizante);

                    for (AST eAlgumaCoisa : mRequisicao.getASTS()) {
                        if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                            mPacotes.add(new SigmazPackage(eAlgumaCoisa));
                        }
                    }


                }

                unidade(mSimbolismo, mValoramento, mReferenciamento, mPacotes, mIntegralizante, mAST);

            }
        }


    }


    public ArrayList<AST> getRequisicoes() {
        return mRequisicoes;
    }

    public ArrayList<Ligacao> getBibliotecas(AST eAST, String mLocalLibs) {

        ArrayList<Ligacao> mBibliotecas = new ArrayList<Ligacao>();

        for (AST ASTC : eAST.getASTS()) {
            if (ASTC.mesmoTipo("REQUIRED")) {

                String mReq = mLocalLibs + ASTC.getNome() + ".sigmad";

                if (!jaExiste(mBibliotecas,mReq)) {
                    mBibliotecas.add(new Ligacao(mReq,ASTC));
                }
            }

        }

        return mBibliotecas;
    }


    public boolean jaExiste(ArrayList<Ligacao> mBibliotecas, String eBiblioteca) {
        boolean ret = false;

        for (Ligacao eLigacao : mBibliotecas) {
            if (eLigacao.getBiblioteca().contentEquals(eBiblioteca)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public void requisitar(ArrayList<AST> mTodos, String mLocalLibs) {

        mensagem("");
        mensagem(" -->> Requisitando Bibliotecas");
        mensagem("");

        mRequisicoes.clear();

        // IMPORTANDO BIBLIOTECAS EXTERNAS


        ArrayList<Ligacao> mBibliotecas = new ArrayList<Ligacao>();


        for (AST ASTCGlobal : mTodos) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (Ligacao mBiblioteca : getBibliotecas(ASTCGlobal, mLocalLibs)) {

                    if (!jaExiste(mBibliotecas,mBiblioteca.getBiblioteca())) {
                        mBibliotecas.add(mBiblioteca);
                    }

                }

            }
        }

        ArrayList<String> mRequisicoes_Unicamente = new ArrayList<>();

        while (mBibliotecas.size() > 0) {

            Ligacao mBiblioteca = mBibliotecas.get(0);

            if (!mRequisicoes_Unicamente.contains(mBiblioteca)) {
                mensagem("Carregar Biblioteca : " + mBiblioteca);

                mRequisicoes_Unicamente.add(mBiblioteca.getBiblioteca());

                mensagem("Biblioteca Externa  -> " + mBiblioteca);


                File arq = new File(mBiblioteca.getBiblioteca());

                if (arq.exists()) {

                    RunTime RunTimeC = new RunTime();

                    try {
                        RunTimeC.init(mBiblioteca.getBiblioteca(), false);


                        mRequisicoes.add(RunTimeC.getBranch("SIGMAZ"));


                        mensagem("\t - Encontrada : Sim");
                        mensagem("\t - Status : OK");
                        mensagem("\t - Chave : " + RunTimeC.getShared());


                        AST mBibliotecaAST = RunTimeC.getBranch("SIGMAZ");


                        for (Ligacao mBibliotecaSecundaria : getBibliotecas(mBibliotecaAST, mLocalLibs)) {

                            if (!jaExiste(mBibliotecas,mBibliotecaSecundaria.getBiblioteca())) {
                                mBibliotecas.add(mBibliotecaSecundaria);
                            }

                        }


                        Contador mContador = new Contador();
                        mContador.init(mBibliotecaAST);


                        mBiblioteca.getAST().setValor(RunTimeC.getShared());

                        if (mDebug) {

                            mensagem("");

                            mensagem("\t - Actions : " + mContador.getActions());
                            mensagem("\t - Functions : " + mContador.getFunctions());
                            mensagem("\t - Autos : " + mContador.getAutos());
                            mensagem("\t - Functors : " + mContador.getFunctors());
                            mensagem("\t - Casts : " + mContador.getCasts());
                            mensagem("\t - Stages : " + mContador.getStages());
                            mensagem("\t - Types : " + mContador.getTypes());
                            mensagem("\t - Structs : " + mContador.getStructs());
                            mensagem("\t - Models : " + mContador.getModels());
                            mensagem("\t - Externals : " + mContador.getExternals());
                            mensagem("\t - Packages : " + mContador.getPackages());

                        }


                    } catch (Exception e) {

                        if (mDebug) {
                            mensagem("\t - Status : Corrompida");
                        }

                        errar("Biblioteca " + mBiblioteca + " : Problema ao carregar !");

                        for (String mLibErro : RunTimeC.getErros()) {
                            errar("Biblioteca " + mBiblioteca + " : " + mLibErro);
                        }
                    }

                } else {
                    if (mDebug) {
                        mensagem("\t - Encontrada : Nao");
                    }
                    errar("Biblioteca " + mBiblioteca + " : Nao encontrada !");

                }

            }


            mBibliotecas.remove(mBiblioteca);

        }


    }


    public SigmazPackage getPackage(String eNome, ArrayList<SigmazPackage> mPacotes) {

        SigmazPackage mRet = null;

        for (SigmazPackage ePacote : mPacotes) {
            if (ePacote.mesmoNome(eNome)) {
                mRet = ePacote;
                break;
            }
        }

        return mRet;
    }

    public void unidade(Simbolismo mSimbolismo, Valoramento mValoramento, Referenciamento mReferenciamento, ArrayList<SigmazPackage> mPacotes, Integralizante mIntegralizante, AST mAST) {


        mMensageiro.mensagem("");

        for (AST eAlgumaCoisa : mAST.getASTS()) {
            if (eAlgumaCoisa.mesmoTipo("PACKAGE")) {
                mPacotes.add(new SigmazPackage(eAlgumaCoisa));


            }
        }

        if (mPacotes.size() > 0) {

            mensagem("PACOTES : " + mPacotes.size());
            mensagem("");
            int pi = 1;


            for (SigmazPackage ePacote : mPacotes) {
                mensagem("\t - " + pi + " : " + ePacote.getNome());
                pi += 1;
            }

            mensagem("");

        }

        mensagem("SIGMAZ");
        mensagem("");


        if (mMensageiro.temErros()) {
            return;
        }

        mensagem("REFERS : ");

        mReferenciamento.processar_Refers("\t", mPacotes, mAST, mIntegralizante);

        mensagem("SIMBOLISMO : ");

        mSimbolismo.realizarSimbolismo("\t", mAST, mIntegralizante);

        mensagem("VALORAMENTO : ");

        Escopante mEscopoGlobal = new Escopante("GLOBAL");

        mValoramento.realizarValoramento("\t", mAST, mIntegralizante, mEscopoGlobal);


        for (SigmazPackage ePacote : mPacotes) {

            mensagem("Pacote : " + ePacote.getNome());

            Integralizante mIntegralizantePackage = new Integralizante();

            mIntegralizantePackage.adicionarSigmaz(mIntegralizante);

            mReferenciamento.processar_Refers("\t\t", mPacotes, ePacote.getAST(), mIntegralizantePackage);

            if (mMensageiro.temErros()) {
                return;
            }

            mSimbolismo.realizarSimbolismo("\t\t", ePacote.getAST(), mIntegralizantePackage);

            Escopante mEscopoPacote = new Escopante("PACKAGE :: " + ePacote.getNome());
            mEscopoPacote.setAnterior(mEscopoGlobal);


            mValoramento.realizarValoramento("\t\t", ePacote.getAST(), mIntegralizantePackage, mEscopoPacote);


            if (mMensageiro.getErros().size() > 0) {
                break;
            }
        }

    }

}
