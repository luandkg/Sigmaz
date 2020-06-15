package Sigmaz.Executor;

public class Item {
    private String mNome;

    private String mValor;
    private int mModo;
    private String mTipo;
    private boolean mNulo;

    public Item(String eNome) {
        this.mNome = eNome;
        mValor = "";
        mModo = 0;
        mTipo = "";
        mNulo = false;
    }

    public boolean getNulo() {
        return mNulo;
    }

    public void setNulo(boolean eNulo) {
        mNulo = eNulo;
    }

    public void setModo(int eModo) {
        mModo = eModo;
    }

    public int getModo() {
        return mModo;
    }

    public void setValor(String eValor) {
        mValor = eValor;
    }

    public String getValor() {
        return mValor;
    }

    public String getNome() {
        return mNome;
    }

    public void setTipo(String eTipo) {
        mTipo = eTipo;
    }

    public String getTipo() {
        return mTipo;
    }


}