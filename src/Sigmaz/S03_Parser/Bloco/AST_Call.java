package Sigmaz.S03_Parser.Bloco;

import Sigmaz.S03_Parser.Fluxo.AST_Corpo;
import Sigmaz.S03_Parser.Fluxo.AST_ValueTypes;
import Sigmaz.S03_Parser.Organizador.AST_Argumentos;
import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Call {

    private Parser mCompiler;

    public AST_Call(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("CALL");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_SENDING = AST_Corrente.criarBranch("SENDING");
            AST AST_Corpo = AST_Corrente.criarBranch("BODY");


            Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");


            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.CHAVE_ABRE) {

                AST_Corrente.setValor("AUTO");

                Sigmaz.S03_Parser.Fluxo.AST_Corpo mAST = new AST_Corpo(mCompiler);
                mAST.init(AST_Corpo);

                AST_SENDING.criarBranch("ARGUMENTS");

            } else {

                AST_Corrente.setValor("REFER");

                Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de uma ACTION ou FUNCTION !");
                AST_SENDING.setNome(TokenP2.getConteudo());


                AST_ValueTypes mAVA = new AST_ValueTypes(mCompiler);
                mAVA.ReceberArgumentos_AbrirParenteses(AST_SENDING, false, null);


                Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado PONTO E VIRGULA !");
            }


        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma ACTION !", TokenC);
        }


    }


}