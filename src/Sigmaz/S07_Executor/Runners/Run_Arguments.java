package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.*;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Argument;
import Sigmaz.S07_Executor.Indexador.Index_Function;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Run_Arguments {


    public ArrayList<Item> preparar_argumentos(RunTime mRunTime, Escopo mBuscadorDeVariaveis, AST ASTCorrente) {

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        for (AST a : ASTCorrente.getASTS()) {
            if (a.mesmoTipo("ARGUMENT")) {


                Run_Value mAST = new Run_Value(mRunTime, mBuscadorDeVariaveis);
                mAST.init(a, "<<ANY>>");


                Item v = new Item("");
                v.setModo(0);
                v.setNulo(mAST.getIsNulo());
                v.setPrimitivo(mAST.getIsPrimitivo());
                v.setIsEstrutura(mAST.getIsStruct());
                v.setTipo(mAST.getRetornoTipo());
                v.setValor(mAST.getConteudo(), mRunTime, mBuscadorDeVariaveis);

                if (mAST.getIsReferenciavel()) {
                    v.setIsReferenciavel(true);
                    v.setReferencia(mAST.getReferencia());
                }

                mArgumentos.add(v);


            }

        }

        return mArgumentos;

    }

    public void passarParametros(Escopo mEscopoInterno, ArrayList<Index_Argument> eParametros, ArrayList<Item> mArgumentos) {

        if (eParametros.size() == mArgumentos.size()) {


            if (eParametros.size() > 0) {

                for (int argC = 0; argC < eParametros.size(); argC++) {


                    if (eParametros.get(argC).getModo().contentEquals("VALUE")) {
                        mEscopoInterno.passarParametroByValue(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                    } else if (eParametros.get(argC).getModo().contentEquals("REF")) {
                        mEscopoInterno.passarParametroByRef(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                    } else {
                        mEscopoInterno.getRunTime().getErros().add("Passagem de parametro desconhecida : " + eParametros.get(argC).getModo());
                        return;
                    }

                    if (mArgumentos.get(argC).getTemValor()) {
                        if (mArgumentos.get(argC).getIsEstrutura()) {
                            mEscopoInterno.getRunTime().getHeap().aumentar(mArgumentos.get(argC).getValor(mEscopoInterno.getRunTime(), mEscopoInterno));
                        }
                    }


                }

            }
        } else {
            mEscopoInterno.getRunTime().errar("RunArguments", "Argumentos Incompativeis");

            String a = "";

            for (Index_Argument e : eParametros) {
                a += e.getNome() + ",";
            }

            String b = "";

            for (Item e : mArgumentos) {
                b += e.getValor(mEscopoInterno.getRunTime(), mEscopoInterno) + ",";
            }

            mEscopoInterno.getRunTime().errar("RunArguments", eParametros.size() + " - Argumentadores : " + a);
            mEscopoInterno.getRunTime().errar("RunArguments", mArgumentos.size() + " - Argumentos : " + b);

        }

    }


    public int conferirArgumentos(RunTime mRunTime,  ArrayList<Index_Argument> mParametros, ArrayList<Item> eArgumentos) {

        int v = 0;
        int i = 0;

        // System.out.println("\t - Inicio da Checagem :  " + mTipoArgumentos.size() + " e " + eArgumentos.size());

        int argumentacao = eArgumentos.size();
        int parametragem = mParametros.size();

        if (argumentacao == parametragem) {

            for (Item mArgumentos : eArgumentos) {

                String eArgumento = mArgumentos.getTipo();
                String eParametro = mParametros.get(i).getTipo();

                //    System.out.println("\t - Conferindo Tipo :  " + mArgumentos.getValor(mRunTime,mEscopo) + " de " + eArgumento + " e " + eArgumento);

                if (eArgumento.contentEquals(eParametro)) {
                    v += 1;
                } else if (eParametro.contentEquals("any")) {
                    v += 1;
                } else if (eParametro.contentEquals("<<ANY>>")) {
                    v += 1;
                } else if (eArgumento.contentEquals("<<ANY>>")) {
                    v += 1;
                } else if (eArgumento.contentEquals("any")) {
                    v += 1;
                }


                i += 1;

                if (i != v) {
                    break;
                }

                if (mRunTime.getErros().size() > 0) {
                    break ;
                }

            }


            //  System.out.println("\t - Contagem Tipo :  " + i + " e " + v + " -> " + ret);

        }

       // if(i!=v){
       //     System.out.println("\t - Conferiu :: " + v + " e " + i + " de " + argumentacao);
      //  }


        return v;
    }


}
