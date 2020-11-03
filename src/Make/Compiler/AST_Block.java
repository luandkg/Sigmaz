package Make.Compiler;

import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Block {

    private Compiler mCompiler;

    public AST_Block(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        boolean aberto = true;
        boolean mais = true;

        while (aberto) {

            Token TokenProximo = mCompiler.getTokenAvante();

            if (TokenProximo.getTipo() == TokenTipo.TEXTO) {

                AST eItem = ASTPai.criarBranch("ITEM");
                eItem.setNome("TEXT");
                eItem.setValor(TokenProximo.getConteudo());

                mais = false;

                Token TokenProximo2 = mCompiler.getTokenAvante();
                if (TokenProximo2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else {
                    mCompiler.voltar();
                }
            } else if (TokenProximo.getTipo() == TokenTipo.INTEIRO) {

                AST eItem = ASTPai.criarBranch("ITEM");
                eItem.setNome("INT");
                eItem.setValor(TokenProximo.getConteudo());

                mais = false;

                Token TokenProximo2 = mCompiler.getTokenAvante();
                if (TokenProximo2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else {
                    mCompiler.voltar();
                }
            } else if (TokenProximo.getTipo() == TokenTipo.COLCHETE_FECHA) {
                aberto = false;
                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro item antes de fechar !", TokenProximo);
                }
                break;
            } else {

                mCompiler.errarCompilacao("Item deconhecido : " + TokenProximo.getConteudo(), TokenProximo);
            }


        }


    }


}