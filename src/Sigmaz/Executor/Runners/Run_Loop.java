package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Loop {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    private boolean mRetornado;
    private Item mItem;

    public Run_Loop(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Loop";


        mRetornado=false;
        mItem=null;
    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public Item getRetorno(){ return mItem; }
    public boolean getRetornado() { return mRetornado; }

    public void Retorne(Item eItem){
        mEscopo.retorne(eItem);
        mItem=eItem;
    }

    public void init(AST ASTCorrente) {


        AST mCorpo = ASTCorrente.getBranch("BODY");


        if (mRunTime.getErros().size() > 0) {
            return;
        }


        boolean executando = true;


        while (executando) {

            Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);
            EscopoInterno.setNome("Loop");

            Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
            cAST.init(mCorpo);

            if (cAST.getCancelado()) {
                break;
            }
            if (cAST.getContinuar()) {

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


}
