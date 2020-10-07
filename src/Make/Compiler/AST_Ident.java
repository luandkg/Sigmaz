package Make.Compiler;

import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Ident {

    private Compiler mCompiler;

    public AST_Ident(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {



        AST AST_Corrente = new AST("IDENT");
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_MODE = AST_Corrente.criarBranch("MODE");
        AST_MODE.setValor("NONE");


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
            mCompiler.errarCompilacao("Tipo de make desconhecido : " + TokenSegundo.getConteudo(), TokenSegundo);
        }


        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");




    }




}