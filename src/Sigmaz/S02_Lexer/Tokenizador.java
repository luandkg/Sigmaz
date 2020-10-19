package Sigmaz.S02_Lexer;

public class Tokenizador {

    private Lexer mLexer;
    private final String ALFANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz0123456789";
    private final String NUM = "0123456789";

    public Tokenizador() {


    }

    public void init(Lexer eLexer) {
        mLexer = eLexer;
    }

    public Token getIdentificador() {


        int eInicio = mLexer.getPosicao();

        String eTokenConteudo = mLexer.getCorrente();

        mLexer.avancar();

        while (mLexer.Continuar()) {
            String charC = mLexer.getCorrente();

            if (ALFANUM.contains(charC)) {
                eTokenConteudo += charC;

            } else {
                mLexer.voltar();

                break;
            }
            mLexer.avancar();
        }


        int eFim = mLexer.getPosicao();


        return new Token(TokenTipo.ID, eTokenConteudo, eInicio, eFim, mLexer.getLinha());
    }

    public Token getNumero() {

        int eInicio = mLexer.getPosicao();

        String eTokenConteudo = mLexer.getCorrente();
        mLexer.avancar();


        boolean pontuar = false;

        while (mLexer.Continuar()) {
            String charC = mLexer.getCorrente();

            if (NUM.contains(charC)) {
                eTokenConteudo += charC;
            } else if (charC.contentEquals(".")) {
                pontuar = true;
                mLexer.avancar();
                break;
            } else {
                mLexer.voltar();
                break;
            }
            mLexer.avancar();
        }


        if (pontuar) {
            boolean pontuou = false;
            eTokenConteudo += ".";

            while (mLexer.Continuar()) {
                String charC = mLexer.getCorrente();

                if (NUM.contains(charC)) {
                    eTokenConteudo += charC;
                    pontuou = true;
                } else {
                    mLexer.voltar();

                    break;
                }
                mLexer.avancar();
            }

            if (!pontuou) {
                mLexer.errar("Numerop invalido !", mLexer.getLinha(), mLexer.getPosicao());
            }
        }


        int eFim = mLexer.getPosicao();


        if (eTokenConteudo.contains(".")) {
            return new Token(TokenTipo.NUMERO_FLOAT, eTokenConteudo, eInicio, eFim, mLexer.getLinha());
        } else {
            return new Token(TokenTipo.NUMERO, eTokenConteudo, eInicio, eFim, mLexer.getLinha());
        }

    }

