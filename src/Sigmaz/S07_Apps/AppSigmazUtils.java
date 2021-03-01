package Sigmaz.S07_Apps;

import AppSigmaz.Opcional;
import AppSigmaz.OrdenadorSigmaz;
import BioGerador.BioGerador;
import Sigmaz.S00_Utilitarios.Tempo;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.UML;
import Sigmaz.S06_Ferramentas.*;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.S01_Compilador.C02_Lexer.Lexer;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.Sigmaz_Executor;
import Sigmaz.Sigmaz_Compilador;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import Sigmaz.S00_Utilitarios.Erro;

public class AppSigmazUtils {

    public static void LEXER(int eIndice, ArrayList<String> mArquivos) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Lexer LexerC = new Lexer();

            System.out.println("################# LEXER ##################");
            System.out.println("");
            System.out.println("\t Iniciado : " + LexerC.getData().toString());
            System.out.println("\t - Arquivo : " + mOpcional.getConteudo());


            LexerC.init(mOpcional.getConteudo());

            System.out.println("\t - Chars : " + LexerC.getChars());
            System.out.println("\t - Tokens : " + LexerC.getTokens().size());
            System.out.println("\t - Erros : " + LexerC.getErros().size());
            System.out.println("\t Finalizado : " + LexerC.getData().toString());
            System.out.println("");


