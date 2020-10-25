package Sigmaz.S00_Utilitarios;

import java.util.ArrayList;

public class SigmazPackage {



    private AST mSigmazPackage;

    private ArrayList<SigmazStruct> mStructs;
    private ArrayList<SigmazType> mTypes;


    public String getNome() {
        return mSigmazPackage.getNome();
    }

    public boolean mesmoNome(String eNome){return getNome().contentEquals(eNome);}


    public SigmazPackage(AST eSigmazRaiz) {
        mSigmazPackage = eSigmazRaiz;

        mStructs = new ArrayList<SigmazStruct>();
        mTypes = new ArrayList<SigmazType>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("STRUCT")) {
                    mStructs.add(new SigmazStruct(mStruct));
                } else if (mStruct.getBranch("EXTENDED").mesmoNome("TYPE")) {
                    mTypes.add(new SigmazType(mStruct));
                } else if (mStruct.getBranch("EXTENDED").mesmoNome("TYPE_UNION")) {
                    mTypes.add(new SigmazType(mStruct));
                }

            }
        }

    }


    public ArrayList<String> getRefers() {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST mStruct : mSigmazPackage.getASTS()) {
            if (mStruct.mesmoTipo("REFER")) {
                mRefers.add(mStruct.getNome());
            }

        }

        return mRefers;

    }

    public ArrayList<SigmazStruct> getStructs() {
        return mStructs;
    }

    public ArrayList<SigmazType> getTypes() {
        return mTypes;
    }


    public ArrayList<SigmazStruct> getStructs_Completa() {
        ArrayList<SigmazStruct> mStructs_Completa = new ArrayList<SigmazStruct>();

        for (SigmazStruct mStruct : mStructs) {
            if (mStruct.isCompleta()) {
                mStructs_Completa.add(mStruct);
            }
        }


        return mStructs_Completa;
    }

    public ArrayList<SigmazStruct> getStructs_Incompleta() {
        ArrayList<SigmazStruct> mStructs_Incompleta = new ArrayList<SigmazStruct>();

        for (SigmazStruct mStruct : mStructs) {
            if (mStruct.isIncompleta()) {
                mStructs_Incompleta.add(mStruct);
            }
        }


        return mStructs_Incompleta;
    }




}