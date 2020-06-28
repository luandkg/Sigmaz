package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Estrutural {

    private ArrayList<AST> mCall;

    private ArrayList<AST> mDefines;
    private ArrayList<AST> mMockizes;

    private ArrayList<AST> mActions;
    private ArrayList<AST> mFunctions;
    private ArrayList<AST> mOperations;
    private ArrayList<AST> mCasts;
    private ArrayList<AST> mStructs;
    private ArrayList<AST> mStages;


    public Estrutural() {

        mCall = new ArrayList<AST>();

        mDefines = new ArrayList<AST>();
        mMockizes = new ArrayList<AST>();

        mActions = new ArrayList<AST>();
        mFunctions = new ArrayList<AST>();
        mOperations = new ArrayList<AST>();

        mCasts = new ArrayList<AST>();
        mStructs = new ArrayList<AST>();
        mStages = new ArrayList<AST>();

    }

    public void guardar(AST eAST) {


        if (eAST.mesmoTipo("CALL")) {

            mCall.add(eAST);

        } else if (eAST.mesmoTipo("DEFINE")) {

            mDefines.add(eAST);

        } else if (eAST.mesmoTipo("MOCKIZ")) {

            mMockizes.add(eAST);

        } else if (eAST.mesmoTipo("ACTION")) {

            mActions.add(eAST);

        } else if (eAST.mesmoTipo("FUNCTION")) {

            mFunctions.add(eAST);


        } else if (eAST.mesmoTipo("OPERATION")) {

            mOperations.add(eAST);

        } else if (eAST.mesmoTipo("CAST")) {

            mCasts.add(eAST);

        } else if (eAST.mesmoTipo("STRUCT")) {

            mStructs.add(eAST);
        } else if (eAST.mesmoTipo("STAGES")) {

            mStages.add(eAST);


        }

    }


    public ArrayList<AST> getFunctions() {
        return mFunctions;
    }

    public ArrayList<AST> getActions() {
        return mActions;
    }

    public ArrayList<AST> getOperations() {
        return mOperations;
    }

    public ArrayList<AST> getCasts() {
        return mCasts;
    }

    public String getTipagem(AST eAST){

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")){

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" +getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public void mostrar() {


        System.out.println(" ######################### ESTRUTURAL - SIGMAZ 1.0 ############################ ");

        System.out.println(" - CALL : ");
        for (AST mAST : mCall) {

            AST mSending = mAST.getBranch("SENDING");

            System.out.println("\t - " + mAST.getNome() + " -> " + mSending.getNome());
        }

        System.out.println(" - DEFINES : ");
        for (AST mAST : mDefines) {
            System.out.println("\t - " + mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")));
        }

        System.out.println(" - MOCKIZES : ");
        for (AST mAST : mMockizes) {
            System.out.println("\t - " + mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")));
        }

        System.out.println(" - ACTIONS : ");
        for (AST mAST : mActions) {

            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");

        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mAST : mFunctions) {

            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));

        }

        System.out.println(" - OPERATIONS : ");
        for (AST mAST : mOperations) {
            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
        }

        for (AST mStruct : mStructs) {
            for (AST mStructBody : mStruct.getBranch("BODY").getASTS()) {
                if (mStructBody.mesmoTipo("OPERATION") && mStructBody.getBranch("VISIBILITY").mesmoNome("EXTERN")) {

                    System.out.println("\t - " + mStructBody.getNome() + " ( " + getParametros(mStructBody) + " ) -> " + mStructBody.getValor());


                }
            }
        }

        System.out.println(" - CASTS : ");
        for (AST mAST : mCasts) {
            System.out.println("\t - " + mAST.getNome());
            for (AST mGetter : mAST.getASTS()) {
                if (mGetter.mesmoTipo("GETTER")) {
                    System.out.println("\t\t - Getter : " + mGetter.getValor());
                }
            }
            for (AST mGetter : mAST.getASTS()) {
                if (mGetter.mesmoTipo("SETTER")) {
                    System.out.println("\t\t - Setter : " + mGetter.getValor());
                }
            }
        }

        System.out.println(" - STAGES : ");
        for (AST mAST : mStages) {
            System.out.println("\t - " + mAST.getNome());

            for (AST AST_STAGE : mAST.getBranch("OPTIONS").getASTS()) {
                System.out.println("\t\t - " + AST_STAGE.getNome());
            }

        }

        ArrayList<AST> mStruct_Stages = new ArrayList<AST>();
        ArrayList<AST> mStruct_External = new ArrayList<AST>();

        System.out.println(" - STRUCTS : ");
        for (AST mAST : mStructs) {

            AST mWith = mAST.getBranch("WITH");
            AST mExtended = mAST.getBranch("EXTENDED");



            if (mExtended.mesmoNome("STAGES")) {

                mStruct_Stages.add(mAST);

            } else if (mExtended.mesmoNome("EXTERNAL")) {

                mStruct_External.add(mAST);

            } else if (mExtended.mesmoNome("STRUCT")) {

                if (mWith.mesmoValor("TRUE")) {
                    System.out.println("\t - " + mAST.getNome() + " -> " + mWith.getNome());
                } else {
                    System.out.println("\t - " + mAST.getNome());
                }

                System.out.println("\t\t - COMPLEXITY = " + mExtended.getValor());


                AST mBases = mAST.getBranch("BASES");
                System.out.println("\t\t - BASES : ");
                for (AST bAST : mBases.getASTS()) {

                    System.out.println("\t\t\t - " + bAST.getNome());

                }


                AST mInits = mAST.getBranch("INITS");

                listarInits(mAST.getNome(), mInits);

                AST mCorpo = mAST.getBranch("BODY");

                listarStruct(mCorpo);


            }

        }

        System.out.println(" - STRUCTS STAGES : ");
        for (AST mAST : mStruct_Stages) {

            System.out.println("\t - " + mAST.getNome());

            AST mCorpo = mAST.getBranch("BODY");
            listarExtern(mCorpo);
        }

        System.out.println(" - STRUCTS EXTERNAL : ");
        for (AST mAST : mStruct_External) {

            System.out.println("\t - " + mAST.getNome());

            AST mCorpo = mAST.getBranch("BODY");
            listarStruct(mCorpo);
        }


        System.out.println(" ################################### ##### #################################### ");

    }

    public void listarInits(String eStructnome, AST ASTPai) {

        System.out.println("\t\t - INITS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("INIT")) {
                if (eStructnome.contentEquals(mAST.getNome())) {


                    AST AST_Call = mAST.getBranch("CALL");

                    if (AST_Call.mesmoValor("TRUE")){


                        String eArgumentos =AST_Call.getNome();

                        for (AST aAST : AST_Call.getBranch("ARGUMENTS").getASTS()) {

                        }

                        System.out.println("\t\t\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + eArgumentos);

                    }else{

                        System.out.println("\t\t\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");



                    }



                }
            }
        }

    }

    public void listarStruct(AST ASTPai) {

        System.out.println("\t\t - DEFINES : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("DEFINE")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " : " + mAST.getValor());
            }
        }

        System.out.println("\t\t - MOCKIZES : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("MOCKIZ")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " : " + mAST.getValor());
            }
        }

        System.out.println("\t\t - ACTIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }

        }
        System.out.println("\t\t - FUNCTIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }

        System.out.println("\t\t - OPERATIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("OPERATION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }

    public void listarExtern(AST ASTPai) {

        System.out.println("\t\t - FUNCTIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }

        System.out.println("\t\t - OPERATIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("OPERATION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }
    }

    public void listarStages(AST ASTPai) {

        System.out.println("\t\t - FUNCTIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }

        System.out.println("\t\t - OPERATIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("OPERATION")) {
                System.out.println("\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }
    }

    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }

    public String getParametros(AST ASTPai) {
        String ret = "";

        int total = 0;

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                total += 1;
            }
        }

        int contando = 0;

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                contando += 1;

                if (contando < total) {
                    ret += mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " , ";
                } else {
                    ret += mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " ";
                }
            }
        }

        return ret;
    }

}
