package Sigmaz.S03_Parser;

import java.io.File;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.Erro;
import Sigmaz.S00_Utilitarios.Texto;
import Sigmaz.S00_Utilitarios.Tempo;

public class Lexer {

    private String mConteudo;
    private int mIndex;
    private int mTamanho;
    private int mLinha;
    private int mPosicao;

    private final String ALFA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ_abcdefghijklmnopqrstuvwxyz";
    private final String NUM = "0123456789";

    private final String MAIS = "+";
    private final String MENOS = "-";
    private final String ASTERISCO = "*";
    private final String BARRA = "/";
    private final String PONTO = ".";
    private final String PONTO_E_VIRGULA = ";";
    private final String DOIS_PONTOS = ":";
    private final String MAIOR = ">";
    private final String MENOR = "<";
    private final String CERQUILHA = "#";
    private final String EXCLAMACAO = "!";
    private final String IGUAL = "=";
    private final String VIRGULA = ",";

    private final String PARENTESES_ABRE = "(";
    private final String PARENTESES_FECHA = ")";

    private final String CHAVE_ABRE = "{";
    private final String CHAVE_FECHA = "}";

    private final String COLCHETE_ABRE = "[";
    private final String COLCHETE_FECHA = "]";

    private final String ARROBA = "@";

    private final String ASPAS_SIMPLES = "'";
    private final String ASPAS_DUPLA = "\"";

    private final String ESPACO = " ";
    private final String TABULACAO = "\t";
    private final String LINHA = "\n";

    private ArrayList<Token> mTokens;
    private ArrayList<Erro> mErros;

    private int mLinhas;
    private int mLinha_Max;
    private int mLinha_Min;

    public Lexer() {

        mConteudo = "";
        mIndex = 0;
        mTamanho = 0;
        mLinha = 0;
        mPosicao = 0;
        mTokens = new ArrayList<>();

        mLinhas = 0;
        mLinha_Min = 0;
        mLinha_Max = 0;

    }

    public int getLinhas() {
        return mLinhas;
    }

    public int getLinha_Min() {
        return mLinha_Min;
    }

    public int getLinha_Max() {
        return mLinha_Max;
    }


    public boolean Continuar() {
        return mIndex < mTamanho;
    }

    public boolean TemProximo() {
        return (mIndex + 1 < mTamanho);
    }

    public ArrayList<Token> getTokens() {
        return mTokens;
    }

    public ArrayList<Erro> getErros() {
        return mErros;
    }

    public int getChars() {
        return mConteudo.length();
    }

    public void errar(String eMensagem, int eLinha, int ePosicao) {
        mErros.add(new Erro(eMensagem, eLinha, ePosicao));
    }


    public boolean igual(String a, String b) {
        return a.contentEquals(b);
    }


