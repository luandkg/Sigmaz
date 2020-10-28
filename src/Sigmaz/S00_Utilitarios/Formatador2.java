package Sigmaz.S00_Utilitarios;

import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;

public class Formatador2 {


    private String eDocumento;

    private int mQuantidade;
    private int mIndex;
    private int mTab;
    private Lexer LexerC;
    private int mLinha;

    public Formatador2() {

        mQuantidade = 0;
        mIndex = 0;
        mTab = 0;
        eDocumento = "";
        LexerC = new Lexer();
        mLinha = 0;

    }


    public void identar(String eArquivoOrigem, String eArquivoDestino) {


        LexerC.init(eArquivoOrigem);

        mQuantidade = LexerC.getTokens().size();
        mIndex = 0;
        mTab = 0;
        eDocumento = "";
        mLinha = 0;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("call")) {

                formate_Call(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                formate_Simples(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("auto")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("functor")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {

                formate_Struct(TokenC);

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;
        }


        Texto.Escrever(eArquivoDestino, eDocumento);

    }

    public void pularLinha() {
        eDocumento += "\n";
    }

    public void formate_Call(Token TokenOrigem) {

        pularLinha();

        eDocumento += TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpo(TokenC);

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento += " " + TokenC.getConteudo();
                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void formate_Simples(Token TokenOrigem) {

        pularLinha();

        eDocumento += getTabulacao(mTab) + TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpo(TokenC);

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void formate_Valoramento(Token TokenOrigem) {

        pularLinha();

        eDocumento += getTabulacao(mTab) + TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {


                eDocumento += " " + TokenC.getConteudo();

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }


    public void formate_Struct(Token TokenOrigem) {

        pularLinha();

        eDocumento += TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpoStruct(TokenC);

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }


    public void dentroCorpo(Token TokenOrigem) {


        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;

        int mTabCorpo = mTab;


        pularLinha();
        mTab += 1;

        boolean fechadorOrigem = false;

        int mCorrente = mIndex;
        if (mCorrente < mQuantidade) {
            Token mTokenFuturo = LexerC.getTokens().get(mCorrente);
            if (mTokenFuturo.getTipo() == TokenTipo.CHAVE_FECHA) {
                fechadorOrigem = true;
            }
        }

        if (fechadorOrigem) {
            eDocumento += getTabulacao(mTab-1);
        } else {
            eDocumento += getTabulacao(mTab);
        }




        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                dentroCorpo(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                mTab = mTabCorpo;
                eDocumento += TokenC.getConteudo();
                pularLinha();

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento += TokenC.getConteudo();
                pularLinha();
                boolean fechador = false;

                int mFuturo = mIndex + 1;
                if (mFuturo < mQuantidade) {
                    Token mTokenFuturo = LexerC.getTokens().get(mFuturo);
                    if (mTokenFuturo.getTipo() == TokenTipo.CHAVE_FECHA) {
                        fechador = true;
                    }
                }

                if (fechador) {
                    eDocumento += getTabulacao(mTabCorpo);
                } else {
                    eDocumento += getTabulacao(mTab);
                }


            } else {

                if (TokenC.getTipo() == TokenTipo.PONTO) {
                    eDocumento += TokenC.getConteudo();
                } else {


                    boolean pontuado = false;

                    int mPassado = mIndex - 1;
                    if (mPassado < mQuantidade) {
                        Token mTokenPassado = LexerC.getTokens().get(mPassado);
                        if (mTokenPassado.getTipo() == TokenTipo.PONTO) {
                            pontuado = true;
                        }
                    }

                    if (pontuado) {
                        eDocumento += TokenC.getConteudo();
                    } else {
                        eDocumento += " " + TokenC.getConteudo();
                    }
                }


            }

            mIndex += 1;
        }


    }


    public void dentroCorpoStruct(Token TokenOrigem) {


        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;

        mTab += 1;

        int mStructTab = mTab;


        pularLinha();
        eDocumento += getTabulacao(mTab);


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                mTab = mStructTab;
                mTab -= 1;

                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();
                pularLinha();

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento += TokenC.getConteudo();
                pularLinha();
                boolean fechador = false;

                int mFuturo = mIndex + 1;
                if (mFuturo < mQuantidade) {
                    Token mTokenFuturo = LexerC.getTokens().get(mFuturo);
                    if (mTokenFuturo.getTipo() == TokenTipo.CHAVE_FECHA) {
                        fechador = true;
                    }
                }

                if (fechador) {
                    eDocumento += getTabulacao(mTab - 1);
                } else {
                    eDocumento += getTabulacao(mTab);
                }


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                formate_Simples(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                pularLinha();
                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();
                mTab = mStructTab + 1;
                dentroStructSub();
                mTab = mStructTab;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                pularLinha();
                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();
                mTab = mStructTab + 1;
                dentroStructSub();
                mTab = mStructTab;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                pularLinha();
                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();
                mTab = mStructTab + 1;
                dentroStructSub();
                mTab = mStructTab;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                pularLinha();
                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();
                mTab = mStructTab + 1;
                dentroStructSub();
                mTab = mStructTab;


            } else {

                if (TokenC.getTipo() == TokenTipo.PONTO) {
                    eDocumento += TokenC.getConteudo();
                } else {
                    eDocumento += " " + TokenC.getConteudo();
                }


            }

            mIndex += 1;
        }


    }

    public void dentroStructSub() {

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);

            } else {

                eDocumento += " " + TokenC.getConteudo();

            }

            mIndex += 1;
            break;

        }


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO) {

                eDocumento += "\n" + getTabulacao(mTab) + getComentario(TokenC) + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                formate_Simples(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                formate_Valoramento(TokenC);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                formate_Valoramento(TokenC);


            } else {
                mIndex -= 1;

                break;


            }

            mIndex += 1;
        }

    }


    public String getComentario(Token TokenC) {

        String mRet = TokenC.getConteudo();

        return mRet;
    }


    public String getTexto(Token TokenC) {

        String mRet = "";

        if (TokenC.getConteudo().contains("\"")) {
            mRet = "'" + TokenC.getConteudo() + "'";
        } else {
            mRet = "\"" + TokenC.getConteudo() + "\"";
        }

        return mRet;
    }


    private void idente_Outro(Token TokenC) {

        if (mIndex + 1 < mQuantidade) {
            Token Futuro = LexerC.getTokens().get(mIndex + 1);

            if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {
                eDocumento += TokenC.getConteudo();
            } else if (Futuro.getTipo() == TokenTipo.PONTO) {
                eDocumento += TokenC.getConteudo();
            } else {
                eDocumento += TokenC.getConteudo() + " ";
            }

        } else {
            eDocumento += TokenC.getConteudo();
        }

    }

    private void idente_DoisPontos(Token TokenC) {

        if (mIndex - 1 >= 0) {

            Token Passado = LexerC.getTokens().get(mIndex - 1);

            if (Passado.getTipo() == TokenTipo.ID && Passado.mesmoConteudo("all")) {


                eDocumento += TokenC.getConteudo() + " \n" + getTabulacao(mTab);

            } else if (Passado.getTipo() == TokenTipo.ID && Passado.mesmoConteudo("restrict")) {

                eDocumento += TokenC.getConteudo() + " \n" + getTabulacao(mTab);

            } else if (Passado.getTipo() == TokenTipo.ID && Passado.mesmoConteudo("extern")) {

                eDocumento += TokenC.getConteudo() + " \n" + getTabulacao(mTab);

            } else {
                eDocumento += TokenC.getConteudo() + " ";
            }

        } else {
            eDocumento += TokenC.getConteudo() + " ";
        }

    }

    private void idente_ChaveFecha(Token TokenC) {

        mTab -= 1;


        if (mIndex + 1 < mQuantidade) {
            Token Futuro = LexerC.getTokens().get(mIndex + 1);

            if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("others")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("other")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("act")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("struct")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);
            } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("operator")) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n\n" + getTabulacao(mTab);

            } else if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {
                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo();
            } else {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            }

        } else {

            eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

        }


    }

    private void idente_PontoEVirgula(Token TokenC) {


        boolean ja = false;
        if (mIndex - 1 > 0) {
            Token Passado = LexerC.getTokens().get(mIndex - 1);
            if (Passado.getTipo() == TokenTipo.CHAVE_FECHA) {
                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                ja = true;
            }
        }

        if (!ja) {

            if (mIndex + 1 < mQuantidade) {

                Token Futuro = LexerC.getTokens().get(mIndex + 1);

                if (Futuro.getTipo() == TokenTipo.CHAVE_FECHA) {
                    eDocumento += TokenC.getConteudo();
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("call")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("act")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("struct")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                } else if (Futuro.getTipo() == TokenTipo.ID && Futuro.mesmoConteudo("operator")) {

                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);

                } else {
                    eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
                }
            } else {
                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);
            }

        }


    }

    public String getTabulacao(int t) {
        String ret = "";

        if (t > 0) {
            for (int i = 0; i < t; i++) {
                ret += "\t";
            }
        }

        return ret;
    }


}
