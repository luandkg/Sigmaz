package Sigmaz.S06_Montador;

public class Chaveador {

    private int mCrifrador[];

    private int mTamanho;

    public Chaveador(int eTamanho) {

        mCrifrador = new int[eTamanho];
        mTamanho=eTamanho;

        for(int i=0;i<eTamanho;i++){
            mCrifrador[i]=0;
        }

    }

    public void set(int e,int eValor){
        mCrifrador[e] = eValor;
    }

    public int[] getChave() {
        return mCrifrador;
    }

    public int getChaveTamanho() {
        return mTamanho;
    }
}
