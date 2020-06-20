package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_All {

    private Compiler mCompiler;

    public AST_All(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("ALL");
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
            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("inside")) {

                dentro("INSIDE",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("extrem")) {

                dentro("EXTREM",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("outside")) {

                dentro("OUTSIDE",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("start")) {

                dentro("START",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("end")) {

                dentro("END",AST_Casos);

            } else  if (TokenD.getTipo() == TokenTipo.ID && TokenD.mesmoConteudo("others")) {


                AST AST_CASE = AST_Corrente.criarBranch("OTHERS");


                AST_Corpo cAST = new AST_Corpo(mCompiler);
                cAST.init(AST_CASE);


            } else {



                mCompiler.errarCompilacao("Comando Desconhecido :  " + TokenD.getConteudo(), TokenD.getInicio());
                mCompiler.pularLinha();

            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }


    }

    public void dentro(String eTitulo,AST AST_Casos){

        AST_Value mVal = new AST_Value(mCompiler);

        AST AST_CASE = AST_Casos.criarBranch(eTitulo);

        Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado ( ");



        AST_Value mAST = new AST_Value(mCompiler);
        mAST.ReceberArgumentos(AST_CASE);

       // AST_CONDITION.setTipo("CONDITION");


        Token TokenC7 = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado -> ");

        AST_Corpo cAST = new AST_Corpo(mCompiler);
        cAST.init(AST_CASE.criarBranch("BODY"));


    }

}