package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;

import java.util.ArrayList;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.AST;

public class Run_ExecuteLocal {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_ExecuteLocal(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_ExecuteLocal";


    }


    public Item initComRetorno(AST ASTCorrente) {


        if (mEscopo.temLocalAnteriormente() == true) {

            AST eAST = mEscopo.getLocalAnteriormente();

            Run_Arguments mPreparadorDeArgumentos = new Run_Arguments();

            ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));
            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            Index_Action mIndex_Function = new Index_Action(mRunTime, mEscopo, eAST);
            mIndex_Function.setNome("LOCAL");

            mIndex_Function.resolverTipagem(mEscopo.getRefers());
            Run_Arguments mRunArguments = new Run_Arguments();

            if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {

                int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);
                if (contagem == mArgumentos.size()) {

                    return mPreparadorDeArgumentos.executar_ActionComRetorno(mRunTime, mEscopo, mIndex_Function, mArgumentos, "<<ANY>>");


                }

            } else {
                mRunTime.errar(mLocal, "Argumentos incompativeis com funcao local !");
            }


        } else {
            mRunTime.errar(mLocal, "Nao existe funcao local definida !");
        }

        return null;

    }

    public void initSemRetorno(AST ASTCorrente) {


        if (mEscopo.temLocalAnteriormente() == true) {

            AST eAST = mEscopo.getLocalAnteriormente();

            Run_Arguments mPreparadorDeArgumentos = new Run_Arguments();

            ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));
            if (mRunTime.getErros().size() > 0) {
                return ;
            }

            Index_Action mIndex_Function = new Index_Action(mRunTime, mEscopo, eAST);
            mIndex_Function.setNome("LOCAL");

            mIndex_Function.resolverTipagem(mEscopo.getRefers());
            Run_Arguments mRunArguments = new Run_Arguments();

            if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {

                int contagem = mRunArguments.conferirArgumentos(mRunTime, mEscopo, mIndex_Function.getArgumentos(), mArgumentos);
                if (contagem == mArgumentos.size()) {

                     mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                }

            } else {
                mRunTime.errar(mLocal, "Argumentos incompativeis com funcao local !");
            }


        } else {
            mRunTime.errar(mLocal, "Nao existe funcao local definida !");
        }

        return ;

    }

}
