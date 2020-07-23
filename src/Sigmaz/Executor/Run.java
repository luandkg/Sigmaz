package Sigmaz.Executor;

import Sigmaz.Executor.Runners.*;
import Sigmaz.Utils.AST;

import java.io.File;
import java.util.ArrayList;

public class Run {

    private RunTime mRunTime;

    public Run(RunTime eRunTime) {
        mRunTime = eRunTime;
    }

    public void runSigmaz(AST ASTSigmaz_Call, AST ASTCGlobal, Escopo Global) {


        ArrayList<String> mRefers = new ArrayList<String>();

        ArrayList<AST> mGlobalPackages = new ArrayList<AST>();


        for (AST ASTC : ASTCGlobal.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {

                String Referencia = ASTC.getNome();
                mRefers.add(Referencia);

            } else if (ASTC.mesmoTipo("PACKAGE")) {
                mGlobalPackages.add(ASTC);

                for (AST eAST : ASTC.getASTS()) {
                    if (eAST.mesmoTipo("STRUCT")) {
                        // ASTCGlobal.getASTS().add(eAST);

                        Run_Extern mRE = new Run_Extern(mRunTime);
                        mRE.init(ASTC.getNome(), eAST.getNome(), eAST, ASTCGlobal);

                        //    System.out.println(" EXTERN " + mRE.getNomeCompleto());

                        mRunTime.getExtern().add(mRE);
                    }

                }


            } else if (ASTC.mesmoTipo("STRUCT")) {


                Run_Extern mRE = new Run_Extern(mRunTime);
                mRE.init("", ASTC.getNome(), ASTC, ASTCGlobal);
                mRunTime.getExtern().add(mRE);

                //  System.out.println(" EXTERN " + mRE.getNomeCompleto());

                Global.externalizar(mRE.getNomeCompleto());


            }
        }

        for (String Referencia : mRefers) {

       //     System.out.println("Procurando Refer : " + Referencia);

            Global.adicionarRefer(Referencia);

            if (existePacote(Referencia, mGlobalPackages)) {
                for (AST eAST : getPacote(Referencia, mGlobalPackages).getASTS()) {

                    if (eAST.mesmoTipo("STRUCT")) {

                        Global.externalizar(Referencia + "<>" + eAST.getNome());

                    }


                }
            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado !");
            }


        }

        mRunTime.indexar(ASTCGlobal, Global);



        for (Run_Extern RE : mRunTime.getExtern()) {
            //  System.out.println("RE -->> " + RE.getNomeCompleto());
            RE.run();
        }


        for (
                AST ASTC : ASTCGlobal.getASTS()) {

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (ASTC.mesmoTipo("DEFINE")) {

                Run_Def mAST = new Run_Def(mRunTime, Global);
                mAST.init(ASTC);


            } else if (ASTC.mesmoTipo("MOCKIZ")) {


                Run_Moc mAST = new Run_Moc(mRunTime, Global);
                mAST.init(ASTC);

            } else if (ASTC.mesmoTipo("INVOKE")) {

                Run_Invoke mAST = new Run_Invoke(mRunTime, Global);
                mAST.init(ASTC);

            }

        }

        for (
                AST ASTC : ASTSigmaz_Call.getASTS()) {

            if (ASTC.mesmoTipo("CALL")) {

                Global.setNome(ASTC.getNome());

                if (ASTC.mesmoValor("REFER")) {
                    AST mSending = ASTC.getBranch("SENDING");
                    Run_Any mAST = new Run_Any(mRunTime);
                    mAST.init_ActionFunction(mSending,Global);
                } else {

                    Run_Body mAST = new Run_Body(mRunTime, Global);
                    mAST.init(ASTC.getBranch("BODY"));

                }


            }


            if (mRunTime.getErros().size() > 0) {
                break;
            }

        }

    }


    public boolean existePacote(String eNome, ArrayList<AST> mGlobalPackages) {
        boolean ret = false;

        for (AST ASTC : mGlobalPackages) {
            if (ASTC.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public AST getPacote(String eNome, ArrayList<AST> mGlobalPackages) {
        AST ret = null;

        for (AST ASTC : mGlobalPackages) {
            if (ASTC.mesmoNome(eNome)) {
                ret = ASTC;
                break;
            }
        }
        return ret;
    }


}
