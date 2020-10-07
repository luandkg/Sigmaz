package Sigmaz.S06_Executor.Debuggers;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Indexador.Index_Function;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.Runners.Run_Context;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Debugador {

    public static void listarOperadores(RunTime mRunTime, Escopo mEscopo){


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mOperadores = mRun_Context.getOperatorsContexto(mEscopo);


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            System.out.println("\t - Guardado : " + mAST.getTipo() + " " + mAST.getNome());
        }

        for (AST mAST : mOperadores) {

            Index_Function mIndex_Function = new Index_Function(mRunTime, mEscopo, mAST);
            mIndex_Function.resolverTipagem(mEscopo.getRefers());
            System.out.println("\t - Operador : " + mIndex_Function.getDefinicao());

        }

    }
}
