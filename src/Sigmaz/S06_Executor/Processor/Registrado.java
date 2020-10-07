package Sigmaz.S06_Executor.Processor;

public class Registrado {

    private String mConteudo = "";
    private String mTipo = "";


    public void atribuir(String eConteudo,String eTipo){
        mConteudo=eConteudo;
        mTipo=eTipo;
    }

    public String getConteudo(){return mConteudo;}
    public String getTipo(){return mTipo;}


}
