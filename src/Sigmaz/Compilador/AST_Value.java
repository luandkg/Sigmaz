package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Value {

    private Compiler mCompiler;
    private TokenTipo mTerminar;
    private String mTerminarErro;

    public AST_Value(Compiler eCompiler) {

        mCompiler = eCompiler;
        mTerminar = TokenTipo.PONTOVIRGULA;
        mTerminarErro = "Era esperado um ponto e virgula ! : ";

    }


    public void initParam(AST ASTPai) {

        mTerminar = TokenTipo.PARENTESES_FECHA;
        mTerminarErro = "Era esperado  fechar  parenteses ! : ";

        init(ASTPai);
    }

    public void initSeta(AST ASTPai) {

        mTerminar = TokenTipo.SETA;
        mTerminarErro = "Era esperado -> ";

        init(ASTPai);
    }


    public void operation_final(String eModo, AST ASTPai) {

        AST ASTEsquerda = ASTPai.copiar();
        ASTEsquerda.setTipo("LEFT");

        ASTPai.limpar();

        AST ASTDireita = new AST("RIGHT");


        ASTPai.setTipo("VALUE");
        ASTPai.criarBranch("MODE").setNome(eModo);
        ASTPai.setValor("OPERATOR");

        ASTPai.getASTS().add(ASTEsquerda);
        ASTPai.getASTS().add(ASTDireita);

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.mTerminar = mTerminar;
        mAST.mTerminarErro = mTerminarErro;


        mAST.initUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");


    }


    public void init(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

            ASTPai.setNome("");
            ASTPai.setValor("BLOCK");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminar = TokenTipo.PARENTESES_FECHA;
            mAST.mTerminarErro = "Era esperado )";

            mAST.init(ASTPai.criarBranch("VALUE"));

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {

                } else {
                    System.out.println("Problema C : " + TokenD.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                operation_final("MATCH", ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                operation_final("UNMATCH", ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.SOMADOR) {
                operation_final("SUM", ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.DIMINUIDOR) {
                operation_final("SUB", ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.MULTIPLICADOR) {
                operation_final("MUX", ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.DIVISOR) {
                operation_final("DIV", ASTPai);
            } else if (TokenC2.getTipo() == mTerminar) {

            } else {
                System.out.println("Problema D : " + TokenC2.getConteudo());
            }
        } else if (TokenD.getTipo() == TokenTipo.NEGADOR) {

            ASTPai.criarBranch("MODE").setNome("INVERSE");

            ASTPai.setNome("");
            ASTPai.setValor("UNARY");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(ASTPai.criarBranch("VALUE"));


        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.SETA) {

            ASTPai.setValor("TERNAL");


            Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");


            AST AST_Condicao = ASTPai.criarBranch("CONDITION");
            AST_Value mAST = new AST_Value(mCompiler);
            mAST.initParam(AST_Condicao);
            AST_Condicao.setTipo("CONDITION");


            Token TokenQ1 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado ::");
            Token TokenC_1 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

            AST AST_True = ASTPai.criarBranch("TRUE");
            AST_Value mAST_TRUE = new AST_Value(mCompiler);
            mAST_TRUE.initParam(AST_True);
            AST_True.setTipo("TRUE");

            Token TokenQ2 = mCompiler.getTokenAvanteIDStatus("not", "Era esperado not");
            Token TokenC_2 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

            AST AST_False = ASTPai.criarBranch("FALSE");
            AST_Value mAST_False = new AST_Value(mCompiler);
            mAST_False.initParam(AST_False);
            AST_False.setTipo("FALSE");

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.ID) {


            if (TokenD.mesmoConteudo("init")) {

                ASTPai.setValor("INIT");


                AST AST_Generico = ASTPai.criarBranch("GENERIC");
                AST_Generico.setNome("FALSE");

                Token TokenFuturo = mCompiler.getTokenFuturo();
                if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

                    AST_Generico.setNome("TRUE");

                    AST_Generic mg = new AST_Generic(mCompiler);
                    mg.init(AST_Generico);

                }

                Token TokenC2 = mCompiler.getTokenAvante();
                if (TokenC2.getTipo() == TokenTipo.ID) {

                    ASTPai.setNome(TokenC2.getConteudo());
                    Token TokenC3 = mCompiler.getTokenAvante();


                    if (TokenC3.getTipo() == TokenTipo.PARENTESES_ABRE) {

                        AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                        gAST.ReceberArgumentos(ASTPai);

                    } else {
                        System.out.println("Problema IP : " + TokenC3.getConteudo());
                    }

                } else {
                    System.out.println("Problema IC : " + TokenC2.getConteudo());
                }

                SegundaParte(ASTPai);


            } else if (TokenD.mesmoConteudo("start")) {


                ASTPai.setValor("START");

                Token TokenC2 = mCompiler.getTokenAvante();
                if (TokenC2.getTipo() == TokenTipo.ID) {

                    ASTPai.setNome(TokenC2.getConteudo());


                    AST_Start mAST = new AST_Start(mCompiler);
                    mAST.init(ASTPai);

                    SegundaParte(ASTPai);

                } else {
                    System.out.println("Problema IC : " + TokenC2.getConteudo());
                }


            } else {


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("ID");

                Token TokenC2 = mCompiler.getTokenAvante();


                if (TokenC2.getTipo() == mTerminar) {
                    return;
                } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                    operation_final("MATCH", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                    operation_final("UNMATCH", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.SOMADOR) {
                    operation_final("SUM", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.DIMINUIDOR) {
                    operation_final("SUB", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.MULTIPLICADOR) {
                    operation_final("MUX", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.DIVISOR) {
                    operation_final("DIV", ASTPai);

                } else if (TokenC2.getTipo() == TokenTipo.QUAD) {

                    ASTPai.setValor("STAGE");
                    Token TokenC3 = mCompiler.getTokenAvante();
                    if (TokenC3.getTipo() == TokenTipo.ID) {
                        ASTPai.criarBranch("STAGED").setNome(TokenC3.getConteudo());
                    } else {
                        System.out.println("Problema ZZ : " + TokenC3.getConteudo());
                    }


                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PONTO) {

                    // System.out.println("Vamos pontuar o escopo 1 !");


                    ASTPai.setValor("STRUCT");

                    ReceberNovoEscopo(ASTPai);

                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.SETA) {

                    // System.out.println("Vamos pontuar o escopo 1 !");


                    ASTPai.setValor("STRUCT_EXTERN");

                    ReceberNovoEscopo(ASTPai);

                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                    ASTPai.setValor("FUNCT");

                    AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                    gAST.ReceberArgumentos(ASTPai);

                    SegundaParte(ASTPai);


                } else {
                    System.out.println("Problema H : " + TokenD.getConteudo());
                }

            }


        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }


    public void SegundaParte(AST ASTPai) {


        Token TokenC3 = mCompiler.getTokenAvante();
        if (TokenC3.getTipo() == mTerminar) {
            return;
        } else if (TokenC3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
            operation_final("MATCH", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
            operation_final("UNMATCH", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.SOMADOR) {
            operation_final("SUM", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.DIMINUIDOR) {
            operation_final("SUB", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.MULTIPLICADOR) {
            operation_final("MUX", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.DIVISOR) {
            operation_final("DIV", ASTPai);
        } else {
            System.out.println("Problema G : " + TokenC3.getConteudo());
        }

    }


    public void ReceberNovoEscopo(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.ID) {

            AST mASTSub = ASTPai.criarBranch("INTERNAL");
            mASTSub.setNome(TokenD.getConteudo());

            mASTSub.setValor("STRUCT_OBJECT");

            Token TokenF = mCompiler.getTokenAvante();
            if (TokenF.getTipo() == TokenTipo.PARENTESES_ABRE) {

                mASTSub.setValor("STRUCT_FUNCT");


                AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                gAST.ReceberArgumentos(mASTSub);

                Token TokenF2 = mCompiler.getTokenAvante();


                if (TokenF2.getTipo() == TokenTipo.PONTO) {
                    //  System.out.println("Vamos pontuar o escopo 2 !");


                    mASTSub.setValor("STRUCT");

                    ReceberNovoEscopo(mASTSub);

                    if (mASTSub.existeBranch("ARGUMENTS")) {
                        if (mASTSub.mesmoValor("STRUCT")) {
                            mASTSub.setValor("STRUCT_FUNCT");
                        }
                    }


                } else {
                    mCompiler.voltar();
                }


            } else if (TokenF.getTipo() == TokenTipo.PONTO) {


                // System.out.println("Vamos pontuar o escopo 2 !");


                mASTSub.setValor("STRUCT_OBJECT");

                ReceberNovoEscopo(mASTSub);

            } else {
                mCompiler.voltar();
            }


        } else {
            System.out.println("Era esperado um ID : " + TokenD.getConteudo());
        }

    }


    public void initUltimo(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

            ASTPai.setNome("");
            ASTPai.setValor("BLOCK");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminar = TokenTipo.PARENTESES_FECHA;
            mAST.mTerminarErro = "Era esperado )";

            mAST.initParam(ASTPai.criarBranch("VALUE"));

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {

                } else {
                    System.out.println("Problema C : " + TokenD.getConteudo());
                }


            } else if (TokenC2.getTipo() == mTerminar) {

            } else {
                System.out.println("Problema D : " + TokenC2.getConteudo());
            }
        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == mTerminar) {
                return;

            } else {
                System.out.println("Problema 1 : " + TokenC2.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == mTerminar) {
                return;
            } else {
                System.out.println("Problema 2 : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {


            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == mTerminar) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                gAST.ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {
                    return;
                } else {
                    System.out.println("Problema 6 : " + TokenC3.getConteudo());
                }
            } else if (TokenC2.getTipo() == TokenTipo.QUAD) {


                ASTPai.setValor("STAGE");
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.ID) {
                    ASTPai.criarBranch("STAGED").setNome(TokenC3.getConteudo());
                } else {
                    System.out.println("Problema ZZ : " + TokenC3.getConteudo());
                }

                Token TokenC4 = mCompiler.getTokenAvante();
                if (TokenC4.getTipo() == mTerminar) {
                    return;
                } else {
                    System.out.println("Problema EE :  " + TokenC4.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.PONTO) {


                ASTPai.setValor("STRUCT");

                ReceberNovoEscopo(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {
                    return;

                } else {
                    System.out.println("Problema A : " + TokenC3.getConteudo());
                }


            } else {
                System.out.println("Problema 4 : " + TokenC2.getConteudo());
            }

        } else {

            System.out.println("Valorando 5 : " + TokenD.getConteudo());


        }


    }

    public void initUltimoArgumento(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

            ASTPai.setNome("");
            ASTPai.setValor("BLOCK");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminar = TokenTipo.PARENTESES_FECHA;
            mAST.mTerminarErro = "Era esperado )";

            mAST.init(ASTPai.criarBranch("VALUE"));

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.VIRGULA || TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

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

                AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                gAST.ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.VIRGULA || TokenC3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    return;
                } else {
                    System.out.println("Problema x2 : " + TokenC3.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.QUAD) {

                ASTPai.setValor("STAGE");
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.ID) {
                    ASTPai.criarBranch("STAGED").setNome(TokenC3.getConteudo());
                } else {
                    System.out.println("Problema ZZ : " + TokenC3.getConteudo());
                }

                Token TokenC4 = mCompiler.getTokenAvante();
                if (TokenC4.getTipo() == mTerminar) {
                    return;
                } else {
                    System.out.println("Problema EE :  " + TokenC4.getConteudo());
                }

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }

    public void initUltimoArgumentoParenteses(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

            ASTPai.setNome("");
            ASTPai.setValor("BLOCK");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminar = TokenTipo.PARENTESES_FECHA;
            mAST.mTerminarErro = "Era esperado )";

            mAST.init(ASTPai.criarBranch("VALUE"));

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                gAST.ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    return;
                } else {
                    System.out.println("Problema x2 : " + TokenC3.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.QUAD) {

                ASTPai.setValor("STAGE");
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.ID) {
                    ASTPai.criarBranch("STAGED").setNome(TokenC3.getConteudo());
                } else {
                    System.out.println("Problema ZZ : " + TokenC3.getConteudo());
                }

                Token TokenC4 = mCompiler.getTokenAvante();
                if (TokenC4.getTipo() == mTerminar) {
                    return;
                } else {
                    System.out.println("Problema EE :  " + TokenC4.getConteudo());
                }

            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }

        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }

}
