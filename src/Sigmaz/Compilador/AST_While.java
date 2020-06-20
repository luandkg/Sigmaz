package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_While {

    private Compiler mCompiler;

    public AST_While(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");


        AST AST_Corrente = new AST("WHILE");
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Condicao = AST_Corrente.criarBranch("CONDITION");
        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initParam(AST_Condicao);
        AST_Condicao.setTipo("CONDITION");


        if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.PARENTESES_FECHA) {

        }else{
            mCompiler.errarCompilacao("Era esperado fechar paresenteses",mCompiler.getTokenCorrente().getInicio());
        }


        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");


        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch("BODY"));




    }


}