package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.*;
import Sigmaz.Executor.Runners.Run_Extern;
import Sigmaz.Executor.Runners.Run_Invoke;
import Sigmaz.Executor.Runners.Run_Struct;
import Sigmaz.Utils.AST;

public class InvokeCompiler {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private Run_Invoke mRun_Invoke;

    public InvokeCompiler(RunTime eRunTime, Escopo eEscopo, Run_Invoke eRun_Invoke) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mRun_Invoke = eRun_Invoke;


    }

    public void init(String eAcao, String eSaida, AST ASTArgumentos) {

        if (eAcao.contentEquals("SHOW_SCOPE")) {

            show_scope(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_GLOBAL")) {

            show_global(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_HEAP")) {

            show_heap(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_EXTERN")) {

            show_extern(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("UNIQUE_EXTERN")) {

            show_unique_extern(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("UNIQUE_STRUCT")) {

            show_unique_struct(eAcao, eSaida, ASTArgumentos);


        } else if (eAcao.contentEquals("SHOW_STRUCT")) {

            show_struct(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("STRUCT")) {

            struct(eAcao, eSaida, ASTArgumentos);


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

    public void show_extern(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {
            if (eSaida.contentEquals("ALL")) {

                if (mRunTime.getExterno()) {

                    System.out.println("\n ######################### SIGMAZ - EXTERN ############################ ");

                    for (Run_Extern mRun_Struct : mRunTime.getExtern()) {


                        System.out.println("\t - Struct : " + mRun_Struct.getNome());
                        System.out.println("");
                        System.out.println("\t\t -  Defines : " + mRun_Struct.getDefines());
                        System.out.println("\t\t -  Mockizes : " + mRun_Struct.getMockizes());
                        System.out.println("\t\t -  Actions : " + mRun_Struct.getActions());
                        System.out.println("\t\t -  Functions : " + mRun_Struct.getFunctions());
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


    public void show_unique_struct(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        String a = mRun_Invoke.getQualquer(ASTArgumentos,1);

        if (i == 1) {

            if (eSaida.contentEquals("ALL")) {

                if (mRunTime.getExterno()) {

                    Run_Struct mRun = mRunTime.getRun_Struct(a);
                    mRun.getEscopo().getDebug().mostrar_Struct(mRun.getEscopo());

                }


            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

    public void struct(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 1) {

            if (eSaida.contentEquals("REMOVE")) {


                String eNome = mRun_Invoke.getQualquer(ASTArgumentos, 1);

                //  System.out.println("Removendo : " + eNome);
                mRunTime.removerHeap(eNome);

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

            } else if (eSaida.contentEquals("STACK")) {

                if (mRunTime.getExterno()) {
                    mEscopo.ListarGlobalStack();
                }

            } else if (eSaida.contentEquals("COUNT")) {

                if (mRunTime.getExterno()) {

                    System.out.println("\n Contando Escopos : " + mEscopo.getContagem());

                }
            } else if (eSaida.contentEquals("PATH")) {

                if (mRunTime.getExterno()) {

                    System.out.println("\n Caminho Escopos : " + mEscopo.getCaminho());

                }
            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

    public void show_unique_extern(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;

        String eNome = "";

        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
                eNome = eAST.getNome();
            }
        }


        if (i == 1) {
            if (eSaida.contentEquals("ALL")) {

                if (mRunTime.getExterno()) {


                    Run_Extern mRun_Struct = mRunTime.getRun_Extern(eNome);

                    if (mRunTime.getErros().size()>0) {
                      return;
                    }


                    mRun_Struct.mostrar();



                }


            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }


        } else {
            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }

    }

}
