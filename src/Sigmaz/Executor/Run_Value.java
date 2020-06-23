package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

public class Run_Value {

    private RunTime mRunTime;
    private Escopo mEscopo;

    private boolean mIsNulo;
    private boolean mIsPrimitivo;
    private boolean mIsEstrutura;

    private String mConteudo;
    private Escopo mObjeto;

    private String mRetornoTipo;

    public Run_Value(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mIsNulo = false;
        mIsPrimitivo = false;
        mIsEstrutura = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;

    }


    public String getConteudo() {
        return mConteudo;
    }

    public Escopo getObjeto() {
        return mObjeto;
    }

    public boolean getIsNulo() {
        return mIsNulo;
    }

    public boolean getIsPrimitivo() {
        return mIsPrimitivo;
    }

    public boolean getIsStruct() {
        return !mIsPrimitivo;
    }

    public String getRetornoTipo() {
        return mRetornoTipo;
    }

    public void setNulo(boolean eNulo) {
        mIsNulo = eNulo;
    }

    public void setPrimitivo(boolean ePrimitivo) {
        mIsPrimitivo = ePrimitivo;
    }

    public void setRetornoTipo(String eRetornoTipo) {
        mRetornoTipo = eRetornoTipo;
    }

    public void setConteudo(String eConteudo) {
        mConteudo = eConteudo;
    }

    public void setEstrutura(boolean eEstrutura) {
        mIsEstrutura = eEstrutura;
    }

    public boolean getEstrutura() {
        return mIsEstrutura;
    }


