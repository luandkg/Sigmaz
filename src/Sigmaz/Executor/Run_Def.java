package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Def {

    private ArrayList<Item> mStacks;
    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Def(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public void init(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        //  System.out.println("Iniciando Def : " + eAST.getNome());

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, eAST.getValor());

        //    System.out.println("Finalizado Def : " + eAST.getNome());

        if (mAST.getIsNulo()) {
            mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
        } else if (mAST.getIsPrimitivo()) {

            if (eAST.getValor().contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarDefinicao(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());
            } else {
                mRunTime.getErros().add("Retorno incompativel : " + mAST.getRetornoTipo());
            }


        } else {
            System.out.println("Finalizado Def : " + eAST.getNome());
            mRunTime.getErros().add("AST_Value com problemas !");
        }


    }
}
