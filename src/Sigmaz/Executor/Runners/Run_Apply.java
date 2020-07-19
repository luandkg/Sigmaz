package Sigmaz.Executor.Runners;

import Sigmaz.Executor.*;
import Sigmaz.Utils.AST;

public class Run_Apply {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Apply(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

    }


    public void init(AST ASTCorrente) {

        AST mSettable = ASTCorrente.getBranch("SETTABLE");
        AST mValue = ASTCorrente.getBranch("VALUE");


        Run_Value mAplicador = new Run_Value(mRunTime, mEscopo);
        mAplicador.init(mSettable, "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("Settable -->> " + SettableValue.getRetornoTipo());

        //   System.out.println("Settable -->>" + SettableValue.getRetornoTipo() +  " ::: " +  SettableValue.getReferencia().getNome());

        //   System.out.println("Erros : " + mRunTime.getErros().size());


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value  mAST = mRun_Valoramento.init(mAplicador.getReferencia().getNome(), mValue, mAplicador.getRetornoTipo());


        if (mAplicador.getReferencia() == null) {
            mRunTime.getErros().add("Referencia nao encontrada !");
        } else {


            if (mAplicador.getReferencia().getModo() == 1) {
                mRunTime.getErros().add("A constante nao pode ser alterada : " + mAplicador.getReferencia().getNome());
                return;
            }


            mAplicador.getReferencia().setValor(mAST.getConteudo());
            mAplicador.getReferencia().setNulo(mAST.getIsNulo());



        }


    }




}