    public void init(String eArquivo) {

        File arq = new File(eArquivo);


        mConteudo = "";
        mIndex = 0;
        mTamanho = 0;
        mLinha = 1;
        mPosicao = 0;
        mTokens.clear();

        mLinhas = 0;
        mLinha_Min = -1;
        mLinha_Max = -1;

        mErros = new ArrayList<>();

        if (arq.exists()) {
            mConteudo = Texto.Ler(eArquivo);
        } else {
            errar("Arquivo nao encontrado : " + eArquivo, 0, 0);
        }

        mTamanho = mConteudo.length();

        if (mTamanho == 0) {
            return;
        }

        if (mConteudo == null) {
            return;
        }


        Tokenizador mTokenizador = new Tokenizador();
        mTokenizador.init(this);

        while (Continuar()) {

            String charC = String.valueOf(mConteudo.charAt(mIndex));


            if (ALFA.contains(charC)) {

                mTokens.add(mTokenizador.getIdentificador());

            } else if (NUM.contains(charC)) {

                mTokens.add(mTokenizador.getNumero());

            } else if (igual(charC,MAIS)) {

                mTokens.add(mTokenizador.getMais());

            } else if (igual(charC,MENOS)) {

                mTokens.add(mTokenizador.getMenos());

            } else if (igual(charC,ASTERISCO)) {

                mTokens.add(mTokenizador.getAsterisco());

            } else if (igual(charC,BARRA)) {

                mTokens.add(mTokenizador.getBarra());

            } else if (igual(charC,PONTO)) {

                mTokens.add(mTokenizador.getPonto());

            } else if (igual(charC,DOIS_PONTOS)) {

                mTokens.add(mTokenizador.getDoisPontos());

            } else if (igual(charC,VIRGULA)) {

                mTokens.add(mTokenizador.getVirgula());

            } else if (igual(charC,IGUAL)) {

                mTokens.add(mTokenizador.getIgual());

            } else if (igual(charC,EXCLAMACAO)) {

                mTokens.add(mTokenizador.getExclamacao());

            } else if (igual(charC,MENOR)) {

                mTokens.add(mTokenizador.getMenor());

            } else if (igual(charC,MAIOR)) {

                mTokens.add(mTokenizador.getMaior());

            } else if (igual(charC,CERQUILHA)) {

                mTokens.add(mTokenizador.getComentario());

            } else if (igual(charC,ASPAS_DUPLA)) {

                mTokens.add(mTokenizador.getTextoDuplo());

            } else if (igual(charC,ASPAS_SIMPLES)) {

                mTokens.add(mTokenizador.getTextoSimples());

            } else if (igual(charC,CHAVE_ABRE)) {

                mTokens.add(mTokenizador.getChave_Abre());

            } else if (igual(charC,CHAVE_FECHA)) {

                mTokens.add(mTokenizador.getChave_Fecha());

            } else if (igual(charC,COLCHETE_ABRE)) {

                mTokens.add(mTokenizador.getColchete_Abre());

            } else if (igual(charC,COLCHETE_FECHA)) {

                mTokens.add(mTokenizador.getColchete_Fecha());

            } else if (igual(charC,PONTO_E_VIRGULA)) {

                mTokens.add(mTokenizador.getPontoEVirgula());

            } else if (igual(charC,PARENTESES_ABRE)) {

                mTokens.add(mTokenizador.getParenteses_Abre());

            } else if (igual(charC,PARENTESES_FECHA)) {

                mTokens.add(mTokenizador.getParenteses_Fecha());

            } else if (igual(charC,ARROBA)) {

                mTokens.add(mTokenizador.getArroba());


            } else if (igual(charC,LINHA)) {

                descerLinha();

            } else if (igual(charC,TABULACAO)) {

            } else if (igual(charC,ESPACO)) {

            } else {

                errar("Lexema Desconhecido : " + charC, mLinha, mPosicao);

            }

            mIndex += 1;
            mPosicao += 1;

            organizar();

        }

        mLinhas = mLinha;


    }


    public void organizar() {

        if (mLinha_Min == -1) {
            mLinha_Min = mPosicao;
        } else {
            if (mPosicao < mLinha_Min) {
                mLinha_Min = mPosicao;
            }
        }
        if (mLinha_Max == -1) {
            mLinha_Max = mPosicao;
        } else {
            if (mPosicao > mLinha_Max) {
                mLinha_Max = mPosicao;
            }
        }

    }


    public void avancar() {

        mIndex += 1;
        mPosicao += 1;

    }

    public void voltar() {

        mIndex -= 1;
        mPosicao -= 1;

    }

    public int getPosicao() {
        return mPosicao;
    }

    public int getLinha() {
        return mLinha;
    }

    public String getCorrente() {
        return String.valueOf(mConteudo.charAt(mIndex));
    }

    public String getFuturo() {
        return String.valueOf(mConteudo.charAt(mIndex + 1));
    }

    public void descerLinha() {

        mLinha += 1;
        mPosicao = 0;

    }

    public String getData() {
        return Tempo.getData();
    }

}
