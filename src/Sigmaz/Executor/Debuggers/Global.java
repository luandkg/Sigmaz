package Sigmaz.Executor.Debuggers;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

public class Global {

    private EscopoDebug mEscopoDebug;

    public Global(EscopoDebug eEscopoDebug) {

        mEscopoDebug = eEscopoDebug;

    }

    public Escopo getEscopo() {
        return mEscopoDebug.getEscopo();
    }

    public void ListarGlobalAll() {


        System.out.println(" #########################  GLOBAL " + mEscopoDebug.getNome() + " ############################ ");


        mEscopoDebug.mapear_stack();

        System.out.println(" - ACTIONS : ");
        for (AST mIndex_Function : getEscopo().getRunTime().getGlobalActions()) {
            System.out.println("\t - " + (new Index_Action(mIndex_Function)).getDefinicao());
        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mIndex_Function : getEscopo().getRunTime().getGlobalFunctions()) {
            System.out.println("\t - " + (new Index_Function(mIndex_Function)).getDefinicao());
        }

        System.out.println(" - DIRECTORS : ");
        for (AST mIndex_Function : getEscopo().getRunTime().getGlobalDirectors()) {
            System.out.println("\t - " + (new Index_Function(mIndex_Function)).getDefinicao());
        }

        System.out.println(" - OPERATORS : ");
        for (AST mIndex_Function : getEscopo().getRunTime().getGlobalOperations()) {
            System.out.println("\t - " + (new Index_Function(mIndex_Function)).getDefinicao());
        }

        System.out.println(" - CASTS : ");
        for (AST mAST : getEscopo().getRunTime().getGlobalCasts()) {
            System.out.println("\t - " + mAST.getNome());
            for (AST mGetter : mAST.getASTS()) {
                if (mGetter.mesmoTipo("GETTER")) {
                    System.out.println("\t\t - Getter : " + mGetter.getValor());
                }
            }
            for (AST mGetter : mAST.getASTS()) {
                if (mGetter.mesmoTipo("SETTER")) {
                    System.out.println("\t\t - Setter : " + mGetter.getValor());
                }
            }
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

    public void ListarGlobalStack() {


        System.out.println(" ######################### STACK - GLOBAL STACK ############################ ");


        mEscopoDebug.mapear_stack();


        System.out.println(" ######################### ##### ############################ ");

    }

}
