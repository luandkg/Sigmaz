package Sigmaz.S05_Executor;

import Sigmaz.S01_Compilador.Orquestrantes;
import Sigmaz.S08_Utilitarios.Chronos_Intervalo;
import Sigmaz.S04_Montador.Montador;
import Sigmaz.S05_Executor.Debuggers.Global;

import java.io.File;
import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.AST;
import Sigmaz.S05_Executor.Processor.TabelaDeArquivos;

public class RunTime {

    private ArrayList<AST> mCabecalho;
    private ArrayList<AST> mASTS;
    private ArrayList<AST> mASTDebug;


    private ArrayList<String> mErros;
    private ArrayList<String> mDebugs;

    private ArrayList<String> mMensagens;

    private Escopo mEscopoGlobal;
    private Global mGlobal;

    private ArrayList<AST> mGlobalPackages;


    private String mLocal_Objeto;
    private String mLocal_Bibliotecas;

    private Heap mHeap;
    private Externals mExternals;

    private Processador mProcessador;

    private boolean mMostrar_Mensagens;

    private float mTempo_Leitura;
    private float mTempo_Processamento;
    private float mTempo_Organizacao;
    private float mTempo_Execucao;
    private float mTempo_Inicializacao;

    private boolean mIsDebug;
    private TabelaDeArquivos mTabelaDeArquivos;
    private ArrayList<TipoAssociativo> mTipoAssociativos;

    public RunTime() {

        mASTS = new ArrayList<>();
        mCabecalho = new ArrayList<>();


        mErros = new ArrayList<>();
        mDebugs = new ArrayList<>();
        mMensagens = new ArrayList<String>();

        mGlobalPackages = new ArrayList<AST>();


        mEscopoGlobal = null;
        mLocal_Bibliotecas = "";

        mMostrar_Mensagens = true;

        mHeap = new Heap(this);
        mExternals = new Externals(this);

        mProcessador = new Processador(this);

        mLocal_Bibliotecas = "";
        mTempo_Leitura = 0.0F;
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;
        mTempo_Execucao = 0.0F;
        mTempo_Inicializacao = 0.0F;

        mIsDebug = false;
        mASTDebug = null;

        mTabelaDeArquivos = new TabelaDeArquivos();
        mTipoAssociativos = new ArrayList<TipoAssociativo>();

    }

    public TabelaDeArquivos getTabelaDeArquivos() {
        return mTabelaDeArquivos;
    }

    public ArrayList<AST> getASTDebug() {
        return mASTDebug;
    }


    public void init(String eArquivo, boolean eDebugar) {

        mIsDebug = eDebugar;

        limpar();

        mLocal_Objeto = eArquivo;
        mLocal_Bibliotecas = new File(eArquivo).getParent() + "/";


        Chronos_Intervalo mChronos = new Chronos_Intervalo();

        mChronos.marqueInicio();

        Montador mMontador = new Montador();

        OMLRun mOMLRun = mMontador.Decompilar(eArquivo);

        mCabecalho = mOMLRun.getCabecalho();
        mASTS = mOMLRun.getCodigo();
        mASTDebug = mOMLRun.getDebug();


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

        mChronos.marqueFim();

        mTempo_Inicializacao = mChronos.getIntervalo();

    }


    public void run() {

        limpar_run();


        if (existeBranch(Orquestrantes.SIGMAZ)) {

            AST ASTCGlobal = getBranch(Orquestrantes.SIGMAZ);


            Run_Required mRun_Required = new Run_Required(this);
            mRun_Required.requerer(getBranch(Orquestrantes.SIGMAZ));

            Chronos_Intervalo mChronos = new Chronos_Intervalo();

            mChronos.marqueInicio();


            Escopo Global = new Escopo("GLOBAL", this);

            mEscopoGlobal = Global;
            mGlobal = new Global(mEscopoGlobal);


            Run eRun = new Run(this, Global);
            eRun.runSigmaz(ASTCGlobal, ASTCGlobal);


            mChronos.marqueFim();

            mTempo_Execucao = mChronos.getIntervalo();


        } else {
            mErros.add("Sigmaz Vazio !");
        }


    }

    public ArrayList<TipoAssociativo> getTipoAssociativos() {
        return mTipoAssociativos;
    }

