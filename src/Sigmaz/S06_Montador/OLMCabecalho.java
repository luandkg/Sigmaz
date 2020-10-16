package Sigmaz.S06_Montador;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class OLMCabecalho {

    private String mTitulo;
    private int mVersao;

    private long mSetorCabecalho;
    private long mSetorCodigo;

    private long mCabecalho_Tamanho;
    private long mCodigo_Tamanho;

    private long mAssinatura_Inicio;
    private long mAssinatura_Tamanho;

    public OLMCabecalho() {

        mTitulo = "";
        mVersao = 0;

        mSetorCabecalho = 0;
        mSetorCodigo = 0;

        mCabecalho_Tamanho = 0;
        mCodigo_Tamanho = 0;

        mAssinatura_Inicio = 0;
        mAssinatura_Tamanho = 0;
    }

    public String getTitulo() {
        return mTitulo;
    }

    public int getVersao() {
        return mVersao;
    }

    public long getSetorCabecalho() {
        return mSetorCabecalho;
    }

    public long getSetorCodigo() {
        return mSetorCodigo;
    }

    public long getCabecalho_Tamanho() {
        return mCabecalho_Tamanho;
    }

    public long getCodigo_Tamanho() {
        return mCodigo_Tamanho;
    }

    public long getAssinatura_Inicio() {
        return mAssinatura_Inicio;
    }

    public long getAssinatura_Tamanho() {
        return mAssinatura_Tamanho;
    }



    public void ler(String eArquivo) {


        ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
        ma.setPonteiro(0);

        mTitulo = ma.readStringPrefix(3);
        mVersao = ma.readInt();

        mSetorCabecalho = ma.readLong();
        mCabecalho_Tamanho = ma.readLong();

        mSetorCodigo = ma.readLong();
        mCodigo_Tamanho = ma.readLong();

        mAssinatura_Inicio = ma.readLong();
        mAssinatura_Tamanho = ma.readLong();

        ma.close();


    }


    public void definir(String eTitulo, int eVersao, long eSetorCabecalho, long eCabecalho_Tamanho, long eSetorCodigo, long eCodigo_Tamanho, long eSetorAssinatura, long eAssinatura_Tamanho) {


        mTitulo = eTitulo;
        mVersao = eVersao;

        mSetorCabecalho = eSetorCabecalho;
        mCabecalho_Tamanho = eCabecalho_Tamanho;

        mSetorCodigo = eSetorCodigo;
        mCodigo_Tamanho = eCodigo_Tamanho;

        mAssinatura_Inicio = eSetorAssinatura;
        mAssinatura_Tamanho = eAssinatura_Tamanho;

    }
}
