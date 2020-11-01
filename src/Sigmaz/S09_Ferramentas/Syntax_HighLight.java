package Sigmaz.S09_Ferramentas;

import Sigmaz.S02_Lexer.Lexer;
import Sigmaz.S02_Lexer.Token;
import Sigmaz.S02_Lexer.TokenTipo;
import Sigmaz.S00_Utilitarios.Erro;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class Syntax_HighLight {

    private ArrayList<String> mReservadas;
    private ArrayList<String> mReservadas_Grupo2;

    private ArrayList<String> mComandos;
    private ArrayList<String> mPrimitivos;
    private ArrayList<String> mEspeciais;
    private ArrayList<String> mInvocadores;


    private SyntaxTheme mSyntaxTheme;

    private boolean mInvocando;

    public void setSyntaxTheme(SyntaxTheme eSyntaxTheme) {
        mSyntaxTheme = eSyntaxTheme;
    }

    public SyntaxTheme getSyntaxTheme() {
        return mSyntaxTheme;
    }

    public Syntax_HighLight() {


        mSyntaxTheme = new SyntaxTheme();


        mReservadas = new ArrayList<String>();

        mReservadas.add("act");
        mReservadas.add("func");
        mReservadas.add("operator");
        mReservadas.add("director");

        mReservadas.add("type");
        mReservadas.add("cast");

        mReservadas.add("stages");
        mReservadas.add("struct");
        mReservadas.add("model");
        mReservadas.add("external");
        mReservadas.add("package");

        mReservadas.add("refer");
        mReservadas.add("call");
        mReservadas.add("import");
        mReservadas.add("require");


        mReservadas.add("auto");
        mReservadas.add("functor");


        mReservadas.add("define");
        mReservadas.add("mockiz");
        mReservadas.add("default");

        mReservadas.add("in");
        mReservadas.add("with");
        mReservadas.add("at");

        mReservadas_Grupo2 = new ArrayList<String>();
        mReservadas_Grupo2.add("init");
        mReservadas_Grupo2.add("start");
        mReservadas_Grupo2.add("all");
        mReservadas_Grupo2.add("restrict");
        mReservadas_Grupo2.add("implicit");
        mReservadas_Grupo2.add("extern");

        mReservadas_Grupo2.add("getter");
        mReservadas_Grupo2.add("setter");

        mComandos = new ArrayList<String>();
        mComandos.add("def");
        mComandos.add("moc");
        mComandos.add("let");
        mComandos.add("voz");
        mComandos.add("mut");
        mComandos.add("if");
        mComandos.add("other");
        mComandos.add("others");
        mComandos.add("case");

        mComandos.add("while");
        mComandos.add("step");
        mComandos.add("daz");
        mComandos.add("return");
        mComandos.add("loop");
        mComandos.add("change");
        mComandos.add("using");
        mComandos.add("exception");
        mComandos.add("cancel");
        mComandos.add("continue");
        mComandos.add("local");

        mPrimitivos = new ArrayList<String>();
        mPrimitivos.add("int");
        mPrimitivos.add("num");
        mPrimitivos.add("bool");
        mPrimitivos.add("string");


        mEspeciais = new ArrayList<String>();
        mEspeciais.add("null");
        mEspeciais.add("true");
        mEspeciais.add("false");

        mInvocadores = new ArrayList<String>();
        mInvocadores.add("invoke");
        mInvocadores.add("__COMPILER__");
        mInvocadores.add("STACK");

    }


    private class Sintaxando {

        public int eFonte_EntreLinhas = 0;

        public int eLinha = 0;
        public int ePos = 0;
        public int mTab = 0;
        public int mIndex = 0;

        public int mQuantidade = 0;
        public int eFonte_Tamanho = 0;
        public Font eFonte = null;

    }

    public void init(String eArquivoOrigem, String eLocal) {


        Lexer LexerC = new Lexer();

        String DI = LexerC.getData();

        LexerC.init(eArquivoOrigem);

        String DF = LexerC.getData();

        System.out.println("");
        System.out.println("################# SYNTAX HIGHT-LIGHT ##################");
        System.out.println("");
        System.out.println("\t Iniciado : " + DI);
        System.out.println("\t - Origem : " + eArquivoOrigem);
        System.out.println("\t - Chars : " + LexerC.getChars());
        System.out.println("\t - Tokens : " + LexerC.getTokens().size());
        System.out.println("\t - Erros : " + LexerC.getErros().size());
        System.out.println("\t Finalizado : " + DF);
        System.out.println("");


        if (LexerC.getErros().size() > 0) {

            System.out.println("\n\t ERROS DE LEXICOGRAFICA : ");

            for (Erro eErro : LexerC.getErros()) {
                System.out.println("\t\t    ->> " + eErro.getPosicao() + " : " + eErro.getMensagem());
            }

        } else {

            System.out.println("\t HIGH-LIGHTING");
            System.out.println("\t - Inicio : " + LexerC.getData());
            System.out.println("\t - Destino : " + eLocal);


            highlight(eArquivoOrigem, eLocal);

            System.out.println("\t - Fim : " + LexerC.getData());
            System.out.println("");

            System.out.println("################# ##### ##################");

        }

    }

    public boolean highlight_simples(String eArquivoOrigem, String eArquivoDestino) {
        boolean ret = false;

        Lexer LexerC = new Lexer();


        LexerC.init(eArquivoOrigem);
        if (LexerC.getErros().size() == 0) {
            highlight(eArquivoOrigem, eArquivoDestino);
            ret = true;
        }


        return ret;

    }


    public void highlight(String eArquivoOrigem, String eLocal) {


        Lexer LexerC = new Lexer();

        LexerC.init(eArquivoOrigem);

        int eLinhas = LexerC.getLinhas();

        int eLinha_MenorTamanho = LexerC.getLinha_Min();
        int eLinha_MaiorTamanho = LexerC.getLinha_Max();


        String eTitulo = new File(eArquivoOrigem).getName();


        Sintaxando mSintaxando = new Sintaxando();
        mSintaxando.mIndex = 0;
        mSintaxando.mTab = 0;
        mSintaxando.eLinha = 0;
        mSintaxando.ePos = 0;
        mSintaxando.mQuantidade = LexerC.getTokens().size();
        mSintaxando.eFonte_Tamanho = 12;
        mSintaxando.eFonte_EntreLinhas = 20;
        mSintaxando.eFonte = new Font("TimesRoman", Font.BOLD, mSintaxando.eFonte_Tamanho);


        int IMAGEM_LARGURA = 100;
        int IMAGEM_ALTURA = 300;

        if (eLinhas > 0) {

            IMAGEM_ALTURA = (eLinhas + (eLinhas / 10)) * mSintaxando.eFonte_EntreLinhas;

            if (eLinha_MaiorTamanho > 0) {
                IMAGEM_LARGURA = (eLinha_MaiorTamanho + 5) * mSintaxando.eFonte_Tamanho;
            }
        }

        //  System.out.println("\tLinhas : " + eLinhas);
        //  System.out.println("\tMenor Coluna : " + eLinha_MenorTamanho);
        //   System.out.println("\tMaior Coluna : " + eLinha_MaiorTamanho);
        //   System.out.println("\tLargura : " + IMAGEM_LARGURA);
        //   System.out.println("\tAltura : " + IMAGEM_ALTURA);


        BufferedImage img = new BufferedImage(IMAGEM_LARGURA, IMAGEM_ALTURA, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        g.setColor(mSyntaxTheme.getSyntax_Fundo());
        g.fillRect(0, 0, IMAGEM_LARGURA, IMAGEM_ALTURA);


        g.setColor(mSyntaxTheme.getSyntax_Comum());

        mInvocando = false;


        ArrayList<String> emFuturoProximo = new ArrayList<String>();
        emFuturoProximo.add("call");
        emFuturoProximo.add("act");
        emFuturoProximo.add("struct");
        emFuturoProximo.add("operator");

        ArrayList<String> emFuturoEspacar = new ArrayList<String>();
        emFuturoEspacar.add("other");
        emFuturoEspacar.add("others");

        ArrayList<String> emFuturoQuebrarLinha = new ArrayList<String>();
        emFuturoQuebrarLinha.add("act");
        emFuturoQuebrarLinha.add("struct");
        emFuturoQuebrarLinha.add("operator");
        emFuturoQuebrarLinha.add("director");

        ArrayList<String> emPassadoDoisPontos = new ArrayList<String>();
        emPassadoDoisPontos.add("all");
        emPassadoDoisPontos.add("restrict");
        emPassadoDoisPontos.add("extern");
        emPassadoDoisPontos.add("implicit");

        Color mInocandoCorrente = mSyntaxTheme.getSyntax_Invocadores();

        for (Token TokenC : LexerC.getTokens()) {

            Color eSyntax_Corrente = getSyntaxCor(TokenC);

            // PERSONALIZAR COR PARA INVOKE
            if (mInvocando == false && TokenC.getTipo() == TokenTipo.ID && TokenC.mesmoConteudo("invoke")) {
                mInvocando = true;
                eSyntax_Corrente = mSyntaxTheme.getSyntax_Invocadores();
                mInocandoCorrente = mSyntaxTheme.getSyntax_Invocadores();
            } else {

                if (mInvocando) {
                    if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {
                        mInvocando = false;
                    } else if (TokenC.getTipo() == TokenTipo.ID) {
                        eSyntax_Corrente = mInocandoCorrente;
                    } else if (TokenC.getTipo() == TokenTipo.QUAD) {
                        mInocandoCorrente = mSyntaxTheme.getSyntax_Invocadores_Saida();
                    } else {

                    }
                }


            }


            //  System.out.println(" -->> " + mSintaxando.eLinha + "::" + mSintaxando.ePos + " T : " + mSintaxando.mTab + " :: " + TokenC.getConteudo() + " " + mInvocando);

            if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {

                emPontoEVirgula(LexerC, mSintaxando, TokenC, emFuturoProximo, g, eSyntax_Corrente);


            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_LINHA) {

                mSintaxando.ePos += g.getFontMetrics().stringWidth(" ");

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, mSyntaxTheme.getSyntax_Comentario());


            } else if (TokenC.getTipo() == TokenTipo.COMENTARIO_BLOCO) {

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, mSyntaxTheme.getSyntax_Comentario());

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {

                mSintaxando.mTab += 1;
                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);


                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

            } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {

                mSintaxando.mTab -= 1;

                mSintaxando.eLinha += 1;

                emChaveFecha(LexerC, mSintaxando, TokenC, emFuturoEspacar, emFuturoQuebrarLinha, g, eSyntax_Corrente);


            } else if (TokenC.getTipo() == TokenTipo.TEXTO) {

                String eConteudo = "\"" + TokenC.getConteudo() + "\"";

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, eConteudo.length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), eConteudo, mSintaxando.eFonte, mSyntaxTheme.getSyntax_Texto());

                mSintaxando.ePos += g.getFontMetrics().stringWidth(eConteudo);

            } else if (TokenC.getTipo() == TokenTipo.PONTO) {

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(TokenC.getConteudo());

            } else if (TokenC.getTipo() == TokenTipo.DOISPONTOS) {

                emDoisPontos(LexerC, mSintaxando, TokenC, emPassadoDoisPontos, g, eSyntax_Corrente);

            } else {

                emOutro(LexerC, mSintaxando, TokenC, g, eSyntax_Corrente);


            }

            mSintaxando.mIndex += 1;

        }


        try {
            ImageIO.write(img, "png", new File(eLocal + eTitulo + ".png"));
        } catch (IOException e) {

        }


    }

    public void emPontoEVirgula(Lexer LexerC, Sintaxando mSintaxando, Token TokenC, ArrayList<String> emFuturoProximo, Graphics g, Color eSyntax_Corrente) {

        boolean ja = false;
        if (mSintaxando.mIndex - 1 > 0) {
            Token Passado = LexerC.getTokens().get(mSintaxando.mIndex - 1);
            if (Passado.getTipo() == TokenTipo.CHAVE_FECHA) {


                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);


                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

                ja = true;
            }
        }

        if (!ja) {

            if (mSintaxando.mIndex + 1 < mSintaxando.mQuantidade) {

                Token Futuro = LexerC.getTokens().get(mSintaxando.mIndex + 1);

                if (Futuro.getTipo() == TokenTipo.CHAVE_FECHA) {

                    String eConteudo = TokenC.getConteudo() + " ";

                    leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, eConteudo.length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), eConteudo, mSintaxando.eFonte, eSyntax_Corrente);

                    mSintaxando.ePos += g.getFontMetrics().stringWidth(eConteudo);

                } else if (Futuro.getTipo() == TokenTipo.ID && emFuturoProximo.contains(Futuro.getConteudo())) {

                    leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                    mSintaxando.eLinha += 1;
                    mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));


                } else {
                    leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                    mSintaxando.eLinha += 1;
                    mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

                }
            } else {
                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

            }

        }

    }

    public void emChaveFecha(Lexer LexerC, Sintaxando mSintaxando, Token TokenC, ArrayList<String> emFuturoEspacar, ArrayList<String> emFuturoQuebrarLinha, Graphics g, Color eSyntax_Corrente) {


        if (mSintaxando.mIndex + 1 < mSintaxando.mQuantidade) {
            Token Futuro = LexerC.getTokens().get(mSintaxando.mIndex + 1);

            if (Futuro.getTipo() == TokenTipo.ID && emFuturoEspacar.contains(Futuro.getConteudo())) {

                String eConteudo = TokenC.getConteudo() + " ";

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, eConteudo.length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), eConteudo, mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(eConteudo);

            } else if (Futuro.getTipo() == TokenTipo.ID && emFuturoQuebrarLinha.contains(Futuro.getConteudo())) {

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));


            } else if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(TokenC.getConteudo());

            } else {

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));


                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));

            }

        } else {

            mSintaxando.eLinha += 1;
            mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));


            leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

            mSintaxando.ePos += g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));


        }


    }

    public void emDoisPontos(Lexer LexerC, Sintaxando mSintaxando, Token TokenC, ArrayList<String> emPassadoDoisPontos, Graphics g, Color eSyntax_Corrente) {

        if (mSintaxando.mIndex - 1 >= 0) {
            Token Passado = LexerC.getTokens().get(mSintaxando.mIndex - 1);
            if (Passado.getTipo() == TokenTipo.ID && emPassadoDoisPontos.contains(Passado.getConteudo())) {


                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.eLinha += 1;
                mSintaxando.ePos = g.getFontMetrics().stringWidth(getTabulacao(mSintaxando.mTab));


            } else {
                String eConteudo = TokenC.getConteudo() + " ";

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, eConteudo.length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), eConteudo, mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(eConteudo);

            }

        } else {

            String eConteudo = TokenC.getConteudo() + " ";

            leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, eConteudo.length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), eConteudo, mSintaxando.eFonte, eSyntax_Corrente);

            mSintaxando.ePos += g.getFontMetrics().stringWidth(eConteudo);

        }
    }

    public void emOutro(Lexer LexerC, Sintaxando mSintaxando, Token TokenC, Graphics g, Color eSyntax_Corrente) {

        if (mSintaxando.mIndex + 1 < mSintaxando.mQuantidade) {
            Token Futuro = LexerC.getTokens().get(mSintaxando.mIndex + 1);
            if (Futuro.getTipo() == TokenTipo.PONTOVIRGULA) {


                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(TokenC.getConteudo());


            } else if (Futuro.getTipo() == TokenTipo.PONTO) {

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(TokenC.getConteudo());

            } else {

                String eConteudo = TokenC.getConteudo() + " ";

                leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, eConteudo.length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), eConteudo, mSintaxando.eFonte, eSyntax_Corrente);

                mSintaxando.ePos += g.getFontMetrics().stringWidth(eConteudo);

            }
        } else {

            leftString(g, new Rectangle(mSintaxando.ePos, mSintaxando.eLinha * mSintaxando.eFonte_EntreLinhas, TokenC.getConteudo().length() * mSintaxando.eFonte_Tamanho, mSintaxando.eFonte_EntreLinhas), TokenC.getConteudo(), mSintaxando.eFonte, eSyntax_Corrente);
            mSintaxando.ePos += g.getFontMetrics().stringWidth(TokenC.getConteudo());

        }

    }

    public Color getSyntaxCor(Token TokenC) {

        String mCorrente = TokenC.getConteudo();
        Color eSyntax_Corrente = mSyntaxTheme.getSyntax_Comum();

        if (TokenC.getTipo() == TokenTipo.PARENTESES_FECHA) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.PARENTESES_ABRE) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.CHAVE_FECHA) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.CHAVE_ABRE) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.VIRGULA) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.PONTOVIRGULA) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.DOISPONTOS) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.MAIOR) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.MENOR) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.SETA) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.SOMADOR) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.DIMINUIDOR) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.QUAD) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Delimitadores();
        } else if (TokenC.getTipo() == TokenTipo.NUMERO) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Numero();

        } else if (TokenC.getTipo() == TokenTipo.NUMERO_FLOAT) {
            eSyntax_Corrente = mSyntaxTheme.getSyntax_Numero();

        } else {

            if (mReservadas.contains(mCorrente)) {

                eSyntax_Corrente = mSyntaxTheme.getSyntax_Reservado();
            } else if (mReservadas_Grupo2.contains(mCorrente)) {

                eSyntax_Corrente = mSyntaxTheme.getSyntax_Reservado_Grupo2();

            } else if (mComandos.contains(mCorrente)) {

                eSyntax_Corrente = mSyntaxTheme.getSyntax_Comando();

            } else if (mPrimitivos.contains(mCorrente)) {

                eSyntax_Corrente = mSyntaxTheme.getSyntax_Primitivo();
            } else if (mEspeciais.contains(mCorrente)) {

                eSyntax_Corrente = mSyntaxTheme.getSyntax_Especial();
            } else if (mInvocadores.contains(mCorrente)) {

                //   eSyntax_Corrente = mSyntax_Invocadores;

            }

        }


        return eSyntax_Corrente;
    }

    public void leftString(Graphics g, Rectangle r, String s, Font font, Color eCor) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;


        g.setFont(font);
        g.setColor(eCor);

        g.drawString(s, r.x + 20, r.y + b);
    }


    public String getTabulacao(int t) {
        String ret = "";

        if (t > 0) {
            for (int i = 0; i < t; i++) {
                // ret += "\t";
                ret += "           ";
            }
        }

        return ret;
    }


    public String getData() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

    }

}
