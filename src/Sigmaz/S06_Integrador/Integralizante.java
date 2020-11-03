package Sigmaz.S06_Integrador;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Visualizador.*;

import java.util.ArrayList;

public class Integralizante {

    private ArrayList<String> mRefers;

    private ArrayList<SigmazAction> mActions;
    private ArrayList<SigmazAuto> mAutos;
    private ArrayList<SigmazFunction> mFunctions;
    private ArrayList<SigmazFunctor> mFunctores;

    private ArrayList<SigmazDirector> mDirectors;
    private ArrayList<SigmazOperator> mOperators;
    private ArrayList<SigmazMark> mMarks;

    private ArrayList<SigmazCast> mCasts;
    private ArrayList<SigmazStages> mStages;

    private ArrayList<SigmazType> mTypes;
    private ArrayList<SigmazStruct> mStructs;
    private ArrayList<SigmazModel> mModels;
    private ArrayList<SigmazExternal> mExternals;

    public Integralizante() {

        mRefers = new ArrayList<String>();

        mActions = new ArrayList<SigmazAction>();
        mAutos = new ArrayList<SigmazAuto>();
        mFunctions = new ArrayList<SigmazFunction>();
        mFunctores = new ArrayList<SigmazFunctor>();

        mDirectors = new ArrayList<SigmazDirector>();
        mOperators = new ArrayList<SigmazOperator>();
        mMarks = new ArrayList<SigmazMark>();

        mCasts = new ArrayList<SigmazCast>();
        mTypes = new ArrayList<SigmazType>();
        mStages = new ArrayList<SigmazStages>();

        mStructs = new ArrayList<SigmazStruct>();
        mModels = new ArrayList<SigmazModel>();
        mExternals = new ArrayList<SigmazExternal>();

    }


    public void adicionar_Refer(SigmazPackage eSigmazPackage) {

        mRefers.add(eSigmazPackage.getNome());

        mActions.addAll(eSigmazPackage.getActions());
        mAutos.addAll(eSigmazPackage.getAutos());
        mFunctions.addAll(eSigmazPackage.getFunctions());
        mFunctores.addAll(eSigmazPackage.getFunctors());

        mDirectors.addAll(eSigmazPackage.getDirectors());
        mOperators.addAll(eSigmazPackage.getOperators());
        mMarks.addAll(eSigmazPackage.getMarks());

        mCasts.addAll(eSigmazPackage.getCasts());
        mStages.addAll(eSigmazPackage.getStages());

        mTypes.addAll(eSigmazPackage.getTypes());
        mStructs.addAll(eSigmazPackage.getStructs());
        mModels.addAll(eSigmazPackage.getModelos());
        mExternals.addAll(eSigmazPackage.getExternals());

    }


    public void adicionarSigmaz(Integralizante eIntegrador) {

        mActions.addAll(eIntegrador.getActions());
        mAutos.addAll(eIntegrador.getAutos());
        mFunctions.addAll(eIntegrador.getFunctions());
        mFunctores.addAll(eIntegrador.getFunctors());

        mDirectors.addAll(eIntegrador.getDirectors());
        mOperators.addAll(eIntegrador.getOperators());
        mMarks.addAll(eIntegrador.getMarks());

        mCasts.addAll(eIntegrador.getCasts());
        mStages.addAll(eIntegrador.getStages());

        mTypes.addAll(eIntegrador.getTypes());
        mStructs.addAll(eIntegrador.getStructs());
        mModels.addAll(eIntegrador.getModels());
        mExternals.addAll(eIntegrador.getExternals());

    }

    public void adicionar_Action(AST eAction) {
        mActions.add(new SigmazAction(eAction));
    }

    public void adicionar_Function(AST eFunction) {
        mFunctions.add(new SigmazFunction(eFunction));
    }

    public void adicionar_Cast(AST eCast) {
        mCasts.add(new SigmazCast(eCast));
    }

    public void adicionar_Type(AST eType) {
        mTypes.add(new SigmazType(eType));
    }

    public void adicionar_Struct(AST eStruct) {
        mStructs.add(new SigmazStruct(eStruct));
    }


    public void adicionar_Stages(AST eStages) {
        mStages.add(new SigmazStages(eStages));
    }

    public void adicionar_External(AST eExternal) {
        mExternals.add(new SigmazExternal(eExternal));
    }

    public boolean existeActionFunction(String eNome) {

        boolean ret = false;

        for (SigmazAction a : mActions) {
            if (a.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }

        if (!ret) {
            for (SigmazFunction a : mFunctions) {
                if (a.mesmoNome(eNome)) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;

    }

    public boolean existeFunction(String eNome) {

        boolean ret = false;


        for (SigmazFunction a : mFunctions) {
            if (a.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }


        return ret;

    }

    public boolean existeStruct(String eNome) {

        boolean ret = false;


        for (SigmazStruct a : mStructs) {
            if (a.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }


        return ret;

    }

    public boolean existeType(String eNome) {

        boolean ret = false;


        for (SigmazType a : mTypes) {
            if (a.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }


        return ret;

    }

    public boolean existeStage(String eNome) {

        boolean ret = false;


        for (SigmazStages a : mStages) {
            if (a.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }


        return ret;

    }


    public SigmazStages getStage(String eNome) {

        SigmazStages ret = null;


        for (SigmazStages a : mStages) {
            if (a.mesmoNome(eNome)) {
                ret = a;
                break;
            }
        }


        return ret;

    }


    public boolean existeExtern(String eNome) {

        boolean ret = false;


        for (SigmazExternal a : mExternals) {
            if (a.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }


        return ret;

    }


    public SigmazExternal getExternal(String eNome) {

        SigmazExternal ret = null;


        for (SigmazExternal a : mExternals) {
            if (a.mesmoNome(eNome)) {
                ret = a;
                break;
            }
        }


        return ret;

    }


    public ArrayList<SigmazAction> getActions() {
        return mActions;
    }

    public ArrayList<SigmazFunction> getFunctions() {
        return mFunctions;
    }

    public ArrayList<SigmazDirector> getDirectors() {
        return mDirectors;
    }

    public ArrayList<SigmazOperator> getOperators() {
        return mOperators;
    }

    public ArrayList<SigmazMark> getMarks() {
        return mMarks;
    }

    public ArrayList<SigmazAuto> getAutos() {
        return mAutos;
    }

    public ArrayList<SigmazFunctor> getFunctors() {
        return mFunctores;
    }

    public ArrayList<SigmazCast> getCasts() {
        return mCasts;
    }

    public ArrayList<SigmazType> getTypes() {
        return mTypes;
    }

    public ArrayList<SigmazStages> getStages() {
        return mStages;
    }

    public ArrayList<SigmazStruct> getStructs() {
        return mStructs;
    }

    public ArrayList<SigmazModel> getModels() {
        return mModels;
    }

    public ArrayList<SigmazExternal> getExternals() {
        return mExternals;
    }

}
