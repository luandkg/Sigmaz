package Sigmaz.S06_Montador;

public class Chaveador {

    private int mCrifrador[];

    private int mTamanho;

    public Chaveador() {

        mCrifrador = new int[6];
        mTamanho=6;

        mCrifrador[0] = 10;
        mCrifrador[1] = 56;
        mCrifrador[2] = 130;
        mCrifrador[3] = 22;
        mCrifrador[4] = 12;
        mCrifrador[5] = 80;

    }

    public int[] getChave() {
        return mCrifrador;
    }

    public int getChaveTamanho() {
        return mTamanho;
    }
}
