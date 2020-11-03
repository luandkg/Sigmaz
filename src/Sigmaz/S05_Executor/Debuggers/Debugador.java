package Sigmaz.S05_Executor.Debuggers;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_Context;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Debugador {

    private RunTime mRunTime;

    public Debugador(RunTime eRunTime) {
        mRunTime = eRunTime;
    }

    public static void listarOperadores(RunTime mRunTime, Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<Index_Function> mOperadores = mRun_Context.getOperatorsContexto(mEscopo);


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            System.out.println("\t - Guardado : " + mAST.getTipo() + " " + mAST.getNome());
        }

        for (Index_Function mIndex_Function : mOperadores) {


            System.out.println("\t - Operador : " + mIndex_Function.getDefinicao());

        }

    }


    public void debugOperadores(Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<Index_Function> mOperadores = mRun_Context.getOperatorsContexto(mEscopo);


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            System.out.println("\t - Guardado : " + mAST.getTipo() + " " + mAST.getNome());
        }

        for (Index_Function mIndex_Function : mOperadores) {


            System.out.println("\t - Operador : " + mIndex_Function.getDefinicao());

        }

    }

    public void debugRefers(Escopo mEscopo) {

        System.out.println(" ESCOPO : " + mEscopo.getNome());

        for (String mAST : mEscopo.getRefers()) {
            System.out.println("\t - REFER : " + mAST);
        }

    }

    public void debugCasts(Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mListagem = mRun_Context.getCastsContexto(mEscopo);


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            System.out.println("\t - Guardado : " + mAST.getTipo() + " " + mAST.getNome());
        }

        for (AST mAST : mListagem) {

            System.out.println("\t - Cast : " + mAST.getNome());

        }

    }

    public void debugTodosOsTipos(Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mListagem = mRun_Context.getDefinidos(mEscopo);

        for (AST mAST : mListagem) {

            System.out.println("\t - Definido : " + mAST.getNome());

        }

    }
}
