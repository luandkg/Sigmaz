package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Run_Invoke;
import Sigmaz.Utils.AST;

public class InvokeStages {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;


    public InvokeStages(RunTime eRunTime, Escopo eEscopo, Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke = eRun_Invoke;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = mRun_Invoke.argumentosContagem(ASTArgumentos);


        if (i == 2) {

            argumentos_2(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }

    }





    public void argumentos_2(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("match")) {

            String p1 = mRun_Invoke.getQualquer(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getQualquer(ASTArgumentos, 2);


            if (p1.contentEquals(p2)) {
                mEscopo.setDefinido(eSaida, "true");
            } else {
                mEscopo.setDefinido(eSaida, "false");
            }

        } else     if (eAcao.contentEquals("unmatch")) {

            String p1 = mRun_Invoke.getQualquer(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getQualquer(ASTArgumentos, 2);


            if (p1.contentEquals(p2)) {
                mEscopo.setDefinido(eSaida, "false");
            } else {
                mEscopo.setDefinido(eSaida, "true");
            }



        } else {

            mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

        }

    }


}


