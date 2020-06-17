package Sigmaz.Executor;

public class Item {

    private String mNome;
    private boolean mNulo;
    private String mTipo;
    private int mModo;
    private String mValor;
    private boolean mPrimitivo;




    public Item(String eNome) {
        this.mNome = eNome;
        mValor = "";
        mModo = 0;
        mTipo = "";
        mNulo = false;
        mPrimitivo=true;
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

    public void setPrimitivo(boolean ePrimitivo) {
        mPrimitivo = ePrimitivo;
    }

    public boolean getPrimitivo() {
        return mPrimitivo;
    }

}