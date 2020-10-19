package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Exception {

    private Parser mCompiler;

    public AST_Exception(Parser eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {


        AST AST_Corrente = new AST("EXCEPTION");
        ASTPai.getASTS().add(AST_Corrente);

        AST AST_Valor = AST_Corrente.criarBranch("VALUE");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.init(AST_Valor);




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