package Sigmaz.S08_Utilitarios;

import java.util.ArrayList;

public class EscopoTipos {

    private ArrayList<String> mTipos;

    public EscopoTipos() {

        mTipos = new ArrayList<String>();

    }


    public ArrayList<String> getTipos() {
        return mTipos;
    }

    public boolean existe(String eTipo) {
        return mTipos.contains(eTipo);
    }

    public void adicionar(String eTipo) {
        mTipos.add(eTipo);
    }


    public EscopoTipos getCopia() {
        EscopoTipos mCopia = new EscopoTipos();

        for (String a : mTipos) {
            mCopia.adicionar(a);

        }

        return mCopia;
    }

}
