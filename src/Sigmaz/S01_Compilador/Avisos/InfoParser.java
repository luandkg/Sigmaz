package Sigmaz.S01_Compilador.Avisos;

public class InfoParser {

    private String mArquivo;
    private int mTokens;
    private int mASTS;

    public InfoParser(String eArquivo, int eTokens, int eASTS) {
        mArquivo = eArquivo;
        mTokens = eTokens;
        mASTS = eASTS;

    }

    public String getArquivo() {
        return mArquivo;
    }

    public int getASTS() {
        return mASTS;
    }

    public int getTokens() {
        return mTokens;
    }

}
