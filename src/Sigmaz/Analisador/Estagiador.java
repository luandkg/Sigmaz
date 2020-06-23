package Sigmaz.Analisador;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Estagiador {

    private Analisador mAnalisador;

    private boolean mExterno;

    public Estagiador(Analisador eAnalisador) {

        mAnalisador = eAnalisador;
        mExterno = true;

    }


    public void init(ArrayList<AST> mTodos) {


        ArrayList<AST> mStages = new ArrayList<AST>();

        for (AST mSigmaz : mTodos) {

            if (mSigmaz.mesmoTipo("SIGMAZ")) {

                for (AST Struct_AST : mSigmaz.getASTS()) {

                    if (Struct_AST.mesmoTipo("STAGES")) {

                        if (Struct_AST.getBranch("DEFINED").mesmoNome("TRUE")) {
                            mStages.add(Struct_AST);
                        }

                    }

                }


                for (AST mStageDef : mStages) {

                    System.out.println("Definir " + mStageDef.getNome());



                    criarStructExtern(mSigmaz, mStageDef);

                }


            }
        }


    }


    public void criarStructExtern(AST mSigmaz, AST mStageDef) {

        AST mStruct = mSigmaz.criarBranch("STRUCT");
        mStruct.setNome(mStageDef.getNome());

        mStruct.criarBranch("INITS");
        AST mCorpo = mStruct.criarBranch("BODY");
        mStruct.criarBranch("WITH").setValor("FALSE");

        criarFunction_NameOf(mCorpo, mStageDef);
        criarFunction_ValueOf(mCorpo, mStageDef);

        criarOperador(mCorpo, mStageDef, "MATCH", "match");
        criarOperador(mCorpo, mStageDef, "UNMATCH", "unmatch");


    }

    public void criarFunction_NameOf(AST mCorpo, AST mStageDef) {

        AST mNome = mCorpo.criarBranch("FUNCTION");
        mNome.setNome("nameOf");
        mNome.setValor("string");
        mNome.criarBranch("VISIBILITY").setNome("EXTERN");
        AST mArguments = mNome.criarBranch("ARGUMENTS");
        AST mBody = mNome.criarBranch("BODY");


        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor(mStageDef.getNome());


        AST mDef = mBody.criarBranch("DEF");
        mDef.setNome("BETA");
        mDef.setValor("string");

        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("Desconhecido");
        mValue.setValor("Text");


        for (AST AST_STAGE : mStageDef.getBranch("OPTIONS").getASTS()) {

            if (AST_STAGE.mesmoTipo("STAGE")) {

                String eRaiz = mStageDef.getNome();
                String eNomeStage = AST_STAGE.getNome();


                AST mIf = mBody.criarBranch("IF");
                AST mCondition = mIf.criarBranch("CONDITION");
                mCondition.setValor("COMPARATOR");

                mCondition.criarBranch("MODE").setNome("MATCH");
                AST mLeft = mCondition.criarBranch("LEFT");
                mLeft.setNome("ALFA");
                mLeft.setValor("ID");

                AST mRight = mCondition.criarBranch("RIGHT");
                mRight.setNome(eRaiz);
                mRight.setValor("STAGE");
                mRight.criarBranch("STAGED").setNome(eNomeStage);

                AST mIfbody = mIf.criarBranch("BODY");
                AST mApply = mIfbody.criarBranch("APPLY");
                AST mSETTABLE = mApply.criarBranch("SETTABLE");
                mSETTABLE.setNome("BETA");
                mSETTABLE.setValor("ID");

                AST mVALUE = mApply.criarBranch("VALUE");
                mVALUE.setNome(eNomeStage);
                mVALUE.setValor("Text");
            }


        }


        AST mReturn = mBody.criarBranch("RETURN");
        mReturn.setNome("BETA");
        mReturn.setValor("ID");

    }

    public void criarFunction_ValueOf(AST mCorpo, AST mStageDef) {

        AST mNome = mCorpo.criarBranch("FUNCTION");
        mNome.setNome("valueOf");
        mNome.setValor("num");
        mNome.criarBranch("VISIBILITY").setNome("EXTERN");
        AST mArguments = mNome.criarBranch("ARGUMENTS");
        AST mBody = mNome.criarBranch("BODY");


        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor(mStageDef.getNome());


        AST mDef = mBody.criarBranch("DEF");
        mDef.setNome("BETA");
        mDef.setValor("num");

        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("");
        mValue.setValor("NULL");

        int resposta = 0;

        for (AST AST_STAGE : mStageDef.getBranch("OPTIONS").getASTS()) {

            if (AST_STAGE.mesmoTipo("STAGE")) {

                String eRaiz = mStageDef.getNome();
                String eNomeStage = AST_STAGE.getNome();


                AST mIf = mBody.criarBranch("IF");
                AST mCondition = mIf.criarBranch("CONDITION");
                mCondition.setValor("COMPARATOR");

                mCondition.criarBranch("MODE").setNome("MATCH");
                AST mLeft = mCondition.criarBranch("LEFT");
                mLeft.setNome("ALFA");
                mLeft.setValor("ID");

                AST mRight = mCondition.criarBranch("RIGHT");
                mRight.setNome(eRaiz);
                mRight.setValor("STAGE");
                mRight.criarBranch("STAGED").setNome(eNomeStage);

                AST mIfbody = mIf.criarBranch("BODY");
                AST mApply = mIfbody.criarBranch("APPLY");
                AST mSETTABLE = mApply.criarBranch("SETTABLE");
                mSETTABLE.setNome("BETA");
                mSETTABLE.setValor("ID");

                AST mVALUE = mApply.criarBranch("VALUE");
                mVALUE.setNome("" + resposta);
                mVALUE.setValor("Num");

                resposta+=1;
            }


        }


        AST mReturn = mBody.criarBranch("RETURN");
        mReturn.setNome("BETA");
        mReturn.setValor("ID");

    }

    public void criarOperador(AST mSigmaz, AST mStageDef, String mOperacao, String mInvocacao) {


        AST mMATCH = mSigmaz.criarBranch("OPERATION");
        mMATCH.setNome(mOperacao);
        mMATCH.setValor("bool");

        mMATCH.criarBranch("VISIBILITY").setNome("EXTERN");

        AST mArguments = mMATCH.criarBranch("ARGUMENTS");
        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor(mStageDef.getNome());

        AST mBeta = mArguments.criarBranch("ARGUMENT");
        mBeta.setNome("BETA");
        mBeta.setValor(mStageDef.getNome());

        AST mBody = mMATCH.criarBranch("BODY");

        AST mDef = mBody.criarBranch("DEF");
        mDef.setNome("GAMA");
        mDef.setValor("bool");

        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("");
        mValue.setValor("NULL");

        AST mInvoke = mBody.criarBranch("INVOKE");
        mInvoke.setNome("stages");
        mInvoke.setValor(mInvocacao);
        mInvoke.criarBranch("EXIT").setNome("GAMA");
        AST mInvokeArguments = mInvoke.criarBranch("ARGUMENTS");

        AST mAlfa_Invoke = mInvokeArguments.criarBranch("ARGUMENT");
        mAlfa_Invoke.setNome("ALFA");
        mAlfa_Invoke.setValor("ID");

        AST mBeta_Invoke = mInvokeArguments.criarBranch("ARGUMENT");
        mBeta_Invoke.setNome("BETA");
        mBeta_Invoke.setValor("ID");


        AST mReturn = mBody.criarBranch("RETURN");
        mReturn.setNome("GAMA");
        mReturn.setValor("ID");

    }

}
