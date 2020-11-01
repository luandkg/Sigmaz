package Sigmaz.S08_Executor.RunValueTypes;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Runners.Run_Any;
import Sigmaz.S08_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Director {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Director(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Director";

    }

    public void init(Run_Value eRunValue,AST ASTCorrente, String eRetorno) {


        AST eModo = ASTCorrente.getBranch("MODE");

        //System.out.println("OPERATOR  -> " + eModo.getNome());


        Run_Value mRun_Esquerda = new Run_Value(mRunTime, mEscopo);
        mRun_Esquerda.init(ASTCorrente.getBranch("VALUE"), "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (eModo.mesmoNome("INVERSE")) {

            realizarDirector(eRunValue,"INVERSE", mRun_Esquerda, eRetorno);


        } else {
            mRunTime.errar(mLocal, "Director Desconhecido : " + eModo.getNome());
        }


    }

    public void realizarDirector(Run_Value eRunValue,String eOperacao, Run_Value mRun_Esquerda, String eRetorno) {


        Run_Any mRun_Matchable = new Run_Any(mRunTime);
        Item mItem = mRun_Matchable.init_Director(eOperacao, mRun_Esquerda, mEscopo, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        eRunValue.setNulo(mItem.getNulo());
        eRunValue.setPrimitivo(mItem.getPrimitivo());
        eRunValue.setConteudo(mItem.getValor(mRunTime, mEscopo));
        eRunValue.setRetornoTipo(mItem.getTipo());


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (eRunValue.getRetornoTipo().contentEquals("bool")) {
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
        } else if (eRunValue.getRetornoTipo().contentEquals("int")) {
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
        } else if (eRunValue.getRetornoTipo().contentEquals("num")) {
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
        } else if (eRunValue.getRetornoTipo().contentEquals("string")) {
            eRunValue.setPrimitivo(true);
            eRunValue.setEstrutura(false);
        }

    }

}
