package Sigmaz.Executor;

import Sigmaz.Executor.Runners.*;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Documento;

import java.io.File;
import java.util.ArrayList;

public class RunTime {

    private ArrayList<AST> mASTS;
    private ArrayList<String> mErros;

    private Escopo mEscopoGlobal;

    private ArrayList<Run_Struct> mHeap;
    private ArrayList<Run_Extern> mExtern;
    private ArrayList<Run_Type> mTypes_Instances;


    private ArrayList<AST> mGlobalActions;
    private ArrayList<AST> mGlobalFunctions;


    private ArrayList<AST> mGlobalCasts;
    private ArrayList<AST> mGlobalTypes;
    private ArrayList<AST> mGlobalStages;
    private ArrayList<AST> mGlobalStructs;

    private ArrayList<AST> mGlobalOperacoes;
    private ArrayList<AST> mGlobalDirectors;

    private ArrayList<String> mT_Primitivos;
    private ArrayList<String> mT_Casts;
    private ArrayList<String> mT_Types;
    private ArrayList<String> mT_Structs;
    private ArrayList<String> mT_Stages;


    private String mLocal;

    private long mHEAPID;

    private boolean mExterno;

    private int mContador_Actions;
    private int mContador_Casts;
    private int mContador_Functions;
    private int mContador_Opertations;
    private int mContador_Structs;
    private int mContador_Stages;
    private int mContador_Types;

    public RunTime() {

        mASTS = new ArrayList<>();

        mErros = new ArrayList<>();


        mT_Primitivos = new ArrayList<String>();
        mT_Casts = new ArrayList<String>();
        mT_Types = new ArrayList<String>();
        mT_Structs = new ArrayList<String>();
        mT_Stages = new ArrayList<String>();


        mGlobalActions = new ArrayList<>();
        mGlobalFunctions = new ArrayList<>();
        mGlobalOperacoes = new ArrayList<AST>();
        mGlobalDirectors = new ArrayList<AST>();
        mGlobalCasts = new ArrayList<AST>();
        mGlobalTypes = new ArrayList<AST>();
        mGlobalStages = new ArrayList<AST>();
        mGlobalStructs = new ArrayList<AST>();

        mHeap = new ArrayList<Run_Struct>();
        mTypes_Instances = new ArrayList<Run_Type>();
        mExtern = new ArrayList<Run_Extern>();

        mEscopoGlobal = null;
        mLocal = "";

        mExterno = true;


        limpar();

    }

    public void limpar() {

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
        mTypes_Instances.clear();

        mT_Primitivos.clear();
        mT_Casts.clear();
        mT_Types.clear();
        mT_Structs.clear();
        mT_Stages.clear();

        mGlobalActions.clear();
        mGlobalFunctions.clear();
        mGlobalOperacoes.clear();
        mGlobalDirectors.clear();
        mGlobalCasts.clear();
        mGlobalTypes.clear();
        mGlobalStages.clear();
        mGlobalStructs.clear();


    }

    public String getQualificador(String aNome) {
        String ret = "";


        String eNome = "";
        int i =0;
        int o = aNome.length();
        while(i<o){
            String l = String.valueOf(aNome.charAt(i));
            if (l.contentEquals("<")){
                break;
            }else{eNome +=l;}
            i+=1;
        }

        if (mT_Primitivos.contains(eNome)) {
            ret = "PRIMITIVE";
        } else if (mT_Casts.contains(eNome)) {
            ret = "CAST";
        } else if (mT_Types.contains(eNome)) {
            ret = "TYPE";
        } else if (mT_Structs.contains(eNome)) {
            ret = "STRUCT";
        } else if (mT_Stages.contains(eNome)) {
            ret = "STAGES";
        }

        return ret;
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

    public ArrayList<Run_Type> getTypes_Instances() {
        return mTypes_Instances;
    }


    public ArrayList<AST> getASTS() {
        return mASTS;
    }

    public ArrayList<String> getErros() {
        return mErros;
    }

    public void init(String eArquivo) {

        File arq = new File(eArquivo);
        mLocal = arq.getParent() + "/";

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

    public void adicionarType(Run_Type eEscopo) {
        mTypes_Instances.add(eEscopo);
    }


    public void removerHeap(String eNome) {

        for (Run_Struct mRun_Struct : mHeap) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mHeap.remove(mRun_Struct);
                break;
            }
        }

    }

