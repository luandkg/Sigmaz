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


      //  String mTipagem = getTipagem(eAST.getBranch("TYPE"));

        Run_GetType mRun_GetType = new Run_GetType(mRunTime,mEscopo);

        String mTipagem = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));


        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, mTipagem);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getIsNulo()) {
            mEscopo.criarConstanteNula(eAST.getNome(), mAST.getRetornoTipo());
        } else if (mAST.getIsPrimitivo()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarConstante(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());
            } else {

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);

                String res = mCast.realizarCast(mTipagem, mTipagem, mAST.getConteudo());

                if (res == null) {
                    mEscopo.criarConstanteNula(eAST.getNome(), mTipagem);
                } else {
                    mEscopo.criarConstante(eAST.getNome(), mTipagem, res);
                }

            }
        } else if (mAST.getIsStruct()) {


            mEscopo.criarConstanteStruct(eAST.getNome(), mTipagem, mAST.getConteudo());

        } else {

            mRunTime.getErros().add("Retorno incompativel  : " + mAST.getRetornoTipo());

        }



    }

}
