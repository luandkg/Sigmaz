package Make.Compiler;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Generate {

    private Compiler mCompiler;

    public AST_Generate(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenID = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o tipo de comando generate !");


        AST AST_Corrente = new AST("GENERATE");
        AST AST_MODE = AST_Corrente.criarBranch("MODE");
        AST_MODE.setValor("NONE");

        if (TokenID.mesmoConteudo("intellisense")) {

            AST_Corrente.setNome("INTELLISENSE");

        } else if (TokenID.mesmoConteudo("uml")) {

            AST_Corrente.setNome("UML");

        } else if (TokenID.mesmoConteudo("dependency")) {

            AST_Corrente.setNome("DEPENDENCY");


        } else {
            mCompiler.errarCompilacao("Tipo de generate desconhecido : " + TokenID.getConteudo(), TokenID);
        }


        Token TokenTexto = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado um texto !");

        AST_Corrente.setValor(TokenTexto.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        Token TokenSegundo = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o segundo tipo de comando generate !");

        if (TokenSegundo.mesmoConteudo("with")) {


            Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");

            if (mCompiler.getTokenFuturo().getTipo() == TokenTipo.TEXTO) {

                AST_MODE.setValor("UNIQUE");

                AST AST_WITH = AST_Corrente.criarBranch("UNIQUE");

                Token TokenTexto2 = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado um texto !");

                AST_WITH.setNome(TokenTexto2.getConteudo());


            } else if (mCompiler.getTokenFuturo().getTipo() == TokenTipo.CHAVE_ABRE) {

                AST_MODE.setValor("LIST");

                AST_List ePeca = new AST_List(mCompiler);
                ePeca.init(AST_Corrente);


            } else {

                mCompiler.errarCompilacao("Era esperado o valor do WITH!", TokenSeta);

            }


        } else {
            mCompiler.errarCompilacao("Tipo de generate desconhecido : " + TokenID.getConteudo(), TokenID);
        }


        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");


    }


}