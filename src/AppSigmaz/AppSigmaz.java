package AppSigmaz;

import Sigmaz.Sigmaz;

import Sigmaz.Utils.Identador;

import java.util.ArrayList;

public class AppSigmaz {

    public static void main(String[] args) {

        ArrayList<String> mArquivos = Carregadores.Carregar_Arquivos();
        ArrayList<String> mBibliotecas = Carregadores.Carregar_Bibliotecas();

        String mBiblioteca_Fonte = "res/libs/lib.sigmaz";
        String mBiblioteca_Sigmad = "res/lib.sigmad";


        Fases mFase = Fases.EXECUTAR;

        int ARQUIVO = 46;


        if (mFase == Fases.EXECUTAR) {

            UM(ARQUIVO, mArquivos);

        } else if (mFase == Fases.DEPENDENCIAS) {

            DEPENDENCIA(ARQUIVO, mArquivos);

        } else if (mFase == Fases.IDENTAR) {

            IDENTAR(ARQUIVO, mArquivos);

        } else if (mFase == Fases.ESTRUTURADOR) {

            ESTRUTURAL(ARQUIVO, mArquivos);

        } else if (mFase == Fases.TESTES) {

            TESTE_GERAL(mArquivos);

        } else if (mFase == Fases.IDENTAR_TUDO) {

            IDENTAR_LOTE("ARQUIVOS",mArquivos);

        } else if (mFase == Fases.IDENTAR_BIBLIOTECAS) {

            IDENTAR_LOTE("BIBLIOTECAS",mBibliotecas);

        } else if (mFase == Fases.MONTAR_BIBLIOTECAS) {

            MONTAR_BIBLIOTECA(mBiblioteca_Fonte,mBiblioteca_Sigmad);

        } else {

            System.out.println("\t - Fases : Desconhecida !");

        }


    }



    public static void UM(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;

                String saida = "res/Sigmaz.sigmad";

                Sigmaz SigmazC = new Sigmaz();

                SigmazC.init(mArquivo, saida);

                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void MONTAR_BIBLIOTECA(String eArquivo,String eSaida){

        Sigmaz SigmazC = new Sigmaz();

        SigmazC.estrutural(eArquivo, eSaida);

    }

    public static void DEPENDENCIA(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;

                Sigmaz SigmazC = new Sigmaz();

                SigmazC.initDependencia(mArquivo);

                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


    public static void ESTRUTURAL(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;

                String saida = "res/Sigmaz.sigmad";

                Sigmaz SigmazC = new Sigmaz();

                SigmazC.estrutural(mArquivo, saida);

                break;
            }
        }

        if (!enc) {
            System.out.println("Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void TESTE_GERAL(ArrayList<String> mArquivos) {

        System.out.println("");

        Testes mTestes = new Testes();

        for (String mArquivo : mArquivos) {
            mTestes.adicionar(mArquivo);
        }


        mTestes.init();

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

    public static void IDENTAR_LOTE(String eNome,ArrayList<String> mArquivos) {

        System.out.println("");
        System.out.println("################ IDENTADOR - " + eNome + " ################");

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

        for (String mArquivo : mArquivos) {

            String sContador = String.valueOf(Contador);

            if (sContador.length() == 1) {
                sContador = "0" + sContador;
            }

            boolean t = mIdentador.initSimples(mArquivo, mArquivo);

            if (t) {
                System.out.println(" Arquivo : " + sContador + " -> " + mArquivo + " : SUCESSO ");
            } else {
                System.out.println(" Arquivo : " + sContador + " -> " + mArquivo + " : FALHOU ");
            }

            Contador += 1;

        }


    }

}
