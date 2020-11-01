package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class SigmazExternal {

    private AST mAST;
    private Organizador mOrganizador;

    private ArrayList<String> mCampos;

    private ArrayList<String> mActions;
    private ArrayList<String> mFunctions;
    private ArrayList<String> mActionsFunctions;


    public SigmazExternal(AST eAST) {

        mAST = eAST;
        mOrganizador = new Organizador();


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

            } else if (mCorrente.mesmoTipo("DEFINE")) {

                if (mCorrente.getBranch("VISIBILITY").mesmoNome("EXPLICIT")) {
                    mCampos.add(mCorrente.getNome());
                }

            } else if (mCorrente.mesmoTipo("MOCKIZ")) {

                if (mCorrente.getBranch("VISIBILITY").mesmoNome("EXPLICIT")) {
                    mCampos.add(mCorrente.getNome());
                }
            }

        }


    }

    public String getNome() {
        return mAST.getNome();
    }

    public boolean mesmoNome(String eNome) {
        return getNome().contentEquals(eNome);
    }

    public String getDefinicao() {
        return mAST.getNome();
    }

    public int getContagem() {
        int r = 1;


        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DEFINE")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                r += 1;
            }
        }
        return r;
    }



    public ArrayList<SigmazDefine> getDefines() {

        ArrayList<SigmazDefine> mLista = new ArrayList<SigmazDefine>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                mLista.add(new SigmazDefine(Sub2));
            }

        }

        return mLista;

    }


    public ArrayList<SigmazMockiz> getMockizes() {

        ArrayList<SigmazMockiz> mLista = new ArrayList<SigmazMockiz>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("MOCKIZ")) {
                mLista.add(new SigmazMockiz(Sub2));
            }

        }

        return mLista;

    }


    public ArrayList<SigmazAction> getAction() {
        ArrayList<SigmazAction> mLista = new ArrayList<SigmazAction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                SigmazAction mCorrente = new SigmazAction(Sub2);
                mLista.add(mCorrente);

            }
        }
        return mLista;
    }

    public ArrayList<SigmazFunction> getFunctions() {
        ArrayList<SigmazFunction> mLista = new ArrayList<SigmazFunction>();

        for (AST Sub2 : mAST.getBranch("BODY").getASTS()) {
            if (Sub2.mesmoTipo("FUNCTION")) {
                SigmazFunction mCorrente = new SigmazFunction(Sub2);
                mLista.add(mCorrente);

            }
        }
        return mLista;
    }


    public boolean temDefinesOuMockizes() {
        return mOrganizador.temDefinesOuMockizes(getDefines(), getMockizes());
    }

    public boolean temActionsOuFunctions() {
        return mOrganizador.temActionsOuFunctions(getAction(), getFunctions());
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



}