    public void removerType(String eNome) {

        for (Run_Type mRun_Struct : mTypes_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mTypes_Instances.remove(mRun_Struct);
              //  System.out.println("Removendo Object Type : " + eNome);
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

    public Run_Type getRun_Type(String eNome) {

        Run_Type mRet = null;
        boolean enc = false;

        for (Run_Type mRun_Struct : mTypes_Instances) {
            if (mRun_Struct.mesmoNome(eNome)) {
                mRet = mRun_Struct;
                enc = true;
                break;
            }
        }

        if (!enc) {
            mErros.add("Nao foi possivel encontrar a type : " + eNome);
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
                    } else if (ASTC.mesmoTipo("OPERATOR")) {
                        mContador_Opertations += 1;

                    } else if (ASTC.mesmoTipo("CAST")) {
                        mContador_Casts += 1;
                    } else if (ASTC.mesmoTipo("STAGES")) {
                        mContador_Stages += 1;


                    } else if (ASTC.mesmoTipo("STRUCT")) {
                        mContador_Structs += 1;

                    } else if (ASTC.mesmoTipo("TYPE")) {
                        mContador_Types += 1;

                    }

                }
            }
        }

    }


    public void run() {

        limpar();


        Escopo Global = new Escopo(this, null);
        Global.setNome("GLOBAL");

        mEscopoGlobal = Global;

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
                    mErros.add("Library " + mReq + " : Problema ao carregar !");
                }

            } else {
                mErros.add("Library " + mReq + " : Nao Encontrado !");
            }

        }

        // INICIANDO EXECUCAO

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {
                        Global.guardar(ASTC);

                        mGlobalFunctions.add(ASTC);

                    } else if (ASTC.mesmoTipo("ACTION")) {
                        Global.guardar(ASTC);

                        mGlobalActions.add(ASTC);

                    } else if (ASTC.mesmoTipo("OPERATOR")) {
                        Global.guardar(ASTC);

                        mGlobalOperacoes.add(ASTC);
                    } else if (ASTC.mesmoTipo("DIRECTOR")) {
                        Global.guardar(ASTC);

                        mGlobalDirectors.add(ASTC);

                    } else if (ASTC.mesmoTipo("CAST")) {
                        Global.guardar(ASTC);
                        mT_Casts.add(ASTC.getNome());

                        mGlobalCasts.add(ASTC);

                    } else if (ASTC.mesmoTipo("STAGES")) {

                        Global.guardar(ASTC);

                        mGlobalStages.add(ASTC);
                        mT_Stages.add(ASTC.getNome());

                    } else if (ASTC.mesmoTipo("STRUCT")) {

                        mGlobalStructs.add(ASTC);
                        mT_Structs.add(ASTC.getNome());

                    } else if (ASTC.mesmoTipo("TYPE")) {
                        mT_Types.add(ASTC.getNome());

                        mGlobalTypes.add(ASTC);

                    }

                }


                for (AST mStruct : mGlobalStructs) {
                    for (AST mStructBody : mStruct.getBranch("BODY").getASTS()) {
                        if (mStructBody.mesmoTipo("OPERATOR") && mStructBody.getBranch("VISIBILITY").mesmoNome("EXTERN")) {
                            // System.out.println("PORRA PERDIDA");

                            mGlobalOperacoes.add(mStructBody);


                        }
                    }
                }

                for (AST mStruct : mGlobalStructs) {
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


                        if (ASTC.mesmoValor("REFER")){
                            AST mSending = ASTC.getBranch("SENDING");
                            Run_Func mAST = new Run_Func(this, Global);
                            mAST.init_ActionFunction(mSending);
                        }else{

                            Run_Body mAST = new Run_Body(this, Global);
                            mAST.init(ASTC.getBranch("BODY"));

                        }


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

    public void AlocarStages(AST eAST, Escopo mEscopo) {


        int i = 0;

        for (AST AST_STAGE : eAST.getBranch("OPTIONS").getASTS()) {

            if (AST_STAGE.mesmoTipo("STAGE")) {
                mEscopo.criarDefinicao(eAST.getNome() + "::" + AST_STAGE.getNome(), eAST.getNome(), String.valueOf(i));
                i += 1;
            }


        }

    }

    public ArrayList<AST> getGlobalActions() {
        return mGlobalActions;
    }

    public ArrayList<AST> getGlobalFunctions() {
        return mGlobalFunctions;
    }

    public ArrayList<AST> getGlobalCasts() {
        return mGlobalCasts;
    }

    public ArrayList<AST> getGlobalTypes() {
        return mGlobalTypes;
    }


    public ArrayList<AST> getGlobalStages() {
        return mGlobalStages;
    }

    public ArrayList<AST> getGlobalStructs() {
        return mGlobalStructs;
    }

    public ArrayList<AST> getGlobalOperations() {
        return mGlobalOperacoes;
    }

    public ArrayList<AST> getGlobalDirectors() {
        return mGlobalDirectors;
    }


    public boolean existeStage(String eStage) {
        boolean enc = false;

        for (AST mAST : mGlobalStages) {
            for (AST sAST : mAST.getBranch("OPTIONS").getASTS()) {

                if (sAST.mesmoTipo("STAGE")) {
                    String tmp = mAST.getNome() + "::" + sAST.getNome();
                    if (tmp.contentEquals(eStage)) {
                        enc = true;
                        break;
                    }
                }


            }
        }

        return enc;
    }

    public void estrutura() {

        mHeap.clear();
        mErros.clear();
        mGlobalStages.clear();


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
                    } else if (ASTC.mesmoTipo("TYPE")) {
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

    public int getTypes() {
        return mContador_Types;
    }


}
