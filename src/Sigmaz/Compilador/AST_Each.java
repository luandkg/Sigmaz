package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Each {

    private CompilerUnit mCompiler;

    public AST_Each(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("EACH");
        ASTPai.getASTS().add(AST_Corrente);


        AST AST_D = AST_Corrente.criarBranch("DEF");


        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST_D.setNome(TokenC.getConteudo());

        } else {
            mCompiler.errarCompilacao("Era esperado o nome da variavel !", TokenC);
            return;
        }

        AST_TYPE mType = new AST_TYPE(mCompiler);
        mType.init(AST_Corrente);

        Token TokenC6 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado :: ");


        AST AST_L = AST_Corrente.criarBranch("LIST");


        Token TokenC7 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initParam(AST_L);
        AST_L.setTipo("LIST");

        Token TokenC8 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");


        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch("BODY"));


    }


}