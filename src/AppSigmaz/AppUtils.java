package AppSigmaz;

import Sigmaz.Executor.UML;
import Sigmaz.Ferramentas.Dependenciador;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Intellisenses.IntellisenseTheme;
import Sigmaz.Lexer.Lexer;
import Sigmaz.Lexer.Token;
import Sigmaz.Sigmaz;
import Sigmaz.Sigmaz_Fases;

import Sigmaz.Comentarios;
import Sigmaz.Todos;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import Sigmaz.Utils.Erro;
import Sigmaz.Utils.Identador;
import Sigmaz.Sigmaz_SemObjeto;
import Sigmaz.Internal;
import Sigmaz.Utils.Syntax_HighLight;

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

    public static void COMPILAR_BIBLIOTECA(int eIndice, ArrayList<String> mArquivos, String eCompilado) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Sigmaz SigmazC = new Sigmaz();
                SigmazC.setObject(true);

                SigmazC.compilar_biblioteca(mArquivo, eCompilado);


                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void COMPILAR_EXECUTAVEL(int eIndice, ArrayList<String> mArquivos, String eCompilado) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz SigmazC = new Sigmaz();
            SigmazC.setObject(true);

            SigmazC.compilar_executavel(mOpcional.getConteudo(), eCompilado);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void EXECUTAR(int eIndice, ArrayList<String> mArquivos, String eCompilado) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz SigmazC = new Sigmaz();
            SigmazC.setObject(false);

            SigmazC.init(mOpcional.getConteudo(), eCompilado, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void EXECUTAR_SIMPLES(int eIndice, ArrayList<String> mArquivos, String eCompilado) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz SigmazC = new Sigmaz();

            SigmazC.init_simples(mOpcional.getConteudo(), eCompilado, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void EXECUTAR_FASES(int eIndice, ArrayList<String> mArquivos, String eCompilado) {


        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {

            Sigmaz_Fases SigmazC = new Sigmaz_Fases();

            SigmazC.mostrarDebug(true);

            SigmazC.init(mOpcional.getConteudo(), eCompilado, 1);


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

    public static void MONTAR_BIBLIOTECA(String eArquivo, String eSaida) {


        Sigmaz SigmazC = new Sigmaz();
        SigmazC.setObject(false);

        SigmazC.estrutura(eArquivo, eSaida, 1);


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


    public static void ESTRUTURAL(int eIndice, ArrayList<String> mArquivos, String eSaida) {



        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Sigmaz SigmazC = new Sigmaz();
            SigmazC.setObject(false);

            SigmazC.estrutura(mOpcional.getConteudo(), eSaida, 1);


        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }



    }

    public static void INTERNO(int eIndice, ArrayList<String> mArquivos, String eSaida, String eLocal) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Sigmaz_SemObjeto SigmazC = new Sigmaz_SemObjeto();
                SigmazC.init(mArquivo, eSaida, false);

                if (!SigmazC.temErros()) {

                    Internal mInternal = new Internal(SigmazC.getASTS());
                    mInternal.exportar(eLocal);

                }

                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }



    }


    public static void INTELISENSE(int eIndice, ArrayList<String> mArquivos, String eSaida, String eGrafico) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Sigmaz_SemObjeto SigmazC = new Sigmaz_SemObjeto();
                SigmazC.init(mArquivo, eSaida, false);

                if (!SigmazC.temErros()) {

                    Intellisense IntellisenseC = new Intellisense();
                    IntellisenseC.run(SigmazC.getASTS(), new IntellisenseTheme(), eGrafico);


                }

                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void INTELISENSE_BIBLIOTECA(String eFonte, String eSaida, String eGrafico) {


        Sigmaz_SemObjeto SigmazC = new Sigmaz_SemObjeto();
        SigmazC.init(eFonte, eSaida, false);

        if (!SigmazC.temErros()) {

            Intellisense IntellisenseC = new Intellisense();
            IntellisenseC.run(SigmazC.getASTS(), new IntellisenseTheme(), eGrafico);


        }


    }

    public static void UML(int eIndice, ArrayList<String> mArquivos, String eSaida, String eGrafico) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Sigmaz_SemObjeto SigmazC = new Sigmaz_SemObjeto();
                SigmazC.init(mArquivo, eSaida, false);

                if (!SigmazC.temErros()) {

                    UML UMLC = new UML();
                    UMLC.run(SigmazC.getASTS(), eGrafico);


                }

                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void UML_BIBLIOTECA(String eFonte, String eSaida, String eGrafico) {


        Sigmaz_SemObjeto SigmazC = new Sigmaz_SemObjeto();
        SigmazC.init(eFonte, eSaida, false);

        if (!SigmazC.temErros()) {

            UML UMLC = new UML();
            UMLC.run(SigmazC.getASTS(), eGrafico);


        }


    }

    public static void TESTE_GERAL(ArrayList<String> mArquivos, String eCompilado, String mLocal) {

        System.out.println("");

        SigmazTestes mSigmazTestes = new SigmazTestes();
        mSigmazTestes.setSaida(eCompilado);

        for (String mArquivo : mArquivos) {
            mSigmazTestes.adicionar(mArquivo);
        }

        mSigmazTestes.init(mLocal, "SIGMAZ - TESTES UNITARIOS");

    }


    public static void SYNTAX_HIGHLIGHT(int eIndice, ArrayList<String> mArquivos,String eLocal) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;

                Syntax_HighLight mSyntax_HighLight = new Syntax_HighLight();
                mSyntax_HighLight.init(mArquivo,eLocal);


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


}
