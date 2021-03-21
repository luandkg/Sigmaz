package Sigmaz.S08_Utilitarios;

import java.util.ArrayList;

public class OrganizadorDeCiclos {

    private ArrayList<ProcessoCallback> mProcessos;
    private InfoStatus mInfoStatus;

    private int mIndex;
    private int mTamanho;

    public OrganizadorDeCiclos() {

        mProcessos = new ArrayList<ProcessoCallback>();
        mIndex = 0;
        mTamanho = 0;
        mInfoStatus = new InfoStatus();

    }

    public void implemente(ProcessoCallback eProcessoCallback) {
        mProcessos.add(eProcessoCallback);
        mTamanho += 1;
    }


    public void implementeStatus(String eStatus) {

        mProcessos.add(new ProcessoCallback() {
            @Override
            public void processar() {
                mInfoStatus.set(eStatus);
            }
        });

        mTamanho += 1;

    }


    public void implementeComStatus(String eStatus, ProcessoCallback eProcessoCallback) {

        mProcessos.add(new ProcessoCallback() {
            @Override
            public void processar() {
                mInfoStatus.set(eStatus);
            }
        });

        mTamanho += 1;

        mProcessos.add(eProcessoCallback);
        mTamanho += 1;
    }


    public void setStatus(String eStatus) {
        mInfoStatus.set(eStatus);
    }

    public String getStatus() {
        return mInfoStatus.getStatus();
    }

    public void iniciar() {
        mIndex = 0;
    }

    public void processe() {

        if (mIndex < mTamanho) {

            mProcessos.get(mIndex).processar();

            mIndex += 1;
            if (mIndex >= mTamanho) {
                mIndex = 0;
            }

        }
    }


}
