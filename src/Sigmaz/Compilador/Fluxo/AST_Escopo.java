package Sigmaz.Compilador.Fluxo;

import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Utils.AST;

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