package Sigmaz.S00_Utilitarios;

import Sigmaz.S00_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S00_Utilitarios.SigmazLocal;
import Sigmaz.S00_Utilitarios.Alterador.SigmazModel;
import Sigmaz.S02_PosProcessamento.Processadores.Modelador;

public class ObjetoProcurado<T> {

    private String eOrigem;
    private SigmazLocal mLocal;

    private boolean enc;
    private T mModelo;
    private String eNomePacote;

    private boolean estaSigmaz;
    private boolean estaLocal;
    private boolean estaPacote;

    private boolean mOk;
    private String mErro;

    private String mLocalNome;
    private SigmazPackage mLocalPackage;
    private SigmazPackage mPackage;

    public ObjetoProcurado() {


        enc = false;
        estaSigmaz = false;
        estaLocal = false;
        estaPacote = false;
        mOk = false;

        mLocalNome = "";
        eNomePacote = "";

        mLocalPackage = null;

    }

    public void setSigmaz(T eT) {

        eOrigem = "Sigmaz";
        mLocal = SigmazLocal.Sigmaz;

        enc = true;
        mModelo = eT;
        estaSigmaz = true;
        mOk = true;

    }

    public void setLocal(SigmazPackage mPacote, T eT) {

        eOrigem = "Package -> " + mPacote.getNome();
        mLocal = SigmazLocal.Package;

        eNomePacote = mPacote.getNome();
        mPackage = mPacote;

        mModelo = eT;
        estaPacote = true;
        enc = true;
        mOk = true;

    }


    public void setPackage(SigmazPackage mPacote, T eT) {

        eOrigem = "Package -> " + mPacote.getNome();
        eNomePacote = mPacote.getNome();
        mModelo = eT;
        estaPacote = true;
        enc = true;
        mOk = true;

    }


    public void setErro(String eErro) {

        mErro = eErro;
        mOk = false;

    }

    public SigmazLocal getLocal() {
        return mLocal;
    }

    public boolean isSigmaz() {
        return mLocal == SigmazLocal.Sigmaz;
    }

    public boolean isLocal() {
        return mLocal == SigmazLocal.Local;
    }

    public boolean isPackage() {
        return mLocal == SigmazLocal.Package;
    }

    public String getLocalNome() {
        return mLocalNome;
    }

    public String getLocalOrigem() {
        String m = "";


        if (mLocal == SigmazLocal.Sigmaz) {
            m = "Sigmaz";
        } else if (mLocal == SigmazLocal.Local) {
            m = "Local";
        } else if (mLocal == SigmazLocal.Package) {
            m = "Package";
        }

        return m;
    }

    public SigmazPackage getLocalPackage() {
        return mLocalPackage;
    }

    public SigmazPackage getPackage() {
        return mPackage;
    }


    public boolean getOk() {
        return mOk;
    }

    public String getErro() {
        return mErro;
    }

    public boolean getEncontrado() {
        return enc;
    }

    public String getOrigem() {
        return eOrigem;
    }

    public T getObjeto() {
        return mModelo;
    }

    public String getPacote() {
        return eNomePacote;
    }

    public boolean getEstaSigmaz() {
        return estaSigmaz;
    }

    public boolean getEstaLocal() {
        return estaLocal;
    }

    public boolean getEstaPacote() {
        return estaPacote;
    }
}
