package Sigmaz.S06_Executor.RunValueTypes;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.Runners.Run_Any;
import Sigmaz.S06_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Funct {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Funct(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Funct";

    }


    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());

        Run_Any mAST = new Run_Any(mRunTime);
        Item eItem = mAST.init_Function(ASTCorrente, mEscopo, mEscopo, eRetorno, mEscopo.getFunctionsCompleto());

        // System.out.println("Ent ->> " + eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        eRunValue.setNulo(eItem.getNulo());
        eRunValue.setPrimitivo(eItem.getPrimitivo());
        eRunValue.setConteudo(eItem.getValor(mRunTime, mEscopo));
        eRunValue.setRetornoTipo(eItem.getTipo());


        //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + this.getConteudo() + " Primitivo : " + mIsPrimitivo + " Nulo : " + mIsNulo + " Tipo : " + mRetornoTipo);


    }


}
