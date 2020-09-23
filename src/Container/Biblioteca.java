package Container;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class Biblioteca {

    private String mNome;
    private Container mContainer;
    private ArrayList<Arquivo> mArquivos;
    private boolean mIndexado;

    private ArrayList<String> mLocais;
    private ArrayList<String> mExtensoes;

    public Biblioteca(Container eContainer, String eNome) {

        mNome = eNome;
        mContainer = eContainer;
        mArquivos = new ArrayList<Arquivo>();
        mIndexado = false;

        mLocais = new ArrayList<String>();
        mExtensoes = new ArrayList<String>();

    }


    public String getNome() {
        return mNome;
    }


    public void adicionar(String eLocal) {
        if (!mLocais.contains(eLocal)) {
            mLocais.add(eLocal);
        }
    }

    public void adicionarExtensao(String eExtensao) {
        if (!mExtensoes.contains(eExtensao)) {
            mExtensoes.add(eExtensao);
        }
    }

    public ArrayList<String> getLocais() {
        return mLocais;
    }

    public ArrayList<String> getExtensoes() {
        return mExtensoes;
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

        for (String eLocal : getLocais()) {

            if (eLocal.contentEquals("\\")) {

                for (Arquivo eArquivo : mContainer.getArquivos()) {
                    boolean incluir = false;
                    for(String eExt : mExtensoes){
                        if (eArquivo.getNome().endsWith(eExt)){
                            incluir=true;
                        }
                    }
                    if (incluir){
                        mArquivos.add(eArquivo);
                    }
                }
                for (Pasta ePasta : mContainer.getPastas()) {
                    abrirLocal(ePasta);
                }

            } else   if (eLocal.contentEquals("/")){

                for(Arquivo eArquivo : mContainer.getArquivos()){
                    boolean incluir = false;
                    for(String eExt : mExtensoes){
                        if (eArquivo.getNome().endsWith(eExt)){
                            incluir=true;
                        }
                    }
                    if (incluir){
                        mArquivos.add(eArquivo);
                    }
                }
                for (Pasta ePasta : mContainer.getPastas()) {
                    abrirLocal(ePasta);
                }
            }else{
                Pasta ePasta = mContainer.getPastaCaminho(eLocal);

                abrirLocal(ePasta);
            }


        }
    }

    private void abrirLocal(Pasta ePasta){

        try {

            RandomAccessFile raf = new RandomAccessFile(new File(mContainer.getArquivo()), "rw");

            FileBinary fu = new FileBinary(raf);

            fu.setPonteiro(ePasta.getInicio());

            int v = 0;

            while (v != 13) {
                v = (int) fu.readByte();

                if (v == 11) {

                    String s1 = fu.readString();
                    long l2 = fu.readLong();
                    long l3 = fu.readLong();

                    abrirLocal(new Pasta(mContainer, new Ponto(s1, 11, l2, l3)));


                } else if (v == 12) {

                    String s1 = fu.readString();
                    long l2 = fu.readLong();
                    long l3 = fu.readLong();

                    Arquivo eArquivo = new Arquivo(mContainer, new Ponto(s1, 12, l2, l3));

                    boolean incluir = false;
                    for(String eExt : mExtensoes){
                        if (eArquivo.getNome().endsWith(eExt)){
                            incluir=true;
                        }
                    }
                    if (incluir){
                        mArquivos.add(eArquivo);
                    }


                }
            }

            raf.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

    }
}
