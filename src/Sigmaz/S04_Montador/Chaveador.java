package Sigmaz.S04_Montador;

public class Chaveador {

    private int mCrifrador[];

    private int mTamanho;

    public Chaveador(int eTamanho) {

        mCrifrador = new int[eTamanho];
        mTamanho = eTamanho;

        for (int i = 0; i < eTamanho; i++) {
            mCrifrador[i] = 0;
        }

    }

    public void set(int e, int eValor) {
        mCrifrador[e] = eValor;
    }

    public void setVarios(int e, int eValor1, int eValor2, int eValor3, int eValor4) {

        mCrifrador[e] = eValor1;
        mCrifrador[e + 1] = eValor2;
        mCrifrador[e + 2] = eValor3;
        mCrifrador[e + 3] = eValor4;


    }


    public void setVarios(int e, int eValor1, int eValor2, int eValor3, int eValor4, int eValor5, int eValor6, int eValor7, int eValor8, int eValor9, int eValor10) {

        mCrifrador[e] = eValor1;
        mCrifrador[e + 1] = eValor2;
        mCrifrador[e + 2] = eValor3;
        mCrifrador[e + 3] = eValor4;
        mCrifrador[e + 4] = eValor5;
        mCrifrador[e + 5] = eValor6;
        mCrifrador[e + 6] = eValor7;
        mCrifrador[e + 7] = eValor8;
        mCrifrador[e + 8] = eValor9;
        mCrifrador[e + 9] = eValor10;

    }


    public int[] getChave() {
        return mCrifrador;
    }

    public int getChaveTamanho() {
        return mCrifrador.length;
    }
}
