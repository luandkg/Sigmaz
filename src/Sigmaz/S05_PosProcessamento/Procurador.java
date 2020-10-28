package Sigmaz.S05_PosProcessamento;

import Sigmaz.S00_Utilitarios.Alterador.SigmazPackage;
import Sigmaz.S00_Utilitarios.Alterador.SigmazModel;
import Sigmaz.S00_Utilitarios.Alterador.SigmazRaiz;
import Sigmaz.S00_Utilitarios.Alterador.SigmazStruct;

import java.util.ArrayList;

public class Procurador {


    public ProcurandoModelo procurarModelo_Sigmaz(SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes, String eModelo) {

        ProcurandoModelo mProcurandoModelo = new ProcurandoModelo();


        for (SigmazModel ss : mSigmazRaiz.getModelos()) {

            if (ss.mesmoNome(eModelo)) {

                mProcurandoModelo.setSigmaz(ss);

                break;
            }
        }


        if (!mProcurandoModelo.getEncontrado()) {

            for (String eRefer : mSigmazRaiz.getRefers()) {

                boolean ok = existePacote(eRefer, mPacotes);
                if (ok) {

                    SigmazPackage mPacote = getPacote(eRefer, mPacotes);

                    for (SigmazModel ss : mPacote.getModelos()) {

                        if (ss.mesmoNome(eModelo)) {

                            mProcurandoModelo.setLocal(mPacote, ss);

                            break;
                        }


                    }


                } else {


                    mProcurandoModelo.setErro("Refer : " + eRefer + " -->> NAO ENCONTRADO !");

                }
            }


        }


        return mProcurandoModelo;

    }


    public ProcurandoModelo procurarModelo_Package(String eModelo, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        ProcurandoModelo mProcurandoModelo = new ProcurandoModelo();


        for (SigmazModel ss : mSigmazRaiz.getModelos()) {
            if (ss.mesmoNome(eModelo)) {

                mProcurandoModelo.setSigmaz(ss);

                break;
            }
        }

        if (!mProcurandoModelo.getEncontrado()) {
            for (SigmazModel ss : mSigmazPackage.getModelos()) {
                if (ss.mesmoNome(eModelo)) {

                    mProcurandoModelo.setLocal(mSigmazPackage, ss);

                    break;
                }
            }
        }

        for (String eRefer : mSigmazPackage.getRefers()) {


            boolean ok = existePacote(eRefer, mPacotes);
            if (ok) {

                SigmazPackage mPacote = getPacote(eRefer, mPacotes);

                for (SigmazModel ss : mPacote.getModelos()) {

                    if (ss.mesmoNome(eModelo)) {

                        mProcurandoModelo.setPackage(mPacote, ss);


                        break;
                    }


                }


            } else {

                mProcurandoModelo.setErro("Refer : " + eRefer + " -->> NAO ENCONTRADO !");

            }
        }

        return mProcurandoModelo;
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
