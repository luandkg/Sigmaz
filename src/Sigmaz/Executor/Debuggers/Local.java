package Sigmaz.Executor.Debuggers;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

public class Local {

    private EscopoDebug mEscopoDebug;

    public Local(EscopoDebug eEscopoDebug) {

        mEscopoDebug = eEscopoDebug;

    }

    public Escopo getEscopo() {
        return mEscopoDebug.getEscopo();
    }


    public void ListarLocalAll() {


        System.out.println(" ######################### LOCAL - " + getEscopo().getNome() + " ############################ ");


        mEscopoDebug.mapear_stack();


        System.out.println(" - INITS : ");
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("INIT")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + mEscopoDebug.getParametros(mAST) + " ) ");
            }
        }

        System.out.println(" - ACTIONS : ");
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + mEscopoDebug.getParametros(mAST) + " ) ");
            }
        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + mEscopoDebug.getParametros(mAST) + " ) -> " + mEscopoDebug.getTipagem(mAST.getBranch("TYPE")));
            }
        }

        System.out.println(" - DIRECTORS : ");
        for (Index_Function mIndex_Function : getEscopo().getDirectorsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }

        System.out.println(" - OPERATORS : ");
        for (Index_Function mIndex_Function : getEscopo().getOperationsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }

        System.out.println(" - CASTS : ");
        for (AST mIndex_Function : getEscopo().getCastsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getNome());
        }

        System.out.println(" - TYPES : ");
        for (AST mAST : getEscopo().getRunTime().getGlobalTypes()) {
            System.out.println("\t - " + mAST.getNome());
            for (AST mGetter : mAST.getASTS()) {
                System.out.println("\t\t - " + mGetter.getTipo() + " " + mGetter.getNome() + " : " + mEscopoDebug.getTipagem(mGetter.getBranch("TYPE")));
            }
        }

        System.out.println(" - STAGES : ");
        for (AST mAST : getEscopo().getRunTime().getGlobalStages()) {
            System.out.println("\t - " + mAST.getNome());
        }

        System.out.println(" - STRUCTS : ");
        for (AST mAST : getEscopo().getRunTime().getGlobalStructs()) {
            System.out.println("\t - " + mAST.getNome());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

}
