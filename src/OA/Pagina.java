package OA;

import java.util.ArrayList;

public class Pagina {

    private String mNome;

    private ArrayList<Tarefa> mTarefas;

    public Pagina(String eNome) {
        mNome = eNome;

        mTarefas = new ArrayList<Tarefa>();
    }

    public String getNome() {
        return mNome;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }



    public ArrayList<Tarefa> getTarefas() {
        return mTarefas;
    }

}
