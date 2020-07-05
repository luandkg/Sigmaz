package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Argumentador;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Extern {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mNome;
    private Argumentador mPreparadorDeArgumentos;
    private  AST mStructCorpo;

    private int CA;
    private int CF;
    private int CM;
    private int CD;

    private ArrayList<String> mDesseExtern;

    public Run_Extern(RunTime eRunTime) {
        mRunTime = eRunTime;

        mNome = "";

        mPreparadorDeArgumentos = new Argumentador();

        mDesseExtern = new ArrayList<String>();

    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public boolean mesmoNome(String eNome) {
        return mNome.contentEquals(eNome);
    }

    public void init(AST ASTPai,AST ASTAvo) {

        CA = 0;
        CF = 0;
        CM = 0;
        CD = 0;


        mNome = ASTPai.getNome();

        // System.out.println("Externalizando : " + mNome);

        mEscopo = new Escopo(mRunTime, null);
        mEscopo.setNome(ASTPai.getNome());
        mEscopo.setEstrutura(true);


        for (AST ASTC : mRunTime.getSigmaz().getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("ACTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("OPERATION")) {
                mEscopo.guardar(ASTC);
            }  else if (ASTC.mesmoTipo("CAST")) {
                mEscopo.guardar(ASTC);
            } else if(ASTC.mesmoTipo("STAGES")){
                mEscopo.guardar(ASTC);
            }

        }

        for (AST ASTC : mRunTime.getSigmaz().getASTS()) {



        }

         mStructCorpo = ASTPai.getBranch("BODY");

        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("FUNCTION")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    mEscopo.guardarStruct(ASTC);
                    CF += 1;
                }
            } else if (ASTC.mesmoTipo("ACTION")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    mEscopo.guardarStruct(ASTC);
                    CA += 1;
                }
            }
        }




    }


    public void run() {

        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("DEFINE")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {
                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mDesseExtern.add(ASTC.getNome());

                    mEscopo.guardarStruct(ASTC);
                    CD += 1;
                }


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mDesseExtern.add(ASTC.getNome());

                    mEscopo.guardarStruct(ASTC);
                    CM += 1;

                }


            }

        }

    }

    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }


    public Item init_ObjectExtern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.getErros().add(getNome() + "." + ASTCorrente.getNome() + " : Membro Extern nao encontrado !");
            return null;
        }

        boolean enc = false;
        Item mRet = null;

        for (Item mItem : mEscopo.getOO().getStacks()) {

            if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                mRet = mItem;
                enc = true;
                break;
            }

        }

        if (!enc) {
            mRunTime.getErros().add(getNome() + "." + ASTCorrente.getNome() + " : Membro Extern nao encontrado !");
        }

        return mRet;
    }


    public Item init_Function_Extern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        //  System.out.println("Procurando FUNC " + this.getNome() + "." + ASTCorrente.getNome());

        Item mRet = null;


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        // System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //  System.out.println("\t - Argumentos :  " + mArgumentos.size());

        boolean enc = false;
        boolean algum = false;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        //   System.out.println(" STRUCT :: "  +this.getNome());


        //   for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions_Extern()) {
        //      System.out.println("\t - Func Guardada : " + mIndex_Function.getNome());
        //  }

        //  for (Item mArg : mArgumentos) {
        //   System.out.println("\t - Argumentando : " + mArg.getNome() + " = " + mArg.getValor());
        //  }

        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions_Extern()) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {


                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getPonteiro().mesmoValor(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }


                        mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);

                    } else {
                        mRunTime.getErros().add("Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Retorno incompativel !");
                    }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Function  Extern " + mNome + "." + ASTCorrente.getNome() + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }


        return mRet;
    }


    public int getDefines() {
        return CD;
    }

    public int getMockizes() {
        return CM;
    }

    public int getActions() {
        return CA;
    }

    public int getFunctions() {
        return CF;
    }

    public int getTamanho() {
        return CA + CF + CD + CM;
    }


    public void mostrar() {

        System.out.println(" ######################### EXTERN - " + mNome + " ############################ ");


        ArrayList<Item> ls_Defines = new ArrayList<>();
        ArrayList<Item> ls_Constants = new ArrayList<>();

        int defines_nulos = 0;
        int defines_validos = 0;

        for (Item i : mEscopo.getStacks()) {
            if (mDesseExtern.contains(i.getNome())){
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


        }

        System.out.println(" - DEFINES : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Defines) {
            if (i.getNulo() == false) {
                mEscopo.getDebug().mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Defines) {

            if (i.getNulo()) {
                mEscopo.getDebug().mostrarItem(i);

            }
        }

        System.out.println(" - CONSTANTS : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Constants) {
            if (i.getNulo() == false) {
                mEscopo.getDebug().mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Constants) {

            if (i.getNulo()) {
                mEscopo.getDebug().mostrarItem(i);

            }
        }

        System.out.println(" - ACTIONS : ");
        for (Index_Action mIndex_Function : mEscopo.getOO().getActions_Extern()) {
            if (mIndex_Function.isExtern()) {
                System.out.println("\t - " + mIndex_Function.getDefinicao());

            }
        }
        System.out.println(" - FUNCTIONS : ");
        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions_Extern()) {
            if (mIndex_Function.isExtern()) {
                System.out.println("\t - " + mIndex_Function.getDefinicao());

            }

        }


    }


}

