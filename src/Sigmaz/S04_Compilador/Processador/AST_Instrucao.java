package Sigmaz.S04_Compilador.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S04_Compilador.CompilerUnit;

public class AST_Instrucao {

    private CompilerUnit mCompiler;

    public AST_Instrucao(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        Token TokenA = mCompiler.getTokenCorrente();

        Token TokenC = mCompiler.getTokenAvante();

        AST AST_Corrente = new AST(TokenA.getConteudo().toUpperCase());
        ASTPai.getASTS().add(AST_Corrente);

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_Corrente.setValor("ID");
            AST_Corrente.setNome(TokenC.getConteudo().toUpperCase());

        } else if (TokenC.getTipo() == TokenTipo.NUMERO) {

            AST_Corrente.setValor("Num");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else if (TokenC.getTipo() == TokenTipo.NUMERO_FLOAT) {

            AST_Corrente.setValor("Float");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

            AST_Corrente.setValor("Text");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else {
            mCompiler.errarCompilacao("Era esperado o argumento de " + TokenA.getConteudo().toUpperCase(), TokenC);
            return;
        }


        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");



    }
}
