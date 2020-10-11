package Sigmaz.S07_Executor;

import Sigmaz.S07_Executor.Debuggers.EscopoDebug;
import Sigmaz.S07_Executor.Debuggers.Local;
import Sigmaz.S07_Executor.Debuggers.Regressive;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Escopos.Run_Explicit;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Escopo {

    private ArrayList<Item> mParam;
    private ArrayList<Item> mStacks;


    private RunTime mRunTime;
    private Escopo mEscopoAnterior;

    private EscopoStack mEscopoStack;
    private EscopoDebug mDebug;
    private Regressive mRegressive;
    private Local mLocalDebug;


    private boolean mCancelar;
    private boolean mContinuar;
    private boolean mRetornado;
    private Item mItem;

    private String mNome;
    private boolean mEstrutura;


    private OO mOO;
    private OO mAO;

    private ArrayList<Run_Explicit> mExternos;
    private ArrayList<String> mRefers;
    private ArrayList<String> mRefersOcultas;

    private int mAutoID;
    private int mFunctorID;


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public void setEstrutura(boolean eEstrutura) {
        mEstrutura = eEstrutura;
    }

    public boolean getEstrutura() {
        return mEstrutura;
    }


    public RunTime getRunTime() {
        return mRunTime;
    }


    public Escopo(RunTime eRunTime, Escopo eEscopoAnterior) {


        mParam = new ArrayList<>();
        mStacks = new ArrayList<>();

        mRunTime = eRunTime;
        mEscopoAnterior = eEscopoAnterior;
        mCancelar = false;
        mContinuar = false;
        mRetornado = false;


        mAO = new OO(this, mRunTime);

        mOO = new OO(this, mRunTime);

        mEstrutura = false;

        mRunTime = eRunTime;
        mEscopoStack = new EscopoStack(mRunTime, this);
        mRefers = new ArrayList<String>();
        mRefersOcultas = new ArrayList<String>();

        mExternos = new ArrayList<Run_Explicit>();

        mAutoID = 0;
        mFunctorID = 0;

        mDebug = new EscopoDebug(this);
        mRegressive = new Regressive(this);
        mLocalDebug = new Local(this);

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

    public void adicionarRefer(String eRefer) {
        mRefers.add(eRefer);
    }

    public ArrayList<String> getRefers() {

        ArrayList<String> mRet = new ArrayList<String>();
        mRet.addAll(mRefers);

        if (this.mEscopoAnterior != null) {
            for (String r : mEscopoAnterior.getRefers()) {
                if (!mRet.contains(r)) {
                    mRet.add(r);
                }
            }
        }


        return mRet;
    }

    public void adicionarReferOculto(String eRefer) {
        mRefersOcultas.add(eRefer);
    }

    public ArrayList<String> getRefersOcultas() {

        ArrayList<String> mRet = new ArrayList<String>();
        mRet.addAll(mRefers);
        mRet.addAll(mRefersOcultas);

        if (this.mEscopoAnterior != null) {
            for (String r : mEscopoAnterior.getRefersOcultas()) {
                if (!mRet.contains(r)) {
                    mRet.add(r);
                }
            }
        }


        return mRet;
    }


    public void limpar() {

        mAO.limpar();

    }


    public void externalizar(String eNomeCompleto) {

        for (Run_Explicit eAST : mRunTime.getExternals().getExterns()) {

            if (eAST.getNomeCompleto().contentEquals(eNomeCompleto)) {
                //   System.out.println("\t - Receber Externo Geral : " + eAST.getNomeCompleto());
                mExternos.add(eAST);
            }

        }

    }

    public void externalizarDireto(Run_Explicit eAST) {
        mExternos.add(eAST);
    }


    public ArrayList<Run_Explicit> getExtern() {
        ArrayList<Run_Explicit> mRet = new ArrayList<Run_Explicit>();
        for (Run_Explicit mRE : mExternos) {
            mRet.add(mRE);
        }

        if (mEscopoAnterior != null) {
            for (Run_Explicit mRE : mEscopoAnterior.getExtern()) {
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

    public void indexar(AST ASTCGlobal) {


        for (AST ASTC : ASTCGlobal.getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                this.guardar(ASTC);

            } else if (ASTC.mesmoTipo("ACTION")) {
                this.guardar(ASTC);

            } else if (ASTC.mesmoTipo("OPERATOR")) {
                this.guardar(ASTC);

            } else if (ASTC.mesmoTipo("DIRECTOR")) {
                this.guardar(ASTC);

            } else if (ASTC.mesmoTipo("CAST")) {
                this.guardar(ASTC);

            } else if (ASTC.mesmoTipo("STAGES")) {

                this.guardar(ASTC);


            } else if (ASTC.mesmoTipo("STRUCT")) {

                this.guardar(ASTC);


            } else if (ASTC.mesmoTipo("TYPE")) {

                this.guardar(ASTC);


            } else if (ASTC.mesmoTipo("PROTOTYPE_AUTO")) {

                this.guardar(ASTC);
            } else if (ASTC.mesmoTipo("PROTOTYPE_FUNCTOR")) {

                this.guardar(ASTC);

            } else if (ASTC.mesmoTipo("DEFAULT")) {

                this.guardar(ASTC);
            } else if (ASTC.mesmoTipo("MARK")) {

                this.guardar(ASTC);

            }

        }

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

    public ArrayList<Item> getParametros() {
        return mParam;
    }

    public void passarParametroByValue(String eNome, Item eItem) {

        if (eItem.getIsEstrutura()) {

            if (eItem.getNulo()) {
                this.criarParametroStructNula(eNome, eItem.getTipo());
            } else {
                this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
            }


        } else {

            if (eItem.getNulo()) {
                this.criarParametroNulo(eNome, eItem.getTipo());
            } else {
                this.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
            }


        }

    }

    public void passarParametroByRef(String eNome, Item eItem) {

//        System.out.println("Ref :: " + eNome + " de " + eItem.getReferencia().getNome());
        if (!eItem.getIsReferenciavel()) {
            mRunTime.getErros().add("Nao foi possivel referenciar : " + eNome);
            return;
        }

        if (eItem.getIsEstrutura()) {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroStructNula(eNome, eItem.getTipo());
                } else {
                    this.criarParametroStructNula(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                } else {
                    this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                }
            }

        } else {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroNulo(eNome, eItem.getTipo());
                } else {
                    this.criarParametroNulo(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                } else {
                    this.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                }
            }


        }

        this.referenciar(eNome, eItem.getReferencia());

    }

    public void referenciar(String eNome, Item eItem) {

        boolean enc = false;

        for (Item i : getParametros()) {
            if (i.getNome().contentEquals(eNome)) {
                enc = true;

                i.setReferencia(eItem);
                i.setIsReferenciavel(true);

                break;
            }
        }

        if (!enc) {
            mRunTime.getErros().add("Nao foi possivel referenciar : " + eNome);
        }
    }

    public void passarParametroByRefObrigatorio(String eNome, Item eItem) {

//        System.out.println("Ref :: " + eNome + " de " + eItem.getReferencia().getNome());

        if (eItem.getIsEstrutura()) {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroStructNula(eNome, eItem.getTipo());
                } else {
                    this.criarParametroStructNula(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                } else {
                    this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                }
            }

        } else {

            if (eItem.getNulo()) {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroNulo(eNome, eItem.getTipo());
                } else {
                    this.criarParametroNulo(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                } else {
                    this.criarParametro(eNome, eItem.getTipo(), eItem.getValor(mRunTime, this));
                }
            }


        }

        this.referenciar(eNome, eItem.getReferencia());

    }


    public void criarItem(String eNome, Item eItem) {

        Item Novo = new Item(eNome);
        Novo.setModo(eItem.getModo());
        Novo.setTipo(eItem.getTipo());
        Novo.setNulo(eItem.getNulo());
        Novo.setPrimitivo(eItem.getPrimitivo());
        Novo.setIsEstrutura(eItem.getIsEstrutura());
        Novo.setValor(eItem.getValor(mRunTime, this), mRunTime, this);
        mStacks.add(Novo);


    }


    public void criarExternRefered(String eNome, String mTipagem, String eStruct, String eCampo) {

        if (mEscopoStack.existeAqui(eNome, mStacks)) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        }

        Item Novo = new Item(eNome);
        Novo.setModo(0);
        Novo.setTipo(mTipagem);
        Novo.setNulo(false);
        Novo.setPrimitivo(false);
        Novo.setIsEstrutura(false);

        Novo.setRefer(eStruct, eCampo);

        mStacks.add(Novo);


    }


    public void criarExternRefered_Const(String eNome, String mTipagem, String eStruct, String eCampo) {

        if (mEscopoStack.existeAqui(eNome, mStacks)) {
            mRunTime.getErros().add("Definicao Duplicada : " + eNome);
        }

        Item Novo = new Item(eNome);
        Novo.setModo(0);
        Novo.setTipo(mTipagem);
        Novo.setNulo(false);
        Novo.setPrimitivo(false);
        Novo.setIsEstrutura(false);

        Novo.setReferConst(eStruct, eCampo);

        mStacks.add(Novo);


    }


    public void criarImplicitRefered(String eNome, String mTipagem, String eStruct, String eCampo) {

        Item Novo = new Item(eNome);
        Novo.setModo(0);
        Novo.setTipo(mTipagem);
        Novo.setNulo(false);
        Novo.setPrimitivo(false);
        Novo.setIsEstrutura(false);

        Novo.setImplicit(eStruct, eCampo);

        mStacks.add(Novo);


    }

    public void criarImplicitRefered_Const(String eNome, String mTipagem, String eStruct, String eCampo) {

        Item Novo = new Item(eNome);
        Novo.setModo(0);
        Novo.setTipo(mTipagem);
        Novo.setNulo(false);
        Novo.setPrimitivo(false);
        Novo.setIsEstrutura(false);

        Novo.setImplicitConst(eStruct, eCampo);

        mStacks.add(Novo);


    }


    public void alterarTipo(String eNome, String eTipoAtual, String eTipoNovo) {
        mEscopoStack.alterarTipo(eNome, eTipoAtual, eTipoNovo);
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


    public int getContagem() {
        int i = 0;

        if (this.mEscopoAnterior != null) {
            i += this.mEscopoAnterior.getContagem();
        } else {
            i = 1;
        }

        return i;
    }

    public String getCaminho() {
        String i = "";

        if (this.mEscopoAnterior != null) {
            i = this.mEscopoAnterior.getCaminho() + "." + i;
        } else {
            i = this.getNome();
        }

        return i;
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
        mEscopoStack.alocarMutavelPrimitivo(eNome, eTipo, false, "");
    }

    public void criarMutavelPrimitivo(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocarMutavelPrimitivo(eNome, eTipo, true, eValor);
    }

    public void criarMutavelStruct(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocarMutavelStruct(eNome, eTipo, true, eValor);
    }

    // PRIMITIVO

    public void criarDefinicaoNula(String eNome, String eTipo) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, false, false, "");
    }

    public void criarConstanteNula(String eNome, String eTipo) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, true, false, "");

    }

    public Item criarDefinicao(String eNome, String eTipo, String eValor) {
        return mEscopoStack.alocarPrimitivo(eNome, eTipo, false, true, eValor);
    }

    public void criarConstante(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, true, true, eValor);

    }

    public void criarParametro(String eNome, String eTipo, String eValor) {
        mEscopoStack.criarParametro(eNome, eTipo, eValor);
    }


    // STRUCT

    public void criarDefinicaoStructNula(String eNome, String eTipo) {
        mEscopoStack.criarDefinicaoStructNula(eNome, eTipo);
    }

    public void criarDefinicaoStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.criarDefinicaoStruct(eNome, eTipo, eRef);
    }

    public void criarConstanteStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.criarConstanteStruct(eNome, eTipo, eRef);
    }

    public void criarConstanteStructNula(String eNome, String eTipo) {
        mEscopoStack.criarConstanteStructNula(eNome, eTipo);
    }


    // PARAMETRO

    public void criarParametroNulo(String eNome, String eTipo) {
        mEscopoStack.criarParametroNulo(eNome, eTipo);
    }

    public void criarParametroStructNula(String eNome, String eTipo) {
        mEscopoStack.criarParametroStructNula(eNome, eTipo);
    }

    public void criarParametroStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.criarParametroStruct(eNome, eTipo, eRef);
    }


    public void setDefinido(String eNome, String eValor) {
        mEscopoStack.setDefinido(eNome, eValor);
    }


    public String getDefinidoTipo(String eNome) {
        return mEscopoStack.getDefinidoTipo(eNome);
    }

    public boolean getDefinidoPrimitivo(String eNome) {
        return mEscopoStack.getDefinidoPrimitivo(eNome);
    }


    public String getDefinido(String eNome) {
        return mEscopoStack.getDefinido(eNome);
    }


    public String getDefinidoConteudo(String eNome) {
        return mEscopoStack.getDefinidoConteudo(eNome);
    }

    public boolean getDefinidoNulo(String eNome) {
        return mEscopoStack.getDefinidoNulo(eNome);
    }

    public Item getItem(String eNome) {
        return mEscopoStack.getItem(eNome);
    }


    // FUNCAO LOCAL
    private boolean mTemLocal = false;
    private AST mLocal;

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


}
