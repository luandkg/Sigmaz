package Sigmaz.S08_Executor;

import Sigmaz.S08_Executor.Debuggers.EscopoDebug;
import Sigmaz.S08_Executor.Debuggers.Local;
import Sigmaz.S08_Executor.Debuggers.Regressive;
import Sigmaz.S08_Executor.Indexador.Index_Action;
import Sigmaz.S08_Executor.Indexador.Index_Function;
import Sigmaz.S08_Executor.Escopos.Run_External;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Escopo {

    private ArrayList<Item> mStacks;


    private RunTime mRunTime;

    private boolean mTemAnterior;
    private Escopo mEscopoAnterior;

    private EscopoStack mEscopoStack;
    private Refers mRefers;

    private EscopoDebug mDebug;
    private Regressive mRegressive;
    private Local mLocalDebug;


    private boolean mCancelar;
    private boolean mContinuar;
    private boolean mRetornado;
    private Item mItem;

    private String mNome;


    private OO mOO;
    private OO mAO;

    private ArrayList<Run_External> mExternos;


    // FUNCAO LOCAL
    private boolean mTemLocal = false;
    private AST mLocal;


    public Escopo(String eNome, RunTime eRunTime) {


        mNome = eNome;
        mTemAnterior = false;
        mEscopoAnterior = null;

        mRunTime = eRunTime;


        mStacks = new ArrayList<>();


        mCancelar = false;
        mContinuar = false;
        mRetornado = false;

        mRefers = new Refers(this);

        mAO = new OO(this, mRunTime);
        mOO = new OO(this, mRunTime);

        mRunTime = eRunTime;
        mEscopoStack = new EscopoStack(mRunTime, this);

        mExternos = new ArrayList<Run_External>();


        mDebug = new EscopoDebug(this);
        mRegressive = new Regressive(this);
        mLocalDebug = new Local(this);

    }


    public Escopo(RunTime eRunTime, Escopo eEscopoAnterior) {


        mStacks = new ArrayList<>();

        mRunTime = eRunTime;
        mEscopoAnterior = eEscopoAnterior;
        mTemAnterior = true;

        mCancelar = false;
        mContinuar = false;
        mRetornado = false;


        mAO = new OO(this, mRunTime);
        mOO = new OO(this, mRunTime);


        mRunTime = eRunTime;
        mEscopoStack = new EscopoStack(mRunTime, this);

        mRefers = new Refers(this);


        mExternos = new ArrayList<Run_External>();


        mDebug = new EscopoDebug(this);
        mRegressive = new Regressive(this);
        mLocalDebug = new Local(this);

    }


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }


    public RunTime getRunTime() {
        return mRunTime;
    }

    public void setAnterior(Escopo aEscopo) {
        mEscopoAnterior = aEscopo;
    }

    public Escopo getEscopoAnterior() {
        return mEscopoAnterior;
    }

    public EscopoDebug getDebug() {
        return mDebug;
    }

    public Regressive getRegressiveDebug() {
        return mRegressive;
    }

    public Local getLocalDebug() {
        return mLocalDebug;
    }


    public OO getOO() {
        return mOO;
    }

    public OO getAO() {
        return mAO;
    }


    public void limpar() {

        mAO.limpar();

    }

    public void adicionarRefer(String eRefer) {
        mRefers.adicionarRefer(eRefer);
    }

    public void adicionarReferDe(Escopo eRefer) {
        mRefers.adicionarReferDe(eRefer);
    }

    public ArrayList<String> getRefers() {
        return mRefers.getRefers();
    }

    public ArrayList<String> getRefersOcultas() {
        return mRefers.getRefersOcultas();
    }

    public void externalizar(String eNomeCompleto) {

        for (Run_External eAST : mRunTime.getExternals().getExterns()) {

            if (eAST.getNomeCompleto().contentEquals(eNomeCompleto)) {
                //   System.out.println("\t - Receber Externo Geral : " + eAST.getNomeCompleto());
                mExternos.add(eAST);
            }

        }

    }

    public void externalizarDireto(Run_External eAST) {
        mExternos.add(eAST);
    }


    public ArrayList<Run_External> getExtern() {
        ArrayList<Run_External> mRet = new ArrayList<Run_External>();
        for (Run_External mRE : mExternos) {
            mRet.add(mRE);
        }

        if (mEscopoAnterior != null) {
            for (Run_External mRE : mEscopoAnterior.getExtern()) {
                mRet.add(mRE);
            }
        }

        return mRet;
    }


    public void guardarStruct(AST eAST) {

        mOO.guardar(eAST);

    }

    public void guardar(AST eAST) {

        mAO.guardar(eAST);

    }


    public ArrayList<Index_Function> getFunctionsCompleto() {
        return mAO.getFunctionsCompleto();
    }

    public ArrayList<Index_Action> getActionsCompleto() {
        return mAO.getActionsCompleto();
    }

    public ArrayList<Index_Action> getActionFunctionsCompleto() {
        return mAO.getActionFunctionsCompleto();
    }

    public ArrayList<Index_Function> getDirectorsCompleto() {
        return mAO.getDirectorsCompleto();
    }

    public ArrayList<Index_Function> getOperationsCompleto() {
        return mAO.getOperationsCompleto();
    }

    public ArrayList<Index_Function> getMarcadoresCompleto() {
        return mAO.getMarcadoresCompleto();
    }


    public ArrayList<Index_Action> getInitsCompleto() {
        return mAO.getInitsCompleto();
    }

    public ArrayList<Item> getStacks() {
        return mStacks;
    }


    public void criarItem(String eNome, Item eItem) {
        mEscopoStack.criarItem(eNome, eItem);
    }


    public void criarExternRefered(String eNome, String mTipagem, String eStruct, String eCampo) {
        mEscopoStack.criarExternRefered(eNome, mTipagem, eStruct, eCampo);
    }


    public void criarExternRefered_Const(String eNome, String mTipagem, String eStruct, String eCampo) {
        mEscopoStack.criarExternRefered_Const(eNome, mTipagem, eStruct, eCampo);
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


    public ArrayList<AST> getGuardadosCompleto() {

        ArrayList<AST> mRet = mAO.getGuardadosCompleto();

        if (mTemLocal) {
            mRet.add(mLocal);
        }

        return mRet;
    }


    public ArrayList<AST> getLocais() {

        ArrayList<AST> mRet = new ArrayList<AST>();

        if (mEscopoAnterior != null) {
            mRet.addAll(mEscopoAnterior.getLocais());

        }

        if (mTemLocal) {
            mRet.add(mLocal);
        }

        return mRet;
    }

    public void setCancelar(boolean eCancelar) {
        mCancelar = eCancelar;
    }

    public void setContinuar(boolean eContinuar) {
        mContinuar = eContinuar;
    }

    public void setRetornado(boolean eRetornado) {
        mRetornado = eRetornado;
    }


    public void retorne(Item eItem) {
        mRetornado = true;
        mItem = eItem;
    }

    public boolean getCancelar() {
        return mCancelar;
    }

    public Item getRetorno() {
        return mItem;
    }


    public boolean getContinuar() {
        return mContinuar;
    }

    public boolean getRetornado() {
        return mRetornado;
    }


    public void setContinue(boolean eContinue) {
        mContinuar = eContinue;
    }

    public boolean getContinue() {
        return mContinuar;
    }

    public Item BuscarAnterior(String eNome) {
        return mEscopoStack.BuscarAnterior(eNome);
    }

    // MUTAVEL

    public void criarMutavelNula(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mutable, true, "", false);
    }

    public void criarMutavelPrimitivo(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mutable, false, eValor, false);
    }

    public void criarMutavelStruct(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mutable, false, eValor, true);
    }

    // PRIMITIVO

    public void criarDefinicaoNula(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, true, "", false);
    }

    public void criarConstanteNula(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mockiz, true, "", false);
    }

    public Item criarDefinicao(String eNome, String eTipo, String eValor) {
        return mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, false, eValor, false);
    }

    public void criarConstante(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mockiz, false, eValor, false);
    }


    // STRUCT

    public void criarDefinicaoStructNula(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, true, "", true);
    }

    public void criarDefinicaoStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, false, eRef, true);
    }

    public void criarConstanteStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mockiz, false, eRef, true);
    }

    public void criarConstanteStructNula(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Mockiz, true, "", true);
    }


    // PARAMETRO

    public void criarParametro(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, false, eValor, false);
    }

    public void criarParametroNulo(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, true, "", false);
    }

    public void criarParametroStructNula(String eNome, String eTipo) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, true, "", true);
    }

    public void criarParametroStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.alocar(eNome, eTipo, TipoStack.Define, false, eRef, true);
    }


    public void setDefinido(String eNome, String eValor) {
        mEscopoStack.setDefinido(eNome, eValor);
    }


    public Item getItem(String eNome) {
        return mEscopoStack.getItem(eNome);
    }


    public void definirLocal(AST eLocal) {

        mLocal = eLocal;
        mTemLocal = true;

    }

    public boolean temLocal() {
        return mTemLocal;
    }

    public AST getLocal() {
        return mLocal;
    }

    public String getCaminho() {
        if (mTemAnterior) {
            return mEscopoAnterior.getCaminho()+ " -> " + this.getNome();
        } else {
            return this.getNome();
        }
    }

}
