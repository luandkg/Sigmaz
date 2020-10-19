package Make.Compiler;

import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Source {

    private Compiler mCompiler;

    public AST_Source(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado a SETA !");

        Token TokenTexto = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado um texto !");

        String eLocal = mCompiler.getLocal() + TokenTexto.getConteudo();
        if (!eLocal.endsWith("/")) {
            eLocal+="/";
        }

        AST AST_Corrente = new AST("SOURCE");
        AST_Corrente.setValor(eLocal);
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");


    }


}