package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Transferencia {

    private CompilerUnit mCompiler;

    public AST_Transferencia(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTAvo){


        AST ASTPai = ASTAvo.criarBranch("ARGUMENTS");


        Token TokenI = mCompiler.getTokenAvante();
        if (TokenI.getTipo() == TokenTipo.PARENTESES_ABRE) {


        }else{
            mCompiler.errarCompilacao("Era esperado abrir parenteses",   TokenI);
        }


        boolean saiu = false;
        boolean mais = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if(mais){
                    mCompiler.errarCompilacao("Era esperado outro parametro",   TokenD);
                }

                saiu = true;
                break;

            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais=false;



                AST ASTCorrente =   ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("string");


                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;
                } else  if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2);
                }


            }else{
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(),   TokenD);
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }

    }
}
