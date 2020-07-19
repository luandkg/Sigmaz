package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Return {

    private CompilerUnit mCompiler;

    public AST_Return(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai){

        AST ASTReturn = ASTPai.criarBranch("RETURN");
        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(ASTReturn);

        ASTReturn.setTipo("RETURN");

    }



}
