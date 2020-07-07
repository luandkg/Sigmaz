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

    public void runSigmaz(AST ASTCGlobal, Escopo Global) {


        ArrayList<String> mRefers = new ArrayList<String>();

        ArrayList<AST> mGlobalPackages =  new ArrayList<AST>();

        for (AST ASTC : ASTCGlobal.getASTS()) {
            if (ASTC.mesmoTipo("REFER")) {

                String Referencia = ASTC.getNome();
                mRefers.add(Referencia);

            }else if (ASTC.mesmoTipo("PACKAGE")) {
                mGlobalPackages.add(ASTC);
            }
        }

        for (String Referencia : mRefers) {

            Global.adicionarRefer(Referencia);

            if (existePacote(Referencia,mGlobalPackages)) {
                for (AST eAST : getPacote(Referencia,mGlobalPackages).getASTS()) {
                    if (eAST.mesmoTipo("STRUCT")) {

                        ASTCGlobal.getASTS().add(eAST);
                    }
                }
            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado !");
            }


        }

        mRunTime.indexar(ASTCGlobal, Global);


        ArrayList<Run_Extern> GlobalExtern = new ArrayList<Run_Extern>();
        for (AST mStruct : mRunTime.getGlobalStructs()) {

            Run_Extern mRE = new Run_Extern(mRunTime);
            mRE.init("", mStruct.getNome(), mStruct, ASTCGlobal);
            GlobalExtern.add(mRE);
            mRunTime.getExtern().add(mRE);

            Global.externalizarStructGeral(mRE.getNome());
        }

        for (Run_Extern mRE : GlobalExtern) {

         //   System.out.println(" EXTERN " +mRE.getNome() );

            mRE.externalizar(GlobalExtern);

            mRE.run();
        }

        for (AST mStruct : mRunTime.getGlobalStructs()) {

          //  System.out.println(mStruct.getNome() + " --> ");

            for (AST mStructBody : mStruct.getBranch("BODY").getASTS()) {
                if (mStructBody.mesmoTipo("OPERATOR") && mStructBody.getBranch("VISIBILITY").mesmoNome("EXTERN")) {

                    mRunTime.getGlobalOperations().add(mStructBody);
                    Global.guardar(mStructBody);

                }
            }
        }


        for (AST ASTC : ASTCGlobal.getASTS()) {

            if (   mRunTime.getErros().size() > 0) {
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

        for (AST ASTC : ASTCGlobal.getASTS()) {

            if (ASTC.mesmoTipo("CALL")) {


                if (ASTC.mesmoValor("REFER")) {
                    AST mSending = ASTC.getBranch("SENDING");
                    Run_Func mAST = new Run_Func(mRunTime, Global);
                    mAST.init_ActionFunction(mSending);
                } else {

                    Run_Body mAST = new Run_Body(mRunTime, Global);
                    mAST.init(ASTC.getBranch("BODY"));

                }


            }


            if (   mRunTime.getErros().size() > 0) {
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
