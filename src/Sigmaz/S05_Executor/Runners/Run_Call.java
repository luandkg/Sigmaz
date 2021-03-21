package Sigmaz.S05_Executor.Runners;

import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S08_Utilitarios.AST;

public class Run_Call {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Call(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Call";

    }

    public void init(AST ASTCorrente){


        Escopo mEscopoCall = new Escopo(mRunTime,mEscopo);

        mEscopoCall.setNome("Call::"+ASTCorrente.getNome());

        if (ASTCorrente.mesmoValor(Orquestrantes.REFER)) {
            AST mSending = ASTCorrente.getBranch(Orquestrantes.SENDING);
            Run_Any mAST = new Run_Any(mRunTime);
            mAST.init_ActionFunction(mSending, mEscopoCall);
        } else {

            Run_Body mAST = new Run_Body(mRunTime, mEscopoCall);
            mAST.init(ASTCorrente.getBranch(Orquestrantes.BODY));

        }


    }


}
