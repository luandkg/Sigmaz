package Sigmaz.S05_Executor.RunValueTypes;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_This;
import Sigmaz.S05_Executor.Runners.Run_Value;
import Sigmaz.S08_Utilitarios.AST;

public class RunValueType_This {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_This(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> This";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno){

        Run_This mRun_This = new Run_This(mRunTime);
        Item mRetorno = mRun_This.operadorPonto(ASTCorrente.getBranch("THIS"), mEscopo, eRetorno);

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
