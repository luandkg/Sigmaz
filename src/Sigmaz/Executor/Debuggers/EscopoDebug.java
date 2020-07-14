package Sigmaz.Executor.Debuggers;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.Runners.Run_Context;
import Sigmaz.Executor.Runners.Run_Extern;
import Sigmaz.Executor.Runners.Run_Struct;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class EscopoDebug {

    private Escopo mEscopo;

    private Global mGlobal;
    private Local mLocal;

    public EscopoDebug(Escopo eEscopo) {

        mEscopo = eEscopo;
        mGlobal = new Global(this);
        mLocal = new Local(this);

    }

    public String getNome() {
        return mEscopo.getNome();
    }

    public Escopo getEscopo() {
        return mEscopo;
    }


    public void ListarGlobalStack() {
        mGlobal.ListarGlobalStack();
    }

    public void ListarGlobalAll() {
        mGlobal.ListarGlobalAll();
    }

    public void ListarLocalAll() {
        mLocal.ListarLocalAll();
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


    public void ListarStruct() {

        Escopo mEstrutura = mEscopo;

        Item eThis = mEscopo.getItem("this");


        System.out.println(" ######################### STRUCT - " + mEscopo.getNome() + " ############################ ");
        System.out.println("");

        System.out.println(" - HEAP : " + eThis.getValor());
        System.out.println(" - TYPE : " + eThis.getTipo());

        Run_Struct mRun = mEscopo.getRunTime().getRun_Struct(eThis.getValor());

        mRun.getEscopo().getDebug().ListarStructAll();

    }

    public void ListarStructAll() {

        mapear_stack();

        System.out.println(" - INITS : ");
        for (Index_Action mAST : getEscopo().getOO().getInits()) {

            if (mAST.getPonteiro().getBranch("CALL").mesmoValor("TRUE")){
                System.out.println("\t - " + mAST.getDefinicao() + " -> " + mAST.getPonteiro().getBranch("CALL").getNome());
            }else{
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


    public String getTipagem(AST eAST) {

        String mTipagem = "";

        if (eAST != null){
             mTipagem = eAST.getNome();

            if (eAST.mesmoValor("GENERIC")) {

                for (AST eTipando : eAST.getASTS()) {
                    mTipagem += "<" + getTipagem(eTipando) + ">";
                }

            }
        }



        return mTipagem;

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
                    ret += mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " , ";
                } else {
                    ret += mAST.getNome() + " : " + getTipagem(mAST.getBranch("TYPE")) + " ";
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

    public String getStages(AST eAST) {

        int i = 0;
        int o = eAST.getASTS().size();

        String ret = " -> { ";

        for (AST mAST : eAST.getASTS()) {
            i += 1;
            if (i == o) {
                ret += mAST.getNome();
            } else {
                ret += mAST.getNome() + ",";

            }
        }

        return ret + " }";
    }

    public void listar_Actions() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("ACTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) ");
            }
        }
    }

    public void listar_Functions() {
        for (AST mAST : getEscopo().getGuardadosCompleto()) {
            if (mAST.mesmoTipo("FUNCTION")) {
                System.out.println("\t - " + mAST.getNome() + " ( " + getParametros(mAST) + " ) -> " + getTipagem(mAST.getBranch("TYPE")));
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
                System.out.println("\t\t - " + mGetter.getTipo() + " " + mGetter.getNome() + " : " + getTipagem(mGetter.getBranch("TYPE")));
            }
        }

    }

    public void listar_Stages() {

        for (AST mAST : getEscopo().getStages()) {
            if (mAST.getBranch("EXTENDED").mesmoNome("STAGES")) {
                System.out.println("\t - " + mAST.getNome() + getStages(mAST.getBranch("STAGES")));
            }
        }

    }

    public void listar_Structs() {
        for (AST mAST : getEscopo().getStructs()) {
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
}
