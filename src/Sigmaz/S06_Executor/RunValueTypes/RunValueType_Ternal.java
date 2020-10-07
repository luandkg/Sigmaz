package Sigmaz.S06_Executor.RunValueTypes;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Ternal {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Ternal (RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Ternal";

    }

    public void init(Run_Value eRunValue,AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());

        Run_Value mRun_Value = new Run_Value(mRunTime, mEscopo);
        mRun_Value.init(ASTCorrente.getBranch("CONDITION"), "bool");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mRun_Value.getConteudo().contentEquals("true")) {

            Run_Value sRun_Value = new Run_Value(mRunTime, mEscopo);
            sRun_Value.init(ASTCorrente.getBranch("TRUE"), eRetorno);

            eRunValue.setNulo(sRun_Value.getIsNulo());
            eRunValue.setPrimitivo(sRun_Value.getIsPrimitivo());
            eRunValue.setConteudo(sRun_Value.getConteudo());
            eRunValue.setRetornoTipo(sRun_Value.getRetornoTipo());

        } else {

            if (ASTCorrente.existeBranch("FALSE")) {

                Run_Value sRun_Value = new Run_Value(mRunTime, mEscopo);
                sRun_Value.init(ASTCorrente.getBranch("FALSE"), eRetorno);

                eRunValue.setNulo(sRun_Value.getIsNulo());
                eRunValue.setPrimitivo(sRun_Value.getIsPrimitivo());
                eRunValue.setConteudo(sRun_Value.getConteudo());
                eRunValue.setRetornoTipo(sRun_Value.getRetornoTipo());

            }


        }


        //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + this.getConteudo() + " P : " + mIsPrimitivo + " N : " + mIsNulo + " T : " + mRetornoTipo);


    }


}
