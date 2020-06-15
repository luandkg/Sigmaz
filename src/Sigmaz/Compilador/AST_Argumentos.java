package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Argumentos {

    private Compiler mCompiler;

    public AST_Argumentos(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC= mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE,"Era esperado abrir parenteses" );

        if (TokenC.getTipo() != TokenTipo.PARENTESES_ABRE) {
            return;
        }



        boolean saiu = false;

        boolean mais = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if(mais){
                    mCompiler.errarCompilacao("Era esperado outro argumento",   TokenD.getInicio());
                }

                saiu = true;
                break;
            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais=false;

                AST ASTCorrente =   ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());



                Token TokenC2= mCompiler.getTokenAvanteStatus(TokenTipo.DOISPONTOS,"Era esperado Dois Pontos" );
                Token TokenC3= mCompiler.getTokenAvanteStatus(TokenTipo.ID,"Era esperado uma Tipagem" );


                ASTCorrente.setValor(TokenC3.getConteudo());

                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.VIRGULA) {
                    mais=true;
                } else  if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2.getInicio());
                }
            }else{
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }
    }


}
