package Sigmaz.Executor;

import Sigmaz.Executor.Runners.*;
import Sigmaz.Intellisenses.Intellisense;
import Sigmaz.Internal;
import Sigmaz.Utils.*;

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
    private ArrayList<AST> mGlobalPackages;

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
        mGlobalPackages = new ArrayList<AST>();

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


        mHeap.clear();
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
        mGlobalPackages.clear();


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


    public AST getSigmaz() {

        AST mRet = null;

        for (AST ASTCGlobal : mASTS) {
            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {
                mRet = ASTCGlobal;
            }
        }
        return mRet;
    }


    public void indexar(AST ASTCGlobal, Escopo Global) {

        Global.limpar();

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

                Global.guardar(ASTC);

                mGlobalStructs.add(ASTC);
                mT_Structs.add(ASTC.getNome());

            } else if (ASTC.mesmoTipo("TYPE")) {

                Global.guardar(ASTC);

                mT_Types.add(ASTC.getNome());

                mGlobalTypes.add(ASTC);
            } else if (ASTC.mesmoTipo("PACKAGE")) {
                mT_Types.add(ASTC.getNome());

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
                    mErros.add("Library " + mReq + " : Problema ao carregar !");
                }

            } else {
                mErros.add("Library " + mReq + " : Nao Encontrado !");
            }

        }


    }

    public void run() {

        limpar();

        requerer();

        boolean enc = false;

        AST ASTSigmaz = null;
        AST ASTSigmaz_Call = null;

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                ASTSigmaz_Call = ASTCGlobal;
                ASTSigmaz = ASTCGlobal;
                enc = true;
                break;


            }


        }

        if (enc) {

            Escopo Global = new Escopo(this, null);
            Global.setNome("GLOBAL");

            mEscopoGlobal = Global;

            indexar(ASTSigmaz, Global);

            Run eRun = new Run(this);
            eRun.runSigmaz(ASTSigmaz_Call, ASTSigmaz, Global);

        } else {
            mErros.add("Sigmaz Vazio !");
        }

        //  int i = 0;

        // for (AST ASTCGlobal : mASTS) {

        //   System.out.println(ASTCGlobal.toJava(i));

        // i+=1;

        // }


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

    public String getQualificador(String aNome, ArrayList<String> mPacotes) {


        String ret = "";
        String eNome = "";

        if (aNome.contains("<>")) {

            int i = 0;
            int o = aNome.length();

            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals(">")) {
                    i += 1;
                    break;
                }
                i += 1;
            }

            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals("<")) {
                    break;
                } else if (l.contentEquals(">")) {
                    break;
                } else {
                    eNome += l;
                }
                i += 1;
            }

        } else {

            int i = 0;
            int o = aNome.length();
            while (i < o) {
                String l = String.valueOf(aNome.charAt(i));
                if (l.contentEquals("<")) {
                    break;
                } else {
                    eNome += l;
                }
                i += 1;
            }

        }


        ret = "PRIMITIVE";

        //  System.out.println("Tipando --> " + eNome);

        ArrayList<AST> mCasts = new ArrayList<AST>();
        ArrayList<AST> mTipos = new ArrayList<AST>();
        ArrayList<AST> mStructs = new ArrayList<AST>();

        for (AST eAST : mGlobalCasts) {
            mCasts.add(eAST);
        }
        for (AST eAST : mGlobalTypes) {
            mTipos.add(eAST);
        }
        for (AST eAST : mGlobalStructs) {
            mStructs.add(eAST);
        }

        for (String Referencia : mPacotes) {

            if (existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {

                    //  System.out.println("Tipando " + eAST.getNome() + "....");


                    if (eAST.mesmoTipo("CAST")) {
                        mCasts.add(eAST);
                        //  } else if (eAST.mesmoTipo("TYPE")) {

                    } else if (eAST.mesmoTipo("STRUCT")) {

                        String eExtended = eAST.getBranch("EXTENDED").getNome();


                        if (eExtended.contentEquals("STRUCT")) {
                            mStructs.add(eAST);
                        } else if (eExtended.contentEquals("TYPE")) {
                            mTipos.add(eAST);
                        }

                    }
                }

            } else {
                mErros.add("PACKAGE  " + Referencia + " : Nao encontrado !");
            }

        }

        for (AST eAST : mCasts) {
            if (eAST.mesmoNome(eNome)) {
                ret = "CAST";
                break;
            }
        }

        for (AST eAST : mTipos) {
            if (eAST.mesmoNome(eNome)) {
                ret = "TYPE";
                break;
            }
        }

        for (AST eAST : mStructs) {


            if (eAST.mesmoNome(eNome)) {
                String eExtended = eAST.getBranch("EXTENDED").getNome();

                if (eExtended.contentEquals("STRUCT")) {
                    ret = "STRUCT";
                } else if (eExtended.contentEquals("TYPE")) {
                    ret = "TYPE";
                }


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

    public ArrayList<AST> getGlobalPackages() {
        return mGlobalPackages;
    }

    public ArrayList<AST> getGlobalOperations() {
        return mGlobalOperacoes;
    }

    public ArrayList<AST> getGlobalDirectors() {
        return mGlobalDirectors;
    }


    public ArrayList<String> getRefers(String eNome) {

        ArrayList<String> mRet = new ArrayList<String>();
        for (AST PKG : getGlobalPackages()) {
            for (AST Sub : PKG.getASTS()) {
                if (Sub.mesmoTipo("REFER")) {
                    mRet.add(Sub.getNome());
                }
            }
        }
        return mRet;
    }

    public void intellisense(String eLocal) {

        limpar();

        Intellisense IntellisenseC = new Intellisense();
        IntellisenseC.run(mASTS, eLocal);

    }

    public void uml(String eLocal) {

        limpar();

        UML UMLC = new UML();
        UMLC.run(mASTS, eLocal);

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

                Internal mInternal = new Internal(ASTCGlobal);
                mInternal.exportar(eLocal);
                enc = true;

            }

        }

        if(!enc){
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

    public void errar(String eLocal,String eMensagem){
        getErros().add(eLocal + " -->> " + eMensagem);
    }


}
