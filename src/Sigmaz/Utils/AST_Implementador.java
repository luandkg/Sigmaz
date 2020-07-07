package Sigmaz.Utils;

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

    public AST criar_Def(String eNome, AST eTipo) {
        AST mDefWhile = new AST("DEF");
        mDefWhile.setNome(eNome);
        mDefWhile.getASTS().add(eTipo);
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

}
