package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Condition {

    private Parser mCompiler;

    public AST_Condition(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST AST_Corrente = new AST("IF");
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Condicao = AST_Corrente.criarBranch("CONDITION");

        if (mCompiler.getIsDebug()) {
            mCompiler.debug(AST_Condicao);
        }

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.setBloco();
        mAST.init(AST_Condicao);


        AST_Condicao.setTipo("CONDITION");


        Token TokenSeta = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");


        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_Corrente.criarBranch("BODY"));

        boolean possui_others = false;


        while (true) {
            Token TokenF = mCompiler.getTokenFuturo();

            if (TokenF.mesmoConteudo("other")) {

                if (possui_others) {
                    mCompiler.errarCompilacao("Uma condicao nao pode ter um bloco OTHER apos OTHERS ", TokenF);
                }

                TokenF = mCompiler.getTokenAvante();
                Token TokenC2 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

                AST AST_Other = AST_Corrente.criarBranch("OTHER");


                AST AST_OtherCondition = AST_Other.criarBranch("CONDITION");

                if (mCompiler.getIsDebug()) {
                    mCompiler.debug(AST_OtherCondition);
                }

                AST AST_OtherBody = AST_Other.criarBranch("BODY");

                AST_Value mASTOther = new AST_Value(mCompiler);
                mASTOther.setBloco();
                mASTOther.init(AST_OtherCondition);


                AST_OtherCondition.setTipo("CONDITION");

                Token TokenSeta2 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA");

                AST_Corpo cASTOther = new AST_Corpo(mCompiler);
                cASTOther.init(AST_OtherBody);

            } else if (TokenF.mesmoConteudo("others")) {

                if (possui_others) {
                    mCompiler.errarCompilacao("Uma condicao so pode possuir um bloco OTHERS", TokenF);
                }

                TokenF = mCompiler.getTokenAvante();


                AST AST_Others = AST_Corrente.criarBranch("OTHERS");

                if (mCompiler.getIsDebug()) {
                    mCompiler.debug(AST_Others);
                }

                AST_Corpo cASTOther = new AST_Corpo(mCompiler);
                cASTOther.init(AST_Others);

                possui_others = true;

            } else {
                break;
            }

        }


    }


}