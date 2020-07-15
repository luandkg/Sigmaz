package OA;

import OA.LuanDKG.Objeto;

import java.util.ArrayList;

public class Agrupador<T> {

    private String mNome;

    private ArrayList<T> mObjetos;

    public Agrupador(String eNome) {

        mNome = eNome;
        mObjetos = new ArrayList<T>();

    }


    public String getNome() {
        return mNome;
    }

    public ArrayList<T> getObjetos() {
        return mObjetos;
    }


    public void adicionar(T eObjeto) {
        mObjetos.add(eObjeto);
    }




}
