package OA;

import java.util.ArrayList;

public class Note {

    private String mNome;
    private String mMarcador;
    private ArrayList<String> mTags;
    private ArrayList<Tarefa> mTarefas;
    private ArrayList<Pagina> mPaginas;

    public Note(String eNome) {
        mNome = eNome;
        mMarcador = "";
        mTags = new ArrayList<String>();
        mTarefas = new ArrayList<Tarefa>();
        mPaginas = new ArrayList<Pagina>();

    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getMarcador() {
        return mMarcador;
    }

    public void setMarcador(String eMarcador) {
        mMarcador = eMarcador;
    }

    public ArrayList<String> getTags() {
        return mTags;
    }
    public ArrayList<Tarefa> getTarefas() {
        return mTarefas;
    }
    public ArrayList<Pagina> getPaginas() {
        return mPaginas;
    }

}
