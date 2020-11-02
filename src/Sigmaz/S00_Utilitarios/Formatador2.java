package Sigmaz.S00_Utilitarios;

import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;

public class Formatador2 {


    private String eDocumento;

    private int mQuantidade;
    private int mIndex;
    private Lexer LexerC;
    private int mLinha;

    public Formatador2() {

        mQuantidade = 0;
        mIndex = 0;
        eDocumento = "";
        LexerC = new Lexer();
        mLinha = 0;

    }


    public void identar(String eArquivoOrigem, String eArquivoDestino) {


        LexerC.init(eArquivoOrigem);

        mQuantidade = LexerC.getTokens().size();
        mIndex = 0;
        eDocumento = "";
        mLinha = 0;

        int mTab = 0;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("call")) {

                log("CALL");

                formate_Call(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                log("DEFINE");

                formate_Valor(TokenC, mTab, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                log("MOCKIZ");

                formate_Valor(TokenC, mTab, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("refer")) {

                log("REFER");

                formate_Valor(TokenC, mTab, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                log("ACT");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                log("FUNC");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("auto")) {

                log("AUTO");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("functor")) {

                log("FUNCTOR");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

                log("OPERATOR");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("default")) {

                log("DEFAULT");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("cast")) {

                log("CAST");

                formate_Cast(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("stages")) {

                log("STAGES");

                formate_Stages(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("type")) {

                log("TYPE");

                formate_Type(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("model")) {

                log("MODEL");

                formate_Model(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("struct")) {

                log("STRUCT");

                formate_Struct(mTab);


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

    public void formate_Call(Token TokenOrigem, int mTab) {

        pularLinha();

        eDocumento += TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpo(TokenC, mTab);

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

    public void formate_Simples(int mTab) {


        tudoAteIniciarEscopo(mTab);


        int mDentro = mTab + 1;

        pularLinha();
        eDocumento += getTabulacao(mDentro);

        mIndex += 1;

        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);


            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroSubCorpo(TokenC, mDentro);

                break;

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();

                break;

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("return")) {

                formate_Valor(TokenC, mDentro, false);

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void log(String e) {
        System.out.println(e);
    }

    public void formate_Cast(Token TokenOrigem, int mTab) {

        pularLinha();

        eDocumento += getTabulacao(mTab) + TokenOrigem.getConteudo();

        mIndex += 1;

        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpoCast(TokenC, mTab);

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void formate_Stages(Token TokenOrigem, int mTab) {

        pularLinha();

        eDocumento += getTabulacao(mTab) + TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpoStages(TokenC, mTab);

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void formate_Type(Token TokenOrigem, int mTab) {

        pularLinha();

        eDocumento += getTabulacao(mTab) + TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroCorpoType(TokenC, mTab);

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void tudoAteIniciarEscopo(int mTab) {

        pularLinha();
        eDocumento += getTabulacao(mTab);


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                eDocumento += TokenC.getConteudo() + " ";

                break;

            } else {

                eDocumento += TokenC.getConteudo() + " ";


            }

            mIndex += 1;

        }

    }

    public void formate_Model(int mTab) {

        tudoAteIniciarEscopo(mTab);


        int mDentro = mTab + 1;

        pularLinha();
        eDocumento += getTabulacao(mDentro);

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mDentro) + TokenC.getConteudo() + "\n" + getTabulacao(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                dentroCorpo(TokenC, mDentro);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {


                pularLinha();
                eDocumento += getTabulacao(mTab) + TokenC.getConteudo();
                pularLinha();
                eDocumento += getTabulacao(mTab);

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento += TokenC.getConteudo();

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                formate_Valor(TokenC, mDentro, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                formate_Valor(TokenC, mDentro, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                formate_Valor(TokenC, mDentro, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                formate_Valor(TokenC, mDentro, true);

            } else {
                eDocumento += " " + TokenC.getConteudo();
            }

            mIndex += 1;
        }


    }


    public void formate_Struct(int mTab) {

        tudoAteIniciarEscopo(mTab);


        int mDentro = mTab + 1;

        pularLinha();
        eDocumento += getTabulacao(mDentro);

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mDentro) + TokenC.getConteudo() + "\n" + getTabulacao(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC) + " ";


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                pularLinha();
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
                    eDocumento += getTabulacao(mTab);
                } else {
                    eDocumento += getTabulacao(mDentro);
                }


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                System.out.println("\t STRUCT INIT");
                formate_Simples(mTab+1);

                pularLinha();
                eDocumento += getTabulacao(mTab);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                System.out.println("\t STRUCT RESTRICT");

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mDentro + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                System.out.println("\t STRUCT ALL");

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mDentro + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                System.out.println("\t STRUCT EXPLICIT");

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mDentro + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                System.out.println("\t STRUCT IMPLICIT");

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mDentro + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extra")) {

                System.out.println("\t STRUCT EXTRA");

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mDentro + 1);

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


    public void dentroCorpo(Token TokenOrigem, int mTab) {


        eDocumento += " " + TokenOrigem.getConteudo() + " ";


        mIndex += 1;

        int mTabFora = mTab;
        int mTabDentro = mTab + 1;

        pularLinha();


        log(getTabulacao(mTab) + "ICORPO : " + mTab);


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            System.out.println(TokenC.getConteudo());

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTabDentro) + TokenC.getConteudo() + "\n" + getTabulacao(mTabDentro);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTabDentro);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroSubCorpo(TokenC, mTabDentro);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento += getTabulacao(mTabFora) + TokenC.getConteudo();
                pularLinha();
                eDocumento += getTabulacao(mTabFora);

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                if (isFuturo(TokenTipo.CHAVE_FECHA)) {

                    eDocumento += TokenC.getConteudo();
                    pularLinha();
                    eDocumento += getTabulacao(mTabFora);

                } else {

                    eDocumento += TokenC.getConteudo();
                    pularLinha();
                    eDocumento += getTabulacao(mTabDentro);

                }

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("def")) {

                formate_Valor(TokenC, mTabDentro, false);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("moc")) {

                formate_Valor(TokenC, mTabDentro, false);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("let")) {

                formate_Valor(TokenC, mTabDentro, false);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("voz")) {

                formate_Valor(TokenC, mTabDentro, false);
            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mut")) {

                formate_Valor(TokenC, mTabDentro, false);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("each")) {

                formate_Simples(mTabDentro);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("return")) {

                System.out.println("RETURN");

                formate_Valor(TokenC, mTabDentro, false);

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


                        formate_Valor(TokenC, mTabDentro, false);


                    }
                }


            }

            mIndex += 1;
        }


    }

    public void dentroSubCorpo(Token TokenOrigem, int mTab) {


        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;

        int mFora = mTab;
        int mDentro = mTab + 1;


        pularLinha();

        eDocumento += getTabulacao(mDentro);

        log(getTabulacao(mTab) + "SUB_CORPO : " + mDentro);


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mDentro) + TokenC.getConteudo() + "\n" + getTabulacao(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mDentro);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {


                dentroSubCorpo(TokenC, mDentro);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                pularLinha();
                eDocumento += getTabulacao(mFora) + TokenC.getConteudo() + "\n" + getTabulacao(mFora);

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                if (isFuturo(TokenTipo.CHAVE_FECHA)) {

                    eDocumento += TokenC.getConteudo();
                    eDocumento += "\n" + getTabulacao(mFora);

                } else {

                    eDocumento += TokenC.getConteudo();
                    pularLinha();
                    eDocumento += getTabulacao(mDentro);


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
                        if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("def")) {

                            formate_Valor(TokenC, mDentro, false);

                        } else {
                            eDocumento += " " + TokenC.getConteudo();
                        }
                    }
                }


            }

            mIndex += 1;
        }


    }


    public boolean isFuturo(TokenTipo eTokenTipo) {

        boolean pontuado = false;

        int mFuturo = mIndex + 1;
        if (mFuturo < mQuantidade) {
            Token mTokenPassado = LexerC.getTokens().get(mFuturo);
            if (mTokenPassado.getTipo() == eTokenTipo) {
                pontuado = true;
            }
        }

        return pontuado;

    }

    public void dentroCorpoStages(Token TokenOrigem, int mTab) {


        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;

        int mTabAntes = mTab;
        int mTabCorpo = mTab;


        pularLinha();
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
            eDocumento += getTabulacao(mTab - 1);
        } else {
            eDocumento += getTabulacao(mTab);
        }


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                dentroCorpo(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                pularLinha();
                eDocumento += getTabulacao(mTabAntes) + TokenC.getConteudo();
                pularLinha();

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento += TokenC.getConteudo();


            } else {
                eDocumento += " " + TokenC.getConteudo();
            }

            mIndex += 1;
        }


