package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.RunValueTypes.RunValueType_Col;

import java.util.ArrayList;

public class Run_StructColSet {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_StructColSet(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_StructColSet";


    }


    public void init(AST eAST) {


      //  System.out.println(eAST.getImpressao());


       // System.out.println("Valorando  -> COL_SET ");

        Run_Value mRV = new Run_Value(mRunTime, mEscopo);
        mRV.init(eAST.getBranch("VALUE"), "<<ANY>>");

        if (eAST.existeBranch("STRUCT_SET")) {

            Item mItem = mEscopo.getItem(eAST.getBranch("STRUCT_SET").getNome());




            if (mRunTime.getErros().size() > 0) {
                return;
            }

            Run_Context mRun_Context = new Run_Context(mRunTime);


            ArrayList<String> aRefers = mEscopo.getRefers();
            aRefers.addAll(mEscopo.getRefersOcultas());

            String eQualificador = mRun_Context.getQualificador(mItem.getTipo(), mEscopo);

            //  System.out.println("Qualificar " + mItem.getNome() + " : " + mItem.getTipo() + " -->> " + eQualificador);


            if (eQualificador.contentEquals("STRUCT")) {


                //     System.out.println("STRUCT :: " + mItem.getValor(mRunTime, mEscopo));

                Run_Struct rs = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

                rs.init_ColSet(eAST.getBranch("STRUCT_SET"), mEscopo, mRV);


            } else {

                mRunTime.errar(mLocal, "CAST ou TYPE nao possui operador COL  :  " + eQualificador + " :: " + mItem.getTipo());
            }


        } else   if (eAST.existeBranch("STRUCT_SET_THIS")) {


            Item mItem = mEscopo.getItem("this");



            if (mRunTime.getErros().size() > 0) {
                return;
            }

            Run_Context mRun_Context = new Run_Context(mRunTime);


            ArrayList<String> aRefers = mEscopo.getRefers();
            aRefers.addAll(mEscopo.getRefersOcultas());

            String eQualificador = mRun_Context.getQualificador(mItem.getTipo(), mEscopo);

            //  System.out.println("Qualificar " + mItem.getNome() + " : " + mItem.getTipo() + " -->> " + eQualificador);


            if (eQualificador.contentEquals("STRUCT")) {


                //     System.out.println("STRUCT :: " + mItem.getValor(mRunTime, mEscopo));

                Run_Struct rs = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

                rs.init_ColSet_This(eAST.getBranch("STRUCT_SET_THIS"), mEscopo, mRV);


            } else {

                mRunTime.errar(mLocal, "CAST ou TYPE nao possui operador COL  :  " + eQualificador + " :: " + mItem.getTipo());
            }



        }





    }

}