package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S01_Compilador.C03_Parser.Parser;

public class AST_Debug {

    private Parser mCompiler;

    public AST_Debug(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {


        Token TokenA = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

        Token TokenB = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado { LOCAL | GLOBAL | REGRESSIVE | STRUCT }");

        if (TokenB.mesmoConteudo_SemCS("LOCAL")) {

        } else if (TokenB.mesmoConteudo_SemCS("GLOBAL")) {

        } else if (TokenB.mesmoConteudo_SemCS("REGRESSIVE")) {
        } else if (TokenB.mesmoConteudo_SemCS("STRUCT")) {

        } else {
            mCompiler.errarCompilacao("Era esperado { LOCAL | GLOBAL | REGRESSIVE  | STRUCT }", TokenB);
        }

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado :: ");

        Token TokenD = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o Comando de : " + TokenB.getConteudo().toUpperCase() );

        Token TokenE = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado Ponto e Virgula !");

        AST mAST = ASTPai.criarBranch("DEBUG");
        mAST.setNome(TokenB.getConteudo().toUpperCase());
        mAST.setValor(TokenD.getConteudo().toUpperCase());

    }
}
