package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Moc{

    private ArrayList<Item> mStacks;
    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Moc(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public String getTipagem(AST eAST){

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")){

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" +getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public void init(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);

        String mTipagem = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, mTipagem);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("Definindo " + eAST.getNome() + " : " +mAST.getRetornoTipo() + " = " +mAST.getConteudo() + " -> " + mAST.getIsNulo() );

        if (mAST.getIsNulo()) {


            if (mAST.getIsPrimitivo()) {
                mEscopo.criarConstanteNula(eAST.getNome(), mAST.getRetornoTipo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarConstanteStructNula(eAST.getNome(), mAST.getRetornoTipo());

            }

        }else{

            if (mAST.getIsPrimitivo()) {
                mEscopo.criarConstante(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarConstanteStruct(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            }

        }



    }

}
