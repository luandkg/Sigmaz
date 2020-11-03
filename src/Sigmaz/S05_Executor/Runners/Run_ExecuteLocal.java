package Sigmaz.S05_Executor.Runners;

import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Indexador.Index_Action;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

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

        boolean enc = false;
        boolean executada = false;

        Run_Arguments mPreparadorDeArgumentos = new Run_Arguments();

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));
        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);


        for (AST eLocal : mEscopo.getLocais()) {

            if (eLocal.mesmoValor("FUNCTION")) {

                enc = true;




                Index_Function mIndex_Function = new Index_Function(mRunTime, mEscopo, eLocal);
                mIndex_Function.setNome("LOCAL");

                mIndex_Function.resolverTipagem(mEscopo.getRefers());
                Run_Arguments mRunArguments = new Run_Arguments();

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);
                    if (contagem == mArgumentos.size()) {
                        Run_Escopo mRun_Escopo = new Run_Escopo();

                        executada = true;
                        return mRun_Escopo.executar_ComRetorno(mRunTime, mEscopo, mIndex_Function, mArgumentos, "<<ANY>>");

                    }

                } else {
                    mRunTime.errar(mLocal, "Argumentos incompativeis com funcao local !");
                }


            }

        }

        if (!enc) {

            mRunTime.errar(mLocal, "Nao existe funcao local definida !");


        } else {

            if (!executada) {
                mRunTime.errar(mLocal, "Nao existe funcao local com argumentos compativeis : " + mTipagem);
            }

        }


        return null;

    }

    public void initSemRetorno(AST ASTCorrente) {


        boolean enc = false;
        boolean executada = false;

        Run_Arguments mPreparadorDeArgumentos = new Run_Arguments();

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));
        if (mRunTime.getErros().size() > 0) {
            return;
        }
        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);


        for (AST eLocal : mEscopo.getLocais()) {

            enc = true;



            Index_Action mIndex_Function = new Index_Action(mRunTime, mEscopo, eLocal);
            mIndex_Function.setNome("LOCAL");

            mIndex_Function.resolverTipagem(mEscopo.getRefers());
            Run_Arguments mRunArguments = new Run_Arguments();

            if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {

                int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);
                if (contagem == mArgumentos.size()) {

                    executada = true;
                    Run_Escopo mRun_Escopo = new Run_Escopo();

                    mRun_Escopo.executar_SemRetorno(mRunTime, mEscopo, mIndex_Function, mArgumentos);
                    break;

                }

            } else {
                mRunTime.errar(mLocal, "Argumentos incompativeis com funcao local !");
            }

        }

        if (!enc) {

            mRunTime.errar(mLocal, "Nao existe acao local definida !");

        }else{

            if (!executada) {
                mRunTime.errar(mLocal, "Nao existe acao local com argumentos compativeis : " + mTipagem);
            }

        }

        return;

    }

}
