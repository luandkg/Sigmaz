package Sigmaz.S08_Utilitarios;

public class AST_Implementador {

    public AST criar_ExecuteFunction(String eMaior, String eFuncao) {


        AST mExecute2 = new AST("EXECUTE");
        mExecute2.setNome(eMaior);
        mExecute2.setValor("STRUCT");
        AST mInternal2 = mExecute2.criarBranch("INTERNAL");
        mInternal2.setNome(eFuncao);
        mInternal2.setValor("STRUCT_FUNCT");
        mInternal2.criarBranch("ARGUMENTS");

        return mExecute2;
    }


    public AST criar_Proc_InstrucaoID(AST ASTPai, String eNome, String eValor) {

        AST mProc = ASTPai.criarBranch(eNome);
        mProc.setNome(eValor);
        mProc.setValor("ID");

        return mProc;
    }

    public AST crar_Reg_ValueID(AST ASTPai, String eRegistrador, String eReceber) {

        AST mReg = ASTPai.criarBranch("REG");
        mReg.setNome(eRegistrador);

        AST mValue = mReg.criarBranch("VALUE");
        mValue.setNome(eReceber);
        mValue.setValor("ID");

        return mReg;
    }

    public AST criar_Proc(AST ASTPai) {
        return ASTPai.criarBranch("PROC");
    }

    public AST criar_Proc_InstrucaoText(AST ASTPai, String eNome, String eValor) {

        AST mProc = ASTPai.criarBranch(eNome);
        mProc.setNome(eValor);
        mProc.setValor("Text");

        return mProc;
    }


    public AST criar_ExecuteFunction2Args(String eMaior, String eFuncao, String eArg1, String eValor1, String eArg2, String eValor2) {


        AST mExecute2 = new AST("EXECUTE");
        mExecute2.setNome(eMaior);
        mExecute2.setValor("STRUCT");
        AST mInternal2 = mExecute2.criarBranch("INTERNAL");
        mInternal2.setNome(eFuncao);
        mInternal2.setValor("STRUCT_FUNCT");
        AST eArgs = mInternal2.criarBranch("ARGUMENTS");

        AST a1 = eArgs.criarBranch("ARGUMENT");
        a1.setNome(eArg1);
        a1.setValor(eValor1);

        AST a2 = eArgs.criarBranch("ARGUMENT");
        a2.setNome(eArg2);
        a2.setValor(eValor2);

        return mExecute2;
    }

    public AST criar_Def(String eNome, AST eTipo) {
        AST mDefWhile = new AST("DEF");
        mDefWhile.setNome(eNome);
        mDefWhile.getASTS().add(eTipo);
        return mDefWhile;
    }

    public AST criarDef_ComRegistrador(AST ASTPai, String eNome, String eTipo, String eRegistrador) {


        AST mDef = ASTPai.criarBranch("DEF");
        mDef.setNome(eNome);

        AST ASTTipo = mDef.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

        AST ASTValue = mDef.criarBranch("VALUE");
        ASTValue.setNome(eRegistrador);
        ASTValue.setValor("REG");


        return mDef;
    }

    public AST criar_Def_ValueText(AST ASTPai, String eNome, String eTipo, String eTexto) {


        AST mDef = ASTPai.criarBranch("DEF");
        mDef.setNome(eNome);

        AST ASTTipo = mDef.criarBranch("TYPE");
        ASTTipo.setNome(eTipo);
        ASTTipo.setValor("CONCRETE");

        AST ASTValue = mDef.criarBranch("VALUE");
        ASTValue.setNome(eTexto);
        ASTValue.setValor("Text");


        return mDef;
    }

    public AST criar_DefCom_ValueStructFunction(String eNome, AST eTipo, String eNomeStruct, String eFuncaoStruct) {
        AST mDefWhile = new AST("DEF");
        mDefWhile.setNome(eNome);
        mDefWhile.getASTS().add(eTipo);

        AST mVal = mDefWhile.criarBranch("VALUE");
        mVal.setNome(eNomeStruct);
        mVal.setValor("STRUCT");
        AST mInternalVal = mVal.criarBranch("INTERNAL");
        mInternalVal.setNome(eFuncaoStruct);
        mInternalVal.setValor("STRUCT_FUNCT");
        mInternalVal.criarBranch("ARGUMENTS");

        return mDefWhile;
    }

    public AST criar_ValueStructFunction(String eNome, String eFuncao) {

        AST mVal = new AST("VALUE");
        mVal.setNome(eNome);
        mVal.setValor("STRUCT");
        AST mInternalVal = mVal.criarBranch("INTERNAL");
        mInternalVal.setNome(eFuncao);
        mInternalVal.setValor("STRUCT_FUNCT");
        mInternalVal.criarBranch("ARGUMENTS");
        return mVal;

    }


    public void adicionar(AST eLocal, AST eNovo) {

        eLocal.getASTS().add(eNovo);

    }

    public void copiarBranches(AST eLocal, AST eOutro) {

        eLocal.getASTS().addAll(eOutro.getASTS());

    }

    public AST criar_CondicaoStruct_Func(String eStruct, String eFunc) {

        AST mCondition = new AST("CONDITION");
        mCondition.setNome(eStruct);

        mCondition.setValor("STRUCT");
        AST mInternalCondition = mCondition.criarBranch("INTERNAL");
        mInternalCondition.setNome(eFunc);
        mInternalCondition.setValor("STRUCT_FUNCT");
        mInternalCondition.criarBranch("ARGUMENTS");

        return mCondition;
    }

    public AST criar_InitGenerico(String eStruct, String mTipagem) {

        AST eAST = new AST("INIT");
        eAST.setNome(eStruct);
        AST eGen = eAST.criarBranch("GENERIC");

        AST eType = eGen.criarBranch("TYPE");
        eType.setNome(mTipagem);
        eGen.setNome("TRUE");

        AST eArgs = eAST.criarBranch("ARGUMENTS");

        return eAST;
    }

    public AST criar_InitGenerico(String eStruct, String mT1,String mT2) {

        AST eAST = new AST("INIT");
        eAST.setNome(eStruct);
        AST eGen = eAST.criarBranch("GENERIC");

        AST eType = eGen.criarBranch("TYPE");
        eType.setNome(mT1);

        AST eType2 = eGen.criarBranch("TYPE");
        eType2.setNome(mT2);

        eGen.setNome("TRUE");

        AST eArgs = eAST.criarBranch("ARGUMENTS");

        return eAST;
    }
}
