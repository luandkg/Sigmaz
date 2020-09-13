package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_While {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_While(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_While";


    }


    public boolean getCancelado(){
        return mEscopo.getCancelar();
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
