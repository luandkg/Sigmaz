package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;
import Sigmaz.S08_Utilitarios.AST;

public class Run_Voz{

    private ArrayList<Item> mStacks;
    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Voz(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Voz";

    }



    public void initVoz(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(eAST.getNome(), mValor, "<<ANY>>");

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
                mRunTime.getHeap().aumentar(mAST.getConteudo());

            }

        }



    }

}
