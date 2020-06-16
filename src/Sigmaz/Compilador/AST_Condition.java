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


        while (true) {
            Token TokenF = mCompiler.getTokenFuturo();

            if (TokenF.mesmoConteudo("other")) {

                TokenF = mCompiler.getTokenAvante();
                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

                AST AST_Other = AST_Corrente.criarBranch("OTHER");

                AST AST_OtherCondition = AST_Other.criarBranch("CONDITION");
                AST AST_OtherBody = AST_Other.criarBranch("BODY");

                AST_Value mASTOther = new AST_Value(mCompiler);
                mASTOther.initParam(AST_OtherCondition);
                AST_OtherCondition.setTipo("CONDITION");


                if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.PARENTESES_FECHA) {

                } else {
                    mCompiler.errarCompilacao("Era esperado fechar paresenteses", mCompiler.getTokenCorrente().getInicio());
                }

                Token TokenSeta2 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

                AST_Corpo cASTOther = new AST_Corpo(mCompiler);
                cASTOther.init(AST_OtherBody);

            } else    if (TokenF.mesmoConteudo("others")) {

                TokenF = mCompiler.getTokenAvante();


                AST AST_Other = AST_Corrente.criarBranch("OTHERS");
                AST_Corpo cASTOther = new AST_Corpo(mCompiler);
                cASTOther.init(AST_Other);


            } else {
                break;
            }

        }


    }


}