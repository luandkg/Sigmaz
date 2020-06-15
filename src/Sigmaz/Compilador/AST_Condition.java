package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Condition {

    private Compiler mCompiler;

    public AST_Condition(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");


        AST AST_Corrente = new AST("IF");
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Condicao=  AST_Corrente.criarBranch("CONDITION");

        AST_Value mAST = new AST_Value(mCompiler);

        mAST.initParam(AST_Condicao);
        AST_Condicao.setTipo("CONDITION");

        AST_Corpo cAST = new AST_Corpo(mCompiler);

        cAST.init(AST_Corrente.criarBranch("BODY"));

        Token TokenF = mCompiler.getTokenFuturo();

        System.out.println("Futuro : " + TokenF.getConteudo());

    }


}