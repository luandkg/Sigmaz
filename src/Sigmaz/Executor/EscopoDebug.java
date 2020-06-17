package Sigmaz.Executor;

import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class EscopoDebug {

    private Escopo mEscopo;

    public EscopoDebug(Escopo eEscopo) {

        mEscopo = eEscopo;

    }

    public void ListarAll() {


        System.out.println(" ######################### STACK - " + mEscopo.getNome() + " ############################ ");


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
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Defines) {

            if (i.getNulo()) {
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = NULL");
            }
        }

        System.out.println(" - CONSTANTS : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Constants) {
            if (i.getNulo() == false) {
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Constants) {

            if (i.getNulo()) {
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = NULL");
            }
        }

        System.out.println(" - ACTIONS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome()+ " ( " + getParametros(mAST) + " ) ");
            }
        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
        }

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarGlobalAll() {


        System.out.println(" ######################### STACK - GLOBAL ############################ ");


        ArrayList<Item> ls_Defines = new ArrayList<>();
        ArrayList<Item> ls_Constants = new ArrayList<>();

        int defines_nulos = 0;
        int defines_validos = 0;

        for (Item i : mEscopo.getStacksAll()) {
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
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Defines) {

            if (i.getNulo()) {
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = NULL");
            }
        }

        System.out.println(" - CONSTANTS : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Constants) {
            if (i.getNulo() == false) {
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Constants) {

            if (i.getNulo()) {
                System.out.println("\t\t - " + i.getNome() + " : " + i.getTipo() + " = NULL");
            }
        }

        System.out.println(" - ACTIONS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome()+ " ( " + getParametros(mAST) + " ) ");
            }
        }
        System.out.println(" - FUNCTIONS : ");
        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + mAST.getValor());
            }
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

                if(contando<total){
                    ret+=mAST.getNome() + " : " + mAST.getValor() + " , ";
                }else{
                    ret+=mAST.getNome() + " : " + mAST.getValor() + " ";
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
                    System.out.println("\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
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
                    System.out.println("\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
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
                    System.out.println("\t - " + i.getNome() + " : " + i.getTipo() + " = " + i.getValor());
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
                System.out.println("\t - " + mAST.getNome()+ " ( " + getParametros(mAST) + " ) ");
            }
        }

        System.out.println(" ######################### ##### ############################ ");

    }

    public void ListarFunctions() {


        System.out.println(" ######################### FUNCTIONS - " + mEscopo.getNome() + " ############################ ");


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome()+ " ( " + getParametros(mAST) + " ) ");

            }

        }

        System.out.println(" ######################### ##### ############################ ");

    }

}
