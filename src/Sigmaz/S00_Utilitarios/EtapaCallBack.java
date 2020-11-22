package Sigmaz.S00_Utilitarios;

public abstract class EtapaCallBack {

    private int mEtapaID;

    public EtapaCallBack(int eEtapaID) {
        mEtapaID = eEtapaID;
    }

    public int getEtapaID() {
        return mEtapaID;
    }

    public abstract int processar();

}
