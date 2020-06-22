package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class OO {

    private Escopo mEscopo;

    private ArrayList<AST> mStructGuardados;

    private ArrayList<Index_Action> mInits;

    private ArrayList<Index_Action> mActions;
    private ArrayList<Index_Function> mFunctions;
    private ArrayList<Index_Function> mOperations;
    private ArrayList<Index_Action> mActionFunctions;
    private ArrayList<AST> mCasts;


    public OO(Escopo eEscopo) {

        mEscopo = eEscopo;

        mStructGuardados = new ArrayList<>();

        mInits = new ArrayList<Index_Action>();

        mActions = new ArrayList<Index_Action>();
        mActionFunctions = new ArrayList<Index_Action>();
        mFunctions = new ArrayList<Index_Function>();
        mOperations = new ArrayList<Index_Function>();
        mCasts = new ArrayList<AST>();

    }

    public void guardar(AST eAST) {


        mStructGuardados.add(eAST);

        if (eAST.mesmoTipo("ACTION")) {

            Index_Action mAct = new Index_Action(eAST);
            mActions.add(mAct);
            mActionFunctions.add(mAct);

        } else if (eAST.mesmoTipo("FUNCTION")) {

            Index_Function mFunc = new Index_Function(eAST);
            mFunctions.add(mFunc);

            Index_Action mAct = new Index_Action(eAST);
            mActionFunctions.add(mAct);

        } else if (eAST.mesmoTipo("OPERATION")) {

            Index_Function mFunc = new Index_Function(eAST);
            mOperations.add(mFunc);

        } else if (eAST.mesmoTipo("CAST")) {

            mCasts.add(eAST);
        } else if (eAST.mesmoTipo("INIT")) {

            Index_Action mAct = new Index_Action(eAST);
            mInits.add(mAct);


        }

    }


    public ArrayList<AST> getStruct() {
        return mStructGuardados;
    }

    public ArrayList<Item> getStacks() {
        return mEscopo.getStacks();

    }


    public ArrayList<Index_Action> getInits() {
        return mInits;
    }

    public ArrayList<Index_Function> getFunctions() {
        return mFunctions;
    }

    public ArrayList<Index_Action> getActions() {
        return mActions;
    }

    public ArrayList<Index_Action> getActionFunctions() {
        return mActionFunctions;
    }

    public ArrayList<Index_Function> getOperations() {
        return mOperations;
    }

    public ArrayList<AST> getCasts() {
        return mCasts;
    }

}
