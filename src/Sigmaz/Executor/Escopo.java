package Sigmaz.Executor;

import Sigmaz.Compilador.AST_Value;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Escopo {

    private ArrayList<Item> mStacks;
    private RunTime mRunTime;
    private Escopo mEscopoAnterior;

    private EscopoDebug mDebug;
    private EscopoStack mEscopoStack;

    private ArrayList<AST> mGuardados;

    private boolean mCancel;
    private boolean mContinue;

    private String mNome;


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public Escopo(RunTime eRunTime, Escopo eEscopoAnterior) {
        mStacks = new ArrayList<>();
        mRunTime = eRunTime;
        mEscopoAnterior = eEscopoAnterior;
        mCancel = false;
        mContinue = false;

        mGuardados = new ArrayList<>();


        mRunTime = eRunTime;
        mDebug = new EscopoDebug(this);
        mEscopoStack = new EscopoStack(mRunTime, this);
    }

    public Escopo getEscopoAnterior() {
        return mEscopoAnterior;
    }


    public boolean run(AST ASTC) {

        boolean ret = true;

        System.out.println("RUNNING " + ASTC.getNome() + " :: " + ASTC.getValor());

        return ret;
    }


    public void guardar(AST eAST) {


        mGuardados.add(eAST);

    }

    public void definirVariavel(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        Run_Value mAST = new Run_Value(mRunTime, this);
        mAST.init(mValor, eAST.getValor());

        if (mAST.getIsNulo()) {
            criarDefinicaoNula(eAST.getNome(), eAST.getValor());
        } else if (mAST.getIsPrimitivo()) {


            if(eAST.getValor().contentEquals(mAST.getRetornoTipo())){
                criarDefinicao(eAST.getNome(), eAST.getValor(), mAST.getPrimitivo());
            }else{
                mRunTime.getErros().add("Retorno incompativel : " +mAST.getRetornoTipo() );
            }


        } else {
            mRunTime.getErros().add("AST_Value com problemas !");
        }


    }

    public void definirConstante(AST eAST) {


        AST mValor = eAST.getBranch("VALUE");

        Run_Value mAST = new Run_Value(mRunTime, this);
        mAST.init(mValor, eAST.getValor());

        if (mAST.getIsNulo()) {
            criarConstanteNula(eAST.getNome(), eAST.getValor());
        } else if (mAST.getIsPrimitivo()) {

            if(eAST.getValor().contentEquals(mAST.getRetornoTipo())){
                criarConstante(eAST.getNome(), eAST.getValor(), mAST.getPrimitivo());
            }else{
                mRunTime.getErros().add("Retorno incompativel : " +mAST.getRetornoTipo() );
            }

        } else {
            mRunTime.getErros().add("AST_Value com problemas !");
        }


    }


    public void ListarGlobal() {
        mDebug.ListarGlobal();
    }

    public void ListarActions() {
        mDebug.ListarActions();
    }

    public void ListarFunctions() {
        mDebug.ListarFunctions();
    }

    public void ListarDefines() {
        mDebug.ListarDefines();
    }

    public void ListarConstants() {
        mDebug.ListarConstants();
    }

    public void ListarAll() {
        mDebug.ListarAll();
    }

    public ArrayList<Item> getStacks() {
        return mStacks;
    }

    public ArrayList<Item> getStacksAll() {

        ArrayList<Item> gc = new ArrayList<Item>();


        if (this.mEscopoAnterior != null) {
            for (Item fAST : mEscopoAnterior.getStacksAll()) {
                gc.add(fAST);
            }
        }

        for (Item fAST : mStacks) {
            gc.add(fAST);
        }

        return gc;
    }


    public ArrayList<AST> getGuardados() {
        return mGuardados;
    }

    public ArrayList<AST> getGuardadosCompleto() {

        ArrayList<AST> gc = new ArrayList<AST>();

        for (AST fAST : mGuardados) {
            gc.add(fAST);
        }

        if (this.mEscopoAnterior != null) {
            for (AST fAST : mEscopoAnterior.getGuardadosCompleto()) {
                gc.add(fAST);
            }
        }

        return gc;
    }

    public void setCancel(boolean eCancel) {
        mCancel = eCancel;
    }

    public boolean getCancel() {
        return mCancel;
    }

    public void setContinue(boolean eContinue) {
        mContinue = eContinue;
    }

    public boolean getContinue() {
        return mContinue;
    }

    public Item BuscarAnterior(String eNome) {
        return mEscopoStack.BuscarAnterior(eNome);
    }
    public void criarDefinicaoNula(String eNome, String eTipo) {
         mEscopoStack.criarDefinicaoNula(eNome,eTipo);
    }
    public void criarConstanteNula(String eNome, String eTipo) {
        mEscopoStack.criarConstanteNula(eNome,eTipo);

    }

    public void criarDefinicao(String eNome, String eTipo, String eValor) {
        mEscopoStack.criarDefinicao(eNome,eTipo,eValor);

    }
    public void criarConstante(String eNome, String eTipo, String eValor) {
        mEscopoStack.criarConstante(eNome,eTipo,eValor);

    }
    public void setDefinido(String eNome, String eValor) {
        mEscopoStack.setDefinido(eNome,eValor);
    }
    public String getDefinidoTipo(String eNome) {
      return  mEscopoStack.getDefinidoTipo(eNome);
    }
    public void anularDefinido(String eNome) {
        mEscopoStack.anularDefinido(eNome);
    }
    public String getDefinido(String eNome) {
        return  mEscopoStack.getDefinido(eNome);
    }
    public String getDefinidoNum(String eNome) {
        return  mEscopoStack.getDefinidoNum(eNome);
    }

}
