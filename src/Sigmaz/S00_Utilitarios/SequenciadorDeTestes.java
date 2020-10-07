package Sigmaz.S00_Utilitarios;

import java.util.ArrayList;

public class SequenciadorDeTestes {

    private ArrayList<ArquivoProblema> mArquivos_Problematicos;

    public SequenciadorDeTestes() {
        mArquivos_Problematicos = new ArrayList<ArquivoProblema>();
    }

    public void adicionarProblema(String eArquivo, String eProblema) {
        mArquivos_Problematicos.add(new ArquivoProblema(eArquivo, eProblema));
    }

    public boolean temProblemas() {
        if (mArquivos_Problematicos.size() == 0) {
            return false;
        } else {
            return true;
        }
    }


    public ArrayList<ArquivoProblema> getProblemas(){return mArquivos_Problematicos;}

}
