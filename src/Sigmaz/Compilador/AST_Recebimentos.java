package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Recebimentos {

    private CompilerUnit mCompiler;

    public AST_Recebimentos(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai){

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
            } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

                mais=false;


                AST ASTCorrente =   ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("Num");


                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;
                } else  if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2);
                }
            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

                mais=false;


                AST ASTCorrente =   ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("Text");

                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;
                } else  if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2);
                }
            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais=false;





                AST ASTCorrente =   ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;
                } else if(P2.getTipo()==TokenTipo.PARENTESES_ABRE){

                    ASTCorrente.setValor("FUNCT");

                    init(ASTCorrente);

                    Token P3 = mCompiler.getTokenAvante();

                    if(P3.getTipo()==TokenTipo.VIRGULA) {
                        mais=true;
                    } else  if (P3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                        saiu = true;
                        break;
                    }else{
                        mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2);
                    }

                } else if(P2.getTipo()==TokenTipo.PARENTESES_FECHA){
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),   P2);
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
