package Sigmaz.S06_Executor.Runners;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_While {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private boolean mRetornado;
    private Item mItem;

    public Run_While(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_While";

        mRetornado=false;
        mItem=null;

    }


    public boolean getCancelado(){
        return mEscopo.getCancelar();
    }

    public Item getRetorno(){ return mItem; }
    public boolean getRetornado() { return mRetornado; }

    public void Retorne(Item eItem){
        mEscopo.retorne(eItem);
        mItem=eItem;
    }

    public void init(AST ASTCorrente) {


        AST mCondicao = ASTCorrente.getBranch("CONDITION");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mCondicao, "bool");

        if (mRunTime.getErros().size() > 0) {
           return;
        }

        if (mAST.getRetornoTipo().contentEquals("bool")) {

            if (mAST.getConteudo().contentEquals("true")) {

                int w = 0;

                while(mAST.getConteudo().contentEquals("true")){

                    Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);
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

                    if (cAST.getRetornado()){
                        Retorne(cAST.getRetorno());
                        break;
                    }


                    w+=1;

                    mAST = new Run_Value(mRunTime, mEscopo);
                    mAST.init(mCondicao, "bool");

                    if( mRunTime.getErros().size()>0){
                        break;
                    }

                }


            }

        } else {
            mRunTime.errar(mLocal,"O loop deve possuir tipo BOOL !");
        }

    }


}
