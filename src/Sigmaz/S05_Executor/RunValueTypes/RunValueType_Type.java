package Sigmaz.S05_Executor.RunValueTypes;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_Context;
import Sigmaz.S05_Executor.Escopos.Run_Type;
import Sigmaz.S05_Executor.Runners.Run_Value;
import Sigmaz.S08_Utilitarios.AST;

public class RunValueType_Type {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Type(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Type";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        Run_Context mRun_Context = new Run_Context(mRunTime);

        String eQualificador = mRun_Context.getQualificador(ASTCorrente.getNome(), mEscopo);

        if (eQualificador.contentEquals("STRUCT")) {
            mRunTime.errar(mLocal, "Era esperado um TYPE e nao STRUCT !");
            return;
        } else if (eQualificador.contentEquals("TYPE")) {

        }

        //  System.out.println(" START -->> " + ASTCorrente.getNome());

        Run_Type mRun_Type = new Run_Type(mRunTime);
        mRun_Type.init(ASTCorrente, mEscopo);


        String eNome = mRun_Type.getNome();

        mRunTime.getHeap().adicionarType(mRun_Type);

        eRunValue.setNulo(false);
        eRunValue.setPrimitivo(false);
        eRunValue.setRetornoTipo(mRun_Type.getTipoCompleto());
        eRunValue.setEstrutura(true);
        eRunValue.setConteudo(eNome);

    }


}
