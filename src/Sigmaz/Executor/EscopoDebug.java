package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class EscopoDebug {

    private Escopo mEscopo;

    public EscopoDebug(Escopo eEscopo) {

        mEscopo = eEscopo;

    }

    private void mapear_stack(){

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

        int defines_nulos = 0;
        int defines_validos = 0;

        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {
                ls_Defines.add(i);

                if (i.getNulo()) {
                    defines_nulos += 1;
                } else {
                    defines_validos += 1;
                }

            } else if (i.getModo() == 1) {
                ls_Constants.add(i);
            }
        }

        System.out.println(" - DEFINES : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Defines) {
            if (i.getNulo() == false) {
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

    public void ListarStack() {


        System.out.println(" ######################### STACK - " + mEscopo.getNome() + " ############################ ");

        mapear_stack();

    }


    public void ListarAll() {


        System.out.println(" ######################### STACK - " + mEscopo.getNome() + " ############################ ");


        mapear_stack();




        System.out.println(" - INITS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("INIT")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }
        }

        System.out.println(" - ACTIONS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }
        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }

        System.out.println(" - OPERATIONS : ");
        for (Index_Function mIndex_Function : mEscopo.getOperationsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }

        System.out.println(" - CASTS : ");
        for (AST mIndex_Function : mEscopo.getCastsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getNome());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStruct() {

        Escopo mEstrutura = mEscopo.getEstruturador();

        if (mEstrutura == null) {

            System.out.println("\n ######################### STRUCT - NULL ############################ ");
            System.out.println(" ######################### ##### ############################ ");

            return;
        } else {

            mostrar_Struct(mEstrutura);

        }


    }

    public void mostrar_Struct(Escopo mEstrutura) {

        System.out.println("\n ######################### STRUCT - " + mEstrutura.getNomeStruct() + " ############################ ");


        System.out.println(" - DEFINES : ");
        for (AST mAST : mEstrutura.getStructCompleto()) {
            if (mAST.mesmoTipo("DEFINE")) {
                System.out.println("\t - " + mAST.getNome() + " : " + mAST.getValor());
            }
        }
        System.out.println(" - MOCKIZES : ");
        for (AST mAST : mEstrutura.getStructCompleto()) {
            if (mAST.mesmoTipo("MOCKIZ")) {
                System.out.println("\t - " + mAST.getNome() + " : " + mAST.getValor());
            }
        }


        System.out.println(" - ACTIONS : ");
        for (AST mAST : mEstrutura.getStructCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }
        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mAST : mEstrutura.getStructCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
            }
        }

        System.out.println(" - OPERATIONS : ");
        for (AST mAST : mEstrutura.getStructCompleto()) {
            if (mAST.mesmoTipo("OPERATION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }

        System.out.println(" - CASTS : ");
        for (AST mAST : mEstrutura.getStructCompleto()) {
            if (mAST.mesmoTipo("CAST")) {
                System.out.println("\t - " + mAST.getNome());
            }
        }


        System.out.println(" ######################### ##### ############################ ");
    }

    public void mostrarItem(Item eItem) {


        if (eItem.getNulo()) {
            System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = NULL");
        } else {


            if (eItem.getIsEstrutura()) {


                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " -> " + eItem.getValor());


            } else {
                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = " + eItem.getValor());
            }

        }


    }

    public void ListarGlobalAll() {


        System.out.println(" ######################### STACK - GLOBAL " + mEscopo.getNome() + " ############################ ");


        mapear_stack();

        System.out.println(" - ACTIONS : ");
        for (Index_Action mIndex_Function : mEscopo.getActionsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }
        System.out.println(" - FUNCTIONS : ");
        for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }

        System.out.println(" - OPERATIONS : ");
        for (Index_Function mIndex_Function : mEscopo.getOperationsCompleto()) {
            System.out.println("\t - " + mIndex_Function.getDefinicao());
        }

        System.out.println(" - STAGES : ");
        for (AST mAST : mEscopo.getRunTime().getGlobalStages()) {
            System.out.println("\t - " + mAST.getNome());
        }

        System.out.println(" - STRUCTS : ");
        for (AST mAST : mEscopo.getRunTime().getGlobalStructs()) {
            System.out.println("\t - " + mAST.getNome());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalStack() {


        System.out.println(" ######################### STACK - GLOBAL STACK ############################ ");


        mapear_stack();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalStructs() {


        System.out.println(" ######################### STACK - GLOBAL STRUCTS ############################ ");


        for (AST mAST : mEscopo.getRunTime().getGlobalStructs()) {
            System.out.println("\t - " + mAST.getNome());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public String getTipagem(AST eAST){

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")){

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" +getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public void ListarGlobalStages() {


        System.out.println(" ######################### STACK - GLOBAL STAGES ############################ ");


        for (AST mAST : mEscopo.getRunTime().getGlobalStages()) {
            System.out.println("\t - " + mAST.getNome());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalFunctions() {


        System.out.println(" ######################### STACK - GLOBAL FUNCTIONS ############################ ");


        for (AST mAST : mEscopo.getRunTime().getGlobalFunctions()) {
            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalOperations() {


        System.out.println(" ######################### STACK - GLOBAL OPERATIONS ############################ ");


        for (Index_Function mAST : mEscopo.getRunTime().getGlobalOperations()) {
            System.out.println("\t - " + mAST.getDefinicao());
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalActions() {


        System.out.println(" ######################### STACK - GLOBAL ACTIONS ############################ ");


        for (AST mAST : mEscopo.getRunTime().getGlobalActions()) {
            System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " )");
        }


        System.out.println(" ######################### ##### ############################ ");

    }

    public String getParametros(AST ASTPai) {
        String ret = "";

        int total = 0;

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                total += 1;
            }
        }

        int contando = 0;

        for (AST mAST : ASTPai.getBranch("ARGUMENTS").getASTS()) {
            if (mAST.mesmoTipo("ARGUMENT")) {
                contando += 1;

                if (contando < total) {
                    ret += mAST.getNome() + " : " + mAST.getValor() + " , ";
                } else {
                    ret += mAST.getNome() + " : " + mAST.getValor() + " ";
                }
            }
        }

        return ret;
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
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " -> " + i.getValor());
                    } else {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
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
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " -> " + i.getValor());
                    } else {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
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

    public void ListarGlobal() {


        System.out.println(" ######################### STACK - GLOBAL ############################ ");

        ArrayList<Item> ls = mEscopo.getStacksAll();

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
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " -> " + i.getValor());
                    } else {
                        System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
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

    public void ListarActions() {


        System.out.println(" ######################### ACTIONS - " + mEscopo.getNome() + " ############################ ");


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }
        }

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarFunctions() {


        System.out.println(" ######################### FUNCTIONS - " + mEscopo.getNome() + " ############################ ");


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");

            }

        }

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarStructs() {


        System.out.println(" ######################### STRUCTS - " + mEscopo.getNome() + " ############################ ");


        for (AST mAST : mEscopo.getRunTime().getGlobalStructs()) {
            if (mAST.mesmoTipo("STRUCT")) {
                System.out.println("\t - " + mAST.getNome() );

            }

        }

        System.out.println(" ######################### ##### ############################ ");

    }

}
