package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;
import Sigmaz.Utils.AST_Implementador;

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

    public String getSubTipo(String eTipo) {
        String mTipo = "";

        int i = 0;
        int o = eTipo.length() - 1;

        boolean pegar = false;

        while (i < o) {
            String l = eTipo.charAt(i) + "";
            if (l.contentEquals("<")) {
                pegar = true;
                i += 1;
                break;
            }
            i += 1;
        }

        if (pegar) {

            while (i < o) {
                String l = eTipo.charAt(i) + "";
                mTipo += l;
                i += 1;
            }

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

        if (mTipagem.contentEquals(getSubTipo(mAST.getRetornoTipo()))) {

        } else {
            mRunTime.getErros().add("O Tipo da variavel do Iterable nao e compativel : " + mTipagem + " vs " + getSubTipo(mAST.getRetornoTipo()));
            return;
        }

        if (mAST.getIsNulo()) {
            mRunTime.getErros().add("O Iterable nao poder ser nulo !");
            return;
        }


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
        AST_Implementador ASTC = new AST_Implementador();


        AST mExecute = ASTC.criar_ExecuteFunction(eNomeEach, "iniciar");


        Run_Execute mASTExecute = new Run_Execute(mRunTime, EachEscopo);
        mASTExecute.init(mExecute);

        //  System.out.println(eNomeEach + " -->> INICIAR ");


        AST mCondition = new AST("CONDITION");
        mCondition.setNome(eNomeEach);
        mCondition.setValor("STRUCT");
        AST mInternalCondition = mCondition.criarBranch("INTERNAL");
        mInternalCondition.setNome("continuar");
        mInternalCondition.setValor("STRUCT_FUNCT");
        mInternalCondition.criarBranch("ARGUMENTS");


        AST mBody2 = new AST("BODY");


        AST mDefWhile = ASTC.criar_Def(mDef.getNome(),mType);

        mDefWhile.getASTS().add(ASTC.criar_ValueStructFunction(eNomeEach,"getValor"));



        mBody2.getASTS().add(mDefWhile);

        mBody2.getASTS().addAll(mBody.getASTS());


        mBody2.getASTS().add(ASTC.criar_ExecuteFunction(eNomeEach, "proximo"));


        //     mWhile.ImprimirArvoreDeInstrucoes();

        initEach(mCondition, mBody2, EachEscopo);

        // System.out.println(eNomeEach + " -->> PROXIMO ");


        mRunTime.removerHeap(eNome);


    }

    public void initEach(AST mCondicao, AST mCorpo, Escopo EachEscopo) {


        Run_Value mAST = new Run_Value(mRunTime, EachEscopo);
        mAST.init(mCondicao, "bool");

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getRetornoTipo().contentEquals("bool")) {

            if (mAST.getConteudo().contentEquals("true")) {

                int w = 0;

                while (mAST.getConteudo().contentEquals("true")) {

                    Escopo EscopoInterno = new Escopo(mRunTime, EachEscopo);
                    EscopoInterno.setNome("While");

                    Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                    cAST.init(mCorpo);

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
