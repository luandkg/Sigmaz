package Sigmaz.S07_Montador;

import java.io.File;


public class Arquivador {

    public void limpar(String eArquivo) {

        File arq = new File(eArquivo);
        if (arq.exists()) {
            arq.delete();
        }
    }

    public void marcar(String eTitulo, int eVersao, String eArquivo) {


        ArquivadorUtils ma = new ArquivadorUtils(eArquivo);

        ma.setPonteiro(ma.getLength());

        ma.writeStringPrefix(eTitulo);
        ma.writeInt(eVersao);

        ma.close();


    }


    public long criarSetor(String eArquivo) {

        long ePos = 0;





            ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
            ma.setPonteiro(ma.getLength());

            ePos = ma.getPonteiro();

            ma.writeLong(0);

            ma.close();




        return ePos;

    }


    public void marcarLocal(long ePos, long eDados, String eArquivo) {





            ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
            ma.setPonteiro(ePos);

            ma.writeLong(eDados);

            ma.close();





    }

    public long guardarSetor(byte[] eDados, String eArquivo) {

        long ePos = 0;



            ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
            ma.setPonteiro(ma.getLength());

            ePos = ma.getPonteiro();


            int mIndex = 0;
            int mTamanho = eDados.length;

            while (mIndex < mTamanho) {

                byte mByte = eDados[mIndex];

                ma.writeByte(mByte);

                mIndex += 1;
            }


            ma.close();




        return ePos;

    }



    public byte[] lerBloco(long eInicio, long eTamanho, String eArquivo) {

        byte mDados[] = new byte[(int) eTamanho];





            ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
            ma.setPonteiro(eInicio);


            int mIndex = 0;

            while (mIndex < eTamanho) {

                byte mByte = ma.readByte();

                mDados[mIndex] = mByte;

                mIndex += 1;
            }


            ma.close();



        return mDados;
    }


}
