package Sigmaz.S08_Executor.RunValueTypes;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Processor.Registrado;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Reg {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Reg(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> REG";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        Registrado mReg = mRunTime.getProcessador().getRegistrador(ASTCorrente.getNome());

        if (mReg.getTipo().contentEquals("Inteiro")) {

            eRunValue.setNulo(false);
            eRunValue.setRetornoTipo("int");
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
            eRunValue.setConteudo(mReg.getConteudo());
            eRunValue.setIsReferenciavel(false);

        } else if (mReg.getTipo().contentEquals("Real")) {

            eRunValue.setNulo(false);
            eRunValue.setRetornoTipo("num");
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
            eRunValue.setConteudo(mReg.getConteudo());
            eRunValue.setIsReferenciavel(false);

        } else if (mReg.getTipo().contentEquals("Logico")) {

            eRunValue.setNulo(false);
            eRunValue.setRetornoTipo("bool");
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
            eRunValue.setConteudo(mReg.getConteudo());
            eRunValue.setIsReferenciavel(false);

        } else if (mReg.getTipo().contentEquals("Texto")) {

            eRunValue.setNulo(false);
            eRunValue.setRetornoTipo("string");
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
            eRunValue.setConteudo(mReg.getConteudo());
            eRunValue.setIsReferenciavel(false);

        } else if (mReg.getTipo().contentEquals("Tipo")) {

            eRunValue.setNulo(false);
            eRunValue.setRetornoTipo("string");
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
            eRunValue.setConteudo(mReg.getConteudo());
            eRunValue.setIsReferenciavel(false);



        } else {

            mRunTime.errar(mLocal, "Problema com retorno do registrador : " + mReg.getTipo());

        }


    }

}
