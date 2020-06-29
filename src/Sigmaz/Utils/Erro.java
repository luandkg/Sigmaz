package Sigmaz.Utils;

public class Erro {

    private String mMensagem;

    private int mLinha;
    private int mPosicao;

    public Erro(String eMensagem, int eLinha, int ePosicao) {
        mMensagem = eMensagem;
        mLinha = eLinha;
        mPosicao = ePosicao;

    }

    public int getPosicao() {
        return mPosicao;
    }

    public int getLinha() {
        return mLinha;
    }

    public String getMensagem() {
        return mMensagem;
    }
}
