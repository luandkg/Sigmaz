package Sigmaz.S00_Utilitarios;

public class ArquivoTeste {

    private String mArquivo;
    private String mProblema;
    private boolean mStatus;
    private Documento mComplemento;

    public ArquivoTeste(String eArquivo) {
        mArquivo = eArquivo;
        mProblema = "";
        mStatus = true;
        mComplemento = new Documento();
    }

    public void setProblema(String eProblema) {
        mProblema = eProblema;
        mStatus = false;
    }

    public void setOk() {
        mProblema = "";
        mStatus = true;
    }

    public String getArquivo() {
        return mArquivo;
    }

    public String getProblema() {
        return mProblema;
    }

    public boolean getStatus() {
        return mStatus;
    }

    public boolean temProblema() {
        return mStatus == false;
    }

    public boolean tudoOk() {
        return mStatus == true;
    }

    public Documento getComplemento(){
        return mComplemento;
    }
}
