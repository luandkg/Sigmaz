package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;
import Sigmaz.S08_Utilitarios.AST;

public class Run_Moc{

    private ArrayList<Item> mStacks;
    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Moc(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Moc";

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
                mRunTime.getHeap().aumentar(mAST.getConteudo());

            }

        }



    }

}
