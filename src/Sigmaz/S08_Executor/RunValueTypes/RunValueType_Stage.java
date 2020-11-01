package Sigmaz.S08_Executor.RunValueTypes;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Runners.Run_Context;
import Sigmaz.S08_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Stage {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Stage(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Stage";

    }

    public void init(Run_Value eRun_Value, AST ASTCorrente, String eRetorno) {

        AST mFilho = ASTCorrente.getBranch("STAGED");

        Run_Context mRun_Context = new Run_Context(mRunTime);

        if (mRun_Context.existeStage(ASTCorrente.getNome(), mEscopo)) {

            if (mRun_Context.existeStage_Type(ASTCorrente.getNome() + "::" + mFilho.getNome(), mEscopo)) {
                Item eItem = mRun_Context.obterStage(ASTCorrente.getNome() + "::" + mFilho.getNome(), mEscopo);

                if (mRunTime.getErros().size() > 0) {
                    return;
                }


                eRun_Value.setNulo(eItem.getNulo());
                eRun_Value.setPrimitivo(eItem.getPrimitivo());
                eRun_Value.setConteudo(eItem.getValor(mRunTime, mEscopo));
                eRun_Value.setRetornoTipo(eItem.getTipo());

                // System.out.println("Stage -->> " + eItem.getTipo());
                // System.out.println("Primitivo -->> " + eItem.getPrimitivo());

            } else {

                mRunTime.errar(mLocal, "Stage Tipo Deconhecido : " + ASTCorrente.getNome() + "::" + mFilho.getNome());

            }


        } else {
            mRunTime.errar(mLocal, "Stage Deconhecido : " + ASTCorrente.getNome());
            return;
        }


    }

}
