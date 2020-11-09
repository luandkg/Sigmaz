package Sigmaz.S04_Montador;

public class OLMCabecalho {

    private String mTitulo;
    private int mVersao;
    private long mTamanho;

    private long mSetorSigmaz;
    private long mSetorCodigo;
    private long  mDebug_Inicio;

    private long mSetorSigmaz_Tamanho;
    private long mCodigo_Tamanho;
    private long mDebug_Tamanho;

    private long mAssinatura_Inicio;
    private long mAssinatura_Tamanho;

    private long mL0;
    private long mL1;
    private long mL2;
    private long mL3;

    private long mL4;
    private long mL5;

    private long mL6;
    private long mL7;

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

        mDebug_Inicio=0;

        mL0 = 0;
        mL1 = 0;
        mL2 = 0;
        mL3 = 0;
        mL4 = 0;
        mL5 = 0;
        mL6 = 0;
        mL7 = 0;


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

    public long getSigmaz_Fim() {
        return mSetorSigmaz + mSetorSigmaz_Tamanho;
    }

    public long getSetorCodigo_Inicio() {
        return mSetorCodigo;
    }

    public long getSigmaz_Tamanho() {
        return mSetorSigmaz_Tamanho;
    }


    public long getCodigo_Fim() {
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

    public long getAssinatura_Fim() {
        return mAssinatura_Inicio + mAssinatura_Tamanho;
    }



    public long getSetorDebug_Inicio() {
        return mDebug_Inicio;
    }

    public long getDebug_Tamanho() {
        return mDebug_Tamanho;
    }

    public long getDebug_Fim() {
        return mDebug_Inicio + mDebug_Tamanho;
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

        mDebug_Inicio = ma.readLong();
        mDebug_Tamanho = ma.readLong();

        ma.close();


    }


    public void definirLocais_Sigmaz(long eSetorSigmaz, long eSetorSigmaz_Tamanho) {


        mL0 = eSetorSigmaz;
        mL1 = eSetorSigmaz_Tamanho;


    }

    public void definirLocais_Codigo(long eSetorCodigo, long eCodigo_Tamanho) {

        mL2 = eSetorCodigo;
        mL3 = eCodigo_Tamanho;

    }

    public void definirLocais_Assinatura(long eSetorAssinatura, long eAssinatura_Tamanho) {

        mL4 = eSetorAssinatura;
        mL5 = eAssinatura_Tamanho;

    }


    public void definirLocais_Debug(long eSetorDebug, long eDebug_Tamanho) {

        mL6 = eSetorDebug;
        mL7 = eDebug_Tamanho;

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

    public void definirSigmaz(long eSetorSigmaz, long eSetorSigmaz_Tamanho) {

        mSetorSigmaz = eSetorSigmaz;
        mSetorSigmaz_Tamanho = eSetorSigmaz_Tamanho;

    }

    public void definirCodigo( long eSetorCodigo, long eCodigo_Tamanho) {
        mSetorCodigo = eSetorCodigo;
        mCodigo_Tamanho = eCodigo_Tamanho;

    }

    public void definirAssinatura(long eSetorAssinatura, long eAssinatura_Tamanho) {
        mAssinatura_Inicio = eSetorAssinatura;
        mAssinatura_Tamanho = eAssinatura_Tamanho;

    }

    public void definirDebug(long eInicio, long eTamanho) {
        mDebug_Inicio = eInicio;
        mDebug_Tamanho = eTamanho;

    }

    public long getTamanho() {
        return mTamanho;
    }


    public long getL0() {
        return mL0;
    }

    public long getL1() {
        return mL1;
    }

    public long getL2() {
        return mL2;
    }

    public long getL3() {
        return mL3;
    }

    public long getL4() {
        return mL4;
    }

    public long getL5() {
        return mL5;
    }

    public long getL6() {
        return mL6;
    }

    public long getL7() {
        return mL7;
    }

}
