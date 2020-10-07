package Sigmaz.S06_Executor.Runners;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

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

        mRetornado=false;
        mItem=null;

    }


    public boolean getCancelado(){
        return mEscopo.getCancelar();
    }
    public boolean getRetornado() { return mRetornado; }

    public Item getRetorno(){ return mItem; }

    public void Retorne(Item eItem){
        mEscopo.retorne(eItem);
        mItem=eItem;
    }

    public void init(AST ASTCorrente) {


        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();



        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mArgumentos.getASTS().get(0), "num");

        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal,"Valor inicial do Step com problemas !");
        } else if (mAST.getIsPrimitivo()) {

            mEscopo.setDefinido(mPassador,mAST.getConteudo());


            Run_Value mAST2 = new Run_Value(mRunTime, mEscopo);
            mAST2.init(mArgumentos.getASTS().get(1), "num");

            int w = 0;

            String mPassante = mAST.getConteudo();


            while(!mPassante.contentEquals(mAST2.getConteudo())){

           //     System.out.println("C1 : "+mAST.getPrimitivo());
              //  System.out.println("C2 : "+mAST2.getPrimitivo());

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);
                EscopoInterno.setNome("Step");

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(mCorpo);

                if(cAST.getCancelado()){
                    break;
                }

                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }


                w+=1;

                Run_Value mAST3= new Run_Value(mRunTime, mEscopo);
                mAST3.init(mArgumentos.getASTS().get(2), "num");

                mEscopo.setDefinido(mPassador,mAST3.getConteudo());
                mPassante = mAST3.getConteudo();

                if( mRunTime.getErros().size()>0){
                    break;
                }

            }


        } else {
            mRunTime.errar(mLocal,"Step com problemas !");
        }






    }

    public void initDef(AST ASTCorrente) {


        AST mArgumentos = ASTCorrente.getBranch("ARGUMENTS");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        String mPassador = ASTCorrente.getNome();



        Escopo EscopoPrimario= new Escopo(mRunTime, mEscopo);

        EscopoPrimario.criarDefinicaoNula(ASTCorrente.getNome(), ASTCorrente.getValor());


        Run_Value mAST = new Run_Value(mRunTime, EscopoPrimario);
        mAST.init(mArgumentos.getASTS().get(0), "num");

        if (mAST.getIsNulo()) {
            mRunTime.errar(mLocal,"Valor inicial do Step com problemas !");
        } else if (mAST.getIsPrimitivo()) {

            EscopoPrimario.setDefinido(mPassador,mAST.getConteudo());


            Run_Value mAST2 = new Run_Value(mRunTime, EscopoPrimario);
            mAST2.init(mArgumentos.getASTS().get(1), "num");

            int w = 0;

            String mPassante = mAST.getConteudo();


            while(!mPassante.contentEquals(mAST2.getConteudo())){

                //     System.out.println("C1 : "+mAST.getPrimitivo());
                //  System.out.println("C2 : "+mAST2.getPrimitivo());

                Escopo EscopoInterno = new Escopo(mRunTime, EscopoPrimario);
                EscopoInterno.setNome("Step");

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(mCorpo);

                if(cAST.getCancelado()){
                    break;
                }
                if (cAST.getRetornado()){
                    Retorne(cAST.getRetorno());
                    break;
                }


                w+=1;

                Run_Value mAST3= new Run_Value(mRunTime, EscopoPrimario);
                mAST3.init(mArgumentos.getASTS().get(2), "num");

                if( mRunTime.getErros().size()>0){
                    break;
                }

                EscopoPrimario.setDefinido(mPassador,mAST3.getConteudo());
                mPassante = mAST3.getConteudo();

                if( mRunTime.getErros().size()>0){
                    break;
                }

            }


        } else {
            mRunTime.errar(mLocal,"Step com problemas !");
        }






    }


}
