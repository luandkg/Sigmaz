package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Execute {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Execute(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST ASTCorrente) {

        if (ASTCorrente.mesmoValor("FUNCT")) {


            Run_ActionFunction mAST = new Run_ActionFunction(mRunTime, mEscopo);
            mAST.init(ASTCorrente);


        } else {
            mRunTime.getErros().add("Problema na execucao : " + ASTCorrente.getNome());
        }


    }


}
