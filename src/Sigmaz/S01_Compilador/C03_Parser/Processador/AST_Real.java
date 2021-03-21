package Sigmaz.S01_Compilador.C03_Parser.Processador;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_Real {

    private Parser mCompiler;

    public AST_Real(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("REAL");
        ASTPai.getASTS().add(AST_Corrente);


        valor(AST_Corrente);

        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");



    }

    public void valor(AST AST_Corrente){

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor("ID");
            AST_Corrente.setNome(TokenC.getConteudo().toUpperCase());

        } else if (TokenC.getTipo() == TokenTipo.INTEIRO) {

            AST_Corrente.setValor("INT");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else if (TokenC.getTipo() == TokenTipo.NUMERO_FLOAT) {

            AST_Corrente.setValor("Float");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

            AST_Corrente.setValor("Text");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else {
            mCompiler.errarCompilacao("Era esperado o argumento para OPE !", TokenC);
            return;
        }



    }
}
