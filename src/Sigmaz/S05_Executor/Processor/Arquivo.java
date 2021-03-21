package Sigmaz.S05_Executor.Processor;

import Sigmaz.S08_Utilitarios.Texto;

public class Arquivo {

    private String mArquivo;
    private int mFD;

    public Arquivo(String eArquivo, int eFD) {
        mArquivo = eArquivo;
        mFD = eFD;
    }

    public String getArquivo() {
        return mArquivo;
    }

    public int getFD() {
        return mFD;
    }

    public void fechar() {

    }


    public void escrever(String eConteudo) {

        Texto.Escrever(mArquivo, eConteudo);

    }

}
