package Sigmaz.S05_Executor.Escopos;

import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S05_Executor.*;

import java.util.ArrayList;

import Sigmaz.S05_Executor.Runners.*;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Struct {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mNome;
    private Run_Arguments mPreparadorDeArgumentos;

    private String mStructNome;
    private int mTamanho;

    private AST mAST_Struct;

    private AST mStructGeneric;
    private AST mStructCorpo;
    private AST mStructInits;
    private AST mDestruct;

    private AST mBases;
    private AST mModelos;

    private String mBaseado;

    private ArrayList<String> mStack_All;
    private String mLocal;
    private ArrayList<String> mRefers;
    private int mRefs;

    private String mHerdadeDe;

    public Run_Struct(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Run_Struct";

        mNome = "";
        mStructNome = "";

        mTamanho = 0;
        mStructGeneric = null;
        mStructCorpo = null;
        mBases = null;
        mModelos = null;
        mBaseado = "";
        mHerdadeDe = "";

        mPreparadorDeArgumentos = new Run_Arguments();

        mStack_All = new ArrayList<String>();
        mRefers = new ArrayList<String>();
        mRefs = 0;

    }

    public int getRefs() {
        return mRefs;
    }

    public void refAumentar() {
        mRefs += 1;
    }

    public void refDiminuir() {
        if (mRefs > 0) {
            mRefs -= 1;
        }
    }


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public String getStructNome() {
        return mStructNome;
    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public ArrayList<String> getStack_All() {
        return mStack_All;
    }

    public boolean mesmoNome(String eNome) {
        return mNome.contentEquals(eNome);
    }

    public int getTamanho() {
        return mTamanho;
    }

    public String getModo(AST eAST) {
        return eAST.getBranch("VISIBILITY").getNome();
    }

    private String mTipoCompleto;

    public String getTipoCompleto() {
        return mTipoCompleto;
    }


    public void adicionar_refer(String eRefer) {
        mRefers.add(eRefer);
    }


    public ArrayList<String> getBases() {
        ArrayList<String> mB = new ArrayList<String>();

        for (AST eAST : mBases.getASTS()) {
            mB.add(eAST.getNome());
        }

        return mB;
    }

    public ArrayList<String> getModelos() {
        ArrayList<String> mB = new ArrayList<String>();

        for (AST eAST : mModelos.getASTS()) {
            mB.add(eAST.getNome());
        }

        return mB;
    }

    public String getBaseado() {
        return mBaseado;
    }

    public void init(AST mIniter, Escopo BuscadorDeArgumentos) {

        mEscopo = new Escopo(mRunTime, mRunTime.getEscopoGlobal());

        mStructCorpo = null;
        mStructInits = null;

        mStructNome = mIniter.getNome();

        long HEAPID = mRunTime.getHeap().getHEAPID();
        mNome = "<Struct::" + mStructNome + ":" + HEAPID + ">";


        mEscopo.setNome(mNome);

        mStack_All.clear();


        for (String e : mRefers) {
            mEscopo.adicionarRefer(e);
        }


        // EXTERNALIZAR
        for (Run_External ASTC : BuscadorDeArgumentos.getExtern()) {
            mEscopo.externalizarDireto(ASTC);
        }


        boolean enc = procurarStruct(BuscadorDeArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (!enc) {
            mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao Encontrada !");
            return;
        }


        Run_Refer mRun_Refer = new Run_Refer(mRunTime, mEscopo);
        mRun_Refer.init(mAST_Struct.getBranch("REFERS"));


        AST init_Generic = mIniter.getBranch("GENERIC");

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, BuscadorDeArgumentos);


        mEscopo.adicionarReferDe(BuscadorDeArgumentos);

        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());


        mRun_GetType.tiparMultiplo(mStructGeneric);


        mTipoCompleto = mRun_GetType.getTipagemSimples(mStructNome) + mRun_GetType.semTiparMultiplo(init_Generic);


        mStructCorpo = mAST_Struct.getBranch("BODY").copiar();
        mStructInits = mAST_Struct.getBranch("INITS").copiar();


        AST mBase = mAST_Struct.getBranch("BASES");

        for (AST ASTC : mBase.getASTS()) {
            mEscopo.guardarStruct(ASTC);
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        struct_Generic(init_Generic);


        for (AST eAST : mStructCorpo.getASTS()) {

            if (eAST.mesmoTipo("OPERATOR")) {

                BuscadorDeArgumentos.guardar(eAST);

            } else if (eAST.mesmoTipo("DIRECTOR")) {

                BuscadorDeArgumentos.guardar(eAST);

            }

        }


        for (AST ASTC : mStructInits.getASTS()) {
            mEscopo.guardarStruct(ASTC);
            mEscopo.guardar(ASTC);
            //   System.out.println("Passando Init : " + ASTC.getNome());
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        for (AST ASTC : mStructCorpo.getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                mEscopo.guardar(ASTC);

                mEscopo.guardarStruct(ASTC);

            } else if (ASTC.mesmoTipo("ACTION")) {
                mEscopo.guardar(ASTC);

                mEscopo.guardarStruct(ASTC);

            } else if (ASTC.mesmoTipo("OPERATION")) {
                mEscopo.guardar(ASTC);
                mEscopo.guardarStruct(ASTC);

            } else if (ASTC.mesmoTipo("CAST")) {
                mEscopo.guardar(ASTC);
                mEscopo.guardarStruct(ASTC);

            } else if (ASTC.mesmoTipo("GETTER")) {
                mEscopo.guardar(ASTC);
                mEscopo.guardarStruct(ASTC);

            } else if (ASTC.mesmoTipo("SETTER")) {
                mEscopo.guardar(ASTC);
                mEscopo.guardarStruct(ASTC);
            }

        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        for (AST ASTC : mStructCorpo.getASTS()) {

            mTamanho += 1;

            if (ASTC.mesmoTipo("DEFINE")) {

                if (!getModo(ASTC).contentEquals("EXPLICIT")) {
                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mEscopo.guardarStruct(ASTC);

                    if (getModo(ASTC).contentEquals("ALL")) {
                        mStack_All.add(ASTC.getNome());
                    }
                }


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                if (!getModo(ASTC).contentEquals("EXPLICIT")) {

                    Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mEscopo.guardarStruct(ASTC);

                    if (getModo(ASTC).contentEquals("ALL")) {
                        mStack_All.add(ASTC.getNome());
                    }
                }


            }

        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mEscopo.criarConstanteStruct("this", mTipoCompleto, mNome);
        mStack_All.add("this");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        for (AST ASTC : mStructInits.getASTS()) {

            AST AST_Call = ASTC.getBranch("CALL");
            AST AST_BODY = ASTC.getBranch("BODY");

            if (AST_Call.mesmoValor("TRUE")) {

                AST eCopia = AST_Call.copiar();

                eCopia.setTipo("EXECUTE_INIT");
                eCopia.setValor(mHerdadeDe);

                AST_BODY.getASTS().add(0, eCopia);

                AST_Call.setValor("FALSE");
                AST_Call.getASTS().clear();

            }

        }


        if (mStructInits.getASTS().size() > 0) {


            Run_Any mRA = new Run_Any(mRunTime);

            mRA.Inicializador(mStructNome, mIniter, BuscadorDeArgumentos, mEscopo, mLocal);


        } else {

            if (mIniter.getBranch("ARGUMENTS").getASTS().size() > 0) {
                mRunTime.errar(mLocal, "Struct " + mStructNome + " nao possui Init com argumentos !");
            }

        }


    }


    public boolean procurarStruct(Escopo BuscadorDeArgumentos) {

        boolean enc = false;

        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST ASTC : mRun_Context.getStructsContexto(BuscadorDeArgumentos)) {

            if (ASTC.mesmoTipo("STRUCT")) {
                if (ASTC.mesmoNome(mStructNome)) {


                    mHerdadeDe = ASTC.getBranch("WITH").getNome();

                    mStructGeneric = ASTC.getBranch("GENERIC");

                    mBases = ASTC.getBranch("BASES");
                    mModelos = ASTC.getBranch("MODELS");

                    mDestruct = ASTC.getBranch("DESTRUCT");

                    enc = true;

                    mAST_Struct = ASTC;

                    if (ASTC.existeBranch("COMPLEMENT")) {
                        if (ASTC.getBranch("COMPLEMENT").mesmoNome("INHENTABLE")) {
                            mRunTime.errar(mLocal, "A Struct " + mStructNome + " : Nao pode ser instanciada porque e apenas herdavel ! !");
                            return false;
                        }
                    }


                    AST init_Extend = ASTC.getBranch("EXTENDED");

                    if (init_Extend.mesmoNome("STRUCT")) {

                        mBaseado = ASTC.getBranch("WITH").getNome();

                    } else if (init_Extend.mesmoNome("TYPE")) {
                        mRunTime.errar(mLocal, "Type " + mStructNome + " : Nao pode ser instanciada como Struct !");
                    } else if (init_Extend.mesmoNome("STAGES")) {
                        mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao pode ser instanciada !");
                    } else if (init_Extend.mesmoNome("EXTERNAL")) {
                        mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao pode ser instanciada !");
                    }


                }

            }
        }

        return enc;
    }


    public void struct_Generic(AST inicializador_Generic) {

        Utilitario mu = new Utilitario();


        int StructContagem = mStructGeneric.getASTS().size();
        int initContagem = inicializador_Generic.getASTS().size();


        String mAbstratos = mu.getAbastratos(mStructGeneric);


        if (inicializador_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("TRUE")) {


            if (StructContagem == initContagem) {

                Alterador mAlterador = new Alterador();


                //  System.out.println("ALTERADOR " + eNome);

                int i = 0;
                //      for (AST eSub : mStructGeneric.getASTS()) {
                for (AST eSub : inicializador_Generic.getASTS()) {

                    AST sInit = mStructGeneric.getASTS().get(i);

                    //   System.out.println("Alterando " + sInit.getNome() + " -> " + eSub.ImprimirArvoreDeInstrucoes());

                    mAlterador.adicionar(sInit.getNome(), eSub);

                    //   init_Generic.getBranch("TYPE").ImprimirArvoreDeInstrucoes();

                    i += 1;
                }

                mAlterador.alterar(mStructInits);
                mAlterador.alterar(mStructCorpo);
                mAlterador.alterar(mDestruct);


                // System.out.println(mStructInits.ImprimirArvoreDeInstrucoes());
                // System.out.println(mStructCorpo.ImprimirArvoreDeInstrucoes());


            } else {

                mRunTime.errar(mLocal, "Struct " + mStructNome + " : Tipos abstratos nao conferem !");
                mRunTime.errar(mLocal, "Struct " + mStructNome + " : Precisa de implementar os tipos abstratos " + mAbstratos);

            }


        } else if (inicializador_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("FALSE")) {

        } else if (inicializador_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("TRUE")) {
            mRunTime.errar(mLocal, "Struct " + mStructNome + " : Precisa retornar uma Instancia Generica !");
        } else if (inicializador_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("FALSE")) {


            if (inicializador_Generic.getASTS().size() == 0) {

            } else {
                mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao e Generica !");
            }


        } else {
            mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao e Generica !");
        }

    }

    public ArrayList<AST> getFunctions() {

        ArrayList<AST> ls = new ArrayList<AST>();

        for (AST ASTC : mStructCorpo.getASTS()) {
            if (ASTC.mesmoTipo("FUNCTION")) {
                ls.add(ASTC);
            }

        }
        return ls;

    }


    public ArrayList<AST> getActions() {

        ArrayList<AST> ls = new ArrayList<AST>();

        for (AST ASTC : mStructCorpo.getASTS()) {
            if (ASTC.mesmoTipo("ACTION")) {
                ls.add(ASTC);
            }

        }
        return ls;

    }

    public Item init_Object(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.errar(mLocal, mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
            return null;
        }

        boolean enc = false;
        Item mRet = null;


        if (mStack_All.contains(ASTCorrente.getNome())) {
            for (Item mItem : mEscopo.getOO().getStacks()) {


                if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                    mRet = mItem;
                    enc = true;
                    break;
                }

            }
        }


        if (!enc) {
            mRunTime.errar(mLocal, mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
        }

        return mRet;
    }

    public Item init_ObjectDireto(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.errar(mLocal, mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
            return null;
        }

        boolean enc = false;
        Item mRet = null;


        for (Item mItem : mEscopo.getOO().getStacks()) {


            if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                mRet = mItem;
                enc = true;
                break;
            }

        }


        if (!enc) {
            mRunTime.errar(mLocal, mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
        }

        return mRet;
    }


    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        Item eItem = mRun_Any.init_Function(ASTCorrente, BuscadorDeVariaveis, mEscopo, eRetorne, mEscopo.getOO().getFunctions_All());

        if (eRetorne.contentEquals("<<ANY>>")) {
            eRetorne = eItem.getTipo();
        }

        if (eItem.getTipo().contentEquals(eRetorne)) {
        } else {
            mRunTime.errar("Run_Arguments", "Era esperado retornar " + eRetorne + " mas retornou " + eItem.getTipo());
        }

        return eItem;

    }

    public Item init_FunctionDireto(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        return mRun_Any.init_Function(ASTCorrente, BuscadorDeVariaveis, mEscopo, eRetorne, mEscopo.getOO().getFunctions());

    }

    public void init_ActionFunction(AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        //  System.out.println(BuscadorDeArgumentos.getNome() + " AF -> Structs : " + BuscadorDeArgumentos.getStructs().size() );


        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Action(ASTCorrente, BuscadorDeArgumentos, mEscopo, mStructNome + "." + ASTCorrente.getNome(), mEscopo.getOO().getActionsFunctions_All());

    }

    public void init_ActionFunctionDireto(AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        //  System.out.println(BuscadorDeArgumentos.getNome() + " AF -> Structs : " + BuscadorDeArgumentos.getStructs().size() );


        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Action(ASTCorrente, BuscadorDeArgumentos, mEscopo, mStructNome + "." + ASTCorrente.getNome(), mEscopo.getOO().getActionsFunctions());

    }


    public Item init_Getter(String eNome, AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        return mRun_Any.init_Getter(ASTCorrente, this.getStructNome(), BuscadorDeVariaveis, mEscopo, eRetorne, mEscopo.getOO().getColGet_All());

    }

    public Item init_Getter_This(String eNome, AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        return mRun_Any.init_Getter(ASTCorrente, this.getStructNome(), BuscadorDeVariaveis, mEscopo, eRetorne, mEscopo.getOO().getColGet());

    }

    public void init_Setter(AST ASTCorrente, Escopo BuscadorDeVariaveis, Run_Value eRun_Value) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Setter(ASTCorrente, this.getStructNome(), BuscadorDeVariaveis, mEscopo, eRun_Value, mEscopo.getOO().getSetter());

    }

    public void init_Setter_This(AST ASTCorrente, Escopo BuscadorDeVariaveis, Run_Value eRun_Value) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Setter(ASTCorrente, this.getStructNome(), BuscadorDeVariaveis, mEscopo, eRun_Value, mEscopo.getOO().getSetter());

    }

    public void destruct() {

        //   System.out.println("DESTRUIR :: " + this.getNome());

        //  System.out.println(mStructCorpo.getImpressao());

        Escopo mEscopoInterno = new Escopo(mRunTime, this.getEscopo());
        mEscopoInterno.setNome(this.getNome() + "::DESTRUCT");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mDestruct);


    }


    public void debug() {

        if (mRunTime.getVisibilidade()) {


            System.out.println("################# STRUCT : " + mNome + " ##########################");

            mapear_stack();


        }


    }

    public void mapear_stack() {


        ArrayList<Item> ls_Defines = new ArrayList<>();
        ArrayList<Item> ls_Constants = new ArrayList<>();

        for (Item i : mEscopo.getStacks()) {
            if (i.getModo() == 0) {
                ls_Defines.add(i);

            } else if (i.getModo() == 1) {
                ls_Constants.add(i);
            }

            // System.out.println("DEFINICAO :: " + i.getNome());
        }

        System.out.println(" - DEFINES : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Defines) {
            if (!i.getNulo()) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Defines) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }

        System.out.println(" - CONSTANTS : ");


        System.out.println("\t - NAO NULOS : ");

        for (Item i : ls_Constants) {
            if (i.getNulo() == false) {
                mostrarItem(i);

            }
        }


        System.out.println("\t - NULOS : ");
        for (Item i : ls_Constants) {

            if (i.getNulo()) {
                mostrarItem(i);

            }
        }

    }

    public void mostrarItem(Item eItem) {


        if (eItem.getNulo()) {
            System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = NULL");
        } else {


            if (eItem.getIsEstrutura()) {


                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " -> " + eItem.getValor(mEscopo.getRunTime(), mEscopo));


            } else {
                System.out.println("\t\t - " + eItem.getNome() + " : " + eItem.getTipo() + " = " + eItem.getValor(mEscopo.getRunTime(), mEscopo));
            }

        }


    }


    public boolean temEssaBase(String eQualBase) {

        boolean temHeranca = false;

        // System.out.println("\t " + this.getNome() + " : " + this.getStructNome() + " :: Procurando Base --> " + eQualBase);

        if (getStructNome().contentEquals(eQualBase)) {
            temHeranca = true;
        } else {
            for (String eBase : getBases()) {

                //   System.out.println("\t " + this.getNome() + " :: BASE ->> " + eBase);

                if (eBase.contentEquals(eQualBase)) {
                    temHeranca = true;
                    break;
                }
            }

        }

        return temHeranca;

    }

    public boolean temEsseModelo(String eQualModelo) {


        boolean temModelo = false;

        // System.out.println("\t " + this.getNome() + " :: Procurando Modelos");
        //  System.out.println(mModelos.getImpressao());

        if (getStructNome().contentEquals(eQualModelo)) {
            temModelo = true;
        } else {
            for (String eBase : getModelos()) {

                //   System.out.println("\t " + this.getNome() + " :: BASE ->> " + eBase);

                if (eBase.contentEquals(eQualModelo)) {
                    temModelo = true;
                    break;
                }
            }

        }


        return temModelo;

    }


}
