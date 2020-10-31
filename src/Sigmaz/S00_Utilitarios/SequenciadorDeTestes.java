package Sigmaz.S00_Utilitarios;

import java.util.ArrayList;

public class SequenciadorDeTestes {

    private ArrayList<ArquivoTeste> mTestes;

    public SequenciadorDeTestes() {

        mTestes = new ArrayList<ArquivoTeste>();

    }

    public ArquivoTeste adicionarTeste(String eArquivo) {

        ArquivoTeste mTeste = new ArquivoTeste(eArquivo);

        mTestes.add(mTeste);

        return mTeste;
    }


    public boolean temProblemas() {
        boolean ret = false;

        for (ArquivoTeste mTeste : mTestes) {
            if (mTeste.temProblema()) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public boolean tudoOk() {
        boolean ret = true;

        for (ArquivoTeste mTeste : mTestes) {
            if (mTeste.temProblema()) {
                ret = false;
                break;
            }
        }
        return ret;
    }

    public ArrayList<ArquivoTeste> getCorretos() {

        ArrayList<ArquivoTeste> mLista = new ArrayList<ArquivoTeste>();

        for (ArquivoTeste mTeste : mTestes) {
            if (mTeste.tudoOk()) {
                mLista.add(mTeste);
            }
        }
        return mLista;
    }

    public ArrayList<ArquivoTeste> getProblemas() {

        ArrayList<ArquivoTeste> mLista = new ArrayList<ArquivoTeste>();

        for (ArquivoTeste mTeste : mTestes) {
            if (mTeste.temProblema()) {
                mLista.add(mTeste);
            }
        }
        return mLista;
    }

    public int getTotal() {
        return mTestes.size();
    }

}
