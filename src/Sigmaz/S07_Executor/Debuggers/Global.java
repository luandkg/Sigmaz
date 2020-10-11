package Sigmaz.S07_Executor.Debuggers;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;

import java.util.ArrayList;

public class Global {

    private Escopo mEscopo;

    public Global(Escopo eEscopo) {

        mEscopo = eEscopo;

    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public void ListarGlobalAll() {


        System.out.println(" #########################  GLOBAL " + mEscopo.getNome() + " ############################ ");


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

    public void ListarGlobalStack() {


        System.out.println(" ######################### GLOBAL - STACK ############################ ");


        mapear_stack();


        System.out.println(" ######################### ##### ############################ ");

    }


    public void ListarRegressiveStack() {


        System.out.println(" ######################### STACK - REGRESSIVE STACK ############################ ");


        mEscopo.getDebug().mapear_regressive_stack();


        System.out.println(" ######################### ##### ############################ ");

    }

    public void mapear_stack() {

        System.out.println(" - ESCOPO : " + mEscopo.getNome());


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
}
