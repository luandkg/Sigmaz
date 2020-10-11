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

      //  System.out.println("Def " + eNome + " : " + mTipagem);

       // mRunTime.debugTodosOsTipos(mEscopo);


        if (!mRun_GetType.estaEmPacotado(mTipagem)) {

            mTipagem=mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

           // System.out.println("Forcar Tipagem !");

        }


       // System.out.println("Def " + eNome + " : " + mTipagem);


        AST mValor = eAST.getBranch("VALUE");


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, mTipagem, mTipagem);


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")) {
            mAST.setRetornoTipo(mTipagem);
        }


        //   System.out.println("Def : " + eAST.getNome() + " : " + mAST.getRetornoTipo() + " -->> PRIMITIVO : " + mAST.getIsPrimitivo());


        if (mAST.getIsNulo()) {


            if (mAST.getIsPrimitivo()) {
                mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());
            } else if (mAST.getIsStruct()) {
                mEscopo.criarDefinicaoStructNula(eAST.getNome(), mAST.getRetornoTipo());
            }

        } else {

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
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, "<<ANY>>", "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")) {
            mRunTime.getErros().add("O retorno de uma variavel definida a partir de LET nao pode ser nula");
        }


        if (mAST.getIsNulo()) {


            if (mAST.getIsPrimitivo()) {

                mEscopo.criarDefinicaoNula(eAST.getNome(), mAST.getRetornoTipo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarDefinicaoStructNula(eAST.getNome(), mAST.getRetornoTipo());

            }

        } else {

            if (mAST.getIsPrimitivo()) {

                mEscopo.criarDefinicao(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarDefinicaoStruct(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            }

        }


    }


}


