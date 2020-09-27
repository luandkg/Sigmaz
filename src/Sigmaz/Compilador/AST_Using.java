package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Using {

    private CompilerUnit mCompiler;

    public AST_Using(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvante();
        if (TokenC.getTipo() == TokenTipo.ID) {
        } else {
            mCompiler.errarCompilacao("Era esperado uma variavel STRUCT para uma USING !", TokenC);
        }


        AST AST_Corrente = new AST("USING");
        AST_Corrente.setNome(TokenC.getConteudo());
        ASTPai.getASTS().add(AST_Corrente);
        AST AST_Corpo = AST_Corrente.criarBranch("TYPED");

        Token TokenC1 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");


        AST_UsingCorpo mAST_UsingCorpo = new AST_UsingCorpo(mCompiler);
        mAST_UsingCorpo.init(AST_Corpo);




    }
}
