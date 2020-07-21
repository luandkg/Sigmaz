package OA;

import java.util.ArrayList;

public class Tarefa {

    private String mNome;
    private String mMarcador;
    private ArrayList<String> mTags;

    public Tarefa(String eNome) {
        mNome = eNome;
        mMarcador = "";
        mTags = new ArrayList<String>();
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

}
