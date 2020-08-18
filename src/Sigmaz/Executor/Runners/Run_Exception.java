package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Exception {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Exception(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Exception";


    }

    public void init(AST eAST) {

        AST mValor = eAST.getBranch("VALUE");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, eAST.getValor());

        if (mAST.getIsNulo()) {

            mRunTime.errar(mLocal,"NULL");

        } else if (mAST.getIsPrimitivo()) {
            mRunTime.errar("",mAST.getConteudo());
        }


    }

}
