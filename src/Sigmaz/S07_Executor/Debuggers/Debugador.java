package Sigmaz.S07_Executor.Debuggers;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_Context;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Debugador {

    public static void listarOperadores(RunTime mRunTime, Escopo mEscopo){


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
}
