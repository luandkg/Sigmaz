package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

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


        String eNome = eAST.getNome();


        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());

        String mTipagem = mRun_GetType.getTipagemAntes(eAST.getBranch("TYPE"));


        if (!mRun_GetType.estaEmPacotado(mTipagem)) {

            mTipagem = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

        }


     //   System.out.println(" -->> Def : " + eAST.getNome() + " : " + mTipagem);


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), eAST.getBranch("VALUE"), mTipagem);


        if (mRunTime.getErros().size() > 0) {
            return;
        }

      //  System.out.println("      Def Retorno : " + eAST.getNome() + " : " + mTipagem + " Retornou : " + mAST.getRetornoTipo());

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")) {
            mAST.setRetornoTipo(mTipagem);
        }


        if (mAST.getIsNulo()) {


            if (mAST.getIsPrimitivo()) {
                mEscopo.criarDefinicaoNula(eNome, mAST.getRetornoTipo());
            } else if (mAST.getIsStruct()) {
                mEscopo.criarDefinicaoStructNula(eNome, mAST.getRetornoTipo());
            }

        } else {

            if (mAST.getIsPrimitivo()) {
                mEscopo.criarDefinicao(eNome, mAST.getRetornoTipo(), mAST.getConteudo());
            } else if (mAST.getIsStruct()) {
                mEscopo.criarDefinicaoStruct(eNome, mAST.getRetornoTipo(), mAST.getConteudo());

                mRunTime.getHeap().aumentar(mAST.getConteudo());

            }

        }


    }



}


