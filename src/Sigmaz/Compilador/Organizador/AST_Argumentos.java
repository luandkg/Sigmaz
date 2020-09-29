package Sigmaz.Compilador.Organizador;

import Sigmaz.Compilador.AST_TYPE;
import Sigmaz.Compilador.CompilerUnit;
import Sigmaz.Compilador.Fluxo.AST_Value;
import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Argumentos {

    private CompilerUnit mCompiler;

    public AST_Argumentos(CompilerUnit eCompiler) {
        mCompiler = eCompiler;
    }

    public void init(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado abrir parenteses");

        if (TokenC.getTipo() != TokenTipo.PARENTESES_ABRE) {
            return;
        }


        boolean saiu = false;

        boolean mais = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro argumento", TokenD);
                }

                saiu = true;
                break;
            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais = false;

                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");


                boolean mOpt = false;

                if (TokenD.mesmoConteudo("ref")) {
                    ASTCorrente.setValor("REF");

                    Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de um parametro");
                    ASTCorrente.setNome(TokenC4.getConteudo());

                } else if (TokenD.mesmoConteudo("opt")) {
                    mOpt = true;

                    ASTCorrente.setValor("OPT");

                    Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de um parametro");

                    if (TokenC4.mesmoConteudo("ref")) {

                        ASTCorrente.setValor("OPTREF");

                        Token TokenC5 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de um parametro");
                        ASTCorrente.setNome(TokenC5.getConteudo());

                    } else {

                        ASTCorrente.setNome(TokenC4.getConteudo());

                    }


                } else {
                    ASTCorrente.setValor("VALUE");
                    ASTCorrente.setNome(TokenD.getConteudo());
                }


                AST_TYPE mType = new AST_TYPE(mCompiler);
                mType.init(ASTCorrente);

                Token P2 = null;

                if (mOpt) {

                    Token TokenP3 = mCompiler.getTokenAvante();
                    if (TokenP3.getTipo() == TokenTipo.IGUAL) {

                        AST ASTValue = ASTCorrente.criarBranch("VALUE");

                        AST_Value gAST = new AST_Value(mCompiler);
                        gAST.setBuscadorDeArgumentos();

                        gAST.init(ASTValue);

                        ASTValue.setTipo("VALUE");

                    } else {
                        mCompiler.errarCompilacao("Era esperado o valor opcional do argumento " + ASTCorrente.getNome() + " !", TokenC);
                    }


                    P2 = mCompiler.getTokenCorrente();

                } else {

                    P2 = mCompiler.getTokenAvante();
                }


                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                } else {
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(), P2);
                }
            } else {
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }

    public void init_Tipagem(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado abrir parenteses");

        if (TokenC.getTipo() != TokenTipo.PARENTESES_ABRE) {
            return;
        }


        boolean saiu = false;

        boolean mais = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro tipo", TokenD);
                }

                saiu = true;
                break;
            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais = false;

                mCompiler.voltar();

                AST_TYPE mType = new AST_TYPE(mCompiler);
                mType.init_Definicao(ASTPai);

                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                } else {
                    mCompiler.errarCompilacao("Era esperado um tipo : " + P2.getConteudo(), P2);
                }
            } else {
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }

}
