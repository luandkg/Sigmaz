package Container;

import java.util.ArrayList;

public class Listador {

    private String mNome;
    private ArrayList<Pasta> mLocais;
    private Container mContainer;
    private ArrayList<Arquivo> mArquivos;
    private boolean mIndexado;

    public Listador(Container eContainer, String eNome) {

        mNome = eNome;
        mLocais = new ArrayList<Pasta>();
        mContainer = eContainer;
        mArquivos = new ArrayList<Arquivo>();
        mIndexado = false;
    }


    public String getNome() {
        return mNome;
    }


    public void adicionar(Pasta ePasta) {
        if (!mLocais.contains(ePasta)) {
            mLocais.add(ePasta);
        }
    }

    public ArrayList<Pasta> getLocais() {
        return mLocais;
    }

    public ArrayList<Arquivo> getArquivos() {

        if (!mIndexado) {
            mIndexado = true;
            abrir();
        }

        return mArquivos;
    }

    private void abrir() {

        mArquivos.clear();

        for (Pasta eLocal : getLocais()) {

            mArquivos.addAll(eLocal.getArquivosTodos());


        }
    }


}
