package Sigmaz.S07_Executor.Runners;

import Sigmaz.S00_Utilitarios.AST;
import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

public class Run_Escopo {

    public void executar_Action(RunTime mRunTime, Escopo mStructEscopo, Index_Action mFunction, ArrayList<Item> mArgumentos) {

        Run_Arguments mRun_Arguments = new Run_Arguments();

        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());

        mRun_Arguments.passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        if (mRunTime.getErros().size() > 0) {
            return ;
        }
        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

    }


    public Item executar_Function(RunTime mRunTime, Escopo mStructEscopo, Index_Function mFunction, ArrayList<Item> mArgumentos, String eReturne ) {


        Item Saida = new Item("");

        Saida.setTipo(mFunction.getTipo());



        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());


        for (String eRefer : mStructEscopo.getRefers()) {
            mEscopoInterno.adicionarRefer(eRefer);
        }

        Run_Arguments mRun_Arguments = new Run_Arguments();

        mRun_Arguments.  passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        //  if (mFunction.getTipo().contentEquals(mAST.getRetorno().getTipo())){
        //  }else{
        //  System.out.println("Voltando Tipo : " + mAST.getRetorno().getTipo());
        // System.out.println("Esperava Tipo : " + mFunction.getTipo());
        //    mRunTime.errar("Run_Arguments","Era esperado retornar " + mFunction.getTipo() + " mas retornou " + mAST.getRetorno().getTipo());
        //    mRunTime.errar("RA",mFunction.getPonteiro().getImpressao());
        // }

        Saida = mAST.getRetorno();

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        if (Saida.getNulo()) {
            Saida.setTipo(eReturne);
        }

        //   System.out.println("EXECUTAR Function Retorando -> " + mFunction.getNome() + " " + Saida.getValor(mRunTime,mEscopoInterno) + " :: " +Saida.getTipo() );


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

    public Item executar_ActionComRetorno(RunTime mRunTime, Escopo mStructEscopo, Index_Action mFunction, ArrayList<Item> mArgumentos, String eReturne ) {


        Item Saida = new Item("");

        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        mEscopoInterno.setNome(mFunction.getNome());


        for (String eRefer : mStructEscopo.getRefers()) {

            mEscopoInterno.adicionarRefer(eRefer);

        }
        Run_Arguments mRun_Arguments = new Run_Arguments();

        mRun_Arguments. passarParametros(mEscopoInterno, mFunction.getArgumentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);


        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        Saida = mAST.getRetorno();

        if (Saida.getNulo()) {
            Saida.setTipo(eReturne);
        }


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


        return Saida;
    }




}
