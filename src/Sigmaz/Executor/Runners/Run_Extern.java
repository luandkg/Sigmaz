package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Extern {

    private RunTime mRunTime;
    private String mLocal;

    private Escopo mEscopo;

    private String mPacote;
    private String mNome;

    private Run_Arguments mPreparadorDeArgumentos;
    private AST mStructCorpo;


    private ArrayList<String> mDesseExtern;
    private ArrayList<String> dRefers;

    private ArrayList<String> mExterns;
    private ArrayList<String> mImplicits;


    public Run_Extern(RunTime eRunTime) {
        mRunTime = eRunTime;

        mNome = "";

        mPreparadorDeArgumentos = new Run_Arguments();

        mDesseExtern = new ArrayList<String>();
        dRefers = new ArrayList<String>();
        mLocal = "Run_Extern";

        mExterns = new ArrayList<String>();
        mImplicits = new ArrayList<String>();

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


        mPacote = ePacote;
        mNome = eNome;

        // System.out.println("Externalizando : " + mNome);

        //  System.out.println("INICIANDO EXTERN :: " + ePacote + "<>" + eNome);


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


        AST ASTRefers = ASTPai.getBranch("REFERS");
        dRefers = new ArrayList<String>();

        for (AST ASTC : ASTRefers.getASTS()) {
            String eRefer = ASTC.getNome();
            dRefers.add(eRefer);
            mEscopo.adicionarRefer(eRefer);
        }


        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("FUNCTION")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                } else if (getModo(ASTC).contentEquals("IMPLICIT")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                }

            } else if (ASTC.mesmoTipo("ACTION")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                } else if (getModo(ASTC).contentEquals("IMPLICIT")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                }


            }
        }


    }


    public void run() {

        // System.out.println("Externo :: " + this.getNomeCompleto());

        //    System.out.println("INICIANDO EXTERN :: " + mPacote + "<>" + mNome);

        mEscopo.externalizar(this.getNomeCompleto());


        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("DEFINE")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {
                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    //System.out.println("\t EXTERN -->> " + ASTC.getNome());

                    mDesseExtern.add(ASTC.getNome());

                    mEscopo.guardarStruct(ASTC);

                    mExterns.add(ASTC.getNome());

                } else if (getModo(ASTC).contentEquals("IMPLICIT")) {

                    //System.out.println("\t IMPLICIT -->> " + ASTC.getNome());


                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);


                }


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                if (getModo(ASTC).contentEquals("EXTERN")) {

                    Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mDesseExtern.add(ASTC.getNome());

                    //  System.out.println("\t EXTERN -->> " + ASTC.getNome());

                    mEscopo.guardarStruct(ASTC);

                    mExterns.add(ASTC.getNome());

                } else if (getModo(ASTC).contentEquals("IMPLICIT")) {

                    //  System.out.println("\t IMPLICIT -->> " + ASTC.getNome());

                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);


                }


            }

        }


    }

    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }


    public Item init_ObjectExtern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.errar(mLocal, getNome() + "." + ASTCorrente.getNome() + " : Membro Extern nao encontrado !");
            return null;
        }

        boolean enc = false;
        Item mRet = null;

        //  System.out.println(" --> Escopo Externo : " + mEscopo.getNome());

        if (mExterns.contains(ASTCorrente.getNome())) {

            for (Item mItem : mEscopo.getOO().getStacks()) {

                // System.out.println(" --> Membro Externo : " + mItem.getNome());

                if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                    mRet = mItem;
                    enc = true;
                    break;
                }

            }

            if (!enc) {
                mRunTime.errar(mLocal, getNome() + "->" + ASTCorrente.getNome() + " :  Membro Extern nao encontrado !");
            }

        } else {
            mRunTime.errar(mLocal, getNome() + "->" + ASTCorrente.getNome() + " :  Membro Extern nao existente !");
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

                if (!mIndex_Function.getEstaResolvido()) {
                    mIndex_Function.resolverTipagem(BuscadorDeVariaveis.getRefers());
                }

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mEscopo, mArgumentos)) {

                    // System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;
                    // System.out.println("\t - Executar :  " + mIndex_Function.getNome() + " ANTES :: " + eRetorne);

                    //if (mIndex_Function.getPonteiro().mesmoValor(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }


                    mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);

                    //  System.out.println("\t - Executar :  " + mIndex_Function.getNome() + " RET ::: " + mRet);

                    // } else {
                    //     mRunTime.errar(mLocal,"Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Retorno incompativel !");
                    // }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.errar(mLocal, "Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.errar(mLocal, "Function  Extern " + mNome + "." + ASTCorrente.getNome() + " : Nao Encontrada !");

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

            //System.out.println("\t - Procurar Extern :  " + mIndex_Function.getNome());

            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                if (!mIndex_Function.getEstaResolvido()) {
                    mIndex_Function.resolverTipagem(BuscadorDeVariaveis.getRefers());
                }

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mEscopo, mArgumentos)) {

                    // System.out.println("\t - Executar Extern :  " + mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getPonteiro().mesmoValor(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }


                     //   mEscopo.getDebug().ListarActions();

                        mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);

                    } else {
                        mRunTime.errar(mLocal, "Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Retorno incompativel !");
                    }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.errar(mLocal, "Function Extern " + mNome + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.errar(mLocal, "Function  Extern " + mNome + "." + ASTCorrente.getNome() + " : Nao Encontrada !");

        }


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

            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !");

        }


    }


}

