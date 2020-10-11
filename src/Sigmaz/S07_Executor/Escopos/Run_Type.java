package Sigmaz.S07_Executor.Escopos;

import Sigmaz.S07_Executor.Alterador;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S07_Executor.Runners.*;
import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S00_Utilitarios.SemanticaPortugues;

public class Run_Type {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private String mNome;

    private String mStructNome;
    private AST mStructCorpo;
    private AST mStructGeneric;

    private String mTipoCompleto;
    private ArrayList<String> dRefers;

    private ArrayList<String> mAlocados;
    private AST mBases;

    public Run_Type(RunTime eRunTime) {

        mRunTime = eRunTime;
        mTipoCompleto = "";
        dRefers = new ArrayList<String>();
        mLocal = "Run_Type";

        mAlocados = new ArrayList<>();
        mBases = null;
    }

    public boolean mesmoNome(String eNome) {
        return mNome.contentEquals(eNome);
    }

    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public String getTypeNome() {
        return mStructNome;
    }

    public Escopo getEscopo() {
        return mEscopo;
    }

    public String getTipoCompleto() {
        return mTipoCompleto;
    }

    public void init(AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        mEscopo = new Escopo(mRunTime, BuscadorDeArgumentos);
        mEscopo.setEstrutura(true);

        mStructCorpo = null;
        mStructGeneric = null;


        mStructNome = ASTCorrente.getNome();

        long HEAPID = mRunTime.getHeap().getHEAPID();
        mNome = "<Type::" + ASTCorrente.getNome() + ":" + HEAPID + ">";

        mAlocados.clear();


        boolean enc = false;



        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST ASTC : mRun_Context.getTypesContexto(BuscadorDeArgumentos)) {

            if (ASTC.mesmoTipo("STRUCT")) {
                if (ASTC.mesmoNome(mStructNome)) {


                    mStructGeneric = ASTC.getBranch("GENERIC");
                    mBases = ASTC.getBranch("BASES");

                    enc = true;

                    mStructCorpo = ASTC.getBranch("BODY");
                    mStructGeneric = ASTC.getBranch("GENERIC");

                    AST init_Extend = ASTC.getBranch("EXTENDED");

                    if (init_Extend.mesmoNome("TYPE")) {

                    } else if (init_Extend.mesmoNome("STRUCT")) {
                        mRunTime.errar(mLocal, "Struct " + mStructNome + " : Nao pode ser instanciada como Type !");
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
            mRunTime.errar(mLocal, "Type " + mStructNome + " : Nao Encontrada !");
            return;
        }


        Run_GetType mRun_GetType = new Run_GetType(mRunTime, BuscadorDeArgumentos);


        AST init_Generic = ASTCorrente.getBranch("GENERIC");
        String structTipagem = "";
        int StructContagem = 0;

        for (AST ASTC : mStructGeneric.getASTS()) {
            structTipagem += "<" + mRun_GetType.getTipagem(ASTC) + ">";
            StructContagem += 1;
        }


        String initTipagem = "";
        int initContagem = 0;


        for (AST ASTC : init_Generic.getASTS()) {
            initTipagem += "<" + mRun_GetType.getTipagem(ASTC) + ">";
            initContagem += 1;
        }


        if (mStructCorpo.existeBranch("REFERS")) {
            AST mRefers = mStructCorpo.getBranch("REFERS");
            dRefers = new ArrayList<String>();

            for (AST ASTC : mRefers.getASTS()) {
                String eRefer = ASTC.getNome();
                dRefers.add(eRefer);
            }
        }


        String mTipando = "";

        for (AST ASTC : init_Generic.getASTS()) {
            mTipando += "<" + mRun_GetType.getTipagem(ASTC) + ">";

        }

        mTipoCompleto = mRun_GetType.getTipagemSimples(mStructNome) + mTipando;

        // System.out.println(" -->> TYPE :: " + mTipoCompleto);


        if (init_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("TRUE")) {


            if (StructContagem == initContagem) {

                Alterador mAlterador = new Alterador();

                int i = 0;
                for (AST eSub : mStructGeneric.getASTS()) {

                    mAlterador.adicionar(eSub.getNome(), init_Generic.getASTS().get(i).copiar());

                    //   System.out.println("Alterando " + eSub.getNome() + " -> " + getTipagem( init_Generic.getASTS().get(i)) );
                    //   init_Generic.getBranch("TYPE").ImprimirArvoreDeInstrucoes();

                    i += 1;
                }

                mAlterador.alterar(mStructCorpo);

                //  System.out.println(mStructCorpo.ImprimirArvoreDeInstrucoes());

            } else {

                String frase_precisa = SemanticaPortugues.getConcordancia(mStructGeneric.getASTS().size(),"tipo","tipos");
                String frase_precisagenericos = SemanticaPortugues.getConcordancia(mStructGeneric.getASTS().size(),"generico","genericos");

                String frase_recebeu = SemanticaPortugues.getConcordancia(init_Generic.getASTS().size(),"tipo","tipos");
                String frase_recebeugenericos = SemanticaPortugues.getConcordancia(init_Generic.getASTS().size(),"generico","genericos");

                String frase_precisafinal = frase_precisa + " " + frase_precisagenericos + " !";
                String frase_recebeufinal = frase_recebeu + " " + frase_recebeugenericos + " !";


                mRunTime.errar(mLocal, "Type " + mStructNome + " : Tipos abstratos nao conferem !");
                mRunTime.errar(mLocal, "Type " + mStructNome + " : Precisa de " + mStructGeneric.getASTS().size() + " " +frase_precisafinal);
                mRunTime.errar(mLocal, "Type " + mStructNome + " : Recebeu " + init_Generic.getASTS().size() + " " + frase_recebeufinal);

            }


        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("FALSE")) {

        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("TRUE")) {


            String eGenericoTexto = "";

            int o = mStructGeneric.getASTS().size();
            int i = 1;

            for (AST eB : mStructGeneric.getASTS()) {
                if (i == o) {
                    eGenericoTexto += eB.getNome();
                } else {
                    eGenericoTexto += eB.getNome()+",";
                }

                i += 1;
            }

            eGenericoTexto =mStructNome + " <" + eGenericoTexto + ">";

            mRunTime.errar(mLocal, "Type " + mStructNome + " : Precisa retornar uma Instancia Generica " + eGenericoTexto);


        } else if (init_Generic.mesmoNome("TRUE") && mStructGeneric.mesmoNome("FALSE")) {


            if (init_Generic.getASTS().size() == 0) {

            } else {
                mRunTime.errar(mLocal, "Type " + mStructNome + " : Nao e Generica !");
            }


        } else {
            mRunTime.errar(mLocal, "Type " + mStructNome + " : Nao e Generica !");
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        for (AST ASTC : mStructCorpo.getASTS()) {


            if (ASTC.mesmoTipo("DEFINE")) {

                adicionarCampo(ASTC.getNome());

                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.init(ASTC);

                mEscopo.guardarStruct(ASTC);


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                adicionarCampo(ASTC.getNome());

                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                mAST.init(ASTC);

                mEscopo.guardarStruct(ASTC);


            }

        }

        for (AST mDentro : ASTCorrente.getBranch("STARTED").getASTS()) {

            if (!getCampos().contains(mDentro.getBranch("SETTABLE").getNome())) {
                mRunTime.errar(mLocal, mStructNome + "." + mDentro.getBranch("SETTABLE").getNome() + " : Membro nao existe !");
                return;
            }

            Run_Apply mRUA = new Run_Apply(mRunTime, mEscopo);
            mRUA.init(mDentro);

        }

        //mEscopo.ListarAll();


    }


    public ArrayList<String> getBases() {
        ArrayList<String> mB = new ArrayList<String>();

        for (AST eAST : mBases.getASTS()) {
            mB.add(eAST.getNome());
        }

        return mB;
    }


    public void adicionarCampo(String eCampo) {

        mAlocados.add(eCampo);

    }

    public void setAnterior(Escopo aEscopo) {
        mEscopo.setAnterior(aEscopo);
    }

    public ArrayList<String> getCampos() {
        return mAlocados;
    }

    public Item init_Object(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        if (BuscadorDeVariaveis == null) {
            mRunTime.errar(mLocal, mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
            return null;
        }

        boolean enc = false;
        Item mRet = null;

        for (Item mItem : mEscopo.getStacks()) {

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


}
