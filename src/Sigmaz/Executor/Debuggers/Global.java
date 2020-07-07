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


        getEscopo().getRunTime().getEscopoGlobal().getDebug().mapear_stack();


        System.out.println(" - ACTIONS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Actions();

        System.out.println(" - FUNCTIONS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Functions();


        System.out.println(" - DIRECTORS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Directors();


        System.out.println(" - OPERATORS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Operators();


        System.out.println(" - CASTS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Casts();


        System.out.println(" - TYPES : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Types();


        System.out.println(" - STAGES : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Stages();


        System.out.println(" - STRUCTS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Structs();

        System.out.println(" - EXTERNALS : ");
        getEscopo().getRunTime().getEscopoGlobal().getDebug().listar_Externals();



        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalStack() {


        System.out.println(" ######################### STACK - GLOBAL STACK ############################ ");


        getEscopo().getRunTime().getEscopoGlobal().getDebug().mapear_stack();


        System.out.println(" ######################### ##### ############################ ");

    }

}
