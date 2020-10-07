package Sigmaz.S00_Utilitarios;

public class Documento {


    private String mConteudo;

    public Documento() {
        mConteudo = "";
    }


    public void adicionar(String eConteudo) {
        mConteudo += eConteudo;
    }


    public void adicionarLinha(String eConteudo) {
        if (mConteudo.length() == 0) {
            mConteudo += eConteudo;
        } else {
            mConteudo += "\n" + eConteudo;
        }
    }

    public void adicionarLinha(int mTab, String eConteudo) {

        String sTabs = "";

        if (mTab > 0) {
            for (int t = 0; t < mTab; t++) {
                sTabs += "\t";
            }
        }


        if (mConteudo.length() == 0) {
            mConteudo += sTabs + eConteudo;
        } else {
            mConteudo += "\n" + sTabs + eConteudo;
        }


    }


    public String getConteudo() {
        return mConteudo;
    }


    public void limpar() {
        mConteudo = "";
    }
}
