package Sigmaz;

import Sigmaz.Analisador.Analisador;
import Sigmaz.Ferramentas.Dependenciador;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.PosProcessamento.Cabecalho;
import Sigmaz.PosProcessamento.PosProcessador;
import Sigmaz.Executor.RunTime;
import Sigmaz.Compilador.Compiler;

import java.io.File;
import java.util.ArrayList;

import Sigmaz.Utils.*;

// COMPILADOR SIGMAZ

// DESENVOLVEDOR : LUAN FREITAS


public class Sigmaz {

    private boolean mObject;
    private boolean mPosProcess;
    private boolean mStackProcess;
    private boolean mAnaliseProcess;

    private ArrayList<String> mMensagens;
    private boolean mTemErros;
    private String mLocalErro;


    private Cabecalho mCabecalho;

    public Sigmaz() {

        mObject = true;
        mPosProcess = true;
        mStackProcess = true;
        mAnaliseProcess = true;

        mMensagens = new ArrayList<String>();
        mTemErros = false;
        mLocalErro = "";

        mCabecalho = new Cabecalho();

    }

    public void setCabecalho(Cabecalho eCabecalho) {
        mCabecalho = eCabecalho;
    }


    public void setObject(boolean e) {
        mObject = e;
    }

    public boolean getObject() {
        return mObject;
    }

    public void setPosProcess(boolean e) {
        mPosProcess = e;
    }

    public boolean getPosProcess() {
        return mPosProcess;
    }

    public void setStackProcess(boolean e) {
        mStackProcess = e;
    }

    public boolean getStackProcess() {
        return mStackProcess;
    }


    public void setAnalysisProcess(boolean e) {
        mAnaliseProcess = e;
    }

    public boolean getAnalysisProcess() {
        return mAnaliseProcess;
    }

    public boolean geral(String eArquivo, String saida, int mOpcao) {

        boolean ret = false;

        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";


        System.out.println("################# SIGMAZ #################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo, mOpcao);

        System.out.println("############## PRE PROCESSAMENTO ###############");
        System.out.println("");

        if (mStackProcess) {
            System.out.println(CompilerC.getPreProcessamento());
        }

        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Arquivo : " + eArquivo);
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + CompilerC.getData().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }

        }

        if (CompilerC.getErros_Compiler().size() > 0) {

            System.out.println("");
            System.out.println("################ AST - COM DEFEITOS ################");
            System.out.println("");

            if (mObject) {
                Documentador DC = new Documentador();
                System.out.println(CompilerC.getArvoreDeInstrucoes());
            }


            System.out.println("\n\t ERROS DE COMPILACAO : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            System.out.println("");
            System.out.println("################ ANALISE ################");
            System.out.println("");


            Analisador AnaliseC = new Analisador();
            String AI = CompilerC.getData().toString();

            if (mObject) {
                System.out.println(CompilerC.getArvoreDeInstrucoes());

            }


            AnaliseC.init(CompilerC.getASTS(), mLocal);
            String AF = CompilerC.getData().toString();


            System.out.println("\t - Iniciado : " + AI);
            System.out.println("\t - Finalizado : " + AF);


            System.out.println("\t - Erros : " + AnaliseC.getErros().size());

            if (mAnaliseProcess) {
                AnaliseC.MostrarMensagens();
            }


            if (AnaliseC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE ANALISE : ");

                for (String Erro : AnaliseC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }

            } else {


                System.out.println("");
                System.out.println("################ POS-PROCESSAMENTO ################");
                System.out.println("");

                String AI_PosProcessamento = CompilerC.getData().toString();

                PosProcessador PosProcessadorC = new PosProcessador();

                PosProcessadorC.init(mCabecalho,CompilerC.getASTS(), mLocal);

                String AF_PosProcessamento = CompilerC.getData().toString();

                System.out.println("\t - Iniciado : " + AI_PosProcessamento);
                System.out.println("\t - Finalizado : " + AF_PosProcessamento);

                if (mPosProcess) {
                    PosProcessadorC.MostrarMensagens();
                }


                if (PosProcessadorC.getErros().size() > 0) {
                    System.out.println("\n\t ERROS DE POS-PROCESSAMENTO : ");


                    for (String Erro : PosProcessadorC.getErros()) {
                        System.out.println("\t\t" + Erro);
                    }
                } else {


                    System.out.println("");
                    System.out.println("################ OBJETO ################");
                    System.out.println("");


                    CompilerC.Compilar(saida);

                    Documentador DC2 = new Documentador();

                    System.out.println("\t Iniciado : " + CompilerC.getData().toString());
                    System.out.println("\t - Tamanho : " + DC2.tamanhoObjeto(saida));
                    System.out.println("\t - Saida : " + saida);
                    System.out.println("\t Finalizado : " + CompilerC.getData().toString());

                    System.out.println("");

                    if (mObject) {

                        System.out.println(CompilerC.imprimirArvore());


                    }


                    ret = true;

                }


            }


        } else {


        }

        return ret;
    }

