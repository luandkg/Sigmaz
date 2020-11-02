package Sigmaz.S08_Executor.Escopos;

import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S08_Executor.Run_Refer;
import Sigmaz.S08_Executor.Runners.*;
import Sigmaz.S00_Utilitarios.AST;

import java.util.ArrayList;

public class Run_External {

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

    private Utilitario mUtilitario;

    public Run_External(RunTime eRunTime) {
        mRunTime = eRunTime;

        mNome = "";

        mPreparadorDeArgumentos = new Run_Arguments();

        mDesseExtern = new ArrayList<String>();
        dRefers = new ArrayList<String>();
        mLocal = "Run_External";

        mExterns = new ArrayList<String>();
        mImplicits = new ArrayList<String>();

        mUtilitario = new Utilitario();

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

        mEscopo = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
        mEscopo.setNome(mNome);


        mStructCorpo = ASTPai.getBranch("BODY");

        Run_Refer mRefer = new Run_Refer(mRunTime, mEscopo);
        mRefer.init(ASTPai.getBranch("REFERS"));


        for (AST ASTC : mStructCorpo.getASTS()) {

            if (ASTC.mesmoTipo("FUNCTION")) {

                if (mUtilitario.getModo(ASTC).contentEquals("EXPLICIT")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                } else if (mUtilitario.getModo(ASTC).contentEquals("IMPLICIT")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                }

            } else if (ASTC.mesmoTipo("ACTION")) {

                if (mUtilitario.getModo(ASTC).contentEquals("EXPLICIT")) {
                    mEscopo.guardarStruct(ASTC);
                    mEscopo.guardar(ASTC);

                } else if (mUtilitario.getModo(ASTC).contentEquals("IMPLICIT")) {
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

                if (mUtilitario.getModo(ASTC).contentEquals("EXPLICIT")) {
                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    //System.out.println("\t EXTERN -->> " + ASTC.getNome());

                    mDesseExtern.add(ASTC.getNome());

                    mEscopo.guardarStruct(ASTC);

                    mExterns.add(ASTC.getNome());

                } else if (mUtilitario.getModo(ASTC).contentEquals("IMPLICIT")) {

                    //System.out.println("\t IMPLICIT -->> " + ASTC.getNome());


                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);


                }


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                if (mUtilitario.getModo(ASTC).contentEquals("EXPLICIT")) {

                    Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mDesseExtern.add(ASTC.getNome());

                    //  System.out.println("\t EXTERN -->> " + ASTC.getNome());

                    mEscopo.guardarStruct(ASTC);

                    mExterns.add(ASTC.getNome());

                } else if (mUtilitario.getModo(ASTC).contentEquals("IMPLICIT")) {

                    //  System.out.println("\t IMPLICIT -->> " + ASTC.getNome());

                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);


                }


            }

        }


    }


    public Item init_ObjectExtern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.errar(mLocal, getNome() + "." + ASTCorrente.getNome() + " : Membro Explicit nao encontrado !");
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
                mRunTime.errar(mLocal, getNome() + "->" + ASTCorrente.getNome() + " :  Membro Explicit nao encontrado !");
            }

        } else {
            mRunTime.errar(mLocal, getNome() + "->" + ASTCorrente.getNome() + " :  Membro Explicit nao existente !");
        }


        return mRet;
    }


    public Item init_Function_Extern(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Item mRet = new Item("");

        if (mRunTime.getErros().size() > 0) {
            return mRet;
        }

        Run_AnyScope mRun_AnyScope = new Run_AnyScope(mRunTime);

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        if (mRunTime.getErros().size() > 0) {
            return mRet;
        }

        return mRun_AnyScope.executeComRetorno("Run_Explicit_Function", "Function Explicit", ASTCorrente.getNome(), ASTCorrente,mEscopo, mEscopo.getOO().getFunctions_Extern(), mArgumentos, eRetorne);


    }

    public void init_ActionFunction_Extern(AST ASTCorrente, Escopo BuscadorDeVariaveis ) {


        if (mRunTime.getErros().size() > 0) {
            return ;
        }

        Run_AnyScope mRun_AnyScope = new Run_AnyScope(mRunTime);

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        if (mRunTime.getErros().size() > 0) {
            return ;
        }

         mRun_AnyScope.executeSemRetorno("Run_Explicit_Action", "Action Explicit", ASTCorrente.getNome(),ASTCorrente, mEscopo, mEscopo.getOO().getActionsFunctions_Extern(), mArgumentos);



    }




}

