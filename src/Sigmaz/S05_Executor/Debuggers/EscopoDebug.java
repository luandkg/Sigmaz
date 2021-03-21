package Sigmaz.S05_Executor.Debuggers;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Indexador.Index_Action;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.Runners.Run_Context;
import Sigmaz.S05_Executor.Escopos.Run_External;
import Sigmaz.S05_Executor.Escopos.Run_Struct;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.AST;

public class EscopoDebug {

    private Escopo mEscopo;

    private Local mLocal;
    private Simplificador mSimplificador;

    public EscopoDebug(Escopo eEscopo) {

        mEscopo = eEscopo;
        mLocal = new Local(mEscopo);
        mSimplificador = new Simplificador();

    }

    public String getNome() {
        return mEscopo.getNome();
    }

    public Escopo getEscopo() {
        return mEscopo;
    }


    public void mapear_stack() {



        ArrayList<Item> ls_mutavel = new ArrayList<>();
        ArrayList<Item> ls_Defines = new ArrayList<>();
        ArrayList<Item> ls_Constants = new ArrayList<>();
        ArrayList<Item> ls_refereds_extern = new ArrayList<>();
        ArrayList<Item> ls_refereds_implicit = new ArrayList<>();

        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {
                ls_Defines.add(i);

            } else if (i.getModo() == 1) {
                ls_Constants.add(i);
            } else if (i.getModo() == 2) {
                ls_mutavel.add(i);
            } else if (i.getModo() == 5) {
                ls_refereds_extern.add(i);
            } else if (i.getModo() == 6) {
                ls_refereds_extern.add(i);
            } else if (i.getModo() == 7) {
                ls_refereds_implicit.add(i);
            } else if (i.getModo() == 8) {
                ls_refereds_implicit.add(i);
            }

            // System.out.println("DEFINICAO :: " + i.getNome());
        }

        if (ls_mutavel.size() > 0) {

            System.out.println(" - MUTABLE : ");

            System.out.println("\t - NAO NULOS : ");

            for (Item i : ls_mutavel) {
                if (!i.getNulo()) {
                    mostrarItem(i);
                }
            }

            System.out.println("\t - NULOS : ");
            for (Item i : ls_mutavel) {

                if (i.getNulo()) {
                    mostrarItem(i);
                }
            }

        }

        if (ls_refereds_extern.size() > 0) {

            System.out.println(" - REFEREDS EXTERN : ");

            System.out.println("\t - NAO NULOS : ");

            for (Item i : ls_refereds_extern) {
                if (!i.getNulo()) {
                    mostrarItem(i);
                }
            }

            System.out.println("\t - NULOS : ");
            for (Item i : ls_refereds_extern) {

                if (i.getNulo()) {
                    mostrarItem(i);
                }
            }

        }

        if (ls_refereds_implicit.size() > 0) {

            System.out.println(" - REFEREDS IMPLICIT : ");

            System.out.println("\t - NAO NULOS : ");

            for (Item i : ls_refereds_implicit) {
                if (!i.getNulo()) {
                    mostrarItem(i);
                }
            }

            System.out.println("\t - NULOS : ");
            for (Item i : ls_refereds_implicit) {

                if (i.getNulo()) {
                    mostrarItem(i);
                }
            }

        }

        //  if (ls_Defines.size()>0){

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

        //   }

        if (ls_Constants.size() > 0) {

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


    }

