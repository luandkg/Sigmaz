package Sigmaz.Executor.Debuggers;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.Runners.Run_Context;
import Sigmaz.Executor.Runners.Run_Extern;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Local {

    private Escopo mEscopo;
    private Simplificador mSimplificador;

    public Local(Escopo eEscopo) {

        mEscopo = eEscopo;
        mSimplificador=new Simplificador();

    }

    public Escopo getEscopo() {
        return mEscopo;
    }




    public void ListarStack() {


        System.out.println(" ######################### LOCAL - STACK : " + mEscopo.getNome() + " ############################ ");

        mapear_stack();

        System.out.println(" ######################### ##### ############################ ");

    }


    public void ListarDefines() {


        System.out.println(" ######################### LOCAL - DEFINES : " + mEscopo.getNome() + " ############################ ");

        mapear_stack_def();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarConstantes() {


        System.out.println(" ######################### LOCAL - CONSTANTES : " + mEscopo.getNome() + " ############################ ");

        mapear_stack_moc();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void mapear_stack() {

        System.out.println(" - PARAM : ");
        ArrayList<Item> ls_Param = new ArrayList<>();
        for (Item i : mEscopo.getParametros()) {
            ls_Param.add(i);
        }

        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Param) {
            if (i.getNulo() == false) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Param) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }


        ArrayList<Item> ls_Defines = new ArrayList<>();
        ArrayList<Item> ls_Constants = new ArrayList<>();

        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {
                ls_Defines.add(i);

            } else if (i.getModo() == 1) {
                ls_Constants.add(i);
            }

            // System.out.println("DEFINICAO :: " + i.getNome());
        }

        System.out.println(" - DEFINES : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Defines) {
            if (!i.getNulo()) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Defines) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }

        System.out.println(" - CONSTANTS : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Constants) {
            if (i.getNulo() == false) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Constants) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }

    }

    public void mapear_stack_def() {




        ArrayList<Item> ls_Defines = new ArrayList<>();

        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {
                ls_Defines.add(i);

         }

            // System.out.println("DEFINICAO :: " + i.getNome());
        }

        System.out.println(" - DEFINES : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Defines) {
            if (!i.getNulo()) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Defines) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }

    }

    public void mapear_stack_moc() {




        ArrayList<Item> ls_Constants = new ArrayList<>();

        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {

            } else if (i.getModo() == 1) {
                ls_Constants.add(i);
            }

            // System.out.println("DEFINICAO :: " + i.getNome());
        }


        System.out.println(" - CONSTANTS : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Constants) {
            if (i.getNulo() == false) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Constants) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }

    }


    public void mostrarItem(Item eItem) {


        if (eItem.getNulo()) {
            System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = NULL");
        } else {


            if (eItem.getIsEstrutura()) {


                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " -> " + eItem.getValor(mEscopo.getRunTime(),mEscopo));


            } else {
                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = " + eItem.getValor(mEscopo.getRunTime(),mEscopo));
            }

        }


    }



    public void ListarFunctions() {

        System.out.println(" ######################### FUNCTIONS - " + mEscopo.getNome() + " ############################ ");

        listar_Functions();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarActions() {


        System.out.println(" ######################### LOCAL : ACTIONS - " + mEscopo.getNome() + " ############################ ");


        listar_Actions();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStages() {


        System.out.println(" ######################### LOCAL : STAGES - " + mEscopo.getNome() + " ############################ ");


        listar_Stages();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStructs() {


        System.out.println(" ######################### LOCAL : STRUCTS - " + mEscopo.getNome() + " ############################ ");
        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST ASTC : mRun_Context.getStructsContexto(mEscopo.getRefers())) {


            if (ASTC.getBranch("EXTENDED").mesmoNome("STRUCT")) {
                System.out.println("\t - " + ASTC.getNome());
            }




        }




        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarPackages() {


        System.out.println(" ######################### LOCAL : PACKAGES - " + mEscopo.getNome() + " ############################ ");


        listar_Packages();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarExterns() {


        System.out.println(" ######################### LOCAL : EXTERNS - " + mEscopo.getNome() + " ############################ ");


        listar_Externs();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarRefers() {


        System.out.println(" ######################### LOCAL : REFERS - " + mEscopo.getNome() + " ############################ ");


        listar_Refers();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarLocal() {


        System.out.println(" ######################### LOCAL : LOCAL - " + mEscopo.getNome() + " ############################ ");


        listar_Local();


        System.out.println(" ######################### ##### ############################ ");

    }


    public void listar_Actions() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) ");
            }
        }
    }

    public void listar_Functions() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " +  mSimplificador.getParametros(mAST) + " ) -> " + mSimplificador.getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }

    public void listar_Directors() {
        for (Index_Function mIndex_Function : getEscopo().getDirectorsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }
    }

    public void listar_Operators() {
        for (Index_Function mIndex_Function : getEscopo().getOperationsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }
    }

    public void listar_Auto() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("PROTOTYPE_AUTO")) {
                System.out.println("\t - " + "< " + mSimplificador.getGenericos(mAST) + " >" + " "  + mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) ");
            }
        }
    }

    public void listar_Functor() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                System.out.println("\t - " + "< " + mSimplificador.getGenericos(mAST) + " >" + " "  +  mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) -> " + mSimplificador.getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }


    public void listar_Local() {

        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("LOCAL")) {
                System.out.println("\t - " + " LOCAL ( " + mSimplificador.getParametros(mAST) + " ) ");
            }
        }
    }


    public void listar_Casts() {
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

    }

    public void listar_Types() {

        for (AST mAST : getEscopo().getRunTime().getGlobalTypes()) {
            System.out.println("\t - " + mAST.getNome());
            for (AST mGetter : mAST.getASTS()) {
                System.out.println("\t\t - " + mGetter.getTipo() + " " + mGetter.getNome() + " : " + mSimplificador.getTipagem(mGetter.getBranch("TYPE")));
            }
        }

    }

    public void listar_Stages() {

        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST mAST : mRun_Context.getStructsContexto(mEscopo.getRefers())) {
            if (mAST.getBranch("EXTENDED").mesmoNome("STAGES")) {
                System.out.println("\t - " + mAST.getNome() + mSimplificador. getStages(mAST.getBranch("STAGES")));
            }
        }

    }

    public void listar_Structs() {
        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST mAST : mRun_Context.getStructsContexto(mEscopo.getRefers())) {
            if (mAST.getBranch("EXTENDED").mesmoNome("STRUCT")) {
                System.out.println("\t - " + mAST.getNome());
            }
        }
    }

    public void listar_Externals() {
        for (AST mAST : getEscopo().getRunTime().getGlobalStructs()) {
            if (mAST.getBranch("EXTENDED").mesmoNome("EXTERNAL")) {
                System.out.println("\t - " + mAST.getNome());
            }
        }
    }

    public void listar_Packages() {
        for (AST mAST : mEscopo.getRunTime().getGlobalPackages()) {
            System.out.println("\t - " + mAST.getNome());
        }
    }

    public void listar_Externs() {
        for (Run_Extern mAST : mEscopo.getExtern()) {
            System.out.println("\t - " + mAST.getNomeCompleto());
        }
    }

    public void listar_Refers() {
        for (String mAST : mEscopo.getRefers()) {
            System.out.println("\t - " + mAST);
        }
    }

    public void ListarAuto() {


        System.out.println(" ######################### LOCAL : AUTO - " + mEscopo.getNome() + " ############################ ");


        listar_Auto();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarFunctor() {


        System.out.println(" ######################### LOCAL : FUNCTOR - " + mEscopo.getNome() + " ############################ ");


        listar_Functor();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarAll() {


        System.out.println(" #########################  LOCAL " + mEscopo.getNome() + " ############################ ");


        mEscopo.getDebug().mapear_stack();


        System.out.println(" - ACTIONS : ");
        mEscopo.getDebug().listar_Actions();

        System.out.println(" - FUNCTIONS : ");
        mEscopo.getDebug().listar_Functions();


        System.out.println(" - DIRECTORS : ");
        mEscopo.getDebug().listar_Directors();


        System.out.println(" - OPERATORS : ");
        mEscopo.getDebug().listar_Operators();


        System.out.println(" - CASTS : ");
        mEscopo.getDebug().listar_Casts();


        System.out.println(" - TYPES : ");
        mEscopo.getDebug().listar_Types();


        System.out.println(" - STAGES : ");
        mEscopo.getDebug().listar_Stages();


        System.out.println(" - STRUCTS : ");
        mEscopo.getDebug().listar_Structs();

        System.out.println(" - EXTERNALS : ");
        mEscopo.getDebug().listar_Externals();


        System.out.println(" ######################### ##### ############################ ");

    }


}
