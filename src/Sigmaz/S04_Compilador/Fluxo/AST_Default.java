package Sigmaz.S04_Compilador.Fluxo;

import Sigmaz.S04_Compilador.AST_TYPE;
import Sigmaz.S04_Compilador.CompilerUnit;
import Sigmaz.S03_Parser.Token;
import Sigmaz.S03_Parser.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Default {

    private CompilerUnit mCompiler;

    public AST_Default(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        AST AST_Corrente = ASTPai.criarBranch("DEFAULT");

        Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.initDireto(AST_Corrente);

        AST AST_BODY = AST_Corrente.criarBranch("BODY");
        AST_Corpo mCorpo = new AST_Corpo(mCompiler);
        mCorpo.init(AST_BODY);




    }
}
