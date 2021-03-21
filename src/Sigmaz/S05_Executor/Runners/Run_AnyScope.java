package Sigmaz.S05_Executor.Runners;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S08_Utilitarios.Utilitario;
import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Indexador.Index_Action;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.Procuradores.ProcuradorRetornavel;
import Sigmaz.S05_Executor.Procuradores.ProcuradorSemRetorno;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

public class Run_AnyScope {

    private RunTime mRunTime;

    public Run_AnyScope(RunTime eRunTime) {

        mRunTime = eRunTime;


    }

    public Item executeComRetorno(String eExecutor, String eGrupo, String eNome, AST ASTCorrente, Escopo mEscopo, ArrayList<Index_Function> eListagem, ArrayList<Item> mArgumentos, String eRetorno) {

        //  System.out.println("\t\t  ------------------------------------------------");

        boolean realizada = false;
        Item mRet = new Item("");

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        Run_Searcher mRun_Searcher = new Run_Searcher(mRunTime, mEscopo);

        ProcuradorRetornavel mexecutarAST = mRun_Searcher.procurarRetornavelArgumentado(eListagem, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {

            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRet = mRun_Escopo.executar_ComRetorno(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos, eRetorno);
            realizada = true;


        }


        errar(eGrupo, eNome, ASTCorrente, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, eExecutor);

        //  System.out.println("\t\t  ------------------------------------------------");

        return mRet;

    }

    public Item executeComRetornoGet(String eExecutor, String eGrupo, String eNome, AST ASTCorrente, Escopo mEscopo, ArrayList<Index_Function> eListagem, ArrayList<Item> mArgumentos, String eRetorno) {

        boolean realizada = false;
        Item mRet = new Item("");

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        Run_Searcher mRun_Searcher = new Run_Searcher(mRunTime, mEscopo);

        ProcuradorRetornavel mexecutarAST = mRun_Searcher.procurarRetornavelArgumentadoGet(eListagem, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {

            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRet = mRun_Escopo.executar_ComRetorno(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos, eRetorno);
            realizada = true;


        }


        errar(eGrupo, eNome, ASTCorrente, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, eExecutor);

        return mRet;

    }


    public void executeSemRetorno(String eExecutor, String eGrupo, String eNome, AST ASTCorrente, Escopo mEscopo, ArrayList<Index_Action> eListagem, ArrayList<Item> mArgumentos) {

        boolean realizada = false;
        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        Run_Searcher mRun_Searcher = new Run_Searcher(mRunTime, mEscopo);

        ProcuradorSemRetorno mexecutarAST = mRun_Searcher.procurarSemRetornoArgumentado(eListagem, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_SemRetorno(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;


        }

        errar(eGrupo, eNome, ASTCorrente, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, eExecutor);


    }


    public void errar(String eGrupo, String eNome, AST ASTCorrente, String mTipagem, boolean enc, boolean algum, boolean realizada, String mLocal) {

        if (enc) {
            if (algum) {
                if (realizada) {
                } else {
                    mRunTime.errar(mLocal, eGrupo + " " + eNome + " : Argumentos incompativeis : " + mTipagem);

                    if (mRunTime.isDebug()) {
                        mRunTime.debug(ASTCorrente);
                    }
                }
            } else {
                mRunTime.errar(mLocal, eGrupo + "  " + eNome + " " + mTipagem + " : Quantidade de Argumentos incompativeis !");

                if (mRunTime.isDebug()) {
                    mRunTime.debug(ASTCorrente);
                }
            }
        } else {
            mRunTime.errar(mLocal, eGrupo + "  " + eNome + " " + mTipagem + " : Nao Encontrada !");

            if (mRunTime.isDebug()) {
                mRunTime.debug(ASTCorrente);
            }

        }


    }


}