    public Token getMais() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("+")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.SOMADOR, "++", eInicio, eFim, mLexer.getLinha());

        } else {

            mLexer.avancar();

            mTokenRetorno = getNumero();

            mTokenRetorno.setConteudo("+" + mTokenRetorno.getConteudo());

        }

        return mTokenRetorno;
    }

    public Token getMenos() {


        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }


        if (charP.contentEquals(">")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.SETA, "->", eInicio, eFim, mLexer.getLinha());

        } else if (charP.contentEquals("-")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.DIMINUIDOR, "--", eInicio, eFim, mLexer.getLinha());
        } else {

            mLexer.avancar();

            mTokenRetorno = getNumero();

            mTokenRetorno.setConteudo("-" + mTokenRetorno.getConteudo());


        }


        return mTokenRetorno;

    }


    public Token getAsterisco() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("*")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = (new Token(TokenTipo.MULTIPLICADOR, "**", eInicio, eFim, mLexer.getLinha()));


        } else {

            mLexer.errar("Era esperado *", mLexer.getLinha(), mLexer.getPosicao());

        }

        return mTokenRetorno;

    }

    public Token getBarra() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("/")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = (new Token(TokenTipo.DIVISOR, "//", eInicio, eFim, mLexer.getLinha()));


        } else {

            mLexer.errar("Era esperado /", mLexer.getLinha(), mLexer.getPosicao());

        }

        return mTokenRetorno;

    }

    public Token getPonto() {


        Token mTokenRetorno = null;

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        mTokenRetorno = new Token(TokenTipo.PONTO, ".", eInicio, eFim, mLexer.getLinha());


        return mTokenRetorno;

    }


    public Token getDoisPontos() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals(":")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.QUAD, "::", eInicio, eFim, mLexer.getLinha());


        } else {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mTokenRetorno = new  Token(TokenTipo.DOISPONTOS, ":", eInicio, eFim, mLexer.getLinha());


        }

        return mTokenRetorno;
    }

    public Token getIgual() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("=")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.COMPARADOR_IGUALDADE, "==", eInicio, eFim, mLexer.getLinha());


        } else {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mTokenRetorno = new  Token(TokenTipo.IGUAL, "=", eInicio, eFim, mLexer.getLinha());


        }

        return mTokenRetorno;
    }


    public Token getExclamacao() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("!")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.COMPARADOR_DIFERENTE, "!!", eInicio, eFim, mLexer.getLinha());


        } else {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mTokenRetorno = new  Token(TokenTipo.NEGADOR, "!", eInicio, eFim, mLexer.getLinha());


        }

        return mTokenRetorno;
    }


    public Token getMenor() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("<")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.RECEBER, "<<", eInicio, eFim, mLexer.getLinha());


        } else {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mTokenRetorno = new  Token(TokenTipo.MENOR, "<", eInicio, eFim, mLexer.getLinha());


        }

        return mTokenRetorno;
    }

    public Token getMaior() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals(">")) {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.ENVIAR, ">>", eInicio, eFim, mLexer.getLinha());


        } else {

            int eInicio = mLexer.getPosicao();
            int eFim = mLexer.getPosicao();

            mTokenRetorno = new  Token(TokenTipo.MAIOR, ">", eInicio, eFim, mLexer.getLinha());


        }

        return mTokenRetorno;
    }

    public Token getComentario() {

        String charP = "";

        Token mTokenRetorno = null;

        if (mLexer.TemProximo()) {
            charP = mLexer.getFuturo();
        }

        if (charP.contentEquals("#")) {

            int eInicio = mLexer.getPosicao();


            String mComentario = "##";

            mLexer.avancar();
            mLexer.avancar();

            while (  mLexer.Continuar()) {
                String charC = mLexer.getCorrente();

                String charFuturo = "";
                if (mLexer.TemProximo()) {
                    charFuturo =mLexer.getFuturo();
                    if (charC.contentEquals("#") && charFuturo.contentEquals("#")) {
                        mLexer.avancar();
                        mComentario += "##";
                        break;
                    } else {
                        mComentario += charC;
                    }
                }


                mLexer.avancar();
            }


            int eFim = mLexer.getPosicao();

            mLexer.avancar();

            mTokenRetorno = new Token(TokenTipo.COMENTARIO, mComentario, eInicio, eFim, mLexer.getLinha());


        } else {

            int eInicio = mLexer.getPosicao();

            String mComentario = mLexer.getCorrente();
            mLexer.avancar();
            while (mLexer.Continuar()) {
                String charC = mLexer.getCorrente();

                if (charC.contentEquals("\n")) {
                    mLexer.descerLinha();
                    break;
                } else {
                    mComentario += charC;
                }
                mLexer.avancar();
            }


            int eFim = mLexer.getPosicao();

            mTokenRetorno = new  Token(TokenTipo.COMENTARIO, mComentario, eInicio, eFim, mLexer.getLinha());


        }

        return mTokenRetorno;
    }



    public Token getVirgula() {


        Token mTokenRetorno = null;

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        mTokenRetorno = new Token(TokenTipo.VIRGULA, ",", eInicio, eFim, mLexer.getLinha());


        return mTokenRetorno;

    }

    public Token getPontoEVirgula() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();

        return  new Token(TokenTipo.PONTOVIRGULA, ";", eInicio, eFim, mLexer.getLinha());


    }

    public Token getArroba() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();

        return new Token(TokenTipo.ARROBA, "@", eInicio, eFim, mLexer.getLinha());

    }

    public Token getParenteses_Abre() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


       return new Token(TokenTipo.PARENTESES_ABRE, "(", eInicio, eFim, mLexer.getLinha());


    }

    public Token getParenteses_Fecha() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        return new Token(TokenTipo.PARENTESES_FECHA, ")", eInicio, eFim, mLexer.getLinha());

    }



    public Token getColchete_Fecha() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        return new Token(TokenTipo.COLCHETE_FECHA, "]", eInicio, eFim, mLexer.getLinha());

    }


    public Token getColchete_Abre() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        return new Token(TokenTipo.COLCHETE_ABRE, "[", eInicio, eFim, mLexer.getLinha());

    }



    public Token getChave_Fecha() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        return new Token(TokenTipo.CHAVE_FECHA, "}", eInicio, eFim, mLexer.getLinha());

    }


    public Token getChave_Abre() {

        int eInicio = mLexer.getPosicao();
        int eFim = mLexer.getPosicao();


        return new Token(TokenTipo.CHAVE_ABRE, "{", eInicio, eFim, mLexer.getLinha());

    }



    public Token getTextoDuplo() {


        Token mTokenRetorno = null;

        int eInicio = mLexer.getPosicao();


        String eTokenConteudo = "";
        mLexer.avancar();

        boolean finalizado = false;

        while (   mLexer. Continuar()) {
            String charC = mLexer.getCorrente();

            if (charC.contentEquals("\"")) {
                finalizado = true;
                break;
            } else {
                eTokenConteudo += charC;
            }
            mLexer.avancar();
        }

        if (finalizado == false) {
            mLexer. errar("Texto nao finalizado !", mLexer.getLinha(), mLexer.getPosicao());

        }


        int eFim = mLexer.getPosicao();

        mTokenRetorno = new Token(TokenTipo.TEXTO, eTokenConteudo, eInicio, eFim, mLexer.getLinha());


        return mTokenRetorno;

    }

    public Token getTextoSimples() {


        Token mTokenRetorno = null;

        int eInicio = mLexer.getPosicao();


        String eTokenConteudo = "";
        mLexer.avancar();

        boolean finalizado = false;

        while (   mLexer. Continuar()) {
            String charC = mLexer.getCorrente();

            if (charC.contentEquals("'")) {
                finalizado = true;
                break;
            } else {
                eTokenConteudo += charC;
            }
            mLexer.avancar();
        }

        if (finalizado == false) {
            mLexer. errar("Texto nao finalizado !", mLexer.getLinha(), mLexer.getPosicao());

        }


        int eFim = mLexer.getPosicao();

        mTokenRetorno = new Token(TokenTipo.TEXTO, eTokenConteudo, eInicio, eFim, mLexer.getLinha());


        return mTokenRetorno;

    }


}
