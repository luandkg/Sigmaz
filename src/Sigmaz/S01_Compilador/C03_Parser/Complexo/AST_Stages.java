package Sigmaz.S01_Compilador.C03_Parser.Complexo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Stages {

    private Parser mCompiler;

    public AST_Stages(Parser eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(String eNomePacote,AST ASTAvo) {


        AST ASTPai = ASTAvo.criarBranch("STRUCT");



        AST AST_With =  ASTPai.criarBranch("WITH");
        AST_With.setValor("FALSE");

        AST AST_Model =  ASTPai.criarBranch("MODEL");
        AST_Model.setValor("FALSE");


        AST mExtended = ASTPai.criarBranch("EXTENDED");
        mExtended.setNome("STAGES");

        AST mRefers = ASTPai.criarBranch("REFERS");


        AST AST_DEFINED = ASTPai.criarBranch("DEFINED");
        AST_DEFINED.setNome("TRUE");

        AST AST_Opcoes = ASTPai.criarBranch("STAGES");


        AST AST_Corpo = ASTPai.criarBranch("BODY");


        Token TokenN = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome do STAGES !");


        ASTPai.setNome(TokenN.getConteudo());

        if (eNomePacote.length()==0) {
            ASTPai.setValor( TokenN.getConteudo());
        } else{
            ASTPai.setValor(eNomePacote + "<>" + TokenN.getConteudo());
        }


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
