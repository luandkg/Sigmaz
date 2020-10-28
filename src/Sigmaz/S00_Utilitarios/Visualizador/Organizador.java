package Sigmaz.S00_Utilitarios.Visualizador;

import java.util.ArrayList;

public class Organizador {


    public boolean temDefines(ArrayList<SigmazDefine> mDefines) {
        boolean m = false;

        for (SigmazDefine e : mDefines) {
            m = true;
            break;
        }

        return m;
    }

    public boolean temDefines_Internos(ArrayList<SigmazDefine> mDefines) {
        boolean m = false;

        for (SigmazDefine e : mDefines) {
            if (e.isAll()) {
                m = true;
                break;
            } else if (e.isRestrict()) {
                m = true;
                break;
            }
        }

        return m;
    }

    public boolean temMockizes(ArrayList<SigmazMockiz> mMockizes) {
        boolean m = false;

        for (SigmazMockiz e : mMockizes) {
            m = true;
            break;
        }

        return m;

    }


    public boolean temMockizes_Internos(ArrayList<SigmazMockiz> mMockizes) {
        boolean m = false;

        for (SigmazMockiz e : mMockizes) {
            if (e.isAll()) {
                m = true;
                break;
            } else if (e.isRestrict()) {
                m = true;
                break;
            }
        }

        return m;

    }

    public boolean temDefinesOuMockizes_Internos(ArrayList<SigmazDefine> mDefines, ArrayList<SigmazMockiz> mMockizes) {

        boolean m = false;

        for (SigmazDefine e : mDefines) {
            if (e.isAll()) {
                m = true;
                break;
            } else if (e.isRestrict()) {
                m = true;
                break;
            }
        }

        if (!m) {


            for (SigmazMockiz e : mMockizes) {
                if (e.isAll()) {
                    m = true;
                    break;
                } else if (e.isRestrict()) {
                    m = true;
                    break;
                }
            }


        }

        return m;

    }


    public boolean temDefines_Externos(ArrayList<SigmazDefine> mDefines) {
        boolean m = false;

        for (SigmazDefine e : mDefines) {
            if (e.isImplicit()) {
                m = true;
                break;
            } else if (e.isExplicit()) {
                m = true;
                break;
            }
        }

        return m;
    }

    public boolean temMockizes_Externos(ArrayList<SigmazMockiz> mMockizes) {
        boolean m = false;

        for (SigmazMockiz e : mMockizes) {
            if (e.isImplicit()) {
                m = true;
                break;
            } else if (e.isExplicit()) {
                m = true;
                break;
            }
        }

        return m;

    }

    public boolean temDefinesOuMockizes_Externos(ArrayList<SigmazDefine> mDefines, ArrayList<SigmazMockiz> mMockizes) {

        boolean m = false;

        for (SigmazDefine e : mDefines) {
            if (e.isImplicit()) {
                m = true;
                break;
            } else if (e.isExplicit()) {
                m = true;
                break;
            }
        }

        if (!m) {


            for (SigmazMockiz e : mMockizes) {
                if (e.isImplicit()) {
                    m = true;
                    break;
                } else if (e.isExplicit()) {
                    m = true;
                    break;
                }
            }


        }

        return m;

    }

    public boolean temDefinesOuMockizes(ArrayList<SigmazDefine> mDefines, ArrayList<SigmazMockiz> mMockizes) {

        boolean m = false;

        for (SigmazDefine e : mDefines) {
            m = true;
            break;
        }

        if (!m) {


            for (SigmazMockiz e : mMockizes) {
                m = true;
                break;
            }


        }

        return m;

    }


    public boolean temActions(ArrayList<SigmazAction> mActions) {
        boolean m = false;

        for (SigmazAction e : mActions) {
            m = true;
            break;
        }

        return m;
    }

    public boolean temActions_Internos(ArrayList<SigmazAction> mActions) {
        boolean m = false;

        for (SigmazAction e : mActions) {
            if (e.isAll()) {
                m = true;
                break;
            } else if (e.isRestrict()) {
                m = true;
                break;
            }
        }

        return m;
    }

    public boolean temFunctions(ArrayList<SigmazFunction> mFunctions) {
        boolean m = false;

        for (SigmazFunction e : mFunctions) {
            m = true;
            break;
        }

        return m;

    }

    public boolean temFunctions_Internos(ArrayList<SigmazFunction> mFunctions) {
        boolean m = false;

        for (SigmazFunction e : mFunctions) {
            if (e.isAll()) {
                m = true;
                break;
            } else if (e.isRestrict()) {
                m = true;
                break;
            }
        }

        return m;

    }

    public boolean temActionsOuFunctions_Internos(ArrayList<SigmazAction> mActions, ArrayList<SigmazFunction> mFunctions) {

        boolean m = false;

        for (SigmazAction e : mActions) {
            if (e.isAll()) {
                m = true;
                break;
            } else if (e.isRestrict()) {
                m = true;
                break;
            }
        }

        if (!m) {


            for (SigmazFunction e : mFunctions) {
                if (e.isAll()) {
                    m = true;
                    break;
                } else if (e.isRestrict()) {
                    m = true;
                    break;
                }
            }


        }

        return m;

    }


    public boolean temActions_Externos(ArrayList<SigmazAction> mActions) {
        boolean m = false;

        for (SigmazAction e : mActions) {
            if (e.isImplicit()) {
                m = true;
                break;
            } else if (e.isExplicit()) {
                m = true;
                break;
            }
        }

        return m;
    }

    public boolean temFunctions_Externos(ArrayList<SigmazFunction> mFunctions) {
        boolean m = false;

        for (SigmazFunction e : mFunctions) {
            if (e.isImplicit()) {
                m = true;
                break;
            } else if (e.isExplicit()) {
                m = true;
                break;
            }
        }

        return m;

    }

    public boolean temActionsOuFunctions_Externos(ArrayList<SigmazAction> mActions, ArrayList<SigmazFunction> mFunctions) {

        boolean m = false;

        for (SigmazAction e : mActions) {
            if (e.isImplicit()) {
                m = true;
                break;
            } else if (e.isExplicit()) {
                m = true;
                break;
            }
        }

        if (!m) {


            for (SigmazFunction e : mFunctions) {
                if (e.isImplicit()) {
                    m = true;
                    break;
                } else if (e.isExplicit()) {
                    m = true;
                    break;
                }
            }


        }

        return m;

    }

    public boolean temActionsOuFunctions(ArrayList<SigmazAction> mActions, ArrayList<SigmazFunction> mFunctions) {

        boolean m = false;

        for (SigmazAction e : mActions) {
            m = true;
            break;
        }

        if (!m) {


            for (SigmazFunction e : mFunctions) {
                m = true;
                break;
            }


        }

        return m;

    }


    public boolean temDirectorsOuOperators(ArrayList<SigmazDirector> mDirectors, ArrayList<SigmazOperator> mOperators) {

        boolean m = false;

        for (SigmazDirector e : mDirectors) {
            m = true;
            break;
        }

        if (!m) {


            for (SigmazOperator e : mOperators) {
                m = true;
                break;
            }


        }

        return m;

    }



    public boolean temGettersOuSetters(ArrayList<SigmazBlocoGetter> mGetters, ArrayList<SigmazBlocoSetter> mSetters) {

        boolean m = false;

        for (SigmazBlocoGetter e : mGetters) {
            m = true;
            break;
        }

        if (!m) {


            for (SigmazBlocoSetter e : mSetters) {
                m = true;
                break;
            }


        }

        return m;

    }

}
