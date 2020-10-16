package Sigmaz.S06_Montador;

import java.nio.charset.StandardCharsets;


public class R1 {

    private byte mCodigo;

    public R1(byte eCodigo) {

        mCodigo = eCodigo;

    }

    public R5Resposta guardar(String eDocumento) {

        R5Resposta mR5Resposta = new R5Resposta();

        byte[] mGuardando = eDocumento.getBytes(StandardCharsets.UTF_8);

        int mIndex = 0;
        int mTamanho = eDocumento.length();

        while (mIndex < mTamanho) {
            int novo = (int) mGuardando[mIndex];


            novo += (int)mCodigo;

            if (novo > 255) {
                novo -= 255;
            }


            mGuardando[mIndex] = (byte) novo;
            mIndex += 1;
        }


        mR5Resposta.validarComData(mGuardando);


        return mR5Resposta;
    }

    public R5Resposta revelar(byte[] eDados) {

        String mRevelando = "";

        R5Resposta mR5Resposta = new R5Resposta();

        int mIndex = 0;
        int mTamanho = eDados.length;


        while (mIndex < mTamanho) {
            int novo = (int) eDados[mIndex];

            novo -= (int)mCodigo;

            if (novo < 0) {
                novo += 256;
            }

            eDados[mIndex] = (byte) novo;
            mIndex += 1;
        }


        mRevelando = new String(eDados, StandardCharsets.UTF_8);

        // System.out.println("Decompilado : " + saida);

        mR5Resposta.validar(mRevelando);


        return mR5Resposta;
    }

}
