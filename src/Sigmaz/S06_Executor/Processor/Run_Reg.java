package Sigmaz.S06_Executor.Processor;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.Runners.Run_Valoramento;
import Sigmaz.S06_Executor.Runners.Run_Value;

public class Run_Reg {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Reg(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Reg";

    }


    public void init(AST ASTCorrente) {


        AST mValor = ASTCorrente.getBranch("VALUE");


        Run_Valoramento mRun_Valoramento = new Run_Valoramento(mRunTime, mEscopo);
        Run_Value mAST = mRun_Valoramento.init(ASTCorrente.getNome(), mValor, "<<ANY>>", "<<ANY>>");


        if (mRunTime.getErros().size() > 0) {
            return;
        }

        if (mAST.getIsNulo()) {

            if (ASTCorrente.getNome().contentEquals("R0")) {

                mRunTime.getProcessador().aplicar_nulo(ASTCorrente.getNome(), "true");

            } else {

                mRunTime.errar(mLocal, "O registrador " + ASTCorrente.getNome() + " nao pode receber um valor nulo !");

            }
        } else {


            if (mRunTime.getProcessador().getRegistradores().contains(ASTCorrente.getNome())) {

                if (ASTCorrente.getNome().contentEquals("R0")) {

                    mRunTime.getProcessador().aplicar_nulo(ASTCorrente.getNome(), "false");


                } else {

                    if (mAST.getRetornoTipo().contentEquals("bool")) {

                        mRunTime.getProcessador().aplicar_logico(ASTCorrente.getNome(), mAST.getConteudo());

                    } else if (mAST.getRetornoTipo().contentEquals("int")) {

                        mRunTime.getProcessador().aplicar_inteiro(ASTCorrente.getNome(), mAST.getConteudo());

                    } else if (mAST.getRetornoTipo().contentEquals("num")) {

                        mRunTime.getProcessador().aplicar_real(ASTCorrente.getNome(), mAST.getConteudo());

                    } else if (mAST.getRetornoTipo().contentEquals("string")) {

                        mRunTime.getProcessador().aplicar_texto(ASTCorrente.getNome(), mAST.getConteudo());


                    } else {

                        mRunTime.errar(mLocal, "O registrador " + ASTCorrente.getNome() + "  nao pode receber o tipo " + mAST.getRetornoTipo());

                    }

                }



            } else {

                mRunTime.errar(mLocal, "O registrador Desconhecido : " + ASTCorrente.getNome());

            }


        }


    }


}
