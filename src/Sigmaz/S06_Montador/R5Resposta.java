package Sigmaz.S06_Montador;

public class R5Resposta {

    private boolean mOk;
    private String mConteudo;

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

    public boolean getOk() {
        return mOk;
    }
    public String getConteudo(){return mConteudo;}
}
