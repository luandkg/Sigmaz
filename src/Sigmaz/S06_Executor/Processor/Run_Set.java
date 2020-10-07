package Sigmaz.S06_Executor.Processor;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.RunTime;

public class Run_Set {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Set(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Set";

    }


    public void init(AST ASTCorrente) {

    }


}
