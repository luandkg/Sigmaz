package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Loop {

    private CompilerUnit mCompiler;

    public AST_Loop(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {



        AST AST_Corrente = new AST("LOOP");
        ASTPai.getASTS().add(AST_Corrente);


        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");


        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch("BODY"));




    }


}