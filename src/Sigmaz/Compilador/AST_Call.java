package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Call {

    private Compiler mCompiler;

    public AST_Call(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvante();

        if (TokenC.getTipo() == TokenTipo.ID) {

            AST AST_Corrente = new AST("CALL");
            AST_Corrente.setNome(TokenC.getConteudo());
            ASTPai.getASTS().add(AST_Corrente);

            AST AST_SENDING = AST_Corrente.criarBranch("SENDING");
            AST AST_Argumentos = AST_SENDING.criarBranch("ARGUMENTS");

            AST AST_Corpo = AST_Corrente.criarBranch("BODY");


            Token TokenP = mCompiler.getTokenAvanteStatus(TokenTipo.SETA,"Era esperado uma SETA !");


            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.CHAVE_ABRE ) {

                AST_Corrente.setValor("AUTO");

                AST_Corpo mAST = new AST_Corpo(mCompiler);
                mAST.init(AST_Corpo);


            }else{

                AST_Corrente.setValor("REFER");

                Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.ID,"Era esperado o nome de uma ACTION ou FUNCTION !");
                AST_SENDING.setNome(TokenP2.getConteudo());



                recebendoParametros(AST_Argumentos);

                Token TokenP3 = mCompiler.getTokenAvanteStatus(TokenTipo.PONTOVIRGULA,"Era esperado PONTO E VIRGULA !");
            }





        } else {
            mCompiler.errarCompilacao("Era esperado o nome para uma ACTION !", TokenC);
        }


    }

    public void recebendoParametros(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE,"Era esperado abrir parenteses ");


        if (TokenC.getTipo() != TokenTipo.PARENTESES_ABRE) {
             return;
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