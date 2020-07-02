package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
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

    private boolean mIsReferenciavel;
    private Item mReferencia;

    public Run_Value(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mIsNulo = false;
        mIsPrimitivo = false;
        mIsEstrutura = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;

        mIsReferenciavel=false;
        mReferencia=null;
    }


    public String getConteudo() {
        return mConteudo;
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

    public boolean getIsReferenciavel() {
        return mIsReferenciavel;
    }
    public Item getReferencia() {
        return mReferencia;
    }

    public void init(AST ASTCorrente, String eRetorno) {

        mRetornoTipo = eRetorno;


        if (ASTCorrente.mesmoValor("NULL")) {

            mIsNulo = true;
            mRetornoTipo = eRetorno;

        } else if (ASTCorrente.mesmoValor("Text")) {


            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "string";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Num")) {


            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "num";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("ID")) {


            Variavel(ASTCorrente, eRetorno);


        } else if (ASTCorrente.mesmoValor("BLOCK")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);

        } else if (ASTCorrente.mesmoValor("OPERATOR")) {

            Operador(ASTCorrente, eRetorno);

        } else if (ASTCorrente.mesmoValor("UNARY")) {

            Unario(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("FUNCT")) {

            Funct(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("TERNAL")) {

            Ternal(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("STAGE")) {

            Stage(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("INIT")) {

            Init(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("START")) {

            Start(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("STRUCT")) {

            Struct(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("STRUCT_EXTERN")) {

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            Struct_Extern(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("CONTAINER")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);

        } else {


            mRunTime.getErros().add("AST_Value --> STRUCTURED VALUE  : " + ASTCorrente.getValor());


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

                //    System.out.println(mItem.getNome() + " E nulo :" + mItem.getNulo());
                //  System.out.println(mItem.getNome() + " E Estrutura :" + mItem.getIsEstrutura());


                mIsNulo = mItem.getNulo();
                mRetornoTipo = mItem.getTipo();
                mConteudo = mItem.getValor();


                mIsPrimitivo = mItem.getPrimitivo();
                mIsEstrutura = mItem.getIsEstrutura();

                mIsReferenciavel = true;
                mReferencia = mItem;

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

    public void Operador(AST ASTCorrente, String eRetorno) {


        AST eModo = ASTCorrente.getBranch("MODE");

        //System.out.println("OPERATOR  -> " + eModo.getNome());


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

            realizarOperacao("MATCH", mRun_Esquerda, mRun_Direita, mRetornoTipo);

        } else if (eModo.mesmoNome("UNMATCH")) {

            realizarOperacao("UNMATCH", mRun_Esquerda, mRun_Direita, mRetornoTipo);

        } else if (eModo.mesmoNome("SUM")) {

            realizarOperacao("SUM", mRun_Esquerda, mRun_Direita, mRetornoTipo);
        } else if (eModo.mesmoNome("SUB")) {

            realizarOperacao("SUB", mRun_Esquerda, mRun_Direita, mRetornoTipo);
        } else if (eModo.mesmoNome("SUB")) {

            realizarOperacao("SUB", mRun_Esquerda, mRun_Direita, mRetornoTipo);
        } else if (eModo.mesmoNome("MUX")) {

            realizarOperacao("MUX", mRun_Esquerda, mRun_Direita, mRetornoTipo);
        } else if (eModo.mesmoNome("DIV")) {

            realizarOperacao("DIV", mRun_Esquerda, mRun_Direita, mRetornoTipo);

        } else {
            mRunTime.getErros().add("Comparador Desconhecido : " + eModo.getNome());
        }


    }


    public void Unario(AST ASTCorrente, String eRetorno) {


        AST eModo = ASTCorrente.getBranch("MODE");

        //System.out.println("OPERATOR  -> " + eModo.getNome());


        Run_Value mRun_Esquerda = new Run_Value(mRunTime, mEscopo);
        mRun_Esquerda.init(ASTCorrente.getBranch("VALUE"), "<<ANY>>");

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (eModo.mesmoNome("INVERSE")) {

            realizarDirector("INVERSE", mRun_Esquerda, mRetornoTipo);


        } else {
            mRunTime.getErros().add("Unario Desconhecido : " + eModo.getNome());
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

    public void Ternal(AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());

        Run_Value mRun_Value = new Run_Value(mRunTime, mEscopo);
        mRun_Value.init(ASTCorrente.getBranch("CONDITION"), "bool");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mRun_Value.getConteudo().contentEquals("true")) {

            Run_Value sRun_Value = new Run_Value(mRunTime, mEscopo);
            sRun_Value.init(ASTCorrente.getBranch("TRUE"), eRetorno);

            this.setNulo(sRun_Value.getIsNulo());
            this.setPrimitivo(sRun_Value.getIsPrimitivo());
            this.setConteudo(sRun_Value.getConteudo());
            this.setRetornoTipo(sRun_Value.getRetornoTipo());

        } else {

            Run_Value sRun_Value = new Run_Value(mRunTime, mEscopo);
            sRun_Value.init(ASTCorrente.getBranch("FALSE"), eRetorno);

            this.setNulo(sRun_Value.getIsNulo());
            this.setPrimitivo(sRun_Value.getIsPrimitivo());
            this.setConteudo(sRun_Value.getConteudo());
            this.setRetornoTipo(sRun_Value.getRetornoTipo());


        }


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
        mRetornoTipo = mRun_Struct.getTipoCompleto();
        mIsEstrutura = true;
        mConteudo = eNome;

    }

    public void Start(AST ASTCorrente, String eRetorno) {

        //  mRunTime.getErros().add("Vamos Type - " + eRetorno);
        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Type::" + eRetorno + ":" + HEAPID + ">";

        if (mRunTime.getQualificador(eRetorno).contentEquals("STRUCT")) {
            mRunTime.getErros().add("Era esperado um TYPE e nao STRUCT !");
            return;
        } else if (mRunTime.getQualificador(eRetorno).contentEquals("TYPE")) {

        }


        Run_Type mRun_Type = new Run_Type(mRunTime);
        mRun_Type.init(eNome, ASTCorrente, mEscopo);

        mRun_Type.setNome(eNome);

        mRunTime.adicionarType(mRun_Type);

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

        String eQualificador = mRunTime.getQualificador(mItem.getTipo());

        //  System.out.println("Tipo : " + mItem.getNome() + " : " + mItem.getTipo() + " -> " + eQualificador);

        if (eQualificador.contentEquals("STRUCT")) {

            Struct_DentroStruct(mItem.getValor(), ASTCorrente, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {

            Struct_DentroType(mItem.getValor(), ASTCorrente, eRetorno);

        } else {
            mRunTime.getErros().add("CAST nao possui operador PONTO !");
        }


    }

    public void Struct_DentroType(String eLocal, AST ASTCorrente, String eRetorno) {

        Run_Type mEscopoType = mRunTime.getRun_Type(eLocal);

        //System.out.println("TYPE DENTRO : " + mEscopoType.getNome());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        AST eInternal = ASTCorrente.getBranch("INTERNAL");

        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //  System.out.println("STRUCT_OBJECT TYPE : " + eInternal.getNome());


            Item eItem = mEscopoType.init_Object(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            //    System.out.println("STRUCT_OBJECT : " + eInternal.getNome() + " = " + eItem.getValor());

            while (eInternal.existeBranch("INTERNAL")) {

                AST sInternal = eInternal.getBranch("INTERNAL");

                if (sInternal.mesmoValor("STRUCT_ACT")) {

                } else if (sInternal.mesmoValor("STRUCT_FUNCT")) {

                    //System.out.println("STRUCT OBJECT : " + eItem.getValor());

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if (!eItem.getTipo().contentEquals(eRetorno)) {
                        Run_Struct mEscopoStruct = mRunTime.getRun_Struct(eItem.getValor());

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }

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

    public void Struct_DentroStruct(String eLocal, AST ASTCorrente, String eRetorno) {

        Run_Struct mEscopoStruct = mRunTime.getRun_Struct(eLocal);

        AST eInternal = ASTCorrente.getBranch("INTERNAL");

        if (eInternal.mesmoValor("STRUCT_FUNCT")) {

            if (mRunTime.getErros().size() > 0) {
                return;
            }

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

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }

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

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }

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


    public void realizarOperacao(String eOperacao, Run_Value mRun_Esquerda, Run_Value mRun_Direita, String eRetorno) {


        Run_Func mRun_Matchable = new Run_Func(mRunTime, mEscopo);
        Item mItem = mRun_Matchable.init_Operation(eOperacao, mRun_Esquerda, mRun_Direita, eRetorno);

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

    public void realizarDirector(String eOperacao, Run_Value mRun_Esquerda, String eRetorno) {


        Run_Func mRun_Matchable = new Run_Func(mRunTime, mEscopo);
        Item mItem = mRun_Matchable.init_Director(eOperacao, mRun_Esquerda, eRetorno);

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
