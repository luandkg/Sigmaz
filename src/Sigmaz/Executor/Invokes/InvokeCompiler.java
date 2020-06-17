package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class InvokeCompiler {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public InvokeCompiler(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("SHOW_SCOPE")) {

            show_scope(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_GLOBAL")) {

            show_global(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }


    }

    public void show_scope(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {

            if (eSaida.contentEquals("ALL")) {
                mEscopo.ListarAll();
            } else  if (eSaida.contentEquals("ACTIONS")) {
                mEscopo.ListarActions();
            } else  if (eSaida.contentEquals("FUNCTIONS")) {
                mEscopo.ListarFunctions();
            } else  if (eSaida.contentEquals("DEFINES")) {
                mEscopo.ListarDefines();
            } else  if (eSaida.contentEquals("CONSTANTS")) {
                mEscopo.ListarConstants();
            } else{
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

    public void show_global(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {

            if (eSaida.contentEquals("ALL")) {

                mEscopo.ListarGlobalAll();

            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

            } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }


}
