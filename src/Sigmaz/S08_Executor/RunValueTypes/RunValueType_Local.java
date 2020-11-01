package Sigmaz.S08_Executor.RunValueTypes;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Runners.Run_ExecuteLocal;
import Sigmaz.S08_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Local {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Local(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Local";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        Run_ExecuteLocal mRun_ExecuteLocal = new Run_ExecuteLocal(mRunTime, mEscopo);
        Item mItem = mRun_ExecuteLocal.initComRetorno(ASTCorrente);

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        eRunValue.setNulo(mItem.getNulo());
        eRunValue.setRetornoTipo(mItem.getTipo());
        eRunValue.setConteudo(mItem.getValor(mRunTime, mEscopo));
        eRunValue.setPrimitivo(mItem.getPrimitivo());
        eRunValue.setEstrutura(mItem.getIsEstrutura());

        eRunValue.setIsReferenciavel(true);
        eRunValue.setReferencia(mItem);


    }
}
