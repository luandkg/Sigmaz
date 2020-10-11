package Sigmaz;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S01_PreProcessamento.Etapa;
import Sigmaz.S01_PreProcessamento.Planejador;
import Sigmaz.S04_Compilador.Compiler;
import Sigmaz.S06_Montador.Montador;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S05_PosProcessamento.Processadores.Cabecalho;
import Sigmaz.S05_PosProcessamento.PosProcessador;

import java.util.ArrayList;

public class Sigmaz_Fases {

    private Cabecalho mCabecalho;
    private ArrayList<String> mErros_Mensagens;
    private Fases mFase;

    private String mETAPA_PRE_PROCESSAMENTO;
    private String mETAPA_LEXER;
    private String mETAPA_COMPILER;
    private String mETAPA_ANALISE;
    private String mETAPA_POS_PROCESSAMENTO;
    private String mETAPA_MONTAGEM;

    private String mSTATUS_NAO;
    private String mSTATUS_SUCESSO;
    private String mSTATUS_FALHOU;

    private boolean mDebug;
    private ArrayList<String> mDebugMensagens;

    private ArrayList<GrupoDeErro> mErros_PreProcessamento;
    private ArrayList<GrupoDeErro> mErros_Lexer;
    private ArrayList<GrupoDeErro> mErros_Compiler;
    private ArrayList<String> mErros_PosProcessamento;
    private ArrayList<String> mErros_Execucao;

    private boolean mMostrar_Fases;
    private boolean mMostrar_ArvoreRunTime;
    private boolean mMostrar_ArvoreFalhou;
    private boolean mMostrar_Erros;
    private boolean mMostrar_Execucao;
    private boolean mMostrar_Mensagens;

    private ArrayList<Etapa> mEtapas;

    private boolean mTemErros;

    private ArrayList<AST> mASTS;

    private Chronos mChronos;

    public enum Fases {
        PRE_PROCESSAMENTO, LEXER, COMPILER, POS_PROCESSAMENTO, MONTAGEM, PRONTO;
    }

    public Sigmaz_Fases() {


        mCabecalho = new Cabecalho();

        mSTATUS_NAO = "NAO REALIZADO";
        mSTATUS_SUCESSO = "SUCESSO";
        mSTATUS_FALHOU = "FALHOU";


        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_COMPILER = mSTATUS_NAO;
        mETAPA_ANALISE = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebug = false;
        mDebugMensagens = new ArrayList<String>();

        mErros_Mensagens = new ArrayList<String>();

        mMostrar_Fases = true;
        mMostrar_Erros = true;
        mMostrar_ArvoreRunTime = true;
        mMostrar_ArvoreFalhou = true;
        mMostrar_Execucao = true;
        mMostrar_Mensagens = true;

        mEtapas = new ArrayList<Etapa>();

        mTemErros = false;

        mErros_PreProcessamento = new ArrayList<>();
        mErros_Lexer = new ArrayList<>();
        mErros_Compiler = new ArrayList<>();
        mErros_PosProcessamento = new ArrayList<>();
        mErros_Execucao = new ArrayList<>();
        mASTS = new ArrayList<AST>();

        mChronos = new Chronos();

    }


    public void setMostrar_Fases(boolean e) {
        mMostrar_Fases = e;
    }

    public void setMostrar_Erros(boolean e) {
        mMostrar_Erros = e;
    }

    public void setMostrarArvoreRunTime(boolean e) {
        mMostrar_ArvoreRunTime = e;
    }

    public void setMostrar_ArvoreFalhou(boolean e) {
        mMostrar_ArvoreFalhou = e;
    }

    public void mostrarDebug(boolean eDebug) {
        mDebug = eDebug;
    }

    public void setMostrar_Execucao(boolean e) {
        mMostrar_Execucao = e;
    }

