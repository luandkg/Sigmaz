package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Generic {

    private CompilerUnit mCompiler;

    public AST_Generic(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai){

        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ENVIAR, "Era esperado >> ");

        init_receber(ASTPai);

    }


    public void init_receber(AST ASTPai){

        boolean saiu = false;
        boolean mais = false;

        Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado (");

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if(mais){

                    mCompiler.errarCompilacao("Era esperado outro tipo generico",   TokenD);
                }

                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais=false;

                AST ASTCorrente =   ASTPai.criarBranch("TYPE");
                ASTCorrente.setNome(TokenD.getConteudo());


                ASTCorrente.setValor("CONCRETE");


                Token TokenFuturo = mCompiler.getTokenFuturo();
                if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

                    ASTCorrente.setValor("GENERIC");

                    AST_Generic mg = new AST_Generic(mCompiler);
                    mg.init(ASTCorrente);


                }



                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;

                } else if(P2.getTipo()==TokenTipo.PARENTESES_FECHA){

                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um ) ou ,  : " + P2.getConteudo(),   P2);
                }


            }else{
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(),   TokenD);
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado ) " + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }

    }
}