    public void mostrarItem(Item eItem) {


        if (eItem.getNulo()) {
            System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = NULL");
        } else {


            if (eItem.getIsEstrutura()) {


                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " -> " + eItem.getValor(mEscopo.getRunTime(), mEscopo));


            } else {
                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = " + eItem.getValor(mEscopo.getRunTime(), mEscopo));
            }

        }


    }


    public void ListarStack() {


        System.out.println(" ######################### LOCAL : STACK - " + mEscopo.getNome() + " ############################ ");

        mapear_stack();

    }


    public void ListarStruct() {

        Escopo mEstrutura = mEscopo;

        Item eThis = mEscopo.getItem("this");


        System.out.println(" ######################### STRUCT - " + mEscopo.getNome() + " ############################ ");
        System.out.println("");

        System.out.println(" - HEAP : " + eThis.getValor(mEscopo.getRunTime(), mEscopo));
        System.out.println(" - TYPE : " + eThis.getTipo());

        Run_Struct mRun = mEscopo.getRunTime().getHeap().getRun_Struct(eThis.getValor(mEscopo.getRunTime(), mEscopo));

        if (mEscopo.getRunTime().getErros().size() > 0) {
            return;
        }

        mRun.getEscopo().getDebug().ListarStructAll();

    }

    public void ListarStructAll() {

        mapear_stack();

        System.out.println(" - INITS : ");
        for (Index_Action mAST : getEscopo().getOO().getInits()) {

            if (mAST.getPonteiro().getBranch("CALL").mesmoValor("TRUE")) {
                System.out.println("\t - " + mAST.getDefinicao() + " -> " + mAST.getPonteiro().getBranch("CALL").getNome());
            } else {
                System.out.println("\t - " + mAST.getDefinicao());
            }

        }
        System.out.println(" - BASES : ");
        for (AST mAST : getEscopo().getOO().getBases()) {
            System.out.println("\t - " + mAST.getNome());
        }

        System.out.println(" - ACTIONS : ");
        for (Index_Action mAST : getEscopo().getOO().getActions_All()) {
            System.out.println("\t - " + mAST.getDefinicao());
        }
        System.out.println(" - FUNCTIONS : ");
        for (Index_Function mAST : getEscopo().getOO().getFunctions_All()) {
            System.out.println("\t - " + mAST.getDefinicao());
        }

    }


    public void ListarGlobalPackages() {


        System.out.println(" #########################  GLOBAL - PACKAGES ############################ ");

        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Packages();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalExterns() {


        System.out.println(" #########################  GLOBAL - EXTERNS ############################ ");

        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Externs();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalRefers() {


        System.out.println(" #########################  GLOBAL - REFERS ############################ ");

        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Refers();

        System.out.println(" ######################### ##### ############################ ");

    }


    public void ListarGlobalStages() {


        System.out.println(" #########################  GLOBAL - STAGES ############################ ");

        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Stages();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalFunctions() {


        System.out.println(" ######################### GLOBAL - FUNCTIONS ############################ ");


        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Functions();


        System.out.println(" ######################### ##### ############################ ");

    }


    public void ListarGlobalOperations() {


        System.out.println(" #########################  GLOBAL - OPERATIONS ############################ ");


        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Operators();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalActions() {


        System.out.println(" ######################### GLOBAL - ACTIONS ############################ ");

        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Actions();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalStructs() {


        System.out.println(" ######################### GLOBAL - STRUCTS ############################ ");


        mEscopo.getRunTime().getEscopoGlobal().getDebug().listar_Structs();


        System.out.println(" ######################### ##### ############################ ");

    }


    public void ListarDefines() {


        System.out.println(" ######################### STACK - " + mEscopo.getNome() + " ############################ ");

        ArrayList<Item> ls = new ArrayList<>();
        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {
                ls.add(i);
            }
        }

        int nulos = 0;
        int validos = 0;
        for (Item i : ls) {
            if (i.getNulo()) {
                nulos += 1;
            } else {
                validos += 1;
            }
        }

        if (validos > 0) {
            System.out.println(" - NAO NULOS : ");

            for (Item i : ls) {
                if (i.getNulo() == false) {
                    if (i.getIsEstrutura()) {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " -> " + i.getValor(mEscopo.getRunTime(), mEscopo));
                    } else {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor(mEscopo.getRunTime(), mEscopo));
                    }
                }
            }
        }

        if (nulos > 0) {
            System.out.println(" - NULOS : ");
            for (Item i : ls) {

                if (i.getNulo()) {
                    System.out.println("\t - " + i.getNome() + " : " + i.getTipo() + " = NULL");
                }
            }
        }

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarConstants() {


        System.out.println(" ######################### STACK - " + mEscopo.getNome() + " ############################ ");

        ArrayList<Item> ls = new ArrayList<>();
        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 1) {
                ls.add(i);
            }
        }

        int nulos = 0;
        int validos = 0;
        for (Item i : ls) {
            if (i.getNulo()) {
                nulos += 1;
            } else {
                validos += 1;
            }
        }

        if (validos > 0) {
            System.out.println(" - NAO NULOS : ");

            for (Item i : ls) {
                if (i.getNulo() == false) {
                    if (i.getIsEstrutura()) {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " -> " + i.getValor(mEscopo.getRunTime(), mEscopo));
                    } else {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor(mEscopo.getRunTime(), mEscopo));
                    }
                }
            }
        }

        if (nulos > 0) {
            System.out.println(" - NULOS : ");
            for (Item i : ls) {

                if (i.getNulo()) {
                    System.out.println("\t - " + i.getNome() + " : " + i.getTipo() + " = NULL");
                }
            }
        }

        System.out.println(" ######################### ##### ############################ ");

    }




    public void mapear_regressive_stack() {



        ArrayList<Item> ls_Defines = new ArrayList<>();
        ArrayList<Item> ls_Constants = new ArrayList<>();

        for (Item i : mEscopo.getStacksAll()) {
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

        for (AST ASTC : mRun_Context.getStructsContexto(mEscopo)) {


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
                System.out.println("\t - " + mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) -> " + mSimplificador.getTipagem(mAST.getBranch("TYPE")));
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
                System.out.println("\t - " + "< " + mSimplificador.getGenericos(mAST) + " >" + " " + mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) ");
            }
        }
    }

    public void listar_Functor() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("PROTOTYPE_FUNCTOR")) {
                System.out.println("\t - " + "< " + mSimplificador.getGenericos(mAST) + " >" + " " + mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) -> " + mSimplificador.getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }

    public void listar_Casts() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("CAST")) {

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

    }

    public void listar_Types() {

        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST mAST : mRun_Context.getTypesContexto(mEscopo)) {
            System.out.println("\t - " + mAST.getNome());
            for (AST mGetter : mAST.getASTS()) {
                System.out.println("\t\t - " + mGetter.getTipo() + " " + mGetter.getNome() + " : " + mSimplificador.getTipagem(mGetter.getBranch("TYPE")));
            }
        }

    }

    public void listar_Stages() {

        Simplificador mSimplificador = new Simplificador();

        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST mAST : mRun_Context.getStagesContexto(mEscopo)) {
            System.out.println("\t - " + mAST.getNome() + mSimplificador.getStages(mAST.getBranch("STAGES")));
        }

    }

    public void listar_Structs() {
        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST mAST : mRun_Context.getStructsContexto(mEscopo)) {
            System.out.println("\t - " + mAST.getNome());

        }
    }

    public void listar_Externals() {
        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST mAST : mRun_Context.getExternalsContexto(mEscopo)) {
            System.out.println("\t - " + mAST.getNome());

        }
    }

    public void listar_Packages() {
        for (AST mAST : mEscopo.getRunTime().getGlobalPackages()) {
            System.out.println("\t - " + mAST.getNome());
        }
    }

    public void listar_Externs() {
        for (Run_External mAST : mEscopo.getExtern()) {
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

}
