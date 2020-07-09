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


        Run_Value SettableValue = new Run_Value(mRunTime, mEscopo);
        SettableValue.init(mSettable, "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

      //  System.out.println("Settable -->> " + SettableValue.getRetornoTipo());

     //   System.out.println("Settable -->>" + SettableValue.getRetornoTipo() +  " ::: " +  SettableValue.getReferencia().getNome());

     //   System.out.println("Erros : " + mRunTime.getErros().size());



        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValue,"<<ANY>>");

        if ( SettableValue.getReferencia() == null) {
            mRunTime.getErros().add("Referencia nao encontrada !");
        } else {
            SettableValue.getReferencia().setValor(mAST.getConteudo());
            SettableValue.getReferencia().setNulo(mAST.getIsNulo());
        }


      //  System.out.println("Settable : "+ SettableValue.getReferencia().getNome() + " = " + mAST.getConteudo() + " ::: " + mAST.getRetornoTipo());



    }




}
