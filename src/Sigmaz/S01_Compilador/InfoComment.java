package Sigmaz.S01_Compilador;

public class InfoComment {

    private String mArquivo;
    private int mTokens;
    private int mComentarios;

    public InfoComment(String eArquivo, int eTokens, int eComentarios) {
        mArquivo = eArquivo;
        mTokens = eTokens;
        mComentarios = eComentarios;

    }

    public String getArquivo() {
        return mArquivo;
    }

    public int getComentarios() {
        return mComentarios;
    }

    public int getTokens() {
        return mTokens;
    }

}
