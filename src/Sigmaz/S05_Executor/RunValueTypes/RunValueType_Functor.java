package Sigmaz.S05_Executor.RunValueTypes;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_ExecuteFunctor;
import Sigmaz.S05_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Functor {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Functor(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Functor";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {




        Run_ExecuteFunctor mRun_ExecuteAuto = new Run_ExecuteFunctor(mRunTime, mEscopo);
        Item eItem = mRun_ExecuteAuto.init(ASTCorrente, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        eRunValue.setNulo(eItem.getNulo());
        eRunValue.setPrimitivo(eItem.getPrimitivo());
        eRunValue.setConteudo(eItem.getValor(mRunTime, mEscopo));
        eRunValue.setRetornoTipo(eItem.getTipo());

    }


}
