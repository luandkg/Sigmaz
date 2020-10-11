package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.AST_Implementador;
import Sigmaz.S00_Utilitarios.AST;

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

    public boolean getRetornado() {
        return mEscopo.getRetornado();
    }

    public Item getRetorno() {
        return mEscopo.getRetorno();
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


       // System.out.println("Lista Tipo : " + mAST.getRetornoTipo());
    //    System.out.println("Lista Tipo Ref : " + getTipo(mAST.getRetornoTipo()));
     //   System.out.println("Lista Tipo Sub : " + getTipo(mAST.getRetornoTipo()));


       // String realTipagem = "";

      //  System.out.println("Item Tipo : " + mTipagem);


       // String mIteradorTipo = "Iterador<>Iterador<" + mAST.getRetornoTipo() + ">";


        if (getTipo(mAST.getRetornoTipo()).contentEquals("Lista")) {


           // realTipagem = "Lista<>Lista<" + mTipagem + ">";
            // realTipagem = mTipagem;

          //  if (realTipagem.contentEquals((mAST.getRetornoTipo()))) {

          //  } else {
          //      mRunTime.errar(mLocal, "O Tipo da variavel do Iterable nao e compativel : " + realTipagem + " vs " + (mAST.getRetornoTipo()));
          //      return;
         //   }

          //  eTipado = "Lista";

        } else if (getTipo(mAST.getRetornoTipo()).contentEquals("Vetor")) {

          //  realTipagem = "Vetor<>Vetor<" + mTipagem + ">";
          //  if (realTipagem.contentEquals((mAST.getRetornoTipo()))) {

           // } else {
            //    mRunTime.errar(mLocal, "O Tipo da variavel do Iterable nao e compativel : " + realTipagem + " vs " + (mAST.getRetornoTipo()));
            //    return;
           // }

           // eTipado = "Vetor";

        } else {
            mRunTime.errar(mLocal, "O Iterable do Each precisa ser do tipo : Lista ou Vetor");
            return;
        }


        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal, "O Iterable nao poder ser nulo !");
            return;
        }

        // System.out.println("  EACH LISTA --> " + mAST.getRetornoTipo());
       //  System.out.println("  EACH ITERADOR --> " + mIteradorTipo);
       //  System.out.println("  EACH ITEM --> " + mTipagem);

       //  System.out.println("  ERROS = " + mRunTime.getErros().size());



        if (mRunTime.getErros().size() > 0) {
            return;
        }

        AST_Implementador mImplementador = new AST_Implementador();

        AST eAST = mImplementador.criar_InitGenerico("Iterador", mAST.getRetornoTipo());
        //AST eAST = mImplementador.criar_InitGenerico("Iterador",  mTipagem );

       // System.out.println("Criar ITERADOR :: " + mTipagemInterador);

        AST eArgs = eAST.getBranch("ARGUMENTS");

        AST eArg = mList.copiar();
        eArg.setTipo("ARGUMENT");
        eArgs.getASTS().add(eArg);


        Run_Struct mRun_Struct = new Run_Struct(mRunTime);

        for (String Referencia : mEscopo.getRefers()) {
            mRun_Struct.adicionar_refer(Referencia);
        }


        mRun_Struct.init(eAST, EachEscopo);

       // System.out.println("RUN STRUCT ITERADOR :: " + mRun_Struct.getNome());
      //  System.out.println("RUN STRUCT ITERADOR TIPO :: " + mRun_Struct.getTipoCompleto());

      //  System.out.println("ERROS :: " + mRunTime.getErros().size());

      //  System.out.println(eAST.getImpressao());


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        mRunTime.getHeap().adicionarStruct(mRun_Struct);

        String eNomeEach = "{{EACH}}::" + mRunTime.getHeap().getHEAPID();

        String eNome = mRun_Struct.getNome();

       // String eTipoIterador = eTiparIterador + "<>Iterador<" +  mTipagem + ">";

       // EachEscopo.criarDefinicao(eNomeEach, eTiparIterador + "<>Iterador<" + eTipado + "<" + mTipagem + ">>", mRun_Struct.getNome());
        EachEscopo.criarDefinicao(eNomeEach, mRun_Struct.getTipoCompleto(), mRun_Struct.getNome());

       // System.out.println("Tipo Each Iteravel :: " + mTipagem);
       // EachEscopo.getDebug().mapear_stack();

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


        mRunTime.getHeap().removerStruct(eNome);


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
