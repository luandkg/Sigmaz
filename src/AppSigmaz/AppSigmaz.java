package AppSigmaz;

import Sigmaz.Sigmaz;
import Sigmaz.CSigmaz;

import java.util.ArrayList;

public class AppSigmaz {

    public static void main(String[] args) {

        ArrayList<String> mArquivos = Carregar_Arquivos();


        int TESTE = 1;

        int ARQUIVO = 36;



        if (TESTE == 1) {

            UM(ARQUIVO, mArquivos);

        } else if (TESTE == 2) {

            ESTRUTURAL(ARQUIVO, mArquivos);

        } else {

            TESTE_GERAL(mArquivos);


        }


    }

    public static ArrayList<String> Carregar_Arquivos() {

        ArrayList<String> mArquivos = new ArrayList<String>();

        // INICIANTE

        mArquivos.add("res/01 - init.sigmaz");
        mArquivos.add("res/02 - arguments.sigmaz");
        mArquivos.add("res/03 - define.sigmaz");
        mArquivos.add("res/04 - require.sigmaz");
        mArquivos.add("res/05 - function.sigmaz");
        mArquivos.add("res/06 - functions2.sigmaz");
        mArquivos.add("res/07 - mockiz.sigmaz");
        mArquivos.add("res/08 - matches.sigmaz");

        // STATEMENTS

        mArquivos.add("res/09 - condition.sigmaz");
        mArquivos.add("res/10 - while.sigmaz");
        mArquivos.add("res/11 - cancel.sigmaz");
        mArquivos.add("res/12 - continue.sigmaz");
        mArquivos.add("res/13 - step.sigmaz");
        mArquivos.add("res/14 - stepdef.sigmaz");
        mArquivos.add("res/15 - when.sigmaz");
        mArquivos.add("res/16 - all.sigmaz");
        mArquivos.add("res/17 - test.sigmaz");
        mArquivos.add("res/18 - operations.sigmaz");

        // ESTRUTURANTES

        mArquivos.add("res/19 - cast.sigmaz");
        mArquivos.add("res/20 - cast2.sigmaz");
        mArquivos.add("res/21 - struct.sigmaz");
        mArquivos.add("res/22 - internal.sigmaz");
        mArquivos.add("res/23 - oo.sigmaz");
        mArquivos.add("res/24 - oo2.sigmaz");
        mArquivos.add("res/25 - recursao.sigmaz");
        mArquivos.add("res/26 - oorecursao.sigmaz");
        mArquivos.add("res/27 - construtor.sigmaz");

        // HERANCA

        mArquivos.add("res/28 - heranca.sigmaz");
        mArquivos.add("res/29 - initheranca.sigmaz");
        mArquivos.add("res/30 - initheranca2.sigmaz");
        mArquivos.add("res/31 - stages.sigmaz");

        // OUTROS

        mArquivos.add("res/32 - stages2.sigmaz");
        mArquivos.add("res/33 - oo.sigmaz");
        mArquivos.add("res/34 - visibility.sigmaz");
        mArquivos.add("res/35 - extern.sigmaz");
        mArquivos.add("res/36 - externs.sigmaz");

        return mArquivos;
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

        CSigmaz mCSigmaz = new CSigmaz();

        for (String mArquivo : mArquivos) {
            mCSigmaz.adicionar(mArquivo);
        }


        mCSigmaz.init();

    }

}
