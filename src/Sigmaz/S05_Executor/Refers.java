package Sigmaz.S05_Executor;


import java.util.ArrayList;

public class Refers {

    private ArrayList<String> mRefers;
    private ArrayList<String> mRefersOcultas;

    private Escopo mEscopo;

    public Refers(Escopo eEscopo){

        mRefers = new ArrayList<String>();
        mRefersOcultas = new ArrayList<String>();

        mEscopo=eEscopo;

    }

    public void adicionarRefer(String eRefer) {
        mRefers.add(eRefer);
    }

    public void adicionarReferDe(Escopo outroEscopo) {

        for (String eref : outroEscopo.getRefers()) {
            adicionarReferOculto(eref);
        }
        for (String eref : outroEscopo.getRefersOcultas()) {
            adicionarReferOculto(eref);
        }

    }

    public void adicionarReferOculto(String eRefer) {
        mRefersOcultas.add(eRefer);
    }


    public ArrayList<String> getRefers() {

        ArrayList<String> mRet = new ArrayList<String>();
        mRet.addAll(mRefers);

        if (mEscopo.getEscopoAnterior() != null) {
            for (String r : mEscopo.getEscopoAnterior().getRefers()) {
                if (!mRet.contains(r)) {
                    mRet.add(r);
                }
            }
        }


        return mRet;
    }


    public ArrayList<String> getRefersOcultas() {

        ArrayList<String> mRet = new ArrayList<String>();
        mRet.addAll(mRefers);
        mRet.addAll(mRefersOcultas);

        if (mEscopo.getEscopoAnterior() != null) {
            for (String r : mEscopo.getEscopoAnterior().getRefers()) {
                if (!mRet.contains(r)) {
                    mRet.add(r);
                }
            }
        }


        return mRet;
    }


}
