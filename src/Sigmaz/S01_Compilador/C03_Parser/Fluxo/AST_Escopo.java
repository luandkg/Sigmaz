package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Escopo {

    private Parser mCompiler;

    public AST_Escopo(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("SCOPE");
        ASTPai.getASTS().add(AST_Corrente);


        AST_Corpo cASTOther = new AST_Corpo(mCompiler);
        cASTOther.dentro(AST_Corrente);


    }


}