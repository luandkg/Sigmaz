package Make.Compiler;

import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Make {

    private Compiler mCompiler;

    public AST_Make(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenID = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o tipo de comando make !");


        AST AST_Corrente = new AST("MAKE");
        AST AST_MODE = AST_Corrente.criarBranch("MODE");
        AST_MODE.setValor("NONE");

        if (TokenID.mesmoConteudo("executable")) {

            AST_Corrente.setNome("EXECUTABLE");

        } else if (TokenID.mesmoConteudo("library")) {

            AST_Corrente.setNome("LIBRARY");


        } else {
            mCompiler.errarCompilacao("Tipo de make desconhecido : " + TokenID.getConteudo(), TokenID);
        }


        Token TokenTexto = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado um texto !");

        AST_Corrente.setValor(TokenTexto.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);

        Token TokenSegundo = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o segundo tipo de comando make !");

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
            mCompiler.errarCompilacao("Tipo de make desconhecido : " + TokenID.getConteudo(), TokenID);
        }


        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");


    }


}