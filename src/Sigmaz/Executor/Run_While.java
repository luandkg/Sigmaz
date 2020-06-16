package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_While {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_While(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public boolean getCancelado(){
        return mEscopo.getCancelar();
    }


    public void init(AST ASTCorrente) {


        AST mCondicao = ASTCorrente.getBranch("CONDITION");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mCondicao, "bool");

        if (mAST.getRetornoTipo().contentEquals("bool")) {

            if (mAST.getPrimitivo().contentEquals("true")) {

                int w = 0;

                while(mAST.getPrimitivo().contentEquals("true")){

                    Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);
                    EscopoInterno.setNome("While");

                    Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                    cAST.init(mCorpo,null,"");

                    if(cAST.getCancelado()){
                        break;
                    }
                    if(cAST.getContinuar()){

                     //   System.out.println("\n  -->> Voltando o Continuar : " + mEscopo.getNome());

                        // break;
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
            mRunTime.getErros().add("O loop deve possuir tipo BOOL !");
        }

    }


}
