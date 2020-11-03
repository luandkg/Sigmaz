package Sigmaz.S02_PosProcessamento;

import Sigmaz.S00_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S00_Utilitarios.Alterador.SigmazRaiz;
import Sigmaz.S00_Utilitarios.Alterador.SigmazStruct;
import Sigmaz.S02_PosProcessamento.Processadores.Heranca;

import java.util.ArrayList;

public class ProcurandoStruct {

    private String eOrigem;
    private boolean enc;
    private SigmazStruct mStructBase;
    private String eNomePacote;

    private boolean estaSigmaz;
    private boolean estaLocal;
    private boolean estaPacote;

    private boolean mOk;
    private String mErro;

    private Heranca mHeranca;

    public ProcurandoStruct(Heranca eHeranca) {
        mHeranca = eHeranca;
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

      //  mHeranca.mensagem("Procurando " + eBase);

      //  mHeranca.mensagem("SIGMAZ ");
        for (SigmazStruct ss : mSigmazRaiz.getStructs()) {
         //   mHeranca.mensagem("\t - " + ss.getNome());
        }

       // mHeranca.mensagem("LOCAL ");
        for (SigmazStruct ss : mSigmazPackage.getStructs()) {
       //     mHeranca.mensagem("\t - " + ss.getNome());
        }

       // mHeranca.mensagem("REFEREDS ");
        for (String eRefer : mSigmazPackage.getRefers()) {


            boolean ok = existePacote(eRefer, mPacotes);
            if (ok) {

                SigmazPackage mPacote = getPacote(eRefer, mPacotes);

               // mHeranca.mensagem("\t REFER : " + mPacote.getNome());

                for (SigmazStruct ss : mPacote.getStructs()) {

                 //   mHeranca.mensagem("\t\t - " + ss.getNome());

                }

            }
        }


        for (SigmazStruct ss : mSigmazRaiz.getStructs()) {
            if (ss.mesmoNome(eBase)) {
                eOrigem = "Sigmaz";
                enc = true;
                mStructBase = ss;
                estaSigmaz = true;
                break;
            }
        }

        if (!enc) {
            for (SigmazStruct ss : mSigmazPackage.getStructs()) {
                if (ss.mesmoNome(eBase)) {
                    eOrigem = "Local";
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

                for (SigmazStruct ss : mPacote.getStructs()) {

                    if (ss.mesmoNome(eBase)) {
                        eOrigem = "Package -> " + mPacote.getNome();
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

        for (SigmazStruct ss : mSigmazRaiz.getStructs()) {
            if (ss.mesmoNome(eBase)) {
                eOrigem = "Sigmaz";
                enc = true;
                mStructBase = ss;
                estaSigmaz = true;
                break;
            }
        }


        if (!enc) {

            for (String eRefer : mSigmazRaiz.getRefers()) {

            //    mHeranca.mensagem("Procurando Struct : " + eBase + " em " + eRefer);

                boolean ok = existePacote(eRefer, mPacotes);
                if (ok) {

                    SigmazPackage mPacote = getPacote(eRefer, mPacotes);

                    for (SigmazStruct ss : mPacote.getStructs()) {


                        if (ss.mesmoNome(eBase)) {
                            eOrigem = "Package -> " + mPacote.getNome();
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

    public SigmazStruct getStruct() {
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
