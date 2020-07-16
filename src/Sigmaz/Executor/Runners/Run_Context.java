package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Context {

    private RunTime mRunTime;

    public Run_Context(RunTime eRunTime) {

        mRunTime = eRunTime;

    }

    public ArrayList<AST> getCastsContexto(ArrayList<String> mPacotes) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mRunTime.getGlobalCasts()) {
            ret.add(eAST);
        }

        for (String Referencia : mPacotes) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : mRunTime.getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo("CAST")) {
                        ret.add(eAST);

                    }
                }

            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado x1 !");
            }

        }


        return ret;
    }


    public ArrayList<AST> getStructsContexto(ArrayList<String> mPacotes) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mRunTime.getGlobalStructs()) {
            ret.add(eAST);
        }

        for (String Referencia : mPacotes) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : mRunTime.getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo("STRUCT")) {
                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());
                        ret.add(eAST);

                    }
                }

            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado x2 !");
            }

        }


        return ret;
    }




    public ArrayList<AST> getOperatorsContexto(ArrayList<String> mPacotes) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mRunTime.getGlobalOperations()) {
            if (eAST.mesmoTipo("OPERATOR")) {
                ret.add(eAST);
            }
        }



        for (AST mStruct : mRunTime.getGlobalStructs()) {
            for (AST mStructBody : mStruct.getBranch("BODY").getASTS()) {
                if (mStructBody.mesmoTipo("OPERATOR")) {

                    ret.add(mStructBody);

                }
            }
        }

        for (String Referencia : mPacotes) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : mRunTime.getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo("STRUCT")) {


                        for (AST mStructBody : eAST.getBranch("BODY").getASTS()) {
                            if (mStructBody.mesmoTipo("OPERATOR")) {

                                ret.add(mStructBody);


                            }
                        }

                    }
                }

            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return ret;
    }

    public ArrayList<Run_Extern> getRunExternContexto(ArrayList<String> mPacotes) {
        ArrayList<Run_Extern> ret = new ArrayList<Run_Extern>();

        for (Run_Extern eAST : mRunTime.getExtern()) {
            if (eAST.getPacote().contentEquals("")) {
                ret.add(eAST);
            }
        }

        for (String Referencia : mPacotes) {

            if (mRunTime.existePacote(Referencia)) {

                for (Run_Extern rAST : mRunTime.getExtern()) {
                    if (rAST.getPacote().contentEquals(Referencia)) {
                        ret.add(rAST);
                    }
                }
            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return ret;
    }

    public ArrayList<AST> getTypesContexto(ArrayList<String> mPacotes) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mRunTime.getGlobalTypes()) {
            ret.add(eAST);
        }


        for (String Referencia : mPacotes) {

            if (mRunTime.existePacote(Referencia)) {

                for (AST eAST : mRunTime.getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo("TYPE")) {
                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());
                        ret.add(eAST);

                    }
                }

            } else {
                mRunTime.getErros().add("PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }


        return ret;
    }

    public boolean existeStage(String eStage,ArrayList<String> mRefers) {
        boolean enc = false;

        //  System.out.println(this.getNome() + " -> Stages : " + mRunTime.getStructsContexto(this.getRefers()).size());
        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST mAST : mRun_Context.getStructsContexto(mRefers)) {
            //  System.out.println(" -->> " + mAST.getNome());
            if (mAST.mesmoNome(eStage)) {
                enc = true;
                break;
            }

        }

        return enc;
    }

    public boolean existeStage_Type(String eStage,ArrayList<String> mRefers) {
        boolean enc = false;

        //  System.out.println(this.getNome() + " -> Stages : " + mRunTime.getStructsContexto(this.getRefers()).size());
        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST mAST : mRun_Context.getStructsContexto(mRefers)) {
            //  System.out.println(" -->> " + mAST.getNome());

            for (AST sAST : mAST.getBranch("STAGES").getASTS()) {
                //  System.out.println("\t :: " + sAST.getNome());

                if (sAST.mesmoTipo("STAGE")) {
                    String tmp = mAST.getNome() + "::" + sAST.getNome();
                    if (tmp.contentEquals(eStage)) {
                        enc = true;
                        break;
                    }
                }


            }
        }

        return enc;
    }

    public Item obterStage(String eStage,ArrayList<String> mRefers) {
        Item retStage = new Item("ret");

        retStage.setNulo(true);

        //  System.out.println(this.getNome() + " -> Stages : " + mRunTime.getStructsContexto(this.getRefers()).size());

        for (AST mAST : getStructsContexto(mRefers)) {
            // System.out.println(" -->> " + mAST.getNome());

            int i = 0;

            for (AST sAST : mAST.getBranch("STAGES").getASTS()) {
                //  System.out.println("\t :: " + sAST.getNome());

                if (sAST.mesmoTipo("STAGE")) {
                    String tmp = mAST.getNome() + "::" + sAST.getNome();
                    if (tmp.contentEquals(eStage)) {

                        retStage.setNulo(false);
                        retStage.setValor(String.valueOf(i));
                        retStage.setTipo(mAST.getValor());
                        break;
                    }
                }

                i += 1;
            }
        }

        return retStage;
    }


}
