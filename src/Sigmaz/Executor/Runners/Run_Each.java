package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.AST_Implementador;

import java.util.ArrayList;

public class Run_Each {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Each(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public String getTipo(String eTipo) {
        String mTipo = "";

        int i = 0;
        int o = eTipo.length();

        while (i < o) {
            String l = eTipo.charAt(i) + "";
            if (l.contentEquals("<")) {
                break;
            } else {
                mTipo += l;
            }
            i += 1;
        }

        return mTipo;
    }


    public void init(AST ASTCorrente) {


        Escopo EachEscopo = new Escopo(mRunTime, mEscopo);

        AST mDef = ASTCorrente.getBranch("DEF");
        AST mType = ASTCorrente.getBranch("TYPE");
        AST mList = ASTCorrente.getBranch("LIST");
        AST mBody = ASTCorrente.getBranch("BODY");

        String mTipagem = mRunTime.getTipagem(mType);
        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);

        mTipagem = mRun_GetType.getTipagemSimples(mTipagem);


        Run_Value mAST = new Run_Value(mRunTime, EachEscopo);
        mAST.init(mList, "<<ANY>>");


        //System.out.println("Lista Tipo : " + mAST.getRetornoTipo());
        //System.out.println("Lista Tipo Ref : " + getTipo(mAST.getRetornoTipo()));
        //  System.out.println("Lista Tipo Sub : " + getSubTipo(mAST.getRetornoTipo()));

        if (getTipo(mAST.getRetornoTipo()).contentEquals("Lista")) {

        } else {
            mRunTime.getErros().add("O Iterable do Each precisa ser do tipo : Lista");
            return;
        }
        String realTipagem = "Lista<>Lista<" + mTipagem + ">";
        if (realTipagem.contentEquals((mAST.getRetornoTipo()))) {

        } else {
            mRunTime.getErros().add("O Tipo da variavel do Iterable nao e compativel : " + realTipagem + " vs " + (mAST.getRetornoTipo()));
            return;
        }

        if (mAST.getIsNulo()) {
            mRunTime.getErros().add("O Iterable nao poder ser nulo !");
            return;
        }

        // System.out.println("  EACH LISTA --> " + mAST.getRetornoTipo());
        // System.out.println("  EACH ITERADOR --> " + mTipagem);
        // System.out.println("  ERROS = " + mRunTime.getErros().size());

        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Struct::" + "Lista<>Iterador<" + mTipagem + ">>" + ":" + HEAPID + ">";

      //  System.out.println(eNome);
        AST_Implementador mImplementador = new AST_Implementador();

        AST eAST = mImplementador.criar_InitGenerico("Iterador","Lista<" + mTipagem + ">");

        AST eArgs = eAST.getBranch("ARGUMENTS");

        AST eArg = mList.copiar();
        eArg.setTipo("ARGUMENT");
        eArgs.getASTS().add(eArg);

        //  eArgs.ImprimirArvoreDeInstrucoes();

        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.setNome(eNome);
        mRun_Struct.init("Iterador<Lista<" + mTipagem + ">>", eAST, EachEscopo);

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mRunTime.adicionarHeap(mRun_Struct);

        String eNomeEach = "{{EACH}}::" + HEAPID;

        // System.out.println("  ERROS = " + mRunTime.getErros().size());

        EachEscopo.criarDefinicao(eNomeEach, "Lista<>Iterador<Lista<" + mTipagem + ">>", eNome);

        //  System.out.println(eNomeEach + " -->> PRONTA ");


        AST mExecute = mImplementador.criar_ExecuteFunction(eNomeEach, "iniciar");


        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
        mASTExecute.init(mExecute);

        //  System.out.println(eNomeEach + " -->> INICIAR ");

        AST mCondition =   mImplementador.criar_CondicaoStruct_Func(eNomeEach,"continuar");

        AST mBody2 = new AST("BODY");


        AST mGetValor = mImplementador.criar_DefCom_ValueStructFunction(mDef.getNome(), mType,eNomeEach, "getValor");

        AST mProximo = mImplementador.criar_ExecuteFunction(eNomeEach, "proximo");

        mImplementador.adicionar(mBody2,mGetValor);

        mImplementador. copiarBranches(mBody2,mBody);

        mImplementador.adicionar(mBody2,mProximo);


     // System.out.println(  mBody2.ImprimirArvoreDeInstrucoes());

        initEach(mCondition, mBody2, EachEscopo);


        mRunTime.removerHeap(eNome);


    }

    public void initEach(AST mCondicao, AST mCorpo, Escopo EachEscopo) {


        Run_Value mAST = new Run_Value(mRunTime, EachEscopo);
        mAST.init(mCondicao, "bool");

        //   System.out.println("  ERROS IE = " + mRunTime.getErros().size());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getRetornoTipo().contentEquals("bool")) {

            if (mAST.getConteudo().contentEquals("true")) {

                int w = 0;

                while (mAST.getConteudo().contentEquals("true")) {

                    //  System.out.println("  ERROS INTERNO = " + mRunTime.getErros().size());

                    Escopo EscopoInterno = new Escopo(mRunTime, EachEscopo);
                    EscopoInterno.setNome("While");

                    Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                    cAST.init(mCorpo);

                    //  System.out.println(mCorpo.ImprimirArvoreDeInstrucoes());

                    //     System.out.println("  ERROS INTERNO = " + mRunTime.getErros().size());

                    if (cAST.getCancelado()) {
                        break;
                    }
                    if (cAST.getContinuar()) {

                    }
                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    w += 1;

                    mAST = new Run_Value(mRunTime, EachEscopo);
                    mAST.init(mCondicao, "bool");

                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }

                }


            }

        } else {
            mRunTime.getErros().add("O loop deve possuir tipo BOOL !");
        }

    }


}
