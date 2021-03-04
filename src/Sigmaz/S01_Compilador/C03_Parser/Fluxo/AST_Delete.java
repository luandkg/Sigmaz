package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.Orquestrantes;

public class AST_Delete {

    private Parser mCompiler;

    public AST_Delete(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai) {

        AST mDelete = ASTPai.criarBranch(Orquestrantes.DELETE);

        AST AST_Valor = mDelete.criarBranch(Orquestrantes.VALUE);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_Valor);

    }

}

