package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Each {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Each(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }


    public void init(AST ASTCorrente) {


        Escopo EachEscopo = new Escopo(mRunTime,mEscopo);

        AST mDef = ASTCorrente.getBranch("DEF");
        AST mType = ASTCorrente.getBranch("TYPE");
        AST mList = ASTCorrente.getBranch("LIST");
        AST mBody = ASTCorrente.getBranch("BODY");

        String mTipagem = mRunTime.getTipagem(mType);


        Run_Value mAST = new Run_Value(mRunTime, EachEscopo);
        mAST.init(mList, "<<ANY>>");


      //  System.out.println("Def : " + mDef.getNome());
     //   System.out.println("Tipo : " + mTipagem);
     //   System.out.println("Lista : " + mAST.getConteudo());

        long HEAPID = mRunTime.getHEAPID();
        String eNome = "<Struct::" + "Iterador<Lista<" + mTipagem + ">>" + ":" + HEAPID + ">";

        AST eAST = new AST("INIT");
        eAST.setNome("Iterador");
        AST eGen = eAST.criarBranch("GENERIC");
        AST eType = eGen.criarBranch("TYPE");
        eType.setNome("Lista<" + mTipagem + ">");
        eGen.setNome("TRUE");

        AST eArgs = eAST.criarBranch("ARGUMENTS");
        AST eArg = mList.copiar();
        eArg.setTipo("ARGUMENT");
        eArgs.getASTS().add(eArg);

      //  eArgs.ImprimirArvoreDeInstrucoes();

        Run_Struct mRun_Struct = new Run_Struct(mRunTime);
        mRun_Struct.setNome(eNome);
        mRun_Struct.init("Iterador<Lista<" + mTipagem + ">>", eAST, EachEscopo);


        mRunTime.adicionarHeap(mRun_Struct);

        String eNomeEach = "{{EACH}}::" + HEAPID;

        EachEscopo.criarDefinicao(eNomeEach, "Iterador<Lista<" + mTipagem + ">>", eNome);

      //  System.out.println(eNomeEach + " -->> PRONTA ");

        AST mExecute = new AST("EXECUTE");
        mExecute.setNome(eNomeEach);
        mExecute.setValor("STRUCT");
        AST mInternal = mExecute.criarBranch("INTERNAL");
        mInternal.setNome("iniciar");
        mInternal.setValor("STRUCT_FUNCT");
        mInternal.criarBranch("ARGUMENTS");

        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
        mASTExecute.init(mExecute);

      //  System.out.println(eNomeEach + " -->> INICIAR ");


        AST mWhile = new AST("WHILE");
        AST mCondition = mWhile.criarBranch("CONDITION");
        mCondition.setNome(eNomeEach);
        mCondition.setValor("STRUCT");
        AST mInternalCondition = mCondition.criarBranch("INTERNAL");
        mInternalCondition.setNome("continuar");
        mInternalCondition.setValor("STRUCT_FUNCT");
        mInternalCondition.criarBranch("ARGUMENTS");







        AST mBody2 = mWhile.criarBranch("BODY");


        AST mDefWhile = mBody2.criarBranch("DEF");
        mDefWhile.setNome(mDef.getNome());
        mDefWhile.getASTS().add(mType);
        AST mVal = mDefWhile.criarBranch("VALUE");
        mVal.setNome(eNomeEach);
        mVal.setValor("STRUCT");
        AST mInternalVal = mVal.criarBranch("INTERNAL");
        mInternalVal.setNome("getValor");
        mInternalVal.setValor("STRUCT_FUNCT");
        mInternalVal.criarBranch("ARGUMENTS");


        mBody2.getASTS().addAll(mBody.getASTS());


        AST mExecute2 = mBody2.criarBranch("EXECUTE");
        mExecute2.setNome(eNomeEach);
        mExecute2.setValor("STRUCT");
        AST mInternal2 = mExecute2.criarBranch("INTERNAL");
        mInternal2.setNome("proximo");
        mInternal2.setValor("STRUCT_FUNCT");
        mInternal2.criarBranch("ARGUMENTS");


   //     mWhile.ImprimirArvoreDeInstrucoes();

        initWhile(mWhile,EachEscopo);

       // System.out.println(eNomeEach + " -->> PROXIMO ");


        mRunTime.removerHeap(eNome);


    }

    public void initWhile(AST ASTCorrente,Escopo EachEscopo) {


        AST mCondicao = ASTCorrente.getBranch("CONDITION");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        Run_Value mAST = new Run_Value(mRunTime, EachEscopo);
        mAST.init(mCondicao, "bool");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getRetornoTipo().contentEquals("bool")) {

            if (mAST.getConteudo().contentEquals("true")) {

                int w = 0;

                while(mAST.getConteudo().contentEquals("true")){

                    Escopo EscopoInterno = new Escopo(mRunTime, EachEscopo);
                    EscopoInterno.setNome("While");

                    Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                    cAST.init(mCorpo);

                    if(cAST.getCancelado()){
                        break;
                    }
                    if(cAST.getContinuar()){

                    }
                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    w+=1;

                    mAST = new Run_Value(mRunTime, EachEscopo);
                    mAST.init(mCondicao, "bool");

                    if( mRunTime.getErros().size()>0){
                        break;
                    }

                }


            }

        } else {
            mRunTime.getErros().add("O loop deve possuir tipo BOOL !");
        }

    }



}
