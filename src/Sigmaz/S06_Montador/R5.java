package Sigmaz.S06_Montador;

import java.nio.charset.StandardCharsets;


public class R5 {

    Chaveador mChaveador ;

    public R5(Chaveador eChave){

        mChaveador=eChave;

    }

    public R5Resposta guardar(String eDocumento) {

        R5Resposta mR5Resposta = new R5Resposta();

        byte[] mGuardado = eDocumento.getBytes(StandardCharsets.UTF_8);

        int mIndex = 0;
        int mTamanho = eDocumento.length();

        int mChave_Index = 0;
        int mChave_Tamanho = mChaveador.getChaveTamanho();


        while (mIndex < mTamanho) {
            int novo = (int) mGuardado[mIndex];


            novo += mChaveador.getChave()[mChave_Index];
            mChave_Index += 1;
            if (mChave_Index == mChave_Tamanho) {
                mChave_Index = 0;
            }

            if (novo > 255) {
                novo -= 255;
            }


            mGuardado[mIndex] = (byte) novo;
            mIndex += 1;
        }


        mR5Resposta.validarComData(mGuardado);


        return mR5Resposta;
    }

    public R5Resposta revelar(byte[] eDados) {

        String mDecifrando = "";

        R5Resposta mR5Resposta = new R5Resposta();

        int mIndex = 0;
        int mTamanho = eDados.length;

        int mChave_Index = 0;
        int mChave_Tamanho = mChaveador.getChaveTamanho();


        while (mIndex < mTamanho) {
            int novo = (int) eDados[mIndex];


            novo -= mChaveador.getChave()[mChave_Index];
            mChave_Index += 1;
            if (mChave_Index == mChave_Tamanho) {
                mChave_Index = 0;
            }


            if (novo < 0) {
                novo += 256;
            }


            eDados[mIndex] = (byte) novo;
            mIndex += 1;
        }


        mDecifrando = new String(eDados, StandardCharsets.UTF_8);

        // System.out.println("Decifrado : " + saida);

        mR5Resposta.validar(mDecifrando);


        return mR5Resposta;
    }

}
