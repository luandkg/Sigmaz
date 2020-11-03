package Sigmaz.S01_Compilador.C03_Parser.Organizador;

import Sigmaz.S01_Compilador.C03_Parser.AST_TYPE;
import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C03_Parser.Fluxo.AST_Value;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Argumentos {

    private Parser mCompiler;

    public AST_Argumentos(Parser eCompiler) {
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
                        gAST.sePrecisarTipar(ASTCorrente.getBranch("TYPE"));
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

    public void initColchete(AST ASTPai) {

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.COLCHETE_ABRE, "Era esperado abrir parenteses");

        if (TokenC.getTipo() != TokenTipo.COLCHETE_ABRE) {
            return;
        }


        boolean saiu = false;

        boolean mais = false;


        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.COLCHETE_FECHA) {

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
                        gAST.sePrecisarTipar(ASTCorrente.getBranch("TYPE"));
                        gAST.setBuscadorDeArgumentos_Colchete();

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
                } else if (P2.getTipo() == TokenTipo.COLCHETE_FECHA) {
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
            mCompiler.errarCompilacao("Era esperado fechar colchetes" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
        }
    }

}
