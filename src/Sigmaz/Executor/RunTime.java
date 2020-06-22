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

    private ArrayList<AST> mStages;

    private long mHEAPID;

    private boolean mExterno;

    public RunTime() {

        mASTS = new ArrayList<>();

        mErros = new ArrayList<>();

        mDataTypes = new DataTypes();

        mHeap = new ArrayList<Run_Struct>();

        mEscopoGlobal = null;
        mHEAPID = 0;

        mExterno = true;

        mStages = new ArrayList<AST>();
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

    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void init(String eArquivo) {

        mASTS.clear();
        mErros.clear();
        mHeap.clear();
        mHEAPID = 0;
        mStages.clear();

        Documentador DC = new Documentador();

        mASTS = DC.Decompilar(eArquivo);


    }


    public Escopo getEscopoGlobal() {
        return mEscopoGlobal;
    }


    public void adicionarHeap(Run_Struct eEscopo) {
        mHeap.add(eEscopo);
    }

    public Run_Struct getRun_Struct(String eNome) {

        Run_Struct mRet = null;

        for (Run_Struct mRun_Struct : mHeap) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mRet = mRun_Struct;
                break;
            }
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

    public void run() {

        mHeap.clear();
        mErros.clear();
        mStages.clear();


        Escopo Global = new Escopo(this, null);
        Global.setNome("GLOBAL");

        mEscopoGlobal = Global;

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

                    }

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


}
