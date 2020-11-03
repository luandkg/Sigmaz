package Sigmaz;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_PreProcessamento.Etapa;
import Sigmaz.S04_Compilador.Compilador;
import Sigmaz.S06_Integrador.Integrador;
import Sigmaz.S07_Montador.Montador;
import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.S05_PosProcessamento.PosProcessador;

import java.io.File;
import java.util.ArrayList;

public class Sigmaz_Etapas {

    private Cabecalho mCabecalho;
    private ArrayList<String> mErros_Mensagens;
    private Fases mFase;

    private String mETAPA_PRE_PROCESSAMENTO;
    private String mETAPA_LEXER;
    private String mETAPA_PARSER;
    private String mETAPA_POS_PROCESSAMENTO;
    private String mETAPA_MONTAGEM;
    private String mETAPA_INTEGRALIZACAO;

    private String mSTATUS_NAO;
    private String mSTATUS_SUCESSO;
    private String mSTATUS_FALHOU;

    private boolean mDebug;
    private ArrayList<String> mDebugMensagens;
    private ArrayList<String> mRequisitados;

    private int mContinuar;
    private String mLocal;
    private String mSaida;
    private String mArquivo;
    private int mOpcao;

    private boolean mPre;
    private int mPreInt;

    private boolean mEstaPreProcessando;
    private String mPreProcessando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;

    private Chronos mChronos;

    private int mIChars;
    private int mITokens;

    private String mPreProcessamento;
    private String mLexer_Processamento;

    private ArrayList<Etapa> mEtapas;

    private Compilador mPreProcessador;

    private ArrayList<GrupoDeComentario> mComentarios;


    private ArrayList<GrupoDeErro> mErros_PreProcessamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;
    private ArrayList<String> mErros_PosProcessamento;
    private ArrayList<String> mErros_Execucao;

    private ArrayList<AST> mASTS;

    private boolean mTemErros;

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    private PosProcessador PosProcessadorC;
    private String mEtapa;
    private String mSubEtapa;

    private Montador MontadorC;
    private int mPosInt;
    private int mPassos;

    private boolean mDebugar;

    public enum Fases {
        PRE_PROCESSAMENTO, LEXER, PARSER, POS_PROCESSAMENTO, INTEGRALIZACAO, MONTAGEM, PRONTO;
    }

    public boolean estaPre() {
        return mCorrentePreprocessando;
    }

    public boolean estaParseando() {
        return mCorrenteParseando;
    }

    public boolean estaCompilando() {
        return mCorrenteCompilando;
    }


    public Sigmaz_Etapas() {


        mCabecalho = new Cabecalho();

        mSTATUS_NAO = "NAO REALIZADO";
        mSTATUS_SUCESSO = "SUCESSO";
        mSTATUS_FALHOU = "FALHOU";


        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_INTEGRALIZACAO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mASTS = new ArrayList<AST>();

        mDebug = false;
        mDebugMensagens = new ArrayList<String>();
        mErros_Mensagens = new ArrayList<String>();
        mContinuar = -1;
        mSaida = "";
        mLocal = "";
        mArquivo = "";
        mOpcao = 0;
        mPreInt = 0;
        mEstaPreProcessando = false;
        mPreProcessando = "";
        mPassos = 0;

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

        mChronos = new Chronos();

        mEtapas = new ArrayList<Etapa>();

        mComentarios = new ArrayList<GrupoDeComentario>();
        mRequisitados = new ArrayList<String>();

        mIChars = 0;
        mITokens = 0;
        mPreProcessamento = "";
        mLexer_Processamento = "";

        mPreInt = 0;

        mEtapas = new ArrayList<Etapa>();
        mTemErros = false;


        mErros_PreProcessamento = new ArrayList<GrupoDeErro>();
        mErros_Lexer = new ArrayList<GrupoDeErro>();
        mErros_Compiler = new ArrayList<GrupoDeErro>();
        mErros_PosProcessamento = new ArrayList<String>();
        mErros_Execucao = new ArrayList<String>();

        mEtapa = "";
        mDebugar=false;

    }


