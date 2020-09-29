package Sigmaz.Compilador.Organizador;

import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Require {

    private CompilerUnit mCompiler;

    public AST_Require(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }



    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST mReq = ASTPai.criarBranch("REQUIRED");
            mReq.setNome(TokenC.getConteudo());

            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");


        } else {
            mCompiler.errarCompilacao("Era esperado o caminho de uma requisicao !", TokenC);
        }


    }

}
