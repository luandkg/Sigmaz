package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_When {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_When(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_When";


    }

    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
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

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                break;

            }

        }


    }
}
