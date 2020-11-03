package Sigmaz.S01_Compilador.C03_Parser.Fluxo;

import Sigmaz.S01_Compilador.C03_Parser.Parser;
import Sigmaz.S01_Compilador.C02_Lexer.Token;
import Sigmaz.S01_Compilador.C02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.AST;

public class AST_Value {

    private Parser mCompiler;

    private String mTerminarErro;

    private boolean mTemTipo;
    private AST mTipo;


    private TokenTipo mTerminadorPrimario;
    private TokenTipo mTerminadorSecundario;
    private String mTerminadorErro;


    public AST_Value(Parser eCompiler) {

        mCompiler = eCompiler;
        mTemTipo = false;
        mTipo = null;


        mTerminadorPrimario = TokenTipo.PONTOVIRGULA;
        mTerminadorSecundario = TokenTipo.PONTOVIRGULA;
        mTerminadorErro = "Era esperado um ponto e virgula !";

    }


    public void setBuscadorDeArgumentos() {

        mTerminadorPrimario = TokenTipo.VIRGULA;
        mTerminadorSecundario = TokenTipo.PARENTESES_FECHA;
        mTerminadorErro = "Era esperado uma virgula ou um fechador de paresenteses !";

    }


    public void setBuscadorDeArgumentos_Colchete() {

        mTerminadorPrimario = TokenTipo.VIRGULA;
        mTerminadorSecundario = TokenTipo.COLCHETE_FECHA;
        mTerminadorErro = "Era esperado uma virgula ou um fechador de colchete !";

    }

    public void setBuscadorDeItensDeVetor() {

        mTerminadorPrimario = TokenTipo.VIRGULA;
        mTerminadorSecundario = TokenTipo.CHAVE_FECHA;
        mTerminadorErro = "Era esperado uma virgula ou um fechador de chaves !";

    }

    public void setBloco() {

        mTerminadorPrimario = TokenTipo.PARENTESES_FECHA;
        mTerminadorSecundario = TokenTipo.PARENTESES_FECHA;
        mTerminadorErro = "Era esperado fechar parenteses !";

    }

    public void setSeta() {

        mTerminadorPrimario = TokenTipo.SETA;
        mTerminadorSecundario = TokenTipo.SETA;
        mTerminadorErro = "Era esperado uma SETA !";

    }


    public void sePrecisarTipar(AST eTipo) {
        mTipo = eTipo;
        mTemTipo = true;
    }


    public void initParam(AST ASTPai) {

        mTerminadorPrimario = TokenTipo.PARENTESES_FECHA;
        mTerminadorSecundario = TokenTipo.PARENTESES_FECHA;

        mTerminarErro = "Era esperado  fechar  parenteses ! : ";

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
        mAST.mTerminarErro = mTerminarErro;
        mAST.mTerminadorPrimario = mTerminadorPrimario;
        mAST.mTerminadorSecundario = mTerminadorSecundario;
        mAST.mTerminadorErro = mTerminadorErro;

        if (mTemTipo) {
            mAST.sePrecisarTipar(mTipo);
        }


        mAST.initUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");

        if (mCompiler.getIsDebug()) {
            mCompiler.debug(ASTPai);
        }

    }


