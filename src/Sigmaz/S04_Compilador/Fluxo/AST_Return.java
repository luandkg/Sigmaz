package Sigmaz.S04_Compilador.Fluxo;

import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Return {

    private CompilerUnit mCompiler;

    public AST_Return(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai){

        AST ASTReturn = ASTPai.criarBranch("RETURN");
        AST AST_VALUE = ASTReturn.criarBranch("VALUE");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_VALUE);

     //   ASTReturn.setTipo("RETURN");

    }



}
