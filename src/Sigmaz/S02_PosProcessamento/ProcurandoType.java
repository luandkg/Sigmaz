package Sigmaz.S02_PosProcessamento;

import Sigmaz.S00_Utilitarios.*;
import Sigmaz.S00_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S00_Utilitarios.Alterador.SigmazRaiz;
import Sigmaz.S00_Utilitarios.Alterador.SigmazType;
import Sigmaz.S02_PosProcessamento.Processadores.Unificador;

import java.util.ArrayList;

public class ProcurandoType {

    private String eOrigem;
    private SigmazLocal mLocal;

    private boolean enc;
    private SigmazType mStructBase;
    private String eNomePacote;

    private boolean estaSigmaz;
    private boolean estaLocal;
    private boolean estaPacote;

    private boolean mOk;
    private String mErro;

    private Unificador mUnificador;
    private String mLocalNome;
    private SigmazPackage mLocalPackage;
    private SigmazPackage mPackage;

    public ProcurandoType(Unificador eUnificador) {
        mUnificador = eUnificador;
    }


    public void procurar(String eBase, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        eOrigem = "";
        enc = false;
        mStructBase = null;
        eNomePacote = "";

        estaSigmaz = false;
        estaLocal = false;
        estaPacote = false;
        mOk = true;
        mErro = "";
        mLocal = SigmazLocal.Sigmaz;
        mLocalNome = "";
        mLocalPackage = null;
        mPackage=null;



        for (SigmazType ss : mSigmazRaiz.getTypes()) {
            if (ss.mesmoNome(eBase)) {
                eOrigem = "Sigmaz";
                mLocal = SigmazLocal.Sigmaz;
                enc = true;
                mStructBase = ss;
                estaSigmaz = true;
                break;
            }
        }

        if (!enc) {
            for (SigmazType ss : mSigmazPackage.getTypes()) {
                if (ss.mesmoNome(eBase)) {
                    eOrigem = "Local";
                    mLocalNome = mSigmazPackage.getNome();
                    mLocalPackage = mSigmazPackage;
                    mLocal = SigmazLocal.Local;
                    enc = true;
                    mStructBase = ss;
                    eNomePacote = mSigmazPackage.getNome();
                    estaLocal = true;
                    break;
                }
            }
        }

        for (String eRefer : mSigmazPackage.getRefers()) {


            boolean ok = existePacote(eRefer, mPacotes);
            if (ok) {

                SigmazPackage mPacote = getPacote(eRefer, mPacotes);

                for (SigmazType ss : mPacote.getTypes()) {

                    if (ss.mesmoNome(eBase)) {
                        eOrigem = "Package -> " + mPacote.getNome();
                        mLocal = SigmazLocal.Package;
                        mPackage=mPacote;

                        eNomePacote = mPacote.getNome();
                        mStructBase = ss;
                        estaPacote = true;
                        enc = true;
                        break;
                    }


                }


            } else {

                mErro = "Refer : " + eRefer + " -->> NAO ENCONTRADO !";
                mOk = false;

            }
        }

    }


    public void procurarSigmaz(String eBase, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {

        eOrigem = "";
        enc = false;
        mStructBase = null;
        eNomePacote = "";

        estaSigmaz = false;
        estaLocal = false;
        estaPacote = false;
        mOk = true;
        mErro = "";

        for (SigmazType ss : mSigmazRaiz.getTypes()) {
            if (ss.mesmoNome(eBase)) {
                eOrigem = "Sigmaz";
                mLocal = SigmazLocal.Sigmaz;

                enc = true;
                mStructBase = ss;
                estaSigmaz = true;
                break;
            }
        }


        if (!enc) {

            for (String eRefer : mSigmazRaiz.getRefers()) {

                boolean ok = existePacote(eRefer, mPacotes);
                if (ok) {

                    SigmazPackage mPacote = getPacote(eRefer, mPacotes);

                    for (SigmazType ss : mPacote.getTypes()) {


                        if (ss.mesmoNome(eBase)) {
                            eOrigem = "Package -> " + mPacote.getNome();
                            mLocal = SigmazLocal.Package;

                            eNomePacote = mPacote.getNome();
                            mPackage=mPacote;

                            mStructBase = ss;
                            estaPacote = true;
                            enc = true;
                            break;
                        }


                    }


                } else {

                    mErro = "Refer : " + eRefer + " -->> NAO ENCONTRADO !";
                    mOk = false;

                }
            }


        }

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

    public SigmazPackage getLocalPackage(){return mLocalPackage;}
    public SigmazPackage getPackage(){return mPackage;}


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

    public SigmazType getType() {
        return mStructBase;
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


    public boolean existePacote(String eRefer, ArrayList<SigmazPackage> mPacotes) {
        boolean ret = false;
        for (SigmazPackage ePacote : mPacotes) {
            if (ePacote.mesmoNome(eRefer)) {
                ret = true;
                break;
            }
        }
        return ret;
    }


    public SigmazPackage getPacote(String eRefer, ArrayList<SigmazPackage> mPacotes) {
        SigmazPackage ret = null;
        for (SigmazPackage ePacote : mPacotes) {
            if (ePacote.mesmoNome(eRefer)) {
                ret = ePacote;
                break;
            }
        }
        return ret;
    }

}
