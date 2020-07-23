package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

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
    private String mLocal;

    public Run_Value(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value";

        mIsNulo = false;
        mIsPrimitivo = false;
        mIsEstrutura = false;

        mObjeto = null;
        mConteudo = null;
        mRetornoTipo = null;

        mIsReferenciavel = false;
        mReferencia = null;
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

        //  System.out.println("INIT VALUE " + ASTCorrente.getNome());

        if (ASTCorrente.mesmoValor("NULL")) {

            mIsNulo = true;
            mRetornoTipo = eRetorno;
            mIsPrimitivo=true;
            mIsEstrutura=false;

         //   System.out.println("RETORNAR NULO");

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

        } else if (ASTCorrente.mesmoValor("DIRECTOR")) {

            Director(ASTCorrente, eRetorno);


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

            Run_Dot mRun_Dot = new Run_Dot(mRunTime);
            Item mRetorno = mRun_Dot.operadorPonto(ASTCorrente, mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(mRetorno.getNulo());
            this.setPrimitivo(mRetorno.getPrimitivo());
            this.setConteudo(mRetorno.getValor());
            this.setRetornoTipo(mRetorno.getTipo());

            mIsReferenciavel = true;
            mReferencia = mRetorno;

        } else if (ASTCorrente.getValor().contentEquals("STRUCT_EXTERN")) {


            Run_Arrow mRun_Arrow = new Run_Arrow(mRunTime);
            Item mRetorno = mRun_Arrow.operadorSeta(ASTCorrente, mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(mRetorno.getNulo());
            this.setPrimitivo(mRetorno.getPrimitivo());
            this.setConteudo(mRetorno.getValor());
            this.setRetornoTipo(mRetorno.getTipo());

            mIsReferenciavel = true;
            mReferencia = mRetorno;


        } else if (ASTCorrente.getValor().contentEquals("CONTAINER")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("AUTO")) {

            auto(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("FUNCTOR")) {

            functor(ASTCorrente, eRetorno);


        } else {


            mRunTime.errar(mLocal,"AST_Value --> STRUCTURED VALUE  : " + ASTCorrente.getValor());


        }



        if (mRunTime.getErros().size()>0){
            return ;
        }

        if (!getRetornoTipo().contains("<>")){

            Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
            mRetornoTipo=mRun_GetType.getTipagemSimples(mRetornoTipo);

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

            //  System.out.println("RETORNO  -> " + eRetorno);

            mIsNulo = true;
            mRetornoTipo = eRetorno;
mIsPrimitivo=true;

        } else {


            Item mItem = mEscopo.getItem(ASTCorrente.getNome());

            //System.out.println("Valorando  -> Def " + mItem.getNome() + " : " + mItem.getTipo() + " = " + mItem.getValor());

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


       // System.out.println("OPERATOR  DIREITA -> " + mRun_Direita.getRetornoTipo());
       // System.out.println("OPERATOR  ESQUERDA -> " + mRun_Esquerda.getRetornoTipo());


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
            mRunTime.errar(mLocal,"Comparador Desconhecido : " + eModo.getNome());
        }


    }


    public void Director(AST ASTCorrente, String eRetorno) {


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
            mRunTime.errar(mLocal,"Unario Desconhecido : " + eModo.getNome());
        }


    }

    public void Funct(AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());

        Run_Any mAST = new Run_Any(mRunTime);
        Item eItem = mAST.init_Function(ASTCorrente,mEscopo, mEscopo,eRetorno,"",mEscopo.getFunctionsCompleto());

       // System.out.println("Ent ->> " + eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        this.setNulo(eItem.getNulo());
        this.setPrimitivo(eItem.getPrimitivo());
        this.setConteudo(eItem.getValor());
        this.setRetornoTipo(eItem.getTipo());


        //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + this.getConteudo() + " Primitivo : " + mIsPrimitivo + " Nulo : " + mIsNulo + " Tipo : " + mRetornoTipo);


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

            if (ASTCorrente.existeBranch("FALSE")){

                Run_Value sRun_Value = new Run_Value(mRunTime, mEscopo);
                sRun_Value.init(ASTCorrente.getBranch("FALSE"), eRetorno);

                this.setNulo(sRun_Value.getIsNulo());
                this.setPrimitivo(sRun_Value.getIsPrimitivo());
                this.setConteudo(sRun_Value.getConteudo());
                this.setRetornoTipo(sRun_Value.getRetornoTipo());

            }



        }


        //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + this.getConteudo() + " P : " + mIsPrimitivo + " N : " + mIsNulo + " T : " + mRetornoTipo);


    }

    public void Init(AST ASTCorrente, String eRetorno) {

      //  System.out.println("Vamos Struct 1 - " + eRetorno);

        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Struct::" + eRetorno + ":" + HEAPID + ">";



        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.setNome(eNome);
        mRunTime.adicionarHeap(mRun_Struct);

        mRun_Struct.init(eRetorno, ASTCorrente, mEscopo);



     //    System.out.println("Vamos Struct 2 - " + mRunTime.getHeap().size());

        mIsNulo = false;
        mIsPrimitivo = false;
        mRetornoTipo = mRun_Struct.getTipoCompleto();
        mIsEstrutura = true;
        mConteudo = eNome;



    }

    public void Start(AST ASTCorrente, String eRetorno) {

        //  mRunTime.errar(mLocal,"Vamos Type - " + eRetorno);
        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Type::" + ASTCorrente.getNome() + ":" + HEAPID + ">";

        String eQualificador = mRunTime.getQualificador(ASTCorrente.getNome(), mEscopo.getRefers());

        if (eQualificador.contentEquals("STRUCT")) {
            mRunTime.errar(mLocal,"Era esperado um TYPE e nao STRUCT !");
            return;
        } else if (eQualificador.contentEquals("TYPE")) {

        }

        //  System.out.println(" START -->> " + ASTCorrente.getNome());

        Run_Type mRun_Type = new Run_Type(mRunTime);
        mRun_Type.init(eNome, ASTCorrente, mEscopo);

        mRun_Type.setNome(eNome);

        mRunTime.adicionarType(mRun_Type);

        mIsNulo = false;
        mIsPrimitivo = false;
        mRetornoTipo = mRun_Type.getTipoCompleto();
        mIsEstrutura = true;
        mConteudo = eNome;

    }

    public void auto(AST ASTCorrente, String eRetorno) {


        mIsNulo = false;
        mIsPrimitivo = true;
        mRetornoTipo = "auto";
        mIsEstrutura = false;
        mConteudo = "auto";


    }


    public void functor(AST ASTCorrente, String eRetorno) {


        mIsNulo = false;
        mIsPrimitivo = true;
        mRetornoTipo = "functor";
        mIsEstrutura = false;
        mConteudo = "functor";


    }

    public void Stage(AST ASTCorrente, String eRetorno) {

        AST mFilho = ASTCorrente.getBranch("STAGED");

        Run_Context mRun_Context = new Run_Context(mRunTime);

        if (mRun_Context.existeStage(ASTCorrente.getNome() , mEscopo.getRefers())) {

            if (mRun_Context.existeStage_Type(ASTCorrente.getNome() + "::" + mFilho.getNome(), mEscopo.getRefers())) {
                Item eItem = mRun_Context.obterStage(ASTCorrente.getNome() + "::" + mFilho.getNome(), mEscopo.getRefers());

                if (mRunTime.getErros().size() > 0) {
                    return;
                }


                this.setNulo(eItem.getNulo());
                this.setPrimitivo(eItem.getPrimitivo());
                this.setConteudo(eItem.getValor());
                this.setRetornoTipo(eItem.getTipo());
            }else{

                mRunTime.errar(mLocal,"Stage Tipo Deconhecido : " + ASTCorrente.getNome() + "::" + mFilho.getNome());

            }



        } else {
            mRunTime.errar(mLocal,"Stage Deconhecido : " + ASTCorrente.getNome() );
            return;
        }


    }


    public void realizarOperacao(String eOperacao, Run_Value mRun_Esquerda, Run_Value mRun_Direita, String eRetorno) {


        // System.out.println("Realizando Operacao : " + eOperacao + " :: " + eRetorno);

      //  System.out.println("OPERATOR " + eOperacao + " DIREITA -> " + mRun_Direita.getRetornoTipo());
       // System.out.println("OPERATOR " + eOperacao + " ESQUERDA -> " + mRun_Esquerda.getRetornoTipo());


        Run_Any mRun_Matchable = new Run_Any(mRunTime);
        Item mItem = mRun_Matchable.init_Operation(eOperacao, mRun_Esquerda, mRun_Direita,mEscopo, eRetorno);



        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mIsNulo = mItem.getNulo();
        mIsPrimitivo = mItem.getPrimitivo();
        mConteudo = mItem.getValor();
        mRetornoTipo = mItem.getTipo();

        // System.out.println("Realizado Operacao : " + eOperacao + " :: " + mRetornoTipo);


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


        Run_Any mRun_Matchable = new Run_Any(mRunTime);
        Item mItem = mRun_Matchable.init_Director(eOperacao, mRun_Esquerda,mEscopo, eRetorno);

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


}
