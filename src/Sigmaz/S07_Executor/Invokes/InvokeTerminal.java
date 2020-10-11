package Sigmaz.S07_Executor.Invokes;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_Invoke;
import Sigmaz.S00_Utilitarios.AST;

public class InvokeTerminal {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;


    public InvokeTerminal(RunTime eRunTime, Escopo eEscopo, Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke = eRun_Invoke;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = mRun_Invoke.argumentosContagem(ASTArgumentos);

        if (i == 0) {

            argumentos_vazio(eAcao, eSaida, ASTArgumentos);

        } else if (i == 1) {

            argumentos_1num(eAcao, eSaida, ASTArgumentos);

        } else if (i == 2) {

            argumentos_2(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }

    }

    public void argumentos_1num(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = 0;

        String p1 = mRun_Invoke.getQualquer(ASTArgumentos, 1);




            if (eAcao.contentEquals("print")) {


                if(mRunTime.getVisibilidade()){
                    System.out.print(p1 );
                }

                mEscopo.setDefinido(eSaida, "true");

            } else if (eAcao.contentEquals("cast_ns")) {

                String s = String.valueOf(p1);

                mEscopo.setDefinido(eSaida, s);

            } else if (eAcao.contentEquals("cast_bs")) {

                String s = String.valueOf(p1);


                mEscopo.setDefinido(eSaida, s);

            } else if (eAcao.contentEquals("cast_sb")) {

                String s = String.valueOf(p1);

                if (s.contentEquals("true") || s.contentEquals("false")) {
                    mEscopo.setDefinido(eSaida, s);
                } else {
                    mRunTime.getErros().add("Conversao impossivel !");
                }
            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);


            }


    }

    public void argumentos_vazio(String eAcao, String eSaida, AST ASTArgumentos) {


        int i = 0;

        for (AST eAST : ASTArgumentos.getASTS()) {

            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }

        }

        if (i == 0) {

            if (eAcao.contentEquals("change")) {

                if(mRunTime.getVisibilidade()) {
                    System.out.print("\n");
                }


                mEscopo.setDefinido(eSaida, "true");


            } else {

                mRunTime.getErros().add("Invocacao : Ação inconsistente ->  " + eAcao);

            }

        }
    }


    public void argumentos_2(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("operator_match")) {

            String p1 = mRun_Invoke.getString(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getString(ASTArgumentos, 2);


            if (p1.contentEquals(p2)) {
                mEscopo.setDefinido(eSaida, "true");
            } else {
                mEscopo.setDefinido(eSaida, "false");
            }


        } else if (eAcao.contentEquals("operator_unmatch")) {

            String p1 = mRun_Invoke.getString(ASTArgumentos, 1);
            String p2 = mRun_Invoke.getString(ASTArgumentos, 2);


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


