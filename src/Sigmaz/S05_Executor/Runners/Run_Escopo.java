package Sigmaz.S05_Executor.Runners;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Indexador.Index_Action;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

public class Run_Escopo {

    public void executar_SemRetorno(RunTime mRunTime, Escopo mStructEscopo, Index_Action mFunction, ArrayList<Item> mArgumentos) {

        Run_Arguments mRun_Arguments = new Run_Arguments();

        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());

        mRun_Arguments.passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }
        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

    }

    public Item executar_ComRetorno(RunTime mRunTime, Escopo mStructEscopo, Index_Function mFunction, ArrayList<Item> mArgumentos, String eReturne) {


        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());


     //   System.out.println("\t\t EXECUTANDO :: " + mFunction.getDefinicao());

        Run_Arguments mRun_Arguments = new Run_Arguments();

        mRun_Arguments.passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.esperaRetornar(eReturne);
        mAST.init(mASTBody);

        Item eRetornoItem = mAST.getRetorno();

        if (mRunTime.getErros().size() > 0) {
            return eRetornoItem;
        }

        if (mFunction.getTipo().contentEquals(mAST.getRetorno().getTipo())) {
        } else {
            mRunTime.errar("Run_Arguments", "A Funcao " + mFunction.getDefinicao() + " esperava retornar " + mFunction.getTipo() + " mas retornou " + mAST.getRetorno().getTipo());
        }



        if (eRetornoItem.getNulo()) {
      //      System.out.println("\t\t  - Retornando -->> " + eRetornoItem.getNome() + " : " + eRetornoItem.getTipo() + " -- NULO");
        } else {
       //     System.out.println("\t\t  - Retornando -->> " + eRetornoItem.getNome() + " : " + eRetornoItem.getTipo() + " = " + eRetornoItem.getValor(mRunTime, mEscopoInterno));
        }



      //  System.out.println("\t\t  - Voltando Tipo : " + mAST.getRetorno().getTipo());
      //  System.out.println("\t\t  - Esperava Tipo : " + mFunction.getTipo());
    //    System.out.println("\t\t  - Anterior Esperava Tipo : " + eReturne);


        return eRetornoItem;
    }

}
