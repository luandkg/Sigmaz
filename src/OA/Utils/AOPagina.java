package OA.Utils;

import java.util.ArrayList;

public class AOPagina {

    private String mNome;

    private ArrayList<AOTarefa> mAOTarefas;

    public AOPagina(String eNome) {
        mNome = eNome;

        mAOTarefas = new ArrayList<AOTarefa>();
    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }



    public ArrayList<AOTarefa> getTarefas() {
        return mAOTarefas;
    }

}
