package Sigmaz.S06_Montador;

public class R5Resposta {

    private boolean mOk;
    private String mConteudo;
    private  byte[] mData;

    public R5Resposta() {

        mOk = false;
        mConteudo = "";

    }

    public void anular() {
        mConteudo = "";
        mOk = false;
    }

    public void validar(String eConteudo) {
        mConteudo = eConteudo;
        mOk = true;
    }

    public void validarComData( byte[] eData) {
        mData = eData;
        mOk = true;
    }

    public boolean getOk() {
        return mOk;
    }
    public String getConteudo(){return mConteudo;}
    public  byte[] getData(){return mData;}

}
