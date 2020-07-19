package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class OO {

    private Escopo mEscopo;
    private RunTime mRunTime;

    private ArrayList<AST> mGuardados;

    private ArrayList<AST> mStructGuardados;

    private ArrayList<Index_Action> mInits;
    private ArrayList<AST> mBases;

    private ArrayList<Index_Action> mActions;
    private ArrayList<Index_Function> mFunctions;
    private ArrayList<Index_Function> mDirectors;
    private ArrayList<Index_Function> mOperations;
    private ArrayList<Index_Action> mActionFunctions;

    private ArrayList<AST> mCasts;
    private ArrayList<AST> mStages;
    private ArrayList<AST> mTypes;
    private ArrayList<AST> mStructs;


    private ArrayList<Index_Action> mActionFunctions_All;
    private ArrayList<Index_Action> mActionFunctions_Restrict;
    private ArrayList<Index_Action> mActionFunctions_Extern;


    private ArrayList<Index_Function> mFunctions_All;
    private ArrayList<Index_Function> mFunctions_Restrict;
    private ArrayList<Index_Function> mFunctions_Extern;

    private ArrayList<Index_Action> mActions_All;
    private ArrayList<Index_Action> mActions_Restrict;
    private ArrayList<Index_Action> mActions_Extern;

    private ArrayList<Index_Function> mOperations_All;
    private ArrayList<Index_Function> mOperations_Restrict;
    private ArrayList<Index_Function> mOperations_Extern;

    private ArrayList<Index_Function> mDirectors_All;
    private ArrayList<Index_Function> mDirectors_Restrict;
    private ArrayList<Index_Function> mDirectors_Extern;

    public OO(Escopo eEscopo, RunTime eRunTime) {

        mEscopo = eEscopo;
        mRunTime = eRunTime;

        mGuardados = new ArrayList<>();

        mStructGuardados = new ArrayList<>();

        mInits = new ArrayList<Index_Action>();
        mBases = new ArrayList<AST>();

        mActions = new ArrayList<Index_Action>();
        mActionFunctions = new ArrayList<Index_Action>();
        mFunctions = new ArrayList<Index_Function>();
        mDirectors = new ArrayList<Index_Function>();
        mOperations = new ArrayList<Index_Function>();

        mCasts = new ArrayList<AST>();
        mStages = new ArrayList<AST>();
        mTypes = new ArrayList<AST>();
        mStructs = new ArrayList<AST>();


        mActionFunctions_All = new ArrayList<Index_Action>();
        mActionFunctions_Restrict = new ArrayList<Index_Action>();
        mActionFunctions_Extern = new ArrayList<Index_Action>();

        mActions_All = new ArrayList<Index_Action>();
        mActions_Restrict = new ArrayList<Index_Action>();
        mActions_Extern = new ArrayList<Index_Action>();

        mFunctions_All = new ArrayList<Index_Function>();
        mFunctions_Restrict = new ArrayList<Index_Function>();
        mFunctions_Extern = new ArrayList<Index_Function>();

        mDirectors_All = new ArrayList<Index_Function>();
        mDirectors_Restrict = new ArrayList<Index_Function>();
        mDirectors_Extern = new ArrayList<Index_Function>();

        mOperations_All = new ArrayList<Index_Function>();
        mOperations_Restrict = new ArrayList<Index_Function>();
        mOperations_Extern = new ArrayList<Index_Function>();

    }

    public void limpar(){

        mGuardados = new ArrayList<>();

        mStructGuardados = new ArrayList<>();

        mInits = new ArrayList<Index_Action>();
        mBases = new ArrayList<AST>();

        mActions = new ArrayList<Index_Action>();
        mActionFunctions = new ArrayList<Index_Action>();
        mFunctions = new ArrayList<Index_Function>();
        mDirectors = new ArrayList<Index_Function>();
        mOperations = new ArrayList<Index_Function>();

        mCasts = new ArrayList<AST>();
        mStages = new ArrayList<AST>();
        mTypes = new ArrayList<AST>();
        mStructs = new ArrayList<AST>();


        mActionFunctions_All = new ArrayList<Index_Action>();
        mActionFunctions_Restrict = new ArrayList<Index_Action>();
        mActionFunctions_Extern = new ArrayList<Index_Action>();

        mActions_All = new ArrayList<Index_Action>();
        mActions_Restrict = new ArrayList<Index_Action>();
        mActions_Extern = new ArrayList<Index_Action>();

        mFunctions_All = new ArrayList<Index_Function>();
        mFunctions_Restrict = new ArrayList<Index_Function>();
        mFunctions_Extern = new ArrayList<Index_Function>();

        mDirectors_All = new ArrayList<Index_Function>();
        mDirectors_Restrict = new ArrayList<Index_Function>();
        mDirectors_Extern = new ArrayList<Index_Function>();

        mOperations_All = new ArrayList<Index_Function>();
        mOperations_Restrict = new ArrayList<Index_Function>();
        mOperations_Extern = new ArrayList<Index_Function>();

    }



    public Escopo getEscopo() {
        return mEscopo;
    }

    public void guardar(AST eAST) {
        guardar(eAST,new ArrayList<String>());
    }

    public void guardar(AST eAST,ArrayList<String> dRefers) {


        mGuardados.add(eAST);

        mStructGuardados.add(eAST);

        if (eAST.mesmoTipo("ACTION")) {

            Index_Action mAct = new Index_Action(mRunTime,mEscopo,eAST);

            mActions.add(mAct);
            mActionFunctions.add(mAct);

            if (mAct.isExtern()) {

                mActions_Extern.add(mAct);
                mActionFunctions_Extern.add(mAct);

            } else if (mAct.isAll()) {

                mActions.add(mAct);
                mActions_All.add(mAct);

                mActionFunctions.add(mAct);
                mActionFunctions_All.add(mAct);

            } else if (mAct.isRestrict()) {

                mActions_Restrict.add(mAct);

                mActionFunctions.add(mAct);
                mActionFunctions_Restrict.add(mAct);

            }

        } else if (eAST.mesmoTipo("FUNCTION")) {

            Index_Function mFunc = new Index_Function(mRunTime,mEscopo,eAST);

            if (mFunc.isExtern()) {

                Index_Action mAct = new Index_Action(mRunTime,mEscopo,eAST);

                mFunctions_Extern.add(mFunc);
                mActionFunctions_Extern.add(mAct);

            } else if (mFunc.isAll()) {

                mFunctions.add(mFunc);
                mFunctions_All.add(mFunc);

                Index_Action mAct = new Index_Action(mRunTime,mEscopo,eAST);
                mActionFunctions.add(mAct);
                mActionFunctions_All.add(mAct);

            } else if (mFunc.isRestrict()) {

                mFunctions_Restrict.add(mFunc);

                Index_Action mAct = new Index_Action(mRunTime,mEscopo,eAST);
                mActionFunctions.add(mAct);
                mActionFunctions_Restrict.add(mAct);

            }


        } else if (eAST.mesmoTipo("OPERATOR")) {

            Index_Function mFunc = new Index_Function(mRunTime,mEscopo,eAST);

            if (mFunc.isExtern()) {

                mOperations_Extern.add(mFunc);
                mOperations.add(mFunc);

            } else if (mFunc.isAll()) {

                mOperations.add(mFunc);
                mOperations_All.add(mFunc);

            } else if (mFunc.isRestrict()) {

                mOperations_Restrict.add(mFunc);

            }
        } else if (eAST.mesmoTipo("DIRECTOR")) {

            Index_Function mFunc = new Index_Function(mRunTime,mEscopo,eAST);

            if (mFunc.isExtern()) {

                mDirectors_Extern.add(mFunc);
                mDirectors.add(mFunc);

            } else if (mFunc.isAll()) {

                mDirectors.add(mFunc);
                mDirectors_All.add(mFunc);

            } else if (mFunc.isRestrict()) {

                mDirectors_Restrict.add(mFunc);

            }
        } else if (eAST.mesmoTipo("CAST")) {

            mCasts.add(eAST);
        } else if (eAST.mesmoTipo("INIT")) {

            Index_Action mAct = new Index_Action(mRunTime,mEscopo,eAST);
            mInits.add(mAct);


        } else if (eAST.mesmoTipo("TYPE")) {

            mTypes.add(eAST);

        } else if (eAST.mesmoTipo("STRUCT")) {

            if (eAST.getBranch("EXTENDED").mesmoNome("STAGES")) {
                mStages.add(eAST);

               // AlocarStages(eAST, mEscopo);
            } else {
                mStructs.add(eAST);
            }
        } else if (eAST.mesmoTipo("BASE")) {
            mBases.add(eAST);
        } else if (eAST.mesmoTipo("REFER")) {
            mEscopo.adicionarRefer(eAST.getNome());
        }

    }

    public void AlocarStages(AST eAST, Escopo mEscopo) {


        int i = 0;

        for (AST AST_STAGE : eAST.getBranch("STAGES").getASTS()) {

            if (AST_STAGE.mesmoTipo("STAGE")) {
                mEscopo.criarDefinicao(eAST.getNome() + "::" + AST_STAGE.getNome(), eAST.getNome(), String.valueOf(i));
                i += 1;
            }


        }

    }


    public ArrayList<AST> getStruct() {
        return mStructGuardados;
    }

    public ArrayList<Item> getStacks() {
        return mEscopo.getStacks();
    }

    public ArrayList<AST> getBases() {
        return mBases;
    }

    public ArrayList<Index_Action> getActionsFunctions_All() {
        return mActionFunctions_All;
    }
    public ArrayList<Index_Action> getActionsFunctions_Extern() {
        return mActionFunctions_Extern;
    }


    public ArrayList<Index_Action> getInits() {
        return mInits;
    }

    public ArrayList<Index_Function> getFunctions() {
        return mFunctions;
    }

    public ArrayList<Index_Function> getFunctions_All() {
        return mFunctions_All;
    }

    public ArrayList<Index_Function> getFunctions_Extern() {
        return mFunctions_Extern;
    }

    public ArrayList<Index_Function> getFunctions_Restrict() {
        return mFunctions_Restrict;
    }


    public ArrayList<Index_Action> getActions() {
        return mActions;
    }

    public ArrayList<Index_Action> getActionsFunctions() {
        return mActionFunctions;
    }


    public ArrayList<Index_Action> getActions_All() {
        return mActions_All;
    }

    public ArrayList<Index_Action> getActions_Extern() {
        return mActions_Extern;
    }

    public ArrayList<Index_Action> getActions_Restrict() {
        return mActions_Restrict;
    }

    public ArrayList<Index_Function> getDirectors() {
        return mDirectors;
    }


    public ArrayList<Index_Function> getOperations() {
        return mOperations;
    }

    public ArrayList<Index_Function> getOperations_All() {
        return mOperations_All;
    }

    public ArrayList<Index_Function> getOperations_Restrict() {
        return mOperations_Restrict;
    }

    public ArrayList<Index_Function> getOperations_Extern() {
        return mOperations_Extern;
    }

    public ArrayList<AST> getCasts() {
        return mCasts;
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

    public ArrayList<Index_Action> getActionFunctionsCompleto() {

        ArrayList<Index_Action> gc = new ArrayList<Index_Action>();

        for (Index_Action mIndex_Function : getActionsFunctions()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (Index_Action mIndex_Function : getEscopo().getEscopoAnterior().getActionFunctionsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
    }

    public ArrayList<Index_Function> getDirectorsCompleto() {

        ArrayList<Index_Function> gc = new ArrayList<Index_Function>();

        for (Index_Function mIndex_Function : getDirectors()) {
            gc.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (Index_Function mIndex_Function : getEscopo().getEscopoAnterior().getDirectorsCompleto()) {
                gc.add(mIndex_Function);
            }
        }

        return gc;
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

    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }


    public ArrayList<AST> getTypes() {

        ArrayList<AST> gca = new ArrayList<AST>();

        for (AST mIndex_Function : mTypes) {
            gca.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (AST mIndex_Function : getEscopo().getEscopoAnterior().getTypes()) {
                gca.add(mIndex_Function);
            }
        }

        return gca;
    }

    public ArrayList<AST> getStructs() {

        ArrayList<AST> gca = new ArrayList<AST>();

        for (AST mIndex_Function : mStructs) {
            gca.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (AST mIndex_Function : getEscopo().getEscopoAnterior().getStructs()) {
                gca.add(mIndex_Function);
            }
        }

        return gca;
    }

    public ArrayList<AST> getStages() {

        ArrayList<AST> gca = new ArrayList<AST>();

        for (AST mIndex_Function : mStages) {
            gca.add(mIndex_Function);
        }

        if (getEscopo().getEscopoAnterior() != null) {
            for (AST mIndex_Function : getEscopo().getEscopoAnterior().getStages()) {
                gca.add(mIndex_Function);
            }
        }

        return gca;
    }


}
