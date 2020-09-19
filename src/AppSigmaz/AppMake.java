package AppSigmaz;

import Make.Make;
import Sigmaz.Lexer.Lexer;
import Sigmaz.Lexer.Token;
import Sigmaz.Sigmaz;
import Sigmaz.Utils.Erro;

import java.util.ArrayList;

public class AppMake {

    public static void MAKE_LEXER(int eIndice, ArrayList<String> mArquivos) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Lexer LexerC = new Lexer();

                System.out.println("################# MAKE LEXER ##################");
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
                        System.out.println("\t\t    ->> " + eErro.getLinha() + ":" + eErro.getPosicao() + " -> " + eErro.getMensagem()); }


                }else{


                }





                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }

    public static void MAKE_EXECUTAR(int eIndice, ArrayList<String> mArquivos, String eCompilado,boolean mostrarAST) {


        int iContando = 0;
        boolean enc = false;

        for (String mArquivo : mArquivos) {
            iContando += 1;
            if (iContando == eIndice) {

                enc = true;


                Make MakeC = new Make();

                MakeC.init(mArquivo, mostrarAST);



              //  System.out.println("\n - Run MAKE : " + mArquivo);

                break;
            }
        }

        if (!enc) {
            System.out.println("\n - Indice de Arquivo nao encontrado : " + eIndice);
        }


    }


}
