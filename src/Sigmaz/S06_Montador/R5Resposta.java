package Sigmaz.S06_Montador;

public class R5Resposta {

    private boolean mOk;
    private String mConteudo;
    private byte[] mData;

    public R5Resposta() {

        mOk = false;

        mConteudo = "";
        mData = null;

    }

    public void anular() {

        mConteudo = "";
        mData = null;

        mOk = false;
    }

    public void validarComConteudoEData(String eConteudo,byte[] eData) {
        mConteudo = eConteudo;
        mData=eData;
        mOk = true;
    }

    public void validarComConteudo(String eConteudo) {
        mConteudo = eConteudo;
        mOk = true;
    }

    public void validarComData(byte[] eData) {
        mData = eData;
        mOk = true;
    }

    public boolean getOk() {
        return mOk;
    }

    public String getConteudo() {
        return mConteudo;
    }

    public byte[] getData() {
        return mData;
    }

}
