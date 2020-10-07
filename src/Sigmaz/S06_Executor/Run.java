package Sigmaz.S06_Executor;

import Sigmaz.S06_Executor.Escopos.Run_Explicit;
import Sigmaz.S06_Executor.Runners.*;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Run {

    private RunTime mRunTime;

    public Run(RunTime eRunTime) {
        mRunTime = eRunTime;
    }

    public void runSigmaz(AST ASTSigmaz_Call, AST ASTCGlobal, Escopo Global) {


        ArrayList<AST> mGlobalPackages = new ArrayList<AST>();


        for (AST ASTC : ASTCGlobal.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {

                Global.adicionarRefer(ASTC.getNome());


            } else if (ASTC.mesmoTipo("PACKAGE")) {
                mGlobalPackages.add(ASTC);

                for (AST eAST : ASTC.getASTS()) {
                    if (eAST.mesmoTipo("STRUCT")) {


                        mRunTime.getExternals().adicionar(ASTC.getNome(), eAST, ASTCGlobal);
                    }

                }


            } else if (ASTC.mesmoTipo("STRUCT")) {


                Run_Explicit mRE = mRunTime.getExternals().adicionar("", ASTC, ASTCGlobal);

                Global.externalizar(mRE.getNomeCompleto());


            }

            if (mRunTime.getErros().size() > 0) {
                return;
            }
        }


        for (Run_Explicit RE : mRunTime.getExternals().getExterns()) {

            RE.run();

            if (mRunTime.getErros().size() > 0) {
                return;
            }
        }


        Run_Alocadores mRun_Alocadores = new Run_Alocadores(mRunTime, Global);
        mRun_Alocadores.init(ASTSigmaz_Call);


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        for (AST ASTC : ASTSigmaz_Call.getASTS()) {

            if (ASTC.mesmoTipo("CALL")) {

                Run_Call mRun_Call = new Run_Call(mRunTime, Global);

                mRun_Call.init(ASTC);

            }


            if (mRunTime.getErros().size() > 0) {
                break;
            }

        }

    }


}