    public void setMostrar_Mensagens(boolean e) {
        mMostrar_Mensagens = e;
    }


    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }

    public ArrayList<Etapa> getEtapas() {
        return mEtapas;
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

    public Fases getFase() {
        return mFase;
    }


    public String getModo(int mOpcao) {
        String eModo = "DESCONHECIDO";

        if (mOpcao == 1) {
            eModo = "EXECUTAVEL";
        }

        if (mOpcao == 2) {
            eModo = "BIBLIOTECA";
        }

        return eModo;
    }


    public void limpar() {

        mFase = Fases.PRE_PROCESSAMENTO;

        mETAPA_PRE_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_LEXER = mSTATUS_NAO;
        mETAPA_COMPILER = mSTATUS_NAO;
        mETAPA_ANALISE = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();
        mDebugMensagens.clear();

        mTemErros = false;

        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mErros_PosProcessamento.clear();
        mErros_Execucao.clear();
        mErros_PreProcessamento.clear();

        mASTS.clear();

    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public void init(String eArquivo, String mSaida, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        init(aa, mSaida, eLocalLibs, mOpcao);
    }

    public void init(ArrayList<String> eArquivos, String mSaida, String eLocalLibs, int mOpcao) {

        limpar();

        String eModo = getModo(mOpcao);

        mFase = Fases.PRE_PROCESSAMENTO;


        if (mMostrar_Fases) {

            System.out.println("");
            System.out.println("################ SIGMAZ FASES ################");
            System.out.println("");

            for (String a : eArquivos) {
                System.out.println("\t - Source : " + a);
            }
            System.out.println("\t - Local  : " + eLocalLibs);
            System.out.println("\t - Build  : " + mSaida);
            System.out.println("\t - Modo   : " + eModo);

            System.out.println("");

        }

        Chronos_Intervalo mIntervalo = new Chronos_Intervalo();

        mIntervalo.marqueInicio();

        Compiler CompilerC = new Compiler();

        executar_PreProcessamento(eArquivos, mOpcao, CompilerC);

        executar_Lexer(CompilerC);

        executar_Compilador(CompilerC);

        executar_PosProcessamento(CompilerC, eLocalLibs);

        executar_Montagem(CompilerC, mSaida);

        mIntervalo.marqueFim();

        listar_mensagens();

        if (mFase == Fases.PRONTO) {

            if (mMostrar_Fases) {
                System.out.println("");
                System.out.println("\t - Tempo   : " + mIntervalo.getIntervalo() + " segundos");
            }

            executar(mSaida);

        } else {

            falhou(CompilerC);

        }

    }

    public void executar(String eSaida){

        Sigmaz_Executor mSigmaz_Executor = new Sigmaz_Executor();

        mSigmaz_Executor.setMostrar_Execucao(mMostrar_Execucao);
        mSigmaz_Executor.setMostrar_ArvoreRunTime(mMostrar_ArvoreRunTime);

        mSigmaz_Executor.executar(eSaida);

        if (mSigmaz_Executor.temErros()){
            for(String mErro : mSigmaz_Executor.getErros()){
                mErros_Execucao.add(mErro);
            }
        }

    }

    public void initSemExecucao(String eArquivo, String mSaida, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        initSemExecucao(aa, mSaida, eLocalLibs, mOpcao);
    }

    public void initSemExecucao(ArrayList<String> eArquivos, String mSaida, String eLocalLibs, int mOpcao) {

        limpar();

        String eModo = getModo(mOpcao);

        mFase = Fases.PRE_PROCESSAMENTO;


        if (mMostrar_Fases) {

            System.out.println("");
            System.out.println("################ SIGMAZ FASES ################");
            System.out.println("");

            for (String a : eArquivos) {
                System.out.println("\t - Source : " + a);
            }
            System.out.println("\t - Local  : " + eLocalLibs);
            System.out.println("\t - Build  : " + mSaida);
            System.out.println("\t - Modo   : " + eModo);

            System.out.println("");

        }

        Chronos_Intervalo mIntervalo = new Chronos_Intervalo();

        mIntervalo.marqueInicio();

        Compiler CompilerC = new Compiler();

        executar_PreProcessamento(eArquivos, mOpcao, CompilerC);

        executar_Lexer(CompilerC);

        executar_Compilador(CompilerC);

        executar_PosProcessamento(CompilerC, eLocalLibs);

        executar_Montagem(CompilerC, mSaida);

        mIntervalo.marqueFim();

        listar_mensagens();

        if (mFase == Fases.PRONTO) {

            if (mMostrar_Fases) {
                System.out.println("");
                System.out.println("\t - Tempo   : " + mIntervalo.getIntervalo() + " segundos");
            }


        } else {

            falhou(CompilerC);

        }

    }


    public void listar_mensagens() {

        if (mMostrar_Mensagens) {

            if (mDebug) {

                System.out.println("");
                System.out.println("################ DEBUG ################");
                System.out.println("");

                for (String eMensagem : mDebugMensagens) {
                    System.out.println(eMensagem);
                }

            }

        }


    }

    public void falhou(Compiler CompilerC) {

        mTemErros = true;


        if (mMostrar_ArvoreFalhou) {

            System.out.println("----------------------------------------------");
            System.out.println("");

            System.out.println(CompilerC.getArvoreDeInstrucoes());
        }


        if (mMostrar_Erros) {

            System.out.println("");
            System.out.println("----------------------------------------------");


            for (String eMensagem : mErros_Mensagens) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

    public void montarPlano(String eArquivo, String mSaida, String eLocalLibs, int mOpcao) {
        ArrayList<String> aa = new ArrayList<String>();
        aa.add(eArquivo);
        montarPlano(aa, mSaida, eLocalLibs, mOpcao);
    }


    public void montarPlano(ArrayList<String> eArquivos, String eLocalLibs, String mArquivoPlano, int mOpcao) {


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
        mETAPA_ANALISE = mSTATUS_NAO;
        mETAPA_POS_PROCESSAMENTO = mSTATUS_NAO;
        mETAPA_MONTAGEM = mSTATUS_NAO;

        mDebugMensagens.clear();
        mDebugMensagens.clear();

        mTemErros = false;

        mErros_Lexer.clear();
        mErros_Compiler.clear();
        mErros_PosProcessamento.clear();
        mErros_Execucao.clear();
        mErros_PreProcessamento.clear();

        System.out.println("");
        System.out.println("################ SIGMAZ FASES ################");
        System.out.println("");

        for (String a : eArquivos) {
            System.out.println("\t - Source : " + a);
        }

        System.out.println("\t - Local  : " + eLocalLibs);
        System.out.println("\t - Build  : " + eLocalLibs);
        System.out.println("\t - Modo   : " + eModo);
        System.out.println("\t - Plano   : " + mArquivoPlano);

        System.out.println("");


        Compiler CompilerC = new Compiler();


        executar_PreProcessamento(eArquivos, mOpcao, CompilerC);

        executar_Lexer(CompilerC);

        executar_Compilador(CompilerC);


        executar_PosProcessamento(CompilerC, eLocalLibs);

        //executar_Montagem(CompilerC, eLocalLibs);


        if (mDebug) {

            System.out.println("");
            System.out.println("################ DEBUG ################");
            System.out.println("");

            for (String eMensagem : mDebugMensagens) {

                System.out.println(eMensagem);
            }

        }

        if (mFase == Fases.MONTAGEM) {

            System.out.println("");
            System.out.println("################ PLANO ################");
            System.out.println("");

            System.out.println("ETAPAS : " + mEtapas.size());


            for (Etapa mEtapa : mEtapas) {

                System.out.println("");
                System.out.println("ETAPA: " + mEtapa.getEtapaID() + " :: " + mEtapa.getCorrente());

                System.out.println("\t - Adicionar : " + mEtapa.getAdicionar().size());
                for (String a : mEtapa.getAdicionar()) {
                    System.out.println("\t\t - " + a);
                }

                System.out.println("\t - Ja Esperando : " + mEtapa.getJaEsperando().size());
                for (String a : mEtapa.getJaEsperando()) {
                    System.out.println("\t\t - " + a);
                }

                System.out.println("\t - Ja Processados : " + mEtapa.getJaProcessado().size());
                for (String a : mEtapa.getJaProcessado()) {
                    System.out.println("\t\t - " + a);
                }

            }

            Planejador mPlanejador = new Planejador();
            mPlanejador.init(mArquivoPlano, mEtapas);


        } else {


            System.out.println("----------------------------------------------");
            System.out.println("");


            if (mMostrar_ArvoreFalhou) {
                System.out.println(CompilerC.getArvoreDeInstrucoes());
            }


            System.out.println("");
            System.out.println("----------------------------------------------");


            for (String eMensagem : mErros_Mensagens) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");

        }

    }


    private void executar_PreProcessamento(ArrayList<String> eArquivos, int mOpcao, Compiler CompilerC) {

        if (mFase == Fases.PRE_PROCESSAMENTO) {


            CompilerC.initvarios(eArquivos, mOpcao);

            mDebugMensagens.add(CompilerC.getPreProcessamento());


            mEtapas = CompilerC.getEtapas();

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
                    mErros_PreProcessamento.add(eGE);

                }

            }


        }

        if (mMostrar_Fases) {
            System.out.println("\t - 1 : Pre Processamento       : " + mETAPA_PRE_PROCESSAMENTO);

        }


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
                    mErros_Lexer.add(eGE);

                }

            }

        }
        if (mMostrar_Fases) {
            System.out.println("\t - 2 : Lexer                   : " + mETAPA_LEXER);

        }


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
                    mErros_Compiler.add(eGE);
                }

            }

        }
        if (mMostrar_Fases) {

            System.out.println("\t - 3 : Compilador              : " + mETAPA_COMPILER);

        }


    }


    private void executar_PosProcessamento(Compiler CompilerC, String mLocalLibs) {

        if (mFase == Fases.POS_PROCESSAMENTO) {

            PosProcessador PosProcessadorC = new PosProcessador();
            PosProcessadorC.init(mCabecalho, CompilerC.getASTS(), mLocalLibs);

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
                    mErros_PosProcessamento.add(Erro);
                }

            }


        }
        if (mMostrar_Fases) {

            System.out.println("\t - 5 : Pos Processamento       : " + mETAPA_POS_PROCESSAMENTO);

        }


    }

    public void executar_Montagem(Compiler CompilerC, String mSaida) {


        if (mFase == Fases.MONTAGEM) {


            Montador MontadorC = new Montador();

            MontadorC.compilar(CompilerC.getASTS(), mSaida);

            mASTS = CompilerC.getASTS();


            mFase = Fases.PRONTO;

            mETAPA_MONTAGEM = mSTATUS_SUCESSO;

            for (String eMensagem : MontadorC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }

        }
        if (mMostrar_Fases) {

            System.out.println("\t - 6 : Montagem                : " + mETAPA_MONTAGEM);

        }


    }


}
