package Sigmaz.S05_Executor;

import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S05_Executor.Escopos.Run_External;
import Sigmaz.S05_Executor.Runners.*;

import Sigmaz.S08_Utilitarios.AST;

import java.util.ArrayList;

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


    private void runMontagem(AST ASTSigmaz_Call, AST ASTCGlobal) {

        if (mRunTime.isDebug()) {

            if (mRunTime.getASTDebug().get(0).mesmoValor(Orquestrantes.TRUE)) {

            } else {
                mRunTime.errar("RunTime", "O objeto nao e debugavel !");
                return;
            }

        }


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
            if (ASTC.mesmoTipo(Orquestrantes.DEBUG)) {

                Run_Debug mRun_Debug = new Run_Debug(mRunTime, mGlobal);
                mRun_Debug.init(ASTC);

            }
        }


    }


    public void runSigmaz(AST ASTSigmaz_Call, AST ASTCGlobal) {

        runMontagem(ASTSigmaz_Call, ASTCGlobal);

        if (mRunTime.temErros()) {
            return;
        }


        for (AST ASTC : ASTSigmaz_Call.getASTS()) {

            if (ASTC.mesmoTipo(Orquestrantes.CALL)) {

                Run_Call mRun_Call = new Run_Call(mRunTime, mGlobal);
                mRun_Call.init(ASTC);

            }


            if (mRunTime.temErros()) {
                return;
            }

        }

    }

    public void runTestes(AST ASTSigmaz_Call, AST ASTCGlobal) {


        runMontagem(ASTSigmaz_Call, ASTCGlobal);

        if (mRunTime.temErros()) {
            return;
        }

        int cSucesso = 0;
        int cFalhou = 0;

        ArrayList<String> mTestando = new ArrayList<String>();

        System.out.println("################### TESTES UNITARIOS #####################");
        System.out.println("");
        System.out.println("");


        for (AST ASTC : ASTSigmaz_Call.getASTS()) {

            String mValor = "SUCESSO";

            if (ASTC.mesmoTipo(Orquestrantes.TEST)) {

                boolean mSucesso = true;

                Escopo mEscopoInterno = new Escopo(mRunTime, mGlobal);
                mEscopoInterno.setNome(ASTC.getBranch(Orquestrantes.GROUP).getNome() + "::" + ASTC.getBranch(Orquestrantes.TEST).getNome());


                if (mRunTime.getErros().size() > 0) {
                    mSucesso = false;
                }

                if (mSucesso) {
                    Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
                    mAST.init(ASTC.getBranch(Orquestrantes.BODY));
                    if (mRunTime.getErros().size() > 0) {
                        mSucesso = false;
                    }
                }



                if (mSucesso) {
                    cSucesso += 1;
                } else {
                    cFalhou += 1;
                    mValor = "FALHOU";
                }


                mTestando.add("TEST -> " + ASTC.getBranch(Orquestrantes.GROUP).getNome() + "::" + ASTC.getBranch(Orquestrantes.TEST).getNome() + "  -->>  " + mValor);

                System.out.println("TESTE :: " + ASTC.getBranch(Orquestrantes.GROUP).getNome() + "::" + ASTC.getBranch(Orquestrantes.TEST).getNome() + " --->>> " + mValor );
                if (mRunTime.getErros().size()>0){
                    for(String eErro : mRunTime.getErros()){
                        System.out.println("\t " + eErro);
                    }
                }


            }



            mRunTime.getErros().clear();


        }

        System.out.println("################### TESTES UNITARIOS #####################");
        System.out.println("");
        System.out.println("");


        for (String eTestando : mTestando) {
            System.out.println("\t " + eTestando);
        }

        System.out.println("");
        System.out.println("\t - TESTES    = " + (cSucesso + cFalhou));
        System.out.println("\t - SUCESSO   = " + (cSucesso));
        System.out.println("\t - FALHOU    = " + (cFalhou));
        System.out.println("");

    }


    public void mapearPacotes(AST ASTCGlobal) {

        mRunTime.getGlobalPackages().clear();

        for (AST ASTC : ASTCGlobal.getASTS()) {

            if (ASTC.mesmoTipo(Orquestrantes.PACKAGE)) {

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

            } else if (ASTC.mesmoTipo(Orquestrantes.COMPLEX)) {
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
            if (eAST.mesmoTipo(Orquestrantes.COMPLEX)) {

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
            if (ASTC.mesmoTipo(Orquestrantes.REFER)) {
                mGlobal.adicionarRefer(ASTC.getNome());
            }
        }

    }

    public void externalizar(AST ASTCGlobal) {

        for (AST ASTC : ASTCGlobal.getASTS()) {
            if (ASTC.mesmoTipo(Orquestrantes.PACKAGE)) {

                externalizarPackage(ASTC, ASTCGlobal);

            } else if (ASTC.mesmoTipo(Orquestrantes.COMPLEX)) {

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
