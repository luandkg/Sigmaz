package Sigmaz.S04_Compilador.Fluxo;

import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Escopo {

    private CompilerUnit mCompiler;

    public AST_Escopo(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("SCOPE");
        ASTPai.getASTS().add(AST_Corrente);


        AST_Corpo cASTOther = new AST_Corpo(mCompiler);
        cASTOther.dentro(AST_Corrente);


    }


}