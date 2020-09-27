package Sigmaz.Compilador;

import Sigmaz.Lexer.Token;
import Sigmaz.Lexer.TokenTipo;
import Sigmaz.Utils.AST;

public class AST_Value {

    private CompilerUnit mCompiler;

    //   private TokenTipo mTerminar;
    private String mTerminarErro;

    private boolean mTemTipo;
    private AST mTipo;


    private TokenTipo mTerminadorPrimario;
    private TokenTipo mTerminadorSecundario;
    private String mTerminadorErro;


    public AST_Value(CompilerUnit eCompiler) {

        mCompiler = eCompiler;
        // mTerminar = TokenTipo.PONTOVIRGULA;
        mTerminarErro = "Era esperado um ponto e virgula ! : ";
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
        //  mAST.mTerminar = mTerminar;
        mAST.mTerminarErro = mTerminarErro;
        mAST.mTerminadorPrimario = mTerminadorPrimario;
        mAST.mTerminadorSecundario = mTerminadorSecundario;
        mAST.mTerminadorErro = mTerminadorErro;

        if (mTemTipo) {
            mAST.sePrecisarTipar(mTipo);
        }

        mAST.initUltimo(ASTDireita);

        ASTDireita.setTipo("RIGHT");


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

            mAST.init(ASTPai.criarBranch("VALUE"));

            mCompiler.voltar();

            Token TokenC3 = mCompiler.getTokenCorrente();

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

            mAST_ValueTypes.dentro_local(ASTPai, mTemTipo, mTipo);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {

            mAST_ValueTypes.dentro_num(TokenD, ASTPai);

            SegundaParte(ASTPai);

        } else if (TokenD.getTipo() == TokenTipo.NUMERO_FLOAT) {

            mAST_ValueTypes.dentro_float(TokenD, ASTPai);

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            SegundaParte(ASTPai);


        } else if (TokenD.getTipo() == TokenTipo.ID) {


            if (TokenD.mesmoConteudo("if")) {

                mAST_ValueTypes.dentro_ternal(ASTPai, mTerminadorPrimario, mTerminadorSecundario);

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


                } else if (TokenC2.getTipo() == TokenTipo.PONTO) {


                    mAST_ValueTypes.dentro_dot(eThis, mTemTipo, mTipo);


                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.SETA) {


                    mAST_ValueTypes.dentro_extern(eThis, mTemTipo, mTipo);


                    SegundaParte(ASTPai);


                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {


                    mAST_ValueTypes.dentro_funct(eThis, mTemTipo, mTipo);

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


                ASTPai.setNome(TokenD.getConteudo());
                ASTPai.setValor("ID");

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


        } else if (TokenD.getTipo() == TokenTipo.NUMERO) {


            mAST_ValueTypes.dentro_num(TokenD, ASTPai);


            terminar();

        } else if (TokenD.getTipo() == TokenTipo.NUMERO_FLOAT) {

            mAST_ValueTypes.dentro_float(TokenD, ASTPai);


            terminar();

        } else if (TokenD.getTipo() == TokenTipo.TEXTO) {

            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("Text");

            terminar();

        } else if (TokenD.getTipo() == TokenTipo.NEGADOR) {

            ASTPai.criarBranch("MODE").setNome("INVERSE");

            ASTPai.setNome("");
            ASTPai.setValor("DIRECTOR");

            AST_Value mAST = new AST_Value(mCompiler);
            mAST.init(ASTPai.criarBranch("VALUE"));

            mCompiler.voltar();

            //  Token TokenC3 = mCompiler.getTokenCorrente();

            // System.out.println("apos negador : " + TokenC3.getConteudo());

            terminar();


        } else if (TokenD.getTipo() == TokenTipo.ARROBA) {

            mAST_ValueTypes.dentro_local(ASTPai, mTemTipo, mTipo);

            terminar();


        } else if (TokenD.getTipo() == TokenTipo.ID) {


            ASTPai.setNome(TokenD.getConteudo());
            ASTPai.setValor("ID");

            if (TokenD.mesmoConteudo("default")) {

                mAST_ValueTypes.dentro_default(TokenD, ASTPai, mTemTipo, mTipo);

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

                mAST_ValueTypes.dentro_init(ASTPai, mTemTipo, mTipo);

                terminar();
                return;

            } else if (TokenD.mesmoConteudo("start")) {

                mAST_ValueTypes.dentro_start(ASTPai);

                terminar();
                return;

            } else {


                Token TokenC2 = mCompiler.getTokenAvante();
                if (TokenC2.getTipo() == mTerminadorPrimario) {
                    return;
                } else if (TokenC2.getTipo() == mTerminadorSecundario) {
                    return;
                } else if (TokenC2.getTipo() == TokenTipo.PARENTESES_ABRE) {


                    mAST_ValueTypes.dentro_funct(ASTPai, mTemTipo, mTipo);


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
                    System.out.println("Problema XX4 : " + TokenC2.getConteudo());
                }

                return;

            }


        } else {

            System.out.println("Valorando 5 : " + TokenD.getConteudo());


        }


    }


}
