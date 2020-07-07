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
    private ArrayList<Escopo> mPacotes;


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

        mPacotes = new ArrayList<Escopo>();

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
       // mExtern.clear();
        mTypes_Instances.clear();

        mT_Primitivos.clear();
        mT_Casts.clear();
        mT_Types.clear();
        mT_Structs.clear();
        mT_Stages.clear();

        mPacotes.clear();

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

    public String getArvoreDeInstrucoes(){

        Documentador DC = new Documentador();

        String ret = "";

        for (AST eAST : getASTS()) {

            ret+=eAST.ImprimirArvoreDeInstrucoes();
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

                        initPackage(ASTC);


                    }

                }

                ReferenciasInternas();

                for (AST mStruct : mGlobalStructs) {
                    for (AST mStructBody : mStruct.getBranch("BODY").getASTS()) {
                        if (mStructBody.mesmoTipo("OPERATOR") && mStructBody.getBranch("VISIBILITY").mesmoNome("EXTERN")) {
                            // System.out.println("PORRA PERDIDA");

                            mGlobalOperacoes.add(mStructBody);


                        }
                    }
                }

                ArrayList<Run_Extern> GlobalExtern = new ArrayList<Run_Extern>();
                for (AST mStruct : mGlobalStructs) {
                    Run_Extern mRE = new Run_Extern(this);
                    mRE.init("",mStruct.getNome(),mStruct, ASTCGlobal);
                    mExtern.add(mRE);
                    GlobalExtern.add(mRE);

                    Global.externalizarStructGeral(mRE.getNome());
                }

                for (Run_Extern mRE : GlobalExtern) {

                    mRE.externalizar(GlobalExtern);

                    mRE.run();
                }



                ArrayList<String> mUsados = new ArrayList<String>();

                // USAR PACKAGES
                for (AST ASTC : ASTCGlobal.getASTS()) {
                    if (ASTC.mesmoTipo("REFER")) {

                        //  Usar(ASTC.getNome(), Global);

                        Referenciar(ASTC.getNome(), Global, mUsados);
                        Externalizar(ASTC, Global);

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


                        if (ASTC.mesmoValor("REFER")) {
                            AST mSending = ASTC.getBranch("SENDING");
                            Run_Func mAST = new Run_Func(this, Global);
                            mAST.init_ActionFunction(mSending);
                        } else {

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

    public void ReferenciasInternas() {

        for (Escopo EscopoC : mPacotes) {

            AST ASTPacote = getPacote(EscopoC.getNome());
            ArrayList<String> mUsados = new ArrayList<String>();

            for (AST ASTC : ASTPacote.getASTS()) {
                if (ASTC.mesmoTipo("REFER")) {
                    Referenciar(ASTC.getNome(), EscopoC, mUsados);
                }
            }

        }

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

    public void initPackage(AST ASTPai) {

        Escopo EscopoPacote = new Escopo(this, null);

        EscopoPacote.setNome(ASTPai.getNome());


        // System.out.println(" -->> Iniciando Pacote : " + ASTPai.getNome());

        ArrayList<AST> mPackageStructs = new ArrayList<AST>();

        for (AST ASTC : ASTPai.getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                EscopoPacote.guardar(ASTC);


            } else if (ASTC.mesmoTipo("ACTION")) {
                EscopoPacote.guardar(ASTC);


            } else if (ASTC.mesmoTipo("OPERATOR")) {
                EscopoPacote.guardar(ASTC);

            } else if (ASTC.mesmoTipo("DIRECTOR")) {
                EscopoPacote.guardar(ASTC);


            } else if (ASTC.mesmoTipo("CAST")) {
                EscopoPacote.guardar(ASTC);


            } else if (ASTC.mesmoTipo("STAGES")) {

                EscopoPacote.guardar(ASTC);


            } else if (ASTC.mesmoTipo("STRUCT")) {
                EscopoPacote.guardar(ASTC);

                mPackageStructs.add(ASTC);

            } else if (ASTC.mesmoTipo("TYPE")) {
                EscopoPacote.guardar(ASTC);

            }


        }

        ArrayList<Run_Extern> mPackageExterns = new ArrayList<Run_Extern>();

        for (AST mStruct : mPackageStructs) {
            Run_Extern mRE = new Run_Extern(this);
            mRE.init( ASTPai.getNome() ,  mStruct.getNome(),mStruct, ASTPai);
            mPackageExterns.add(mRE);
        }

        for (Run_Extern mRE : mPackageExterns) {
            mRE.run();
            mExtern.add(mRE);
        }


        mPacotes.add(EscopoPacote);
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

    public void Externalizar(AST ePacote, Escopo EscopoDono) {

        //System.out.println("Externalizador " + EscopoDono.getNome() + " : " + ePacote.getNome());

        for (AST eStruct : getPacote(ePacote.getNome()).getASTS()) {

            if (eStruct.mesmoTipo("STRUCT")){
            //    System.out.println("Externalizar " + eStruct.getNome() + " para " + EscopoDono.getNome());
                EscopoDono.externalizarStruct(ePacote.getNome(),eStruct.getNome());
            }

        }

    }



    public void Referenciar(String eNome, Escopo EscopoDono, ArrayList<String> mUsados) {

        if (!mUsados.contains(eNome)) {

            mUsados.add(eNome);
            boolean enc = false;

            for (Escopo ASTPackage : mPacotes) {

                if (ASTPackage.getNome().contentEquals(eNome)) {

                    //System.out.println(" -->> Referenciando Pacote  " + eNome + " para " + EscopoDono.getNome());

                    EscopoDono.referenciarEscopo(getPacote(eNome));


                    enc = true;
                    break;
                }

            }

            if (!enc) {
                mErros.add("Pacote " + eNome + " : nao encontrado !");
            }

        } else {
            mErros.add("Pacote " + eNome + " : ja esta sendo usado !");
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

    public ArrayList<AST> getGlobalPackages() {
        return mGlobalPackages;
    }

    public ArrayList<AST> getGlobalOperations() {
        return mGlobalOperacoes;
    }

    public ArrayList<AST> getGlobalDirectors() {
        return mGlobalDirectors;
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
                    } else if (ASTC.mesmoTipo("DIRECTOR")) {
                        mEstrutural.guardar(ASTC);
                    } else if (ASTC.mesmoTipo("OPERATOR")) {
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
                    } else if (ASTC.mesmoTipo("PACKAGE")) {
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




    public int getInstrucoes() {

        int ret = 0;

        for (AST ASTCGlobal : mASTS) {
            ret += ASTCGlobal.getInstrucoes();
        }

        return ret;
    }





}
