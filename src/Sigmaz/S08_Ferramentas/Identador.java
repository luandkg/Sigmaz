package Sigmaz.S08_Ferramentas;

import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S00_Utilitarios.Formatador;
import Sigmaz.S00_Utilitarios.Formatador2;
import Sigmaz.S00_Utilitarios.Texto;
import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;

import java.util.Calendar;

public class Identador {

    public void init(String eArquivoOrigem, String eArquivoDestino) {


        Lexer LexerC = new Lexer();

        String DI = LexerC.getData().toString();

        LexerC.init(eArquivoOrigem);

        String DF = LexerC.getData().toString();

        System.out.println("");
        System.out.println("################# LEXER ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + DI);
        System.out.println("\t - Origem : " + eArquivoOrigem);
        System.out.println("\t - Chars : " + LexerC.getChars());
        System.out.println("\t - Tokens : " + LexerC.getTokens().size());
        System.out.println("\t - Erros : " + LexerC.getErros().size());
        System.out.println("\t Finalizado : " + DF);
        System.out.println("");


        if (LexerC.getErros().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (Erro eErro : LexerC.getErros()) {
                System.out.println("\t\t    ->> " + eErro.getPosicao() + " : " + eErro.getMensagem());
            }

        } else {

            System.out.println("\t IDENTANDO");
            System.out.println("\t - Inicio : " + LexerC.getData());
            System.out.println("\t - Destino : " + eArquivoDestino);

            Formatador2 mFormatador = new Formatador2();
            mFormatador.identar(eArquivoOrigem, eArquivoDestino);

            System.out.println("\t - Fim : " + LexerC.getData());
            System.out.println("");

            System.out.println("################# ##### ##################");

        }

    }

    public boolean initSimples(String eArquivoOrigem, String eArquivoDestino) {
        boolean ret = false;

        Lexer LexerC = new Lexer();


        LexerC.init(eArquivoOrigem);
        if (LexerC.getErros().size() == 0) {

            Formatador2 mFormatador = new Formatador2();

            mFormatador.identar(eArquivoOrigem, eArquivoDestino);
            ret = true;
        }


        return ret;

    }


}
