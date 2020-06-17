package Sigmaz.Executor;

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

    public void init(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, eAST.getValor());

        if (mAST.getIsNulo()) {
            mEscopo.criarConstanteNula(eAST.getNome(), mAST.getRetornoTipo());
        } else if (mAST.getIsPrimitivo()) {

            if (eAST.getValor().contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarConstante(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());
            } else {
                mRunTime.getErros().add("Retorno incompativel : " + mAST.getRetornoTipo());
            }

        } else {
            mRunTime.getErros().add("AST_Value com problemas !");
        }

    }
}
