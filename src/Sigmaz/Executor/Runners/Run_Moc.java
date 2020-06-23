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

    public void init(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        String eTipado = eAST.getValor();

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, eAST.getValor());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getIsNulo()) {
            mEscopo.criarConstanteNula(eAST.getNome(), mAST.getRetornoTipo());
        } else if (mAST.getIsPrimitivo()) {

            if (eAST.getValor().contentEquals(mAST.getRetornoTipo())) {
                mEscopo.criarConstante(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());
            } else {

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                //  System.out.println("Retorno ESQ : " + eTipado);
                // System.out.println("Retorno DIR x : " + mAST.getRetornoTipo());

                if (mEscopo.existeCast(eTipado)) {


                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarGetterCast(eTipado, mAST.getRetornoTipo(), mAST.getConteudo());

                    if (res == null) {
                        mEscopo.criarConstanteNula(eAST.getNome(), mAST.getRetornoTipo());
                    } else {
                        mEscopo.criarConstante(eAST.getNome(), eAST.getValor(), res);
                    }

                } else if (mEscopo.existeCast(mAST.getRetornoTipo())) {

                    Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);
                    String res = mCast.realizarSetterCast(mAST.getRetornoTipo(), eTipado, mAST.getConteudo());

                    if (res == null) {
                        mEscopo.criarConstanteNula(eAST.getNome(), mAST.getRetornoTipo());
                    } else {
                        mEscopo.criarConstante(eAST.getNome(), eAST.getValor(), res);
                    }

                } else {

                    mRunTime.getErros().add("Retorno incompativel  : " + mAST.getRetornoTipo());


                }

            }
        } else if (mAST.getIsStruct()) {


            mEscopo.criarConstanteStruct(eAST.getNome(), eAST.getValor(), mAST.getConteudo());

        } else {

            mRunTime.getErros().add("Retorno incompativel  : " + mAST.getRetornoTipo());

        }



    }

}
