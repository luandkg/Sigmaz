package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Value {

    private Compiler mCompiler;

    public AST_Value(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    private boolean mFechado2 = false;
    private TokenTipo mTipo1;
    private TokenTipo mTipo2;

    public void FecharCom2(TokenTipo eTipo1, TokenTipo eTipo2) {
        mFechado2 = true;
        mTipo1 = eTipo1;
        mTipo2 = eTipo2;
    }

    public void init(AST ASTPai) {

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.NUMERO) {

                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("Num");

                Token P2 = mCompiler.getTokenAvante();

                if (mFechado2) {
                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    }
                } else {
                    if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {


                        AST ASTEsquerda = ASTPai.copiar();
                        ASTEsquerda.setTipo("LEFT");

                        ASTPai.limpar();

                        AST ASTDireita = new AST("RIGHT");


                        ASTPai.setTipo("VALUE");
                        ASTPai.criarBranch("MODE").setNome("MATCH");
                        ASTPai.setValor("COMPARATOR");

                        ASTPai.getASTS().add(ASTEsquerda);
                        ASTPai.getASTS().add(ASTDireita);

                        AST_Value mAST = new AST_Value(mCompiler);
                        mAST.initSegundo(ASTDireita);
                        ASTDireita.setTipo("RIGHT");


                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {


                        AST ASTEsquerda = ASTPai.copiar();
                        ASTEsquerda.setTipo("LEFT");

                        ASTPai.limpar();

                        AST ASTDireita = new AST("RIGHT");

                        ASTPai.setTipo("VALUE");
                        ASTPai.criarBranch("MODE").setNome("MISMATCH");
                        ASTPai.setValor("COMPARATOR");

                        ASTPai.getASTS().add(ASTEsquerda);
                        ASTPai.getASTS().add(ASTDireita);

                        AST_Value mAST = new AST_Value(mCompiler);
                        mAST.initSegundo(ASTDireita);
                        ASTDireita.setTipo("RIGHT");


                        break;
                    } else {
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                    }
                }

            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("Text");

                Token P2 = mCompiler.getTokenAvante();

                if (mFechado2) {
                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    }
                } else {
                    if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {


                        AST ASTEsquerda = ASTPai.copiar();
                        ASTEsquerda.setTipo("LEFT");

                        ASTPai.limpar();

                        AST ASTDireita = new AST("RIGHT");


                        ASTPai.setTipo("VALUE");
                        ASTPai.criarBranch("MODE").setNome("MATCH");
                        ASTPai.setValor("COMPARATOR");

                        ASTPai.getASTS().add(ASTEsquerda);
                        ASTPai.getASTS().add(ASTDireita);

                        AST_Value mAST = new AST_Value(mCompiler);
                        mAST.initSegundo(ASTDireita);
                        ASTDireita.setTipo("RIGHT");


                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {


                        AST ASTEsquerda = ASTPai.copiar();
                        ASTEsquerda.setTipo("LEFT");

                        ASTPai.limpar();

                        AST ASTDireita = new AST("RIGHT");

                        ASTPai.setTipo("VALUE");
                        ASTPai.criarBranch("MODE").setNome("MISMATCH");
                        ASTPai.setValor("COMPARATOR");

                        ASTPai.getASTS().add(ASTEsquerda);
                        ASTPai.getASTS().add(ASTDireita);

                        AST_Value mAST = new AST_Value(mCompiler);
                        mAST.initSegundo(ASTDireita);
                        ASTDireita.setTipo("RIGHT");


                        break;
                    } else {
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                    }
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();


                if (mFechado2) {
                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    }
                } else {

                    if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                        ASTPai.setValor("FUNCT");


                        // AST_Recebimentos mAST = new AST_Recebimentos(mCompiler);
                        // mAST.init(ASTPai);

                        recebendoParametros(ASTPai);

                        Token P3 = mCompiler.getTokenAvante();

                        if (P3.getTipo() == TokenTipo.PONTOVIRGULA) {
                            break;
                        } else if (P3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {


                            AST ASTEsquerda = ASTPai.copiar();
                            ASTEsquerda.setTipo("LEFT");

                            ASTPai.limpar();

                            AST ASTDireita = new AST("RIGHT");


                            ASTPai.setTipo("VALUE");
                            ASTPai.criarBranch("MODE").setNome("MATCH");
                            ASTPai.setValor("COMPARATOR");

                            ASTPai.getASTS().add(ASTEsquerda);
                            ASTPai.getASTS().add(ASTDireita);

                            AST_Value mAST = new AST_Value(mCompiler);
                            mAST.initSegundo(ASTDireita);
                            ASTDireita.setTipo("RIGHT");


                            break;
                        } else if (P3.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {


                            AST ASTEsquerda = ASTPai.copiar();
                            ASTEsquerda.setTipo("LEFT");

                            ASTPai.limpar();

                            AST ASTDireita = new AST("RIGHT");

                            ASTPai.setTipo("VALUE");
                            ASTPai.criarBranch("MODE").setNome("MISMATCH");
                            ASTPai.setValor("COMPARATOR");

                            ASTPai.getASTS().add(ASTEsquerda);
                            ASTPai.getASTS().add(ASTDireita);

                            AST_Value mAST = new AST_Value(mCompiler);
                            mAST.initSegundo(ASTDireita);
                            ASTDireita.setTipo("RIGHT");


                            break;
                        } else {
                            mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P3.getConteudo(), P3.getInicio());
                        }

                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {


                        AST ASTEsquerda = ASTPai.copiar();
                        ASTEsquerda.setTipo("LEFT");

                        ASTPai.limpar();

                        AST ASTDireita = new AST("RIGHT");


                        ASTPai.setTipo("VALUE");
                        ASTPai.criarBranch("MODE").setNome("MATCH");
                        ASTPai.setValor("COMPARATOR");

                        ASTPai.getASTS().add(ASTEsquerda);
                        ASTPai.getASTS().add(ASTDireita);

                        AST_Value mAST = new AST_Value(mCompiler);
                        mAST.initSegundo(ASTDireita);
                        ASTDireita.setTipo("RIGHT");


                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {


                        AST ASTEsquerda = ASTPai.copiar();
                        ASTEsquerda.setTipo("LEFT");

                        ASTPai.limpar();

                        AST ASTDireita = new AST("RIGHT");

                        ASTPai.setTipo("VALUE");
                        ASTPai.criarBranch("MODE").setNome("MISMATCH");
                        ASTPai.setValor("COMPARATOR");

                        ASTPai.getASTS().add(ASTEsquerda);
                        ASTPai.getASTS().add(ASTDireita);

                        AST_Value mAST = new AST_Value(mCompiler);
                        mAST.initSegundo(ASTDireita);
                        ASTDireita.setTipo("RIGHT");


                        break;
                    } else {
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                    }


                }





            } else {
                mCompiler.errarCompilacao("Era esperado um valor : " + TokenD.getConteudo(), TokenD.getInicio());
                break;
            }
        }

    }

    public void initSegundo(AST ASTPai) {

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.NUMERO) {

                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("Num");

                Token P2 = mCompiler.getTokenAvante();

                if (mFechado2) {
                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    }
                } else {
                    if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {
                        break;
                    } else {
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                    }
                }

            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("Text");

                Token P2 = mCompiler.getTokenAvante();

                if (mFechado2) {
                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    }
                } else {
                    if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {
                        break;
                    } else {
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                    }
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();




                    if (P2.getTipo() == TokenTipo.PONTOVIRGULA) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                        ASTPai.setValor("FUNCT");


                        // AST_Recebimentos mAST = new AST_Recebimentos(mCompiler);
                        // mAST.init(ASTPai);

                        recebendoParametros(ASTPai);

                        Token P3 = mCompiler.getTokenAvante();

                        if (P3.getTipo() == TokenTipo.PONTOVIRGULA) {
                            break;
                        } else {
                            mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P3.getConteudo(), P3.getInicio());
                        }


                    } else {
                        mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                    }


                }

        }

    }


    public void recebendoParametros(AST ASTPai) {


        boolean saiu = false;
        boolean mais = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro parametro", TokenD.getInicio());
                }

                saiu = true;
                break;
            } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("Num");


                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {


                    AST ASTEsquerda = ASTCorrente.copiar();
                    ASTEsquerda.setTipo("LEFT");

                    ASTCorrente.limpar();

                    AST ASTDireita = new AST("RIGHT");


                    ASTCorrente.setTipo("ARGUMENT");
                    ASTCorrente.criarBranch("MODE").setNome("MATCH");
                    ASTCorrente.setValor("COMPARATOR");

                    ASTCorrente.getASTS().add(ASTEsquerda);
                    ASTCorrente.getASTS().add(ASTDireita);

                    AST_Value mAST = new AST_Value(mCompiler);
                    mAST.FecharCom2(TokenTipo.VIRGULA, TokenTipo.PARENTESES_FECHA);
                    mAST.init(ASTDireita);
                    ASTDireita.setTipo("RIGHT");

                   // System.out.println("Tentando fechar corretamente");

                    P2 =mCompiler.getTokenCorrente();

                    if (P2.getTipo() == TokenTipo.VIRGULA) {
                        mais = true;
                    } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                        saiu = true;
                        break;
                    }


                } else {
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(), P2.getInicio());
                }
            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("Text");

                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                } else {
                    mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(), P2.getInicio());
                }
            } else if (TokenD.getTipo() == TokenTipo.ID) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setNome(TokenD.getConteudo());
                ASTCorrente.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                    ASTCorrente.setValor("FUNCT");

                    recebendoParametros(ASTCorrente);

                    Token P3 = mCompiler.getTokenAvante();

                    if (P3.getTipo() == TokenTipo.VIRGULA) {
                        mais = true;
                    } else if (P3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                        saiu = true;
                        break;
                    } else {
                        mCompiler.errarCompilacao("Era esperado um argumento : " + P2.getConteudo(), P2.getInicio());
                    }

                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                } else {
                    mCompiler.errarCompilacao("Era esperado um ponto e virgula ! : " + P2.getConteudo(), P2.getInicio());
                }


            } else {
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(), TokenD.getInicio());
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante().getInicio());
        }
    }

}
