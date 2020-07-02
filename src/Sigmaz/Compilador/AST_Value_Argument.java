package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Value_Argument {

    private Compiler mCompiler;


    public AST_Value_Argument(Compiler eCompiler) {
        mCompiler = eCompiler;
    }

    public void initArgumento(AST ASTPai) {

        AST_Value_Operator Matcher = new AST_Value_Operator(mCompiler);

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.NUMERO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Num");


            ParteFinal(ASTPai);

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            ParteFinal(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            if (TokenD.mesmoConteudo("init")){

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
                        mCompiler.Proximo();

                    } else {
                        System.out.println("Problema IP : " + TokenC3.getConteudo());
                    }

                } else {
                    System.out.println("Problema IC : " + TokenC2.getConteudo());
                }

                return;
            } else if (TokenD.mesmoConteudo("start")) {


                ASTPai.setValor("START");

                Token TokenC2 = mCompiler.getTokenAvante();
                if (TokenC2.getTipo() == TokenTipo.ID) {

                    ASTPai.setNome(TokenC2.getConteudo());


                    AST_Start mAST = new AST_Start(mCompiler);
                    mAST.init(ASTPai);

                    ParteFinal(ASTPai);

                } else {
                    System.out.println("Problema IC : " + TokenC2.getConteudo());
                }
                return;
            }


            Token TokenC2 = mCompiler.getTokenAvante();

            if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA || TokenC2.getTipo() == TokenTipo.VIRGULA) {
                return;
            } else if (TokenC2.getTipo() == TokenTipo.QUAD) {

                ASTPai.setValor("STAGE");
                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.ID) {
                    ASTPai.criarBranch("STAGED").setNome(TokenC3.getConteudo());
                } else {
                    System.out.println("Problema ZZ : " + TokenC3.getConteudo());
                }


                ParteFinal(ASTPai);


            } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                ASTPai.setValor("FUNCT");

                ReceberArgumentos(ASTPai);

                Token TokenC3 = mCompiler.getTokenAvante();
                if (TokenC3.getTipo() == TokenTipo.PONTOVIRGULA) {

                } else if (TokenC3.getTipo() == TokenTipo.PARENTESES_FECHA) {
                } else if (TokenC3.getTipo() == TokenTipo.VIRGULA) {

                } else {
                    System.out.println("Problema x3 : " + TokenC3.getConteudo());
                }

            } else if (TokenC2.getTipo() == TokenTipo.PONTO) {

                // System.out.println("Vamos pontuar o escopo 1 !");


                ASTPai.setValor("STRUCT");

                ReceberNovoEscopo(ASTPai);

                ParteFinal(ASTPai);


            } else if (TokenC2.getTipo() == TokenTipo.SETA) {

                // System.out.println("Vamos pontuar o escopo 1 !");


                ASTPai.setValor("STRUCT_EXTERN");

                ReceberNovoEscopo(ASTPai);

                ParteFinal(ASTPai);



            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                Matcher.final_argumento("MATCH",ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                Matcher.final_argumento("UNMATCH",ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.SOMADOR) {
                Matcher.final_argumento("SUM",ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.DIMINUIDOR) {
                Matcher.final_argumento("SUB",ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.MULTIPLICADOR) {
                Matcher.final_argumento("MUX",ASTPai);
            } else if (TokenC2.getTipo() == TokenTipo.DIVISOR) {
                Matcher.final_argumento("DIV",ASTPai);
            } else {
                System.out.println("Problema : " + TokenD.getConteudo());
            }


        } else {

            System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }


    public void ParteFinal(AST ASTPai){

        AST_Value_Operator Matcher = new AST_Value_Operator(mCompiler);

        Token TokenC2 = mCompiler.getTokenAvante();
        if (TokenC2.getTipo() == TokenTipo.PARENTESES_FECHA || TokenC2.getTipo() == TokenTipo.VIRGULA) {
            return;
        } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
            Matcher.final_argumento("MATCH",ASTPai);
        } else if (TokenC2.getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
            Matcher.final_argumento("UNMATCH",ASTPai);
        } else if (TokenC2.getTipo() == TokenTipo.SOMADOR) {
            Matcher.final_argumento("SUM",ASTPai);
        } else if (TokenC2.getTipo() == TokenTipo.DIMINUIDOR) {
            Matcher.final_argumento("SUB",ASTPai);
        } else if (TokenC2.getTipo() == TokenTipo.MULTIPLICADOR) {
            Matcher.final_argumento("MUX",ASTPai);
        } else if (TokenC2.getTipo() == TokenTipo.DIVISOR) {
            Matcher.final_argumento("DIV",ASTPai);
        } else {
            System.out.println("Problema : " + TokenC2.getConteudo());
        }

    }




    public void ReceberArgumentos(AST ASTAvo) {

        AST ASTPai = ASTAvo.criarBranch("ARGUMENTS");


        boolean saiu = false;
        boolean mais = false;

        while (mCompiler.Continuar()) {
            Token TokenD = mCompiler.getTokenAvante();
            if (TokenD.getTipo() == TokenTipo.PARENTESES_FECHA) {

                if (mais) {
                    mCompiler.errarCompilacao("Era esperado outro parametro", TokenD);
                }

                saiu = true;
                break;
            } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");

                AST_Value_Argument mAST = new AST_Value_Argument(mCompiler);
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

                AST_Value_Argument mAST = new AST_Value_Argument(mCompiler);
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

                mCompiler.voltar();

                AST_Value_Argument mAST = new AST_Value_Argument(mCompiler);
                mAST.initArgumento(ASTCorrente);

                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();
                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }

            } else if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setValor("CONTAINER");

                AST ASTV= ASTCorrente.criarBranch("VALUE");


                AST_Value_Parenteses mAST = new AST_Value_Parenteses(mCompiler);
                mAST.initArgumento(ASTV);
                ASTV.setTipo("VALUE");


                Token P2 = mCompiler.getTokenAvante();

                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }



            } else {
                mCompiler.errarCompilacao("Era esperado um argumento : " + TokenD.getConteudo(), TokenD);
                break;
            }
        }

        if (!saiu) {
            mCompiler.errarCompilacao("Era esperado fechar parenteses" + mCompiler.getTokenAvante().getConteudo(), mCompiler.getTokenAvante());
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


}
