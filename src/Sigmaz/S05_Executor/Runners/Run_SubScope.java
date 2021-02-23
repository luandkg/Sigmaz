package Sigmaz.S05_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;

public class Run_SubScope {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private boolean mRetornado;

    public Run_SubScope(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_SubScope";

        mRetornado=false;

    }

    public void init(AST ASTCorrente) {

        Escopo eNovoEscopo = new Escopo(mRunTime, mEscopo);
        eNovoEscopo.setNome("SUBSCOPE::" + mEscopo.getNome());

        Run_Body mAST = new Run_Body(mRunTime, eNovoEscopo);
        mAST.init(ASTCorrente);

        mRetornado=false;


        if (mRunTime.getErros().size() == 0 && mAST.getRetornado()) {

            mEscopo.retorne(mAST.getRetorno());
            mRetornado=true;
        }




    }

    public boolean getRetornado(){return mRetornado;}
}
