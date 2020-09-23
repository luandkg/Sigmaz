package AppSigmaz;

public class Opcional {

    private boolean mValido;
    private String mConteudo;

    public Opcional() {
        mValido = false;
        mConteudo = "";
    }

    public void validar(String eConteudo) {
        mValido = true;
        mConteudo = eConteudo;
    }

    public boolean estaValido() {
        return mValido;
    }

    public String getConteudo() {
        return mConteudo;
    }
}
