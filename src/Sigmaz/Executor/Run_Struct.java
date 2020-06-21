package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Struct {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private String mNome;
    private PreparadorDeArgumentos mPreparadorDeArgumentos;

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


        mPreparadorDeArgumentos = new PreparadorDeArgumentos();

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

    public void init(String eNome) {

        mEscopo = new Escopo(mRunTime, null);
        mEscopo.setNome(eNome);
        mEscopo.setEstrutura(true);

        mStructCorpo = null;

        mStructNome = eNome;

        mEscopo.setNome(eNome);

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

                }
            }

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

        for (AST ASTC : mStructCorpo.getASTS()) {

            mTamanho += 1;

            if (ASTC.mesmoTipo("DEFINE")) {

                Run_Def mAST = new Run_Def(mRunTime, mEscopo);
                mAST.init(ASTC);

                mEscopo.guardarStruct(ASTC);

            } else if (ASTC.mesmoTipo("MOCKIZ")) {


                Run_Moc mAST = new Run_Moc(mRunTime, mEscopo);
                mAST.init(ASTC);

                mEscopo.guardarStruct(ASTC);

            }

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

        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions()) {
           // System.out.println("\t - " + mIndex_Function.getNome());
        }

        for (Index_Function mIndex_Function : mEscopo.getOO().getFunctions()) {

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


        for (Index_Action mIndex_Function : mEscopo.getOO().getActions()) {

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
                    mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);


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


}
