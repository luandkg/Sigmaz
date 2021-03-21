package Sigmaz.S05_Executor;

import java.util.ArrayList;
import Sigmaz.S08_Utilitarios.AST;

public class Estrutural {

    private ArrayList<AST> mCall;

    private ArrayList<AST> mDefines;
    private ArrayList<AST> mMockizes;

    private ArrayList<AST> mActions;
    private ArrayList<AST> mFunctions;
    private ArrayList<AST> mDirectors;
    private ArrayList<AST> mOperators;

    private ArrayList<AST> mTypes;
    private ArrayList<AST> mCasts;
    private ArrayList<AST> mStructs;
    private ArrayList<AST> mPackages;


    public Estrutural() {

        mCall = new ArrayList<AST>();

        mDefines = new ArrayList<AST>();
        mMockizes = new ArrayList<AST>();

        mActions = new ArrayList<AST>();
        mFunctions = new ArrayList<AST>();
        mDirectors = new ArrayList<AST>();
        mOperators = new ArrayList<AST>();

        mTypes = new ArrayList<AST>();
        mCasts = new ArrayList<AST>();
        mStructs = new ArrayList<AST>();
        mPackages = new ArrayList<AST>();

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

        } else if (eAST.mesmoTipo("DIRECTOR")) {

            mDirectors.add(eAST);

        } else if (eAST.mesmoTipo("OPERATOR")) {

            mOperators.add(eAST);

        } else if (eAST.mesmoTipo("CAST")) {

            mCasts.add(eAST);

        } else if (eAST.mesmoTipo("TYPE")) {

            mTypes.add(eAST);

        } else if (eAST.mesmoTipo("STRUCT")) {


                mStructs.add(eAST);



        } else if (eAST.mesmoTipo("PACKAGE")) {

            mPackages.add(eAST);

        }

    }


    public String getTipagem(AST eAST) {

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public void mostrar() {


        System.out.println(" ######################### ESTRUTURAL - SIGMAZ 1.0 ############################ ");

        System.out.println(" - CALL : ");
        for (AST mAST : mCall) {

            AST mSending = mAST.getBranch("SENDING");

            if (mAST.mesmoValor("REFER")) {
                System.out.println("\t - " + mAST.getNome() + " : REFER " + " -> " + mSending.getNome());
            } else {
                System.out.println("\t - " + mAST.getNome() + " : AUTO ");
            }
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

        System.out.println(" - DIRECTORS : ");
        for (AST mAST : mDirectors) {
            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
        }

        System.out.println(" - OPERATORS : ");
        for (AST mAST : mOperators) {
            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
        }

        for (AST mStruct : mStructs) {
            for (AST mStructBody : mStruct.getBranch("BODY").getASTS()) {
                if (mStructBody.mesmoTipo("OPERATION") && mStructBody.getBranch("VISIBILITY").mesmoNome("EXTERN")) {

                    System.out.println("\t - " + mStructBody.getNome() + " ( " + getParametros(mStructBody) + " ) -> " + mStructBody.getValor());


                }
            }
        }

        System.out.println(" - TYPES : ");
        for (AST mAST : mTypes) {
            System.out.println("\t - " + mAST.getNome());
            for (AST mGetter : mAST.getASTS()) {
                System.out.println("\t\t - " + mGetter.getTipo() + " " + mGetter.getNome() + " : " + getTipagem(mGetter.getBranch("TYPE")));
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



        listarStructGeral("", mStructs);

        System.out.println(" - PACKAGES : ");
        for (AST mAST : mPackages) {

            System.out.println("\t - " + mAST.getNome());

            listarPackage(mAST);

        }


        System.out.println(" ################################### ##### #################################### ");

    }


    public void listarStructGeral(String ePrefixo, ArrayList<AST> mGeralStructs) {


        ArrayList<AST> mStruct_Stages = new ArrayList<AST>();
        ArrayList<AST> mStruct_Structs = new ArrayList<AST>();
        ArrayList<AST> mStruct_External = new ArrayList<AST>();

        for (AST mAST : mGeralStructs) {
            if (mAST.mesmoTipo("STRUCT")) {


                AST mWith = mAST.getBranch("WITH");
                AST mExtended = mAST.getBranch("EXTENDED");


                if (mExtended.mesmoNome("STAGES")) {

                    mStruct_Stages.add(mAST);

                } else if (mExtended.mesmoNome("EXTERNAL")) {

                    mStruct_External.add(mAST);

                } else if (mExtended.mesmoNome("STRUCT")) {

                    mStruct_Structs.add(mAST);


                }
            }
        }

        System.out.println(ePrefixo + " - STAGES : ");
        for (AST mAST : mStruct_Stages) {

            System.out.println(ePrefixo + "\t - " + mAST.getNome());


            listarStages(ePrefixo, mAST);
        }


        System.out.println(ePrefixo + " - STRUCTS : ");
        for (AST mAST : mStruct_Structs) {

            AST mWith = mAST.getBranch("WITH");
            AST mExtended = mAST.getBranch("EXTENDED");

            if (mWith.mesmoValor("TRUE")) {
                System.out.println(ePrefixo + "\t - " + mAST.getNome() + " -> " + mWith.getNome());
            } else {
                System.out.println(ePrefixo + "\t - " + mAST.getNome());
            }

            System.out.println(ePrefixo + "\t\t - COMPLEXITY = " + mExtended.getValor());


            AST mBases = mAST.getBranch("BASES");
            System.out.println(ePrefixo + "\t\t - BASES : ");
            for (AST bAST : mBases.getASTS()) {

                System.out.println(ePrefixo + "\t\t\t - " + bAST.getNome());

            }


            AST mInits = mAST.getBranch("INITS");

            listarInits(ePrefixo, mAST.getNome(), mInits);

            AST mCorpo = mAST.getBranch("BODY");

            listarStruct(ePrefixo, mCorpo);

        }

        System.out.println(ePrefixo + " - EXTERNAL : ");
        for (AST mAST : mStruct_External) {

            System.out.println(ePrefixo + "\t - " + mAST.getNome());

            AST mCorpo = mAST.getBranch("BODY");
            listarStruct(ePrefixo, mCorpo);
        }


    }


    public void listarInits(String ePrefixo, String eStructnome, AST ASTPai) {

        System.out.println(ePrefixo + "\t\t - INITS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("INIT")) {
                if (eStructnome.contentEquals(mAST.getNome())) {


                    AST AST_Call = mAST.getBranch("CALL");

                    if (AST_Call.mesmoValor("TRUE")) {


                        String eArgumentos = AST_Call.getNome();

                        for (AST aAST : AST_Call.getBranch("ARGUMENTS").getASTS()) {

                        }

                        System.out.println(ePrefixo + "\t\t\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + eArgumentos);

                    } else {

                        System.out.println(ePrefixo + "\t\t\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");


                    }


                }
            }
        }

    }

    public void listarStruct(String ePrefixo, AST ASTPai) {

        System.out.println(ePrefixo + "\t\t - DEFINES : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("DEFINE")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " : " + getTipagem( mAST.getBranch("TYPE")));
            }
        }

        System.out.println(ePrefixo + "\t\t - MOCKIZES : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("MOCKIZ")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " : " + getTipagem( mAST.getBranch("TYPE")));
            }
        }

        System.out.println(ePrefixo + "\t\t - ACTIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }

        }
        System.out.println(ePrefixo + "\t\t - FUNCTIONS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }

        System.out.println(ePrefixo + "\t\t - DIRECTORS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("DIRECTOR")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }

        System.out.println(ePrefixo + "\t\t - OPERATORS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("OPERATOR")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }


    public void listarStages(String ePrefixo,AST ASTPai) {

        AST mStages = ASTPai.getBranch("STAGES");
        AST mCorpo = ASTPai.getBranch("BODY");

        System.out.println(ePrefixo + "\t\t - STAGES : ");
        for (AST mAST : mStages.getASTS()) {
                System.out.println(ePrefixo + "\t\t\t - " + mAST.getNome() );

        }


        System.out.println(ePrefixo + "\t\t - FUNCTIONS : ");
        for (AST mAST : mCorpo.getASTS()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }

        System.out.println(ePrefixo +"\t\t - OPERATORS : ");
        for (AST mAST : mCorpo.getASTS()) {
            if (mAST.mesmoTipo("OPERATOR")) {
                System.out.println(ePrefixo + "\t\t\t - " + getModo(mAST) + " " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
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

    public void listarPackage(AST ASTPai) {

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


        System.out.println("\t\t - TYPES : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("TYPE")) {
                System.out.println("\t\t\t - " + mAST.getNome());
                for (AST mGetter : mAST.getASTS()) {
                    System.out.println("\t\t\t\t - " + mGetter.getTipo() + " " + mGetter.getNome() + " : " + getTipagem(mGetter.getBranch("TYPE")));
                }
            }

        }

        System.out.println("\t\t - CASTS : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("CAST")) {
                System.out.println("\t\t\t - " + mAST.getNome());
                for (AST mGetter : mAST.getASTS()) {
                    if (mGetter.mesmoTipo("GETTER")) {
                        System.out.println("\t\t\t\t - Getter : " + mGetter.getValor());
                    }
                }
                for (AST mGetter : mAST.getASTS()) {
                    if (mGetter.mesmoTipo("SETTER")) {
                        System.out.println("\t\t\t\t - Setter : " + mGetter.getValor());
                    }
                }
            }

        }

        System.out.println("\t\t - STAGES : ");
        for (AST mAST : ASTPai.getASTS()) {
            if (mAST.mesmoTipo("STAGES")) {
                System.out.println("\t\t\t - " + mAST.getNome());

                for (AST AST_STAGE : mAST.getBranch("OPTIONS").getASTS()) {
                    System.out.println("\t\t\t\t - " + AST_STAGE.getNome());
                }
            }


        }


        listarStructGeral("\t\t", ASTPai.getASTS());

    }


}
