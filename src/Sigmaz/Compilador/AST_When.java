package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_When {

    private Compiler mCompiler;

    public AST_When(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("WHEN");
        ASTPai.getASTS().add(AST_Corrente);

        mCompiler.Proximo();

        AST AST_Arguments = AST_Corrente.criarBranch("CHOOSABLE");
        AST AST_Casos = AST_Corrente.criarBranch("CASES");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.initParam(AST_Arguments);

        Token TokenC6 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.CHAVE_ABRE, "Era esperado abrir chaves");

        if (TokenC.getTipo() != TokenTipo.CHAVE_ABRE) {
            return;
        }

        boolean saiu = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {
                saiu = true;
                break;
            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("case")) {

                AST_Value mVal = new AST_Value(mCompiler);

                AST AST_CASE = AST_Casos.criarBranch("CASE");
                AST AST_CONDITION = AST_CASE.criarBranch("CONDITION");
                mVal.initSeta(AST_CONDITION);

                AST_CONDITION.setTipo("CONDITION");

                mCompiler.voltar();

                Token TokenC7 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

                AST_Corpo cAST = new AST_Corpo(mCompiler);
                cAST.init(AST_CASE.criarBranch("BODY"));
            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("others")) {


                AST AST_CASE = AST_Corrente.criarBranch("OTHERS");



                AST_Corpo cAST = new AST_Corpo(mCompiler);
                cAST.init(AST_CASE);


            } else {



                mCompiler.errarCompilacao("When " + TokenD.getConteudo(), TokenD);


            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }


    }


}