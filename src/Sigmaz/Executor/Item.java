package Sigmaz.Executor;

public class Item {

    private String mNome;
    private boolean mNulo;
    private String mTipo;
    private int mModo;
    private boolean mPrimitivo;
    private boolean mEstrutura;

    private String mValor;
    private Escopo mObjeto;



    public Item(String eNome) {
        this.mNome = eNome;
        mValor = "";
        mModo = 0;
        mTipo = "";
        mNulo = false;
        mPrimitivo=true;
        mEstrutura=false;
        mObjeto=null;
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

    public void setIsEstrutura(boolean eEstrutura) {
        mEstrutura = eEstrutura;
    }

    public boolean getIsEstrutura() {
        return mEstrutura;
    }

    public Escopo getObjeto() {
        return mObjeto;
    }

    public void setObjeto(Escopo eObjeto) {
        mObjeto = eObjeto;
    }


}