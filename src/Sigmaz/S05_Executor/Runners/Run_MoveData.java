package Sigmaz.S05_Executor.Runners;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

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

        mAlpha.setNulo(mBeta.getNulo(),mRunTime);

        if (!mBeta.getNulo()) {
            mAlpha.setValor(mBeta.getValor(mRunTime, mEscopo),mRunTime, mEscopo);
        }


    }


}
