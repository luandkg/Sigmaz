package Sigmaz.S08_Executor;

import Sigmaz.S08_Executor.Escopos.Run_External;
import Sigmaz.S08_Executor.Runners.*;

import Sigmaz.S00_Utilitarios.AST;

public class Run {

    private RunTime mRunTime;
    private Escopo mGlobal;

    public Run(RunTime eRunTime, Escopo eGlobal) {
        mRunTime = eRunTime;
        mGlobal = eGlobal;
    }

    public AST getPackage(String eNome) {

        AST mRet = null;

        for (AST ePacote : mRunTime.getGlobalPackages()) {
            if (ePacote.mesmoNome(eNome)) {
                mRet = ePacote;
                break;
            }
        }

        return mRet;
    }

    public void runSigmaz(AST ASTSigmaz_Call, AST ASTCGlobal) {


        mapearPacotes(ASTCGlobal);

        // IMPORTAR PACOTE SIGMAZ

        indexar(ASTCGlobal);

        referenciar(ASTCGlobal);

        externalizar(ASTCGlobal);

        alocadorGlobal(ASTCGlobal);


        if (mRunTime.temErros()) {
            return;
        }

        for (AST ASTC : ASTSigmaz_Call.getASTS()) {
            if (ASTC.mesmoTipo("DEBUG")) {

                Run_Debug mRun_Debug = new Run_Debug(mRunTime, mGlobal);
                mRun_Debug.init(ASTC);

            }
        }


        for (AST ASTC : ASTSigmaz_Call.getASTS()) {

            if (ASTC.mesmoTipo("CALL")) {

                Run_Call mRun_Call = new Run_Call(mRunTime, mGlobal);
                mRun_Call.init(ASTC);

            }


            if (mRunTime.temErros()) {
                return;
            }

        }

    }

    public void mapearPacotes(AST ASTCGlobal) {

        mRunTime.getGlobalPackages().clear();

        for (AST ASTC : ASTCGlobal.getASTS()) {

            if (ASTC.mesmoTipo("PACKAGE")) {

                mRunTime.getGlobalPackages().add(ASTC);

            }

        }

    }

    public void indexar(AST ASTCGlobal) {


        for (AST ASTC : ASTCGlobal.getASTS()) {

            if (ASTC.mesmoTipo("FUNCTION")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("ACTION")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("OPERATOR")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("DIRECTOR")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("CAST")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("STAGES")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("STRUCT")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("TYPE")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("PROTOTYPE_AUTO")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("DEFAULT")) {
                mGlobal.guardar(ASTC);
            } else if (ASTC.mesmoTipo("MARK")) {
                mGlobal.guardar(ASTC);
            }

        }

    }

    public void alocadorGlobal(AST ASTCorrente) {

        for (AST ASTC : ASTCorrente.getASTS()) {



            if (ASTC.mesmoTipo("DEFINE")) {

                Run_Def mAST = new Run_Def(mRunTime, mGlobal);
                mAST.init(ASTC);


            } else if (ASTC.mesmoTipo("MOCKIZ")) {


                Run_Moc mAST = new Run_Moc(mRunTime, mGlobal);
                mAST.init(ASTC);


            }

            if (mRunTime.temErros()) {
                return;
            }

        }


    }

    public void externalizarDireto(Escopo mGlobal, AST ASTC, AST ASTCGlobal) {

        String mExtended = ASTC.getBranch("EXTENDED").getNome();

        if (mExtended.contentEquals("STRUCT")) {

            Run_External mRE = mRunTime.getExternals().adicionar("", ASTC, ASTCGlobal);
            mGlobal.externalizar(mRE.getNomeCompleto());

        } else if (mExtended.contentEquals("EXTERNAL")) {

            Run_External mRE = mRunTime.getExternals().adicionar("", ASTC, ASTCGlobal);
            mGlobal.externalizar(mRE.getNomeCompleto());
        } else if (mExtended.contentEquals("STAGES")) {

            Run_External mRE = mRunTime.getExternals().adicionar("", ASTC, ASTCGlobal);
            mGlobal.externalizar(mRE.getNomeCompleto());
        }

    }

    public void externalizarPackage(AST ASTC, AST ASTCGlobal) {

        for (AST eAST : ASTC.getASTS()) {
            if (eAST.mesmoTipo("STRUCT")) {

                String mExtended = eAST.getBranch("EXTENDED").getNome();
                if (mExtended.contentEquals("STRUCT")) {
                    mRunTime.getExternals().adicionar(ASTC.getNome(), eAST, ASTCGlobal);
                } else if (mExtended.contentEquals("EXTERNAL")) {
                    mRunTime.getExternals().adicionar(ASTC.getNome(), eAST, ASTCGlobal);
                } else if (mExtended.contentEquals("STAGES")) {
                    mRunTime.getExternals().adicionar(ASTC.getNome(), eAST, ASTCGlobal);
                }

            }

        }

    }

    public void referenciar(AST ASTCGlobal) {

        for (AST ASTC : ASTCGlobal.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {
                mGlobal.adicionarRefer(ASTC.getNome());
            }
        }

    }

    public void externalizar(AST ASTCGlobal) {

        for (AST ASTC : ASTCGlobal.getASTS()) {
            if (ASTC.mesmoTipo("PACKAGE")) {

                externalizarPackage(ASTC, ASTCGlobal);

            } else if (ASTC.mesmoTipo("STRUCT")) {

                externalizarDireto(mGlobal, ASTC, ASTCGlobal);

            }

            if (mRunTime.temErros()) {
                return;
            }
        }


        for (Run_External RE : mRunTime.getExternals().getExterns()) {

            RE.run();

            if (mRunTime.temErros()) {
                return;
            }
        }


    }


}
