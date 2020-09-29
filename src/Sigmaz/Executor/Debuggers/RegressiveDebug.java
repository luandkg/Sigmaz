package Sigmaz.Executor.Debuggers;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class RegressiveDebug {

    private Escopo mEscopo;
    private Simplificador mSimplificador;


    public RegressiveDebug(Escopo eEscopo) {

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



    public void listar_Mark() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("MARK")) {
                System.out.println("\t - "   +  mAST.getNome() + " ( " + mSimplificador.getParametros(mAST) + " ) -> " + mSimplificador.getTipagem(mAST.getBranch("TYPE")));
            }
        }
    }
}
