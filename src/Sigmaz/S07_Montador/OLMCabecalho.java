package Sigmaz.S07_Montador;

public class OLMCabecalho {

    private String mTitulo;
    private int mVersao;
    private long mTamanho;

    private long mSetorSigmaz;
    private long mSetorCodigo;

    private long mSetorSigmaz_Tamanho;
    private long mCodigo_Tamanho;

    private long mAssinatura_Inicio;
    private long mAssinatura_Tamanho;

    public OLMCabecalho() {

        mTitulo = "";
        mVersao = 0;
        mTamanho = 0;

        mSetorSigmaz = 0;
        mSetorCodigo = 0;

        mSetorSigmaz_Tamanho = 0;
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

    public long getSetorSigmaz_Inicio() {
        return mSetorSigmaz;
    }

    public long getSigmaz_Fim(){
        return mSetorSigmaz + mSetorSigmaz_Tamanho;
    }

    public long getSetorCodigo_Inicio() {
        return mSetorCodigo;
    }

    public long getSigmaz_Tamanho() {
        return mSetorSigmaz_Tamanho;
    }


    public long getCodigo_Fim(){
        return mSetorCodigo + mCodigo_Tamanho;
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

    public long getAssinatura_Fim(){
        return mAssinatura_Inicio + mAssinatura_Tamanho;
    }

    public void ler(String eArquivo) {


        ArquivadorUtils ma = new ArquivadorUtils(eArquivo);
        ma.setPonteiro(0);

        mTitulo = ma.readStringPrefix(3);
        mVersao = ma.readInt();
        mTamanho = ma.getLength();

        mSetorSigmaz = ma.readLong();
        mSetorSigmaz_Tamanho = ma.readLong();

        mSetorCodigo = ma.readLong();
        mCodigo_Tamanho = ma.readLong();

        mAssinatura_Inicio = ma.readLong();
        mAssinatura_Tamanho = ma.readLong();

        ma.close();


    }


    public void definir(String eTitulo, int eVersao, long eSetorSigmaz, long eSetorSigmaz_Tamanho, long eSetorCodigo, long eCodigo_Tamanho, long eSetorAssinatura, long eAssinatura_Tamanho) {


        mTitulo = eTitulo;
        mVersao = eVersao;

        mSetorSigmaz = eSetorSigmaz;
        mSetorSigmaz_Tamanho = eSetorSigmaz_Tamanho;

        mSetorCodigo = eSetorCodigo;
        mCodigo_Tamanho = eCodigo_Tamanho;

        mAssinatura_Inicio = eSetorAssinatura;
        mAssinatura_Tamanho = eAssinatura_Tamanho;

    }

    public long getTamanho() {
        return mTamanho;
    }
}
