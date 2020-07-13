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

    private String mPacote;
    private String mNome;

    private Argumentador mPreparadorDeArgumentos;
    private AST mStructCorpo;

    private int CA;
    private int CF;
    private int CM;
    private int CD;

    private ArrayList<String> mDesseExtern;
    private  ArrayList<String> dRefers;

    public Run_Extern(RunTime eRunTime) {
        mRunTime = eRunTime;

        mNome = "";

        mPreparadorDeArgumentos = new Argumentador();

        mDesseExtern = new ArrayList<String>();
        dRefers = new ArrayList<String>();

    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getPacote() {
        return mPacote;
    }

    public String getNome() {
        return mNome;
    }

    public String getNomeCompleto() {
        return mPacote + "<>" + mNome;
    }


    public boolean mesmoNome(String eNome) {
        return mNome.contentEquals(eNome);
    }

    public void init(String ePacote, String eNome, AST ASTPai, AST ASTAvo) {

        CA = 0;
        CF = 0;
        CM = 0;
        CD = 0;


        mPacote = ePacote;
        mNome = eNome;

        // System.out.println("Externalizando : " + mNome);

        mEscopo = new Escopo(mRunTime, null);
        mEscopo.setNome(mNome);
        mEscopo.setEstrutura(true);




        for (AST ASTC : mRunTime.getSigmaz().getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("ACTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("OPERATION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("CAST")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("STRUCT")) {
                mEscopo.guardar(ASTC);
            }

        }


        mStructCorpo = ASTPai.getBranch("BODY");


        AST mRefers = ASTPai.getBranch("REFERS");
       dRefers = new ArrayList<String>();

        for (AST ASTC : mRefers.getASTS()) {
            String eRefer = ASTC.getNome();
            dRefers.add(eRefer);
        }




        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("FUNCTION")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    mEscopo.guardarStruct(ASTC,dRefers);
                    CF += 1;
                }
            } else if (ASTC.mesmoTipo("ACTION")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    mEscopo.guardarStruct(ASTC,dRefers);
                    CA += 1;
                }
            }
        }


    }

    public void externalizar(ArrayList<Run_Extern> GlobalExtern) {

        for (Run_Extern mRE : GlobalExtern) {
            mEscopo.externalizarStructGeral(mRE.getNome());
        }

    }

    public void run() {

        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("DEFINE")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {
                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mDesseExtern.add(ASTC.getNome());

                    mEscopo.guardarStruct(ASTC,dRefers);
                    CD += 1;
                }


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mDesseExtern.add(ASTC.getNome());

                    mEscopo.guardarStruct(ASTC,dRefers);
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

      //  System.out.println(" --> Escopo Externo : " + mEscopo.getNome());


        for (Item mItem : mEscopo.getOO().getStacks()) {

           // System.out.println(" --> Membro Externo : " + mItem.getNome());

            if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                mRet = mItem;
                enc = true;
                break;
            }

        }

        if (!enc) {
            mRunTime.getErros().add(getNome() + "." + ASTCorrente.getNome() + " :  Membro Extern nao encontrado !");
        }

        return mRet;
    }


    public Item init_Function_Extern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        Item mRet = null;

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));


        boolean enc = false;
        boolean algum = false;


        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions_Extern()) {


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                if ( !mIndex_Function.getEstaResolvido()){
                    mIndex_Function.resolverTipagem(BuscadorDeVariaveis.getRefers());
                }

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mEscopo,mArgumentos)) {

                   //  System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;
                   // System.out.println("\t - Executar :  " + mIndex_Function.getNome() + " ANTES :: " + eRetorne);

                    //if (mIndex_Function.getPonteiro().mesmoValor(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }


                        mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);

                      //  System.out.println("\t - Executar :  " + mIndex_Function.getNome() + " RET ::: " + mRet);

                   // } else {
                   //     mRunTime.getErros().add("Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Retorno incompativel !");
                   // }

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

        }


        return mRet;
    }

    public void init_ActionFunction_Extern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));


        boolean enc = false;
        boolean algum = false;


        for (Index_Action mIndex_Function : mEscopo.getOO().getActionsFunctions_Extern()) {

           // System.out.println("\t - Procurar Extern :  " + mIndex_Function.getNome());

            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                if (! mIndex_Function.getEstaResolvido()){
                    mIndex_Function.resolverTipagem(BuscadorDeVariaveis.getRefers());
                }

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mEscopo,mArgumentos)) {

                   // System.out.println("\t - Executar Extern :  " + mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getPonteiro().mesmoValor(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }


                        mPreparadorDeArgumentos.executar_Action(mRunTime, BuscadorDeVariaveis, mIndex_Function, mArgumentos);

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

        }


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


    public void Struct_Execute(AST ASTCorrente, Escopo gEscopo) {

      //  System.out.println("GET EXTERN  CALL -> " + gEscopo.getNome() + " : " + ASTCorrente.getNome());

      //  for (String r : gEscopo.getRefers()) {
       //     System.out.println("\t -  REFER : " + r);
      //  }
        Run_Context mRun_Context = new Run_Context(mRunTime);

        Run_Extern mEscopoExtern = null;
        for (Run_Extern mRun_Struct : mRun_Context.getRunExternContexto(gEscopo.getRefers())) {

        //    System.out.println("\t - PASS EXTERN : " + mRun_Struct.getNome());

            if (mRun_Struct.getNome().contentEquals(ASTCorrente.getNome())) {
                mEscopoExtern = mRun_Struct;
                break;
            }
        }

      //  System.out.println("ENC EXTERN : " + ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        AST eInternal = ASTCorrente.getBranch("INTERNAL");


        if (eInternal.mesmoValor("STRUCT_FUNCT")) {


            if (mRunTime.getErros().size() > 0) {
                return;
            }

          //  System.out.println("Mudando Para EXTERN - STRUCT_FUNCT " + eInternal.getNome());


            mEscopoExtern.init_ActionFunction_Extern(eInternal, gEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }


        } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //      System.out.println("STRUCT_OBJECT : " + mEscopoStruct.getNome());

            Item eItem = mEscopoExtern.init_ObjectExtern(eInternal, gEscopo, "<<ANY>>");

            while (eInternal.existeBranch("INTERNAL")) {

                AST sInternal = eInternal.getBranch("INTERNAL");

                if (sInternal.mesmoValor("STRUCT_ACT")) {

                } else if (sInternal.mesmoValor("STRUCT_FUNCT")) {

                    //System.out.println("STRUCT OBJECT : " + eItem.getValor());

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if (!eItem.getTipo().contentEquals("<<ANY>>")) {
                        //   mEscopoExtern = mRunTime.getRun_Struct(eItem.getValor());

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }

                        mEscopoExtern.init_ActionFunction_Extern(sInternal, gEscopo, "<<ANY>>");

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }
                    } else {

                        break;
                    }


                } else if (sInternal.mesmoValor("STRUCT_OBJECT")) {

                }
            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }


        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }


    }

}