    public void init(AST ASTCorrente, String eRetorno) {

        mRetornoTipo = eRetorno;


        if (ASTCorrente.mesmoValor("NULL")) {

            //  System.out.println("Valorando  -> NULL");

            mIsNulo = true;

        } else if (ASTCorrente.mesmoValor("Text")) {

            //    System.out.println("Valorando  -> " + ASTCorrente.getNome());

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "string";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Num")) {

            //   System.out.println("Valorando  -> " + ASTCorrente.getNome());

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "num";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("ID")) {


            Variavel(ASTCorrente, eRetorno);


        } else if (ASTCorrente.mesmoValor("BLOCK")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);

        } else if (ASTCorrente.mesmoValor("COMPARATOR")) {

            Comparador(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("FUNCT")) {

            Funct(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("STAGE")) {

            Stage(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("INIT")) {

            Init(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("STRUCT")) {

            Struct(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("STRUCT_EXTERN")) {

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        //    System.out.println("Mudando Para EXTERN "  + ASTCorrente.getNome());

            Struct_Extern(ASTCorrente, eRetorno);

        } else {

            //   System.out.println("PROBLEMA  -> " + ASTCorrente.getValor());

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");


        }


    }

    public void Variavel(AST ASTCorrente, String eRetorno) {


        if (ASTCorrente.mesmoNome("true") || ASTCorrente.mesmoNome("false")) {

            //  System.out.println("Valorando  -> " + ASTCorrente.getNome());

            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "bool";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoNome("null")) {

            //  System.out.println("Valorando  -> NULL");

            // System.out.println("ANULANDO INIT  -> " + ASTCorrente.getNome());

            mIsNulo = true;
            mRetornoTipo = eRetorno;

        } else {

            //   System.out.println("Valorando  -> Def " + ASTCorrente.getNome());

            Item mItem = mEscopo.getItem(ASTCorrente.getNome());

            if (mItem != null) {

                mIsNulo = mItem.getNulo();
                mRetornoTipo = mItem.getTipo();
                mConteudo = mItem.getValor();


                mIsPrimitivo = mItem.getPrimitivo();
                mIsEstrutura = mItem.getIsEstrutura();


                if (mRetornoTipo.contentEquals("bool")) {
                    mIsPrimitivo = true;
                } else if (mRetornoTipo.contentEquals("num")) {
                    mIsPrimitivo = true;
                } else if (mRetornoTipo.contentEquals("string")) {
                    mIsPrimitivo = true;
                }


            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }


        }

    }

    public void Comparador(AST ASTCorrente, String eRetorno) {


        //System.out.println("Valorando  -> COMPARATOR");

        AST eModo = ASTCorrente.getBranch("MODE");


        Run_Value mRun_Esquerda = new Run_Value(mRunTime, mEscopo);
        mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Run_Value mRun_Direita = new Run_Value(mRunTime, mEscopo);
        mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (eModo.mesmoNome("MATCH")) {

            verificarIgualdade(mRun_Esquerda, mRun_Direita, mRetornoTipo);


        } else if (eModo.mesmoNome("MISMATCH")) {

            verificarDesIgualdade(mRun_Esquerda, mRun_Direita, mRetornoTipo);


        } else {
            mRunTime.getErros().add("Comparador Desconhecido : " + eModo.getNome());
        }


    }

    public void Funct(AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());

        Run_Func mAST = new Run_Func(mRunTime, mEscopo);
        Item eItem = mAST.init_Function(ASTCorrente, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        this.setNulo(eItem.getNulo());
        this.setPrimitivo(eItem.getPrimitivo());
        this.setConteudo(eItem.getValor());
        this.setRetornoTipo(eItem.getTipo());


        //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + this.getConteudo() + " P : " + mIsPrimitivo + " N : " + mIsNulo + " T : " + mRetornoTipo);


    }

    public void Init(AST ASTCorrente, String eRetorno) {

        //  mRunTime.getErros().add("Vamos Struct - " + eRetorno);
        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Struct::" + eRetorno + ":" + HEAPID + ">";


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.setNome(eNome);
        mRun_Struct.init(eRetorno, ASTCorrente, mEscopo);


        mRunTime.adicionarHeap(mRun_Struct);

        mIsNulo = false;
        mIsPrimitivo = false;
        mRetornoTipo = eRetorno;
        mIsEstrutura = true;
        mConteudo = eNome;

    }


    public void Struct(AST ASTCorrente, String eRetorno) {

        //  System.out.println("STRUCT: " + ASTCorrente.getNome());

        Item mItem = mEscopo.getItem(ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Run_Struct mEscopoStruct = mRunTime.getRun_Struct(mItem.getValor());

        AST eInternal = ASTCorrente.getBranch("INTERNAL");

        if (eInternal.mesmoValor("STRUCT_FUNCT")) {


            Item eItem = mEscopoStruct.init_Function(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(eItem.getNulo());
            this.setPrimitivo(eItem.getPrimitivo());
            this.setConteudo(eItem.getValor());
            this.setRetornoTipo(eItem.getTipo());


            //   mRunTime.getErros().add("STRUCT : " + mEscopoStruct.getNome() + " -> " + eInternal.getNome() + " : " + eItem.getValor());


        } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //      System.out.println("STRUCT_OBJECT : " + mEscopoStruct.getNome());

            Item eItem = mEscopoStruct.init_Object(eInternal, mEscopo, "<<ANY>>");

            while (eInternal.existeBranch("INTERNAL")) {

                AST sInternal = eInternal.getBranch("INTERNAL");

                if (sInternal.mesmoValor("STRUCT_ACT")) {

                } else if (sInternal.mesmoValor("STRUCT_FUNCT")) {

                    //System.out.println("STRUCT OBJECT : " + eItem.getValor());

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if (!eItem.getTipo().contentEquals(eRetorno)) {
                        mEscopoStruct = mRunTime.getRun_Struct(eItem.getValor());

                        eItem = mEscopoStruct.init_Function(sInternal, mEscopo, "<<ANY>>");

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }
                    } else {
                        this.setNulo(eItem.getNulo());
                        this.setPrimitivo(eItem.getPrimitivo());
                        this.setConteudo(eItem.getValor());
                        this.setRetornoTipo(eItem.getTipo());
                        break;
                    }


                    this.setNulo(eItem.getNulo());
                    this.setPrimitivo(eItem.getPrimitivo());
                    this.setConteudo(eItem.getValor());
                    this.setRetornoTipo(eItem.getTipo());

                } else if (sInternal.mesmoValor("STRUCT_OBJECT")) {

                }
            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(eItem.getNulo());
            this.setPrimitivo(eItem.getPrimitivo());
            this.setConteudo(eItem.getValor());
            this.setRetornoTipo(eItem.getTipo());

        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }


    }


    public void Struct_Extern(AST ASTCorrente, String eRetorno) {

         // System.out.println("STRUCT EXTERN : " + ASTCorrente.getNome());


        Run_Extern mEscopoExtern = mRunTime.getRun_Extern(ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        AST eInternal = ASTCorrente.getBranch("INTERNAL");


        if (eInternal.mesmoValor("STRUCT_FUNCT")) {


            if (mRunTime.getErros().size() > 0) {
                return;
            }

           // System.out.println("Mudando Para EXTERN - STRUCT_FUNCT "  + eInternal.getNome());


            Item eItem = mEscopoExtern.init_Function_Extern(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(eItem.getNulo());
            this.setPrimitivo(eItem.getPrimitivo());
            this.setConteudo(eItem.getValor());
            this.setRetornoTipo(eItem.getTipo());


            //   mRunTime.getErros().add("STRUCT : " + mEscopoStruct.getNome() + " -> " + eInternal.getNome() + " : " + eItem.getValor());


        } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //      System.out.println("STRUCT_OBJECT : " + mEscopoStruct.getNome());

            Item eItem = mEscopoExtern.init_ObjectExtern(eInternal, mEscopo, "<<ANY>>");

            while (eInternal.existeBranch("INTERNAL")) {

                AST sInternal = eInternal.getBranch("INTERNAL");

                if (sInternal.mesmoValor("STRUCT_ACT")) {

                } else if (sInternal.mesmoValor("STRUCT_FUNCT")) {

                    //System.out.println("STRUCT OBJECT : " + eItem.getValor());

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if (!eItem.getTipo().contentEquals(eRetorno)) {
                     //   mEscopoExtern = mRunTime.getRun_Struct(eItem.getValor());

                        eItem = mEscopoExtern.init_Function_Extern(sInternal, mEscopo, "<<ANY>>");

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }
                    } else {
                        this.setNulo(eItem.getNulo());
                        this.setPrimitivo(eItem.getPrimitivo());
                        this.setConteudo(eItem.getValor());
                        this.setRetornoTipo(eItem.getTipo());
                        break;
                    }


                    this.setNulo(eItem.getNulo());
                    this.setPrimitivo(eItem.getPrimitivo());
                    this.setConteudo(eItem.getValor());
                    this.setRetornoTipo(eItem.getTipo());

                } else if (sInternal.mesmoValor("STRUCT_OBJECT")) {

                }
            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(eItem.getNulo());
            this.setPrimitivo(eItem.getPrimitivo());
            this.setConteudo(eItem.getValor());
            this.setRetornoTipo(eItem.getTipo());

        } else {

            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE !");

        }


    }


    public void Stage(AST ASTCorrente, String eRetorno) {

        AST mFilho = ASTCorrente.getBranch("STAGED");

        if (mRunTime.existeStage(ASTCorrente.getNome() + "::" + mFilho.getNome())) {

        } else {
            mRunTime.getErros().add("Stage Deconhecido : " + ASTCorrente.getNome() + "::" + mFilho.getNome());
            return;
        }

        Item eItem = mEscopo.getItem(ASTCorrente.getNome() + "::" + mFilho.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        this.setNulo(eItem.getNulo());
        this.setPrimitivo(eItem.getPrimitivo());
        this.setConteudo(eItem.getValor());
        this.setRetornoTipo(eItem.getTipo());

    }


    public void verificarIgualdade(Run_Value mRun_Esquerda, Run_Value mRun_Direita, String eRetorno) {


        Run_Func mRun_Matchable = new Run_Func(mRunTime, mEscopo);
        Item mItem = mRun_Matchable.init_Operation("MATCH", mRun_Esquerda, mRun_Direita, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        mIsNulo = mItem.getNulo();
        mIsPrimitivo = mItem.getPrimitivo();
        mConteudo = mItem.getValor();
        mRetornoTipo = mItem.getTipo();

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mRetornoTipo.contentEquals("bool")) {
            mIsPrimitivo = true;
        } else if (mRetornoTipo.contentEquals("num")) {
            mIsPrimitivo = true;
        } else if (mRetornoTipo.contentEquals("string")) {
            mIsPrimitivo = true;
        }

    }


    public void verificarDesIgualdade(Run_Value mRun_Esquerda, Run_Value mRun_Direita, String eRetorno) {

        Run_Func mRun_Matchable = new Run_Func(mRunTime, mEscopo);
        Item mItem = mRun_Matchable.init_Operation("UNMATCH", mRun_Esquerda, mRun_Direita, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mIsNulo = mItem.getNulo();
        mIsPrimitivo = mItem.getPrimitivo();
        mConteudo = mItem.getValor();
        mRetornoTipo = mItem.getTipo();

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mRetornoTipo.contentEquals("bool")) {
            mIsPrimitivo = true;
        } else if (mRetornoTipo.contentEquals("num")) {
            mIsPrimitivo = true;
        } else if (mRetornoTipo.contentEquals("string")) {
            mIsPrimitivo = true;
        }

    }

    public String getModulante() {

        String emodo = "";

        if (mIsPrimitivo) {
            emodo = "Primitivo";
        }
        if (mIsEstrutura) {
            emodo = "Estrututura";
        }
        if (!mIsEstrutura) {
            emodo = "Primitivo";
        }


        return emodo;

    }
}
