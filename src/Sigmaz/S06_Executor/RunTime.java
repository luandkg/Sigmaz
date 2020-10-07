package Sigmaz.S06_Executor;

import Sigmaz.S06_Executor.Debuggers.Global;
import Sigmaz.S06_Executor.Indexador.Index_Function;
import Sigmaz.S06_Executor.Runners.*;

import java.io.File;
import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.Documentador;
import Sigmaz.S00_Utilitarios.Tempo;

public class RunTime {

    private ArrayList<AST> mASTS;
    private ArrayList<String> mErros;

    private Escopo mEscopoGlobal;
    private Global mGlobal;

    private ArrayList<AST> mGlobalPackages;


    private String mLocal;
    private String mLocal_Bibliotecas;

    private Heap mHeap;
    private Externals mExternals;

    private Processador mProcessador;

    private boolean mExterno;


    public RunTime() {

        mASTS = new ArrayList<>();

        mErros = new ArrayList<>();


        mGlobalPackages = new ArrayList<AST>();


        mEscopoGlobal = null;
        mLocal = "";

        mExterno = true;

        mHeap = new Heap(this);
        mExternals = new Externals(this);

        mProcessador = new Processador(this);

        mLocal_Bibliotecas="";

    }

    public void limpar() {

        mErros.clear();

        mGlobalPackages.clear();

        mHeap.limpar();
        mExternals.limpar();

        mProcessador = new Processador(this);
        mLocal_Bibliotecas="";

    }


    public void externarlizar() {
        mExterno = true;
    }

    public void internalizar() {
        mExterno = false;
    }

    public boolean getExterno() {
        return mExterno;
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

    public ArrayList<String> getErros() {
        return mErros;
    }

    public Processador getProcessador(){return mProcessador;}

    public void init(String eArquivo) {

        File arq = new File(eArquivo);
        mLocal = arq.getParent() + "/";

        limpar();

        Documentador DC = new Documentador();

        mASTS = DC.Decompilar(eArquivo);


    }

    public String getArvoreDeInstrucoes() {

        Documentador DC = new Documentador();

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

        ArrayList<String> mRequiscoes = new ArrayList<>();


        for (AST ASTCGlobal : mASTS) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {
                    if (ASTC.mesmoTipo("REQUIRED")) {

                        String mReq = mLocal + ASTC.getNome() + ".sigmad";
                        mRequiscoes.add(mReq);


                    }

                }

            }
        }

        // IMPORTANDO BIBLIOTECAS EXTERNAS

        for (String mReq : mRequiscoes) {

            File arq = new File(mReq);

            if (arq.exists()) {


                RunTime RunTimeC = new RunTime();

                try {
                    RunTimeC.init(mReq);

                    for (AST ASTR : RunTimeC.getBranch("SIGMAZ").getASTS()) {

                        this.getBranch("SIGMAZ").getASTS().add(ASTR);

                    }


                } catch (Exception e) {
                    mErros.add("Biblioteca " + mReq + " : Problema ao carregar !");
                }

            } else {
                mErros.add("Biblioteca " + mReq + " : Nao Encontrado !");
            }

        }


    }

    public void run() {

        limpar();

        requerer();

        boolean enc = false;


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


    public int getInstrucoes() {

        int ret = 0;

        for (AST ASTCGlobal : mASTS) {
            ret += ASTCGlobal.getInstrucoes();
        }

        return ret;
    }

    public String getData() {

        return Tempo.getData();


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
