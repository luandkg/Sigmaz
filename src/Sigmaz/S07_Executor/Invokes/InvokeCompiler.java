package Sigmaz.S07_Executor.Invokes;

import Sigmaz.S07_Executor.*;
import Sigmaz.S07_Executor.Escopos.Run_Explicit;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Escopos.Run_Type;
import Sigmaz.S07_Executor.Runners.*;
import Sigmaz.S00_Utilitarios.AST;

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

        } else if (eAcao.contentEquals("SHOW_REGRESSIVE")) {

            show_regressive(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_HEAP")) {

            show_heap(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("SHOW_EXTERN")) {

            show_extern(eAcao, eSaida, ASTArgumentos);


        } else if (eAcao.contentEquals("UNIQUE_STRUCT")) {

            show_unique_struct(eAcao, eSaida, ASTArgumentos);


        } else if (eAcao.contentEquals("SHOW_STRUCT")) {

            show_struct(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("OBJECT")) {

            object(eAcao, eSaida, ASTArgumentos);
        } else if (eAcao.contentEquals("EXISTS")) {

            exists(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("TYPES")) {

            types(eAcao, eSaida, ASTArgumentos);

        } else if (eAcao.contentEquals("STRUCTS")) {

            structs(eAcao, eSaida, ASTArgumentos);

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

                if (mRunTime.getVisibilidade()) {

                    System.out.println("\n ######################### SIGMAZ - HEAP ############################ ");

                    for (Run_Struct mRun_Struct : mRunTime.getHeap().getStructs_Instances()) {


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

    public void structs(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {
            if (eSaida.contentEquals("INSTANCES")) {

                if (mRunTime.getVisibilidade()) {

                    System.out.println("\n ######################### SIGMAZ - STRUCTS : INSTANCES ############################ ");

                    for (Run_Struct mRun_Struct : mRunTime.getHeap().getStructs_Instances()) {

                        System.out.println("\t -  Struct : " + mRun_Struct.getNome());

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

    public void types(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {
            if (eSaida.contentEquals("INSTANCES")) {

                if (mRunTime.getVisibilidade()) {

                    System.out.println("\n ######################### TYPES - INSTANCES ############################ ");

                    for (Run_Type mRun_Struct : mRunTime.getHeap().getTypes_Instances()) {

                        System.out.println("\t - " + mRun_Struct.getTypeNome() + " -> " + mRun_Struct.getNome());

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

                if (mRunTime.getVisibilidade()) {

                    System.out.println("\n ######################### SIGMAZ - EXTERN ############################ ");

                    for (Run_Explicit mRun_Struct : mRunTime.getExternals().getExterns()) {


                        System.out.println("\t - Struct : " + mRun_Struct.getNome());
                        System.out.println("");
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

                if (mRunTime.getVisibilidade()) {
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

                if (mRunTime.getVisibilidade()) {

                    Run_Struct mRun = mRunTime.getHeap().getRun_Struct(a);
                    if (mRunTime.getErros().size() > 0) {
                        return ;
                    }

                }


            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

    public void object(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 1) {

            if (eSaida.contentEquals("REMOVE")) {

                Run_Context mRun_Context = new Run_Context(mRunTime);

                String eTipo = mRun_Invoke.getTipo(ASTArgumentos, 1);
                String eNome = mRun_Invoke.getQualquer(ASTArgumentos, 1);

                String eQualificador = mRun_Context.getQualificador(eTipo,mEscopo);

                // System.out.println("Removendo : " + eNome + " :: " + eQualificador);

                if (eQualificador.contentEquals("STRUCT")) {

                    mRunTime.getHeap().removerStruct(eNome);


                } else if (eQualificador.contentEquals("TYPE")) {

                    mRunTime.getHeap().removerType(eNome);


                }

                //  System.out.println("Removendo : " + eNome);

            } else {
                mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com saida !");
            }

        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

    public void exists(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 1) {


            Run_Context mRun_Context = new Run_Context(mRunTime);

                String eTipo = mRun_Invoke.getTipo(ASTArgumentos, 1);
                String eNome = mRun_Invoke.getQualquer(ASTArgumentos, 1);

                String eQualificador = mRun_Context.getQualificador(eTipo,mEscopo);

                // System.out.println("Removendo : " + eNome + " :: " + eQualificador);
            mEscopo.setDefinido(eSaida, "false");

                if (eQualificador.contentEquals("STRUCT")) {

                    if (mRunTime.getHeap().existeStruct(eNome)){
                        mEscopo.setDefinido(eSaida, "true");

                    }


                } else if (eQualificador.contentEquals("TYPE")) {

                    if (mRunTime.getHeap().existeType(eNome)){
                        mEscopo.setDefinido(eSaida, "true");

                    }

                }

                //  System.out.println("Removendo : " + eNome);



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



        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

    public void show_regressive(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {



        } else {


            mRunTime.getErros().add("Invocacao : " + eAcao + " -> Problema com argumentos !");
        }


    }

}