    public void init(String eArquivo, String eSaida, int eOpcao,boolean eDebugar) {

        limpar();

        mDebugar=eDebugar;

        mSaida = eSaida;
        mArquivo = eArquivo;
        mOpcao = eOpcao;

        File arq = new File(mSaida);
        mLocal = arq.getParent() + "/";

        String eModo = "DESCONHECIDO";

        if (mOpcao == 1) {
            eModo = "EXECUTAVEL";
        }

        if (mOpcao == 2) {
            eModo = "BIBLIOTECA";
        }

        mFase = Fases.PRE_PROCESSAMENTO;

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;


        System.out.println("");
        System.out.println("################ SIGMAZ ETAPAS ################");
        System.out.println("");

        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Local  : " + mLocal);
        System.out.println("\t - Build  : " + mSaida);
        System.out.println("\t - Modo   : " + eModo);

        System.out.println("");


        mContinuar = 0;

        mPreInt = 0;
        mPosInt = 0;

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

        mEtapa = "Pre-Processamento";

    }


    public int getPassos() {
        return mPassos;
    }

    public void continuar() {

        verificarSeTemErros();

        mPassos += 1;


        if (mContinuar == 0 && mPreInt == 0) {
            executar_init_01();
            return;

        }

        if (mContinuar == 0 && mPreInt == 1) {
            executar_init_02();
            return;

        }

        if (mContinuar == 0 && mPreInt == 2) {
            executar_init_03();
            return;

        }


        if (mContinuar == 1) {
            executar_Lexer();
            System.out.println("\t - 2 : Lexer                   : " + mETAPA_LEXER);
            return;
        }

        if (mContinuar == 2) {
            executar_Parser();
            System.out.println("\t - 3 : Parser                  : " + mETAPA_PARSER);
            return;
        }


        if (mContinuar == 3 && mPosInt == 0) {
            iniciar_PosProcessamento(mLocal);
            return;
        }

        if (mContinuar == 3 && mPosInt == 1) {
            continuar_PosProcessamento();
            return;
        }

        if (mContinuar == 3 && mPosInt == 2) {
            terminar_PosProcessamento();
            return;
        }


        if (mContinuar == 4) {
            organizar();
            return;
        }

        if (mContinuar == 5) {
            integralizar_iniciar();
            return;
        }

        if (mContinuar == 6) {
            integralizar_realizar();
            return;
        }

        if (mContinuar == 7) {
            montagem_iniciar(mSaida);
            return;
        }

        if (mContinuar == 8) {
            montagem_continuar();
            return;
        }

        if (mContinuar == 9) {
            montagem_terminar();
            return;
        }

        if (mContinuar == 10) {
            terminar();
            return;
        }

        if (mContinuar == 10) {
            mEtapa = "";
            mSubEtapa = "";
        }


    }

    public void verificarSeTemErros() {

        if (mErros_Mensagens.size() > 0) {
            mTemErros = true;
        }

        if (mTemErros) {
            mContinuar = 8;
        }
    }

    public void executar_init_01() {
        mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO";

        mEstaPreProcessando = true;

        executar_PreProcessamento(mArquivo, mOpcao,mDebugar);

    }

    public void executar_init_02() {

        mEstaPreProcessando = true;


        continuar_PreProcessamento();


        if (getEstaPreProcessando()) {

            mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO";

            if (estaPre()) {

                mEtapa = "Pre-Processador";
                mSubEtapa = getPreprocessando();

                mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO -->> " + getPreprocessando();
            }

            if (estaParseando()) {

                mEtapa = "Lexer";
                mSubEtapa = getPreprocessando();

                mETAPA_PARSER = "PRONTO";
                mETAPA_LEXER = "EXECUTANDO -->> " + getPreprocessando();
            }

            if (estaCompilando()) {

                mEtapa = "Parser";
                mSubEtapa = getPreprocessando();

                mETAPA_LEXER = "PRONTO";
                mETAPA_PARSER = "EXECUTANDO -->> " + getPreprocessando();
            }

        }
    }

    public void executar_init_03() {

        mEstaPreProcessando = true;
        mETAPA_PRE_PROCESSAMENTO = "CONCLUIDO";
        mSubEtapa = "Parser";

        mEtapa = "Pre-Processamento";
        mSubEtapa = "Finalize";

        if (mContinuar == 0) {
            finalizar_PreProcessamento();
            mContinuar = 1;

        }

        mEstaPreProcessando = false;

    }


