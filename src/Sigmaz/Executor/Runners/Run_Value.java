package Sigmaz.Executor.Runners;

import Sigmaz.Executor.AST_Implementador;
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

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (ASTCorrente.mesmoValor("NULL")) {

            //   System.out.println("Value Type 1 : " + this.getRetornoTipo());
            //    System.out.println("Value Primitivo 1 : " + this.getIsPrimitivo());


            mIsNulo = true;
            mRetornoTipo = eRetorno;
            mIsPrimitivo = true;
            mIsEstrutura = false;

            //   System.out.println("RETORNAR NULO");

            //   System.out.println("Value Type 2 : " + this.getRetornoTipo());
            //    System.out.println("Value Primitivo 2 : " + this.getIsPrimitivo());


        } else if (ASTCorrente.mesmoValor("Text")) {


            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "string";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Num")) {


            mIsNulo = false;
            mIsPrimitivo = true;
            mRetornoTipo = "int";
            mConteudo = ASTCorrente.getNome();

        } else if (ASTCorrente.mesmoValor("Float")) {


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
            this.setConteudo(mRetorno.getValor(mRunTime, mEscopo));
            this.setRetornoTipo(mRetorno.getTipo());

            mIsReferenciavel = true;
            mReferencia = mRetorno;

        } else if (ASTCorrente.getValor().contentEquals("THIS")) {

            Run_This mRun_This = new Run_This(mRunTime);
            Item mRetorno = mRun_This.operadorPonto(ASTCorrente.getBranch("THIS"), mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            this.setNulo(mRetorno.getNulo());
            this.setPrimitivo(mRetorno.getPrimitivo());
            this.setConteudo(mRetorno.getValor(mRunTime, mEscopo));
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
            this.setConteudo(mRetorno.getValor(mRunTime, mEscopo));
            this.setRetornoTipo(mRetorno.getTipo());

            mIsReferenciavel = true;
            mReferencia = mRetorno;


        } else if (ASTCorrente.getValor().contentEquals("CONTAINER")) {

            init(ASTCorrente.getBranch("VALUE"), eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("FUNCTOR")) {

            functor(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("VECTOR")) {

            vector(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("DEFAULT")) {


            defaultx(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("EXECUTE_LOCAL")) {

            executeLocal(ASTCorrente, eRetorno);

        } else if (ASTCorrente.getValor().contentEquals("EXECUTE_FUNCTOR")) {

            functor(ASTCorrente, eRetorno);


        } else if (ASTCorrente.getValor().contentEquals("MARKER")) {


            Marker(ASTCorrente,eRetorno);




        } else {


            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE  : " + ASTCorrente.getValor());


        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (!getRetornoTipo().contains("<>")) {

            Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
            mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());
            mRetornoTipo = mRun_GetType.getTipagemSimples(mRetornoTipo);

        }


        //  System.out.println("Value Type 3 : " + this.getRetornoTipo());
        //  System.out.println("Value Primitivo 3 : " + this.getIsPrimitivo());


    }

    public void defaultx(AST ASTCorrente, String eRetorno) {


        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());

        if (ASTCorrente.existeBranch("TYPE")) {

            String mTipo = mRun_GetType.getTipagem(ASTCorrente.getBranch("TYPE"));

            boolean senc = false;


            for (AST eAST : mEscopo.getGuardadosCompleto()) {

                if (eAST.mesmoTipo("DEFAULT")) {


                    String sTipo = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

                    if (mTipo.contentEquals(sTipo)) {

                        Run_Default mRun_Default = new Run_Default(mRunTime, mRunTime.getEscopoGlobal());
                        Item eItem = mRun_Default.init(eAST, sTipo);

                        mIsNulo = eItem.getNulo();
                        mRetornoTipo = eItem.getTipo();
                        mIsPrimitivo = eItem.getPrimitivo();
                        mIsEstrutura = eItem.getIsEstrutura();
                        mConteudo = eItem.getValor(mRunTime, mEscopo);

                        senc = true;
                        break;
                    }


                }
            }

            if (!senc) {

                if (mTipo.contentEquals("int")) {
                    //  mIsNulo = false;
                    //  mRetornoTipo = mTipo;
                    //  mIsPrimitivo = true;
                    //   mIsEstrutura = false;
                    // mConteudo = "0";

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);


                } else if (mTipo.contentEquals("num")) {
                    //  mIsNulo = false;
                    //  mRetornoTipo = mTipo;
                    //  mIsPrimitivo = true;
                    //  mIsEstrutura = false;
                    //  mConteudo = "0.0";

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else if (mTipo.contentEquals("string")) {
                    //  mIsNulo = false;
                    //  mRetornoTipo = mTipo;
                    //  mIsPrimitivo = true;
                    //  mIsEstrutura = false;
                    // mConteudo = "";

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else if (mTipo.contentEquals("bool")) {
                    //  mIsNulo = false;
                    //   mRetornoTipo = mTipo;
                    //  mIsPrimitivo = true;
                    //    mIsEstrutura = false;
                    //  mConteudo = "true";

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else {

                    String eQual = mRunTime.getQualificador(eRetorno, mEscopo.getRefers());
                    if (eQual.contentEquals("CAST")) {

                        Run_Context mRun_Context = new Run_Context(mRunTime);
                        boolean enc = false;
                        for (AST mCast : mRun_Context.getCastsContexto(mEscopo.getRefers())) {
                            if (mCast.mesmoNome(eRetorno)) {

                                if (mCast.existeBranch("DEFAULT")) {

                                    Run_Body mRB = new Run_Body(mRunTime, mEscopo);
                                    mRB.init(mCast.getBranch("DEFAULT"));

                                    mIsNulo = mRB.getRetorno().getNulo();
                                    mRetornoTipo = mRB.getRetorno().getTipo();
                                    mIsPrimitivo = mRB.getRetorno().getPrimitivo();
                                    mIsEstrutura = mRB.getRetorno().getIsEstrutura();
                                    mConteudo = mRB.getRetorno().getValor(mRunTime,mEscopo);


                                } else {
                                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para a CAST : " + eRetorno);
                                }


                                enc = true;
                                break;
                            }

                        }
                        if (!enc) {
                            mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para a CAST : " + eRetorno);
                        }
                    } else if (eQual.contentEquals("STRUCT")) {
                        mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para a STRUCT : " + eRetorno);
                    } else {

                        mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o Tipo: " + eRetorno);

                    }


                }


            }


        } else {
            mRunTime.errar(mLocal, "DEFAULT precisa ser tipado !");
        }


    }


    public void executeLocal(AST ASTCorrente, String eRetorno) {

        Run_ExecuteLocal mRun_ExecuteLocal = new Run_ExecuteLocal(mRunTime, mEscopo);
        Item mItem = mRun_ExecuteLocal.initComRetorno(ASTCorrente);

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        mIsNulo = mItem.getNulo();
        mRetornoTipo = mItem.getTipo();
        mConteudo = mItem.getValor(mRunTime, mEscopo);


        mIsPrimitivo = mItem.getPrimitivo();
        mIsEstrutura = mItem.getIsEstrutura();

        mIsReferenciavel = true;
        mReferencia = mItem;


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
            mIsPrimitivo = false;

            if (mRetornoTipo.contentEquals("num") || mRetornoTipo.contentEquals("bool") || mRetornoTipo.contentEquals("string")) {
                mIsPrimitivo = true;
            }
            //} else if (ASTCorrente.mesmoNome("default")) {

            //   defaultx(ASTCorrente,eRetorno);


        } else {


            Item mItem = mEscopo.getItem(ASTCorrente.getNome());

            //System.out.println("Valorando  -> Def " + mItem.getNome() + " : " + mItem.getTipo() + " = " + mItem.getValor());

            if (mItem != null) {

                //    System.out.println(mItem.getNome() + " E nulo :" + mItem.getNulo());
                //  System.out.println(mItem.getNome() + " E Estrutura :" + mItem.getIsEstrutura());


                mIsNulo = mItem.getNulo();
                mRetornoTipo = mItem.getTipo();
                mConteudo = mItem.getValor(mRunTime, mEscopo);


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

        if (ASTCorrente.getBranch("LEFT").mesmoValor("DEFAULT")) {
            mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), eRetorno);
        } else {
            mRun_Esquerda.init(ASTCorrente.getBranch("LEFT"), "<<ANY>>");
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Run_Value mRun_Direita = new Run_Value(mRunTime, mEscopo);
        if (ASTCorrente.getBranch("RIGHT").mesmoValor("DEFAULT")) {
            mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), eRetorno);
        } else {
            mRun_Direita.init(ASTCorrente.getBranch("RIGHT"), "<<ANY>>");
        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        // System.out.println("OPERATOR  DIREITA -> " + mRun_Direita.getRetornoTipo());
        // System.out.println("OPERATOR  ESQUERDA -> " + mRun_Esquerda.getRetornoTipo());

        //System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

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
        } else if (eModo.mesmoNome("GREAT")) {

            realizarOperacao("GREAT", mRun_Esquerda, mRun_Direita, mRetornoTipo);
        } else if (eModo.mesmoNome("LESS")) {

            realizarOperacao("LESS", mRun_Esquerda, mRun_Direita, mRetornoTipo);

        } else {
            mRunTime.errar(mLocal, "Comparador Desconhecido : " + eModo.getNome());
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
            mRunTime.errar(mLocal, "Unario Desconhecido : " + eModo.getNome());
        }


    }

    public void Funct(AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());

        Run_Any mAST = new Run_Any(mRunTime);
        Item eItem = mAST.init_Function(ASTCorrente, mEscopo, mEscopo, eRetorno, mEscopo.getFunctionsCompleto());

        // System.out.println("Ent ->> " + eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        this.setNulo(eItem.getNulo());
        this.setPrimitivo(eItem.getPrimitivo());
        this.setConteudo(eItem.getValor(mRunTime, mEscopo));
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

            if (ASTCorrente.existeBranch("FALSE")) {

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


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRunTime.adicionarHeap(mRun_Struct);

        mRun_Struct.init(ASTCorrente, mEscopo);

        String eNome = mRun_Struct.getNome();

        //    System.out.println("Vamos Struct 2 - " + mRunTime.getHeap().size());

        mIsNulo = false;
        mIsPrimitivo = false;
        mRetornoTipo = mRun_Struct.getTipoCompleto();
        mIsEstrutura = true;
        mConteudo = eNome;


    }

    public void Start(AST ASTCorrente, String eRetorno) {


        String eQualificador = mRunTime.getQualificador(ASTCorrente.getNome(), mEscopo.getRefers());

        if (eQualificador.contentEquals("STRUCT")) {
            mRunTime.errar(mLocal, "Era esperado um TYPE e nao STRUCT !");
            return;
        } else if (eQualificador.contentEquals("TYPE")) {

        }

        //  System.out.println(" START -->> " + ASTCorrente.getNome());

        Run_Type mRun_Type = new Run_Type(mRunTime);
        mRun_Type.init(ASTCorrente, mEscopo);


        String eNome = mRun_Type.getNome();

        mRunTime.adicionarType(mRun_Type);

        mIsNulo = false;
        mIsPrimitivo = false;
        mRetornoTipo = mRun_Type.getTipoCompleto();
        mIsEstrutura = true;
        mConteudo = eNome;

    }


    public void functor(AST ASTCorrente, String eRetorno) {


        Run_ExecuteFunctor mRun_ExecuteAuto = new Run_ExecuteFunctor(mRunTime, mEscopo);
        Item eItem = mRun_ExecuteAuto.init(ASTCorrente, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        this.setNulo(eItem.getNulo());
        this.setPrimitivo(eItem.getPrimitivo());
        this.setConteudo(eItem.getValor(mRunTime, mEscopo));
        this.setRetornoTipo(eItem.getTipo());

    }

    public void Stage(AST ASTCorrente, String eRetorno) {

        AST mFilho = ASTCorrente.getBranch("STAGED");

        Run_Context mRun_Context = new Run_Context(mRunTime);

        if (mRun_Context.existeStage(ASTCorrente.getNome(), mEscopo.getRefers())) {

            if (mRun_Context.existeStage_Type(ASTCorrente.getNome() + "::" + mFilho.getNome(), mEscopo.getRefers())) {
                Item eItem = mRun_Context.obterStage(ASTCorrente.getNome() + "::" + mFilho.getNome(), mEscopo.getRefers());

                if (mRunTime.getErros().size() > 0) {
                    return;
                }


                this.setNulo(eItem.getNulo());
                this.setPrimitivo(eItem.getPrimitivo());
                this.setConteudo(eItem.getValor(mRunTime, mEscopo));
                this.setRetornoTipo(eItem.getTipo());
            } else {

                mRunTime.errar(mLocal, "Stage Tipo Deconhecido : " + ASTCorrente.getNome() + "::" + mFilho.getNome());

            }


        } else {
            mRunTime.errar(mLocal, "Stage Deconhecido : " + ASTCorrente.getNome());
            return;
        }


    }


    public void realizarOperacao(String eOperacao, Run_Value mRun_Esquerda, Run_Value mRun_Direita, String eRetorno) {


        // System.out.println("Realizando Operacao : " + eOperacao + " :: " + eRetorno);

        //  System.out.println("OPERATOR " + eOperacao + " DIREITA -> " + mRun_Direita.getRetornoTipo());
        // System.out.println("OPERATOR " + eOperacao + " ESQUERDA -> " + mRun_Esquerda.getRetornoTipo());


        Run_Any mRun_Matchable = new Run_Any(mRunTime);
        Item mItem = mRun_Matchable.init_Operation(eOperacao, mRun_Esquerda, mRun_Direita, mEscopo, eRetorno);


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mIsNulo = mItem.getNulo();
        mIsPrimitivo = mItem.getPrimitivo();
        mConteudo = mItem.getValor(mRunTime, mEscopo);
        mRetornoTipo = mItem.getTipo();

        //System.out.println("Realizado Operacao : " + eOperacao + " :: " + mRetornoTipo);


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
        Item mItem = mRun_Matchable.init_Director(eOperacao, mRun_Esquerda, mEscopo, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mIsNulo = mItem.getNulo();
        mIsPrimitivo = mItem.getPrimitivo();
        mConteudo = mItem.getValor(mRunTime, mEscopo);
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

    public void vector(AST ASTCorrente, String eRetorno) {


        Escopo tmp = new Escopo(mRunTime, mEscopo);

        // System.out.println("VECTOR  -> ");

        long VECTORID = mRunTime.getVECTORID();

        long HEAPID = mRunTime.getHEAPID();


        String eNomeVector = "{{VECTOR}}::" + VECTORID;


        String eTipado = "";
        boolean eTipadoja = false;

        if (ASTCorrente.getASTS().size() == 0) {
            mRunTime.errar(mLocal, "O retorno de um objeto VETOR nao pode ser vazio !");
            return;
        }


        for (AST eAST : ASTCorrente.getASTS()) {

            Run_Value eRV = new Run_Value(mRunTime, mEscopo);
            eRV.init(eAST, "<<ANY>>");
            if (eTipadoja == false) {
                eTipadoja = true;
                eTipado = eRV.getRetornoTipo();
            }

            break;

        }

        //   eNome = "<Struct::" + "Vetor<>Vetor<" + eTipado + ">>" + ":" + HEAPID + ">";

        // System.out.println(eNome);

        AST_Implementador mImplementador = new AST_Implementador();


        Escopo EachEscopo = new Escopo(mRunTime, tmp);


        AST eAST = mImplementador.criar_InitGenerico("Vetor", eTipado);
        AST eArg = eAST.getBranch("ARGUMENTS").criarBranch("ARGUMENT");
        eArg.setNome(ASTCorrente.getASTS().size() + "");
        eArg.setValor("Num");

        // System.out.println(eAST.ImprimirArvoreDeInstrucoes());

        // ARGUMENTS ->  :
        //   ARGUMENT -> 5 : Num


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.init(eAST, EachEscopo);

        Item mItem = tmp.criarDefinicao(eNomeVector, "Vetor<>Vetor<" + eTipado + ">", mRun_Struct.getNome());

        //   System.out.println("VETOR -->> " + eNome);
        //System.out.println(eAST.ImprimirArvoreDeInstrucoes());

        mRunTime.adicionarHeap(mRun_Struct);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        int eV = 0;

        for (AST oAST : ASTCorrente.getASTS()) {

            Run_Value eRV = new Run_Value(mRunTime, mEscopo);
            eRV.init(oAST, "<<ANY>>");


            if (eRV.getIsPrimitivo()) {

                if (eRV.getRetornoTipo().contentEquals("num")) {

                    if (eRV.getIsNulo()) {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "Num");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    }

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }
                } else if (eRV.getRetornoTipo().contentEquals("int")) {

                    if (eRV.getIsNulo()) {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "Num");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    }

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }
                } else if (eRV.getRetornoTipo().contentEquals("string")) {

                    if (eRV.getIsNulo()) {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {

                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "Text");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    }

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                } else if (eRV.getRetornoTipo().contentEquals("bool")) {

                    if (eRV.getIsNulo()) {


                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);

                    } else {
                        AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eRV.getConteudo(), "ID");
                        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                        mASTExecute.init(mExecute);
                    }

                    // System.out.println(mExecute.ImprimirArvoreDeInstrucoes());

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                } else {

                    mRunTime.errar(mLocal, "Tipo primitivo desconhecido : " + eRV.getRetornoTipo());


                    return;

                }

            } else {

                // System.out.println(" OBJ NAO PRIMITIVO :: " + eRV.getRetornoTipo());

                if (eRV.getIsNulo()) {

                    AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", "null", "ID");
                    Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
                    mASTExecute.init(mExecute);

                } else {

                    String eNomeRef = "{REF}::" + mRunTime.getVECTORID();

                    Escopo gEscopo = new Escopo(mRunTime, EachEscopo);
                    EachEscopo.criarDefinicaoStruct(eNomeRef, eRV.getRetornoTipo(), eRV.getConteudo());

                    // System.out.println(" -->> " + eRV.getConteudo());

                    AST mExecute = mImplementador.criar_ExecuteFunction2Args(eNomeVector, "set", String.valueOf(eV), "Num", eNomeRef, "ID");

                    //System.out.println(mExecute.ImprimirArvoreDeInstrucoes());

                    Run_Execute mASTExecute = new Run_Execute(mRunTime, gEscopo);
                    mASTExecute.init(mExecute);


                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }
            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }

            //System.out.println(" OBJ :: " + eRV.getRetornoTipo());
            eV += 1;
        }

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mIsNulo = mItem.getNulo();
        mIsPrimitivo = mItem.getPrimitivo();
        mConteudo = mItem.getValor(mRunTime, mEscopo);
        mRetornoTipo = mItem.getTipo();


    }


    public void Marker(AST ASTCorrente, String eRetorno) {

        // System.out.println("Valorando  -> FUNCT " + ASTCorrente.getNome());



        Run_Any mAST = new Run_Any(mRunTime);
        Item eItem = mAST.init_Mark(ASTCorrente, mEscopo, mEscopo, eRetorno, mEscopo.getMarcadoresCompleto());

        // System.out.println("Ent ->> " + eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        this.setNulo(eItem.getNulo());
        this.setPrimitivo(eItem.getPrimitivo());
        this.setConteudo(eItem.getValor(mRunTime, mEscopo));
        this.setRetornoTipo(eItem.getTipo());


        //  System.out.println("FUNCT EXIT  -> " + ASTCorrente.getNome() + " -> " + this.getConteudo() + " Primitivo : " + mIsPrimitivo + " Nulo : " + mIsNulo + " Tipo : " + mRetornoTipo);


    }


}
