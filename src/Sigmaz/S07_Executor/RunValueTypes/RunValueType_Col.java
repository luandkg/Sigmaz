package Sigmaz.S07_Executor.RunValueTypes;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_Any;
import Sigmaz.S07_Executor.Runners.Run_Dot;
import Sigmaz.S07_Executor.Runners.Run_This;
import Sigmaz.S07_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Col {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Col(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Col";

    }


    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

       // System.out.println("Valorando  -> COL " + ASTCorrente.getNome());
       // System.out.println( ASTCorrente.getImpressao());

        if (ASTCorrente.existeBranch("THIS")){

            //System.out.println("Valorando  -> COL THIS " + ASTCorrente.getNome());

            Run_This mRun_This = new Run_This(mRunTime);
            Item mRetorno = mRun_This.operadorCol(ASTCorrente.getNome(),ASTCorrente, mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            eRunValue.setNulo(mRetorno.getNulo());
            eRunValue.setPrimitivo(mRetorno.getPrimitivo());
            eRunValue.setConteudo(mRetorno.getValor(mRunTime, mEscopo));
            eRunValue.setRetornoTipo(mRetorno.getTipo());

            eRunValue.setIsReferenciavel(true);
            eRunValue.setReferencia(mRetorno);

        }else{

            Run_Dot mRun_Dot = new Run_Dot(mRunTime);
            Item mRetorno = mRun_Dot.operadorCol(ASTCorrente.getNome(),ASTCorrente, mEscopo, eRetorno);

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




}
