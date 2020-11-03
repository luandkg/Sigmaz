package Sigmaz.S05_Executor.Debuggers;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Indexador.Index_Function;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.Runners.Run_Context;
import Sigmaz.S05_Executor.Escopos.Run_External;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Regressive {

    private Escopo mEscopo;
    private Simplificador mSimplificador;


    public Regressive(Escopo eEscopo) {

        mEscopo = eEscopo;
        mSimplificador=new Simplificador();

    }

    public String getNome() {
        return mEscopo.getNome();
    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public void ListarRegressiveStack() {

        System.out.println(" ######################### REGRESSIVE : STACK - " + mEscopo.getNome() + " ############################ ");

        mapear_regressive_stack();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarAuto() {

        System.out.println(" ######################### REGRESSIVE : AUTO - " + mEscopo.getNome() + " ############################ ");

        listar_Auto();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarFunctor() {


        System.out.println(" ######################### REGRESSIVE : FUNCTOR - " + mEscopo.getNome() + " ############################ ");

        listar_Functor();

        System.out.println(" ######################### ##### ############################ ");

    }


    public void ListarMark() {


        System.out.println(" ######################### REGRESSIVE : MARK - " + mEscopo.getNome() + " ############################ ");

        listar_Mark();

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

    public void ListarActions() {

        System.out.println(" ######################### REGRESSIVE : ACTIONS - " + mEscopo.getNome() + " ############################ ");

        listar_Actions();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarFunctions() {

        System.out.println(" ######################### REGRESSIVE : FUNCTIONS - " + mEscopo.getNome() + " ############################ ");

        listar_Functions();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarOperators() {

        System.out.println(" ######################### REGRESSIVE : FUNCTIONS - " + mEscopo.getNome() + " ############################ ");

        listar_Operators();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStages() {

        System.out.println(" ######################### REGRESSIVE : STAGES - " + mEscopo.getNome() + " ############################ ");

        listar_Stages();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStructs() {

        System.out.println(" ######################### REGRESSIVE : STRUCTS - " + mEscopo.getNome() + " ############################ ");
        Run_Context mRun_Context = new Run_Context(mEscopo.getRunTime());

        for (AST ASTC : mRun_Context.getStructsContexto(mEscopo)) {


            if (ASTC.getBranch("EXTENDED").mesmoNome("STRUCT")) {
                System.out.println("\t - " + ASTC.getNome());
            }

        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarPackages() {

        System.out.println(" ######################### REGRESSIVE : PACKAGES - " + mEscopo.getNome() + " ############################ ");

        listar_Packages();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarExterns() {

        System.out.println(" ######################### REGRESSIVE : EXTERNS - " + mEscopo.getNome() + " ############################ ");

        listar_Externs();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarRefers() {

        System.out.println(" ######################### REGRESSIVE : REFERS - " + mEscopo.getNome() + " ############################ ");

        listar_Refers();

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarLocal() {

        System.out.println(" ######################### REGRESSIVE : LOCAL - " + mEscopo.getNome() + " ############################ ");

        listar_Local();

        System.out.println(" ######################### ##### ############################ ");

    }


    public void listar_Local() {

        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("LOCAL")) {
                System.out.println("\t - " + " LOCAL ( " + mSimplificador.getParametros(mAST) + " ) ");
            }
        }
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
            for (AST mGetter : mAST.getBranch("BODY").getASTS()) {
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





    public void listar_Mark() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("MARK")) {
                System.out.println("\t - "   +  mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) -> " + mSimplificador.getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }


    public void ListarAll() {


        System.out.println(" #########################  REGRESSIVE " + mEscopo.getNome() + " ############################ ");


        mapear_regressive_stack();


        System.out.println(" - ACTIONS : ");
        listar_Actions();

        System.out.println(" - FUNCTIONS : ");
        listar_Functions();


        System.out.println(" - DIRECTORS : ");
        listar_Directors();


        System.out.println(" - OPERATORS : ");
       listar_Operators();


        System.out.println(" - CASTS : ");
        listar_Casts();


        System.out.println(" - TYPES : ");
        listar_Types();


        System.out.println(" - STAGES : ");
       listar_Stages();


        System.out.println(" - STRUCTS : ");
       listar_Structs();

        System.out.println(" - EXTERNALS : ");
        listar_Externals();


        System.out.println(" ######################### ##### ############################ ");

    }
}
