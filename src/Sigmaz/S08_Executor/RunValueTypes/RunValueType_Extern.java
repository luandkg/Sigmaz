package Sigmaz.S08_Executor.RunValueTypes;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Runners.Run_Arrow;
import Sigmaz.S08_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Extern {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Extern(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Extern";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {


        Run_Arrow mRun_Arrow = new Run_Arrow(mRunTime);
        Item mRetorno = mRun_Arrow.operadorSeta(ASTCorrente, mEscopo, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        eRunValue.setNulo(mRetorno.getNulo());
        eRunValue.setPrimitivo(mRetorno.getPrimitivo());
        eRunValue.setConteudo(mRetorno.getValor(mRunTime, mEscopo));
        eRunValue.setRetornoTipo(mRetorno.getTipo());

        eRunValue.setIsReferenciavel(true);
        eRunValue.setReferencia(mRetorno);


    }

}