    private void continuar_PosProcessamento() {

        if (mPosInt == 1) {

            PosProcessadorC.continuar();

            mSubEtapa = PosProcessadorC.getFase();

            mETAPA_POS_PROCESSAMENTO = "EXECUTANDO -->> " + PosProcessadorC.getFase();


            if (PosProcessadorC.getTerminou()) {

                mPosInt = 2;

                if (PosProcessadorC.getErros().size() == 0) {
                    mETAPA_POS_PROCESSAMENTO = mSTATUS_SUCESSO;
                } else {
                    mETAPA_POS_PROCESSAMENTO = mSTATUS_FALHOU;
                }

            }

        }

    }

    public void terminar_PosProcessamento() {

        if (mPosInt == 2) {

            for (String eMensagem : PosProcessadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }


            if (PosProcessadorC.getErros().size() == 0) {

                mContinuar = 4;
                mSubEtapa = "";
                mEtapa = "";

            } else {

                mETAPA_POS_PROCESSAMENTO = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");

                for (String Erro : PosProcessadorC.getErros()) {
                    mErros_Mensagens.add("\t\t" + Erro);
                }

            }

            System.out.println("\t - 4 : Pos Processamento       : " + mETAPA_POS_PROCESSAMENTO);


        }

    }


    public void organizar() {

        mEtapa = "Organizando";
        mSubEtapa = "";
        mContinuar = 5;
        mFase = Fases.INTEGRALIZACAO;

        try {

            Thread.sleep(500);

        } catch (Exception e) {

        }


    }

