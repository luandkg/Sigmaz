package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class AO {

    private ArrayList<Index_Action> mActions;
    private ArrayList<Index_Function> mFunctions;
    private ArrayList<Index_Function> mOperations;
    private ArrayList<Index_Action> mActionFunctions;
    private ArrayList<AST> mCasts;

    private ArrayList<AST> mGuardados;

    private Escopo mEscopo;


    public AO(Escopo eEscopo) {

        mEscopo = eEscopo;

        mGuardados = new ArrayList<>();

        mActions = new ArrayList<Index_Action>();
        mActionFunctions = new ArrayList<Index_Action>();
        mFunctions = new ArrayList<Index_Function>();
        mOperations = new ArrayList<Index_Function>();
        mCasts = new ArrayList<AST>();

    }

    public Escopo getEscopo(){
        return mEscopo;
    }

    public ArrayList<AST> getGuardados() {
        return mGuardados;
    }

    public void guardar(AST eAST) {


        mGuardados.add(eAST);

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


        }

    }


    public ArrayList<Index_Function> getFunctions() {
        return mFunctions;
    }

    public ArrayList<Index_Function> getFunctionsCompleto() {

        ArrayList<Index_Function> gc = new ArrayList<Index_Function>();

        for (Index_Function mIndex_Function : getFunctions()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (Index_Function mIndex_Function : getEscopo().getEscopoAnterior().getFunctionsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
    }

    public ArrayList<Index_Action> getActions() {
        return mActions;
    }

    public ArrayList<Index_Action> getActionsCompleto() {

        ArrayList<Index_Action> gc = new ArrayList<Index_Action>();

        for (Index_Action mIndex_Function : getActions()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (Index_Action mIndex_Function : getEscopo().getEscopoAnterior().getActionsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
    }

    public ArrayList<Index_Action> getActionFunctions() {
        return mActionFunctions;
    }

    public ArrayList<Index_Action> getActionFunctionsCompleto() {

        ArrayList<Index_Action> gc = new ArrayList<Index_Action>();

        for (Index_Action mIndex_Function : getActionFunctions()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (Index_Action mIndex_Function : getEscopo().getEscopoAnterior().getActionFunctionsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
    }


    public ArrayList<Index_Function> getOperations() {
        return mOperations;
    }

    public ArrayList<Index_Function> getOperationsCompleto() {

        ArrayList<Index_Function> gc = new ArrayList<Index_Function>();

        for (Index_Function mIndex_Function : getOperations()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (Index_Function mIndex_Function : getEscopo().getEscopoAnterior().getOperationsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
    }

    public ArrayList<AST> getCasts() {
        return mCasts;
    }

    public ArrayList<AST> getCastsCompleto() {

        ArrayList<AST> gc = new ArrayList<AST>();

        for (AST mIndex_Function : getCasts()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (AST mIndex_Function : getEscopo().getEscopoAnterior().getCastsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
    }




    public ArrayList<AST> getGuardadosCompleto() {

        ArrayList<AST> gc = new ArrayList<AST>();

        for (AST fAST : mGuardados) {
            gc.add(fAST);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (AST fAST : getEscopo().getEscopoAnterior().getGuardadosCompleto()) {
                gc.add(fAST);
            }
        }

        return gc;
    }

}
