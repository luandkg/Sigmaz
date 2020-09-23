package Container;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.GZIPInputStream;

public class Arquivo {

    private Ponto mPonto;
    private Container mContainer;
    private String mNomeCompleto;

    public Arquivo(Container eContainer, Ponto ePonto) {

        mContainer = eContainer;
        mPonto = ePonto;
        mNomeCompleto = "";

    }

    public String getNome() {
        return mPonto.getNome();
    }

    public long getTipo() {
        return mPonto.getTipo();
    }

    public long getInicio() {
        return mPonto.getInicio();
    }

    public long getFim() {
        return mPonto.getFim();
    }

    public long getTamanho() {
        return getFim() - getInicio();
    }



    public String getNomeCompleto() {
        return mNomeCompleto;
    }

    public void setNomeCompleto(String eNomeCompleto) {
        mNomeCompleto = eNomeCompleto;
    }


    public void exportar(String eLocal) {
        try {


            RandomAccessFile mLendo = new RandomAccessFile(new File(mContainer.getArquivo()), "r");
            mLendo.seek(getInicio());


            RandomAccessFile mExportando = new RandomAccessFile(new File(eLocal), "rw");

            long mAquivandoIndex = 0;
            long mAquivandoTamanho = this.getTamanho();

            mExportando.seek(0);

            while (mAquivandoIndex < mAquivandoTamanho) {

                mExportando.writeByte(mLendo.readByte());

                mAquivandoIndex += 1;
            }

            mExportando.close();
            mLendo.close();

        } catch (IOException e) {

        }


    }

    public byte[] getBytes() {

        byte[] mBytes = new byte[(int) this.getTamanho()];

        try {


            RandomAccessFile mLendo = new RandomAccessFile(new File(mContainer.getArquivo()), "r");
            mLendo.seek(getInicio());


            long mAquivandoIndex = 0;
            long mAquivandoTamanho = this.getTamanho();


            while (mAquivandoIndex < mAquivandoTamanho) {

                mBytes[(int) mAquivandoIndex] = mLendo.readByte();

                mAquivandoIndex += 1;
            }

            mLendo.close();

        } catch (IOException e) {

        }



        return mBytes;
    }

}
