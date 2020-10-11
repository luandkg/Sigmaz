package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;
import Sigmaz.S00_Utilitarios.AST;

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
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, mTipagem,mTipagem);

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

    public void initVoz(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, "<<ANY>>","<<ANY>>");

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")){
            mRunTime.getErros().add("O retorno de uma constante definida a partir de VOZ nao pode ser nula");
        }


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
