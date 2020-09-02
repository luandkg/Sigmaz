package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Def {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Def(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Def";


    }


    public void init(AST eAST) {


        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());

        String mTipagem = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

        //   System.out.println("Tipagem : " + mTipagem);


        AST mValor = eAST.getBranch("VALUE");


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, mTipagem);


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (mAST.getIsNulo()) {


            if (mAST.getIsPrimitivo()) {

                mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarDefinicaoStructNula(eAST.getNome(), mAST.getRetornoTipo());

            }

        }else{

            if (mAST.getIsPrimitivo()) {

                mEscopo.criarDefinicao(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarDefinicaoStruct(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            }

        }



    }

    public void initLet(AST eAST) {


        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());



        AST mValor = eAST.getBranch("VALUE");


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")){
            mRunTime.getErros().add("O retorno de uma variavel definida a partir de LET nao pode ser nula");
        }




        if (mAST.getIsNulo()) {


            if (mAST.getIsPrimitivo()) {

                mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarDefinicaoStructNula(eAST.getNome(), mAST.getRetornoTipo());

            }

        }else{

            if (mAST.getIsPrimitivo()) {

                mEscopo.criarDefinicao(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarDefinicaoStruct(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            }

        }



    }


}


