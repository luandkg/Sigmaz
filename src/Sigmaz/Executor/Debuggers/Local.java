package Sigmaz.Executor.Debuggers;

import Sigmaz.Executor.Escopo;
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
        mEscopoDebug.listar_Actions();

        System.out.println(" - FUNCTIONS : ");
        mEscopoDebug.listar_Functions();


        System.out.println(" - DIRECTORS : ");
        mEscopoDebug.listar_Directors();


        System.out.println(" - OPERATORS : ");
        mEscopoDebug.listar_Operators();


        System.out.println(" - CASTS : ");
        mEscopoDebug.listar_Casts();


        System.out.println(" - TYPES : ");
        mEscopoDebug.listar_Types();


        System.out.println(" - STAGES : ");
        mEscopoDebug.listar_Stages();

        System.out.println(" - STRUCTS : ");
        mEscopoDebug.listar_Structs();

        System.out.println(" - EXTERNALS : ");
        mEscopoDebug.listar_Externals();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStack() {


        System.out.println(" ######################### STACK - GLOBAL STACK ############################ ");

        mEscopoDebug.mapear_stack();

        System.out.println(" ######################### ##### ############################ ");

    }


}
