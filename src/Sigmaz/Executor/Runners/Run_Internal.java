package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Internal {

    private RunTime mRunTime;

    public Run_Internal(RunTime eRunTime) {
        mRunTime = eRunTime;
    }


    public Item Struct_DentroStruct(String eLocal, AST eInternal, Escopo mEscopo, String eRetorno) {

        Run_Struct mEstruturador = mRunTime.getRun_Struct(eLocal);

        // System.out.println(" - STRUCT : " + mEstruturador.getNome());

        //  System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Item eItem = null;

        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome());


            eItem = mEstruturador.init_Object(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome() + " = " + eItem.getValor());


            if (eInternal.existeBranch("INTERNAL")) {
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);
            }

        } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {

            //    System.out.println("\t - ESTRUTURA STRUCT_FUNCT : " + eInternal.getNome());


            eItem = mEstruturador.init_Function(eInternal, mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            if (eInternal.existeBranch("INTERNAL")) {
                //  System.out.println("PONTEIRO :: " + eItem.getValor() + " Dentro de Function -> " + eInternal.getBranch("INTERNAL").getNome());
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);

            }


        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }

        return eItem;
    }

    public Item Struct_DentroDiretoStruct(String eLocal, AST eInternal, Escopo mEscopo, String eRetorno) {

        Run_Struct mEstruturador = mRunTime.getRun_Struct(eLocal);

        // System.out.println(" - STRUCT : " + mEstruturador.getNome());

        //  System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Item eItem = null;

        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome());


            eItem = mEstruturador.init_ObjectDireto(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome() + " = " + eItem.getValor());


            if (eInternal.existeBranch("INTERNAL")) {
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);
            }

        } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {

            //    System.out.println("\t - ESTRUTURA STRUCT_FUNCT : " + eInternal.getNome());


            eItem = mEstruturador.init_FunctionDireto(eInternal, mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            if (eInternal.existeBranch("INTERNAL")) {
                //  System.out.println("PONTEIRO :: " + eItem.getValor() + " Dentro de Function -> " + eInternal.getBranch("INTERNAL").getNome());
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);

            }


        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }

        return eItem;
    }


    public Item Struct_DentroType(String eLocal, AST eInternal, Escopo mEscopo, String eRetorno) {

        Run_Type mEscopoType = mRunTime.getRun_Type(eLocal);

        //   System.out.println(" - TYPE : " + mEscopoType.getNome());

        //   System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Item eItem = null;

        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //    System.out.println("STRUCT_OBJECT TYPE : " + eInternal.getNome());


            eItem = mEscopoType.init_Object(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            //    System.out.println("STRUCT_OBJECT : " + eInternal.getNome() + " = " + eItem.getValor());

            if (eInternal.existeBranch("INTERNAL")) {
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoType(mEscopoType, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);
            }


        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }

        return eItem;
    }

}
