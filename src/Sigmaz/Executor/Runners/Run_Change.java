package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

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


        Item eItem = mEscopo.getItem(eAST.getNome());
        if (!eItem.getNulo()) {

            Run_Type mRun_GetType = mRunTime.getRun_Type(eItem.getValor(mRunTime,mEscopo));

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





    }


}
