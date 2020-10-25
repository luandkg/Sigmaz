package Sigmaz.S07_Executor.RunValueTypes;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_Any;
import Sigmaz.S07_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_Operator {
    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Operator(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Operator";

    }

    public void init(Run_Value eRunValue,AST ASTCorrente, String eRetorno) {


        AST eModo = ASTCorrente.getBranch("MODE");

        //System.out.println("OPERATOR  -> " + eModo.getNome());

        Run_Value mRun_Esquerda = new Run_Value(mRunTime, mEscopo);

        if (ASTCorrente.getBranch("LEFT").mesmoValor("DEFAULT")) {
            mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), eRetorno);
        } else {
            mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), "<<ANY>>");
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Run_Value mRun_Direita = new Run_Value(mRunTime, mEscopo);
        if (ASTCorrente.getBranch("RIGHT").mesmoValor("DEFAULT")) {
            mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), eRetorno);
        } else {
            mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), "<<ANY>>");
        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        // System.out.println("OPERATOR  DIREITA -> " + mRun_Direita.getRetornoTipo());
        // System.out.println("OPERATOR  ESQUERDA -> " + mRun_Esquerda.getRetornoTipo());

        //System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

        if (eModo.mesmoNome("MATCH")) {

            realizarOperacao(eRunValue,"MATCH", mRun_Esquerda, mRun_Direita, eRetorno);

        } else if (eModo.mesmoNome("UNMATCH")) {

            realizarOperacao(eRunValue,"UNMATCH", mRun_Esquerda, mRun_Direita, eRetorno);

        } else if (eModo.mesmoNome("SUM")) {

            realizarOperacao(eRunValue,"SUM", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("SUB")) {

            realizarOperacao(eRunValue,"SUB", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("SUB")) {

            realizarOperacao(eRunValue,"SUB", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("MUX")) {

            realizarOperacao(eRunValue,"MUX", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("DIV")) {

            realizarOperacao(eRunValue,"DIV", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("GREAT")) {

            realizarOperacao(eRunValue,"GREAT", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("LESS")) {

            realizarOperacao(eRunValue,"LESS", mRun_Esquerda, mRun_Direita, eRetorno);
        } else if (eModo.mesmoNome("APPEND")) {

            realizarOperacao(eRunValue,"APPEND", mRun_Esquerda, mRun_Direita, eRetorno);

        } else {
            mRunTime.errar(mLocal, "Comparador Desconhecido : " + eModo.getNome());
        }


    }

    public void realizarOperacao(Run_Value eRunValue,String eOperacao, Run_Value mRun_Esquerda, Run_Value mRun_Direita, String eRetorno) {


        // System.out.println("Realizando Operacao : " + eOperacao + " :: " + eRetorno);

        //  System.out.println("OPERATOR " + eOperacao + " DIREITA -> " + mRun_Direita.getRetornoTipo());
        // System.out.println("OPERATOR " + eOperacao + " ESQUERDA -> " + mRun_Esquerda.getRetornoTipo());


        Run_Any mRun_Matchable = new Run_Any(mRunTime);
        Item mItem = mRun_Matchable.init_Operation(eOperacao, mRun_Esquerda, mRun_Direita, mEscopo,eRetorno);


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        eRunValue.setNulo(mItem.getNulo());
        eRunValue.setPrimitivo(mItem.getPrimitivo());
        eRunValue.setConteudo(mItem.getValor(mRunTime, mEscopo));
        eRunValue.setRetornoTipo(mItem.getTipo());

        //System.out.println("Realizado Operacao : " + eOperacao + " :: " + mRetornoTipo);


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (eRunValue.getRetornoTipo().contentEquals("bool")) {
            eRunValue.setPrimitivo(true);
        } else if (eRunValue.getRetornoTipo().contentEquals("int")) {
            eRunValue.setPrimitivo(true);
        } else if (eRunValue.getRetornoTipo().contentEquals("num")) {
            eRunValue.setPrimitivo(true);
        } else if (eRunValue.getRetornoTipo().contentEquals("string")) {
            eRunValue.setPrimitivo(true);
        }

    }


}
