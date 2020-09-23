package Make.Compiler;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Corpo {

    private Compiler mCompiler;

    public AST_Corpo(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(String eNome,AST ASTPai) {

        AST AST_Corrente = ASTPai.criarBranch(eNome);

        Token TokenAbrir = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves !");

        boolean saiu = false;

        while (saiu == false) {

            Token TokenProximo = mCompiler.getTokenAvante();

            if (TokenProximo.getTipo() == TokenTipo.ID) {

                AST eItem = AST_Corrente.criarBranch("ITEM");
                eItem.setNome(TokenProximo.getConteudo());

                Token TokenIgual = mCompiler.getTokenAvanteStatus(TokenTipo.IGUAL, "Era esperado o sinal de atribuicao !");

                AST_Atribuicao eA = new AST_Atribuicao(mCompiler);
                eA.init(eItem);


            } else if (TokenProximo.getTipo() == TokenTipo.CHAVE_FECHA) {
                break;
            } else {

                mCompiler.errarCompilacao("Item deconhecido : " + TokenProximo.getConteudo(), TokenProximo);
            }


        }


    }


}