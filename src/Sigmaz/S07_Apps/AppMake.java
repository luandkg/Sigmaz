package Sigmaz.S07_Apps;

import AppSigmaz.Opcional;
import Make.Make;
import Sigmaz.S01_Compilador.C02_Lexer.Lexer;
import Sigmaz.S08_Utilitarios.Erro;

import java.util.ArrayList;

public class AppMake {

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


    public static void MAKE_LEXER(int eIndice, ArrayList<String> mArquivos) {



        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Lexer LexerC = new Lexer();

            System.out.println("################# MAKE LEXER ##################");
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
                    System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem()); }


            }else{


            }



        } else {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }






    }

    public static void MAKE_EXECUTAR(int eIndice, ArrayList<String> mArquivos ) {

        Opcional mOpcional = OBTER_ARQUIVO(eIndice, mArquivos);

        if (mOpcional.estaValido()) {


            Make MakeC = new Make();

            MakeC.init(mOpcional.getConteudo(), true);


        }else{
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }



    }


}
