package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;
import Sigmaz.Executor.AST_Implementador;

public class Run_Each {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Each(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Each";


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


        String realTipagem = "";


        String eTiparIterador = "Iterador";
        String eTipado = "";


        if (getTipo(mAST.getRetornoTipo()).contentEquals("Lista")) {

            realTipagem = "Lista<>Lista<" + mTipagem + ">";

            if (realTipagem.contentEquals((mAST.getRetornoTipo()))) {

            } else {
                mRunTime.errar(mLocal, "O Tipo da variavel do Iterable nao e compativel : " + realTipagem + " vs " + (mAST.getRetornoTipo()));
                return;
            }

            eTipado="Lista";

        } else if (getTipo(mAST.getRetornoTipo()).contentEquals("Vetor")) {

            realTipagem = "Vetor<>Vetor<" + mTipagem + ">";
            if (realTipagem.contentEquals((mAST.getRetornoTipo()))) {

            } else {
                mRunTime.errar(mLocal, "O Tipo da variavel do Iterable nao e compativel : " + realTipagem + " vs " + (mAST.getRetornoTipo()));
                return;
            }

            eTipado="Vetor";

        } else {
            mRunTime.errar(mLocal, "O Iterable do Each precisa ser do tipo : Lista ou Vetor");
            return;
        }


        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal, "O Iterable nao poder ser nulo !");
            return;
        }

        // System.out.println("  EACH LISTA --> " + mAST.getRetornoTipo());
        // System.out.println("  EACH ITERADOR --> " + mTipagem);
        // System.out.println("  ERROS = " + mRunTime.getErros().size());

        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Struct::" + "Iterador<>Iterador<" + mTipagem + ">>" + ":" + HEAPID + ">";


        if (mRunTime.getErros().size() > 0) {
            return ;
        }

        AST_Implementador mImplementador = new AST_Implementador();

        AST eAST = mImplementador.criar_InitGenerico("Iterador", eTipado + "<" + mTipagem + ">");

        AST eArgs = eAST.getBranch("ARGUMENTS");

        AST eArg = mList.copiar();
        eArg.setTipo("ARGUMENT");
        eArgs.getASTS().add(eArg);


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.setNome(eNome);

        for (String Referencia : mEscopo.getRefers()) {
            mRun_Struct.adicionar_refer(Referencia);
        }


        mRun_Struct.init("Iterador<" + eTipado + "<" + mTipagem + ">>", eAST, EachEscopo);


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mRunTime.adicionarHeap(mRun_Struct);

        String eNomeEach = "{{EACH}}::" + HEAPID;


        EachEscopo.criarDefinicao(eNomeEach, eTiparIterador + "<>Iterador<" + eTipado + "<" + mTipagem + ">>", eNome);


        AST mExecute = mImplementador.criar_ExecuteFunction(eNomeEach, "iniciar");


        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
        mASTExecute.init(mExecute);

        AST mCondition = mImplementador.criar_CondicaoStruct_Func(eNomeEach, "continuar");

        AST mBody2 = new AST("BODY");


        AST mGetValor = mImplementador.criar_DefCom_ValueStructFunction(mDef.getNome(), mType, eNomeEach, "getValor");

        AST mProximo = mImplementador.criar_ExecuteFunction(eNomeEach, "proximo");

        mImplementador.adicionar(mBody2, mGetValor);

        mImplementador.copiarBranches(mBody2, mBody);

        mImplementador.adicionar(mBody2, mProximo);


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
            mRunTime.errar(mLocal, "O loop deve possuir tipo BOOL !");
        }

    }


}
