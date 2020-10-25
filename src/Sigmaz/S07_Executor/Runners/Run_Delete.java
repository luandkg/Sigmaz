package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Delete {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Delete(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Delete";

    }


    public void init(AST eAST) {

      //  System.out.println(eAST.getImpressao());


        AST mValor = eAST.getBranch("VALUE");


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mRun_Value = mRun_Valoramento.init(eAST.getNome(), mValor, "<<ANY>>");


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (mRun_Value.getIsNulo()) {

            mRunTime.errar(mLocal, "Nao existe como remover algo nulo !");

        } else {

            Run_Context mRun_Context = new Run_Context(mRunTime);


            String eQualificador = mRun_Context.getQualificador(mRun_Value.getRetornoTipo(), mEscopo);


            if (eQualificador.contentEquals("STRUCT")) {

             //   System.out.println("Delete :: " + eQualificador + " -> " + mRun_Value.getConteudo());

                // Chamar DESTRUCT da STRUCT
                Run_Struct mRS = mRunTime.getHeap().getRun_Struct(mRun_Value.getConteudo());
                mRS.destruct();

                mRunTime.getHeap().removerStruct(mRun_Value.getConteudo());

                if (mRun_Value.getIsReferenciavel()){
                //    System.out.println("Delete Ref :: " + mRun_Value.getReferencia().getNome());
                    mRun_Value.getReferencia().setNulo(true);
                }


            } else if (eQualificador.contentEquals("TYPE")) {

              //  System.out.println("Delete :: " + eQualificador + " -> " + mRun_Value.getConteudo());

                mRunTime.getHeap().removerType(mRun_Value.getConteudo());

                if (mRun_Value.getIsReferenciavel()){
              //      System.out.println("Delete Ref :: " + mRun_Value.getReferencia().getNome());
                    mRun_Value.getReferencia().setNulo(true);
                }


            } else {

                if (mRun_Value.getIsReferenciavel()) {
                    mRun_Value.getReferencia().setNulo(true);
                }

                   // mRunTime.errar(mLocal, "DELETE :  " + eQualificador + " :: " + mRun_Value.getRetornoTipo());
            }


        }


    }

}


