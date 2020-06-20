package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Condition {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Condition(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }

    public boolean getCancelado() {
        return mEscopo.getCancelar();
    }

    public boolean getContinuar() {
        return mEscopo.getContinuar();
    }


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


            } else {

                boolean sucesso = false;

                for (AST fAST : ASTCorrente.getASTS()) {
                    if (fAST.mesmoTipo("OTHER")) {

                        AST ouCondicao = fAST.getBranch("CONDITION");
                        AST ouCorpo = fAST.getBranch("BODY");
                        Run_Value ouAST = new Run_Value(mRunTime, mEscopo);
                        ouAST.init(ouCondicao, "bool");
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

                            }
                        } else {
                            mRunTime.getErros().add("A condição deve possuir tipo BOOL !");
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
            mRunTime.getErros().add("A condição deve possuir tipo BOOL !");
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

                break;

            }

        }


    }
}
