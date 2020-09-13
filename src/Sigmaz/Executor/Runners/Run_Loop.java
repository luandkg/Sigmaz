package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Loop {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Loop(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Loop";


    }


    public boolean getCancelado() {
        return mEscopo.getCancelar();
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

            if (mRunTime.getErros().size() > 0) {
                return;
            }



        }


    }


}
