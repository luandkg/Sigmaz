package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Mut {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Mut(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Mut";


    }


    public void init(AST eAST) {


        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());


        AST mValor = eAST.getBranch("VALUE");


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, "<<ANY>>","<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>") && mAST.getIsNulo()) {

            mRunTime.getErros().add(mLocal + " - A definicao de uma MUT com inferencia de tipo de tipo dinamico nao pode ter o valor de atribuicao NULO !");
            return;
        }

        if (mAST.getIsNulo()) {

            mEscopo.criarMutavelNula(eAST.getNome(), mAST.getRetornoTipo());

        } else {

            if (mAST.getIsPrimitivo()) {

                mEscopo.criarMutavelPrimitivo(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            } else if (mAST.getIsStruct()) {

                mEscopo.criarMutavelStruct(eAST.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            }

        }


    }


}


