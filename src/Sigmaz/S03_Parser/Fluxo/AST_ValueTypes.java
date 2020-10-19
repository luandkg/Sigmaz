package Sigmaz.S03_Parser.Fluxo;

import Sigmaz.S03_Parser.Organizador.AST_Generic;
import Sigmaz.S03_Parser.Parser;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_ValueTypes {

    private Parser mCompiler;

    public AST_ValueTypes(Parser eCompiler) {

        mCompiler = eCompiler;

    }

    public void dentro_num(Token TokenD, AST ASTPai) {

        String eConteudo = TokenD.getConteudo();


        Token eProximo = mCompiler.getTokenFuturo();

        if (eProximo.getTipo() == TokenTipo.ID) {

            mCompiler.proximo();

            String eMarcador = eProximo.getConteudo();

           // System.out.println("Possivel marcador : " + eMarcador);

            ASTPai.setValor("MARKER");

            AST eMark = ASTPai.criarBranch("MARK");
            eMark.setNome(eMarcador);
            eMark.setValor("ID");

            AST eVal = ASTPai.criarBranch("VALUE");
            eVal.setNome(eConteudo);
            eVal.setValor("Num");

        } else {

            ASTPai.setNome(eConteudo);
            ASTPai.setValor("Num");


        }

    }

    public void dentro_float(Token TokenD, AST ASTPai) {


        String eConteudo = TokenD.getConteudo();


        Token eProximo = mCompiler.getTokenFuturo();

        if (eProximo.getTipo() == TokenTipo.ID) {

            mCompiler.proximo();

            String eMarcador = eProximo.getConteudo();

            // System.out.println("Possivel marcador : " + eMarcador);

            ASTPai.setValor("MARKER");

            AST eMark = ASTPai.criarBranch("MARK");
            eMark.setNome(eMarcador);
            eMark.setValor("ID");

            AST eVal = ASTPai.criarBranch("VALUE");
            eVal.setNome(eConteudo);
            eVal.setValor("Float");

        } else {

            ASTPai.setNome(eConteudo);
            ASTPai.setValor("Float");


        }

    }

    public void dentro_texto(Token TokenD, AST ASTPai) {


        String eConteudo = TokenD.getConteudo();


        Token eProximo = mCompiler.getTokenFuturo();

        if (eProximo.getTipo() == TokenTipo.ID) {

            mCompiler.proximo();

            String eMarcador = eProximo.getConteudo();

            // System.out.println("Possivel marcador : " + eMarcador);

            ASTPai.setValor("MARKER");

            AST eMark = ASTPai.criarBranch("MARK");
            eMark.setNome(eMarcador);
            eMark.setValor("ID");

            AST eVal = ASTPai.criarBranch("VALUE");
            eVal.setNome(eConteudo);
            eVal.setValor("Text");

        } else {

            ASTPai.setNome(eConteudo);
            ASTPai.setValor("Text");


        }

    }


    public void dentro_id(Token TokenD, AST ASTPai) {


        String eConteudo = TokenD.getConteudo();


        Token eProximo = mCompiler.getTokenFuturo();

        if (eProximo.getTipo() == TokenTipo.ID) {

            mCompiler.proximo();

            String eMarcador = eProximo.getConteudo();

            // System.out.println("Possivel marcador : " + eMarcador);

            ASTPai.setValor("MARKER");

            AST eMark = ASTPai.criarBranch("MARK");
            eMark.setNome(eMarcador);
            eMark.setValor("ID");

            AST eVal = ASTPai.criarBranch("VALUE");
            eVal.setNome(eConteudo);
            eVal.setValor("ID");

        } else {

            ASTPai.setNome(eConteudo);
            ASTPai.setValor("ID");


        }

    }

    public void dentro_init(AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setValor("INIT");


        Token TokenC2 = mCompiler.getTokenAvante();

      //  System.out.println("STRUCT :: " +mCompiler.getTokenCorrente().getConteudo() + " "+ mCompiler.getTokenCorrente().getLinha() + ":" + mCompiler.getTokenCorrente().getPosicao());

        if (TokenC2.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenC2.getConteudo());
            Token TokenC3 = mCompiler.getTokenAvante();


            if (TokenC3.getTipo() == TokenTipo.PARENTESES_ABRE) {

                //  AST_Value_Argument gAST = new AST_Value_Argument(mCompiler);
                //    gAST.ReceberArgumentos(ASTPai);

                AST_Value gAST = new AST_Value(mCompiler);
                ReceberArgumentos(ASTPai, mTemTipo, mTipo);


            } else {
                System.out.println("Problema IP : " + TokenC3.getConteudo());
            }

        } else {
            System.out.println("Problema IC : " + TokenC2.getConteudo());
        }


        AST AST_Generico = ASTPai.criarBranch("GENERIC");
        AST_Generico.setNome("FALSE");

        Token TokenFuturo = mCompiler.getTokenFuturo();
        if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

            AST_Generico.setNome("TRUE");

            AST_Generic mg = new AST_Generic(mCompiler);
            mg.init(AST_Generico);

        }

    }

    public void dentro_start(AST ASTPai) {

        ASTPai.setValor("START");

        Token TokenC2 = mCompiler.getTokenAvante();
        if (TokenC2.getTipo() == TokenTipo.ID) {

            ASTPai.setNome(TokenC2.getConteudo());

            AST AST_Generico = ASTPai.criarBranch("GENERIC");
            AST_Generico.setNome("FALSE");

            Token TokenFuturo = mCompiler.getTokenFuturo();
            if (TokenFuturo.getTipo() == TokenTipo.ENVIAR) {

                AST_Generico.setNome("TRUE");

                AST_Generic mg = new AST_Generic(mCompiler);
                mg.init(AST_Generico);

            }

            AST_Start mAST = new AST_Start(mCompiler);
            mAST.init(ASTPai);


        } else {
            System.out.println("Problema IC : " + TokenC2.getConteudo());
        }


    }

    public boolean dentro_ternal(AST ASTPai, TokenTipo mTerminadorPrimario, TokenTipo mTerminadorSecundario) {

        boolean dentro = false;

        ASTPai.setValor("TERNAL");


        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");


        AST AST_Condicao = ASTPai.criarBranch("CONDITION");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.setBloco();
        mAST.init(AST_Condicao);
        AST_Condicao.setTipo("CONDITION");


        Token TokenQ1 = mCompiler.getTokenAvanteStatus(TokenTipo.QUAD, "Era esperado ::");
        Token TokenC_1 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

        AST AST_True = ASTPai.criarBranch("TRUE");

        AST_Value mAST_TRUE = new AST_Value(mCompiler);
        mAST_TRUE.setBloco();
        mAST_TRUE.init(AST_True);

        AST_True.setTipo("TRUE");


        if (mCompiler.getTokenFuturo().getTipo() == mTerminadorPrimario) {

            // mCompiler.Proximo();
            dentro = false;

        } else if (mCompiler.getTokenFuturo().getTipo() == mTerminadorSecundario) {

            //     mCompiler.Proximo();
            dentro = false;

        } else {

            Token TokenQ2 = mCompiler.getTokenAvanteIDStatus("not", "Era esperado not");
            Token TokenC_2 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado Abrir Parenteses");

            AST AST_False = ASTPai.criarBranch("FALSE");

            AST_Value mAST_False = new AST_Value(mCompiler);
            mAST_False.setBloco();
            mAST_False.init(AST_False);

            AST_False.setTipo("FALSE");


            dentro = true;

        }

        //   System.out.println("Saindo Ternario :: " + mCompiler.getTokenCorrente().getConteudo());


        return dentro;
    }

    public void dentro_default(Token TokenD, AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setNome("");
        ASTPai.setValor("DEFAULT");

        if (mTemTipo) {
            ASTPai.getASTS().add(mTipo);
        } else {
            mCompiler.errarCompilacao("O DEFAULT precisa ser definido em alocacoes !", TokenD);
        }

    }

    public void dentro_stages(AST ASTPai) {

        ASTPai.setValor("STAGE");
        Token TokenC3 = mCompiler.getTokenAvante();
        if (TokenC3.getTipo() == TokenTipo.ID) {
            ASTPai.criarBranch("STAGED").setNome(TokenC3.getConteudo());
        } else {
            System.out.println("Problema ZZ : " + TokenC3.getConteudo());
        }

    }

    public void dentro_local(AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setValor("EXECUTE_LOCAL");


        Token TokenP2 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado ABRIR PARESENTESES !");

        //  AST_Value mAVA = new AST_Value(mCompiler);
        ReceberArgumentos(ASTPai, mTemTipo, mTipo);


    }

    public void dentro_block(AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setNome("");
        ASTPai.setValor("BLOCK");

        AST_Value mAST = new AST_Value(mCompiler);
        mAST.setBloco();

        if (mTemTipo) {
            mAST.sePrecisarTipar(mTipo);
        }

        mAST.init(ASTPai.criarBranch("VALUE"));

    }


    public void dentro_funct(AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setValor("FUNCT");

        // AST_Value gAST = new AST_Value(mCompiler);
        //AST_Value gAST = new AST_Value_Argument(mCompiler);

        // if (mTemTipo) {
        //     gAST.sePrecisarTipar(mTipo);
        // }
        ReceberArgumentos(ASTPai, mTemTipo, mTipo);

    }

    public void dentro_struct(AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setValor("STRUCT");

        ReceberNovoEscopo(ASTPai, mTemTipo, mTipo);


    }


    public void dentro_functor(AST ASTPai, boolean mTemTipo, AST mTipo) {


        ASTPai.setValor("EXECUTE_FUNCTOR");
        AST AST_Generico = ASTPai.criarBranch("GENERICS");


        AST_Generic mg = new AST_Generic(mCompiler);
        mg.init_receberProto(AST_Generico);


        Token TokenC3 = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado o nome de um FUNCTOR!");

        ASTPai.setNome(TokenC3.getConteudo());

        Token TokenC4 = mCompiler.getTokenAvanteStatus(TokenTipo.PARENTESES_ABRE, "Era esperado abrir paresenteses !");

        // AST_Value mAVA = new AST_Value(mCompiler);
        ReceberArgumentos(ASTPai, mTemTipo, mTipo);

    }

    public void dentro_dot(AST ASTPai, boolean mTemTipo, AST mTipo) {


        ReceberNovoEscopo(ASTPai, mTemTipo, mTipo);

        ASTPai.setValor("STRUCT");

    }

    public void dentro_extern(AST ASTPai, boolean mTemTipo, AST mTipo) {

        ASTPai.setValor("STRUCT_EXTERN");

        ReceberNovoEscopo(ASTPai, mTemTipo, mTipo);
    }

    public void ReceberNovoEscopo(AST ASTPai, boolean mTemTipo, AST mTipo) {

        Token TokenD = mCompiler.getTokenAvante();


        if (TokenD.getTipo() == TokenTipo.ID) {

            AST mASTSub = ASTPai.criarBranch("INTERNAL");
            mASTSub.setNome(TokenD.getConteudo());

            mASTSub.setValor("STRUCT_OBJECT");

            Token TokenF = mCompiler.getTokenAvante();
            if (TokenF.getTipo() == TokenTipo.PARENTESES_ABRE) {

                mASTSub.setValor("STRUCT_FUNCT");


                //  AST_Value gAST = new AST_Value(mCompiler);
                ReceberArgumentos(mASTSub, mTemTipo, mTipo);

                Token TokenF2 = mCompiler.getTokenAvante();


                if (TokenF2.getTipo() == TokenTipo.PONTO) {
                    //  System.out.println("Vamos pontuar o escopo 2 !");


                    mASTSub.setValor("STRUCT");

                    ReceberNovoEscopo(mASTSub, mTemTipo, mTipo);

                    if (mASTSub.existeBranch("ARGUMENTS")) {
                        if (mASTSub.mesmoValor("STRUCT")) {
                            mASTSub.setValor("STRUCT_FUNCT");
                        }
                    }


                } else {
                    mCompiler.voltar();
                }


            } else if (TokenF.getTipo() == TokenTipo.PONTO) {


                mASTSub.setValor("STRUCT_OBJECT");

                ReceberNovoEscopo(mASTSub, mTemTipo, mTipo);

            } else {
                mCompiler.voltar();
            }


        } else {
            System.out.println("Era esperado um ID : " + TokenD.getConteudo());
        }

    }


    public void ReceberArgumentos(AST ASTAvo, boolean mTemTipo, AST mTipo) {

        // System.out.println("AST_VALUE -> Receber Argumentos");

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

                AST_Value mAST = new AST_Value(mCompiler);
                mAST.setBuscadorDeArgumentos();

                mCompiler.voltar();

                if (mTemTipo) {
                    mAST.sePrecisarTipar(mTipo);
                }
                mAST.init(ASTCorrente);
                ASTCorrente.setTipo("ARGUMENT");

                Token P2 = mCompiler.getTokenCorrente();


                if (P2.getTipo() == TokenTipo.VIRGULA) {
                    mais = true;
                } else if (P2.getTipo() == TokenTipo.PARENTESES_FECHA) {
                    saiu = true;
                    break;
                }
            } else if (TokenD.getTipo() == TokenTipo.NUMERO_FLOAT) {

                mais = false;


                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");

                AST_Value mAST = new AST_Value(mCompiler);
                mAST.setBuscadorDeArgumentos();

                mCompiler.voltar();

                if (mTemTipo) {
                    mAST.sePrecisarTipar(mTipo);
                }

                mAST.init(ASTCorrente);
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
                mAST.setBuscadorDeArgumentos();

                mCompiler.voltar();

                if (mTemTipo) {
                    mAST.sePrecisarTipar(mTipo);
                }

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

                mCompiler.voltar();

                AST_Value mAST = new AST_Value(mCompiler);
                mAST.setBuscadorDeArgumentos();

                if (mTemTipo) {
                    mAST.sePrecisarTipar(mTipo);
                }
                mAST.init(ASTCorrente);

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

                mCompiler.voltar();

                AST ASTCorrente = ASTPai.criarBranch("ARGUMENT");
                ASTCorrente.setValor("CONTAINER");

                AST ASTV = ASTCorrente.criarBranch("VALUE");


                AST_Value mAST = new AST_Value(mCompiler);
                if (mTemTipo) {
                    mAST.sePrecisarTipar(mTipo);
                }
                mAST.setBuscadorDeArgumentos();

                mAST.init(ASTV);
                ASTV.setTipo("VALUE");


                Token P2 = mCompiler.getTokenCorrente();

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

    public void dentro_reg(AST ASTPai, TokenTipo mTerminadorPrimario, TokenTipo mTerminadorSecundario) {


        Token Tokena = mCompiler.getTokenAvanteStatus(TokenTipo.ARROBA, "Era esperado um @ REGISTRADOR");

        Token TokenC = mCompiler.getTokenAvanteStatus(TokenTipo.ID, "Era esperado um Registrador !");

        ASTPai.setNome(TokenC.getConteudo());
        ASTPai.setValor("REG");




    }


}
