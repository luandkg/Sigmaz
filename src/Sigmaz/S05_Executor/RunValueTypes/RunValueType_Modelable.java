package Sigmaz.S05_Executor.RunValueTypes;

import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_GetType;
import Sigmaz.S05_Executor.Escopos.Run_Struct;
import Sigmaz.S05_Executor.Runners.Run_Value;
import Sigmaz.S08_Utilitarios.AST;

public class RunValueType_Modelable {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Modelable(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Modelable";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        //  System.out.println("Vamos Struct 1 - " + eRetorno);

         // System.out.println(ASTCorrente.getImpressao());


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRunTime.getHeap().adicionarStruct(mRun_Struct);


        mRun_Struct.modelable(ASTCorrente, mEscopo);

        String eNome = mRun_Struct.getNome();

     //   System.out.println("Vamos Struct 2 - " + eNome);

        eRunValue.setNulo(false);
        eRunValue.setPrimitivo(false);
        eRunValue.setRetornoTipo(mRun_Struct.getTipoCompleto());
        eRunValue.setEstrutura(true);
        eRunValue.setConteudo(eNome);

        //System.out.println("Retornando Struct :: " + mRetornoTipo);

    }


}
