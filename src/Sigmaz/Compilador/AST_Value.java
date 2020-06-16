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
        mAST.mTerminar=mTerminar;
        mAST.mTerminarErro=mTerminarErro;
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
        mAST.mTerminar=mTerminar;
        mAST.mTerminarErro=mTerminarErro;
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


        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

            ASTPai.setNome("");
            ASTPai.setValor("BLOCK");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminar=TokenTipo.PARENTESES_FECHA;
            mAST.mTerminarErro="Era esperado )";

            mAST.init(ASTPai.criarBranch("VALUE"));

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {

                }else{
                    System.out.println("Problema C : " + TokenD.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else  if (TokenC2.getTipo() == mTerminar) {

            }else{
                System.out.println("Problema D : " + TokenC2.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == mTerminar) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else {
                System.out.println("Problema E :  " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == mTerminar) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else {
                System.out.println("Problema F : " + TokenD.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() == mTerminar) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                match_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                unmatch_final(ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {
                    return;
                } else if (TokenC3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                    match_final(ASTPai);
                } else if (TokenC3.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                    unmatch_final(ASTPai);
                } else {
                    System.out.println("Problema G : " + TokenC3.getConteudo());
                }


            } else {
                System.out.println("Problema H : " + TokenD.getConteudo());
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
            if (TokenC2.getTipo() == mTerminar) {
                return;

            } else {
                System.out.println("Problema 1 : " + TokenC2.getConteudo());
            }

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            Token TokenC2 = mCompiler.getTokenAvante();
            if (TokenC2.getTipo() ==mTerminar) {
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

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == mTerminar) {
                    return;
                } else {
                    System.out.println("Problema 6 : " + TokenC3.getConteudo());
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
                unmatch_final_argumento(ASTPai);
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
                unmatch_final_argumento(ASTPai);
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
                unmatch_final_argumento(ASTPai);
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







}
