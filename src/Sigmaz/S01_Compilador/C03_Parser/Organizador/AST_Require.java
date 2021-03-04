package Sigmaz.S01_Compilador.C03_Parser.Organizador;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Require {

    private Parser mCompiler;

    public AST_Require(Parser eCompiler) {
        mCompiler = eCompiler;
    }



    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {


            AST mReq = ASTPai.criarBranch(Orquestrantes.REQUIRED);
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
