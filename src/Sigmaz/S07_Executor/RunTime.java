package Sigmaz.S07_Executor;

import Sigmaz.S00_Utilitarios.Chronos;
import Sigmaz.S06_Montador.Montador;
import Sigmaz.S07_Executor.Debuggers.Global;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Runners.*;

import java.io.File;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class RunTime {

    private ArrayList<AST> mCabecalho;
    private ArrayList<AST> mASTS;


    private ArrayList<String> mErros;
    private ArrayList<String> mMensagens;

    private Escopo mEscopoGlobal;
    private Global mGlobal;

    private ArrayList<AST> mGlobalPackages;


    private String mLocal;
    private String mLocal_Bibliotecas;

    private Heap mHeap;
    private Externals mExternals;

    private Processador mProcessador;

    private boolean mMostrar_Mensagens;

    private float mTempo_Leitura;
    private float mTempo_Processamento;
    private float mTempo_Organizacao;
    private float mTempo_Execucao;


    public float getTempo_Leitura() {
        return mTempo_Leitura;
    }

    public float getTempo_Processamento() {
        return mTempo_Processamento;
    }


    public float getTempo_Organizacao() {
        return mTempo_Organizacao;
    }

    public float getTempo_Execucao() {
        return mTempo_Execucao;
    }

    public RunTime() {

        mASTS = new ArrayList<>();
        mCabecalho = new ArrayList<>();


        mErros = new ArrayList<>();
        mMensagens = new ArrayList<String>();

        mGlobalPackages = new ArrayList<AST>();


        mEscopoGlobal = null;
        mLocal = "";

        mMostrar_Mensagens = true;

        mHeap = new Heap(this);
        mExternals = new Externals(this);

        mProcessador = new Processador(this);

        mLocal_Bibliotecas = "";
        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;
        mTempo_Execucao = 0.0F;

    }

    public void limpar() {

        mErros.clear();
        mMensagens.clear();

        mGlobalPackages.clear();

        mHeap.limpar();
        mExternals.limpar();

        mProcessador = new Processador(this);
        mLocal_Bibliotecas = "";
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;
        mTempo_Execucao = 0.0F;
    }


    public void mostrar() {
        mMostrar_Mensagens = true;
    }

    public void ocultar() {
        mMostrar_Mensagens = false;
    }

    public boolean getVisibilidade() {
        return mMostrar_Mensagens;
    }


    public Heap getHeap() {
        return mHeap;
    }

    public Externals getExternals() {
        return mExternals;
    }


    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public ArrayList<String> getMensagens() {
        return mMensagens;
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public Processador getProcessador() {
        return mProcessador;
    }

    public void init(String eArquivo) {


        File arq = new File(eArquivo);
        mLocal = arq.getParent() + "/";

        limpar();

        Montador mMontador = new Montador();

        OMLRun mOMLRun = mMontador.Decompilar(eArquivo);

        mCabecalho = mOMLRun.getCabecalho();
        mASTS = mOMLRun.getCodigo();


        mTempo_Leitura = mMontador.getTempo_Leitura();
        mTempo_Processamento = mMontador.getTempo_Processamento();
        mTempo_Organizacao = mMontador.getTempo_Organizacao();

        if (mMontador.getMensagens().size() > 0) {
            for (String e : mMontador.getMensagens()) {
                mMensagens.add(e);
            }
        }

        if (mMontador.getErros().size() > 0) {
            for (String e : mMontador.getErros()) {
                mErros.add(e);
            }
        }

    }

    public String getArvoreDeInstrucoes() {

        String ret = "";

        for (AST eAST : getASTS()) {

            ret += eAST.ImprimirArvoreDeInstrucoes();
        }

        return ret;

    }


    public Escopo getEscopoGlobal() {
        return mEscopoGlobal;
    }


    public AST getSigmaz() {

        AST mRet = null;

        for (AST ASTCGlobal : mASTS) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {
                mRet = ASTCGlobal;
            }
        }
        return mRet;
    }


    public void mapearPacotes(AST ASTCGlobal) {

        mGlobalPackages.clear();
        for (AST ASTC : ASTCGlobal.getASTS()) {


            if (ASTC.mesmoTipo("PACKAGE")) {

                mGlobalPackages.add(ASTC);

            }

        }

    }


    public void requerer() {


        // IMPORTANDO BIBLIOTECAS EXTERNAS

        ArrayList<AST> mReq = new ArrayList<AST>();


        for (AST ASTCGlobal : mASTS) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {
                    if (ASTC.mesmoTipo("REQUIRED")) {

                        mReq.add(ASTC);


                    }

                }

            }
        }


        ArrayList<String> mRequiscoes = new ArrayList<>();

        int i = 0;
        int o = mReq.size();

        while (i < o) {

            AST ASTReq = mReq.get(i);


            String mLocalBiblioteca = mLocal + ASTReq.getNome() + ".sigmad";

            if (!mRequiscoes.contains(mReq)) {

                //    System.out.println("Importando :: " + ASTReq.getValor());


                mRequiscoes.add(mLocalBiblioteca);

                File arq = new File(mLocalBiblioteca);

                if (arq.exists()) {


                    RunTime RunTimeC = new RunTime();

                    try {
                        RunTimeC.init(mLocalBiblioteca);

                        AST mBiblioteca = RunTimeC.getBranch("SIGMAZ");

                        if (ASTReq.getValor().contentEquals(RunTimeC.getShared())) {

                            for (AST ASTR : mBiblioteca.getASTS()) {

                                this.getBranch("SIGMAZ").getASTS().add(ASTR);

                            }

                        } else {
                            mErros.add("Biblioteca " + ASTReq.getNome() + " : Problema com Chave Compartilhada !");
                        }


                    } catch (Exception e) {
                        mErros.add("Biblioteca " + ASTReq.getNome() + " : Problema ao carregar !");
                    }

                } else {
                    mErros.add("Biblioteca " + ASTReq.getNome() + " : Nao Encontrado !");
                }


            }


            i += 1;
        }

    }

    public void run() {

        limpar();

        requerer();

        boolean enc = false;

        Chronos mChronos = new Chronos();

        long ei = mChronos.get();

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                Escopo Global = new Escopo(this, null);
                Global.setNome("GLOBAL");

                mEscopoGlobal = Global;
                mGlobal = new Global(mEscopoGlobal);

                mapearPacotes(ASTCGlobal);

                Global.indexar(ASTCGlobal);

                Run eRun = new Run(this);
                eRun.runSigmaz(ASTCGlobal, ASTCGlobal, Global);

                enc = true;
                break;

            }

        }

        if (!enc) {
            mErros.add("Sigmaz Vazio !");
        }

        long eo = mChronos.get();

        mTempo_Execucao = mChronos.getIntervalo(ei, eo);

    }


    public boolean existePacote(String eNome) {
        boolean ret = false;

        for (AST ASTC : mGlobalPackages) {
            if (ASTC.mesmoNome(eNome)) {
                ret = true;
                break;
            }
        }
        return ret;
    }

    public AST getPacote(String eNome) {
        AST ret = null;

        for (AST ASTC : mGlobalPackages) {
            if (ASTC.mesmoNome(eNome)) {
                ret = ASTC;
                break;
            }
        }
        return ret;
    }

    public String getTipagem(AST eAST) {

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }


    public AST getBranch(String eTipo) {
        AST mRet = null;

        for (AST mAST : mASTS) {
            if (mAST.mesmoTipo(eTipo)) {
                mRet = mAST;
                break;
            }
        }

        return mRet;
    }


    public ArrayList<AST> getGlobalPackages() {
        return mGlobalPackages;
    }


    public void estrutura() {

        limpar();

        Estrutural mEstrutural = new Estrutural();

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {

                    mEstrutural.guardar(ASTC);

                }


            }

            if (mASTS.size() == 0) {
                mErros.add("Sigmaz Vazio !");
            } else {

                mEstrutural.mostrar();

            }


        }

    }


    public void interno(String eLocal) {

        limpar();

        boolean enc = false;


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                enc = true;

            }

        }

        if (!enc) {
            mErros.add("Sigmaz Vazio !");
        }

    }

    public String getShared() {

        String ret = "";

        for (AST eAST : mCabecalho) {
            if (eAST.mesmoTipo("SHARED")) {
                ret = eAST.getValor();
                break;
            }
        }

        return ret;
    }

    public int getInstrucoes() {

        int ret = 0;

        for (AST ASTCGlobal : mASTS) {
            ret += ASTCGlobal.getInstrucoes();
        }

        return ret;
    }


    public void errar(String eLocal, String eMensagem) {
        getErros().add(eLocal + " -->> " + eMensagem);
    }

    public Global getGlobalDebug() {
        return mGlobal;
    }


    public void debugOperadores(Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(this);

        ArrayList<AST> mOperadores = mRun_Context.getOperatorsContexto(mEscopo);


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            System.out.println("\t - Guardado : " + mAST.getTipo() + " " + mAST.getNome());
        }

        for (AST mAST : mOperadores) {

            Index_Function mIndex_Function = new Index_Function(this, mEscopo, mAST);
            mIndex_Function.resolverTipagem(mEscopo.getRefers());
            System.out.println("\t - Operador : " + mIndex_Function.getDefinicao());

        }

    }

    public void debugRefers(Escopo mEscopo) {

        System.out.println(" ESCOPO : " + mEscopo.getNome());

        for (String mAST : mEscopo.getRefers()) {
            System.out.println("\t - REFER : " + mAST);
        }

    }

    public void debugCasts(Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(this);

        ArrayList<AST> mListagem = mRun_Context.getCastsContexto(mEscopo);


        for (AST mAST : mEscopo.getGuardadosCompleto()) {
            System.out.println("\t - Guardado : " + mAST.getTipo() + " " + mAST.getNome());
        }

        for (AST mAST : mListagem) {

            System.out.println("\t - Cast : " + mAST.getNome());

        }

    }

    public void debugTodosOsTipos(Escopo mEscopo) {


        System.out.println(" ESCOPO : " + mEscopo.getNome());

        Run_Context mRun_Context = new Run_Context(this);

        ArrayList<AST> mListagem = mRun_Context.getDefinidos(mEscopo);

        for (AST mAST : mListagem) {

            System.out.println("\t - Definido : " + mAST.getNome());

        }

    }


}
