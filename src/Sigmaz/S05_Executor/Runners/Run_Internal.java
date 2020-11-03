package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Escopos.Run_Struct;
import Sigmaz.S05_Executor.Escopos.Run_Type;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Run_Internal {

    private RunTime mRunTime;
    private String mLocal;

    public Run_Internal(RunTime eRunTime) {
        mRunTime = eRunTime;
        mLocal = "Run_Internal";

    }


    public Item Struct_DentroStruct(String eLocal, AST eInternal, Escopo mEscopo, String eRetorno) {

        Item eItem = new Item("");

        Run_Struct mEstruturador = mRunTime.getHeap().getRun_Struct(eLocal);

        // System.out.println(" - STRUCT : " + mEstruturador.getNome());

        //  System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (mRunTime.getErros().size() > 0) {
            return eItem;
        }


        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome());

            if (mRunTime.getErros().size() > 0) {
                return eItem;
            }

            eItem = mEstruturador.init_Object(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return eItem;
            }

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome() + " = " + eItem.getValor());


            if (eInternal.existeBranch("INTERNAL")) {
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);
            }

        } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {

            //System.out.println("\t - ESTRUTURA STRUCT_FUNCT : " + eInternal.getNome());


            eItem = mEstruturador.init_Function(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return eItem;
            }

            if (eInternal.existeBranch("INTERNAL")) {
                //  System.out.println("PONTEIRO :: " + eItem.getValor() + " Dentro de Function -> " + eInternal.getBranch("INTERNAL").getNome());
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);
                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, "<<ANY>>");

            }
        } else if (eInternal.mesmoValor("GETTER")) {


            Run_Dot mrd = new Run_Dot(mRunTime);
            eItem = mrd.operadorColDireto(eLocal, eInternal, mEscopo, eRetorno);

            if (eInternal.existeBranch("INTERNAL")) {
                //  System.out.println("PONTEIRO :: " + eItem.getValor() + " Dentro de Function -> " + eInternal.getBranch("INTERNAL").getNome());
                Run_Dot mRun_Dot = new Run_Dot(mRunTime);

                eItem = mRun_Dot.operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);

            }

        } else {

            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !");
            mRunTime.errar(mLocal, eInternal.getValor());
        }

        return eItem;
    }

    public Item Struct_DentroDiretoStruct(String eLocal, AST eInternal, Escopo mEscopo, String eRetorno) {

        Run_Struct mEstruturador = mRunTime.getHeap().getRun_Struct(eLocal);

        // System.out.println(" - STRUCT : " + mEstruturador.getNome());

        //  System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Item eItem = null;

        if (eInternal == null) {
            mRunTime.errar(mLocal, "Internal Nulo !");
            return null;
        }

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

            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !");

        }

        return eItem;
    }

    private String limparGenericos(String eTipo) {
        String ret = "";

        int i = 0;
        int o = eTipo.length();
        while (i < o) {

            String c = String.valueOf(eTipo.charAt(i));
            if (c.contentEquals("<")) {
                int i2 = i + 1;
                if (i2 < o) {
                    if (String.valueOf(eTipo.charAt(i2)).contentEquals(">")) {
                        ret = "";
                        i += 1;
                    } else {
                        break;
                    }
                } else {
                    break;
                }

            } else {
                ret += c;
            }
            i += 1;
        }

        return ret;
    }

    public Item Struct_DentroType(String eTipoOrigem, String eLocal, AST eInternal, Escopo mEscopo, String eRetorno) {

        String aTipo = eTipoOrigem;
        eTipoOrigem = limparGenericos(eTipoOrigem);


        // System.out.println("Tipo Origem : " + aTipo + " -->> " + eTipoOrigem);

        // System.out.println("Escopo :: " + mEscopo.getNome());

        Run_Context eRun_Context = new Run_Context(mRunTime);

        ArrayList<String> eCampos = new ArrayList<String>();

        for (String eRefer : mEscopo.getRefers()) {
            //      System.out.println("Refer -->> " + eRefer);
//
        }
        for (AST eType : eRun_Context.getTypesContexto(mEscopo)) {
            //  System.out.println("Type -->> " + eType.getNome());
            if (eType.getNome().contentEquals(eTipoOrigem)) {

                for (AST eItem : eType.getBranch("BODY").getASTS()) {
                    if (eItem.mesmoTipo("DEFINE")) {
                        eCampos.add(eItem.getNome());
                    } else if (eItem.mesmoTipo("MOCKIZ")) {
                        eCampos.add(eItem.getNome());
                    }
                }

                //System.out.println("Type Origem : " + eTipoOrigem);

                for (String eCampo : eCampos) {

                    //     System.out.println("\t - " + eCampo);

                }
                break;
            }
        }


        Run_Type mEscopoType = mRunTime.getHeap().getRun_Type(eLocal);

        //   System.out.println(" - TYPE : " + mEscopoType.getNome());

        //   System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Item eItem = null;

        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //    System.out.println("STRUCT_OBJECT TYPE : " + eInternal.getNome());

            if (eCampos.contains(eInternal.getNome())) {

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


                mRunTime.errar(mLocal, "Type " + eTipoOrigem + "." + eInternal.getNome() + " : Campo nao existente");

            }


        } else {

            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !");

        }

        return eItem;
    }

}
