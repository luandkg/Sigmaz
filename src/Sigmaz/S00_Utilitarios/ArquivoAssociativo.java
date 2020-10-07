package Sigmaz.S00_Utilitarios;

public class ArquivoAssociativo {

    private String mNome;
    private String mArquivo;

    public ArquivoAssociativo(String eNome,String eArquivo){

        mNome=eNome;
        mArquivo=eArquivo;

    }

    public String getNome(){return mNome;}
    public String getArquivo(){return mArquivo;}

}
