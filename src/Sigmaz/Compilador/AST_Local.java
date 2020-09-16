package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Local {

    private CompilerUnit mCompiler;

    public AST_Local(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai ) {

        Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");


        AST AST_Corrente = new AST("LOCAL");
        ASTPai.getASTS().add(AST_Corrente);


        AST AST_Arguments = AST_Corrente.criarBranch("ARGUMENTS");
        AST AST_BODY = AST_Corrente.criarBranch("BODY");

        AST_Argumentos mArgumentos = new AST_Argumentos(mCompiler);
        mArgumentos.init(AST_Arguments);

        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);


    }


}