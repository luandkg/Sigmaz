package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Change {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Change(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Change";

    }


    public void init(AST eAST) {

        //System.out.println("Oi Change :: " + eAST.ImprimirArvoreDeInstrucoes());


       // Item eItem = mEscopo.getItem(eAST.getNome());


        Run_Value eRV = new Run_Value(mRunTime,mEscopo);
        eRV.init(eAST.getBranch("TYPE"),"<<ANY>>");


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        ArrayList<String> aRefers = mEscopo.getRefers();
        aRefers.addAll(mEscopo.getRefersOcultas());

        String eQualificador = mRunTime.getQualificador(eRV.getRetornoTipo(), aRefers);

        if (eQualificador.contentEquals("TYPE")) {

            if (!eRV.getIsNulo()) {

                Run_Type mRun_GetType = mRunTime.getRun_Type(eRV.getConteudo());

                mRun_GetType.getEscopo().setAnterior(mEscopo);


                for (AST mDentro : eAST.getBranch("TYPED").getASTS()) {

                    if (!mRun_GetType.getCampos().contains(mDentro.getBranch("SETTABLE").getNome())) {
                        mRunTime.errar(mLocal,mRun_GetType.getTypeNome() + "." + mDentro.getBranch("SETTABLE").getNome() + " : Membro nao existe !");
                        return;
                    }

                    Run_Apply mRUA = new Run_Apply(mRunTime, mRun_GetType.getEscopo());
                    mRUA.init(mDentro);

                }

            }else{
                mRunTime.errar(mLocal,"Type " + eAST.getNome() + " : Nao Encontrada !");
            }


        }else{
            mRunTime.errar(mLocal, "Nao e possivel realizar Change em : " + eRV.getRetornoTipo());
        }







    }


}
