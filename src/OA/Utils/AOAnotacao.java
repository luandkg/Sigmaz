package OA.Utils;

import java.util.ArrayList;

public class AOAnotacao {

    private String mNome;
    private String mMarcador;
    private ArrayList<String> mTags;
    private ArrayList<AOTarefa> mAOTarefas;
    private ArrayList<AOPagina> mAOPaginas;

    public AOAnotacao(String eNome) {
        mNome = eNome;
        mMarcador = "";
        mTags = new ArrayList<String>();
        mAOTarefas = new ArrayList<AOTarefa>();
        mAOPaginas = new ArrayList<AOPagina>();

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
    public ArrayList<AOTarefa> getTarefas() {
        return mAOTarefas;
    }
    public ArrayList<AOPagina> getPaginas() {
        return mAOPaginas;
    }

}
