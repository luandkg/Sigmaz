package Sigmaz;

import Sigmaz.S00_Utilitarios.Chronos;
import Sigmaz.S04_Compilador.Compiler;
import Sigmaz.S06_Montador.Montador;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S00_Utilitarios.GrupoDeErro;

import java.io.File;
import java.util.ArrayList;

public class Sigmaz_Etapas {

    private Cabecalho mCabecalho;
    private ArrayList<String> mErros_Mensagens;
    private Fases mFase;

    private String mETAPA_PRE_PROCESSAMENTO;
    private String mETAPA_LEXER;
    private String mETAPA_COMPILER;
    private String mETAPA_POS_PROCESSAMENTO;
    private String mETAPA_MONTAGEM;

    private String mSTATUS_NAO;
    private String mSTATUS_SUCESSO;
    private String mSTATUS_FALHOU;

    private boolean mDebug;
    private ArrayList<String> mDebugMensagens;
    Compiler CompilerC;

    private int mContinuar;
    private String mLocal;
    private String mSaida;
    private String mArquivo;
    private int mOpcao;
    private int mPre;

    private boolean mEstaPreProcessando;
    private String mPreProcessando;

    private boolean mCorrentePreprocessando;
    private boolean mCorrenteParseando;
    private boolean mCorrenteCompilando;

    private Chronos mChronos;

    public enum Fases {
        PRE_PROCESSAMENTO, LEXER, COMPILER, POS_PROCESSAMENTO, MONTAGEM, PRONTO;
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
        mETAPA_COMPILER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebug = false;
        mDebugMensagens = new ArrayList<String>();
        mErros_Mensagens = new ArrayList<String>();
        mContinuar = -1;
        mSaida = "";
        mLocal = "";
        mArquivo = "";
        mOpcao = 0;
        mPre = 0;
        mEstaPreProcessando = false;
        mPreProcessando = "";

        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

        mChronos = new Chronos();

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
        return mETAPA_COMPILER;
    }


    public String getPosProcessamento() {
        return mETAPA_POS_PROCESSAMENTO;
    }

    public String getMontagem() {
        return mETAPA_MONTAGEM;
    }

