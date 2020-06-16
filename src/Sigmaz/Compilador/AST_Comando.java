package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Comando {

    private Compiler mCompiler;

    public AST_Comando(Compiler eCompiler) {
        mCompiler = eCompiler;
    }


    public void init(AST ASTPai){


        AST ASTCorrente = ASTPai.criarBranch("EXECUTE");


        Token TokenD = mCompiler.getTokenCorrente();
            if (TokenD.getTipo() == TokenTipo.NUMERO) {

                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("Num");

                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.PONTOVIRGULA) {

                }else{
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),   P2.getInicio());
                }
            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {


                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("Text");

                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.PONTOVIRGULA) {

                }else{
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),   P2.getInicio());
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {




                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();

                if(P2.getTipo()==TokenTipo.IGUAL) {

                    AST ASTDireita = ASTCorrente.copiar();
                    ASTDireita.setTipo("SETTABLE");

                    ASTCorrente.getASTS().clear();
                    ASTCorrente.setNome("");
                    ASTCorrente.setValor("");

                    ASTCorrente.setTipo("APPLY");
                    ASTCorrente.getASTS().add(ASTDireita);

                    AST ASTC = ASTCorrente.criarBranch("VALUE");

                    AST_Value mAST = new AST_Value(mCompiler);

                    mAST.init(ASTC);


                } else if(P2.getTipo()==TokenTipo.PARENTESES_ABRE){

                    ASTCorrente.setValor("FUNCT");

                    recebendoParametros(ASTCorrente);

                    Token P3 = mCompiler.getTokenAvante();

                    if(P3.getTipo()==TokenTipo.PONTOVIRGULA) {

                    } else     if(P3.getTipo()==TokenTipo.IGUAL) {

                        AST ASTDireita = ASTCorrente.copiar();
                        ASTDireita.setTipo("SETTABLE");

                        ASTCorrente.getASTS().clear();
                        ASTCorrente.setNome("");
                        ASTCorrente.setValor("");

                        ASTCorrente.setTipo("APPLY");
                        ASTCorrente.getASTS().add(ASTDireita);

                        AST ASTC = ASTCorrente.criarBranch("VALUE");

                        AST_Value mAST = new AST_Value(mCompiler);

                        mAST.init(ASTC);

                    }else{
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P3.getConteudo(),   P3.getInicio());
                    }

                }else{
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),   P2.getInicio());
                }

            }else{
                mCompiler.errarCompilacao("Era esperado um valor : " + TokenD.getConteudo(),   TokenD.getInicio());

            }


    }


    public void recebendoParametros(AST ASTPai) {



        boolean saiu = false;
        boolean mais = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if(mais){
                    mCompiler.errarCompilacao("Era esperado outro parametro",   TokenD.getInicio());
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
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2.getInicio());
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
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2.getInicio());
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

                    recebendoParametros(ASTCorrente);

                    Token P3 = mCompiler.getTokenAvante();

                    if(P3.getTipo()==TokenTipo.VIRGULA) {
                        mais=true;
                    } else  if (P3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                        saiu = true;
                        break;
                    }else{
                        mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(),   P2.getInicio());
                    }

                } else if(P2.getTipo()==TokenTipo.PARENTESES_FECHA){
                    saiu = true;
                    break;
                }else{
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(),   P2.getInicio());
                }


            }else{
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(),   TokenD.getInicio());
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }
    }

}
