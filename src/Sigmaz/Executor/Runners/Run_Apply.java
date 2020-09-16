package Sigmaz.Executor.Runners;

import Sigmaz.Executor.*;
import Sigmaz.Utils.AST;

public class Run_Apply {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Apply(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Apply";

    }


    public void init(AST ASTCorrente) {

        AST mValue = ASTCorrente.getBranch("VALUE");
        AST mSettable = ASTCorrente.getBranch("SETTABLE");



        Run_Value mAplicador = new Run_Value(mRunTime, mEscopo);
        mAplicador.init(mSettable, "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("Settable -->> " + SettableValue.getRetornoTipo());

        //   System.out.println("Settable -->>" + SettableValue.getRetornoTipo() +  " ::: " +  SettableValue.getReferencia().getNome());

        //   System.out.println("Erros : " + mRunTime.getErros().size());


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = null;

        if (mAplicador.getReferencia().getModo() == 2) {
            mAST = mRun_Valoramento.initSemCast(mAplicador.getReferencia().getNome(), mValue, mAplicador.getRetornoTipo(),"<<ANY>>");
        } else {
            mAST = mRun_Valoramento.init(mAplicador.getReferencia().getNome(), mValue, mAplicador.getRetornoTipo(),"<<ANY>>");
        }


        if (mAplicador.getReferencia() == null) {
            mRunTime.errar(mLocal, "Referencia nao encontrada !");
        } else {


            if (mAplicador.getReferencia().getModo() == 1) {
                mRunTime.errar(mLocal, "A constante nao pode ser alterada : " + mAplicador.getReferencia().getNome());
                return;
            }

            if (mAplicador.getReferencia().getModo() == 2) {
                mAplicador.getReferencia().setValor(mAST.getConteudo(),mRunTime,mEscopo);
                mAplicador.getReferencia().setNulo(mAST.getIsNulo());
                mAplicador.getReferencia().setIsEstrutura(mAST.getIsStruct());
                mAplicador.getReferencia().setTipo(mAST.getRetornoTipo());
            } else {


                mAplicador.getReferencia().setValor(mAST.getConteudo(),mRunTime,mEscopo);
                mAplicador.getReferencia().setNulo(mAST.getIsNulo());
            }


        }


    }

}

