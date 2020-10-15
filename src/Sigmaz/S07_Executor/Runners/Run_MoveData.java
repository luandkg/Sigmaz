package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

public class Run_MoveData {


    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_MoveData(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_MoveData";

    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }


    public void init(AST ASTCorrente) {


        Item mAlpha = mEscopo.getItem(ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Item mBeta = mEscopo.getItem(ASTCorrente.getValor());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mAlpha.setNulo(mBeta.getNulo());

        if (!mBeta.getNulo()) {
            mAlpha.setValor(mBeta.getValor(mRunTime, mEscopo),mRunTime, mEscopo);
        }


    }


}
