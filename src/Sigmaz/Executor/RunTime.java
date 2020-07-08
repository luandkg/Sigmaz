package Sigmaz.Executor;

import Sigmaz.Executor.Runners.*;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.Documentador;
import Sigmaz.Utils.Documento;
import Sigmaz.Utils.Texto;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;

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

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {

                ASTSigmaz = ASTCGlobal;
                enc = true;

            }


        }

        if (enc) {

            Escopo Global = new Escopo(this, null);
            Global.setNome("GLOBAL");

            mEscopoGlobal = Global;

            Run eRun = new Run(this);
            eRun.runSigmaz(ASTSigmaz, Global);

        } else {
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

    public ArrayList<AST> getStructsContexto(ArrayList<String> mPacotes) {
        ArrayList<AST> ret = new ArrayList<AST>();

        for (AST eAST : mGlobalStructs) {
            ret.add(eAST);
        }

        for (String Referencia : mPacotes) {

            if (existePacote(Referencia)) {

                for (AST eAST : getPacote(Referencia).getASTS()) {
                    if (eAST.mesmoTipo("STRUCT")) {
                        //  System.out.println(" \t\t - " + eAST.getTipo() + " :  " + eAST.getNome());
                        ret.add(eAST);
                    }
                }

            } else {
                mErros.add("PACKAGE  " + Referencia + " : Nao encontrado !");
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

    public void grafico(String eLocal) {

        limpar();


        Documento DocumentoC = new Documento();

        DocumentoC.adicionarLinha("@startuml");
        DocumentoC.adicionarLinha("skinparam class {");
        DocumentoC.adicionarLinha("BackgroundColor White");
        DocumentoC.adicionarLinha("BorderColor Black");
        DocumentoC.adicionarLinha("HeaderBackgroundColor White");
        DocumentoC.adicionarLinha(" }");
        DocumentoC.adicionarLinha("skinparam stereotypeCBackgroundColor White");
        DocumentoC.adicionarLinha("skinparam minClassWidth 200");

        for (AST ASTCGlobal : mASTS) {

            if (ASTCGlobal.mesmoTipo("SIGMAZ")) {


                 colocarGlobal(DocumentoC, ASTCGlobal);

                for (AST ASTC : ASTCGlobal.getASTS()) {


                    if (ASTC.mesmoTipo("FUNCTION")) {

                    } else if (ASTC.mesmoTipo("ACTION")) {

                    } else if (ASTC.mesmoTipo("DIRECTOR")) {

                    } else if (ASTC.mesmoTipo("OPERATOR")) {

                    } else if (ASTC.mesmoTipo("CAST")) {

                    } else if (ASTC.mesmoTipo("TYPE")) {

                        colocarType(DocumentoC, "SIGMAZ", ASTC);

                    } else if (ASTC.mesmoTipo("STRUCT")) {

                            colocarStruct(DocumentoC, "SIGMAZ", ASTC);

                    } else if (ASTC.mesmoTipo("CALL")) {

                    } else if (ASTC.mesmoTipo("DEFINE")) {

                    } else if (ASTC.mesmoTipo("MOCKIZ")) {

                    } else if (ASTC.mesmoTipo("PACKAGE")) {

                        for (AST Sub : ASTC.getASTS()) {

                            if (Sub.mesmoTipo("STRUCT")) {

                           //     colocarStruct(DocumentoC, ASTC.getNome(), Sub);

                            }


                        }


                    }

                }


            }
        }


        DocumentoC.adicionarLinha("@enduml");


        Texto.Escrever(eLocal, DocumentoC.getConteudo());


    }


    public void colocarGlobal(Documento DocumentoC, AST Sub) {

        DocumentoC.adicionarLinha("class SIGMAZ.SIGMAZ <<(S,Red) >> {");


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + getTipagem(Sub2.getBranch("TYPE")));
            }
            if (Sub2.mesmoTipo("MOCKIZ")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + getTipagem(Sub2.getBranch("TYPE")));
            }

        }


        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                DocumentoC.adicionarLinha("#" + Sub2.getNome() + " (" + getParametragem(Sub2) + ")");
            }

        }
        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("FUNCTION")) {
                DocumentoC.adicionarLinha("+" + Sub2.getNome() + " (" + getParametragem(Sub2) + ") : " + getTipagem(Sub2.getBranch("TYPE")));
            }
        }

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {
                DocumentoC.adicionarLinha("~" + Sub2.getNome() + " (" + getParametragem(Sub2) + ") : " + getTipagem(Sub2.getBranch("TYPE")));
            }
        }

        DocumentoC.adicionarLinha("}");


    }

    public String getParametragem(AST eAST) {
        String ret = "";

        int total = eAST.getBranch("ARGUMENTS").getASTS().size();

        if (total > 0) {


            for (int ii = 0; ii < total; ii++) {
                AST eArg = eAST.getBranch("ARGUMENTS").getASTS().get(ii);

                if (ii < total - 1) {
                    ret += eArg.getNome() + " : " + getTipagem(eArg.getBranch("TYPE")) + " , ";
                } else {
                    ret += eArg.getNome() + " : " + getTipagem(eArg.getBranch("TYPE")) + "";
                }

            }

        } else {
            ret = " ";

        }


        return ret;
    }

    public void colocarType(Documento DocumentoC, String ePacote, AST Sub) {
        DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Blue) >> {");

        for (AST Sub2 : Sub.getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + getTipagem(Sub2.getBranch("TYPE")));
            }
            if (Sub2.mesmoTipo("MOCKIZ")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + getTipagem(Sub2.getBranch("TYPE")));
            }

        }
        DocumentoC.adicionarLinha("}");

    }


    public void colocarStruct(Documento DocumentoC, String ePacote, AST Sub) {

        if (Sub.getBranch("EXTENDED").mesmoNome("STAGES")) {
            DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Orange) >> {");

            for (AST Sub2 : Sub.getBranch("STAGES").getASTS()) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + "");
            }

        } else if (Sub.getBranch("EXTENDED").mesmoNome("TYPE")) {
            DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Blue) >> {");
        } else {
            DocumentoC.adicionarLinha("class " + ePacote + "." + Sub.getNome() + " <<(S,Green) >> {");
        }


        if (Sub.existeBranch("INITS")) {
            for (AST Sub2 : Sub.getBranch("INITS").getASTS()) {
                if (Sub2.mesmoNome(Sub.getNome())) {
                    DocumentoC.adicionarLinha("~" + Sub2.getNome() + " (" + getParametragem(Sub2) + ")");
                }
            }
        }

        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("DEFINE")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + getTipagem(Sub2.getBranch("TYPE")));
            }
            if (Sub2.mesmoTipo("MOCKIZ")) {
                DocumentoC.adicionarLinha("<img:https://i.stack.imgur.com/p76Bx.gif>" + Sub2.getNome() + " : " + getTipagem(Sub2.getBranch("TYPE")));
            }

        }

        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("ACTION")) {
                DocumentoC.adicionarLinha("#" + Sub2.getNome() + " (" + getParametragem(Sub2) + ")");
            }

        }
        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {


            if (Sub2.mesmoTipo("FUNCTION")) {
                DocumentoC.adicionarLinha("+" + Sub2.getNome() + " (" + getParametragem(Sub2) + ") : " + getTipagem(Sub2.getBranch("TYPE")));
            }
        }

        for (AST Sub2 : Sub.getBranch("BODY").getASTS()) {

            if (Sub2.mesmoTipo("OPERATOR")) {
                DocumentoC.adicionarLinha("~" + Sub2.getNome() + " (" + getParametragem(Sub2) + ") : " + getTipagem(Sub2.getBranch("TYPE")));
            }
        }


        DocumentoC.adicionarLinha("}");

    }

    public void estrutura() {

        limpar();

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

    public String getData() {

        Calendar c = Calendar.getInstance();

        int dia = c.get(Calendar.DAY_OF_MONTH);
        int mes = c.get(Calendar.MONTH) + 1;
        int ano = c.get(Calendar.YEAR);

        int hora = c.get(Calendar.HOUR);
        int minutos = c.get(Calendar.MINUTE);
        int segundos = c.get(Calendar.SECOND);

        return dia + "/" + mes + "/" + ano + " " + hora + ":" + minutos + ":" + segundos;

    }


}
