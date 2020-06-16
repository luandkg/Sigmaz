package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Step {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Step(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public boolean getCancelado(){
        return mEscopo.getCancelar();
    }


    public void init(AST ASTCorrente) {


        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();



        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mArgumentos.getASTS().get(0), "num");

        if (mAST.getIsNulo()) {
            mRunTime.getErros().add("Valor inicial do Step com problemas !");
        } else if (mAST.getIsPrimitivo()) {

            mEscopo.setDefinido(mPassador,mAST.getPrimitivo());


            Run_Value mAST2 = new Run_Value(mRunTime, mEscopo);
            mAST2.init(mArgumentos.getASTS().get(1), "num");

            int w = 0;

            String mPassante = mAST.getPrimitivo();


            while(!mPassante.contentEquals(mAST2.getPrimitivo())){

           //     System.out.println("C1 : "+mAST.getPrimitivo());
              //  System.out.println("C2 : "+mAST2.getPrimitivo());

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);
                EscopoInterno.setNome("Step");

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(mCorpo,null,"");

                if(cAST.getCancelado()){
                    break;
                }



                w+=1;

                Run_Value mAST3= new Run_Value(mRunTime, mEscopo);
                mAST3.init(mArgumentos.getASTS().get(2), "num");

                mEscopo.setDefinido(mPassador,mAST3.getPrimitivo());
                mPassante = mAST3.getPrimitivo();

                if( mRunTime.getErros().size()>0){
                    break;
                }

            }


        } else {
            mRunTime.getErros().add("Step com problemas !");
        }






    }


}
