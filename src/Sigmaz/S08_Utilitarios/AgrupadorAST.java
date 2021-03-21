package Sigmaz.S08_Utilitarios;

import Sigmaz.S01_Compilador.Orquestrantes;

import java.util.ArrayList;

public class AgrupadorAST {

    public void agruparCalls(AST eASTPai, ArrayList<AST> mCalls) {

        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("CALL")) {

                mCalls.add(mAST);

            }

        }

    }

    public void agruparActionsFunctions(AST eASTPai, ArrayList<AST> mActions, ArrayList<AST> mFunctions) {

        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("ACTION")) {

                mActions.add(mAST);

            } else if (mAST.mesmoTipo("FUNCTION")) {

                mFunctions.add(mAST);

            }

        }

    }

    public void agruparAutosFunctores(AST eASTPai, ArrayList<AST> mAutos, ArrayList<AST> mFunctores) {

        for (AST mAST : eASTPai.getASTS()) {

            if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {

                mAutos.add(mAST);

            } else if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                mFunctores.add(mAST);

            }

        }

    }


    public void agruparTipos(AST eASTPai, ArrayList<AST> mStages, ArrayList<AST> mTypes, ArrayList<AST> mStructs, ArrayList<AST> mExternals, ArrayList<AST> mModels) {

        for (AST mAST : eASTPai.getASTS()) {


            if (mAST.mesmoTipo(Orquestrantes.COMPLEX)) {


                if (mAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {

                    mStructs.add(mAST);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("STAGES")) {

                    mStages.add(mAST);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("TYPE")) {

                    mTypes.add(mAST);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {

                    mExternals.add(mAST);

                } else if (mAST.getBranch("EXTENDED").mesmoNome("MODEL")) {

                    mModels.add(mAST);


                }


            }

        }

    }


}
