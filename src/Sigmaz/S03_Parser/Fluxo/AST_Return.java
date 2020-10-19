package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S03_Parser.Parser;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Return {

    private Parser mCompiler;

    public AST_Return(Parser eCompiler) {
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
