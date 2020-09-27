package Make.Compiler;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Options {

    private Compiler mCompiler;

    public AST_Options(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenNome = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome do tipo de opcoes !");


        AST AST_Corrente = ASTPai.criarBranch("OPTIONS");

        AST eModo = AST_Corrente.criarBranch("MODE");
        eModo.setValor("NONE");

        if (TokenNome.mesmoConteudo("intellisense")) {
            AST_Corrente.setNome("INTELLISENSE");
        } else   if (TokenNome.mesmoConteudo("build")) {
            AST_Corrente.setNome("BUILD");
        } else   if (TokenNome.mesmoConteudo("highlight")) {
            AST_Corrente.setNome("HIGH_LIGHT");
        } else {
            mCompiler.errarCompilacao("Tipo de opcao desconhecido : " + TokenNome.getConteudo(), TokenNome);
        }



        Token efuturo = mCompiler.getTokenFuturo();

        if (efuturo.getTipo() == TokenTipo.ID) {

            Token eProximo = mCompiler.getTokenAvante();
            if (eProximo.mesmoConteudo("with")) {

                Token TokenSegundo= mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o segundo comando de opcao !");

                eModo.setValor("WITH");


                if(TokenSegundo.mesmoConteudo("default")){

                    eModo.setNome(TokenSegundo.getConteudo());

                }else{
                    mCompiler.errarCompilacao("Valor do segundo comando de options desconhecido : " + TokenSegundo.getConteudo(), TokenSegundo);
                }



            } else {
                mCompiler.errarCompilacao("Segundo comando de options desconhecido : " + eProximo.getConteudo(), eProximo);
            }

        } else {

            eModo.setValor("BLOCK");

            AST_Corpo eC = new AST_Corpo(mCompiler);
            eC.init("BLOCK",AST_Corrente);

        }

        Token TokenVirgula = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA, "Era esperado um ponto e virgula !");

    }


}