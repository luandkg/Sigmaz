package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

public class Run_Setter {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Setter(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Setter";


    }


    public void init(AST eAST) {


        Run_Value mRV = new Run_Value(mRunTime, mEscopo);
        mRV.init(eAST.getBranch("VALUE"), "<<ANY>>");


        Run_Context mRun_Context = new Run_Context(mRunTime);
        ArrayList<String> aRefers = mEscopo.getRefers();
        aRefers.addAll(mEscopo.getRefersOcultas());


        if (eAST.existeBranch("STRUCT_SET")) {

            Item mItem = mEscopo.getItem(eAST.getBranch("STRUCT_SET").getNome());

            if (mRunTime.temErros()) {
                return;
            }


            if (mRun_Context.getQualificadorIsStruct(mItem.getTipo(), mEscopo)) {

                Run_Struct rs = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

                rs.init_Setter(eAST.getBranch("STRUCT_SET"), mEscopo, mRV);

            } else {
                mRunTime.errar(mLocal, "A variavel " + mItem.getNome() + " do tipo " + mItem.getTipo() + " nao possui operador SETTER ");
            }


        } else if (eAST.existeBranch("STRUCT_SET_THIS")) {

            Item mItem = mEscopo.getItem("this");

            if (mRunTime.temErros()) {
                return;
            }


            if (mRun_Context.getQualificadorIsStruct(mItem.getTipo(), mEscopo)) {

                Run_Struct rs = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

                rs.init_Setter_This(eAST.getBranch("STRUCT_SET_THIS"), mEscopo, mRV);

            } else {
                mRunTime.errar(mLocal, "A variavel " + mItem.getNome() + " do tipo " + mItem.getTipo() + " nao possui operador SETTER ");
            }


        }


    }

}