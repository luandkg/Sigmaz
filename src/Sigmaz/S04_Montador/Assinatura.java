package Sigmaz.S04_Montador;

import java.util.Random;

public class Assinatura {

    private byte[] mDados;

    public Assinatura() {

        mDados = new byte[1024];

        Random rd = new Random();

        for (int i = 0; i < 1024; i++) {
            mDados[i] = (byte) rd.nextInt(255);
        }

    }


    public byte[] getData() {
        return mDados;
    }

}
