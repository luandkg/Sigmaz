package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Stages {

    private Compiler mCompiler;

    public AST_Stages(Compiler eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTAvo,boolean isDefined){


        AST ASTPai = ASTAvo.criarBranch("STAGES");

        if(isDefined){
            ASTPai.criarBranch("DEFINED").setNome("TRUE");
        }else{
            ASTPai.criarBranch("DEFINED").setNome("FALSE");
        }

       AST AST_Opcoes = ASTPai.criarBranch("OPTIONS");


        Token TokenN = mCompiler.getTokenAvanteStatus(TokenTipo.ID,"Era esperado o nome do STAGES !");


        ASTPai.setNome(TokenN.getConteudo());


        Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.SETA,"Era esperado uma SETA !");







        Token TokenI = mCompiler.getTokenAvante();
        if (TokenI.getTipo() == TokenTipo.CHAVE_ABRE) {


        }else{
            mCompiler.errarCompilacao("Era esperado abrir parenteses",   TokenI.getInicio());
        }


        boolean saiu = false;
        boolean mais = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.CHAVE_FECHA) {

                if(mais){
                    mCompiler.errarCompilacao("Era esperado outro parametro",   TokenD.getInicio());
                }

                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais=false;



                AST ASTCorrente =   AST_Opcoes.criarBranch("STAGE");
                ASTCorrente.setNome(TokenD.getConteudo());


                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;
                } else  if (P2.getTipo() == TokenTipo.CHAVE_FECHA) {
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2.getInicio());
                }


            }else{
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(),   TokenD.getInicio());
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar chaves" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }

        Token TokenV = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado um ponto e virgula !");


    }
}
