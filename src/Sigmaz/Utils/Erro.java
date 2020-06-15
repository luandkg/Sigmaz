package Sigmaz.Utils;

public class Erro {

    private String mMensagem;
    private int mPosicao;

    public Erro(String eMensagem,int ePosicao) {
        mMensagem = eMensagem;
                mPosicao = ePosicao;

    }

    public int getPosicao() {
        return mPosicao;
    }

    public String getMensagem() {
        return mMensagem;
    }
}
