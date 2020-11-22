package Sigmaz.S03_Integrador;

import java.util.ArrayList;

public class Escopante {

    private String mNome;

    private ArrayList<String> mRefers;

    private ArrayList<String> mAlocado;

    private boolean mTemAnterior;
    private Escopante mAnterior;

    public Escopante(String eNome) {

        mNome = eNome;

        mRefers = new ArrayList<String>();
        mAlocado = new ArrayList<String>();

        mTemAnterior = false;
        mAnterior = null;

    }

    public void setAnterior(Escopante eAnterior) {
        mAnterior = eAnterior;
        mTemAnterior = true;
    }


    public void referencie(String eRefer) {
        mRefers.add(eRefer);
    }

    public void alocar(String eNome) {
        mAlocado.add(eNome);
    }


    public boolean existeNesse(String eNome) {
        return mAlocado.contains(eNome);
    }


    public boolean existeAteAqui(String eNome) {

        if (mAlocado.contains(eNome)) {
            return true;
        } else {

            if (mTemAnterior) {
                return mAnterior.existeAteAqui(eNome);
            } else {
                return false;
            }

        }


    }


    public String getRegressivo() {

        String mRet = "";

        if (mTemAnterior) {
            mRet = mAnterior.getRegressivo() + " -> " + mNome;
        } else {
            mRet = mNome;
        }

        return mRet;
    }

    public boolean existeRefer(String eRefer) {

        boolean ret = false;

        for (String pRefer : mRefers) {
            if (pRefer.contentEquals(eRefer)) {
                ret = true;
                break;
            }
        }

        if (!ret) {
            if (mTemAnterior) {
                ret = mAnterior.existeRefer(eRefer);
            }
        }

        return ret;

    }

}
