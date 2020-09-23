package Make.Compiler;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Intellisenses {

    private Compiler mCompiler;

    public AST_Intellisenses(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado a SETA !");

        Token TokenTexto = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado um texto !");

        String eLocal = mCompiler.getLocal() + TokenTexto.getConteudo();
        if (!eLocal.endsWith("/")) {
            eLocal+="/";
        }

        AST AST_Corrente = new AST("INTELLISENSES");
        AST_Corrente.setValor(eLocal);
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");


    }


}