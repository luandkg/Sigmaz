package Sigmaz.S05_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Escopos.Run_Struct;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

public class Run_Debug {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Debug(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Debug";

    }

    public void init(AST ASTCorrente) {

        if (ASTCorrente.mesmoNome("GLOBAL")) {

            global(ASTCorrente.getValor());

        } else if (ASTCorrente.mesmoNome("LOCAL")) {

            local(ASTCorrente.getValor());

        } else if (ASTCorrente.mesmoNome("REGRESSIVE")) {

            regressive(ASTCorrente.getValor());


        } else if (ASTCorrente.mesmoNome("STRUCT")) {

            // mEscopo.getRegressiveDebug().mapear_regressive_stack();

            boolean enc = false;

            for (Item eItem : mEscopo.getStacksAll()) {
                if (eItem.getNome().contentEquals("this")) {

                    if (eItem.getIsEstrutura()) {

                        if (!eItem.getNulo()) {

                            Run_Struct rs = mRunTime.getHeap().getRun_Struct(eItem.getValor(mRunTime, mEscopo));
                            rs.debug();


                        }
                    }

                    enc = true;
                    break;
                }
            }

            if (!enc) {
                mRunTime.errar(mLocal, "Debug STRUCT IMPOSSIVEL !");
            }


        } else {

            mRunTime.errar(mLocal, "Debug desconhecido : " + ASTCorrente.getNome());

        }


    }


    public void local(String eModalidade) {

        if (eModalidade.contentEquals("ALL")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarAll();
            }

        } else if (eModalidade.contentEquals("STACK")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarStack();
            }

        } else if (eModalidade.contentEquals("ACTIONS")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarActions();
            }

        } else if (eModalidade.contentEquals("FUNCTIONS")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarFunctions();
            }

        } else if (eModalidade.contentEquals("DEFINES")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarDefines();

            }


        } else if (eModalidade.contentEquals("CONSTANTS")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarConstantes();
            }

        } else if (eModalidade.contentEquals("STRUCTS")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarStructs();
            }
        } else if (eModalidade.contentEquals("STAGES")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarStages();
            }
        } else if (eModalidade.contentEquals("PACKAGES")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarPackages();
            }
        } else if (eModalidade.contentEquals("EXTERNS")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarExterns();
            }
        } else if (eModalidade.contentEquals("REFER")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarRefers();
            }

        } else if (eModalidade.contentEquals("LOCAL")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarLocal();
            }


        } else if (eModalidade.contentEquals("AUTO")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarAuto();
            }

        } else if (eModalidade.contentEquals("FUNCTOR")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().ListarFunctor();
            }
        } else if (eModalidade.contentEquals("INSTANCES")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getLocalDebug().listarInstances();
            }

        } else {
            mRunTime.getErros().add("Debug Local : " + eModalidade + " -> Desconhecido !");
        }

    }

    public void global(String eModalidade) {

        if (eModalidade.contentEquals("ALL")) {

            if (mRunTime.getVisibilidade()) {
                mRunTime.getGlobalDebug().ListarGlobalAll();
            }

        } else if (eModalidade.contentEquals("STACK")) {

            if (mRunTime.getVisibilidade()) {
                mRunTime.getGlobalDebug().ListarGlobalStack();
            }


        } else if (eModalidade.contentEquals("ACTIONS")) {

            if (mRunTime.getVisibilidade()) {
                //      mRunTime.getGlobalDebug().ListarGlobalActions();
            }
        } else if (eModalidade.contentEquals("FUNCTIONS")) {

            if (mRunTime.getVisibilidade()) {
                //   mRunTime.getGlobalDebug().ListarGlobalFunctions();
            }
        } else if (eModalidade.contentEquals("OPERATIONS")) {

            if (mRunTime.getVisibilidade()) {
                //   mRunTime.getGlobalDebug().ListarGlobalOperations();
            }
        } else if (eModalidade.contentEquals("STAGES")) {

            if (mRunTime.getVisibilidade()) {
                //     mRunTime.getGlobalDebug().ListarGlobalStages();
            }
        } else if (eModalidade.contentEquals("STRUCTS")) {

            if (mRunTime.getVisibilidade()) {
                //        mRunTime.getGlobalDebug().ListarGlobalStructs();
            }
        } else if (eModalidade.contentEquals("PACKAGES")) {

            if (mRunTime.getVisibilidade()) {
                //     mRunTime.getGlobalDebug().ListarGlobalPackages();
            }
        } else if (eModalidade.contentEquals("EXTERNS")) {

            if (mRunTime.getVisibilidade()) {
                //      mRunTime.getGlobalDebug().ListarGlobalExterns();
            }
        } else if (eModalidade.contentEquals("REFER")) {

            if (mRunTime.getVisibilidade()) {
                //    mRunTime.getGlobalDebug().ListarGlobalRefers();
            }


        } else if (eModalidade.contentEquals("INSTANCES")) {

            if (mRunTime.getVisibilidade()) {
                mRunTime.getGlobalDebug().listarInstances();
            }

        } else {
            mRunTime.getErros().add("Debug Global : " + eModalidade + " -> Desconhecido !");
        }

    }

    public void regressive(String eModalidade) {

        if (eModalidade.contentEquals("ALL")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarAll();
            }

        } else if (eModalidade.contentEquals("STACK")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarRegressiveStack();
            }


        } else if (eModalidade.contentEquals("ACTIONS")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarActions();
            }
        } else if (eModalidade.contentEquals("FUNCTIONS")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarFunctions();
            }
        } else if (eModalidade.contentEquals("OPERATORS")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarOperators();
            }
        } else if (eModalidade.contentEquals("STAGES")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarStages();
            }
        } else if (eModalidade.contentEquals("STRUCTS")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarStructs();
            }
        } else if (eModalidade.contentEquals("PACKAGES")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarPackages();
            }
        } else if (eModalidade.contentEquals("EXTERNS")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarExterns();
            }
        } else if (eModalidade.contentEquals("REFER")) {

            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarRefers();
            }

        } else if (eModalidade.contentEquals("AUTO")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarAuto();
            }

        } else if (eModalidade.contentEquals("FUNCTOR")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarFunctor();
            }

        } else if (eModalidade.contentEquals("MARK")) {
            if (mRunTime.getVisibilidade()) {
                mEscopo.getRegressiveDebug().ListarMark();
            }


        } else {
            mRunTime.getErros().add("Debug Regressive : " + eModalidade + " -> Desconhecido !");
        }


    }


}
