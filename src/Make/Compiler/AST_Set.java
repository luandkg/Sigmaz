package Make.Compiler;

import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Set {

    private Compiler mCompiler;

    public AST_Set(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenID= mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome do SET !");

        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado a SETA !");

        Token TokenTexto = mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO, "Era esperado um texto !");


        AST AST_Corrente = new AST("SET");
        AST_Corrente.setNome(TokenID.getConteudo());
        AST_Corrente.setValor(TokenTexto.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");


    }


}