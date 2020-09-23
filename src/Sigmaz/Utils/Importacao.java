package Sigmaz.Utils;

public class Importacao {

    private String mOrigem;
    private String mImportando;
    private int mLinha;
    private int mColuna;

    public Importacao(String eOrigem,String eImportando,int eLinha,int eColuna){

        mOrigem=eOrigem;
        mImportando=eImportando;
        mLinha=eLinha;
        mColuna=eColuna;

    }

    public String getOrigem(){ return mOrigem;}
    public String getImportando(){ return mImportando;}

    public int getLinha(){ return mLinha;}
    public int getColuna(){ return mColuna;}

}
