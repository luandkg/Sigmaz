package Sigmaz.S00_Utilitarios;

public class ArquivoProblema {

    private String mArquivo;
    private String mProblema;

    public ArquivoProblema(String eArquivo,String eProblema){
        mArquivo=eArquivo;
        mProblema=eProblema;
    }

    public String getArquivo(){return mArquivo;}
    public String getProblema(){return mProblema;}

}
