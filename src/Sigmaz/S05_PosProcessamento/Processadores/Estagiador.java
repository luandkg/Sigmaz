package Sigmaz.S05_PosProcessamento.Processadores;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST_Implementador;
import Sigmaz.S05_PosProcessamento.PosProcessador;
import Sigmaz.S00_Utilitarios.AST;

public class Estagiador {

    private PosProcessador mAnalisador;

    private boolean mExterno;

    public Estagiador(PosProcessador eAnalisador) {

        mAnalisador = eAnalisador;
        mExterno = true;

    }


    public void init(ArrayList<AST> mTodos) {


        mAnalisador.mensagem("");
        mAnalisador.mensagem(" ------------------ FASE ESTAGIADOR ----------------------- ");
        mAnalisador.mensagem("");


        for (AST mSigmaz : mTodos) {

            if (mSigmaz.mesmoTipo("SIGMAZ")) {

                for (AST mStruct : mSigmaz.getASTS()) {

                    if (mStruct.mesmoTipo("STRUCT")) {

                        if (mStruct.getBranch("EXTENDED").mesmoNome("STAGES")) {


                            AST mStageDef = mStruct;
                            mAnalisador.mensagem(" Estagiar : " + mStageDef.getNome());

                            organizar(mStageDef);

                            if (mStruct.getBranch("DEFINED").mesmoNome("TRUE")) {
                                externalizar(mStageDef);
                            }
                        }


                    } else if (mStruct.mesmoTipo("PACKAGE")) {

                        AST ePackage = mStruct;


                        for (AST PackageStruct : ePackage.getASTS()) {
                            if (PackageStruct.mesmoTipo("STRUCT")) {

                                if (PackageStruct.getBranch("EXTENDED").mesmoNome("STAGES")) {

                                    AST mStageDef = PackageStruct;
                                    mAnalisador.mensagem(" Estagiar : " + PackageStruct.getNome() + "<>" + mStageDef.getNome());

                                    organizar(mStageDef);

                                    if (mStageDef.getBranch("DEFINED").mesmoNome("TRUE")) {
                                        externalizar(mStageDef);
                                    }


                                }


                            }
                        }


                    }

                }


            }
        }


    }


    public void organizar(AST mStageDef) {

        AST mStages = mStageDef.getBranch("STAGES");

        int eID = 5;
        for (AST eAST : mStages.getASTS()) {
            eAST.setValor(String.valueOf(eID));
            eID += 1;
        }


    }

    public void externalizar(AST mStageDef) {


        AST mCorpo = mStageDef.getBranch("BODY");

        criarFunction_NameOf(mCorpo, mStageDef);
        criarFunction_ValueOf(mCorpo, mStageDef);

        criarFunction_Get(mCorpo, mStageDef);

        criarOperador(mCorpo, mStageDef, "MATCH", "EQUAL");
        criarOperador(mCorpo, mStageDef, "UNMATCH", "NOT");


    }


    private void criarTipagemConcreta(AST ASTPai, String eTipo) {

        AST ASTTipo = ASTPai.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

    }

    public void criarFunction_NameOf(AST mCorpo, AST mStageDef) {

        AST mNome = mCorpo.criarBranch("FUNCTION");
        mNome.setNome("nameOf");

        mAnalisador.mensagem(" Function : " + mStageDef.getNome() + " -> nameOf() ");

        criarTipagemConcreta(mNome, "string");

        mNome.criarBranch("VISIBILITY").setNome("EXPLICIT");
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
        AST mReturnValue = mReturn.criarBranch("VALUE");

        mReturnValue.setNome("BETA");
        mReturnValue.setValor("ID");

    }