    public boolean geralvarios(ArrayList<String> eArquivos, String saida, int mOpcao) {

        boolean ret = false;

        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";


        System.out.println("################# SIGMAZ #################");
        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");
        System.out.println(" - INICIO  	: 2020.06.12");

        System.out.println("");


        Compiler CompilerC = new Compiler();
        CompilerC.initvarios(eArquivos, mOpcao);

        System.out.println("############## PRE PROCESSAMENTO ###############");
        System.out.println("");
        if (mStackProcess) {
            System.out.println(CompilerC.getPreProcessamento());
        }
        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());

        for (String eArquivo : eArquivos) {
            System.out.println("\t - Arquivo : " + eArquivo);
        }
        System.out.println("\t - Chars : " + CompilerC.getIChars());
        System.out.println("\t - Tokens : " + CompilerC.getITokens());
        System.out.println("\t - Erros : " + CompilerC.getErros_Lexer().size());
        System.out.println("\t Finalizado : " + CompilerC.getData().toString());
        System.out.println("");


        System.out.println("############### COMPILADOR ###############");
        System.out.println("");
        System.out.println("\t Iniciado : " + CompilerC.getData().toString());
        System.out.println("\t - Instrucoes : " + CompilerC.getInstrucoes());
        System.out.println("\t - Erros : " + CompilerC.getErros_Compiler().size());
        System.out.println("\t - Requisitados : ");

        for (String Req : CompilerC.getRequisitados()) {
            System.out.println("\t\t - " + Req);
        }

        System.out.println("\t Finalizado : " + CompilerC.getData().toString());


