package Make.Compiler;

import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S08_Utilitarios.AST;

public class AST_Status {

    private Compiler mCompiler;

    public AST_Status(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenSeta =mCompiler.getTokenAvanteStatus(TokenTipo.SETA,"Era esperado a SETA !");

        Token TokenTexto =mCompiler.getTokenAvanteStatus(TokenTipo.TEXTO,"Era esperado um texto !");

        AST AST_Corrente = new AST("STATUS");
        AST_Corrente.setValor(TokenTexto.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenVirgula =mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado um ponto e virgula !");


    }




}