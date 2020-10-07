package Sigmaz.S00_Utilitarios;

import Sigmaz.S03_Parser.Token;

import java.util.ArrayList;

public class GrupoDeComentario {

    private String mArquivo;

    private ArrayList<Token> mComentarios;

    public GrupoDeComentario(String eArquivo){
        mArquivo=eArquivo;
        mComentarios = new ArrayList<Token>();
    }

    public String getArquivo(){return mArquivo;}

    public boolean mesmmoArquivo(String eArquivo){
        return mArquivo.contentEquals(eArquivo);
    }

    public void adicionarComentario(Token eErro){
        mComentarios.add(eErro);
    }


    public ArrayList<Token> getComentarios(){
        return mComentarios;
    }
}
