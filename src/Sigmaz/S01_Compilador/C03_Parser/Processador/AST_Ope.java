package Sigmaz.S01_Compilador.C03_Parser.Processador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_Ope {

    private Parser mCompiler;

    public AST_Ope(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("OPE");
        ASTPai.getASTS().add(AST_Corrente);

        AST mDireita = AST_Corrente.criarBranch("RIGHT");
        AST mEsquerda = AST_Corrente.criarBranch("LEFT");


        lado(mDireita);

        Token TokenOperacao = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado um operador !");
        AST_Corrente.setNome(TokenOperacao.getConteudo().toUpperCase());

        lado(mEsquerda);

        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");



    }

    public void lado(AST AST_Corrente){

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

            AST_Corrente.setValor("STRING");
            AST_Corrente.setNome(TokenC.getConteudo());

        } else {
            mCompiler.errarCompilacao("Era esperado o argumento para OPE !", TokenC);
            return;
        }



    }
}
