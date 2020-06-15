package Sigmaz.Executor;

import Sigmaz.Executor.Invokes.InvokeCompiler;
import Sigmaz.Executor.Invokes.InvokeMath;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Invoke {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Invoke(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST ASTCorrente) {

        AST mSaida = ASTCorrente.getBranch("EXIT");
        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");

        String eNome = ASTCorrente.getNome();
        String eAcao = ASTCorrente.getValor();
        String eSaida = mSaida.getNome();



        if (eNome.contentEquals("math")) {

            InvokeMath mCor = new InvokeMath(mRunTime,mEscopo);

            mCor.init(eAcao, eSaida, mArgumentos);

        } else   if (eNome.contentEquals("__COMPILER__")) {

            InvokeCompiler mCor = new InvokeCompiler(mRunTime,mEscopo);

            mCor.init(eAcao, eSaida, mArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Biblioteca nao encontrada ->  " + eNome);

        }


    }



}

