package Sigmaz.S06_Ferramentas;

import Sigmaz.S08_Utilitarios.Erro;
import Sigmaz.S08_Utilitarios.Formatador;
import Sigmaz.S01_Compilador.C02_Lexer.Lexer;

public class Identador {

    public void init(String eArquivoOrigem, String eArquivoDestino) {


        Lexer LexerC = new Lexer();

        String DI = LexerC.getData();

        LexerC.init(eArquivoOrigem);

        String DF = LexerC.getData();

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

            Formatador mFormatador = new Formatador();
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

            Formatador mFormatador = new Formatador();

            mFormatador.identar(eArquivoOrigem, eArquivoDestino);
            ret = true;
        }


        return ret;

    }


}
