package Sigmaz.S00_Utilitarios;

public class Contador {

    private int mActions;
    private int mFunctions;

    private int mAutos;
    private int mFunctors;

    private int mDefaults;
    private int mOperators;
    private int mDirectors;

    private int mCasts;
    private int mStages;
    private int mTypes;
    private int mStructs;
    private int mExternals;

    private int mPackages;

    public Contador() {

        limpar();

    }

    public void limpar() {

        mActions = 0;
        mFunctions = 0;

        mAutos = 0;
        mFunctors = 0;

        mDefaults=0;
        mOperators=0;
        mDirectors=0;

        mCasts = 0;
        mStages = 0;
        mTypes = 0;
        mStructs = 0;
        mExternals = 0;
        mPackages = 0;

    }


    public void init(AST eAST) {

        limpar();

        for (AST mCorrente : eAST.getASTS()) {
            if (mCorrente.mesmoTipo("ACTION")) {
                mActions += 1;
            } else if (mCorrente.mesmoTipo("FUNCTION")) {
                mFunctions += 1;
            } else if (mCorrente.mesmoTipo("DEFAULT")) {
                mDefaults += 1;
            } else if (mCorrente.mesmoTipo("PROTOTYPE_AUTO")) {
                mAutos += 1;
            } else if (mCorrente.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                mFunctors += 1;
            } else if (mCorrente.mesmoTipo("OPERATOR")) {
                mOperators += 1;
            } else if (mCorrente.mesmoTipo("DIRECTOR")) {
                mDirectors += 1;
            }
        }

    }


    public int getActions() {
        return mActions;
    }

    public int getFunctions() {
        return mFunctions;
    }

    public int getAutos() {
        return mAutos;
    }

    public int getFunctors() {
        return mFunctors;
    }

    public int getCasts() {
        return mCasts;
    }

    public int getDefaults() {
        return mDefaults;
    }

    public int getOperators() {
        return mOperators;
    }

    public int getDirectors() {
        return mDirectors;
    }

    public int getStages() {
        return mStages;
    }

    public int getTypes() {
        return mTypes;
    }

    public int getStructs() {
        return mStructs;
    }

    public int getExternals() {
        return mExternals;
    }

    public int getPackages() {
        return mPackages;
    }
}
