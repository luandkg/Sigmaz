package AppSigmaz;

import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.UML;
import Sigmaz.S08_Ferramentas.Dependenciador;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.S03_Parser.Lexer;
import Sigmaz.S03_Parser.Token;
import Sigmaz.Sigmaz_Executor;
import Sigmaz.Sigmaz_Fases;

import Sigmaz.S08_Ferramentas.Comentarios;
import Sigmaz.S08_Ferramentas.Todos;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S08_Ferramentas.Identador;
import Sigmaz.S08_Ferramentas.Internal;
import Sigmaz.S08_Ferramentas.Syntax_HighLight;

public class AppUtils {

    public static void LEXER(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Lexer LexerC = new Lexer();

                System.out.println("################# LEXER ##################");
                System.out.println("");
                System.out.println("\t Iniciado : " + LexerC.getData().toString());
                System.out.println("\t - Arquivo : " + mArquivo);


                LexerC.init(mArquivo);

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


                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void COMPILAR_BIBLIOTECA(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs) {

        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz_Fases SigmazC = new Sigmaz_Fases();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eCompilado, eLocalLibs, 2);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void COMPILAR_EXECUTAVEL(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz_Fases SigmazC = new Sigmaz_Fases();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void COMPILAR_SIMPLES(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);
            SigmazC.setMostrar_Mensagens(false);

            SigmazC.init(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void EXECUTAR( String eCompilado, String eLocalLibs) {

        Sigmaz_Executor mSE = new Sigmaz_Executor();
        mSE.executar(eCompilado);

    }

    public static void COMPILAR_DETALHADO(int eIndice, ArrayList<String> mArquivos, String eCompilado, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);

            SigmazC.init(mOpcional.getConteudo(), eCompilado, eLocalLibs, 1);


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

    public static void MONTAR_BIBLIOTECA(String eArquivo, String eSaida, String eLocalLibs) {


        //  Sigmaz SigmazC = new Sigmaz();
        //  SigmazC.setObject(false);

        //    SigmazC.estrutura(eArquivo, eSaida, 1);


        Sigmaz_Fases SigmazC = new Sigmaz_Fases();
        SigmazC.initSemExecucao(eArquivo, eSaida, eLocalLibs, 2);


    }

    public static void DEPENDENCIA(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Dependenciador mDependenciador = new Dependenciador();
                mDependenciador.init_debug(mArquivo);


                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void ESTRUTURAL(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1);

            if (!SigmazC.temErros()) {

                System.out.println("");

                RunTime RunTimeC = new RunTime();
                RunTimeC.init(eSaida);
                RunTimeC.estrutura();


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void INTERNO(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs, String eLocal) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1);

            if (!SigmazC.temErros()) {

                Internal mInternal = new Internal(SigmazC.getASTS());
                mInternal.exportar(eLocal);


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void INTELISENSE(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs, String eGrafico) {

        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1);

            if (!SigmazC.temErros()) {

                Intellisense IntellisenseC = new Intellisense();
                IntellisenseC.run(SigmazC.getASTS(), new IntellisenseTheme(), eGrafico);


                System.out.println("\t - 7 : Intellisense\t\t\t: SUCESSO");


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void INTELISENSE_BIBLIOTECA(String eFonte, String eSaida, String eLocalLibs, String eGrafico) {


        Sigmaz_Fases SigmazC = new Sigmaz_Fases();
        SigmazC.initSemExecucao(eFonte, eSaida, eLocalLibs, 1);

        if (!SigmazC.temErros()) {

            Intellisense IntellisenseC = new Intellisense();
            IntellisenseC.run(SigmazC.getASTS(), new IntellisenseTheme(), eGrafico);


            System.out.println("\t - 7 : Intellisense\t\t\t: SUCESSO");


        }


    }

    public static void UML(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocalLibs, String eGrafico) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();
            SigmazC.initSemExecucao(mOpcional.getConteudo(), eSaida, eLocalLibs, 1);

            if (!SigmazC.temErros()) {

                UML UMLC = new UML();
                UMLC.run(SigmazC.getASTS(), eGrafico);


            }


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void UML_BIBLIOTECA(String eFonte, String eSaida, String eLocalLibs, String eGrafico) {


        Sigmaz_Fases SigmazC = new Sigmaz_Fases();
        SigmazC.initSemExecucao(eFonte, eSaida, eLocalLibs, 1);


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

        mSigmazTestes.init(mLocal, mLocalLibs, "SIGMAZ - TESTES UNITARIOS");

    }


    public static void SYNTAX_HIGHLIGHT(int eIndice, ArrayList<String> mArquivos, String eLocal) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;

                Syntax_HighLight mSyntax_HighLight = new Syntax_HighLight();
                mSyntax_HighLight.init(mArquivo, eLocal);


                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void IDENTAR(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;

                Identador mIdentador = new Identador();
                mIdentador.init(mArquivo, mArquivo);


                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void IDENTAR_LOTE(String eNome, ArrayList<String> mArquivos) {

        System.out.println("");
        System.out.println("################ IDENTADOR - " + eNome + " ################");

        long start = System.currentTimeMillis();

        Identador mIdentador = new Identador();
        String DDI = mIdentador.getData();

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

    public static void COMENTARIOS(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Comentarios comentariosC = new Comentarios();

                comentariosC.init(mArquivo);

                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void TODO(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Todos TodosC = new Todos();

                TodosC.init(mArquivo);

                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void PLANO_COMPILACAO(int eIndice, ArrayList<String> mArquivos, String eLocalPlanos, String eLocalLibs) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            File eArq = new File(mOpcional.getConteudo());

            String eLocalPlano = eLocalPlanos + eArq.getName() + ".png";

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();

            SigmazC.mostrarDebug(true);
            SigmazC.setMostrarArvoreRunTime(false);
            SigmazC.setMostrar_ArvoreFalhou(false);

            SigmazC.montarPlano(mOpcional.getConteudo(), eLocalLibs, eLocalPlano, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

}
