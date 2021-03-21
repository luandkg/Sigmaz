package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S08_Utilitarios.AST;

public class Run_Step {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private boolean mRetornado;
    private Item mItem;

    public Run_Step(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Step";

        mRetornado = false;
        mItem = null;

    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getRetornado() {
        return mRetornado;
    }

    public Item getRetorno() {
        return mItem;
    }

    public void Retorne(Item eItem) {
        mEscopo.retorne(eItem);
        mItem = eItem;
    }

    public void init(AST ASTCorrente) {

        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mArgumentos.getASTS().get(0), "num");

        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal, "Valor inicial do Step com problemas !");
        } else if (mAST.getIsPrimitivo()) {

         //   System.out.println("Definindo... ");

            mEscopo.setDefinido(mPassador, mAST.getConteudo());

         //   System.out.println("Definido !");

             lacoStep(mEscopo,mPassador,mArgumentos, mCorpo, mAST);

        } else {
            mRunTime.errar(mLocal, "Step com problemas !");
        }


    }

    public void initDef(AST ASTCorrente) {

        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();

        Escopo mEscopoStep = new Escopo(mRunTime, mEscopo);

        Run_Value mAST = new Run_Value(mRunTime, mEscopoStep);
        mAST.init(mArgumentos.getASTS().get(0), "num");


        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal, "Valor inicial do Step com problemas !");
        } else if (mAST.getIsPrimitivo()) {

            mEscopoStep.criarDefinicao(mPassador, mAST.getRetornoTipo(),mAST.getConteudo());

            lacoStep(mEscopoStep,mPassador,mArgumentos, mCorpo, mAST);


        } else {
            mRunTime.errar(mLocal, "Step com problemas !");
        }


    }

    public void initLet(AST ASTCorrente) {

        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();

        Escopo mEscopoStep = new Escopo(mRunTime, mEscopo);

        Run_Value mAST = new Run_Value(mRunTime, mEscopoStep);
        mAST.init(mArgumentos.getASTS().get(0), "num");


        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal, "Valor inicial do Step Let com problemas !");
        } else if (mAST.getIsPrimitivo()) {

            mEscopoStep.criarDefinicao(mPassador, mAST.getRetornoTipo(), mAST.getConteudo());

            lacoStep(mEscopoStep,mPassador,mArgumentos, mCorpo, mAST);

        } else {
            mRunTime.errar(mLocal, "Step com problemas !");
        }


    }

    public void initMut(AST ASTCorrente) {

        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();

        Escopo mEscopoStep = new Escopo(mRunTime, mEscopo);

        Run_Value mAST = new Run_Value(mRunTime, mEscopoStep);
        mAST.init(mArgumentos.getASTS().get(0), "num");


        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal, "Valor inicial do Step Mut com problemas !");
        } else if (mAST.getIsPrimitivo()) {

            mEscopoStep.criarMutavelPrimitivo(ASTCorrente.getNome(), mAST.getRetornoTipo(), mAST.getConteudo());

            lacoStep(mEscopoStep,mPassador,mArgumentos, mCorpo, mAST);

        } else {
            mRunTime.errar(mLocal, "Step com problemas !");
        }


    }

    private void lacoStep(Escopo mEscopoStep,String mVariavel,AST mArgumentos, AST mCorpo, Run_Value mAST) {

        Run_Value mAST2 = new Run_Value(mRunTime, mEscopo);
        mAST2.init(mArgumentos.getASTS().get(1), "num");

        if (mAST.getIsNulo()){
            mRunTime.errar(mLocal, "Step com problemas no inicializador !");
            return;
        }

        if (mAST2.getIsNulo()){
            mRunTime.errar(mLocal, "Step com problemas no finalizador !");
            return;
        }

        String mProximo = mAST.getConteudo();
        String mFinalizado =  mAST2.getConteudo();

        // System.out.println("P : " + mProximo);
        //  System.out.println("F : " + mFinalizado);

        while (!mProximo.contentEquals(mFinalizado)) {


            Escopo EscopoInterno = new Escopo(mRunTime, mEscopoStep);
            EscopoInterno.setNome("Step");

            Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
            cAST.init(mCorpo);

            if (cAST.getCancelado()) {
                break;
            }

            if (cAST.getRetornado()) {
                Retorne(cAST.getRetorno());
                break;
            }


            Run_Value mAST3 = new Run_Value(mRunTime, mEscopoStep);
            mAST3.init(mArgumentos.getASTS().get(2), "num");

            if (mAST3.getIsNulo()){
                mRunTime.errar(mLocal, "Step com problemas no incrementador !");
                return;
            }

            mProximo = mAST3.getConteudo();

           // System.out.println("P : " + mProximo);

            mEscopoStep.setDefinido(mVariavel, mProximo);

            if (mRunTime.getErros().size() > 0) {
                break;
            }

        }


    }

}
