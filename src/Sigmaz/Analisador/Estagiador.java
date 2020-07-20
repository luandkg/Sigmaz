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

                    if (Struct_AST.mesmoTipo("STRUCT")) {

                        if (Struct_AST.getBranch("EXTENDED").mesmoNome("STAGES")) {
                            if (Struct_AST.getBranch("DEFINED").mesmoNome("TRUE")) {
                                mStages.add(Struct_AST);
                            }
                        }


                    } else if (Struct_AST.mesmoTipo("PACKAGE")) {

                        ArrayList<AST> mPackageEstruturas = new ArrayList<AST>();

                        for (AST PackageStruct : Struct_AST.getASTS()) {
                            if (PackageStruct.mesmoTipo("STRUCT")) {

                                if (PackageStruct.getBranch("EXTENDED").mesmoNome("STAGES")) {
                                    mPackageEstruturas.add(PackageStruct);

                                }


                            }
                        }

                        estagiarAgora(Struct_AST, mPackageEstruturas);


                    }

                }


                estagiarAgora(mSigmaz, mStages);


            }
        }


    }

    public void estagiarAgora(AST mSigmaz, ArrayList<AST> mStages) {

        for (AST mStageDef : mStages) {
            criarStructExtern(mSigmaz, mStageDef);
        }

    }


    public void criarStructExtern(AST mSigmaz, AST mStageDef) {


        AST mCorpo = mStageDef.getBranch("BODY");

        criarFunction_NameOf(mCorpo, mStageDef);
        criarFunction_ValueOf(mCorpo, mStageDef);

     //   System.out.println("Criar operadores para " + mStageDef.getNome());

        criarOperador(mCorpo, mStageDef, "MATCH", "match");
        criarOperador(mCorpo, mStageDef, "UNMATCH", "unmatch");


    }

    private void criarTipagemConcreta(AST ASTPai, String eTipo) {

        AST ASTTipo = ASTPai.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

    }

    public void criarFunction_NameOf(AST mCorpo, AST mStageDef) {

        AST mNome = mCorpo.criarBranch("FUNCTION");
        mNome.setNome("nameOf");

        criarTipagemConcreta(mNome, "string");

        mNome.criarBranch("VISIBILITY").setNome("EXTERN");
        AST mArguments = mNome.criarBranch("ARGUMENTS");
        AST mBody = mNome.criarBranch("BODY");


        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor("VALUE");

        criarTipagemConcreta(mAlfa, mStageDef.getNome());


        AST mBeta = mBody.criarBranch("DEF");
        mBeta.setNome("BETA");


        criarTipagemConcreta(mBeta, "string");

        AST mValue = mBeta.criarBranch("VALUE");
        mValue.setNome("Desconhecido");
        mValue.setValor("Text");


        for (AST AST_STAGE : mStageDef.getBranch("STAGES").getASTS()) {

            if (AST_STAGE.mesmoTipo("STAGE")) {

                String eRaiz = mStageDef.getNome();
                String eNomeStage = AST_STAGE.getNome();


                AST mIf = mBody.criarBranch("IF");
                AST mCondition = mIf.criarBranch("CONDITION");
                mCondition.setValor("OPERATOR");

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

        criarTipagemConcreta(mNome, "num");


        mNome.criarBranch("VISIBILITY").setNome("EXTERN");
        AST mArguments = mNome.criarBranch("ARGUMENTS");
        AST mBody = mNome.criarBranch("BODY");


        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor("VALUE");

        criarTipagemConcreta(mAlfa, mStageDef.getNome());


        AST mDef = mBody.criarBranch("DEF");


        mDef.setNome("BETA");
        criarTipagemConcreta(mDef, "num");


        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("");
        mValue.setValor("NULL");

        int resposta = 0;

        for (AST AST_STAGE : mStageDef.getBranch("STAGES").getASTS()) {

            if (AST_STAGE.mesmoTipo("STAGE")) {

                String eRaiz = mStageDef.getNome();
                String eNomeStage = AST_STAGE.getNome();


                AST mIf = mBody.criarBranch("IF");
                AST mCondition = mIf.criarBranch("CONDITION");
                mCondition.setValor("OPERATOR");

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

                resposta += 1;
            }


        }


        AST mReturn = mBody.criarBranch("RETURN");
        mReturn.setNome("BETA");
        mReturn.setValor("ID");

    }

    public void criarOperador(AST mSigmaz, AST mStageDef, String mOperacao, String mInvocacao) {

        String p1 = mStageDef.getNome() + "_1";
        String p2 = mStageDef.getNome() + "_2";


        AST mMATCH = mSigmaz.criarBranch("OPERATOR");
        mMATCH.setNome(mOperacao);

        //System.out.println(" Operador " + mOperacao);

        criarTipagemConcreta(mMATCH, "bool");

        mMATCH.criarBranch("VISIBILITY").setNome("EXTERN");

        AST mArguments = mMATCH.criarBranch("ARGUMENTS");
        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome(p1);
        mAlfa.setValor("VALUE");

        criarTipagemConcreta(mAlfa, mStageDef.getNome());


        AST mBeta = mArguments.criarBranch("ARGUMENT");
        mBeta.setNome(p2);
        mBeta.setValor("VALUE");

        criarTipagemConcreta(mBeta, mStageDef.getNome());


        AST mBody = mMATCH.criarBranch("BODY");

        AST mDef = mBody.criarBranch("DEF");


        mDef.setNome("GAMA");
        criarTipagemConcreta(mDef, "bool");


        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("");
        mValue.setValor("NULL");

        AST mInvoke = mBody.criarBranch("INVOKE");
        mInvoke.setNome("stages");
        mInvoke.setValor(mInvocacao);
        mInvoke.criarBranch("EXIT").setNome("GAMA");
        AST mInvokeArguments = mInvoke.criarBranch("ARGUMENTS");

        AST mAlfa_Invoke = mInvokeArguments.criarBranch("ARGUMENT");
        mAlfa_Invoke.setNome(p1);
        mAlfa_Invoke.setValor("ID");

        AST mBeta_Invoke = mInvokeArguments.criarBranch("ARGUMENT");
        mBeta_Invoke.setNome(p2);
        mBeta_Invoke.setValor("ID");


        AST mReturn = mBody.criarBranch("RETURN");
        mReturn.setNome("GAMA");
        mReturn.setValor("ID");

    }

}
