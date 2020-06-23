package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.*;
import Sigmaz.Executor.Runners.Run_Body;
import Sigmaz.Executor.Runners.Run_Value;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Argumentador {

    public boolean mesmoArgumentos(ArrayList<String> mTipoArgumentos,ArrayList<Item> eArgumentos) {
        boolean ret = false;

      //  System.out.println("\t - Inicio da Checagem :  " + mTipoArgumentos.size() + " e " + eArgumentos.size());


        if (eArgumentos.size() == mTipoArgumentos.size()) {
            int i = 0;
            int v = 0;
            for (Item mArgumentos : eArgumentos) {


                if (mArgumentos.getTipo().contentEquals(mTipoArgumentos.get(i))){

                    v += 1;
                }


             //  System.out.println("\t - Checando Tipo :  " + mArgumentos.getTipo() + " e " + mTipoArgumentos.get(i));

                i += 1;


            }


            if (v == i) {
                ret=true;
            }

            //  System.out.println("\t - Contagem Tipo :  " + i + " e " + v + " -> " + ret);

        }

        return ret;
    }

    public ArrayList<Item> preparar_argumentos(RunTime mRunTime, Escopo mBuscadorDeVariaveis, AST ASTCorrente) {

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        for (AST a : ASTCorrente.getASTS()) {
            if (a.mesmoTipo("ARGUMENT")) {

                // AST v = new AST("VALUE");


                Run_Value mAST = new Run_Value(mRunTime, mBuscadorDeVariaveis);
                mAST.init(a, "<<ANY>>");

                //  System.out.println("\t - Recebendo Parametro : " + " -> " + mAST.getRetornoTipo() + " :: " + mAST.getIsStruct());
                if (mAST.getIsStruct()) {
                    //     System.out.println("\t - --> " + mAST.getObjeto());
                }

                Item v = new Item("");

                v.setModo(0);
                v.setNulo(mAST.getIsNulo());
                v.setPrimitivo(mAST.getIsPrimitivo());
                v.setIsEstrutura(mAST.getIsStruct());

                v.setTipo(mAST.getRetornoTipo());

                v.setValor(mAST.getConteudo());


                mArgumentos.add(v);

                //  System.out.println(" ARG : " + mAST.getConteudo());

            }

        }

        return mArgumentos;

    }

    public void passarParametros(Escopo mEscopoInterno, ArrayList<String> eParametrosNomes, ArrayList<Item> mArgumentos) {

        if (eParametrosNomes.size() > 0) {

            for (int argC = 0; argC < eParametrosNomes.size(); argC++) {

                // System.out.println("\t - Passando Parametro : " + eParametrosNomes.get(argC) + " -> " + mArgumentos.get(argC).getTipo() + " :: " + mArgumentos.get(argC).getValor());

                if (mArgumentos.get(argC).getIsEstrutura()) {
                    mEscopoInterno.criarParametroStruct(eParametrosNomes.get(argC), mArgumentos.get(argC).getTipo(),  mArgumentos.get(argC).getValor());

                    //   System.out.println("\t - Passando Parametro Struct : " + eParametrosNomes.get(argC) + " -> " + mArgumentos.get(argC).getTipo() + " :: " );

                } else {
                    mEscopoInterno.criarParametro(eParametrosNomes.get(argC), mArgumentos.get(argC).getTipo(), mArgumentos.get(argC).getValor());
                }

            }

        }

    }

    public Item executar_Function(RunTime mRunTime, Escopo mStructEscopo, Index_Function mFunction, ArrayList<Item> mArgumentos, String eReturne) {


        Item Saida = new Item("");

        Saida.setTipo(mFunction.getTipo());

        //   System.out.println(" EXECUTAR Function " + mFunction.getNome() + " : " + mFunction.getTipo());


        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        //Escopo mEscopoInterno = new Escopo(mRunTime, null);


        passarParametros(mEscopoInterno, mFunction.getParamentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);

        Saida.setNulo(mAST.getIsNulo());
        Saida.setPrimitivo(mAST.getIsPrimitivo());
        Saida.setValor(mAST.getConteudo());
        Saida.setTipo(mAST.getRetornoTipo());

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

    public void executar_Action(RunTime mRunTime, Escopo mStructEscopo, Index_Action mFunction, ArrayList<Item> mArgumentos) {


        //   System.out.println(" -->> ACTION CALL : " + mFunction.getNome() );
        //  System.out.println("\t - Escopo : " + mStructEscopo.getNome() );


        Escopo mEscopoInterno = new Escopo(mRunTime, mStructEscopo);
        //Escopo mEscopoInterno = new Escopo(mRunTime, null);

        mEscopoInterno.setNome(mStructEscopo.getNome());


        passarParametros(mEscopoInterno, mFunction.getParamentos(), mArgumentos);


        //  mEscopoInterno.ListarAll();


        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);


        //   System.out.println( mFunction.getNome() + "  Retornando -> " + mAST.getConteudo() + " :: " +mRetornoTipo );


        if (mRunTime.getErros().size() > 0) {

        }


    }

    public Item executar_FunctionGlobal(RunTime mRunTime, Index_Function mFunction, ArrayList<Item> mArgumentos, String eReturne) {


        return executar_Function(mRunTime, mRunTime.getEscopoGlobal(), mFunction, mArgumentos, eReturne);


    }

}
