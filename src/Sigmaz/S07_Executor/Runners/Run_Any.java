package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.Utilitario;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.Procuradores.ProcuradorSemRetorno;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Run_Any {

    private RunTime mRunTime;
    private Run_Arguments mPreparadorDeArgumentos;
    private Run_AnyScope mRun_AnyScope;

    public Run_Any(RunTime eRunTime) {

        mRunTime = eRunTime;
        mPreparadorDeArgumentos = new Run_Arguments();
        mRun_AnyScope = new Run_AnyScope(mRunTime);

    }


    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorno, ArrayList<Index_Function> eFunctions) {


       // System.out.println("\t\t EXECUTOR -->> Run_Function : " + ASTCorrente.getNome());

        Item mRet = new Item("");

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        if (mRunTime.getErros().size() > 0) {
            return mRet;
        }

        mRet = mRun_AnyScope.executeComRetorno("Run_Function", "Function", ASTCorrente.getNome(), mEscopo, eFunctions, mArgumentos, eRetorno);

        return mRet;

    }


    public void init_Action(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eMensagem, ArrayList<Index_Action> eActions) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mRun_AnyScope.executeSemRetorno("Run_Action", "Action", ASTCorrente.getNome(), mEscopo, eActions, mArgumentos);


    }

    public void init_ActionFunction(AST ASTCorrente, Escopo mEscopo) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        mRun_AnyScope.executeSemRetorno("Run_Action", "Action", ASTCorrente.getNome(), mEscopo, mEscopo.getActionFunctionsCompleto(), mArgumentos);


    }

    public Item init_Operation(String eNome, Run_Value Esquerda, Run_Value Direita, Escopo mEscopo, String eReturne) {


        Item mItem = new Item("");

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        colocarArgumento(Esquerda, mEscopo, mArgumentos);
        colocarArgumento(Direita, mEscopo, mArgumentos);


        Run_Context mRun_Context = new Run_Context(mRunTime);


        if (mRunTime.getErros().size() > 0) {
            return mItem;
        }

        //  mItem = mRun_AnyScope.executeComRetorno("Run_Operator", "Operator", eNome, mEscopo, getIndexaveis(mOperadores, mEscopo), mArgumentos, eReturne);
        mItem = mRun_AnyScope.executeComRetorno("Run_Operator", "Operator", eNome, mEscopo, mRun_Context.getOperatorsContexto(mEscopo), mArgumentos, eReturne);


        return mItem;

    }




    public Item init_Director(String eNome, Run_Value Esquerda, Escopo mEscopo, String eReturne) {


        Item mItem = new Item("");

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        colocarArgumento(Esquerda, mEscopo, mArgumentos);


        Run_Context mRun_Context = new Run_Context(mRunTime);


        if (mRunTime.getErros().size() > 0) {
            return mItem;
        }

        mItem = mRun_AnyScope.executeComRetorno("Run_Director", "Director", eNome, mEscopo, mRun_Context.getDirectorsContexto(mEscopo), mArgumentos, eReturne);


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
        Run_Searcher mRun_Searcher = new Run_Searcher(mRunTime, mEscopo);

        ProcuradorSemRetorno mexecutarAST = mRun_Searcher.procurarSemRetornoArgumentado(eInits, eNome, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_SemRetorno(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }
        String eNomeCompleto = "";

        if (ASTCorrente.getValor().length() == 0) {
            eNomeCompleto = eNome;
        } else {
            eNomeCompleto = ASTCorrente.getValor() + "." + eNome;

        }

        mRun_AnyScope.errar("Init", eNomeCompleto, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Init");


    }

    public void argumentar(AST eASTValue, Escopo mEscopo, String eRetorno, ArrayList<Item> mArgumentos) {

        Run_Value mRValue = new Run_Value(mRunTime, mEscopo);
        mRValue.init(eASTValue, eRetorno);

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        Item ve = new Item("VALUE");


        ve.setNulo(mRValue.getIsNulo(),mRunTime);
        ve.setPrimitivo(mRValue.getIsPrimitivo());
        ve.setIsEstrutura(mRValue.getIsStruct());
        ve.setValor(mRValue.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(mRValue.getRetornoTipo());

        mArgumentos.add(ve);

    }

    public void colocarArgumento(Run_Value mRValue, Escopo mEscopo, ArrayList<Item> mArgumentos) {


        Item ve = new Item("VALUE");


        ve.setNulo(mRValue.getIsNulo(),mRunTime);
        ve.setPrimitivo(mRValue.getIsPrimitivo());
        ve.setIsEstrutura(mRValue.getIsStruct());
        ve.setValor(mRValue.getConteudo(), mRunTime, mEscopo);
        ve.setTipo(mRValue.getRetornoTipo());

        mArgumentos.add(ve);

    }


    public Item init_Mark(AST ASTCorrente, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eMarcadores) {


        String eMarcador = ASTCorrente.getBranch("MARK").getNome();
        String eNome = eMarcador;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        argumentar(ASTCorrente.getBranch("VALUE"), mEscopo, eRetorne, mArgumentos);

        Item mItem = new Item("");

        if (mRunTime.getErros().size() > 0) {
            return mItem;
        }

        mItem = mRun_AnyScope.executeComRetorno("Run_Mark", "Marcador", eNome, mEscopo, eMarcadores, mArgumentos, eRetorne);


        return mItem;
    }


    public void Inicializador(String eOrigem, AST ASTCorrente, Escopo BuscadorDeArgumentos, Escopo mEscopo, String mLocal) {

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeArgumentos, ASTCorrente.getBranch("ARGUMENTS"));


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        Utilitario mUtilitario = new Utilitario();

        boolean realizada = false;


        String mTipagem = mUtilitario.getArgumentos(mArgumentos);

        Run_Searcher mRun_Searcher = new Run_Searcher(mRunTime, mEscopo);

        ProcuradorSemRetorno mexecutarAST = mRun_Searcher.procurarSemRetornoArgumentado(mEscopo.getOO().getInits_All(), eOrigem, mEscopo, mArgumentos);

        if (mexecutarAST.mExato) {
            Run_Escopo mRun_Escopo = new Run_Escopo();

            mRun_Escopo.executar_SemRetorno(mRunTime, mEscopo, mexecutarAST.mIndexado, mArgumentos);

            realizada = true;

            if (mRunTime.getErros().size() > 0) {
                return;
            }

        }

        mRun_AnyScope.errar("Init  " + eOrigem + "." + ASTCorrente.getNome(), eOrigem, mTipagem, mexecutarAST.mAlgum, mexecutarAST.mAlgum, realizada, "Run_Init");


    }


    public Item init_Getter(AST ASTCorrente, String eStruct, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, ArrayList<Index_Function> eFunctions) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));


        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        Item mItem = new Item("");

        if (mRunTime.getErros().size() > 0) {
            return mItem;
        }

        mItem = mRun_AnyScope.executeComRetornoGet("Run_Getter", "Getter", eStruct, mEscopo, eFunctions, mArgumentos, eRetorne);


        return mItem;
    }


    public void init_Setter(AST ASTCorrente, String eStruct, Escopo BuscadorDeVariaveis, Escopo mEscopo, Run_Value eRun_Value, ArrayList<Index_Action> eActions) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        Utilitario mUtilitario = new Utilitario();

        String mTipagem = mUtilitario.getArgumentosCol(mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        boolean realizada = false;


        Run_Searcher mRun_Searcher = new Run_Searcher(mRunTime, mEscopo);

        ProcuradorSemRetorno mexecutarAST = mRun_Searcher.procurarSemRetornoArgumentadoSetter(eActions, mEscopo, mArgumentos);


        if (mexecutarAST.mExato) {


            Run_Arguments mRun_Arguments = new Run_Arguments();

            Escopo mEscopoInterno = new Escopo(mRunTime, mEscopo);
            mEscopoInterno.setNome("SETTER");

            mRun_Arguments.passarParametros(mEscopoInterno, mexecutarAST.mIndexado.getArgumentos(), mArgumentos);


            if (mexecutarAST.mIndexado.getPonteiro().getBranch("VALUES").getASTS().size() == 1) {

                for (AST eArg : mexecutarAST.mIndexado.getPonteiro().getBranch("VALUES").getASTS()) {

                    Item eItem = new Item(eArg.getNome());
                    eItem.setNulo(eRun_Value.getIsNulo(),mRunTime);
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

        mRun_AnyScope.errar("Setter", eStruct, mTipagem, mexecutarAST.mEnc, mexecutarAST.mAlgum, realizada, "Run_Setter");


    }


}
