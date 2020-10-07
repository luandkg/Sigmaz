package Sigmaz.S05_PosProcessamento.Pronoco;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Pronoco_Extern {

    private AST mAST;
    private ArrayList<String> mCampos;

    private ArrayList<String> mActions;
    private ArrayList<String> mFunctions;
    private ArrayList<String> mActionsFunctions;

    public Pronoco_Extern(AST eAST) {

        mAST = eAST;
        mCampos = new ArrayList<String>();
        mActions = new ArrayList<String>();
        mFunctions = new ArrayList<String>();
        mActionsFunctions = new ArrayList<String>();


        for (AST mCorrente : mAST.getBranch("BODY").getASTS()) {

            if (mCorrente.mesmoTipo("ACTION")) {

                if (mCorrente.getBranch("VISIBILITY").mesmoNome("EXPLICIT")) {
                    mActions.add(mCorrente.getNome());
                    mActionsFunctions.add(mCorrente.getNome());
                }

            } else if (mCorrente.mesmoTipo("FUNCTION")) {

                if (mCorrente.getBranch("VISIBILITY").mesmoNome("EXPLICIT")) {
                    mFunctions.add(mCorrente.getNome());
                    mActionsFunctions.add(mCorrente.getNome());
                }

            }

        }


    }


    public boolean existeCampo(String eObjeto) {
        return mCampos.contains(eObjeto);
    }


    public boolean existeFunction(String eObjeto) {
        return mFunctions.contains(eObjeto);
    }

    public boolean existeAction(String eObjeto) {
        return mActions.contains(eObjeto);
    }

    public boolean existeActionFunction(String eObjeto) {
        return mActionsFunctions.contains(eObjeto);
    }


    public String getNome() {
        return mAST.getNome();
    }


}
