package Sigmaz.S08_Executor.RunValueTypes;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Runners.Run_Dot;
import Sigmaz.S08_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Dot {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Dot(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Dot";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno){

        Run_Dot mRun_Dot = new Run_Dot(mRunTime);
        Item mRetorno = mRun_Dot.operadorPonto(ASTCorrente, mEscopo, eRetorno);

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
