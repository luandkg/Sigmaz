package Make.Compiler;

import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_List {

    private Compiler mCompiler;

    public AST_List(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corrente = ASTPai.criarBranch("LIST");

        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir CHAVES !");

        boolean aberto = true;
        boolean mais = true;

        while (aberto) {

            Token TokenProximo = mCompiler.getTokenAvante();

            if (TokenProximo.getTipo() == TokenTipo.TEXTO) {

                AST_Corrente.criarBranch("ITEM").setValor(TokenProximo.getConteudo());

                Token TokenProximo2 = mCompiler.getTokenAvante();
                if (TokenProximo2.getTipo() == TokenTipo.CHAVE_FECHA) {
                    aberto = false;
                    mais = false;
                    break;
                } else if (TokenProximo2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                }
            } else if (TokenProximo.getTipo() == TokenTipo.CHAVE_FECHA) {
                aberto = false;
                mais = false;
                break;
            } else {

                mCompiler.errarCompilacao("Item deconhecido : " + TokenProximo.getConteudo(), TokenProximo);
            }


        }



    }


}