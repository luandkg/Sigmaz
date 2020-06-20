package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Run_Struct;
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

        } else if (eAcao.contentEquals("SHOW_HEAP")) {

            show_heap(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_STRUCT")) {

            show_struct(eAcao, eSaida, ASTArgumentos);

        } else {

            mRunTime.getErros().add("Invocacao : Acao nao encontrada ->  " + eAcao);

        }


    }

    public void show_heap(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {
            if (eSaida.contentEquals("ALL")) {

                if (mRunTime.getExterno()) {

                    System.out.println("\n ######################### SIGMAZ - HEAP ############################ ");

                    for (Run_Struct mRun_Struct : mRunTime.getHeap()) {


                        System.out.println("\t - " + mRun_Struct.getNome());
                        System.out.println("");
                        System.out.println("\t\t -  Struct : " + mRun_Struct.getStructNome());
                        System.out.println("");
                        System.out.println("\t\t -  Functions : " + mRun_Struct.getFunctions().size());
                        System.out.println("\t\t -  Actions : " + mRun_Struct.getActions().size());
                        System.out.println("\t\t -  Tamanho : " + mRun_Struct.getTamanho());
                        System.out.println("");

                    }

                    System.out.println(" ######################### ##### ############################ ");

                }


            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }
        } else {
            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }

    }

    public void show_struct(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {

            if (eSaida.contentEquals("ALL")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarStruct();
                }


            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
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
                if (mRunTime.getExterno()) {
                    mEscopo.ListarAll();
                }


            } else if (eSaida.contentEquals("ACTIONS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.ListarActions();
                }

            } else if (eSaida.contentEquals("FUNCTIONS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.ListarFunctions();
                }

            } else if (eSaida.contentEquals("DEFINES")) {
                if (mRunTime.getExterno()) {
                    mEscopo.ListarDefines();
                }


            } else if (eSaida.contentEquals("CONSTANTS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.ListarConstants();
                }

            } else {
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

                if (mRunTime.getExterno()) {
                    mEscopo.ListarGlobalAll();
                }


            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }


}
