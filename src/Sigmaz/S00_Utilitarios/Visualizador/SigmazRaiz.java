package Sigmaz.S00_Utilitarios.Visualizador;

import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class SigmazRaiz {

    private AST mSigmazRaiz;


    public SigmazRaiz(AST eSigmazRaiz) {
        mSigmazRaiz = eSigmazRaiz;
    }


    public ArrayList<String> getRefers() {

        ArrayList<String> mRefers = new ArrayList<String>();

        for (AST mStruct : mSigmazRaiz.getASTS()) {
            if (mStruct.mesmoTipo("REFER")) {
                mRefers.add(mStruct.getNome());
            }

        }

        return mRefers;

    }

    public ArrayList<SigmazStruct> getStructs() {
        ArrayList<SigmazStruct> mLista = new ArrayList<SigmazStruct>();

        for (AST mStruct : mSigmazRaiz.getASTS()) {
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

        for (AST mStruct : mSigmazRaiz.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("TYPE")) {
                    mLista.add(new SigmazType(mStruct));
                }

            }
        }
        return mLista;
    }

    public ArrayList<SigmazStages> getStages() {
        ArrayList<SigmazStages> mLista = new ArrayList<SigmazStages>();

        for (AST mStruct : mSigmazRaiz.getASTS()) {
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

        for (AST mStruct : mSigmazRaiz.getASTS()) {
            if (mStruct.mesmoTipo("CAST")) {

                mLista.add(new SigmazCast(mStruct));

            }
        }
        return mLista;
    }

    public ArrayList<SigmazModel> getModelos() {
        ArrayList<SigmazModel> mLista = new ArrayList<SigmazModel>();

        for (AST mStruct : mSigmazRaiz.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("MODEL")) {
                    mLista.add(new SigmazModel(mStruct));
                }

            }
        }
        return mLista;
    }

    public ArrayList<SigmazExternal> getExternals() {
        ArrayList<SigmazExternal> mLista = new ArrayList<SigmazExternal>();

        for (AST mStruct : mSigmazRaiz.getASTS()) {
            if (mStruct.mesmoTipo("STRUCT")) {

                if (mStruct.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {
                    mLista.add(new SigmazExternal(mStruct));
                }

            }
        }
        return mLista;
    }


    public int getContagem() {
        int r = 1;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                r += 1;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("DEFINE")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                r += 1;
            } else if (Sub2.mesmoTipo("GETTER")) {
                r += 1;
            } else if (Sub2.mesmoTipo("SETTER")) {
                r += 1;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                r += 1;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                r += 1;
            } else if (Sub2.mesmoTipo("MARK")) {
                r += 1;
            }
        }
        return r;
    }

    public void tem() {

        boolean tem_G1 = false;
        boolean tem_G2 = false;
        boolean tem_G3 = false;
        boolean tem_G4 = false;
        boolean tem_G5 = false;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_G5 = true;
            }

        }

    }

    public boolean tem_DefineMockiz() {

        boolean tem_G1 = false;
        boolean tem_G2 = false;
        boolean tem_G3 = false;
        boolean tem_G4 = false;
        boolean tem_G5 = false;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_G5 = true;
            }

        }

        return tem_G1;
    }

    public boolean tem_ActionFunction() {

        boolean tem_G1 = false;
        boolean tem_G2 = false;
        boolean tem_G3 = false;
        boolean tem_G4 = false;
        boolean tem_G5 = false;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_G5 = true;
            }

        }

        return tem_G2;
    }

    public boolean tem_DirectorOperator() {

        boolean tem_G1 = false;
        boolean tem_G2 = false;
        boolean tem_G3 = false;
        boolean tem_G4 = false;
        boolean tem_G5 = false;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_G5 = true;
            }

        }

        return tem_G3;
    }

    public boolean tem_AutoFunctor() {

        boolean tem_G1 = false;
        boolean tem_G2 = false;
        boolean tem_G3 = false;
        boolean tem_G4 = false;
        boolean tem_G5 = false;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_G5 = true;
            }

        }

        return tem_G4;
    }

    public boolean tem_Marker() {

        boolean tem_G1 = false;
        boolean tem_G2 = false;
        boolean tem_G3 = false;
        boolean tem_G4 = false;
        boolean tem_G5 = false;

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("MOCKIZ")) {
                tem_G1 = true;
            } else if (Sub2.mesmoTipo("ACTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("FUNCTION")) {
                tem_G2 = true;
            } else if (Sub2.mesmoTipo("DIRECTOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("OPERATOR")) {
                tem_G3 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                tem_G4 = true;
            } else if (Sub2.mesmoTipo("MARK")) {
                tem_G5 = true;
            }

        }

        return tem_G5;
    }

    public ArrayList<SigmazDefine> getDefines() {

        ArrayList<SigmazDefine> mLista = new ArrayList<SigmazDefine>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DEFINE")) {
                mLista.add(new SigmazDefine(Sub2));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazMockiz> getMockizes() {

        ArrayList<SigmazMockiz> mLista = new ArrayList<SigmazMockiz>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("MOCKIZ")) {
                mLista.add(new SigmazMockiz(Sub2));
            }
        }

        return mLista;
    }


    public ArrayList<SigmazAction> getActions() {

        ArrayList<SigmazAction> mLista = new ArrayList<SigmazAction>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("ACTION")) {
                mLista.add(new SigmazAction(Sub2));
            }
        }

        return mLista;
    }


    public ArrayList<SigmazFunction> getFunctions() {

        ArrayList<SigmazFunction> mLista = new ArrayList<SigmazFunction>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("FUNCTION")) {
                mLista.add(new SigmazFunction(Sub2));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazAuto> getAutos() {

        ArrayList<SigmazAuto> mLista = new ArrayList<SigmazAuto>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("PROTOTYPE_AUTO")) {
                mLista.add(new SigmazAuto(Sub2));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazFunctor> getFunctores() {

        ArrayList<SigmazFunctor> mLista = new ArrayList<SigmazFunctor>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                mLista.add(new SigmazFunctor(Sub2));
            }
        }

        return mLista;
    }


    public ArrayList<SigmazDirector> getDirectors() {

        ArrayList<SigmazDirector> mLista = new ArrayList<SigmazDirector>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("DIRECTOR")) {
                mLista.add(new SigmazDirector(Sub2));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazOperator> getOperators() {

        ArrayList<SigmazOperator> mLista = new ArrayList<SigmazOperator>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("OPERATOR")) {
                mLista.add(new SigmazOperator(Sub2));
            }
        }

        return mLista;
    }

    public ArrayList<SigmazMark> getMarks() {

        ArrayList<SigmazMark> mLista = new ArrayList<SigmazMark>();

        for (AST Sub2 : mSigmazRaiz.getASTS()) {
            if (Sub2.mesmoTipo("MARK")) {
                mLista.add(new SigmazMark(Sub2));
            }
        }

        return mLista;
    }

}