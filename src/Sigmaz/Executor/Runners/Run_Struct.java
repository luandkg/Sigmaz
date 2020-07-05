package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Alterador;
import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Argumentador;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Struct {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mNome;
    private Argumentador mPreparadorDeArgumentos;

    private String mStructNome;
    private int mTamanho;

    private AST mStructGeneric;
    private AST mStructCorpo;

    public Run_Struct(RunTime eRunTime) {

        mRunTime = eRunTime;
        //  mEscopo = eEscopo;

        mNome = "";
        mStructNome = "";

        mTamanho = 0;
        mStructGeneric = null;
        mStructCorpo = null;


        mPreparadorDeArgumentos = new Argumentador();

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

    public void init(String eNome, AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        mEscopo = new Escopo(mRunTime, null);
        mEscopo.setNome(eNome);
        mEscopo.setEstrutura(true);

        mStructCorpo = null;

        mStructNome = ASTCorrente.getNome();

        mEscopo.setNome(eNome);


        AST mStructInits = null;

        AST mAST_Struct = null;


        boolean enc = false;


        for (AST ASTC : mRunTime.getSigmaz().getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("ACTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("OPERATION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("CAST")) {
                mEscopo.guardar(ASTC);

            }

        }

     //   System.out.println(BuscadorDeArgumentos.getNome() + " -> Structs : " + BuscadorDeArgumentos.getStructs().size() );

        for (AST ASTC : BuscadorDeArgumentos.getStructs()) {

            if (ASTC.mesmoTipo("STRUCT")) {
                if (ASTC.mesmoNome(mStructNome)) {

                    mStructGeneric = ASTC.getBranch("GENERIC");
                    enc = true;

                    mAST_Struct = ASTC;

                    AST init_Extend = ASTC.getBranch("EXTENDED");

                    if (init_Extend.mesmoNome("STAGES")) {
                        mRunTime.getErros().add("Struct " + mStructNome + " : Nao pode ser instanciada !");
                        return;
                    } else if (init_Extend.mesmoNome("EXTERNAL")) {
                        mRunTime.getErros().add("Struct " + mStructNome + " : Nao pode ser instanciada !");
                        return;
                    }


                }

            }
        }

        if (!enc) {
            mRunTime.getErros().add("Struct " + mStructNome + " : Nao Encontrada !");
            return;
        }

        AST init_Generic = ASTCorrente.getBranch("GENERIC");
        String structTipagem = "";
        int StructContagem = 0;

        for (AST ASTC : mStructGeneric.getASTS()) {
            structTipagem += "<" + ASTC.getNome() + ">";
            StructContagem += 1;
        }

        //  System.out.println("Struct : " + mStructNome);
        //  System.out.println("\t - Abstrata : " + mStructGeneric.getNome());
        //   System.out.println("\t - Tipagem : " + structTipagem);


        String initTipagem = "";
        int initContagem = 0;

        for (AST ASTC : init_Generic.getASTS()) {
            initTipagem += "<" + getTipagem(ASTC) + ">";
            initContagem += 1;
        }

        mTipoCompleto = mStructNome + initTipagem;

        //  System.out.println("Init");
        //   System.out.println("\t - Abstrata : " + init_Generic.getNome());
        //    System.out.println("\t - Tipagem : " + initTipagem);

        mStructCorpo = mAST_Struct.getBranch("BODY").copiar();
        mStructInits = mAST_Struct.getBranch("INITS").copiar();


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

                mAlterador.alterar(mStructInits);
                mAlterador.alterar(mStructCorpo);



            } else {

                mRunTime.getErros().add("Struct " + mStructNome + " : Tipos abstratos nao conferem !");

            }


        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("FALSE")) {

        } else if (init_Generic.mesmoNome("FALSE") && mStructGeneric.mesmoNome("TRUE")) {
            mRunTime.getErros().add("Struct " + mStructNome + " : Precisa retornar uma Instancia Generica !");
        } else {
            mRunTime.getErros().add("Struct " + mStructNome + " : Nao e Generica !");
        }

        if (mRunTime.getExterno()) {

          //  System.out.println("########################### GENERIC ############################");
          //  mStructInits.ImprimirArvoreDeInstrucoes();
          //  mStructCorpo.ImprimirArvoreDeInstrucoes();
         //   System.out.println("########################### ####### ############################");

        }


        for (AST ASTC : mStructInits.getASTS()) {
            mEscopo.guardarStruct(ASTC);
            mEscopo.guardar(ASTC);
        }

        //  System.out.println("Inicializadores de " + mStructNome + " = " + mStructInits.getASTS().size());

        //  for (Index_Action mIndex_Function : mEscopo.getOO().getInits()) {
        //       System.out.println("\t - INIT :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());
        // }


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

        for (AST ASTC : mStructCorpo.getASTS()) {

            mTamanho += 1;

            if (ASTC.mesmoTipo("DEFINE")) {

                if (!getModo(ASTC).contentEquals("EXTERN")) {
                    Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mEscopo.guardarStruct(ASTC);
                }


            } else if (ASTC.mesmoTipo("MOCKIZ")) {

                if (!getModo(ASTC).contentEquals("EXTERN")) {

                    Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                    mAST.init(ASTC);

                    mEscopo.guardarStruct(ASTC);

                }


            }

        }

        mEscopo.criarConstanteStruct("this", mStructNome, mNome);


        if (mStructInits.getASTS().size() > 0) {
            Inicializador(mStructNome, ASTCorrente, BuscadorDeArgumentos);
        } else {

            if (ASTCorrente.getBranch("ARGUMENTS").getASTS().size() > 0) {
                mRunTime.getErros().add("Struct " + mStructNome + " nao possui Init com argumentos !");

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

    public String getTipagem(AST eAST){

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")){

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" +getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public void Inicializador(String eOrigem, AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeArgumentos, ASTCorrente.getBranch("ARGUMENTS"));

        boolean enc = false;
        boolean algum = false;


        // System.out.println("\t -->> Inicializando :  " + ASTCorrente.getNome());

        for (Index_Action mIndex_Function : mEscopo.getOO().getInits()) {


            if (mRunTime.getErros().size() > 0) {
                break;
            }
            enc = true;
            if (mIndex_Function.mesmoNome(eOrigem) && mIndex_Function.mesmoArgumentos(mArgumentos)) {


                algum = true;


                if (mRunTime.getErros().size() > 0) {
                    break;
                }

                int ai = 0;

                for (Item ArgumentoC : mArgumentos) {
                    ArgumentoC.setNome(mIndex_Function.getParamentos().get(ai));
                    ai += 1;
                }


                AST mInitCall = mIndex_Function.getPonteiro().getBranch("CALL");
                Escopo tmpEscopo = new Escopo(mRunTime, mEscopo);

                if (mInitCall.mesmoValor("TRUE")) {

                    Index_Action mIndex_Function3 = null;

                    int segundomax = 0;
                    for (AST mIndex_Function2 : mEscopo.getGuardadosCompleto()) {
                        if (mIndex_Function2.mesmoTipo("INIT") && mIndex_Function2.mesmoNome(mInitCall.getNome())) {

                            //  System.out.println("\t - Inicializador Interno :  " + mIndex_Function.getNome());

                            mIndex_Function3 = new Index_Action(mIndex_Function2);
                            segundomax = mIndex_Function3.getParamentos().size();

                        }
                    }


                    ai = 0;

                    for (Item ArgumentoC : mArgumentos) {
                        if (ai < segundomax) {
                            ArgumentoC.setNome(mIndex_Function3.getParamentos().get(ai));
                        }
                        // System.out.println("\t\t - Arg :  " + ArgumentoC.getNome() + " = " + ArgumentoC.getValor());
                        ai += 1;
                    }


                    for (Item ArgumentoC : mArgumentos) {
                        tmpEscopo.criarDefinicao(ArgumentoC.getNome(), ArgumentoC.getTipo(), ArgumentoC.getValor());
                    }

                    // System.out.println("\t - Chamador : :  " + mInitCall.getNome());

                    // tmpEscopo.ListarAll();
                    ArrayList<AST> mInitSub = getSubInits(tmpEscopo);

                    Sub_Inicializador(mInitCall.getNome(), mIndex_Function.getPonteiro(), mInitCall, tmpEscopo, mInitSub, mArgumentos);

                }

                mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                break;
            }


        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Init " + eOrigem + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Init  " + eOrigem + "." + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


    }

    private void Sub_Inicializador(String eOrigem, AST InicializadorAnterior, AST ASTCorrente, Escopo BuscadorDeArgumentos, ArrayList<AST> mInitSub, ArrayList<Item> mArgumentos) {

        ArrayList<String> mPrecisa = new ArrayList<String>();
        ArrayList<String> mAnterior = new ArrayList<String>();

        for (AST pIndex_Function : InicializadorAnterior.getBranch("ARGUMENTS").getASTS()) {
            //      System.out.println("\t      Anterior :  " + pIndex_Function.getNome() + " :: " + pIndex_Function.getValor());
            mAnterior.add(pIndex_Function.getNome());
        }

        //System.out.println("\t -->> Sub Inicializando :  " + eOrigem);
        for (AST pIndex_Function : ASTCorrente.getBranch("ARGUMENTS").getASTS()) {
            //   System.out.println("\t      Precisa :  " + pIndex_Function.getNome() + " :: " + pIndex_Function.getValor());
            mPrecisa.add(pIndex_Function.getNome());
        }

        // for (Item ArgumentoC : mArgumentos) {
        //     System.out.println("\t      Passou :  " + ArgumentoC.getNome() + " :: " + ArgumentoC.getTipo());
        // }

        ArrayList<Item> argumentos2 = new ArrayList<Item>();

        int i = 0;

        for (String Anterior : mAnterior) {

            if (mPrecisa.contains(Anterior)) {
                argumentos2.add(mArgumentos.get(i));
            }

            i += 1;
        }


        mArgumentos = argumentos2;

        boolean enc = false;
        boolean algum = false;

        for (AST pIndex_Function : mInitSub) {

            Index_Action mIndex_Function = new Index_Action(pIndex_Function);

            if (mRunTime.getErros().size() > 0) {
                break;
            }
            enc = true;
            if (mIndex_Function.mesmoNome(eOrigem) && mIndex_Function.mesmoArgumentos(mArgumentos)) {


                algum = true;

                //  System.out.println("\t -->> Sub Inicializando Enc :  " + mIndex_Function.getNome());

                if (mRunTime.getErros().size() > 0) {
                    break;
                }

                int ai = 0;


                Escopo gEscopo = new Escopo(mRunTime, BuscadorDeArgumentos);
                gEscopo.setNome(eOrigem);

                for (Item ArgumentoC : mArgumentos) {
                    gEscopo.criarItem(mIndex_Function.getParamentos().get(ai), ArgumentoC);
                    ai += 1;
                }

                // gEscopo.ListarAll();


                AST mInitCall = mIndex_Function.getPonteiro().getBranch("CALL");
                Escopo tmpEscopo = new Escopo(mRunTime, gEscopo);

                if (mInitCall.mesmoValor("TRUE")) {
                    Index_Action mIndex_Function3 = null;

                    int segundomax = 0;
                    for (AST mIndex_Function2 : mEscopo.getGuardadosCompleto()) {
                        if (mIndex_Function2.mesmoTipo("INIT") && mIndex_Function2.mesmoNome(mInitCall.getNome())) {

                            //  System.out.println("\t - Inicializador Interno :  " + mIndex_Function.getNome());

                            mIndex_Function3 = new Index_Action(mIndex_Function2);
                            segundomax = mIndex_Function3.getParamentos().size();

                        }
                    }


                    ai = 0;

                    for (Item ArgumentoC : mArgumentos) {
                        if (ai < segundomax) {
                            ArgumentoC.setNome(mIndex_Function3.getParamentos().get(ai));
                        }
                        ai += 1;
                    }


                    for (Item ArgumentoC : mArgumentos) {
                        tmpEscopo.criarDefinicao(ArgumentoC.getNome(), ArgumentoC.getTipo(), ArgumentoC.getValor());
                    }

                    // System.out.println("\t - Chamador : :  " + mInitCall.getNome());
                    // tmpEscopo.ListarAll();


                    Sub_Inicializador(mInitCall.getNome(), mIndex_Function.getPonteiro(), mInitCall, tmpEscopo, mInitSub, mArgumentos);


                }


                mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                break;
            }


        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Init " + eOrigem + "." + eOrigem + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Init  " + eOrigem + "." + eOrigem + " : Nao Encontrada !");
        }


    }


    private ArrayList<AST> getSubInits(Escopo tmpEscopo) {
        ArrayList<AST> mRet = new ArrayList<AST>();
        for (AST IA : tmpEscopo.getGuardadosCompleto()) {
            if (IA.mesmoTipo("INIT")) {
                mRet.add(IA);
            }
        }
        return mRet;
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
            mRunTime.getErros().add(mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
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
            mRunTime.getErros().add(mStructNome + "." + ASTCorrente.getNome() + " : Membro nao encontrado !");
        }

        return mRet;
    }

    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, String eRetorne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        return mRun_Any.init_Function(ASTCorrente, BuscadorDeVariaveis, mEscopo, eRetorne, mStructNome + "." + ASTCorrente.getNome(), mEscopo.getOO().getFunctions_All());

    }


    public void init_Action(AST ASTCorrente, Escopo BuscadorDeVariaveis) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Action(ASTCorrente, BuscadorDeVariaveis, mEscopo, mStructNome + "." + ASTCorrente.getNome(), mEscopo.getOO().getActions_All());

    }

    public void init_ActionFunction(AST ASTCorrente, Escopo BuscadorDeArgumentos) {

      //  System.out.println(BuscadorDeArgumentos.getNome() + " AF -> Structs : " + BuscadorDeArgumentos.getStructs().size() );


        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Action(ASTCorrente, BuscadorDeArgumentos, mEscopo, mStructNome + "." + ASTCorrente.getNome(), mEscopo.getOO().getActionsFunctions_All());

    }

}
