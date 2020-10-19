package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Try {

    private Parser mCompiler;

    public AST_Try(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("TRY");
        ASTPai.getASTS().add(AST_Corrente);

        AST LOGIC = AST_Corrente.criarBranch("LOGIC");
        AST MESSAGE = AST_Corrente.criarBranch("MESSAGE");

        LOGIC.setNome("FALSE");
        MESSAGE.setNome("FALSE");

        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ID) {

            LOGIC.setNome("TRUE");
            Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado a variavel logica de tentativa");

            LOGIC.setValor(TokenC2.getConteudo());

            Token TokenFuturo2 = mCompiler.getTokenFuturo();
            if (TokenFuturo2.getTipo() == TokenTipo.ID) {

                MESSAGE.setNome("TRUE");
                Token TokenC3 = mCompiler.getTokenAvanteIDStatus("with", "Era esperado with");
                Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado a variavel de destino da mensagem de erro");

                MESSAGE.setValor(TokenC4.getConteudo());
            } else if (TokenFuturo.getTipo() == TokenTipo.SETA) {
                mCompiler.Proximo();
            }


            Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado ->");






        } else if (TokenFuturo.getTipo() == TokenTipo.SETA) {
            mCompiler.Proximo();
        } else {
            mCompiler.errarCompilacao("Era esperado uma variavel logica ou ->", TokenFuturo);
        }


        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch("BODY"));


    }

}
