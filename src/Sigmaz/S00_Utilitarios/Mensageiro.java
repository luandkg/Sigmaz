package Sigmaz.S00_Utilitarios;

import java.util.ArrayList;

public class Mensageiro {

    private ArrayList<String> mErros;
    private ArrayList<String> eMensagens;

    public Mensageiro() {

        eMensagens = new ArrayList<String>();
        mErros = new ArrayList<>();

    }

    public void limpar() {

        eMensagens.clear();
        mErros.clear();


    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void mensagem(String eMensagem) {
        eMensagens.add(eMensagem);
    }

    public void errar(String eErro) {
        mErros.add(eErro);
    }

    public ArrayList<String> getMensagens() {
        return eMensagens;
    }

    public boolean temErros() {

        if (mErros.size() == 0) {
            return false;
        } else {
            return true;
        }

    }

    public boolean tudoOK() {

        if (mErros.size() == 0) {
            return true;
        } else {
            return false;
        }

    }


}