        if (CompilerC.getErros_Lexer().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }

        }

        if (CompilerC.getErros_Compiler().size() > 0) {

            System.out.println("");
            System.out.println("################ AST - COM DEFEITOS ################");
            System.out.println("");

            if (mObject) {
                Documentador DC = new Documentador();
                System.out.println(CompilerC.getArvoreDeInstrucoes());
            }

            System.out.println("\n\t ERROS DE COMPILACAO : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                System.out.println("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


        if (CompilerC.getErros_Lexer().size() == 0 && CompilerC.getErros_Compiler().size() == 0) {


            System.out.println("");
            System.out.println("################ ANALISE ################");
            System.out.println("");


            Analisador AnaliseC = new Analisador();
            String AI = CompilerC.getData().toString();

            if (mObject) {

                System.out.println(CompilerC.getArvoreDeInstrucoes());

            }


            AnaliseC.init(CompilerC.getASTS(), mLocal);
            String AF = CompilerC.getData().toString();


            System.out.println("\t - Iniciado : " + AI);
            System.out.println("\t - Finalizado : " + AF);


            System.out.println("\t - Erros : " + AnaliseC.getErros().size());

            if (mAnaliseProcess) {
                AnaliseC.MostrarMensagens();
            }

            if (AnaliseC.getErros().size() > 0) {
                System.out.println("\n\t ERROS DE ANALISE : ");

                for (String Erro : AnaliseC.getErros()) {
                    System.out.println("\t\t" + Erro);
                }

            } else {


                System.out.println("");
                System.out.println("################ POS-PROCESSAMENTO ################");
                System.out.println("");

                String AI_PosProcessamento = CompilerC.getData().toString();

                PosProcessador PosProcessadorC = new PosProcessador();

                PosProcessadorC.init(mCabecalho,CompilerC.getASTS(), mLocal);

                String AF_PosProcessamento = CompilerC.getData().toString();

                System.out.println("\t - Iniciado : " + AI_PosProcessamento);
                System.out.println("\t - Finalizado : " + AF_PosProcessamento);


                if (mPosProcess) {
                    PosProcessadorC.MostrarMensagens();
                }

                if (PosProcessadorC.getErros().size() > 0) {
                    System.out.println("\n\t ERROS DE POS-PROCESSAMENTO : ");


                    for (String Erro : PosProcessadorC.getErros()) {
                        System.out.println("\t\t" + Erro);
                    }
                } else {


                    System.out.println("");
                    System.out.println("################ OBJETO ################");
                    System.out.println("");


                    CompilerC.Compilar(saida);

                    Documentador DC2 = new Documentador();

                    System.out.println("\t Iniciado : " + CompilerC.getData().toString());
                    System.out.println("\t - Tamanho : " + DC2.tamanhoObjeto(saida));
                    System.out.println("\t - Saida : " + saida);
                    System.out.println("\t Finalizado : " + CompilerC.getData().toString());

                    System.out.println("");

                    if (mObject) {
                        System.out.println(CompilerC.imprimirArvore());
                    }


                    ret = true;

                }


            }


        } else {


        }

        return ret;
    }


    public void geral_simples(String eArquivo, String saida, int mOpcao) {

        mTemErros = false;
        mMensagens.clear();


        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";

        mLocalErro = "PROCESSAMENTO";


        Compiler CompilerC = new Compiler();
        CompilerC.init(eArquivo, mOpcao);

        mLocalErro = "PROCESSAMENTO";

        mLocalErro = "LEXER";

        if (CompilerC.getErros_Lexer().size() == 0) {

            mLocalErro = "COMPILADOR";


            if (CompilerC.getErros_Compiler().size() == 0) {

                mLocalErro = "ANALISADOR";

                Analisador AnaliseC = new Analisador();
                AnaliseC.init(CompilerC.getASTS(), mLocal);

                if (AnaliseC.getErros().size() == 0) {

                    mLocalErro = "POS-PROCESSADOR";

                    PosProcessador PosProcessadorC = new PosProcessador();

                    PosProcessadorC.init(mCabecalho,CompilerC.getASTS(), mLocal);

                    if (PosProcessadorC.getErros().size() == 0) {

                        CompilerC.Compilar(saida);

                        mTemErros = false;

                    } else {


                        mTemErros = true;
                        mMensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");


                        for (String Erro : PosProcessadorC.getErros()) {
                            mMensagens.add("\t\t" + Erro);
                        }

                    }

                } else {

                    mTemErros = true;

                    mMensagens.add("\n\t ERROS DE ANALISE : ");

                    for (String Erro : AnaliseC.getErros()) {
                        mMensagens.add("\t\t" + Erro);
                    }

                }

            } else {


                mTemErros = true;

                mMensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                    mMensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }
            }


        } else {

            mTemErros = true;

            mMensagens.add("\n\t ERROS DE LEXER : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                mMensagens.add("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


    }

    public void geralvarios_simples(ArrayList<String> eArquivos, String saida, int mOpcao) {

        mTemErros = false;
        mMensagens.clear();


        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";

        mLocalErro = "PROCESSAMENTO";


        Compiler CompilerC = new Compiler();
        CompilerC.initvarios(eArquivos, mOpcao);

        mLocalErro = "PROCESSAMENTO";

        mLocalErro = "LEXER";

        if (CompilerC.getErros_Lexer().size() == 0) {

            mLocalErro = "COMPILADOR";


            if (CompilerC.getErros_Compiler().size() == 0) {

                mLocalErro = "ANALISADOR";

                Analisador AnaliseC = new Analisador();
                AnaliseC.init(CompilerC.getASTS(), mLocal);

                if (AnaliseC.getErros().size() == 0) {

                    mLocalErro = "POS-PROCESSADOR";

                    PosProcessador PosProcessadorC = new PosProcessador();

                    PosProcessadorC.init(mCabecalho,CompilerC.getASTS(), mLocal);

                    if (PosProcessadorC.getErros().size() == 0) {

                        CompilerC.Compilar(saida);

                        mTemErros = false;

                    } else {


                        mTemErros = true;
                        mMensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");


                        for (String Erro : PosProcessadorC.getErros()) {
                            mMensagens.add("\t\t" + Erro);
                        }

                    }

                } else {

                    mTemErros = true;

                    mMensagens.add("\n\t ERROS DE ANALISE : ");

                    for (String Erro : AnaliseC.getErros()) {
                        mMensagens.add("\t\t" + Erro);
                    }

                }

            } else {


                mTemErros = true;

                mMensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                    mMensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }
            }


        } else {

            mTemErros = true;

            mMensagens.add("\n\t ERROS DE LEXER : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                mMensagens.add("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


    }


    public void compilar_executavel(String eArquivo, String saida) {

        if (geral(eArquivo, saida, 1)) {

        }

    }

    public void compilar_biblioteca(String eArquivo, String saida) {

        if (geral(eArquivo, saida, 2)) {

        }

    }

    public void init(String eArquivo, String saida, int mOpcao) {


        if (geral(eArquivo, saida, mOpcao)) {

            executar(eArquivo, saida);

        }

    }


    public void init_simples(String eArquivo, String saida, int mOpcao) {

        geral_simples(eArquivo, saida, mOpcao);

        if (!mTemErros) {

            executar(eArquivo, saida);

        } else {

            System.out.println("");
            System.out.println("################ SIGMAZ ################");
            System.out.println("");
            System.out.println("\t - Source : " + eArquivo);
            System.out.println("\t - Status : PROBLEMA");
            System.out.println("\t - Fase : " + mLocalErro);

            System.out.println("----------------------------------------------");
            System.out.println("");

            for (String eMensagem : mMensagens) {
                System.out.println(eMensagem);
            }

            System.out.println("");
            System.out.println("----------------------------------------------");

        }

    }


    private void executar(String eArquivo, String saida) {

        System.out.println("");
        System.out.println("################ RUNTIME ################");
        System.out.println("");
        System.out.println("\t - Source : " + eArquivo);
        System.out.println("\t - Executando : " + saida);

        RunTime RunTimeC = new RunTime();
        String DI = RunTimeC.getData();


        RunTimeC.init(saida);

        System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
        System.out.println("");


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

    public void estrutural(String eArquivo, String saida, boolean mostrarAST) {


        if (geral(eArquivo, saida, 0)) {


            System.out.println("");
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.estrutura();


            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

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

    public void interno(String eArquivo, String saida, String eLocal) {


        if (geral(eArquivo, saida, 0)) {


            System.out.println("");
            System.out.println("################ RUNTIME ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.interno(eLocal);


            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

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


    public void uml(String eArquivo, String saida, String eGrafico) {

        if (geral(eArquivo, saida, 0)) {


            System.out.println("");
            System.out.println("################ UML ################");
            System.out.println("");
            System.out.println("\t - Executando : " + saida);
            System.out.println("");

            RunTime RunTimeC = new RunTime();
            String DI = RunTimeC.getData().toString();


            RunTimeC.init(saida);

            System.out.println("\t - Instrucoes : " + RunTimeC.getInstrucoes());
            System.out.println("");


            System.out.println(RunTimeC.getArvoreDeInstrucoes());

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            RunTimeC.uml(eGrafico);

            System.out.println("");
            System.out.println("----------------------------------------------");
            System.out.println("");

            String DF = RunTimeC.getData().toString();

            System.out.println("\t - Iniciado : " + DI);
            System.out.println("\t - Finalizado : " + DF);


            System.out.println("");
            System.out.println("----------------------------------------------");


        }

    }

    public void intellisense(String eArquivo, String saida, boolean mostrarDebug, IntellisenseTheme
            eIntellisenseTheme, String eGrafico) {

        ArrayList<String> lista = new ArrayList<String>();
        lista.add(eArquivo);

        intellisense(lista, saida, mostrarDebug, eIntellisenseTheme, eGrafico);

    }

    public void intellisense(ArrayList<String> eArquivos, String saida, boolean mostrarDebug, IntellisenseTheme
            eIntellisenseTheme, String eGrafico) {

        mTemErros = false;
        mMensagens.clear();


        File arq = new File(saida);
        String mLocal = arq.getParent() + "/";

        mLocalErro = "PROCESSAMENTO";


        Compiler CompilerC = new Compiler();
        CompilerC.initvarios(eArquivos, 1);

        mLocalErro = "PROCESSAMENTO";

        mLocalErro = "LEXER";

        if (CompilerC.getErros_Lexer().size() == 0) {

            mLocalErro = "COMPILADOR";


            if (CompilerC.getErros_Compiler().size() == 0) {

                mLocalErro = "ANALISADOR";

                Analisador AnaliseC = new Analisador();
                AnaliseC.init(CompilerC.getASTS(), mLocal);

                if (AnaliseC.getErros().size() == 0) {

                    mLocalErro = "POS-PROCESSADOR";

                    PosProcessador PosProcessadorC = new PosProcessador();

                    PosProcessadorC.init(mCabecalho,CompilerC.getASTS(), mLocal);

                    if (PosProcessadorC.getErros().size() == 0) {


                        //CompilerC.Compilar(saida);


                        //  System.out.println("");
                        // System.out.println("################ INTELISENSE ################");
                        //  System.out.println("");
                        //  System.out.println("\t - Executando : " + saida);
                        //   System.out.println("");


                        String DI = Tempo.getData();


                        Intellisense IntellisenseC = new Intellisense();
                        IntellisenseC.run(CompilerC.getASTS(), eIntellisenseTheme, eGrafico);


                        //  System.out.println("");
                        //  System.out.println("----------------------------------------------");
                        //  System.out.println("");

                        //   String DF = Tempo.getData();

                        //  System.out.println("\t - Iniciado : " + DI);
                        //  System.out.println("\t - Finalizado : " + DF);


                        // System.out.println("");
                        // System.out.println("----------------------------------------------");


                        mTemErros = false;

                    } else {


                        mTemErros = true;
                        mMensagens.add("\n\t ERROS DE POS-PROCESSAMENTO : ");


                        for (String Erro : PosProcessadorC.getErros()) {
                            mMensagens.add("\t\t" + Erro);
                        }

                    }

                } else {

                    mTemErros = true;

                    mMensagens.add("\n\t ERROS DE ANALISE : ");

                    for (String Erro : AnaliseC.getErros()) {
                        mMensagens.add("\t\t" + Erro);
                    }

                }

            } else {


                mTemErros = true;

                mMensagens.add("\n\t ERROS DE COMPILACAO : ");

                for (GrupoDeErro eGE : CompilerC.getErros_Compiler()) {
                    mMensagens.add("\t\t" + eGE.getArquivo());
                    for (Erro eErro : eGE.getErros()) {
                        mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                    }
                }
            }


        } else {

            mTemErros = true;

            mMensagens.add("\n\t ERROS DE LEXER : ");

            for (GrupoDeErro eGE : CompilerC.getErros_Lexer()) {
                mMensagens.add("\t\t" + eGE.getArquivo());
                for (Erro eErro : eGE.getErros()) {
                    mMensagens.add("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }
            }
        }


    }

    public void initDependencia(String eArquivo) {


        Dependenciador mDependenciador = new Dependenciador();
        mDependenciador.init_debug(eArquivo);

    }


}
