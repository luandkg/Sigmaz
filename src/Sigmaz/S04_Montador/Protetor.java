package Sigmaz.S04_Montador;

import java.nio.charset.StandardCharsets;


public class Protetor {

    Chaveador mChaveador;

    public Protetor(Chaveador eChave) {

        mChaveador = eChave;

    }

    public R5Resposta guardar(String eDocumento) {

        R5Resposta mR5Resposta = new R5Resposta();

        byte[] mGuardado = eDocumento.getBytes(StandardCharsets.UTF_8);

        int mIndex = 0;
        int mTamanho = mGuardado.length;

        int mChave_Index = 0;
        int mChave_Tamanho = mChaveador.getChaveTamanho();


        while (mIndex < mTamanho) {

            mGuardado[mIndex]  = organizarByte(organizarByteInt(mGuardado[mIndex]) + mChaveador.getChave()[mChave_Index]);


            mChave_Index += 1;
            if (mChave_Index == mChave_Tamanho) {
                mChave_Index = 0;
            }


            mIndex += 1;
        }


        mR5Resposta.validarComData(mGuardado);


        return mR5Resposta;
    }

    public R5Resposta guardar(int[] eOrigemData) {

        R5Resposta mR5Resposta = new R5Resposta();


        int mIndex = 0;
        int mTamanho = eOrigemData.length;

        int mChave_Index = 0;
        int mChave_Tamanho = mChaveador.getChaveTamanho();


        byte[] mGuardado = new byte[mTamanho];

        while (mIndex < mTamanho) {

            mGuardado[mIndex] =organizarByte( eOrigemData[mIndex] + mChaveador.getChave()[mChave_Index]);


            mChave_Index += 1;
            if (mChave_Index == mChave_Tamanho) {
                mChave_Index = 0;
            }


            mIndex += 1;
        }


        mR5Resposta.validarComData(mGuardado);


        return mR5Resposta;
    }


    public R5Resposta revelar(byte[] eDados) {


        R5Resposta mR5Resposta = new R5Resposta();

        int mIndex = 0;
        int mTamanho = eDados.length;

        int mChave_Index = 0;
        int mChave_Tamanho = mChaveador.getChaveTamanho();


        byte[] dDados = new byte[mTamanho];

        while (mIndex < mTamanho) {

            if (mChave_Index == mChave_Tamanho) {
                mChave_Index = 0;
            }

            dDados[mIndex] = organizarByte(organizarByteInt(eDados[mIndex]) - mChaveador.getChave()[mChave_Index]);


            mChave_Index += 1;

            mIndex += 1;
        }


        String mDecifrando =  new String(dDados, StandardCharsets.UTF_8);

        // System.out.println("Decifrado : " + saida);

        mR5Resposta.validarComConteudo(mDecifrando);


        return mR5Resposta;
    }


    public int organizarByteInt(byte b) {

        if (b >= 0) {
            return (int) b;
        } else {
            return 256 + (int) b;
        }

    }

    public byte organizarByte(int inteiro){

        while (inteiro < 0) {
            inteiro += 256;
        }
        while (inteiro > 255) {
            inteiro -= 256;
        }

        return (byte) inteiro;
    }
}
