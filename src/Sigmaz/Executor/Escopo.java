package Sigmaz.Executor;

import Sigmaz.Executor.Debuggers.EscopoDebug;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Runners.Run_Context;
import Sigmaz.Executor.Runners.Run_Extern;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Escopo {

    private ArrayList<Item> mParam;
    private ArrayList<Item> mStacks;


    private RunTime mRunTime;
    private Escopo mEscopoAnterior;

    private EscopoDebug mDebug;
    private EscopoStack mEscopoStack;


    private boolean mCancelar;
    private boolean mContinuar;

    private String mNome;
    private boolean mEstrutura;


    private OO mOO;
    private OO mAO;

    private ArrayList<Run_Extern> mExternos;
    private ArrayList<String> mRefers;


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


    public boolean possuiStruct(String eNome) {
        boolean ret = false;

        for (AST eAST : getStructs()) {
            if (eAST.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public Escopo(RunTime eRunTime, Escopo eEscopoAnterior) {


        mParam = new ArrayList<>();
        mStacks = new ArrayList<>();

        mRunTime = eRunTime;
        mEscopoAnterior = eEscopoAnterior;
        mCancelar = false;
        mContinuar = false;


        mAO = new OO(this, mRunTime);

        mOO = new OO(this, mRunTime);

        mEstrutura = false;

        mRunTime = eRunTime;
        mDebug = new EscopoDebug(this);
        mEscopoStack = new EscopoStack(mRunTime, this);
        mRefers = new ArrayList<String>();

        mExternos = new ArrayList<Run_Extern>();

    }


    public Escopo getEscopoAnterior() {
        return mEscopoAnterior;
    }

    public EscopoDebug getDebug() {
        return mDebug;
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

    public void limpar(){

        mAO.limpar();

    }


    public void externalizar(String eNomeCompleto) {

        for (Run_Extern eAST : mRunTime.getExtern()) {

            if (eAST.getNomeCompleto().contentEquals(eNomeCompleto)) {
             //   System.out.println("\t - Receber Externo Geral : " + eAST.getNomeCompleto());
                mExternos.add(eAST);
            }

        }

    }

    public void externalizarDireto(Run_Extern eAST) {
        mExternos.add(eAST);
    }

    public void externalizarStructGeral(String eExterno) {

        for (Run_Extern eAST : mRunTime.getExtern()) {

            if (eAST.getNome().contentEquals(eExterno)) {
                //   System.out.println("\t - Receber Externo Geral : " + eAST.getNomeCompleto());
                mExternos.add(eAST);
            }


        }

    }

    public ArrayList<Run_Extern> getExtern() {
        ArrayList<Run_Extern> mRet = new ArrayList<Run_Extern>();
        for (Run_Extern mRE : mExternos) {
            mRet.add(mRE);
        }

        if (mEscopoAnterior != null) {
            for (Run_Extern mRE : mEscopoAnterior.getExtern()) {
                mRet.add(mRE);
            }
        }

        return mRet;
    }




    public void guardarStruct(AST eAST,ArrayList<String> dRefers) {

        mOO.guardar(eAST,dRefers);

    }
    public void guardarStruct(AST eAST) {

        mOO.guardar(eAST);

    }

    public void guardar(AST eAST) {

        mAO.guardar(eAST);

    }

    public void guardar(AST eAST,ArrayList<String> dRefers) {

        mAO.guardar(eAST,dRefers);

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

    public ArrayList<AST> getCastsCompleto() {
        return mAO.getCastsCompleto();
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
                this.criarParametroStructNulo(eNome, eItem.getTipo());
            } else {
                this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor());
            }


        } else {

            if (eItem.getNulo()) {
                this.criarParametroNulo(eNome, eItem.getTipo());
            } else {
                this.criarParametro(eNome, eItem.getTipo(), eItem.getValor());
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
                    this.criarParametroStructNulo(eNome, eItem.getTipo());
                } else {
                    this.criarParametroStructNulo(eNome, eItem.getTipo());
                }
            } else {
                if (eItem.getIsReferenciavel()) {
                    this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor());
                } else {
                    this.criarParametroStruct(eNome, eItem.getTipo(), eItem.getValor());
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
                    this.criarParametro(eNome, eItem.getTipo(), eItem.getValor());
                } else {
                    this.criarParametro(eNome, eItem.getTipo(), eItem.getValor());
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

    public void criarItem(String eNome, Item eItem) {

        Item Novo = new Item(eNome);
        Novo.setModo(eItem.getModo());
        Novo.setTipo(eItem.getTipo());
        Novo.setNulo(eItem.getNulo());
        Novo.setPrimitivo(eItem.getPrimitivo());
        Novo.setIsEstrutura(eItem.getIsEstrutura());
        Novo.setValor(eItem.getValor());
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

    public Escopo getEstruturador() {

        Escopo ret = null;

        if (this.getEstrutura() == true) {
            ret = this;
        } else {
            if (this.mEscopoAnterior != null) {
                ret = mEscopoAnterior.getEstruturador();
            }
        }


        return ret;
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

    public String getNomeStruct() {
        if (this.getNome() == null) {
            if (this.mEscopoAnterior != null) {
                return this.mEscopoAnterior.getNome();
            } else {
                return "";
            }
        } else {
            return this.getNome();
        }
    }

    public ArrayList<AST> getGuardadosCompleto() {
        return mAO.getGuardadosCompleto();
    }


    public ArrayList<AST> getStruct() {
        return mOO.getStruct();
    }

    public AST getCast(String eNome) {

        AST gc = null;

        for (AST mCast : getCastsCompleto()) {
            if (mCast.mesmoNome(eNome)) {
                gc = mCast;
                break;
            }
        }

        return gc;
    }

    public ArrayList<AST> getStages() {
        return mAO.getStages();
    }

    public ArrayList<AST> getStructs() {
        return mAO.getStructs();
    }

    public ArrayList<AST> getTypes() {
        return mAO.getTypes();
    }


    public void setCancelar(boolean eCancelar) {
        mCancelar = eCancelar;
    }

    public void setContinuar(boolean eContinuar) {

        mContinuar = eContinuar;
    }

    public boolean getCancelar() {
        return mCancelar;
    }

    public boolean getContinuar() {
        return mContinuar;
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

    public void criarDefinicaoNula(String eNome, String eTipo) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, false, false, "");
    }

    public void criarConstanteNula(String eNome, String eTipo) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, true, false, "");

    }

    public void criarParametro(String eNome, String eTipo, String eValor) {
        mEscopoStack.criarParametro(eNome, eTipo, eValor);
    }

    public void criarParametroNulo(String eNome, String eTipo) {
        mEscopoStack.criarParametroNulo(eNome, eTipo);
    }

    public void criarParametroStructNulo(String eNome, String eTipo) {
        mEscopoStack.criarParametroStructNulo(eNome, eTipo);
    }

    public void criarParametroStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.criarParametroStruct(eNome, eTipo, eRef);
    }

    public void criarDefinicao(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, false, true, eValor);
    }

    public void criarConstante(String eNome, String eTipo, String eValor) {
        mEscopoStack.alocarPrimitivo(eNome, eTipo, true, true, eValor);

    }


    public void criarDefinicaoStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.criarDefinicaoStruct(eNome, eTipo, eRef);
    }

    public void criarConstanteStruct(String eNome, String eTipo, String eRef) {
        mEscopoStack.criarConstanteStruct(eNome, eTipo, eRef);
    }

    public void setDefinido(String eNome, String eValor) {
        mEscopoStack.setDefinido(eNome, eValor);
    }

    public void setDefinidoStruct(String eNome, String eValor) {
        mEscopoStack.setDefinidoStruct(eNome, eValor);
    }

    public String getDefinidoTipo(String eNome) {
        return mEscopoStack.getDefinidoTipo(eNome);
    }

    public boolean getDefinidoPrimitivo(String eNome) {
        return mEscopoStack.getDefinidoPrimitivo(eNome);
    }

    public void anularDefinido(String eNome) {
        mEscopoStack.anularDefinido(eNome);
    }

    public String getDefinido(String eNome) {
        return mEscopoStack.getDefinido(eNome);
    }

    public String getDefinidoNum(String eNome) {
        return mEscopoStack.getDefinidoNum(eNome);
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


    public boolean existeCast(String eNome) {
        boolean ret = false;

        for (AST fAST : this.getCastsCompleto()) {
            if (fAST.mesmoNome(eNome)) {
                ret = true;
            }
        }

        return ret;
    }



}
