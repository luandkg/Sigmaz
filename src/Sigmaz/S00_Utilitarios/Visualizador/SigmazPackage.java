package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class SigmazPackage {


    private AST mSigmazPackage;



    public String getNome() {
        return mSigmazPackage.getNome();
    }

    public boolean mesmoNome(String eNome){return getNome().contentEquals(eNome);}


    public SigmazPackage(AST eSigmazRaiz) {
        mSigmazPackage = eSigmazRaiz;

    }

    public AST getAST(){ return mSigmazPackage; }


    public ArrayList<String> getRefers() {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("REFER")) {
                mRefers.add(mStruct.getNome());
            }

        }

        return mRefers;

    }



    public ArrayList<SigmazAction> getActions() {
        ArrayList<SigmazAction> mLista = new ArrayList<SigmazAction>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("ACTION")) {
                mLista.add(new SigmazAction(a));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazFunction> getFunctions() {
        ArrayList<SigmazFunction> mLista = new ArrayList<SigmazFunction>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("FUNCTION")) {
                mLista.add(new SigmazFunction(a));
            }
        }

        return mLista;
    }


    public ArrayList<SigmazAuto> getAutos() {
        ArrayList<SigmazAuto> mLista = new ArrayList<SigmazAuto>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("PROTYPE_AUTO")) {
                mLista.add(new SigmazAuto(a));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazFunctor> getFunctors() {
        ArrayList<SigmazFunctor> mLista = new ArrayList<SigmazFunctor>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("PROTYPE_FUNCTOR")) {
                mLista.add(new SigmazFunctor(a));
            }
        }

        return mLista;
    }



    public ArrayList<SigmazDirector> getDirectors() {
        ArrayList<SigmazDirector> mLista = new ArrayList<SigmazDirector>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("DIRECTOR")) {
                mLista.add(new SigmazDirector(a));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazOperator> getOperators() {
        ArrayList<SigmazOperator> mLista = new ArrayList<SigmazOperator>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("OPERATOR")) {
                mLista.add(new SigmazOperator(a));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazMark> getMarks() {
        ArrayList<SigmazMark> mLista = new ArrayList<SigmazMark>();

        for (AST a : mSigmazPackage.getASTS()) {
            if (a.mesmoTipo("MARK")) {
                mLista.add(new SigmazMark(a));
            }
        }

        return mLista;
    }


    public ArrayList<SigmazStruct> getStructs() {
        ArrayList<SigmazStruct> mLista = new ArrayList<SigmazStruct>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("STRUCT")) {
                    mLista.add(new SigmazStruct(mStruct));
                }

            }
        }
        return mLista;
    }

    public ArrayList<SigmazType> getTypes() {
        ArrayList<SigmazType> mLista = new ArrayList<SigmazType>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("TYPE")) {
                    mLista.add(new SigmazType(mStruct));
                }

            }
        }
        return mLista;
    }

    public ArrayList<SigmazModel> getModelos() {
        ArrayList<SigmazModel> mLista = new ArrayList<SigmazModel>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("MODEL")) {
                    mLista.add(new SigmazModel(mStruct));
                }

            }
        }
        return mLista;
    }

    public ArrayList<SigmazStages> getStages() {
        ArrayList<SigmazStages> mLista = new ArrayList<SigmazStages>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("STAGES")) {
                    mLista.add(new SigmazStages(mStruct));
                }

            }
        }
        return mLista;
    }

    public ArrayList<SigmazCast> getCasts() {
        ArrayList<SigmazCast> mLista = new ArrayList<SigmazCast>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("CAST")) {

                mLista.add(new SigmazCast(mStruct));

            }
        }
        return mLista;
    }

    public ArrayList<SigmazModel> getModels() {
        ArrayList<SigmazModel> mLista = new ArrayList<SigmazModel>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("MODEL")) {

                mLista.add(new SigmazModel(mStruct));

            }
        }
        return mLista;
    }

    public ArrayList<SigmazExternal> getExternals() {
        ArrayList<SigmazExternal> mLista = new ArrayList<SigmazExternal>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {
                    mLista.add(new SigmazExternal(mStruct));
                }

            }
        }
        return mLista;
    }



}