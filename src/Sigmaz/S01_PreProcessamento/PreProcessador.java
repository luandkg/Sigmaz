package Sigmaz.S01_PreProcessamento;

import java.util.ArrayList;

public class PreProcessador {

    private ArrayList<String> mFila;
    private int mTamanho;
    private int mAntes;

    private String mProcessamento;

    private ArrayList<String> mRemover;
    private ArrayList<String> mAdicionar;
    private ArrayList<String> mRemovido;

    private ArrayList<String> mProcessados;
    private String mProcessandoCorrente;

    public PreProcessador() {

        mFila = new ArrayList<String>();
        mTamanho = 0;
        mProcessamento = "";
        mProcessandoCorrente = "";

        mAdicionar = new ArrayList<String>();
        mRemover = new ArrayList<String>();
        mRemovido = new ArrayList<String>();

        mProcessados = new ArrayList<String>();

    }

    public void adicionar(String eArquivo) {
        mFila.add(eArquivo);
    }

    public int getTamanho() {
        return mFila.size();
    }

    public boolean contem(String eArquivo) {
        return mFila.contains(eArquivo);
    }

    public void remover(String eArquivo) {
        mFila.remove(eArquivo);
    }

    public String obter(int eIndex) {
        return mFila.get(eIndex);
    }


    public ArrayList<String> getProcessados() {
        return mProcessados;
    }

    public void terminar() {
        mFila.clear();
        mAdicionar.clear();
        mRemover.clear();
        mProcessados.clear();
        mProcessandoCorrente = "";
    }

    public void iniciar() {

        mTamanho = getTamanho();
        mProcessamento = "";
        mProcessandoCorrente = "";

        mAntes = 0;

        mAdicionar = new ArrayList<String>();
        mRemover = new ArrayList<String>();
        mRemovido = new ArrayList<String>();
        mProcessados = new ArrayList<String>();

        mProcessamento += "PRE-PROCESSAMENTO \n";
        mProcessamento += "\n";

        mProcessamento += "INICIAR COM : \n";

        for (String ea : mFila) {
            mProcessamento += "\t - " + ea + "\n";
        }

        mProcessamento += "\n";
        isTerminou=false;

    }

    public boolean estaExecutando() {
        return getTamanho() > 0;
    }


    public String getProcessamento() {
        return mProcessamento;
    }

    public String getProcessandoCorrente() {
        return mProcessandoCorrente;
    }

    private boolean isTerminou;

    public boolean isTerminou(){
        return isTerminou;
    }

    public String processar() {

        mAntes = getTamanho();

        if (mAntes > 0) {

            String mCorrente = obter(0);
            if (mFila.size() > 0) {
                if (mRemovido.contains(mCorrente)) {

                    mProcessamento += "PROCESSANDO : " + mCorrente + " :: " + mAntes + " - DUPLICADO \n";

                    mRemovido.add(mCorrente);
                    if (mFila.size() > 0) {
                        mCorrente = processar();
                    } else {
                        mCorrente = null;
                    }
                } else {
                    mProcessamento += "PROCESSANDO : " + mCorrente + " :: " + mAntes + "\n";
                    mRemover.add(mCorrente);
                }
            } else {
                mCorrente = null;
            }
            mProcessandoCorrente = mCorrente;

        } else {
            mProcessandoCorrente = null;
            isTerminou=true;
        }


        return mProcessandoCorrente;
    }

    public void marcarProcessado() {
        mProcessados.add(mProcessandoCorrente);
    }

    public void momentoAdicionar(String a) {
        mAdicionar.add(a);
    }

    public void organizar() {

        ArrayList<String> realAdicionar = new ArrayList<String>();
        ArrayList<String> jaAdicionados = new ArrayList<String>();

        for (String e : mAdicionar) {
            if (!this.contem(e)) {
                this.adicionar(e);
                realAdicionar.add(e);
            } else {
                if (!jaAdicionados.contains(e)) {
                    jaAdicionados.add(e);
                }
            }

        }

        if (realAdicionar.size() > 0) {
            mProcessamento += "\tADICIONAR" + "\n";
            for (String e : realAdicionar) {
                if (!mRemover.contains(e)) {
                    mProcessamento += "\t\t - " + e + "\n";
                }

            }
        }

        if (jaAdicionados.size() > 0) {
            mProcessamento += "\tESPERANDO" + "\n";
            for (String e : jaAdicionados) {
                if (mFila.contains(e)) {
                    if (!mRemover.contains(e)) {
                        mProcessamento += "\t\t - " + e + "\n";
                    }
                }
            }
        }

        if (jaAdicionados.size() > 0) {
            mProcessamento += "\tNAO PROCESSAR" + "\n";
            for (String e : jaAdicionados) {
                if (!mFila.contains(e)) {
                    mProcessamento += "\t\t - " + e + "\n";
                }
            }
        }


        if (mRemover.size() > 0) {
            mProcessamento += "\tREMOVER" + "\n";

            for (String e : mRemover) {
                while (this.contem(e)) {
                    if (this.contem(e)) {
                        this.remover(e);
                    }
                }
                if (!mRemovido.contains(e)) {
                    mProcessamento += "\t\t - " + e + "\n";
                    mRemovido.add(e);
                }
            }
        }


        int mDepois = this.getTamanho();
        String mStatus = "";
        if (mAntes == mDepois) {
            mStatus = "Constante";
        } else if (mDepois > mAntes) {
            mStatus = "Aumentando";
        } else {
            mStatus = "Diminuindo";
        }

        mProcessamento += "\tREENFILEIRAR :: " + mStatus + " -->> " + mAntes + " -->> " + mDepois + "\n";

    }
}
