package Sigmaz.S07_Montador;

public class ChaveadorGama {

    private int mCrifrador[];

    private int mTamanho;

    public ChaveadorGama() {

        mCrifrador = new int[6];
        mTamanho=6;

        mCrifrador[0] = 10;
        mCrifrador[1] = 56;
        mCrifrador[2] = 130;
        mCrifrador[3] = 22;
        mCrifrador[4] = 12;

        mCrifrador[5] = 50;

    }

    public int[] getChave() {
        return mCrifrador;
    }

    public int getChaveTamanho() {
        return mCrifrador.length;
    }
}
