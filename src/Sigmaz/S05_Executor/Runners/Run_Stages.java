package Sigmaz.S05_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

public class Run_Stages {


    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Stages(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Stages";

    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }


    public void init(AST ASTCorrente) {


        Item mAlpha = mEscopo.getItem(ASTCorrente.getNome());
        Item mBeta = mEscopo.getItem(ASTCorrente.getValor());


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mBeta.setNulo(mAlpha.getNulo(),mRunTime);

        if (!mAlpha.getNulo()) {
            mBeta.setValor(mAlpha.getValor(mRunTime, mEscopo),mRunTime, mEscopo);
        }
    }


}
