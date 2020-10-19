package Sigmaz.S03_Parser.Organizador;

import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Require {

    private Parser mCompiler;

    public AST_Require(Parser eCompiler) {
        mCompiler = eCompiler;
    }



    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST mReq = ASTPai.criarBranch("REQUIRED");
            mReq.setNome(TokenC.getConteudo());

            if (mCompiler.getRequisicoes().contains(TokenC.getConteudo())){

                mCompiler.errarCompilacao("Biblioteca externada ja foi chamada : " + TokenC.getConteudo(), TokenC);

            }else{
                mCompiler.getRequisicoes().add(TokenC.getConteudo());
            }

            Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");


        } else {
            mCompiler.errarCompilacao("Era esperado o caminho de uma requisicao !", TokenC);
        }


    }

}
