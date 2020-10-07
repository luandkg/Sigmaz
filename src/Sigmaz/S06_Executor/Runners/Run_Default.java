package Sigmaz.S06_Executor.Runners;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Default {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Default(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Default";

    }


    public Item init(AST ASTCorrente,String eRetorno) {

        Escopo mEscopoInterno = new Escopo(mRunTime, mEscopo);
        mEscopoInterno.setNome("DEFAULT");

        Item Saida = new Item("");

        Saida.setTipo(eRetorno);


        for (String eRefer : mEscopo.getRefers()) {


            //  System.out.println("REFER " + eRefer);

            mEscopoInterno.adicionarRefer(eRefer);

        }

        AST mASTBody = ASTCorrente.getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

        Saida = mAST.getRetorno();

      //  Saida.setNulo(mAST.getIsNulo());
      //  Saida.setPrimitivo(mAST.getIsPrimitivo());
       // Saida.setValor(mAST.getConteudo(),mRunTime,mEscopoInterno);
       // Saida.setTipo(mAST.getRetornoTipo());

        if (Saida.getNulo()) {
            Saida.setTipo(eRetorno);
        }

        //  System.out.println( mFunction.getNome() + "  Retornando -> " + mAST.getConteudo() + " :: " +mAST.getRetornoTipo() );


        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        if (Saida.getTipo().contentEquals("bool")) {
            Saida.setPrimitivo(true);
        } else if (Saida.getTipo().contentEquals("num")) {
            Saida.setPrimitivo(true);
        } else if (Saida.getTipo().contentEquals("string")) {
            Saida.setPrimitivo(true);
        }


        // System.out.println("  Retornando -> " + mAST.getConteudo());

        return Saida;


    }
}
