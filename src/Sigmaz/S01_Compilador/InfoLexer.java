package Sigmaz.S01_Compilador;

public class InfoLexer {

    private String mArquivo;
    private int mChars;
    private int mTokens;

    public InfoLexer(String eArquivo, int eChars, int eTokens) {
        mArquivo = eArquivo;
        mChars = eChars;
        mTokens = eTokens;
    }

    public String getArquivo() {
        return mArquivo;
    }

    public int getChars() {
        return mChars;
    }

    public int getTokens() {
        return mTokens;
    }

}
