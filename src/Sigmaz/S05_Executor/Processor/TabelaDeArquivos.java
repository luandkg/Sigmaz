package Sigmaz.S05_Executor.Processor;

import java.util.ArrayList;

public class TabelaDeArquivos {

    private ArrayList<Arquivo> mArquivos;
    private int mArquivadorID;

    public TabelaDeArquivos() {
        mArquivos = new ArrayList<Arquivo>();
        mArquivadorID = 0;
    }

    public int abrir(String eArquivo) {

        int FD = mArquivadorID;

        Arquivo eNovo = new Arquivo(eArquivo, FD);
        mArquivos.add(eNovo);

        mArquivadorID += 1;

        return FD;
    }

    public void fechar(int eFD) {

        for (Arquivo eArquivo : mArquivos) {
            if (eArquivo.getFD() == eFD) {
                eArquivo.fechar();
                mArquivos.remove(eArquivo);
                break;
            }
        }
    }

    public void escrever(int eFD, String eConteudo) {

        for (Arquivo eArquivo : mArquivos) {
            if (eArquivo.getFD() == eFD) {
                eArquivo.escrever(eConteudo);
                break;
            }
        }
    }

    public void fecharTodos() {
        for (Arquivo eArquivo : mArquivos) {
            eArquivo.fechar();
        }
        mArquivos.clear();
    }

}
