package Sigmaz.S08_Utilitarios;

import java.util.ArrayList;

public class OrganizadorDeEtapas {

    private ArrayList<EtapaCallBack> mProcessos;
    private InfoStatus mInfoStatus;

    private int mEtapaCorrente;

    public OrganizadorDeEtapas() {

        mProcessos = new ArrayList<EtapaCallBack>();
        mInfoStatus = new InfoStatus();
        mEtapaCorrente = 0;

    }

    public int getEtapaID() {
        return mEtapaCorrente;
    }

    public void implemente(EtapaCallBack eEtapaCallBack) {
        mProcessos.add(eEtapaCallBack);
    }


    public void setStatus(String eStatus) {
        mInfoStatus.set(eStatus);
    }

    public String getStatus() {
        return mInfoStatus.getStatus();
    }

    public void iniciar(int eEtapaCorrente) {
        mEtapaCorrente = eEtapaCorrente;
    }

    public void setEtapaID(int eEtapaCorrente) {
        mEtapaCorrente = eEtapaCorrente;
    }


    public void processe() {

        for (EtapaCallBack eProcurando : mProcessos) {
            if (eProcurando.getEtapaID() == mEtapaCorrente) {
                mEtapaCorrente = eProcurando.processar();
                break;
            }
        }

    }


    public void limpar() {
        mProcessos.clear();
    }


}
