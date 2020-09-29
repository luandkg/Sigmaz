package Sigmaz.Executor.Invokes;

import Sigmaz.Executor.*;
import Sigmaz.Executor.Runners.Run_Extern;
import Sigmaz.Executor.Runners.Run_Invoke;
import Sigmaz.Executor.Runners.Run_Struct;
import Sigmaz.Executor.Runners.Run_Type;
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

    public void structs(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {
            if (eSaida.contentEquals("INSTANCES")) {

                if (mRunTime.getExterno()) {

                    System.out.println("\n ######################### SIGMAZ - STRUCTS : INSTANCES ############################ ");

                    for (Run_Struct mRun_Struct : mRunTime.getHeap()) {

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

                if (mRunTime.getExterno()) {

                    System.out.println("\n ######################### TYPES - INSTANCES ############################ ");

                    for (Run_Type mRun_Struct : mRunTime.getTypes_Instances()) {

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

                if (mRunTime.getExterno()) {

                    System.out.println("\n ######################### SIGMAZ - EXTERN ############################ ");

                    for (Run_Extern mRun_Struct : mRunTime.getExtern()) {


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


                String eTipo = mRun_Invoke.getTipo(ASTArgumentos, 1);
                String eNome = mRun_Invoke.getQualquer(ASTArgumentos, 1);

                String eQualificador = mRunTime.getQualificador(eTipo,mEscopo.getRefers());

                // System.out.println("Removendo : " + eNome + " :: " + eQualificador);

                if (eQualificador.contentEquals("STRUCT")) {

                    mRunTime.removerHeap(eNome);


                } else if (eQualificador.contentEquals("TYPE")) {

                    mRunTime.removerType(eNome);


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



                String eTipo = mRun_Invoke.getTipo(ASTArgumentos, 1);
                String eNome = mRun_Invoke.getQualquer(ASTArgumentos, 1);

                String eQualificador = mRunTime.getQualificador(eTipo,mEscopo.getRefers());

                // System.out.println("Removendo : " + eNome + " :: " + eQualificador);
            mEscopo.setDefinido(eSaida, "false");

                if (eQualificador.contentEquals("STRUCT")) {

                    if (mRunTime.existeStruct(eNome)){
                        mEscopo.setDefinido(eSaida, "true");

                    }


                } else if (eQualificador.contentEquals("TYPE")) {

                    if (mRunTime.existeType(eNome)){
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

            if (eSaida.contentEquals("ALL")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarAll();
                }

            } else if (eSaida.contentEquals("STACK")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarStack();
                }

            } else if (eSaida.contentEquals("ACTIONS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarActions();
                }

            } else if (eSaida.contentEquals("FUNCTIONS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarFunctions();
                }

            } else if (eSaida.contentEquals("DEFINES")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarDefines();

                }


            } else if (eSaida.contentEquals("CONSTANTS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarConstantes();
                }

            } else if (eSaida.contentEquals("STRUCTS")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarStructs();
                }
            } else if (eSaida.contentEquals("STAGES")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarStages();
                }
            } else if (eSaida.contentEquals("PACKAGES")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarPackages();
                }
            } else if (eSaida.contentEquals("EXTERNS")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarExterns();
                }
            } else if (eSaida.contentEquals("REFER")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarRefers();
                }

            } else if (eSaida.contentEquals("LOCAL")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarLocal();
                }


            } else if (eSaida.contentEquals("AUTO")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarAuto();
                }

            } else if (eSaida.contentEquals("FUNCTOR")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getLocalDebug().ListarFunctor();
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
                    mRunTime.getGlobalDebug().ListarGlobalAll();
                }

            } else if (eSaida.contentEquals("STACK")) {

                if (mRunTime.getExterno()) {
                    mRunTime.getGlobalDebug().ListarGlobalStack();
                }


            } else if (eSaida.contentEquals("ACTIONS")) {

                if (mRunTime.getExterno()) {
              //      mRunTime.getGlobalDebug().ListarGlobalActions();
                }
            } else if (eSaida.contentEquals("FUNCTIONS")) {

                if (mRunTime.getExterno()) {
                 //   mRunTime.getGlobalDebug().ListarGlobalFunctions();
                }
            } else if (eSaida.contentEquals("OPERATIONS")) {

                if (mRunTime.getExterno()) {
                 //   mRunTime.getGlobalDebug().ListarGlobalOperations();
                }
            } else if (eSaida.contentEquals("STAGES")) {

                if (mRunTime.getExterno()) {
               //     mRunTime.getGlobalDebug().ListarGlobalStages();
                }
            } else if (eSaida.contentEquals("STRUCTS")) {

                if (mRunTime.getExterno()) {
            //        mRunTime.getGlobalDebug().ListarGlobalStructs();
                }
            } else if (eSaida.contentEquals("PACKAGES")) {

                if (mRunTime.getExterno()) {
              //      mRunTime.getGlobalDebug().ListarGlobalPackages();
                }
            } else if (eSaida.contentEquals("EXTERNS")) {

                if (mRunTime.getExterno()) {
              //      mRunTime.getGlobalDebug().ListarGlobalExterns();
                }
            } else if (eSaida.contentEquals("REFER")) {

                if (mRunTime.getExterno()) {
                //    mRunTime.getGlobalDebug().ListarGlobalRefers();
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

    public void show_regressive(String eAcao, String eSaida, AST ASTArgumentos) {

        int i = 0;


        for (AST eAST : ASTArgumentos.getASTS()) {
            if (eAST.mesmoTipo("ARGUMENT")) {
                i += 1;
            }
        }

        if (i == 0) {

            if (eSaida.contentEquals("ALL")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalAll();
                }

            } else if (eSaida.contentEquals("STACK")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getRegressiveDebug().ListarRegressiveStack();
                }


            } else if (eSaida.contentEquals("ACTIONS")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalActions();
                }
            } else if (eSaida.contentEquals("FUNCTIONS")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalFunctions();
                }
            } else if (eSaida.contentEquals("OPERATIONS")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalOperations();
                }
            } else if (eSaida.contentEquals("STAGES")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalStages();
                }
            } else if (eSaida.contentEquals("STRUCTS")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalStructs();
                }
            } else if (eSaida.contentEquals("PACKAGES")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalPackages();
                }
            } else if (eSaida.contentEquals("EXTERNS")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalExterns();
                }
            } else if (eSaida.contentEquals("REFER")) {

                if (mRunTime.getExterno()) {
                    mEscopo.getDebug().ListarGlobalRefers();
                }

            } else if (eSaida.contentEquals("AUTO")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getRegressiveDebug().ListarAuto();
                }

            } else if (eSaida.contentEquals("FUNCTOR")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getRegressiveDebug().ListarFunctor();
                }

            } else if (eSaida.contentEquals("MARK")) {
                if (mRunTime.getExterno()) {
                    mEscopo.getRegressiveDebug().ListarMark();
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

}