    public void init(AST ASTPai) {


        AST_ValueTypes mAST_ValueTypes = new AST_ValueTypes(mCompiler);

        Token TokenD = mCompiler.getTokenAvante();

        // System.out.println("Pre Valor : " + TokenD.getConteudo());


        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {


            mAST_ValueTypes.dentro_block(ASTPai, mTemTipo, mTipo);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.CHAVE_ABRE) {


            ASTPai.setNome("");
            ASTPai.setValor("VECTOR");

            AST_Vector mAST = new AST_Vector(mCompiler);
            mAST.init(ASTPai);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.NEGADOR) {

            ASTPai.criarBranch("MODE").setNome("INVERSE");

            ASTPai.setNome("");
            ASTPai.setValor("DIRECTOR");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminadorPrimario = mTerminadorPrimario;
            mAST.mTerminadorSecundario = mTerminadorSecundario;
            mAST.mTerminadorErro = mTerminadorErro;

            mAST.initApenasUm(ASTPai.criarBranch("VALUE"));


            if (mCompiler.getTokenCorrente().getTipo() == mTerminadorPrimario) {
                return;
            } else if (mCompiler.getTokenCorrente().getTipo() == mTerminadorSecundario) {
                return;
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.COMPARADOR_DIFERENTE) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.SOMADOR) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.DIMINUIDOR) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.MULTIPLICADOR) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.DIVISOR) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.ENVIAR) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.RECEBER) {
                mCompiler.voltar();
            } else if (mCompiler.getTokenCorrente().getTipo() == TokenTipo.ANEXADOR) {
                mCompiler.voltar();

            }

            //  System.out.println("Apos Negador Voltar : " + mCompiler.getTokenCorrente().getConteudo());

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

            mAST_ValueTypes.dentro_local(ASTPai, mTemTipo, mTipo);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.INTEIRO) {

            mAST_ValueTypes.dentro_inteiro(TokenD, ASTPai);

            SegundaParte(ASTPai);

        } else if (TokenD.getTipo() == TokenTipo.NUMERO_FLOAT) {

            mAST_ValueTypes.dentro_float(TokenD, ASTPai);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            mAST_ValueTypes.dentro_string(TokenD, ASTPai);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.ID) {


            if (TokenD.mesmoConteudo("if")) {

                mAST_ValueTypes.dentro_ternal(ASTPai, mTerminadorPrimario, mTerminadorSecundario);

                SegundaParte(ASTPai);


            } else if (TokenD.mesmoConteudo("reg")) {

                mAST_ValueTypes.dentro_reg(ASTPai, mTerminadorPrimario, mTerminadorSecundario);

                SegundaParte(ASTPai);

            } else if (TokenD.mesmoConteudo("null")) {

                ASTPai.setValor("NULL");

                SegundaParte(ASTPai);

            } else if (TokenD.mesmoConteudo("init")) {

                mAST_ValueTypes.dentro_init(ASTPai, mTemTipo, mTipo);

                SegundaParte(ASTPai);


            } else if (TokenD.mesmoConteudo("start")) {


                mAST_ValueTypes.dentro_start(ASTPai);

                SegundaParte(ASTPai);


            } else if (TokenD.mesmoConteudo("functor")) {


                mAST_ValueTypes.dentro_functor(ASTPai, mTemTipo, mTipo);


                SegundaParte(ASTPai);


            } else if (TokenD.mesmoConteudo("this")) {


                ASTPai.setValor("THIS");
                AST eThis = ASTPai.criarBranch("THIS");


                Token TokenC2 = mCompiler.getTokenAvante();

                if (TokenC2.getTipo() == mTerminadorPrimario) {
                    return;
                } else if (TokenC2.getTipo() == mTerminadorSecundario) {
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
                } else if (TokenC2.getTipo() == TokenTipo.ENVIAR) {
                    operation_final("GREAT", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.RECEBER) {
                    operation_final("LESS", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.ANEXADOR) {
                    operation_final("APPEND", ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PONTO) {


                    mAST_ValueTypes.dentro_dot(eThis, mTemTipo, mTipo);


                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.SETA) {


                    mAST_ValueTypes.dentro_extern(eThis, mTemTipo, mTipo);


                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {


                    mAST_ValueTypes.dentro_funct(eThis, mTemTipo, mTipo);

                    SegundaParte(ASTPai);

                } else if (TokenC2.getTipo() == TokenTipo.COLCHETE_ABRE) {


                    mAST_ValueTypes.dentro_Getter(ASTPai, mTemTipo, mTipo);

                    SegundaParte(ASTPai);

                } else {

                    //  System.out.println("Problema H : " + TokenD.getConteudo());
                }

            } else if (TokenD.mesmoConteudo("auto")) {

                mCompiler.errarCompilacao("O AUTO nao retornara uma resposta !", TokenD);


            } else if (TokenD.mesmoConteudo("default")) {

                mAST_ValueTypes.dentro_default(TokenD, ASTPai, mTemTipo, mTipo);

                SegundaParte(ASTPai);


            } else {


                mAST_ValueTypes.dentro_id(TokenD, ASTPai);


                Token TokenC2 = mCompiler.getTokenAvante();


                if (TokenC2.getTipo() == mTerminadorPrimario) {
                    return;
                } else if (TokenC2.getTipo() == mTerminadorSecundario) {
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
                } else if (TokenC2.getTipo() == TokenTipo.ENVIAR) {
                    operation_final("GREAT", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.RECEBER) {
                    operation_final("LESS", ASTPai);
                } else if (TokenC2.getTipo() == TokenTipo.ANEXADOR) {
                    operation_final("APPEND", ASTPai);

                } else if (TokenC2.getTipo() == TokenTipo.QUAD) {

                    mAST_ValueTypes.dentro_stages(ASTPai);

                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PONTO) {


                    mAST_ValueTypes.dentro_struct(ASTPai, mTemTipo, mTipo);

                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.SETA) {


                    mAST_ValueTypes.dentro_extern(ASTPai, mTemTipo, mTipo);

                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {

                    mAST_ValueTypes.dentro_funct(ASTPai, mTemTipo, mTipo);

                    SegundaParte(ASTPai);

                } else if (TokenC2.getTipo() == TokenTipo.COLCHETE_ABRE) {


                    mAST_ValueTypes.dentro_Getter(ASTPai, mTemTipo, mTipo);

                    SegundaParte(ASTPai);


                } else {
                    //  System.out.println("Problema H : " + TokenD.getConteudo());
                }

            }


        } else {

            //   System.out.println("Valorando : " + TokenD.getConteudo());


        }


    }


    public void SegundaParte(AST ASTPai) {


        Token TokenC3 = mCompiler.getTokenAvante();

        //      System.out.println("SEgunda Parte : " + mCompiler.getTokenCorrente().getConteudo());

        if (TokenC3.getTipo() == TokenTipo.COMPARADOR_IGUALDADE) {
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
        } else if (TokenC3.getTipo() == TokenTipo.ENVIAR) {
            operation_final("GREAT", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.RECEBER) {
            operation_final("LESS", ASTPai);
        } else if (TokenC3.getTipo() == TokenTipo.ANEXADOR) {
            operation_final("APPEND", ASTPai);

        } else if (TokenC3.getTipo() == mTerminadorPrimario) {

        } else if (TokenC3.getTipo() == mTerminadorSecundario) {

        } else {

            mCompiler.errarCompilacao(mTerminadorErro, TokenC3);
        }

    }

    public void terminar() {

        Token TokenC2 = mCompiler.getTokenAvante();

        if (TokenC2.getTipo() == mTerminadorPrimario) {
            return;
        } else if (TokenC2.getTipo() == mTerminadorSecundario) {
            return;
        } else {
            System.out.println("Problema 1 : " + mTerminadorErro + " -->> " + TokenC2.getLinha() + ":" + TokenC2.getPosicao() + " com " + TokenC2.getConteudo());
        }

    }

    public void initUltimo(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();

        AST_ValueTypes mAST_ValueTypes = new AST_ValueTypes(mCompiler);

        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {


            mAST_ValueTypes.dentro_block(ASTPai, mTemTipo, mTipo);

            terminar();


        } else if (TokenD.getTipo() == TokenTipo.INTEIRO) {


            mAST_ValueTypes.dentro_inteiro(TokenD, ASTPai);


            terminar();

        } else if (TokenD.getTipo() == TokenTipo.NUMERO_FLOAT) {

            mAST_ValueTypes.dentro_float(TokenD, ASTPai);


            terminar();

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {


            mAST_ValueTypes.dentro_string(TokenD, ASTPai);

            terminar();

        } else if (TokenD.getTipo() == TokenTipo.NEGADOR) {

            ASTPai.criarBranch("MODE").setNome("INVERSE");

            ASTPai.setNome("");
            ASTPai.setValor("DIRECTOR");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminadorPrimario = mTerminadorPrimario;
            mAST.mTerminadorSecundario = mTerminadorSecundario;
            mAST.mTerminadorErro = mTerminadorErro;

            mAST.initApenasUm(ASTPai.criarBranch("VALUE"));


            if (mCompiler.getTokenCorrente().getTipo() == mTerminadorPrimario) {
                return;
            } else if (mCompiler.getTokenCorrente().getTipo() == mTerminadorSecundario) {
                return;
            }


            terminar();


        } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

            mAST_ValueTypes.dentro_local(ASTPai, mTemTipo, mTipo);

            terminar();


        } else if (TokenD.getTipo() == TokenTipo.ID) {


            if (TokenD.mesmoConteudo("default")) {

                mAST_ValueTypes.dentro_default(TokenD, ASTPai, mTemTipo, mTipo);

                terminar();
                return;

            } else if (TokenD.mesmoConteudo("reg")) {

                mAST_ValueTypes.dentro_reg(ASTPai, mTerminadorPrimario, mTerminadorSecundario);

                terminar();
                return;

            } else if (TokenD.mesmoConteudo("null")) {

                ASTPai.setValor("NULL");

                terminar();
                return;

            } else if (TokenD.mesmoConteudo("if")) {

                mAST_ValueTypes.dentro_ternal(ASTPai, mTerminadorPrimario, mTerminadorSecundario);

                terminar();
                return;

            } else if (TokenD.mesmoConteudo("functor")) {

                mAST_ValueTypes.dentro_functor(ASTPai, mTemTipo, mTipo);

                terminar();
                return;

            } else if (TokenD.mesmoConteudo("init")) {


                //  System.out.println(" INIT :: " + mCompiler.getTokenCorrente().getLinha() + ":" + mCompiler.getTokenCorrente().getPosicao());

                mAST_ValueTypes.dentro_init(ASTPai, mTemTipo, mTipo);


                terminar();
                return;

            } else if (TokenD.mesmoConteudo("start")) {

                mAST_ValueTypes.dentro_start(ASTPai);

                terminar();
                return;

            } else {

                mAST_ValueTypes.dentro_id(TokenD, ASTPai);


                Token TokenC2 = mCompiler.getTokenAvante();
                if (TokenC2.getTipo() == mTerminadorPrimario) {
                    return;
                } else if (TokenC2.getTipo() == mTerminadorSecundario) {
                    return;
                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {


                    mAST_ValueTypes.dentro_funct(ASTPai, mTemTipo, mTipo);


                    terminar();

                } else if (TokenC2.getTipo() == TokenTipo.COLCHETE_ABRE) {


                    mAST_ValueTypes.dentro_Getter(ASTPai, mTemTipo, mTipo);

                    terminar();

                } else if (TokenC2.getTipo() == TokenTipo.QUAD) {


                    mAST_ValueTypes.dentro_stages(ASTPai);


                    terminar();


                } else if (TokenC2.getTipo() == TokenTipo.PONTO) {


                    mAST_ValueTypes.dentro_dot(ASTPai, mTemTipo, mTipo);

                    terminar();


                } else if (TokenC2.getTipo() == TokenTipo.SETA) {


                    mAST_ValueTypes.dentro_extern(ASTPai, mTemTipo, mTipo);

                    terminar();


                } else {
                    System.out.println("Problema 4 : " + TokenC2.getConteudo());
                    mCompiler.voltar();
                }

                return;

            }


        } else {

            System.out.println("Valorando 5 : " + TokenD.getConteudo());


        }


    }


    public void initApenasUm(AST ASTPai) {

        Token TokenD = mCompiler.getTokenAvante();

        AST_ValueTypes mAST_ValueTypes = new AST_ValueTypes(mCompiler);

        if (TokenD.getTipo() == TokenTipo.PARENTESES_ABRE) {


            mAST_ValueTypes.dentro_block(ASTPai, mTemTipo, mTipo);


        } else if (TokenD.getTipo() == TokenTipo.INTEIRO) {


            mAST_ValueTypes.dentro_inteiro(TokenD, ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.NUMERO_FLOAT) {

            mAST_ValueTypes.dentro_float(TokenD, ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {


            mAST_ValueTypes.dentro_string(TokenD, ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.NEGADOR) {

            ASTPai.criarBranch("MODE").setNome("INVERSE");

            ASTPai.setNome("");
            ASTPai.setValor("DIRECTOR");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.mTerminadorPrimario = mTerminadorPrimario;
            mAST.mTerminadorSecundario = mTerminadorSecundario;
            mAST.mTerminadorErro = mTerminadorErro;

            mAST.initApenasUm(ASTPai.criarBranch("VALUE"));


        } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

            mAST_ValueTypes.dentro_local(ASTPai, mTemTipo, mTipo);


        } else if (TokenD.getTipo() == TokenTipo.ID) {


            mAST_ValueTypes.dentro_id(TokenD, ASTPai);


            if (TokenD.mesmoConteudo("default")) {

                mAST_ValueTypes.dentro_default(TokenD, ASTPai, mTemTipo, mTipo);

                return;

            } else if (TokenD.mesmoConteudo("null")) {

                ASTPai.setValor("NULL");

                return;

            } else if (TokenD.mesmoConteudo("reg")) {

                mAST_ValueTypes.dentro_reg(ASTPai, mTerminadorPrimario, mTerminadorSecundario);


                return;

            } else if (TokenD.mesmoConteudo("if")) {

                mAST_ValueTypes.dentro_ternal(ASTPai, mTerminadorPrimario, mTerminadorSecundario);

                return;


            } else if (TokenD.mesmoConteudo("functor")) {

                mAST_ValueTypes.dentro_functor(ASTPai, mTemTipo, mTipo);

                return;

            } else if (TokenD.mesmoConteudo("init")) {

                mAST_ValueTypes.dentro_init(ASTPai, mTemTipo, mTipo);

                return;

            } else if (TokenD.mesmoConteudo("start")) {

                mAST_ValueTypes.dentro_start(ASTPai);

                return;

            } else {


                Token TokenC2 = mCompiler.getTokenAvante();
                if (TokenC2.getTipo() == mTerminadorPrimario) {
                    return;
                } else if (TokenC2.getTipo() == mTerminadorSecundario) {
                    return;
                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {


                    mAST_ValueTypes.dentro_funct(ASTPai, mTemTipo, mTipo);

                } else if (TokenC2.getTipo() == TokenTipo.COLCHETE_ABRE) {


                    mAST_ValueTypes.dentro_Getter(ASTPai, mTemTipo, mTipo);


                } else if (TokenC2.getTipo() == TokenTipo.QUAD) {


                    mAST_ValueTypes.dentro_stages(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PONTO) {


                    mAST_ValueTypes.dentro_dot(ASTPai, mTemTipo, mTipo);


                } else if (TokenC2.getTipo() == TokenTipo.SETA) {


                    mAST_ValueTypes.dentro_extern(ASTPai, mTemTipo, mTipo);


                } else {
                    // mCompiler.voltar();
                }

                return;

            }


        } else {

            System.out.println("Valorando 5 : " + TokenD.getConteudo());


        }

    }


}