    public void terminar() {

        mContinuar = 10;

        if (mDebug) {

            System.out.println("");
            System.out.println("################ DEBUG ################");
            System.out.println("");

            for (String eMensagem : mDebugMensagens) {

                System.out.println(eMensagem);
            }

        }

        if (mFase == Fases.PRONTO) {


            System.out.println("----------------------------------------------");
            System.out.println("");
            System.out.println("\t - COMPILADO COM SUCESSO");
            System.out.println("");
            System.out.println("----------------------------------------------");


        } else {


            System.out.println("----------------------------------------------");
            System.out.println("");

            for (String eMensagem : mErros_Mensagens) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

    public boolean getTerminou() {
        return mContinuar > 9;
    }

    public int getContinuar() {
        return mContinuar;
    }

    private void executar_PreProcessamento(String eArquivo, int mOpcao, boolean mDebugar) {

        if (mFase == Fases.PRE_PROCESSAMENTO) {

            initPreProcessamento(eArquivo, mOpcao,mDebugar);

            mPreInt = 1;


        }


    }

    public boolean getPre() {
        return mPre;
    }

    private void continuar_PreProcessamento() {

        if (getPre() == true) {

            mPreInt = 2;

        } else {

            continuarPreProcessamento();

            mPreProcessando = mPreProcessador.getProcessando();

            mCorrentePreprocessando = estaPreProcessando();
            mCorrenteParseando = estaParseando();

            mCorrenteCompilando = estaCompilando();


        }

    }

    public boolean estaPreProcessando() {
        return mCorrentePreprocessando;
    }


    private void finalizar_PreProcessamento() {

        if (mFase == Fases.PRE_PROCESSAMENTO) {

            mPreInt = 3;

            mDebugMensagens.add(getPreProcessamento());


            if (getErros_PreProcessamento().size() == 0) {

                mFase = Fases.LEXER;
                mETAPA_PRE_PROCESSAMENTO = mSTATUS_SUCESSO;

                mETAPA_LEXER = mSTATUS_SUCESSO;
                mETAPA_PARSER = mSTATUS_SUCESSO;

            } else {

                mETAPA_PRE_PROCESSAMENTO = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE PRE PROCESSAMENTO : ");

                for (GrupoDeErro eGE : getErros_PreProcessamento()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

        }

        System.out.println("\t - 1 : Pre Processamento       : " + mETAPA_PRE_PROCESSAMENTO);

    }


    private void executar_Lexer() {


        if (mFase == Fases.LEXER) {

            mDebugMensagens.add(getLexer_Processamento());

            mEtapa = "Lexer";
            mSubEtapa = "";

            if (getErros_Lexer().size() == 0) {
                mFase = Fases.PARSER;
                mETAPA_LEXER = mSTATUS_SUCESSO;
                mContinuar = 2;
            } else {
                mETAPA_LEXER = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE LEXER : ");

                for (GrupoDeErro eGE : getErros_Lexer()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

        }


    }

    private void executar_Parser() {

        if (mFase == Fases.PARSER) {

            mEtapa = "Parser";
            mSubEtapa = "";

            if (getErros_Compiler().size() == 0) {
                mFase = Fases.POS_PROCESSAMENTO;
                mETAPA_PARSER = mSTATUS_SUCESSO;
                mContinuar = 3;

            } else {
                mETAPA_PARSER = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : getErros_Compiler()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());

                    }
                }

            }

        }


    }


    private void iniciar_PosProcessamento(String mLocal) {

        mEtapa = "Pos-Processamento";
        mFase = Fases.POS_PROCESSAMENTO;

        if (mFase == Fases.POS_PROCESSAMENTO) {

            PosProcessadorC = new PosProcessador();
            PosProcessadorC.initPassos(getASTS(), mLocal);
            mPosInt = 1;

            mSubEtapa = PosProcessadorC.getFase();
        }


    }

    public void integralizar_iniciar() {
        if (mFase == Fases.INTEGRALIZACAO) {

            mEtapa = "Integralizacao";
            mSubEtapa = "";
            mETAPA_INTEGRALIZACAO = "EXECUTANDO";
            mContinuar = 6;

        }
    }

    public void integralizar_realizar() {


        if (mFase == Fases.INTEGRALIZACAO) {

            mEtapa = "Integralizacao";
            mSubEtapa = "";


            Integrador mIntegrador = new Integrador();

            mIntegrador.setDebug(false);

            mIntegrador.init(getASTS(), "");

            mETAPA_INTEGRALIZACAO = "CONCLUIDO";

            for (String eMensagem : mIntegrador.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }


            if (mIntegrador.getErros().size() == 0) {

                mETAPA_INTEGRALIZACAO = mSTATUS_SUCESSO;
                mFase = Fases.MONTAGEM;

            } else {

                mETAPA_INTEGRALIZACAO = mSTATUS_FALHOU;


            }

            System.out.println("\t - 5 : Integralizacao          : " + mETAPA_INTEGRALIZACAO);

            mContinuar = 7;

        }

    }

    public void montagem_iniciar(String mSaida) {


        if (mFase == Fases.MONTAGEM) {

            mEtapa = "Montagem";
            mSubEtapa = "Objeto";
            mETAPA_MONTAGEM = "MONTANDO -->> " + mSaida;

            ArrayList<AST> mASTCabecalhos = new ArrayList<AST>();

            SigmazCab mSigmazCab = new SigmazCab();

            mSigmazCab.gravarCabecalho(mCabecalho, mASTCabecalhos);

            mASTS = getASTS();


            MontadorC = new Montador();

            MontadorC.compilar_iniciar(mASTCabecalhos, getASTS(), mSaida);

            mETAPA_MONTAGEM = "EXECUTANDO -->> " + MontadorC.getFase();

            mContinuar = 8;

        }

    }

    public void montagem_continuar() {

        if (mFase == Fases.MONTAGEM) {

            MontadorC.continuar();

            mETAPA_MONTAGEM = "EXECUTANDO -->> " + MontadorC.getFase();

            if (MontadorC.getTerminou()) {
                mContinuar = 9;
            }

        }

    }

    public void montagem_terminar() {

        if (mFase == Fases.MONTAGEM) {

            mFase = Sigmaz_Etapas.Fases.PRONTO;

            mETAPA_MONTAGEM = mSTATUS_SUCESSO;

            for (String eMensagem : MontadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }
            mContinuar = 10;

            System.out.println("\t - 6 : Montagem                : " + mETAPA_MONTAGEM);

        }
    }


    public void initprevarios(ArrayList<String> eArquivos, int mOpcao,boolean mDebugar) {

        limpar();

        mPre = false;

        AST AST_Raiz = new AST("SIGMAZ");


        mASTS.add(AST_Raiz);

        UUID mUUIDC = new UUID();

        AST_Raiz.setValor(mUUIDC.getUUID());

        if (mOpcao == 1) {

            AST_Raiz.setNome("EXECUTABLE");

        } else if (mOpcao == 2) {

            AST_Raiz.setNome("LIBRARY");

        }


        mPreProcessador = new Compilador();
        mPreProcessador.initPassos(eArquivos, AST_Raiz,mDebugar);
        mPreProcessando = mPreProcessador.getProcessando();


    }


    public void initPreProcessamento(String eArquivo, int mOpcao,boolean mDebugar) {

        mPreInt = 0;
        mPreProcessando = "";
        mEtapa = "Pre-Processamento";
        mSubEtapa = "Init";

        ArrayList<String> eArquivos = new ArrayList<String>();
        eArquivos.add(eArquivo);

        initprevarios(eArquivos, mOpcao,mDebugar);

    }


    public void continuarPreProcessamento() {


        if (mPreProcessador.getTerminou()) {

            mPre = true;

            mPreProcessamento = mPreProcessador.getPreProcessamento();
            mLexer_Processamento = mPreProcessador.getLexer_Processamento();

            mIChars = mPreProcessador.getIChars();
            mITokens = mPreProcessador.getITokens();


            for (String mRequisicao : mPreProcessador.getRequisitados()) {
                mRequisitados.add(mRequisicao);
            }

            for (GrupoDeComentario mGrupoDeComentario : mPreProcessador.getComentarios()) {
                mComentarios.add(mGrupoDeComentario);
            }

            for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Processamento()) {
                mErros_PreProcessamento.add(mGrupoDeErro);
            }

            for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Lexer()) {
                mErros_Lexer.add(mGrupoDeErro);
            }


            for (GrupoDeErro mGrupoDeErro : mPreProcessador.getErros_Parser()) {
                mErros_Compiler.add(mGrupoDeErro);
            }

        } else {

            mPreProcessador.continuar();
            mPreProcessando = mPreProcessador.getProcessando();

            mCorrentePreprocessando = mPreProcessador.estaPreProcessando();
            mCorrenteParseando = mPreProcessador.estaParseando();
            mCorrenteCompilando = mPreProcessador.estaCompilando();

        }


    }

    public void limpar() {

        mFase = Sigmaz_Etapas.Fases.PRE_PROCESSAMENTO;

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_PARSER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();

        mTemErros = false;

        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mErros_PosProcessamento.clear();
        mErros_Execucao.clear();
        mErros_PreProcessamento.clear();

        mASTS.clear();

        mEstaPreProcessando = false;
        mPreProcessando = "";

        mRequisitados.clear();

        mErros_PreProcessamento.clear();
        mErros_Lexer.clear();
        mErros_Compiler.clear();

        mComentarios.clear();

        mIChars = 0;
        mITokens = 0;
        mPreProcessamento = "";
        mLexer_Processamento = "";

        mPassos = 0;

    }


    public String getEtapa() {
        return mEtapa;
    }

    public String getSubEtapa() {
        return mSubEtapa;
    }

    public boolean getEstaPreProcessando() {
        return mEstaPreProcessando;
    }

    public int getPreInt() {
        return mPreInt;
    }


    public String getPreprocessando() {
        return mPreProcessando;
    }

    public String getPreProcessamento() {
        return mETAPA_PRE_PROCESSAMENTO;
    }

    public String getLexer() {
        return mETAPA_LEXER;
    }

    public String getCompiler() {
        return mETAPA_PARSER;
    }


    public String getPosProcessamento() {
        return mETAPA_POS_PROCESSAMENTO;
    }

    public String getMontagem() {
        return mETAPA_MONTAGEM;
    }

    public String getIntegralizacao() {
        return mETAPA_INTEGRALIZACAO;
    }


    public void mostrarDebug(boolean eDebug) {
        mDebug = eDebug;
    }

    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }


    public ArrayList<String> getRequisitados() {
        return mRequisitados;
    }


    public String getLexer_Processamento() {
        return mLexer_Processamento;
    }

    public boolean temErros() {
        return mTemErros;
    }

    public ArrayList<GrupoDeErro> getErros_PreProcessamento() {
        return mErros_PreProcessamento;
    }

    public ArrayList<GrupoDeErro> getErros_Lexer() {
        return mErros_Lexer;
    }

    public ArrayList<GrupoDeErro> getErros_Compiler() {
        return mErros_Compiler;
    }

    public ArrayList<String> getErros_PosProcessamento() {
        return mErros_PosProcessamento;
    }

    public ArrayList<String> getErros_Execucao() {
        return mErros_Execucao;
    }


}
