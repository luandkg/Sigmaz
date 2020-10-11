package Sigmaz.S07_Executor.RunValueTypes;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_GetType;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Struct {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Struct(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Struct";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        //  System.out.println("Vamos Struct 1 - " + eRetorno);


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRunTime.getHeap().adicionarStruct(mRun_Struct);

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);

        for(AST eGen : ASTCorrente.getBranch("GENERIC").getASTS()){
            // System.out.println("Generico : " + mRun_GetType.getTipagemSimples(eGen.getNome()));
            eGen.setNome(mRun_GetType.getTipagemSimples(eGen.getNome()));
        }

        mRun_Struct.init(ASTCorrente, mEscopo);

        String eNome = mRun_Struct.getNome();

        //    System.out.println("Vamos Struct 2 - " + mRunTime.getHeap().size());

        eRunValue.setNulo(false);
        eRunValue.setPrimitivo(false);
        eRunValue.setRetornoTipo(mRun_Struct.getTipoCompleto());
        eRunValue.setEstrutura(true);
        eRunValue.setConteudo(eNome);

        //System.out.println("Retornando Struct :: " + mRetornoTipo);

    }


}
