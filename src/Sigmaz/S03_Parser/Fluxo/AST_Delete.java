package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S03_Parser.Parser;

public class AST_Delete {

    private Parser mCompiler;

    public AST_Delete(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        AST mDelete = ASTPai.criarBranch("DELETE");

        AST AST_Valor = mDelete.criarBranch("VALUE");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_Valor);

    }

}