        mTab = mTabAntes;

    }

    public void dentroCorpoType(Token TokenOrigem, int mTab) {


        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;

        int mTabAntes = mTab;

        int mTabCorpo = mTab + 1;


        pularLinha();
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
            eDocumento += getTabulacao(mTab - 1);
        } else {
            eDocumento += getTabulacao(mTab);
        }


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTabCorpo) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTabCorpo);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                dentroCorpo(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {


                pularLinha();
                eDocumento += getTabulacao(mTabAntes) + TokenC.getConteudo();
                pularLinha();
                eDocumento += getTabulacao(mTabAntes);

                break;

            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                eDocumento += TokenC.getConteudo();

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                formate_Valor(TokenC, mTab, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                formate_Valor(TokenC, mTab, true);

            } else {
                eDocumento += " " + TokenC.getConteudo();
            }

            mIndex += 1;
        }

        mTab = mTabAntes;

    }


    public void dentroCorpoCast(Token TokenOrigem, int mTab) {

        log("\tCORPO CAST");

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
            eDocumento += getTabulacao(mTab - 1);
        } else {
            eDocumento += getTabulacao(mTab);
        }


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("getter")) {

                log("\tCAST GETTER");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("setter")) {

                log("\tCAST SETTER");

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("default")) {

                log("\tCAST DEFAULT");

                formate_Simples(mTab);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                dentroCorpo(TokenC, mTab);

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
                        if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("def")) {

                            formate_Valor(TokenC, mTab, false);

                        } else {
                            eDocumento += " " + TokenC.getConteudo();
                        }
                    }
                }


            }

            mIndex += 1;
        }


    }


    public void formate_Valor(Token TokenOrigem, int mTab, boolean isPular) {

        if (isPular) {
            pularLinha();
            eDocumento += getTabulacao(mTab);
        }

        eDocumento += TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {


                eDocumento += " " + TokenC.getConteudo();
                eDocumento += "\n" + getTabulacao(mTab);

                break;

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                formate_ValorChaves(TokenC, mTab);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento += " " + TokenC.getConteudo();

                break;

            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }

    public void formate_ValorChaves(Token TokenOrigem, int mTab) {

        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += " " + getTexto(TokenC);

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                eDocumento += " " + TokenC.getConteudo();
                break;

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                formate_ValorChaves(TokenC, mTab);


            } else {

                eDocumento += " " + TokenC.getConteudo();


            }

            mIndex += 1;

        }


    }


    public void dentroCorpoStruct(Token TokenOrigem, int mTab) {


        eDocumento += " " + TokenOrigem.getConteudo();

        mIndex += 1;


        int mStructTab = mTab + 1;
        int mTabFora = mTab;


        pularLinha();
        eDocumento += getTabulacao(mStructTab);


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mStructTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {


                eDocumento += getTabulacao(mTabFora) + TokenC.getConteudo();
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
                    eDocumento += getTabulacao(mTabFora);
                } else {
                    eDocumento += getTabulacao(mStructTab);
                }


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("init")) {

                formate_Simples(mStructTab);

                pularLinha();
                eDocumento += getTabulacao(mTab);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("restrict")) {

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mStructTab + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("all")) {

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mStructTab + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("explicit")) {

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mStructTab + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("implicit")) {

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mStructTab + 1);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("extra")) {

                pularLinha();
                eDocumento += getTabulacao(mTab + 1) + TokenC.getConteudo();
                dentroStructSub(mStructTab + 1);

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

    public void dentroStructSub(int mTab) {

        mIndex += 1;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);

            } else {

                eDocumento += " " + TokenC.getConteudo();

            }

            mIndex += 1;
            break;

        }

        int mAntes = mTab;


        while (mIndex < mQuantidade) {
            Token TokenC = LexerC.getTokens().get(mIndex);

            if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                eDocumento += "\n" + getTabulacao(mTab) + TokenC.getConteudo() + "\n" + getTabulacao(mTab);

            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                eDocumento += " " + TokenC.getConteudo() + "\n";

            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                eDocumento += getTexto(TokenC);


            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("act")) {

                mTab = mAntes;

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("func")) {

                System.out.println("\t\t\tFUNC");

                mTab = mAntes;

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("define")) {

                mTab = mAntes;

                formate_Valor(TokenC, mTab, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("mockiz")) {

                mTab = mAntes;

                formate_Valor(TokenC, mTab, true);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("operator")) {

                mTab = mAntes;

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("director")) {

                mTab = mAntes;

                formate_Simples(mTab);

            } else if (TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("destruct")) {

                mTab = mAntes;

                formate_Simples(mTab);

            } else {
                mIndex -= 1;

                break;


            }

            mIndex += 1;
        }

        mTab = mAntes;

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