    public void runTestes() {

        limpar_run();


        if (existeBranch(Orquestrantes.SIGMAZ)) {

            AST ASTCGlobal = getBranch(Orquestrantes.SIGMAZ);


            Run_Required mRun_Required = new Run_Required(this);
            mRun_Required.requerer(getBranch(Orquestrantes.SIGMAZ));

            Chronos_Intervalo mChronos = new Chronos_Intervalo();

            mChronos.marqueInicio();


            Escopo Global = new Escopo("GLOBAL", this);

            mEscopoGlobal = Global;
            mGlobal = new Global(mEscopoGlobal);


            Run eRun = new Run(this, Global);
            eRun.runTestes(ASTCGlobal, ASTCGlobal);


            mChronos.marqueFim();

            mTempo_Execucao = mChronos.getIntervalo();


        } else {
            mErros.add("Sigmaz Vazio !");
        }


    }


    public String getArvoreDeInstrucoes() {

        String ret = "";

        for (AST eAST : getASTS()) {

            ret += eAST.ImprimirArvoreDeInstrucoes();
        }

        return ret;

    }


    public String getArvoreDeDebug() {

        String ret = "";

        for (AST eAST : mASTDebug) {

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
            if (ASTCGlobal.mesmoTipo(Orquestrantes.SIGMAZ)) {
                mRet = ASTCGlobal;
            }
        }
        return mRet;
    }


    public boolean existeBranch(String eTipo) {
        boolean enc = false;

        for (AST mAST : mASTS) {
            if (mAST.mesmoTipo(eTipo)) {
                enc = true;
                break;
            }
        }

        return enc;
    }

    public void limpar() {

        mErros.clear();
        mDebugs.clear();
        mMensagens.clear();

        mGlobalPackages.clear();

        mHeap.limpar();
        mExternals.limpar();

        mLocal_Bibliotecas = "";
        mTempo_Processamento = 0.0F;
        mTempo_Organizacao = 0.0F;
        mTempo_Execucao = 0.0F;
        mTempo_Inicializacao = 0.0F;

        mTabelaDeArquivos.fecharTodos();

        mTipoAssociativos.clear();

    }

    public void limpar_run() {

        mTempo_Execucao = 0.0F;
        mErros.clear();

        mGlobalPackages.clear();

        mHeap.limpar();
        mExternals.limpar();

        mProcessador.limpar();

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

    public ArrayList<String> getDebugs() {
        return mDebugs;
    }

    public boolean isDebug() {
        return mIsDebug;
    }

    ;

    public Processador getProcessador() {
        return mProcessador;
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

            if (ASTCGlobal.mesmoTipo(Orquestrantes.SIGMAZ)) {

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


    public AST getASTDebug(AST AST_Raiz) {
        AST mRet = null;
        boolean mEnc = false;

        for (AST eAST : AST_Raiz.getASTS()) {
            if (eAST.mesmoTipo("DEBUG_MODE")) {
                mRet = eAST;
                mEnc = true;
                break;
            }
        }
        if (!mEnc) {
            mRet = AST_Raiz.criarBranch("DEBUG_MODE");
        }

        return mRet;
    }

    public void debug(AST ASTCorrente) {

        if (ASTCorrente.existeBranch("IN_DEBUG")) {
            AST InDebug = ASTCorrente.getBranch("IN_DEBUG");

            String eArquivo = InDebug.getNome();
            String eLocalizacao = InDebug.getValor();

            String mArquivo = eArquivo;

            for (AST eD : mASTDebug) {
                for (AST oD : eD.getASTS()) {
                    if (oD.mesmoNome(eArquivo)) {
                        mArquivo = oD.getValor();
                        break;
                    }
                }

            }

            mDebugs.add(mArquivo + " -->> " + eLocalizacao);
        }


    }

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

    public float getTempo_Inicializacao() {
        return mTempo_Inicializacao;
    }

    public void interno(String eLocal) {

        limpar();

        boolean enc = false;


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo(Orquestrantes.SIGMAZ)) {


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

    public boolean temErros() {
        return mErros.size() > 0;
    }

    public String getLocal_Objeto() {
        return mLocal_Objeto;
    }

    public String getLocal_Bibliotecas() {
        return mLocal_Bibliotecas;
    }
}
