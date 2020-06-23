package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Documento;

import java.util.ArrayList;

public class RunTime {

    private ArrayList<AST> mASTS;
    private ArrayList<String> mErros;
    private DataTypes mDataTypes;

    private Escopo mEscopoGlobal;

    private ArrayList<Run_Struct> mHeap;
    private ArrayList<Run_Extern> mExtern;

    private ArrayList<AST> mStages;

    private long mHEAPID;

    private boolean mExterno;

    private int mContador_Actions;
    private int mContador_Casts;
    private int mContador_Functions;
    private int mContador_Opertations;
    private int mContador_Structs;
    private int mContador_Stages;

    public RunTime() {

        mASTS = new ArrayList<>();

        mErros = new ArrayList<>();

        mDataTypes = new DataTypes();

        mHeap = new ArrayList<Run_Struct>();
        mExtern = new ArrayList<Run_Extern>();

        mEscopoGlobal = null;

        mExterno = true;

        mStages = new ArrayList<AST>();


        limpar();

    }

    public void limpar() {


        mASTS.clear();
        mErros.clear();


        mHEAPID = 0;

        mContador_Actions = 0;
        mContador_Casts = 0;
        mContador_Functions = 0;
        mContador_Opertations = 0;
        mContador_Structs = 0;
        mContador_Stages = 0;


        mHeap.clear();
        mExtern.clear();

        mStages.clear();


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

    public long getHEAPID() {
        mHEAPID += 1;
        return mHEAPID;
    }

    public ArrayList<Run_Struct> getHeap() {
        return mHeap;
    }

    public ArrayList<Run_Extern> getExtern() {
        return mExtern;
    }

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void init(String eArquivo) {


        limpar();

        Documentador DC = new Documentador();

        mASTS = DC.Decompilar(eArquivo);

        contagem();


    }


    public Escopo getEscopoGlobal() {
        return mEscopoGlobal;
    }


    public void adicionarHeap(Run_Struct eEscopo) {
        mHeap.add(eEscopo);
    }


    public void removerHeap(String eNome) {

        for (Run_Struct mRun_Struct : mHeap) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mHeap.remove(mRun_Struct);
                break;
            }
        }

    }


    public Run_Struct getRun_Struct(String eNome) {

        Run_Struct mRet = null;
        boolean enc = false;

        for (Run_Struct mRun_Struct : mHeap) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mRet = mRun_Struct;
                enc = true;
                break;
            }
        }

        if (!enc) {
            mErros.add("Nao foi possivel encontrar a struct : " + eNome);
        }

        return mRet;

    }


    public Run_Extern getRun_Extern(String eNome) {

        Run_Extern mRet = null;
        boolean enc = false;

        for (Run_Extern mRun_Struct : mExtern) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mRet = mRun_Struct;
                enc = true;
                break;
            }
        }

        if (!enc) {
            mErros.add("Nao foi possivel encontrar a struct : " + eNome);
        }

        return mRet;

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


    public void contagem() {

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {
                        mContador_Functions += 1;
                    } else if (ASTC.mesmoTipo("ACTION")) {
                        mContador_Actions += 1;
                    } else if (ASTC.mesmoTipo("OPERATION")) {
                        mContador_Opertations += 1;
                    } else if (ASTC.mesmoTipo("CAST")) {
                        mContador_Casts += 1;
                    } else if (ASTC.mesmoTipo("STAGES")) {
                        mContador_Stages += 1;
                    } else if (ASTC.mesmoTipo("STRUCT")) {
                        mContador_Structs += 1;
                    }

                }
            }
        }

    }


    public void run() {

        mHeap.clear();
        mErros.clear();
        mStages.clear();


        Escopo Global = new Escopo(this, null);
        Global.setNome("GLOBAL");

        mEscopoGlobal = Global;

        ArrayList<AST> mStructs = new ArrayList<AST>();

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {
                        Global.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("ACTION")) {
                        Global.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("OPERATION")) {
                        Global.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("CAST")) {
                        Global.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("STAGES")) {

                        Global.guardar(ASTC);

                        mStages.add(ASTC);

                    } else if (ASTC.mesmoTipo("STRUCT")) {

                        mStructs.add(ASTC);



                    }

                }


                for (AST mStruct : mStructs) {
                    Run_Extern mRE = new Run_Extern(this);
                    mRE.init(mStruct);
                    mExtern.add(mRE);
                }

                for (Run_Extern mRE : mExtern) {
                    mRE.run();
                }

                for (AST ASTC : ASTCGlobal.getASTS()) {

                    if (this.getErros().size() > 0) {
                        return;
                    }

                    if (ASTC.mesmoTipo("DEFINE")) {

                        Run_Def mAST = new Run_Def(this, Global);
                        mAST.init(ASTC);


                    } else if (ASTC.mesmoTipo("MOCKIZ")) {


                        Run_Moc mAST = new Run_Moc(this, Global);
                        mAST.init(ASTC);

                    } else if (ASTC.mesmoTipo("INVOKE")) {

                        Run_Invoke mAST = new Run_Invoke(this, Global);
                        mAST.init(ASTC);

                    }

                }

                for (AST ASTC : ASTCGlobal.getASTS()) {

                    if (ASTC.mesmoTipo("CALL")) {


                        AST mSending = ASTC.getBranch("SENDING");

                        Run_Func mAST = new Run_Func(this, Global);
                        mAST.init_Action(mSending);

                    }


                    if (mErros.size() > 0) {
                        break;
                    }

                }


            }



        }

        if (mASTS.size() == 0) {
            mErros.add("Sigmaz Vazio !");
        }


    }

    public void estrutura() {

        mHeap.clear();
        mErros.clear();
        mStages.clear();


        Estrutural mEstrutural = new Estrutural();


        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("ACTION")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("OPERATION")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("CAST")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("STRUCT")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("STAGES")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("CALL")) {

                        mEstrutural.guardar(ASTC);


                    } else if (ASTC.mesmoTipo("DEFINE")) {
                        mEstrutural.guardar(ASTC);

                    } else if (ASTC.mesmoTipo("MOCKIZ")) {
                        mEstrutural.guardar(ASTC);

                    }

                }


            }

            if (mASTS.size() == 0) {
                mErros.add("Sigmaz Vazio !");
            } else {

                mEstrutural.mostrar();

            }


        }

    }

    public boolean isPrimitivo(String eTipo) {
        return mDataTypes.isPrimitivo(eTipo);
    }

    public boolean existeStage(String eStage) {
        boolean enc = false;

        for (AST mAST : mStages) {
            for (AST sAST : mAST.getASTS()) {

                String tmp = mAST.getNome() + "::" + sAST.getNome();
                if (tmp.contentEquals(eStage)) {
                    enc = true;
                    break;
                }

            }
        }

        return enc;
    }

    public String getArvoreDeInstrucoes() {

        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("");

        for (AST a : getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());
            } else {
                DocumentoC.adicionarLinha(" " + a.getTipo() + " -> " + a.getNome());
            }

            SubArvoreDeInstrucoes("   ", a, DocumentoC);

        }

        DocumentoC.adicionarLinha("");

        return DocumentoC.getConteudo();
    }

    private void SubArvoreDeInstrucoes(String ePref, AST ASTC, Documento DocumentoC) {

        for (AST a : ASTC.getASTS()) {

            if (a.getValor().length() > 0) {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome() + " : " + a.getValor());

            } else {
                DocumentoC.adicionarLinha(ePref + a.getTipo() + " -> " + a.getNome());

            }

            SubArvoreDeInstrucoes(ePref + "   ", a, DocumentoC);

        }

    }

    public int getInstrucoes() {

        int ret = 0;

        for (AST ASTCGlobal : mASTS) {
            ret += ASTCGlobal.getInstrucoes();
        }

        return ret;
    }

    public int getActions() {
        return mContador_Actions;
    }

    public int getCasts() {
        return mContador_Casts;
    }

    public int getFunctions() {
        return mContador_Functions;
    }

    public int getOperations() {
        return mContador_Opertations;
    }

    public int getStages() {
        return mContador_Stages;
    }

    public int getStructs() {
        return mContador_Structs;
    }

}
