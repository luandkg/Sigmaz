package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Return {

    private Parser mCompiler;

    public AST_Return(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai){

        AST ASTReturn = ASTPai.criarBranch(Orquestrantes.RETURN);
        AST AST_VALUE = ASTReturn.criarBranch(Orquestrantes.VALUE);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_VALUE);


    }



}