            if (LexerC.getErros().size() > 0) {

                System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

                for (Erro eErro : LexerC.getErros()) {
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem());
                }


            } else {


                for (Token TokenC : LexerC.getTokens()) {
                    System.out.println(" -->> " + TokenC.getTipo() + " = " + TokenC.getConteudo());
                }

            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void COMPILAR_BIBLIOTECA(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs, boolean mDebugar) {

        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eCompilado, eLocalLibs, 2, mDebugar);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void COMPILAR_EXECUTAVEL(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs, boolean mDebugar) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1, mDebugar);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void COMPILAR_SIMPLES(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs, boolean mDebugar) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);
            SigmazC.setMostrar_Mensagens(false);
            SigmazC.setDebug_Integralizador(false);

            SigmazC.init(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1, mDebugar);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void EXECUTAR(String eCompilado, String eLocalLibs, boolean mDebugar) {

        Sigmaz_Executor mSE = new Sigmaz_Executor();
        mSE.setMostrar_Execucao(true);
        mSE.executar(eCompilado, mDebugar);

    }

    public static void COMPILAR_DETALHADO(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs, boolean mDebugar) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrar_Erros(true);

            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);


            SigmazC.setDebug_PreProcessamento(true);
            SigmazC.setDebug_Lexer(false);
            SigmazC.setDebug_Parser(false);
            SigmazC.setDebug_Comentario(false);
            SigmazC.setDebug_Integralizador(false);
            SigmazC.setDebug_Montagem(true);
            SigmazC.setMostrar_Debugador(false);

            SigmazC.setDebug_PosProcessador_Requisidor(false);

            SigmazC.setDebug_PosProcessador_Cast(false);
            SigmazC.setDebug_PosProcessador_Unificador(false);
            SigmazC.setDebug_PosProcessador_Heranca(false);
            SigmazC.setDebug_PosProcessador_Modelador(false);
            SigmazC.setDebug_PosProcessador_Estagiador(false);

            SigmazC.setDebug_PosProcessador_Referenciador(false);
            SigmazC.setDebug_PosProcessador_Argumentador(false);
            SigmazC.setDebug_PosProcessador_Opcionador(false);

            SigmazC.setDebug_PosProcessador_Alocador(false);
            SigmazC.setDebug_PosProcessador_Tipador(false);
            SigmazC.setDebug_PosProcessador_Estruturador(false);
            SigmazC.setDebug_PosProcessador_Unicidade(false);

            SigmazC.init(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1, mDebugar);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void COMPILAR_DIRETO_DETALHADO(String eSigmazCodigo, String eCompilado, String eLocalLibs, boolean mDebugar) {


        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

        SigmazC.mostrarDebug(true);
        SigmazC.setMostrar_Erros(true);

        SigmazC.setMostrarArvoreRunTime(false);
        SigmazC.setMostrar_ArvoreFalhou(false);


        SigmazC.setDebug_PreProcessamento(true);
        SigmazC.setDebug_Lexer(false);
        SigmazC.setDebug_Parser(false);
        SigmazC.setDebug_Comentario(false);
        SigmazC.setDebug_Integralizador(false);
        SigmazC.setDebug_Montagem(true);
        SigmazC.setMostrar_Debugador(false);

        SigmazC.setDebug_PosProcessador_Requisidor(false);

        SigmazC.setDebug_PosProcessador_Cast(false);
        SigmazC.setDebug_PosProcessador_Unificador(false);
        SigmazC.setDebug_PosProcessador_Heranca(false);
        SigmazC.setDebug_PosProcessador_Modelador(false);
        SigmazC.setDebug_PosProcessador_Estagiador(false);

        SigmazC.setDebug_PosProcessador_Referenciador(false);
        SigmazC.setDebug_PosProcessador_Argumentador(false);
        SigmazC.setDebug_PosProcessador_Opcionador(false);

        SigmazC.setDebug_PosProcessador_Alocador(false);
        SigmazC.setDebug_PosProcessador_Tipador(false);
        SigmazC.setDebug_PosProcessador_Estruturador(false);
        SigmazC.setDebug_PosProcessador_Unicidade(false);

        SigmazC.initDireto(eSigmazCodigo, eCompilado, eLocalLibs, 1, mDebugar);


    }


    public static void TESTE_UNITARIO(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs, boolean mDebugar) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrar_Erros(true);

            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);


            SigmazC.setDebug_PreProcessamento(true);
            SigmazC.setDebug_Lexer(false);
            SigmazC.setDebug_Parser(false);
            SigmazC.setDebug_Comentario(false);
            SigmazC.setDebug_Integralizador(false);
            SigmazC.setDebug_Montagem(true);
            SigmazC.setMostrar_Debugador(false);

            SigmazC.setDebug_PosProcessador_Requisidor(false);

            SigmazC.setDebug_PosProcessador_Cast(false);
            SigmazC.setDebug_PosProcessador_Unificador(false);
            SigmazC.setDebug_PosProcessador_Heranca(false);
            SigmazC.setDebug_PosProcessador_Modelador(false);
            SigmazC.setDebug_PosProcessador_Estagiador(false);

            SigmazC.setDebug_PosProcessador_Referenciador(false);
            SigmazC.setDebug_PosProcessador_Argumentador(false);
            SigmazC.setDebug_PosProcessador_Opcionador(false);

            SigmazC.setDebug_PosProcessador_Alocador(false);
            SigmazC.setDebug_PosProcessador_Tipador(false);
            SigmazC.setDebug_PosProcessador_Estruturador(false);
            SigmazC.setDebug_PosProcessador_Unicidade(false);

            SigmazC.testes_unitarios(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1, mDebugar);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static Opcional OBTER_ARQUIVO(int eIndice, ArrayList<String> mArquivos) {


        Opcional mOpcional = new Opcional();

        int iContando = 0;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                mOpcional.validar(mArquivo);

                break;
            }
        }


        return mOpcional;
    }

    public static void MONTAR_BIBLIOTECA(String eArquivo, String eSaida, String eLocalLibs, boolean mDebugar) {


        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
        SigmazC.mostrarDebug(true);
        SigmazC.initSemExecucao(eArquivo, eSaida, eLocalLibs, 2, mDebugar);


    }

    public static void DEPENDENCIA(int eIndice, ArrayList<String> mArquivos, String mLocallibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Dependenciador mDependenciador = new Dependenciador();
            mDependenciador.init_debug(mOpcional.getConteudo(), mLocallibs);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void ESTRUTURAL(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1, false);

            if (!SigmazC.temErros()) {

                System.out.println("");

                RunTime RunTimeC = new RunTime();
                RunTimeC.init(eSaida, false);
                RunTimeC.estrutura();


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void INTERNO(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs, String eLocal) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1, false);

            if (!SigmazC.temErros()) {

                Internal mInternal = new Internal(SigmazC.getASTS());
                mInternal.exportar(eLocal);


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void INTELISENSE(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs, String eLocalIntellisenses) {

        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
            SigmazC.initIntellisenses(mOpcional.getConteudo(), eSaida, eLocalLibs, 1, eLocalIntellisenses);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void INTELISENSE_BIBLIOTECA(String eFonte, String eSaida, String eLocalLibs, String eLocalIntellisenses) {


        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
        SigmazC.initIntellisenses(eFonte, eSaida, eLocalLibs, 1, eLocalIntellisenses);


    }

    public static void UML(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs, String eGrafico) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1, false);

            if (!SigmazC.temErros()) {

                UML UMLC = new UML();
                UMLC.run(SigmazC.getASTS(), eGrafico);


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void UML_BIBLIOTECA(String eFonte, String eSaida, String eLocalLibs, String eGrafico) {


        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
        SigmazC.initSemExecucao(eFonte, eSaida, eLocalLibs, 1, false);


        if (!SigmazC.temErros()) {

            UML UMLC = new UML();
            UMLC.run(SigmazC.getASTS(), eGrafico);


        }


    }

    public static void TESTE_GERAL(ArrayList<String> mArquivos, String eCompilado, String mLocal, String mLocalLibs) {


        SigmazTestes mSigmazTestes = new SigmazTestes();
        mSigmazTestes.setSaida(eCompilado);

        for (String mArquivo : mArquivos) {
            mSigmazTestes.adicionar(mArquivo);
        }


        mSigmazTestes.init(mLocalLibs, "SIGMAZ - TESTES UNITARIOS");

    }


    public static void SYNTAX_HIGHLIGHT(int eIndice, ArrayList<String> mArquivos, String eLocal) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Syntax_HighLight mSyntax_HighLight = new Syntax_HighLight();
            mSyntax_HighLight.init(mOpcional.getConteudo(), eLocal);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void IDENTAR(int eIndice, ArrayList<String> mArquivos) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Identador mIdentador = new Identador();
            mIdentador.init(mOpcional.getConteudo(), mOpcional.getConteudo());


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void IDENTAR_LOTE(String eNome, ArrayList<String> mArquivos) {

        System.out.println("");
        System.out.println("################ IDENTADOR - " + eNome + " ################");

        long start = System.currentTimeMillis();

        Identador mIdentador = new Identador();
        String DDI = Tempo.getData();

        System.out.println("");
        System.out.println(" - AUTOR	: LUAN FREITAS");
        System.out.println(" - VERSAO   : 1.0");
        System.out.println(" - STATUS  	: ALPHA");

        System.out.println("");
        System.out.println(" - INICIO  	: " + DDI);
        System.out.println("");

        int Contador = 1;

        int mQuantidade = mArquivos.size();
        int mSucesso = 0;
        int mProblema = 0;

        for (String mArquivo : mArquivos) {

            String sContador = String.valueOf(Contador);

            if (sContador.length() == 1) {
                sContador = "0" + sContador;
            }

            boolean t = mIdentador.initSimples(mArquivo, mArquivo);

            if (t) {
                System.out.println(" Arquivo : " + sContador + " -> " + mArquivo + " : SUCESSO ");
                mSucesso += 1;

            } else {
                System.out.println(" Arquivo : " + sContador + " -> " + mArquivo + " : FALHOU ");
                mProblema += 1;

            }

            Contador += 1;

        }

        long end = System.currentTimeMillis();

        float sec = (end - start) / 1000F;

        System.out.println("");

        System.out.println(" - TEMPO  	: " + sec + " segundos");
        System.out.println("");


        NumberFormat formatarFloat = new DecimalFormat("0.00");

        if (mQuantidade > 0) {

            float s = ((float) mSucesso / (float) mQuantidade) * 100.0f;
            float f = ((float) mProblema / (float) mQuantidade) * 100.0f;

            System.out.println(" - TESTES  	: " + mQuantidade + " -> 100.00 % ");
            System.out.println("\t - SUCESSO  : " + mSucesso + " -> " + formatarFloat.format(s).replace(",", ".") + " % ");
            System.out.println("\t - FALHOU  	: " + mProblema + " -> " + formatarFloat.format(f).replace(",", ".") + " % ");

        }

    }

    public static void COMENTARIOS(int eIndice, ArrayList<String> mArquivos, String mLocalLibs) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Comentarios comentariosC = new Comentarios();

                comentariosC.init(mArquivo, mLocalLibs);

                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void TODO(int eIndice, ArrayList<String> mArquivos, String mLocallibs) {

        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Todos TodosC = new Todos();

            TodosC.init(mOpcional.getConteudo(), mLocallibs);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void ORDENADOR(String eLocal) {

        OrdenadorSigmaz mOrdenadorSigmaz = new OrdenadorSigmaz();
        mOrdenadorSigmaz.init(eLocal);

    }

    public static void PLANO_COMPILACAO(int eIndice, ArrayList<String> mArquivos, String eLocalPlanos, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            File eArq = new File(mOpcional.getConteudo());

            String eLocalPlano = eLocalPlanos + eArq.getName() + ".png";

            Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);

            SigmazC.montarPlano(mOpcional.getConteudo(), eLocalLibs, eLocalPlano, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void GERADOR_DETALHADO(String eLocalGerador, String eCompilado, String eLocalLibs, boolean mDebugar) {

        BioGerador mBioGerador = new BioGerador(eLocalGerador);
        mBioGerador.gerar();

        String mArquivo = eLocalGerador + "bio.sigmaz";

        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();

        SigmazC.mostrarDebug(true);
        SigmazC.setMostrarArvoreRunTime(false);
        SigmazC.setMostrar_ArvoreFalhou(false);

        SigmazC.init(mArquivo, eCompilado, eLocalLibs, 1, mDebugar);


    }


    public static void GERADOR_INTELISENSE(String eLocalGerador, String eSaida, String eLocalLibs, String eGrafico) {

        BioGerador mBioGerador = new BioGerador(eLocalGerador);
        mBioGerador.gerar();

        String mArquivo = eLocalGerador + "bio.sigmaz";


        Sigmaz_Compilador SigmazC = new Sigmaz_Compilador();
        SigmazC.initSemExecucao(mArquivo, eSaida, eLocalLibs, 1, false);

        if (!SigmazC.temErros()) {

            Intellisense IntellisenseC = new Intellisense();
            IntellisenseC.run(SigmazC.getASTS(), new IntellisenseTheme(), eGrafico);


            System.out.println("\t - 7 : Intellisense\t\t\t: SUCESSO");


        }


    }

    public static void DUMP(String eCompilado) {

        OLMDump mOMLDump = new OLMDump();
        mOMLDump.dump(eCompilado);

    }


    public static void RECURSOS(String eLocal) {

        AppRecursos mAppRecursos = new AppRecursos();
        mAppRecursos.init(eLocal);

    }
}
