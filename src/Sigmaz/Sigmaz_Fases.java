package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Compilador.Compiler;
import Sigmaz.Executor.RunTime;
import Sigmaz.PosProcessamento.Cabecalho;
import Sigmaz.PosProcessamento.PosProcessador;
import Sigmaz.Utils.Erro;
import Sigmaz.Utils.GrupoDeErro;

import java.io.File;
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

    public enum Fases {
        PRE_PROCESSAMENTO, LEXER, COMPILER, ANALISAR, POS_PROCESSAMENTO, MONTAGEM, PRONTO;
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
    }


    public void mostrarDebug(boolean eDebug) {
        mDebug = eDebug;
    }

    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }


    public void init(String eArquivo, String mSaida, int mOpcao) {


        File arq = new File(mSaida);
        String mLocal = arq.getParent() + "/";

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

        System.out.println("");
        System.out.println("################ SIGMAZ FASES ################");
        System.out.println("");

        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Local  : " + mLocal);
        System.out.println("\t - Build  : " + mSaida);
        System.out.println("\t - Modo   : " + eModo);

        System.out.println("");


        Compiler CompilerC = new Compiler();


        executar_PreProcessamento(eArquivo, mOpcao, CompilerC);

        executar_Lexer(CompilerC);

        executar_Compilador(CompilerC);

        executar_Analisador(CompilerC, mLocal);

        executar_PosProcessamento(CompilerC, mLocal);

        executar_Montagem(CompilerC, mSaida);


        if (mDebug) {

            System.out.println("");
            System.out.println("################ DEBUG ################");
            System.out.println("");

            for (String eMensagem : mDebugMensagens) {

                System.out.println(eMensagem);
            }

        }

        if (mFase == Fases.PRONTO) {

            executar(mSaida);


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

    private void executar_PreProcessamento(String eArquivo, int mOpcao, Compiler CompilerC) {

        if (mFase == Fases.PRE_PROCESSAMENTO) {


            CompilerC.init(eArquivo, mOpcao);

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
                mFase = Fases.ANALISAR;
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

    private void executar_Analisador(Compiler CompilerC, String mLocal) {


        if (mFase == Fases.ANALISAR) {

            Analisador AnaliseC = new Analisador();
            AnaliseC.init(CompilerC.getASTS(), mLocal);

            for (String eMensagem : AnaliseC.getMensagens()) {
                mDebugMensagens.add(eMensagem);
            }

            if (AnaliseC.getErros().size() == 0) {

                mFase = Fases.POS_PROCESSAMENTO;
                mETAPA_ANALISE = mSTATUS_SUCESSO;

            } else {

                mETAPA_ANALISE = mSTATUS_FALHOU;

                mErros_Mensagens.add("\n\t ERROS DE ANALISE : ");
                for (String eErro : AnaliseC.getErros()) {
                    mErros_Mensagens.add("\t\t    ->> " + eErro);
                }

            }

        }

        System.out.println("\t - 4 : Analise                 : " + mETAPA_ANALISE);


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


            CompilerC.Compilar(mSaida);

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
        String DI = RunTimeC.getData();


        RunTimeC.init(eExecutor);

        System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
        System.out.println("");

       System.out.println(RunTimeC.getArvoreDeInstrucoes());

        System.out.println("");
        System.out.println("----------------------------------------------");

        RunTimeC.run();

        System.out.println("");
        System.out.println("----------------------------------------------");
        System.out.println("");

        String DF = RunTimeC.getData();

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


}
