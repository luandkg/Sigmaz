package Sigmaz.S01_Compilador.C01_Enfileirador;

import java.util.ArrayList;

public class Enfileirador {

    private ArrayList<String> mFila;
    private int mTamanho;
    private int mAntes;
    private int mEtapas;

    private String mProcessamento;

    private ArrayList<String> mRemover;
    private ArrayList<String> mAdicionar;
    private ArrayList<String> mRemovido;

    private ArrayList<String> mProcessados;
    private String mProcessandoCorrente;
    private String mCodigo;
    private boolean mPorCodigo;

    public Enfileirador() {

        mFila = new ArrayList<String>();
        mTamanho = 0;
        mProcessamento = "";
        mProcessandoCorrente = "";

        mAdicionar = new ArrayList<String>();
        mRemover = new ArrayList<String>();
        mRemovido = new ArrayList<String>();

        mProcessados = new ArrayList<String>();
        mEtapas = 0;
        mCodigo = "";
        mPorCodigo = false;
    }

    public void setCodigo(String eCodigo) {
        mCodigo = eCodigo;
        mPorCodigo = true;
    }

    public boolean estaPorCodigo(){return mPorCodigo;}

    public String getCodigo(){return mCodigo;}

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
        mProcessamento += "\nTERMINOU :: " + mEtapas + "\n";
    }

    public void iniciar() {

        mTamanho = getTamanho();
        mProcessamento = "";
        mProcessandoCorrente = "";

        mAntes = 0;
        mEtapas = 0;

        mAdicionar = new ArrayList<String>();
        mRemover = new ArrayList<String>();
        mRemovido = new ArrayList<String>();
        mProcessados = new ArrayList<String>();

        mProcessamento = "################################ PLANO DE COMPILACAO ################################ ";
        mProcessamento += "\n";
        mProcessamento += "\n";

        mProcessamento += "INICIAR COM : \n";

        for (String ea : mFila) {
            mProcessamento += "\t - " + ea + "\n";
        }

        mProcessamento += "\n";

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


    public boolean isTerminou() {
        return getTamanho() == 0;
    }

    public String processar() {

        mAntes = getTamanho();

        if (mAntes > 0) {
            mEtapas += 1;

            String mCorrente = obter(0);
            if (mFila.size() > 0) {

                mProcessamento += "ETAPA :: " + mEtapas + "\n";

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
