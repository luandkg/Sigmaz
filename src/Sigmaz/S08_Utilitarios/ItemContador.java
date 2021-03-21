package Sigmaz.S08_Utilitarios;

public class ItemContador {

    private String mNome;
    private int mQuantidade;

    public ItemContador(String eNome, int eQuantidade) {
        mNome = eNome;
        mQuantidade = eQuantidade;
    }

    public void aumentar(int eMais) {
        mQuantidade += eMais;
    }

    public String getNome() {
        return mNome;
    }

    public int getQuantidade() {
        return mQuantidade;
    }

    public boolean mesmoNome(String eNome) {
        return mNome.contentEquals(eNome);
    }
}