    public void criarFunction_ValueOf(AST mCorpo, AST mStageDef) {

        AST mNome = mCorpo.criarBranch("FUNCTION");
        mNome.setNome("valueOf");

        criarTipagemConcreta(mNome, "int");

        mAnalisador.mensagem(" Function : " + mStageDef.getNome() + " -> valueOf() ");

        mNome.criarBranch("VISIBILITY").setNome("EXPLICIT");
        AST mArguments = mNome.criarBranch("ARGUMENTS");
        AST mBody = mNome.criarBranch("BODY");


        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor("VALUE");

        criarTipagemConcreta(mAlfa, mStageDef.getNome());


        AST mDef = mBody.criarBranch("DEF");


        mDef.setNome("BETA");
        criarTipagemConcreta(mDef, "int");


        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("");
        mValue.setValor("NULL");


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
                mVALUE.setNome(AST_STAGE.getValor());
                mVALUE.setValor("Num");

            }


        }


        AST mReturn = mBody.criarBranch("RETURN");
        AST mReturnValue = mReturn.criarBranch("VALUE");

        mReturnValue.setNome("BETA");
        mReturnValue.setValor("ID");

    }

    public void criarFunction_Get(AST mCorpo, AST mStageDef) {

        AST mNome = mCorpo.criarBranch("FUNCTION");
        mNome.setNome("get");

        criarTipagemConcreta(mNome, mStageDef.getNome());

        mAnalisador.mensagem(" Function : " + mStageDef.getNome() + " -> get() ");

        mNome.criarBranch("VISIBILITY").setNome("EXPLICIT");
        AST mArguments = mNome.criarBranch("ARGUMENTS");
        AST mBody = mNome.criarBranch("BODY");


        AST mAlfa = mArguments.criarBranch("ARGUMENT");
        mAlfa.setNome("ALFA");
        mAlfa.setValor("VALUE");

        criarTipagemConcreta(mAlfa, "int");


        AST mDef = mBody.criarBranch("DEF");
        mDef.setNome("BETA");
        criarTipagemConcreta(mDef, mStageDef.getNome());


        AST mValue = mDef.criarBranch("VALUE");
        mValue.setNome("");
        mValue.setValor("NULL");


        AST mEncontrado = mBody.criarBranch("DEF");
        mEncontrado.setNome("GAMA");
        criarTipagemConcreta(mEncontrado, "bool");
        AST mmEncontradoValue = mEncontrado.criarBranch("VALUE");
        mmEncontradoValue.setNome("false");
        mmEncontradoValue.setValor("ID");


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
                mRight.setNome(AST_STAGE.getValor());
                mRight.setValor("Num");


                AST mIfbody = mIf.criarBranch("BODY");
                AST mApply = mIfbody.criarBranch("APPLY");
                AST mSETTABLE = mApply.criarBranch("SETTABLE");
                mSETTABLE.setNome("BETA");
                mSETTABLE.setValor("ID");


                AST mVALUE = mApply.criarBranch("VALUE");
                mVALUE.setNome(eRaiz);
                mVALUE.setValor("STAGE");
                mVALUE.criarBranch("STAGED").setNome(eNomeStage);


                AST mAplicavel = mIfbody.criarBranch("APPLY");

                AST mAplicavel_SET = mAplicavel.criarBranch("SETTABLE");
                mAplicavel_SET.setNome("GAMA");
                mAplicavel_SET.setValor("ID");

                AST mAplicavel_VAL = mAplicavel.criarBranch("VALUE");
                mAplicavel_VAL.setNome("true");
                mAplicavel_VAL.setValor("ID");


            }


        }

        AST mIf = mBody.criarBranch("IF");
        AST mIfBody = mIf.criarBranch("BODY");

        AST mCONDITION = mIf.criarBranch("CONDITION");
        mCONDITION.setNome("GAMA");
        mCONDITION.setValor("ID");

        AST mIfOthers = mIf.criarBranch("OTHERS");


        criarInt_To_String(mIfOthers, "ALFA", "DELTA");


        AST_Implementador mAI = new AST_Implementador();


        mAI.criar_Def_ValueText(mIfOthers, "EPSILON", "string", " O stage com valor ");
        mAI.criar_Def_ValueText(mIfOthers, "IOTA", "string", " nao existe em " + mStageDef.getNome() + " ! ");

        criarAppend(mIfOthers,"EPSILON","DELTA","ZETA");

        criarAppend(mIfOthers,"ZETA","IOTA","OMEGA");

        AST mEXCEPTION = mIfOthers.criarBranch("EXCEPTION");

        AST mEXCEPTIONVALUE = mEXCEPTION.criarBranch("VALUE");
        mEXCEPTIONVALUE.setNome("OMEGA");
        mEXCEPTIONVALUE.setValor("ID");


        AST mReturn = mBody.criarBranch("RETURN");
        AST mReturnValue = mReturn.criarBranch("VALUE");

        mReturnValue.setNome("BETA");
        mReturnValue.setValor("ID");

    }


    public void criarInt_To_String(AST ASTPai, String eReceber, String Saida) {

        AST_Implementador mAI = new AST_Implementador();

        AST mReg = mAI.crar_Reg_ValueID(ASTPai, "R5", eReceber);

        AST mProc = mAI.criar_Proc(ASTPai);


        mAI.criar_Proc_InstrucaoID(mProc, "SET", "R13");
        mAI.criar_Proc_InstrucaoText(mProc, "MOV", "");
        mAI.criar_Proc_InstrucaoID(mProc, "INT_STRING", "R5");

        mAI.criarDef_ComRegistrador(ASTPai, Saida, "string", "R13");


    }

    public void criarAppend(AST ASTPai, String a1, String a2,String a3) {

        AST_Implementador mAI = new AST_Implementador();

        AST mReg12 = mAI.crar_Reg_ValueID(ASTPai, "R13", a1);
        AST mReg14 = mAI.crar_Reg_ValueID(ASTPai, "R14", a2);

        AST mProc = mAI.criar_Proc(ASTPai);
        mAI.criar_Proc_InstrucaoID(mProc, "SET", "R13");
        mAI.criar_Proc_InstrucaoID(mProc, "APPEND", "R14");




        mAI.criarDef_ComRegistrador(ASTPai, a3, "string", "R13");

    }

    public void criarOperador(AST mSigmaz, AST mStageDef, String mOperacao, String mInvocacao) {

        String p1 = mStageDef.getNome() + "_1";
        String p2 = mStageDef.getNome() + "_2";

        mAnalisador.mensagem(" OPERATOR : " + mStageDef.getNome() + " -> " + mOperacao);

        AST mMATCH = mSigmaz.criarBranch("OPERATOR");
        mMATCH.setNome(mOperacao);

        //System.out.println(" Operador " + mOperacao);

        criarTipagemConcreta(mMATCH, "bool");

        mMATCH.criarBranch("VISIBILITY").setNome("EXPLICIT");

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

        String eA1_Verificar = p1 + "_@_STAGE_VERIFY";
        String eA2_Verificar = p2 + "_@_STAGE_VERIFY";

        verificar_lado(mBody, p1, eA1_Verificar);

        verificar_lado(mBody, p2, eA2_Verificar);

        String eA1_Obter = p1 + "_@_STAGE";
        String eA2_Obter = p2 + "_@_STAGE";

        obter_lado(mBody, p1, eA1_Obter);

        obter_lado(mBody, p2, eA2_Obter);


        operador(mBody, mInvocacao, eA1_Obter, eA2_Obter, "GAMA");

        AST mReturn = mBody.criarBranch("RETURN");
        AST mReturnValue = mReturn.criarBranch("VALUE");

        mReturnValue.setNome("GAMA");
        mReturnValue.setValor("ID");

    }

    public void verificar_lado(AST ASTLocal, String eVar, String eResultado) {

        AST mEsquerda_R0 = ASTLocal.criarBranch("REG");
        mEsquerda_R0.setNome("R0");
        AST mEsquerda_R0_Value = mEsquerda_R0.criarBranch("VALUE");
        mEsquerda_R0_Value.setNome(eVar);
        mEsquerda_R0_Value.setValor("ID");

        AST mDef = ASTLocal.criarBranch("DEF");
        mDef.setNome(eResultado);

        AST mDefType = mDef.criarBranch("TYPE");
        mDefType.setNome("bool");
        mDefType.setValor("CONCRETE");

        AST mDefValue = mDef.criarBranch("VALUE");
        mDefValue.setNome("R0");
        mDefValue.setValor("REG");

        AST mIf = ASTLocal.criarBranch("IF");

        AST mCONDITION = mIf.criarBranch("CONDITION");
        mCONDITION.setNome(eResultado);
        mCONDITION.setValor("ID");

        AST mIfBody = mIf.criarBranch("BODY");
        AST mEXCEPTION = mIfBody.criarBranch("EXCEPTION");

        AST mEXCEPTIONVALUE = mEXCEPTION.criarBranch("VALUE");
        mEXCEPTIONVALUE.setNome(" O stage " + eVar + " e nulo !");
        mEXCEPTIONVALUE.setValor("ID");
    }

    public void obter_lado(AST ASTLocal, String eVar, String eResultado) {




        AST mDef = ASTLocal.criarBranch("DEF");
        mDef.setNome(eResultado);

        AST mDefType = mDef.criarBranch("TYPE");
        mDefType.setNome("int");
        mDefType.setValor("CONCRETE");

        AST mDefValue = mDef.criarBranch("VALUE");
        mDefValue.setNome("");
        mDefValue.setValor("NULL");

        AST mEsquerda_R0 = ASTLocal.criarBranch("STAGE_GET");
        mEsquerda_R0.setNome(eVar);
        mEsquerda_R0.setValor(eResultado);

    }


    public void obter_ladoantigo(AST ASTLocal, String eVar, String eResultado) {

        AST mEsquerda_R0 = ASTLocal.criarBranch("REG");
        mEsquerda_R0.setNome("R19");
        AST mEsquerda_R0_Value = mEsquerda_R0.criarBranch("VALUE");
        mEsquerda_R0_Value.setNome(eVar);
        mEsquerda_R0_Value.setValor("ID");

        AST mProc = ASTLocal.criarBranch("PROC");

        AST mSet = mProc.criarBranch("SET");
        mSet.setNome("R5");
        mSet.setValor("ID");

        AST mMov = mProc.criarBranch("MOV");
        mMov.setNome("0");
        mMov.setValor("Num");

        AST mStage = mProc.criarBranch("STAGE");
        mStage.setNome("R19");
        mStage.setValor("ID");


        AST mDef = ASTLocal.criarBranch("DEF");
        mDef.setNome(eResultado);

        AST mDefType = mDef.criarBranch("TYPE");
        mDefType.setNome("int");
        mDefType.setValor("CONCRETE");

        AST mDefValue = mDef.criarBranch("VALUE");
        mDefValue.setNome("R5");
        mDefValue.setValor("REG");


    }

    public void operador(AST ASTLocal, String eOperador, String eA1, String eA2, String eRetorno) {

        AST mREG_A = ASTLocal.criarBranch("REG");
        mREG_A.setNome("R5");

        AST mREG_A_Value = mREG_A.criarBranch("VALUE");
        mREG_A_Value.setNome(eA1);
        mREG_A_Value.setValor("ID");

        AST mREG_B = ASTLocal.criarBranch("REG");
        mREG_B.setNome("R6");

        AST mREG_B_Value = mREG_B.criarBranch("VALUE");
        mREG_B_Value.setNome(eA2);
        mREG_B_Value.setValor("ID");

        AST mProc = ASTLocal.criarBranch("PROC");

        AST mSet = mProc.criarBranch("SET");
        mSet.setNome("R3");
        mSet.setValor("ID");

        AST mMov = mProc.criarBranch("MOV");
        mMov.setNome("FALSE");
        mMov.setValor("ID");

        AST mOPE = mProc.criarBranch("OPE");
        mOPE.setNome(eOperador);

        AST mRIGHT = mOPE.criarBranch("RIGHT");
        mRIGHT.setNome("R5");
        mRIGHT.setValor("ID");

        AST mLEFT = mOPE.criarBranch("LEFT");
        mLEFT.setNome("R6");
        mLEFT.setValor("ID");


        AST mAplicavel = ASTLocal.criarBranch("APPLY");

        AST mAplicavel_SET = mAplicavel.criarBranch("SETTABLE");
        mAplicavel_SET.setNome(eRetorno);
        mAplicavel_SET.setValor("ID");

        AST mAplicavel_VAL = mAplicavel.criarBranch("VALUE");
        mAplicavel_VAL.setNome("R3");
        mAplicavel_VAL.setValor("REG");

    }
}
