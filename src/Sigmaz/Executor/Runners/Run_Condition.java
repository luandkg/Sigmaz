package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Condition {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Condition(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Condition";


    }

    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
    }

    public boolean getRetornado() {
        return mEscopo.getRetornado();
    }

    public Item getRetorno(){ return mEscopo.getRetorno(); }

    public void init(AST ASTCorrente) {


        AST mCondicao = ASTCorrente.getBranch("CONDITION");
        AST mCorpo = ASTCorrente.getBranch("BODY");

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mCondicao, "bool");

        if (mAST.getRetornoTipo().contentEquals("bool")) {

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            if (mAST.getConteudo().contentEquals("true")) {

                //   System.out.println(" --> CONDICIONAR : Escopo Pai : " + mEscopo.getNome() + " -> Structs : " +mEscopo.getStructs().size() );

                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);
                EscopoInterno.setNome("Condicionate");

                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                cAST.init(mCorpo);

                if (cAST.getCancelado()) {
                    mEscopo.setCancelar(true);
                }
                if (cAST.getContinuar()) {
                    mEscopo.setContinuar(true);
                }

                if (cAST.getRetornado()) {

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    mEscopo.setRetornado(true);
                    mEscopo.retorne(cAST.getRetorno());


                }


            } else {

                boolean sucesso = false;

                for (AST fAST : ASTCorrente.getASTS()) {
                    if (fAST.mesmoTipo("OTHER")) {

                        AST ouCondicao = fAST.getBranch("CONDITION");
                        AST ouCorpo = fAST.getBranch("BODY");
                        Run_Value ouAST = new Run_Value(mRunTime, mEscopo);
                        ouAST.init(ouCondicao, "bool");

                        if (mRunTime.getErros().size() > 0) {
                            return;
                        }


                        if (ouAST.getRetornoTipo().contentEquals("bool")) {
                            if (ouAST.getConteudo().contentEquals("true")) {
                                sucesso = true;

                                Escopo EscopoInterno = new Escopo(mRunTime, mEscopo);

                                Run_Body cAST = new Run_Body(mRunTime, EscopoInterno);
                                cAST.init(ouCorpo);
                                if (cAST.getCancelado()) {
                                    mEscopo.setCancelar(true);
                                }
                                if (cAST.getContinuar()) {
                                    mEscopo.setContinuar(true);
                                }

                                if (cAST.getRetornado()) {

                                    if (mRunTime.getErros().size() > 0) {
                                        return;
                                    }

                                    mEscopo.setRetornado(true);
                                    mEscopo.retorne(cAST.getRetorno());


                                }

                            }
                        } else {
                            mRunTime.errar(mLocal, "A condição deve possuir tipo BOOL !");
                        }

                    }
                    if (sucesso) {
                        break;
                    }
                }

                if (sucesso == false) {

                    others(ASTCorrente);

                }


            }

        } else {
            mRunTime.errar(mLocal, "A condição deve possuir tipo BOOL !");
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

                if (cAST.getRetornado()) {

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    mEscopo.setRetornado(true);
                    mEscopo.retorne(cAST.getRetorno());


                }

                break;

            }

        }


    }
}
