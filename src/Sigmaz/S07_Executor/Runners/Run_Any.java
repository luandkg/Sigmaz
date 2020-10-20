package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Argument;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Run_Any {

    private RunTime mRunTime;
    private Run_Arguments mPreparadorDeArgumentos;

    public Run_Any(RunTime eRunTime) {

        mRunTime = eRunTime;
        mPreparadorDeArgumentos = new Run_Arguments();


    }

    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eFunctions) {


        Item mRet = null;

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));
        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        boolean realizada = false;


        String eNome = ASTCorrente.getNome();


        executarAST mexecutarAST = procurarRetornavelArgumentado(eFunctions, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRet = mRun_Escopo.executar_Function(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos, eRetorne);
            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

        }


        errar("Function", eNome, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Function");


        return mRet;
    }

    public void init_Action(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eMensagem, ArrayList<Index_Action> eActions) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        boolean realizada = false;

        String eNome = ASTCorrente.getNome();


        executarSemRetorno mexecutarAST = procurarSemRetornoArgumentado(eActions, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_Action(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }

        errar("Action", eNome, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Action");


    }

    public void init_ActionFunction(AST ASTCorrente, Escopo mEscopo) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        boolean realizada = false;

        String eNome = ASTCorrente.getNome();


        executarSemRetorno mexecutarAST = procurarSemRetornoArgumentado(mEscopo.getActionFunctionsCompleto(), eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_Action(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }

        errar("Action", eNome, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Action");


    }

    public Item init_Operation(String eNome, Run_Value Esquerda, Run_Value Direita, Escopo mEscopo, String eReturne) {


        Item mItem = null;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        colocarArgumento(Esquerda, mEscopo, mArgumentos);
        colocarArgumento(Direita, mEscopo, mArgumentos);


        String mTipagem = "";

        if (mArgumentos.size() > 1) {
            mTipagem = mArgumentos.get(0).getTipo() + " e " + mArgumentos.get(1).getTipo();
        }


        boolean realizada = false;

        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mOperadores = mRun_Context.getOperatorsContexto(mEscopo);


        executarAST mexecutarAST = procurarRetornavelArgumentado(getIndexaveis(mOperadores, mEscopo), eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {

            Run_Escopo mRun_Escopo = new Run_Escopo();

            mItem = mRun_Escopo.executar_Function(mRunTime, mRunTime.getEscopoGlobal(), mexecutarAST.mIndexado, mArgumentos, eReturne);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

        }

        errar("Operator", eNome, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Operator");

        return mItem;

    }

    private class executarAST {

        public Index_Function mIndexado = null;
        public boolean mEnc = false;
        public boolean mAlgum = false;
        public boolean mExato = false;

    }

    private class executarSemRetorno {

        public Index_Action mIndexado = null;
        public boolean mEnc = false;
        public boolean mAlgum = false;
        public boolean mExato = false;

    }

    public ArrayList<Index_Function> getIndexaveis(ArrayList<AST> mListaDeProcura, Escopo mEscopo) {

        ArrayList<Index_Function> mRet = new ArrayList<Index_Function>();

        Run_Arguments mRunArguments = new Run_Arguments();


        for (AST mAST : mListaDeProcura) {

            Index_Function mIndex_Function = new Index_Function(mRunTime, mEscopo, mAST);

            mIndex_Function.resolverTipagem(mEscopo.getRefers());

            mRet.add(mIndex_Function);

        }


        return mRet;
    }

    public executarAST procurarRetornavelArgumentado(ArrayList<Index_Function> mListaDeProcura, String eNome, Escopo mEscopo, ArrayList<Item> mArgumentos) {

        executarAST mexecutarAST = new executarAST();

        Run_Arguments mRunArguments = new Run_Arguments();

        for (Index_Function mIndex_Function : mListaDeProcura) {

            if (mIndex_Function.mesmoNome(eNome)) {


                mexecutarAST.mEnc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    mexecutarAST.mAlgum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);

                    if (contagem == mArgumentos.size()) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }

                        mexecutarAST.mIndexado = mIndex_Function;


                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }
                        mexecutarAST.mExato = true;

                        break;


                    }

                }


            }

        }


        return mexecutarAST;
    }

    public executarSemRetorno procurarSemRetornoArgumentado(ArrayList<Index_Action> mListaDeProcura, String eNome, Escopo mEscopo, ArrayList<Item> mArgumentos) {

        executarSemRetorno mexecutarAST = new executarSemRetorno();

        Run_Arguments mRunArguments = new Run_Arguments();

        for (Index_Action mIndex_Function : mListaDeProcura) {

            if (mIndex_Function.mesmoNome(eNome)) {

                //       System.out.println("Conferindo SA : " +mIndex_Function.getNome() );


                mexecutarAST.mEnc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    mexecutarAST.mAlgum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);

                    //  System.out.println("Conferindo SA : " +mIndex_Function.getNome()  + " :: " + contagem);

                    if (contagem == mArgumentos.size()) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }

                        mexecutarAST.mIndexado = mIndex_Function;


                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }
                        mexecutarAST.mExato = true;

                        break;


                    }

                }


            }

        }


        return mexecutarAST;
    }


    public Item init_Director(String eNome, Run_Value Esquerda, Escopo mEscopo, String eReturne) {


        Item mItem = null;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        colocarArgumento(Esquerda, mEscopo, mArgumentos);


        String mTipagem = "";

        if (mArgumentos.size() > 0) {
            mTipagem = mArgumentos.get(0).getTipo();
        }


        boolean realizada = false;


        Run_Context mRun_Context = new Run_Context(mRunTime);

        ArrayList<AST> mDirecionadores = mRun_Context.getDirectorsContexto(mEscopo);

        executarAST mexecutarAST = procurarRetornavelArgumentado(getIndexaveis(mDirecionadores, mEscopo), eNome, mEscopo, mArgumentos);


        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mItem = mRun_Escopo.executar_Function(mRunTime, mRunTime.getEscopoGlobal(), mexecutarAST.mIndexado, mArgumentos, eReturne);

            realizada = true;

        }

        errar("Director", eNome, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Director");


        return mItem;

    }


    public void init_inits(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, ArrayList<Index_Action> eInits) {

        // System.out.println("Init :: " + ASTCorrente.getNome() + " Argumentar");

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        boolean realizada = false;

        String eNome = ASTCorrente.getNome();

        // System.out.println("Init :: " + eNome);

        executarSemRetorno mexecutarAST = procurarSemRetornoArgumentado(eInits, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_Action(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }

        errar("Init", eNome, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Init");


    }

    public void argumentar(AST eASTValue, Escopo mEscopo, String eRetorno, ArrayList<Item> mArgumentos) {

        Run_Value mRValue = new Run_Value(mRunTime, mEscopo);
        mRValue.init(eASTValue, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        Item ve = new Item("VALUE");


        ve.setNulo(mRValue.getIsNulo());
        ve.setPrimitivo(mRValue.getIsPrimitivo());
        ve.setIsEstrutura(mRValue.getIsStruct());
        ve.setValor(mRValue.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(mRValue.getRetornoTipo());

        mArgumentos.add(ve);

    }

    public void colocarArgumento(Run_Value mRValue, Escopo mEscopo, ArrayList<Item> mArgumentos) {


        Item ve = new Item("VALUE");


        ve.setNulo(mRValue.getIsNulo());
        ve.setPrimitivo(mRValue.getIsPrimitivo());
        ve.setIsEstrutura(mRValue.getIsStruct());
        ve.setValor(mRValue.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(mRValue.getRetornoTipo());

        mArgumentos.add(ve);

    }


    public Item init_Mark(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eMarcadores) {


        String eMarcador = ASTCorrente.getBranch("MARK").getNome();

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        argumentar(ASTCorrente.getBranch("VALUE"), mEscopo, eRetorne, mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        String mTipagem = "";

        if (mArgumentos.size() > 0) {
            mTipagem = mArgumentos.get(0).getTipo();
        }


        Item mRet = null;


        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        boolean realizada = false;
        String eNome = eMarcador;


        executarAST mexecutarAST = procurarRetornavelArgumentado(eMarcadores, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRet = mRun_Escopo.executar_Function(mRunTime, mRunTime.getEscopoGlobal(), mexecutarAST.mIndexado, mArgumentos, eRetorne);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

        }


        errar("Marcador", eNome, mTipagem, mexecutarAST.mAlgum, mexecutarAST.mAlgum, realizada, "Run_Mark");


        return mRet;
    }


    public void Inicializador(String eOrigem, AST ASTCorrente, Escopo BuscadorDeArgumentos, Escopo mEscopo, String mLocal) {

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeArgumentos, ASTCorrente.getBranch("ARGUMENTS"));


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        // System.out.println("\t -->> Inicializando :  " + ASTCorrente.getNome());
        Utilitario mUtilitario = new Utilitario();


        boolean realizada = false;


        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        executarSemRetorno mexecutarAST = procurarSemRetornoArgumentado(mEscopo.getOO().getInits_All(), eOrigem, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_Action(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }

        errar("Init  " + eOrigem + "." + ASTCorrente.getNome(), eOrigem, mTipagem, mexecutarAST.mAlgum, mexecutarAST.mAlgum, realizada, "Run_Init");


    }


    public void errar(String eGrupo, String eNome, String mTipagem, boolean enc, boolean algum, boolean realizada, String mLocal) {

        if (enc) {
            if (algum) {
                if (realizada) {
                } else {
                    mRunTime.errar(mLocal, eGrupo + " " + eNome + " : Argumentos incompativeis : " + mTipagem);
                }
            } else {
                mRunTime.errar(mLocal, eGrupo + "  " + eNome + " " + mTipagem + " : Quantidade de Argumentos incompativeis !");
            }
        } else {
            mRunTime.errar(mLocal, eGrupo + "  " + eNome + " " + mTipagem + " : Nao Encontrada !");
        }


    }


    public Item init_ColGet(AST ASTCorrente, String eStruct, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eFunctions) {


        Item mRet = null;

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));
        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentosCol(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        boolean realizada = false;


        executarAST mexecutarAST = procurarRetornavelArgumentado(eFunctions, "COL_GET", mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRet = mRun_Escopo.executar_Function(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos, eRetorne);
            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

        }


        errar("Col_Get", eStruct, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Col_Get");


        return mRet;
    }


    public void init_ColSet(AST ASTCorrente, String eStruct, Escopo BuscadorDeVariaveis, Escopo mEscopo, Run_Value eRun_Value, ArrayList<Index_Action> eActions) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentosCol(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        boolean realizada = false;


       // System.out.println("Procurando COL ->> SET : " + eStruct + " " + mTipagem);


        executarSemRetorno mexecutarAST = procurarSemRetornoArgumentado(eActions, "COL_SET", mEscopo, mArgumentos);

      //  System.out.println("Enc : " + mexecutarAST.mEnc);
     //   System.out.println("Algum : " + mexecutarAST.mAlgum);
      //  System.out.println("Exato : " + mexecutarAST.mExato);


        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();


            Run_Arguments mRun_Arguments = new Run_Arguments();

            Escopo mEscopoInterno = new Escopo(mRunTime, mEscopo);
            mEscopoInterno.setNome("COL_SET");

            mRun_Arguments.passarParametros(mEscopoInterno, mexecutarAST.mIndexado.getArgumentos(), mArgumentos);


        //    System.out.println(mexecutarAST.mIndexado.getPonteiro().getBranch("VALUES").getImpressao());

            if (mexecutarAST.mIndexado.getPonteiro().getBranch("VALUES").getASTS().size() == 1) {

                for (AST eArg : mexecutarAST.mIndexado.getPonteiro().getBranch("VALUES").getASTS()) {

                    Item eItem = new Item(eArg.getNome());
                    eItem.setNulo(eRun_Value.getIsNulo());
                    eItem.setTipo(eRun_Value.getRetornoTipo());
                    eItem.setValor(eRun_Value.getConteudo(), mRunTime, mEscopo);
                    eItem.setPrimitivo(eRun_Value.getIsPrimitivo());
                    eItem.setIsEstrutura(eRun_Value.getIsStruct());

                    eItem.setIsReferenciavel(eRun_Value.getIsReferenciavel());
                    eItem.setReferencia(eRun_Value.getReferencia());

                    mEscopoInterno.criarItem(eArg.getNome(), eItem);

                }

            } else if (mexecutarAST.mIndexado.getPonteiro().getBranch("VALUES").getASTS().size() == 0) {
                mRunTime.errar("Run_ColSet", "Nao foi encontrado o transferidor de Valor !");
                return;
            } else {
                mRunTime.errar("Run_ColSet", "So pode existir um transferidor de Valor !");
                return;
            }


          //  mEscopoInterno.getRegressiveDebug().mapear_regressive_stack();

            if (mRunTime.getErros().size() > 0) {
                return;
            }
            AST mASTBody = mexecutarAST.mIndexado.getPonteiro().getBranch("BODY");

            Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
            mAST.init(mASTBody);


            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }

        errar("Col_Set", eStruct, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Col_Set");


    }


}
