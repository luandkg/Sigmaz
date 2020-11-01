package Sigmaz.S08_Executor.Runners;

import Sigmaz.S08_Executor.Escopo;
import Sigmaz.S08_Executor.Item;
import Sigmaz.S08_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_When {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private boolean mRetornado;
    private Item mItem;

    public Run_When(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_When";

        mRetornado=false;
        mItem=null;
    }

    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
    }

    public Item getRetorno(){ return mItem; }
    public boolean getRetornado() { return mRetornado; }

    public void Retorne(Item eItem){
        mEscopo.retorne(eItem);
        mItem=eItem;
    }


    public void init(AST ASTCorrente) {


        AST mCondicao = ASTCorrente.getBranch("CHOOSABLE");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mCondicao, "<<ANY>>");

        boolean sucesso = false;

        if (mRunTime.getErros().size() > 0) {
           return;
        }


        for (AST fAST : ASTCorrente.getBranch("CASES").getASTS()) {

            AST ouCondicao = fAST.getBranch("CONDITION");
            AST ouCorpo = fAST.getBranch("BODY");
            Run_Value ouAST = new Run_Value(mRunTime, mEscopo);
            ouAST.init(ouCondicao, "<<ANY>>");

            if (ouAST.getConteudo().contentEquals(mAST.getConteudo())) {

                sucesso = true;

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(ouCorpo);

                if (cAST.getCancelado()) {
                    sucesso=true;
                  break;
                }

                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }


           }

        }

        if(!sucesso){
            others(ASTCorrente);
        }


    }

    public void others(AST ASTCorrente) {

        for (AST fAST : ASTCorrente.getASTS()) {
            if (fAST.mesmoTipo("OTHERS")) {


                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(fAST);

                if (cAST.getCancelado()) {
                    mEscopo.setCancelar(true);
                }
                if (cAST.getContinuar()) {
                    mEscopo.setContinuar(true);
                }

                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                break;

            }

        }


    }
}
