package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

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
