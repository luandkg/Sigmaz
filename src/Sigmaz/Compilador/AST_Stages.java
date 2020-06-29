package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Stages {

    private Compiler mCompiler;

    public AST_Stages(Compiler eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTAvo) {


        AST ASTPai = ASTAvo.criarBranch("STAGES");


        AST AST_DEFINED = ASTPai.criarBranch("DEFINED");
        AST_DEFINED.setNome("TRUE");


        AST AST_Opcoes = ASTPai.criarBranch("OPTIONS");


        Token TokenN = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome do STAGES !");


        ASTPai.setNome(TokenN.getConteudo());


        Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.SETA, "Era esperado uma SETA !");


        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ID && TokenFuturo.mesmoConteudo("not")) {

            mCompiler.Proximo();
            AST_DEFINED.setNome("FALSE");
        } else if (TokenFuturo.getTipo() == TokenTipo.CHAVE_ABRE) {

        } else {
            mCompiler.errarCompilacao("Era esperado not ou {", TokenFuturo);
        }


        Token TokenI = mCompiler.getTokenAvante();
        if (TokenI.getTipo() == TokenTipo.CHAVE_ABRE) {


        } else {
            mCompiler.errarCompilacao("Era esperado abrir parenteses", TokenI);
        }


        boolean saiu = false;
        boolean mais = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {

                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro parametro", TokenD);
                }

                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais = false;


                AST ASTCorrente = AST_Opcoes.criarBranch("STAGE");
                ASTCorrente.setNome(TokenD.getConteudo());


                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.CHAVE_FECHA) {
                    saiu = true;
                    break;
                } else {
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(), P2);
                }


            } else {
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(), TokenD);
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }


    }
}
