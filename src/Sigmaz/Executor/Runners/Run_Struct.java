package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Argumentador;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Def;
import Sigmaz.Executor.Runners.Run_Moc;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Struct {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mNome;
    private Argumentador mPreparadorDeArgumentos;

    private String mStructNome;
    private int mTamanho;

    private AST mStructCorpo;

    public Run_Struct(RunTime eRunTime) {

        mRunTime = eRunTime;
        //  mEscopo = eEscopo;

        mNome = "";
        mStructNome = "";

        mTamanho = 0;
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


    public void init(String eNome, AST ASTCorrente, Escopo BuscadorDeArgumentos) {

        mEscopo = new Escopo(mRunTime, null);
        mEscopo.setNome(eNome);
        mEscopo.setEstrutura(true);

        mStructCorpo = null;

        mStructNome = eNome;

        mEscopo.setNome(eNome);

        AST mStructInits = null;


        for (AST ASTC : mRunTime.getSigmaz().getASTS()) {


            if (ASTC.mesmoTipo("FUNCTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("ACTION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("OPERATION")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("CAST")) {
                mEscopo.guardar(ASTC);
            } else if (ASTC.mesmoTipo("STRUCT")) {
                if (ASTC.mesmoNome(eNome)) {
                    mStructCorpo = ASTC.getBranch("BODY");
                    mStructInits = ASTC.getBranch("INITS");

                    AST AST_Stages = ASTC.getBranch("STAGES");
                    if (AST_Stages.mesmoValor("TRUE")) {
                        mRunTime.getErros().add("Struct " + mStructNome + " : Nao pode ser instanciada !");
                        return;
                    }

                }
            }

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


        // System.out.println("\t -->> Inicializando :  " + ASTCorrente.getNome());


        for (Index_Action mIndex_Function : mEscopo.getOO().getInits()) {

            // System.out.println("\t\t - Init Guardado :  " + mIndex_Function.getNome());

        }


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
                    // System.out.println("\t\t - Arg :  " + ArgumentoC.getNome() + " = " + ArgumentoC.getValor());
                    ai += 1;
                }


                // System.out.println("\t - Executando Dentro :  " +this.getNome());

                //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);

                AST mInitCall = mIndex_Function.getPonteiro().getBranch("CALL");
                Escopo tmpEscopo = new Escopo(mRunTime, mEscopo);

                if (mInitCall.mesmoValor("TRUE")) {


                    //  BuscadorDeArgumentos.ListarAll();


                    ///   System.out.println("\t - Inicializador Argumentos : :  " +mArgumentos.size());


                    Index_Action mIndex_Function3 = null;

                    int segundomax = 0;
                    for (AST mIndex_Function2 : mEscopo.getGuardadosCompleto()) {
                        if (mIndex_Function2.mesmoTipo("INIT") && mIndex_Function2.mesmoNome(mInitCall.getNome())) {

                            //  System.out.println("\t - Inicializador Interno :  " + mIndex_Function.getNome());

                            mIndex_Function3 = new Index_Action(mIndex_Function2);
                            segundomax = mIndex_Function3.getParamentos().size();

                        }
                    }

                    int ri = 0;

                    for (String Parametro : mIndex_Function3.getParamentos()) {
                        //   System.out.println("\t - PARAM -> :  " + Parametro);
                    }

                    ai = 0;

                    for (Item ArgumentoC : mArgumentos) {
                        if (ai < segundomax) {
                            ArgumentoC.setNome(mIndex_Function3.getParamentos().get(ai));
                        }

                        //System.out.println("\t\t - Arg :  " + ArgumentoC.getNome() + " = " + ArgumentoC.getValor());
                        ai += 1;
                    }


                    for (Item ArgumentoC : mArgumentos) {


                        tmpEscopo.criarDefinicao(ArgumentoC.getNome(), ArgumentoC.getTipo(), ArgumentoC.getValor());


                        ri += 1;
                    }


                    // TEMPORARIO

                    // ArrayList<Item> mArgumentos2 = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, tmpEscopo, mIndex_Function3.getPonteiro().getBranch("ARGUMENTS"));

                    //  System.out.println("INITER : " +mIndex_Function3.getPonteiro().getNome() );
                    //  for (AST ArgumentoC : mIndex_Function3.getPonteiro().getBranch("ARGUMENTS").getASTS()) {
                    //      System.out.println(" AST : " +ArgumentoC.getNome() + " = " + ArgumentoC.getValor() );
                    //  }

                    //  for (Item ArgumentoC : mArgumentos2) {
                    //   System.out.println("ARGUMENTADOR : " +ArgumentoC.getNome() + " = " + ArgumentoC.getValor() );
                    //      ri+=1;
                    //  }

                    //


                    //  tmpEscopo.ListarAll();

                    mPreparadorDeArgumentos.executar_Action(mRunTime, tmpEscopo, mIndex_Function3, mArgumentos);


                } else {

                    //   System.out.println("\t - Execucao DIRETA  :  " + this.getNome());

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

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

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

        //System.out.println("Procurando FUNC " + this.getStructNome() + "." + ASTCorrente.getNome());

        Item mRet = null;

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //  System.out.println("\t - Argumentos :  " + mArgumentos.size());

        boolean enc = false;
        boolean algum = false;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        //   System.out.println(" STRUCT :: "  +this.getNome());

        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions_All()) {
            // System.out.println("\t - " + mIndex_Function.getNome());
        }

        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions_All()) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {


                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    //  System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getPonteiro().mesmoValor(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            break;
                        }


                        mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);

                    } else {
                        mRunTime.getErros().add("Function " + mStructNome + "." + ASTCorrente.getNome() + " : Retorno incompativel !");
                    }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Function " + mStructNome + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Function  " + mStructNome + "." + ASTCorrente.getNome() + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }


        return mRet;
    }


    public void init_Action(AST ASTCorrente, Escopo BuscadorDeVariaveis) {

        //   System.out.println(" -->> DENTRO : " + this.getStructNome() );
        //  System.out.println(" -->> Procurando ACTION " + this.getStructNome() + "." + ASTCorrente.getNome());

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        // System.out.println("\t - Action Teste :  " + ASTCorrente.getNome() + " Passando Args " + mArgumentos.size());

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());


        for (Index_Action mIndex_Function : mEscopo.getOO().getActions_All()) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                //  System.out.println("\t - Action Teste :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;


                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }


                    // System.out.println("\t - Executando Dentro :  " +this.getNome());

                    //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);
                    mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Action " + mStructNome + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Action  " + mStructNome + "." + ASTCorrente.getNome() + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }


    }


    public void init_Init(AST ASTCorrente, Escopo BuscadorDeVariaveis) {

        //   System.out.println(" -->> DENTRO : " + this.getStructNome() );
        //  System.out.println(" -->> Procurando ACTION " + this.getStructNome() + "." + ASTCorrente.getNome());

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        // System.out.println("\t - Action Teste :  " + ASTCorrente.getNome() + " Passando Args " + mArgumentos.size());

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());


        for (Index_Action mIndex_Function : mEscopo.getOO().getInits()) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                //  System.out.println("\t - Action Teste :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;


                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }


                    // System.out.println("\t - Executando Dentro :  " +this.getNome());

                    //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);
                    mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Init " + mStructNome + "." + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Init  " + mStructNome + "." + ASTCorrente.getNome() + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }


    }

}
