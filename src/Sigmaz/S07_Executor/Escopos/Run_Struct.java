package Sigmaz.S07_Executor.Escopos;

import Sigmaz.S07_Executor.Alterador;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S07_Executor.Runners.*;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Struct {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mNome;
    private Run_Arguments mPreparadorDeArgumentos;

    private String mStructNome;
    private int mTamanho;

    private AST mStructGeneric;
    private AST mStructCorpo;
    private AST mStructInits;

    private AST mBases;
    private String mBaseado;

    private ArrayList<String> mStack_All;
    private String mLocal;
    private ArrayList<String> mRefers;

    public Run_Struct(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Run_Struct";

        mNome = "";
        mStructNome = "";

        mTamanho = 0;
        mStructGeneric = null;
        mStructCorpo = null;
        mBases = null;
        mBaseado = "";

        mPreparadorDeArgumentos = new Run_Arguments();

        mStack_All = new ArrayList<String>();
        mRefers = new ArrayList<String>();
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

    public String getBaseado() {
        return mBaseado;
    }

    public void init(AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        mEscopo = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
        mEscopo.setEstrutura(true);

        mStructCorpo = null;
        mStructInits = null;

        mStructNome = ASTCorrente.getNome();

        long HEAPID =mRunTime.getHeap().getHEAPID();
        mNome = "<Struct::" + mStructNome + ":" + HEAPID + ">";


        mEscopo.setNome(mNome);

        mStack_All.clear();


        AST mAST_Struct = null;

        for (String e : mRefers) {
            mEscopo.adicionarRefer(e);
        }

        boolean enc = false;





        // EXTERNALIZAR
        for (Run_Explicit ASTC : BuscadorDeArgumentos.getExtern()) {
            mEscopo.externalizarDireto(ASTC);
        }


        //   System.out.println(BuscadorDeArgumentos.getNome() + " -> Structs : " + BuscadorDeArgumentos.getStructs().size() );

        // System.out.println("ESCOPO :: " + mEscopo.getNome());
        //  for (String eRefer : BuscadorDeArgumentos.getRefers()) {

        //   System.out.println( "\t   PAST REFER -->> " + eRefer);

        //   }
        //   for (AST ASTC : mRunTime.getStructsContexto(BuscadorDeArgumentos.getRefers())) {
        //      System.out.println( "\t   STRUCT -->> " + ASTC.getNome());
        //  }
        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST ASTC : mRun_Context.getStructsContexto(BuscadorDeArgumentos)) {

            if (ASTC.mesmoTipo("STRUCT")) {
                if (ASTC.mesmoNome(mStructNome)) {

                    // System.out.println(ASTC.ImprimirArvoreDeInstrucoes());


                    mStructGeneric = ASTC.getBranch("GENERIC");

                    mBases = ASTC.getBranch("BASES");

                    //System.out.println(mBases.ImprimirArvoreDeInstrucoes());

                    enc = true;

                    mAST_Struct = ASTC;

                    AST init_Extend = ASTC.getBranch("EXTENDED");

                    if (init_Extend.mesmoNome("STRUCT")) {

                        mBaseado = ASTC.getBranch("WITH").getNome();
                        //  System.out.println(mNome + " com " + mBaseado);

                        // System.out.println(mBases.ImprimirArvoreDeInstrucoes());

                    } else if (init_Extend.mesmoNome("TYPE")) {
                        mRunTime.errar(mLocal, "Type " + mStructNome + " : Nao pode ser instanciada como Struct !");
                        return;
                    } else if (init_Extend.mesmoNome("STAGES")) {
                        mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao pode ser instanciada !");
                        return;
                    } else if (init_Extend.mesmoNome("EXTERNAL")) {
                        mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao pode ser instanciada !");
                        return;
                    }


                }

            }
        }

        if (!enc) {
            mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao Encontrada !");
            return;
        }

        AST mRefers = mAST_Struct.getBranch("REFERS");
        ArrayList<String> dRefers = new ArrayList<String>();

        ArrayList<AST> eReferenciados = new ArrayList<AST>();

        for (AST ASTC : mRefers.getASTS()) {

            String eRefer = ASTC.getNome();

            eReferenciados.add(mRunTime.getPacote(eRefer));


            dRefers.add(eRefer);
            mEscopo.adicionarRefer(eRefer);

        }
        for (AST ASTC : eReferenciados) {

            for (AST mDentro : ASTC.getASTS()) {

                //    System.out.println("\t\t\t -> Recebendo " + mDentro.getNome());
                mEscopo.guardar(mDentro);

            }

        }

        AST init_Generic = ASTCorrente.getBranch("GENERIC");
        String structTipagem = "";
        int StructContagem = 0;

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, BuscadorDeArgumentos);


        for (String eref : BuscadorDeArgumentos.getRefers()) {
            mEscopo.adicionarReferOculto(eref);
        }
        for (String eref : BuscadorDeArgumentos.getRefersOcultas()) {
            mEscopo.adicionarReferOculto(eref);
        }

        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());

        for (AST ASTC : mStructGeneric.getASTS()) {
            structTipagem += "<" + mRun_GetType.getTipagem(ASTC) + ">";
            StructContagem += 1;
        }

        String initTipagem = "";
        int initContagem = 0;

        for (AST ASTC : init_Generic.getASTS()) {
            initTipagem += "<" + mRun_GetType.getTipagem(ASTC) + ">";
            initContagem += 1;

            // for(String eref : mEscopo.getRefersOcultas()){
            //     System.out.println("Me ref Oculto : " + eref + " da Struct " + mAST_Struct.getNome());
            // }

            // for(String eref : BuscadorDeArgumentos.getRefers()){
            //     System.out.println("Tem ref : " + eref+ " em Escopo " + BuscadorDeArgumentos.getNome());
            // }


            // System.out.println("Iniciando Struct " + mStructNome + " :: Generico = " + mRun_GetType.getTipagem(ASTC));
        }

        //  System.out.println("Struct : " + mStructNome);
        //System.out.println("\t - Init : " + mStructNome + initTipagem);
        // System.out.println("\t - Tipagem : " + mStructNome + structTipagem);

        String mTipando = "";


        for (AST ASTC : init_Generic.getASTS()) {
            mTipando += "<" + mRun_GetType.getTipagemSemAlteracao(ASTC) + ">";

        }


        mTipoCompleto = mRun_GetType.getTipagemSimples(mStructNome) + mTipando;

        //   System.out.println(" -->> TIPANDO STRUCT :: " + mTipoCompleto);


        mStructCorpo = mAST_Struct.getBranch("BODY").copiar();
        mStructInits = mAST_Struct.getBranch("INITS").copiar();


        AST mBase = mAST_Struct.getBranch("BASES");

        for (AST ASTC : mBase.getASTS()) {
            mEscopo.guardarStruct(ASTC);
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (init_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("TRUE")) {


            if (StructContagem == initContagem) {

                Alterador mAlterador = new Alterador();


                //  System.out.println("ALTERADOR " + eNome);

                int i = 0;
                //      for (AST eSub : mStructGeneric.getASTS()) {
                for (AST eSub : init_Generic.getASTS()) {

                    AST sInit = mStructGeneric.getASTS().get(i);

                    //   System.out.println("Alterando " + sInit.getNome() + " -> " + eSub.ImprimirArvoreDeInstrucoes());

                    mAlterador.adicionar(sInit.getNome(), eSub);

                    //   init_Generic.getBranch("TYPE").ImprimirArvoreDeInstrucoes();

                    i += 1;
                }

                mAlterador.alterar(mStructInits);
                mAlterador.alterar(mStructCorpo);


                // System.out.println(mStructInits.ImprimirArvoreDeInstrucoes());
                // System.out.println(mStructCorpo.ImprimirArvoreDeInstrucoes());


            } else {

                mRunTime.errar(mLocal, "Struct " + mStructNome + " : Tipos abstratos nao conferem !");

            }


        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("FALSE")) {

        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("TRUE")) {
            mRunTime.errar(mLocal, "Struct " + mStructNome + " : Precisa retornar uma Instancia Generica !");
        } else if (init_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("FALSE")) {


            if (init_Generic.getASTS().size() == 0) {

            } else {
                mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao e Generica !");
            }


        } else {
            mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao e Generica !");
        }

        if (mRunTime.getVisibilidade()) {

            //  System.out.println("########################### GENERIC ############################");
            //  mStructInits.ImprimirArvoreDeInstrucoes();
            //  mStructCorpo.ImprimirArvoreDeInstrucoes();
            //   System.out.println("########################### ####### ############################");

        }


        for (AST eAST : mStructCorpo.getASTS()) {

            if (eAST.mesmoTipo("OPERATOR")) {

                BuscadorDeArgumentos.guardar(eAST);

                //   System.out.println("Guardando Operador - " +eAST.ImprimirArvoreDeInstrucoes() );

            } else if (eAST.mesmoTipo("DIRECTOR")) {

                BuscadorDeArgumentos.guardar(eAST);

            }

        }

        //   System.out.println(" ----- OPERADOR" );

        // BuscadorDeArgumentos.getDebug().listar_Directors();


        for (AST ASTC : mStructInits.getASTS()) {
            mEscopo.guardarStruct(ASTC);
            mEscopo.guardar(ASTC);
        }

        //  System.out.println("Inicializadores de " + mStructNome + " = " + mStructInits.getASTS().size());

        //  for (Index_Action mIndex_Function : mEscopo.getOO().getInits()) {
        //       System.out.println("\t - INIT :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());
        // }

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
                eCopia.setValor("");

                AST_BODY.getASTS().add(0, eCopia);

                AST_Call.setValor("FALSE");
                AST_Call.getASTS().clear();

            }

        }


        //    System.out.println(mStructInits.ImprimirArvoreDeInstrucoes());
        //  System.out.println(mStructCorpo.ImprimirArvoreDeInstrucoes());


        if (mStructInits.getASTS().size() > 0) {

            //  System.out.println(" I - Iniciar STRUCT :: " + mNome);

            // for (AST ASTC : mStructInits.getASTS()) {
            //     System.out.println("\t G :: " + ASTC.getNome());
            // }

            // System.out.println("Iniciar STRUCT :: " + mStructNome);

            Inicializador(mStructNome, ASTCorrente, BuscadorDeArgumentos);

            //  System.out.println("Iniciado STRUCT :: " + mStructNome + " :: " + mRunTime.getErros().size());

            //  System.out.println(" I - Fim STRUCT :: " + mNome);

        } else {

            if (ASTCorrente.getBranch("ARGUMENTS").getASTS().size() > 0) {
                mRunTime.errar(mLocal, "Struct " + mStructNome + " nao possui Init com argumentos !");
            }

            // System.out.println("Argumentos " + ASTCorrente.getBranch("ARGUMENTS").getASTS().size());

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


    public void Inicializador(String eOrigem, AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeArgumentos, ASTCorrente.getBranch("ARGUMENTS"));

        boolean enc = false;
        boolean algum = false;

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("\t -->> Inicializando :  " + ASTCorrente.getNome());

        String mConferencia = mPreparadorDeArgumentos.getTipagem(mArgumentos);



        String mExigencia = "";

        for (Index_Action mIndex_Function : mEscopo.getOO().getInits_All()) {


            if (mRunTime.getErros().size() > 0) {
                break;
            }
            enc = true;

            mIndex_Function.resolverTipagem(mEscopo.getRefers());

            // System.out.println("Init :: " + mIndex_Function.getDefinicao());

            //   System.out.println("Escopo :: " + mEscopo.getNome());

            mExigencia = mIndex_Function.getParametragem();

            if (mIndex_Function.mesmoNome(eOrigem) && mIndex_Function.mesmoArgumentos(mEscopo, mArgumentos)) {


                algum = true;


                if (mRunTime.getErros().size() > 0) {
                    break;
                }

                int ai = 0;

                for (Item ArgumentoC : mArgumentos) {
                    ArgumentoC.setNome(mIndex_Function.getParamentos().get(ai));
                    ai += 1;
                }


                mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                break;
            } else {

                //  mConferencia = "";

            }


        }


        if (enc) {
            if (!algum) {
                mRunTime.errar(mLocal, "Init  " + eOrigem + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
                mRunTime.errar(mLocal, mExigencia + " vs " + mConferencia);
            }
        } else {
            mRunTime.errar(mLocal, "Init  " + eOrigem + "." + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


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

        // System.out.println("Operador Ponto -> " +mStructNome + "." + ASTCorrente.getNome()  );

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

        // System.out.println("Operador Ponto -> " +mStructNome + "." + ASTCorrente.getNome()  );

        //if (mStack_All.contains(ASTCorrente.getNome())){
        for (Item mItem : mEscopo.getOO().getStacks()) {


            if (mItem.getNome().contentEquals(ASTCorrente.getNome())) {
                mRet = mItem;
                enc = true;
                break;
            }

        }
        // }


        if (!enc) {
            mRunTime.errar(mLocal, mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
        }

        return mRet;
    }


    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        return mRun_Any.init_Function(ASTCorrente, BuscadorDeVariaveis, mEscopo, eRetorne, mEscopo.getOO().getFunctions_All());
        // return mRun_Any.init_Function(ASTCorrente, BuscadorDeVariaveis, mEscopo, eRetorne,mStructNome + "." + ASTCorrente.getNome(), mEscopo.getOO().getFunctions_All());

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

}
