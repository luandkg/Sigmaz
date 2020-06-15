package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Value {

    private Compiler mCompiler;
    private boolean mFechado2 = false;

    private TokenTipo mTipo1;
    private TokenTipo mTipo2;

    private TokenTipo mTerminar;
    private String mTerminarErro;


    public AST_Value(Compiler eCompiler) {

        mCompiler = eCompiler;
        mTerminar = TokenTipo.PONTOVIRGULA;
        mTerminarErro = "Era esperado um ponto e virgula ! : ";
    }


    public void FecharCom2(TokenTipo eTipo1, TokenTipo eTipo2) {
        mFechado2 = true;
        mTipo1 = eTipo1;
        mTipo2 = eTipo2;
    }

    public void initParam(AST ASTPai) {

        mTerminar = TokenTipo.PARENTESES_FECHA;
        mTerminarErro = "Era esperado  fechar  parenteses ! : ";

        init(ASTPai);
    }

    public void match(AST ASTPai) {

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
        mAST.mTerminar = this.mTerminar;
        mAST.mTerminarErro = this.mTerminarErro;
        mAST.initSegundo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }

    public void mismatch(AST ASTPai) {

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
        mAST.mTerminar = this.mTerminar;
        mAST.mTerminarErro = this.mTerminarErro;
        mAST.initSegundo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }

    public void match_final(AST ASTPai){

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
        mAST.initUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }

    public void unmatch_final(AST ASTPai){

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
        mAST.initUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }


    public void match_final_argumento(AST ASTPai){

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
        mAST.initUltimoArgumento(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }

    public void unmatch_final_argumento(AST ASTPai){

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
        mAST.initUltimoArgumento(ASTDireita);

        ASTDireita.setTipo("RIGHT");

    }


    public void init(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PONTOVIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PONTOVIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PONTOVIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.PONTOVIRGULA) {
                    return;
                } else if (TokenC3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                    match_final(ASTPai);
                } else if (TokenC3.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                    unmatch_final(ASTPai);
                } else {
                    System.out.println("Problema x : " + TokenC3.getConteudo());
                }


            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }

    public void initUltimo(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PONTOVIRGULA) {
                return;

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PONTOVIRGULA) {
                return;
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PONTOVIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.PONTOVIRGULA) {
                    return;
                } else {
                    System.out.println("Problema x : " + TokenC3.getConteudo());
                }


            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }

    public void initUltimoArgumento(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.VIRGULA || TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.VIRGULA || TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.VIRGULA || TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.VIRGULA || TokenC3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    return;
                } else {
                    System.out.println("Problema x : " + TokenC3.getConteudo());
                }


            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }


    public void initArgumento(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA || TokenC2.getTipo() == TokenTipo.VIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final_argumento(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                match_final_argumento(ASTPai);
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA || TokenC2.getTipo() == TokenTipo.VIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final_argumento(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                match_final_argumento(ASTPai);
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA || TokenC2.getTipo() == TokenTipo.VIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.PONTOVIRGULA) {

                } else {
                    System.out.println("Problema x : " + TokenC3.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final_argumento(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                match_final_argumento(ASTPai);
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }

    public void ReceberArgumentos(AST ASTPai) {

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

                AST_Value mAST = new AST_Value(mCompiler);
                mCompiler.voltar();
                mAST.initArgumento(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();


                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }


            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");

                AST_Value mAST = new AST_Value(mCompiler);
                mCompiler.voltar();

                mAST.initArgumento(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();
                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");

                AST_Value mAST = new AST_Value(mCompiler);
                mCompiler.voltar();

                mAST.initArgumento(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();
                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
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


    public void initAntigo(AST ASTPai) {

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.NUMERO) {

                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("Num");

                Token P2 = mCompiler.getTokenAvante();

                if (mFechado2) {

                    System.out.println("Argumentando : " + TokenD.getConteudo());


                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                        match(ASTPai);
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                        mismatch(ASTPai);
                        break;
                    }
                } else {
                    if (P2.getTipo() == mTerminar) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                        match(ASTPai);
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                        mismatch(ASTPai);
                        break;
                    } else {
                        mCompiler.errarCompilacao(mTerminarErro + P2.getConteudo(), P2.getInicio());
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
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                        match(ASTPai);
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                        mismatch(ASTPai);
                        break;
                    }
                } else {
                    if (P2.getTipo() == mTerminar) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {

                        match(ASTPai);


                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {


                        mismatch(ASTPai);


                        break;
                    } else {
                        mCompiler.errarCompilacao(mTerminarErro + P2.getConteudo(), P2.getInicio());
                    }
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();

                System.out.println("DEBUGANDO -> " + TokenD.getConteudo() + " em " + P2.getConteudo());


                if (mFechado2) {
                    if (P2.getTipo() == mTipo1) {
                        break;
                    } else if (P2.getTipo() == mTipo2) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                        match(ASTPai);
                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                        mismatch(ASTPai);
                        break;
                    } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {


                        ASTPai.setValor("FUNCT");

                        recebendoParametros(ASTPai);

                        Token P3 = mCompiler.getTokenAvante();

                        if (P3.getTipo() == mTerminar) {
                            break;
                        } else if (P3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {

                            match(ASTPai);

                            break;
                        } else if (P3.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {

                            mismatch(ASTPai);


                            break;
                        } else {
                            mCompiler.errarCompilacao(mTerminarErro + P3.getConteudo(), P3.getInicio());
                        }
                    }
                } else {

                    if (P2.getTipo() == mTerminar) {
                        break;
                    } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                        ASTPai.setValor("FUNCT");

                        System.out.println("TORNANDO FUNC -> " + TokenD.getConteudo() + " em " + P2.getConteudo());

                        recebendoParametros(ASTPai);

                        Token P3 = mCompiler.getTokenAvante();

                        if (P3.getTipo() == mTerminar) {
                            break;
                        } else if (P3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {

                            match(ASTPai);

                            break;
                        } else if (P3.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {

                            mismatch(ASTPai);


                            break;
                        } else {
                            mCompiler.errarCompilacao(mTerminarErro + P3.getConteudo(), P3.getInicio());
                        }

                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {


                        match(ASTPai);


                        break;
                    } else if (P2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {


                        mismatch(ASTPai);


                        break;
                    } else {
                        mCompiler.errarCompilacao(mTerminarErro + P2.getConteudo(), P2.getInicio());
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
                    if (P2.getTipo() == mTerminar) {
                        break;
                    } else {
                        mCompiler.errarCompilacao(mTerminarErro + P2.getConteudo(), P2.getInicio());
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
                    if (P2.getTipo() == mTerminar) {
                        break;
                    } else {
                        mCompiler.errarCompilacao(mTerminarErro + P2.getConteudo(), P2.getInicio());
                    }
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("ID");

                Token P2 = mCompiler.getTokenAvante();


                if (P2.getTipo() == mTerminar) {
                    break;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                    ASTPai.setValor("FUNCT");

                    recebendoParametros(ASTPai);

                    Token P3 = mCompiler.getTokenAvante();

                    if (P3.getTipo() == mTerminar) {
                        break;
                    } else {
                        mCompiler.errarCompilacao(mTerminarErro + P3.getConteudo(), P3.getInicio());
                    }


                } else {
                    mCompiler.errarCompilacao(mTerminarErro + P2.getConteudo(), P2.getInicio());
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

                AST_Value mAST = new AST_Value(mCompiler);
                mAST.FecharCom2(TokenTipo.VIRGULA, TokenTipo.PARENTESES_FECHA);
                mCompiler.voltar();
                mAST.init(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();

                //  System.out.println("Argumentando : " + P2.getConteudo());


                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }


            } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");

                AST_Value mAST = new AST_Value(mCompiler);
                mAST.FecharCom2(TokenTipo.VIRGULA, TokenTipo.PARENTESES_FECHA);
                mCompiler.voltar();

                mAST.init(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();
                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }

            } else if (TokenD.getTipo() == TokenTipo.ID) {


                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");

                AST_Value mAST = new AST_Value(mCompiler);
                mAST.FecharCom2(TokenTipo.VIRGULA, TokenTipo.PARENTESES_FECHA);
                mCompiler.voltar();

                mAST.init(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();
                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
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
