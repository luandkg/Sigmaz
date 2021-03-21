package Sigmaz.S01_Compilador.C03_Parser.Processador;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_Size {

    private Parser mCompiler;

    public AST_Size(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        AST AST_Corrente = new AST("SIZE");
        ASTPai.getASTS().add(AST_Corrente);

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor("ID");
            AST_Corrente.setNome(TokenC.getConteudo().toUpperCase());

        } else {
            mCompiler.errarCompilacao("Era esperado o argumento de SIZE = { R13 ou R14 } !", TokenC);
            return;
        }


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");



    }
}
