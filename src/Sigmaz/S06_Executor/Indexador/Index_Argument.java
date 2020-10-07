package Sigmaz.S06_Executor.Indexador;

public class Index_Argument {

    private String mNome;
    private String mTipo;
    private String mModo;

    public Index_Argument(String eNome,String eTipo,String eModo){

        mNome=eNome;
        mTipo=eTipo;
        mModo=eModo;

    }

    public String getNome(){return mNome;}
    public String getTipo(){return mTipo;}
    public String getModo(){return mModo;}

}
