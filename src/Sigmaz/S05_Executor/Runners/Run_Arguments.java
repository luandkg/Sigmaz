package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.*;
import Sigmaz.S05_Executor.Indexador.Index_Argument;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.AST;

public class Run_Arguments {


    public ArrayList<Item> preparar_argumentos(RunTime mRunTime, Escopo mBuscadorDeVariaveis, AST ASTCorrente) {

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        if (ASTCorrente.getASTS().size()>0){

           // System.out.println("\t\t Preparar Argumentos");

            for (AST a : ASTCorrente.getASTS()) {
                if (a.mesmoTipo("ARGUMENT")) {


                    Run_Value mAST = new Run_Value(mRunTime, mBuscadorDeVariaveis);
                    mAST.init(a, "<<ANY>>");


                    Item v = new Item("");
                    v.setModo(0);
                    v.setNulo(mAST.getIsNulo(),mRunTime);
                    v.setPrimitivo(mAST.getIsPrimitivo());
                    v.setIsEstrutura(mAST.getIsStruct());
                    v.setTipo(mAST.getRetornoTipo());
                    v.setValor(mAST.getConteudo(), mRunTime, mBuscadorDeVariaveis);

                    if (mAST.getIsReferenciavel()) {
                        v.setIsReferenciavel(true);
                        v.setReferencia(mAST.getReferencia());
                    }


                    if (v.getNulo()) {
                    //    System.out.println("\t\t\t - Argumento -->> " + v.getNome() + " : " + v.getTipo() + " -- NULO");
                    } else {
                       // System.out.println("\t\t\t - Argumento -->> " + v.getNome() + " : " + v.getTipo() + " = " + v.getValor(mRunTime, mBuscadorDeVariaveis));
                    }

                    mArgumentos.add(v);


                }

            }

        }



        return mArgumentos;

    }

    public void passarParametros(Escopo mEscopoInterno, ArrayList<Index_Argument> eParametros, ArrayList<Item> mArgumentos) {

        Run_Pass  mRun_Pass = new Run_Pass(mEscopoInterno.getRunTime(), mEscopoInterno);

        if (eParametros.size() == mArgumentos.size()) {


            if (eParametros.size() > 0) {

                for (int argC = 0; argC < eParametros.size(); argC++) {

                    Index_Argument eParametro = eParametros.get(argC);
                    Item eArgumento = mArgumentos.get(argC);


                    if (eParametros.get(argC).getModo().contentEquals("VALUE")) {
                        mRun_Pass.passarParametroByValue(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                    } else if (eParametros.get(argC).getModo().contentEquals("REF")) {
                        mRun_Pass.passarParametroByRef(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                    } else if (eParametros.get(argC).getModo().contentEquals("MOC")) {
                        mRun_Pass.passarParametroByMoc(eParametros.get(argC).getNome(), mArgumentos.get(argC));
                    } else {
                        mEscopoInterno.getRunTime().getErros().add("Passagem de parametro desconhecida : " + eParametros.get(argC).getModo());
                        return;
                    }

                    mEscopoInterno.getRunTime().getHeap().organize(mArgumentos.get(argC), mEscopoInterno);

                    if (eArgumento.getNulo()) {
                     //   System.out.println("\t\t - Passando Param : " + eParametro.getNome() + " : " + eArgumento.getTipo() + " -->> NULO");
                    } else {
                    //    System.out.println("\t\t - Passando Param : " + eParametro.getNome() + " : " + eArgumento.getTipo() + " = " + eArgumento.getValor(mEscopoInterno.getRunTime(), mEscopoInterno));
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


    public int conferirArgumentos(RunTime mRunTime, ArrayList<Index_Argument> mParametros, ArrayList<Item> eArgumentos) {

        int v = 0;
        int i = 0;

        // System.out.println("\t - Inicio da Checagem :  " + mTipoArgumentos.size() + " e " + eArgumentos.size());

        int argumentacao = eArgumentos.size();
        int parametragem = mParametros.size();

        if (argumentacao == parametragem) {

            for (Item mArgumentos : eArgumentos) {

                String eArgumento = mArgumentos.getTipo();
                String eParametro = mParametros.get(i).getTipo();

                //  System.out.println("\t - Conferindo Tipo :  " + eParametro + " e " + eArgumento);

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
                    break;
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
