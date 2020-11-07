package OA.Utils;

import LuanDKG.Objeto;

import java.util.ArrayList;

public class Grupo<T> {

    private String mNome;
    private String mValor;

    private ArrayList<T> mObjetos;

    public Grupo(String eNome) {

        mNome = eNome;
        mObjetos = new ArrayList<T>();

        mValor="";


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


    public String getValor() {
        return mValor;
    }

    public void setValor(String eValor){
        mValor=eValor;
    }

}
