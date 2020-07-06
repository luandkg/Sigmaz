package AppSigmaz;

import Sigmaz.Sigmaz;
import Sigmaz.Utils.Identador;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class AppUtils {


    public static void EXECUTAR(int eIndice, ArrayList<String> mArquivos,String eCompilado) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Sigmaz SigmazC = new Sigmaz();

                SigmazC.init(mArquivo, eCompilado);

                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void MONTAR_BIBLIOTECA(String eArquivo, String eSaida) {

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

    public static void TESTE_GERAL(ArrayList<String> mArquivos,String eCompilado) {

        System.out.println("");

        SigmazTestes mSigmazTestes = new SigmazTestes();
        mSigmazTestes.setSaida(eCompilado);
        for (String mArquivo : mArquivos) {
            mSigmazTestes.adicionar(mArquivo);
        }

        mSigmazTestes.init();

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
                mSucesso+=1;

            } else {
                System.out.println(" Arquivo : " + sContador + " -> " + mArquivo + " : FALHOU ");
                mProblema+=1;

            }

            Contador += 1;

        }

        long end = System.currentTimeMillis();

        float sec = (end - start) / 1000F;

        System.out.println("");

        System.out.println(" - TEMPO  	: " + sec + " segundos");
        System.out.println("");



        NumberFormat formatarFloat= new DecimalFormat("0.00");

        if (mQuantidade>0){

            float s = ((float)mSucesso/(float)mQuantidade)*100.0f;
            float f =( (float)mProblema/(float)mQuantidade)*100.0f;

            System.out.println(" - TESTES  	: " + mQuantidade + " -> 100.00 % ");
            System.out.println("\t - SUCESSO  : " + mSucesso + " -> " +  formatarFloat.format(s).replace(",", ".") + " % ");
            System.out.println("\t - FALHOU  	: " + mProblema+ " -> " + formatarFloat.format(f).replace(",", ".")+ " % ");

        }

    }

}
