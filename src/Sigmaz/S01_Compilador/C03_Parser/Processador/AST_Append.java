package Sigmaz.S01_Compilador.C03_Parser.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_Append {

    private Parser mCompiler;

    public AST_Append(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        AST AST_Corrente = new AST("APPEND");
        ASTPai.getASTS().add(AST_Corrente);

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor("ID");
            AST_Corrente.setNome(TokenC.getConteudo().toUpperCase());

        } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

            AST_Corrente.setValor("Text");
            AST_Corrente.setNome(TokenC.getConteudo());


        } else {
            mCompiler.errarCompilacao("Era esperado o argumento de APPEND = { R13 ou R14 } !", TokenC);
            return;
        }


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");


    }
}
