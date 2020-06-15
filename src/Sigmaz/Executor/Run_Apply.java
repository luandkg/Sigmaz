package Sigmaz.Executor;

import Sigmaz.Utils.AST;

public class Run_Apply {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Apply(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST ASTCorrente) {


        AST mSettable = ASTCorrente.getBranch("SETTABLE");
        AST mValue = ASTCorrente.getBranch("VALUE");

        if (mSettable.mesmoValor("ID")) {

            Run_Value mAST = new Run_Value(mRunTime, mEscopo);
            mAST.init(mValue, mEscopo.getDefinidoTipo(mSettable.getNome()));

            if (mAST.getIsNulo()) {
                mEscopo.anularDefinido(mSettable.getNome());
            } else if (mAST.getIsPrimitivo()) {

                String eValor = mAST.getPrimitivo();
                String eTipo = mEscopo.getDefinidoTipo(mSettable.getNome());

                if (eTipo.contentEquals(mAST.getRetornoTipo())) {
                    mEscopo.setDefinido(mSettable.getNome(), eValor);
                } else {
                    mRunTime.getErros().add("Retorno incompativel : Era esperado " + eTipo + " mas retornou " + mAST.getRetornoTipo());
                }




            } else {
                mRunTime.getErros().add("AST_Value com problemas !");
            }


        } else {
            mRunTime.getErros().add("Nao é possível realizar essa atribuicao !");
        }


    }

}