    public void mostrarDebug(boolean eDebug) {
        mDebug = eDebug;
    }

    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }


    public void init(String eArquivo, String eSaida, int eOpcao) {

        mEstaPreProcessando = false;
        mPreProcessando = "";

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
        mETAPA_COMPILER = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();
        mDebugMensagens.clear();

        System.out.println("");
        System.out.println("################ SIGMAZ FASES ################");
        System.out.println("");

        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Local  : " + mLocal);
        System.out.println("\t - Build  : " + mSaida);
        System.out.println("\t - Modo   : " + eModo);

        System.out.println("");


        CompilerC = new Compiler();

        mContinuar = 0;
        mPre = 0;
        mCorrentePreprocessando = false;
        mCorrenteParseando = false;
        mCorrenteCompilando = false;

    }


    public void continuar() {

        if (mPre == 0) {

            if (mContinuar == 0) {

                mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO";

                mEstaPreProcessando = true;

                executar_PreProcessamento(mArquivo, mOpcao, CompilerC);

            }

        } else if (mPre == 1) {

            mEstaPreProcessando = true;

            if (mContinuar == 0) {
                continuar_PreProcessamento();


                if (getEstaPreProcessando()) {

                    mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO";

                    if (estaPre()) {
                        mETAPA_PRE_PROCESSAMENTO = "EXECUTANDO -->> " + getPreprocessando();
                    }

                    if (estaParseando()) {
                        mETAPA_LEXER = "EXECUTANDO -->> " + getPreprocessando();
                    }

                    if (estaCompilando()) {
                        mETAPA_COMPILER = "EXECUTANDO -->> " + getPreprocessando();
                    }

                }


            }

        } else if (mPre == 2) {

            mEstaPreProcessando = true;
            mETAPA_PRE_PROCESSAMENTO = "CONCLUIDO";

            if (mContinuar == 0) {
                finalizar_PreProcessamento();

            }

            mEstaPreProcessando = false;

            mContinuar += 1;

        } else {

            if (mContinuar == 1) {

                executar_Lexer(CompilerC);

            }

            if (mContinuar == 2) {

                executar_Compilador(CompilerC);

            }


            if (mContinuar == 3) {

                executar_PosProcessamento(CompilerC, mLocal);

            }

            if (mContinuar == 5) {
                executar_Montagem(CompilerC, mSaida);
            }

            if (mContinuar == 6) {
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


            mContinuar += 1;


        }


    }

    public boolean getTerminou() {
        return mContinuar > 6;
    }

    public int getContinuar() {
        return mContinuar;
    }

    private void executar_PreProcessamento(String eArquivo, int mOpcao, Compiler CompilerC) {

        if (mFase == Fases.PRE_PROCESSAMENTO) {


            /// CompilerC.init(eArquivo, mOpcao);

            CompilerC.initPreProcessamento(eArquivo, mOpcao);

            mPre = 1;


        }


    }

    private void continuar_PreProcessamento() {

        if (CompilerC.getPre() == true) {

            mPre = 2;

        } else {

            CompilerC.continuarPreProcessamento();

            mPreProcessando = CompilerC.getPreProcessando();

            mCorrentePreprocessando = CompilerC.estaPreProcessando();
            mCorrenteParseando = CompilerC.estaParseando();
            ;
            mCorrenteCompilando = CompilerC.estaCompilando();
            ;

        }

    }

    private void finalizar_PreProcessamento() {

        if (mFase == Fases.PRE_PROCESSAMENTO) {

            mPre = 3;

            mDebugMensagens.add(CompilerC.getPreProcessamento());


            if (CompilerC.getErros_PreProcessamento().size() == 0) {

                mFase = Fases.LEXER;
                mETAPA_PRE_PROCESSAMENTO = mSTATUS_SUCESSO;

            } else {

                mETAPA_PRE_PROCESSAMENTO = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE PRE PROCESSAMENTO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_PreProcessamento()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

        }

        System.out.println("\t - 1 : Pre Processamento       : " + mETAPA_PRE_PROCESSAMENTO);

    }


    private void executar_Lexer(Compiler CompilerC) {


        if (mFase == Fases.LEXER) {

            mDebugMensagens.add(CompilerC.getLexer_Processamento());


            if (CompilerC.getErros_Lexer().size() == 0) {
                mFase = Fases.COMPILER;
                mETAPA_LEXER = mSTATUS_SUCESSO;
            } else {
                mETAPA_LEXER = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE LEXER : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

        }

        System.out.println("\t - 2 : Lexer                   : " + mETAPA_LEXER);


    }

    private void executar_Compilador(Compiler CompilerC) {

        if (mFase == Fases.COMPILER) {

            if (CompilerC.getErros_Compiler().size() == 0) {
                mFase = Fases.POS_PROCESSAMENTO;
                mETAPA_COMPILER = mSTATUS_SUCESSO;
            } else {
                mETAPA_COMPILER = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                    mErros_Mensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mErros_Mensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }

            }

        }

        System.out.println("\t - 3 : Compilador              : " + mETAPA_COMPILER);


    }


    private void executar_PosProcessamento(Compiler CompilerC, String mLocal) {

        if (mFase == Fases.POS_PROCESSAMENTO) {

            PosProcessador PosProcessadorC = new PosProcessador();
            PosProcessadorC.init(mCabecalho, CompilerC.getASTS(), mLocal);

            for (String eMensagem : PosProcessadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }


            if (PosProcessadorC.getErros().size() == 0) {


                mFase = Fases.MONTAGEM;
                mETAPA_POS_PROCESSAMENTO = mSTATUS_SUCESSO;


            } else {

                mETAPA_POS_PROCESSAMENTO = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");

                for (String Erro : PosProcessadorC.getErros()) {
                    mErros_Mensagens.add("\t\t" + Erro);
                }

            }


        }

        System.out.println("\t - 5 : Pos Processamento       : " + mETAPA_POS_PROCESSAMENTO);


    }

    public void executar_Montagem(Compiler CompilerC, String mSaida) {


        if (mFase == Fases.MONTAGEM) {


            Montador MontadorC = new Montador();

            MontadorC.compilar(CompilerC.getCabecalhos(),CompilerC.getASTS(), mSaida);



            mFase = Fases.PRONTO;

            mETAPA_MONTAGEM = mSTATUS_SUCESSO;


        }

        System.out.println("\t - 6 : Montagem                : " + mETAPA_MONTAGEM);


    }

    private void executar(String eExecutor) {

        System.out.println("");
        System.out.println("################ RUNTIME ################");
        System.out.println("");
        System.out.println("\t - Executando : " + eExecutor);

        RunTime RunTimeC = new RunTime();
        String DI = mChronos.getData();


        RunTimeC.init(eExecutor);

        System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
        System.out.println("");


        System.out.println("");
        System.out.println("----------------------------------------------");

        RunTimeC.run();

        System.out.println("");
        System.out.println("----------------------------------------------");
        System.out.println("");

        String DF = mChronos.getData();

        System.out.println("\t - Iniciado : " + DI);
        System.out.println("\t - Finalizado : " + DF);

        System.out.println("\t - Erros : " + RunTimeC.getErros().size());

        if (RunTimeC.getErros().size() > 0) {
            System.out.println("\n\t ERROS DE EXECUCAO : ");

            for (String Erro : RunTimeC.getErros()) {
                System.out.println("\t\t" + Erro);
            }
        }

        System.out.println("");
        System.out.println("----------------------------------------------");


    }

    public boolean getEstaPreProcessando() {
        return mEstaPreProcessando;
    }

}
