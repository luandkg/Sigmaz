package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Try {

    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Try(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST eAST) {


        AST mCorpo = eAST.getBranch("BODY");

        boolean L = false;
        boolean M = false;

        Item eLogic = null;

        if (eAST.getBranch("LOGIC").mesmoNome("TRUE")) {
            L = true;
            eLogic = mEscopo.getItem(eAST.getBranch("LOGIC").getValor());
        }

        Item eMensagem = null;
        if (eAST.getBranch("MESSAGE").mesmoNome("TRUE")) {
            M = true;
            eMensagem = mEscopo.getItem(eAST.getBranch("MESSAGE").getValor());
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if(L){
            if (!eLogic.getTipo().contentEquals("bool")) {
                mRunTime.getErros().add("A variavel logica precisa ser do tipo bool !");
            }
        }

        if(M){
            if (!eMensagem.getTipo().contentEquals("string")) {
                mRunTime.getErros().add("A variavel logica precisa ser do tipo string !");
            }
        }


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if(L){ eLogic.setValor("false");}
        if(M){ eMensagem.setValor("ok");}


        Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

        Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
        cAST.init(mCorpo);


        if (mRunTime.getErros().size() > 0) {




            if(L){ eLogic.setValor("true");}
            if(M){ eMensagem.setValor(mRunTime.getErros().get(0));}

            mRunTime.getErros().clear();

        }

        if (cAST.getCancelado()) {
            mEscopo.setCancelar(true);
        }
        if (cAST.getContinuar()) {
            mEscopo.setCancelar(true);
        }

    }


}


