package Sigmaz.S02_PosProcessamento;

import Sigmaz.S00_Utilitarios.Alterador.*;

import java.util.ArrayList;

public class Procurador {


    public ObjetoProcurado<SigmazModel> procurarModelo_Sigmaz(SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes, String eModelo) {

        ObjetoProcurado mProcurandoModelo = new ObjetoProcurado();


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

    public ObjetoProcurado<SigmazModel> procurarModelo_Package(String eModelo, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        ObjetoProcurado mProcurandoModelo = new ObjetoProcurado();


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


    public ObjetoProcurado<SigmazType> procurarType_Sigmaz(SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes, String eModelo) {

        ObjetoProcurado<SigmazType> mProcurandoModelo = new ObjetoProcurado();


        for (SigmazType ss : mSigmazRaiz.getTypes()) {

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

                    for (SigmazType ss : mPacote.getTypes()) {

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

    public ObjetoProcurado<SigmazType> procurarType_Package(String eModelo, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        ObjetoProcurado<SigmazType> mProcurandoModelo = new ObjetoProcurado();


        for (SigmazType ss : mSigmazRaiz.getTypes()) {
            if (ss.mesmoNome(eModelo)) {

                mProcurandoModelo.setSigmaz(ss);

                break;
            }
        }

        if (!mProcurandoModelo.getEncontrado()) {
            for (SigmazType ss : mSigmazPackage.getTypes()) {
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

                for (SigmazType ss : mPacote.getTypes()) {

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


    public ObjetoProcurado<SigmazStruct> procurarStruct_Sigmaz(String eNome, SigmazRaiz mSigmazRaiz, ArrayList<SigmazPackage> mPacotes) {

        ObjetoProcurado<SigmazStruct> mProcurandoModelo = new ObjetoProcurado();


        for (SigmazStruct ss : mSigmazRaiz.getStructs()) {

            if (ss.mesmoNome(eNome)) {

                mProcurandoModelo.setSigmaz(ss);

                break;
            }
        }


        if (!mProcurandoModelo.getEncontrado()) {

            for (String eRefer : mSigmazRaiz.getRefers()) {

                boolean ok = existePacote(eRefer, mPacotes);
                if (ok) {

                    SigmazPackage mPacote = getPacote(eRefer, mPacotes);

                    for (SigmazStruct ss : mPacote.getStructs()) {

                        if (ss.mesmoNome(eNome)) {

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

    public ObjetoProcurado<SigmazStruct> procurarStruct_Package(String eModelo, SigmazRaiz mSigmazRaiz, SigmazPackage mSigmazPackage, ArrayList<SigmazPackage> mPacotes) {

        ObjetoProcurado<SigmazStruct> mProcurandoModelo = new ObjetoProcurado();


        for (SigmazStruct ss : mSigmazRaiz.getStructs()) {
            if (ss.mesmoNome(eModelo)) {

                mProcurandoModelo.setSigmaz(ss);

                break;
            }
        }

        if (!mProcurandoModelo.getEncontrado()) {
            for (SigmazStruct ss : mSigmazPackage.getStructs()) {
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

                for (SigmazStruct ss : mPacote.getStructs()) {

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
