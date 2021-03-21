package Sigmaz.S08_Utilitarios;

import java.util.ArrayList;

public class GrupoDeErro {

    private String mArquivo;

    private ArrayList<Erro> mMensagens;

    public GrupoDeErro(String eArquivo){
        mArquivo=eArquivo;
        mMensagens = new ArrayList<Erro>();
    }

    public String getArquivo(){return mArquivo;}

    public boolean mesmmoArquivo(String eArquivo){
        return mArquivo.contentEquals(eArquivo);
    }

    public void adicionarErro(Erro eErro){
        mMensagens.add(eErro);
    }

    public void adicionarErro( String eMensagem,int eLinha,int ePosicao){
        mMensagens.add(new Erro(eMensagem,eLinha,ePosicao));
    }


    public ArrayList<Erro> getErros(){
        return mMensagens;
    }
}
