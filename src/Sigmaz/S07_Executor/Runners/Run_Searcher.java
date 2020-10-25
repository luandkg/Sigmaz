package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Indexador.Index_Action;
import Sigmaz.S07_Executor.Indexador.Index_Function;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.Procuradores.ProcuradorRetornavel;
import Sigmaz.S07_Executor.Procuradores.ProcuradorSemRetorno;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

public class Run_Searcher {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;


    public Run_Searcher(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Searcher";

    }


    public ProcuradorRetornavel procurarRetornavelArgumentado(ArrayList<Index_Function> mListaDeProcura, String eNome, Escopo mEscopo, ArrayList<Item> mArgumentos) {

        ProcuradorRetornavel mexecutarAST = new ProcuradorRetornavel();

        Run_Arguments mRunArguments = new Run_Arguments();

        for (Index_Function mIndex_Function : mListaDeProcura) {

            mIndex_Function.resolverTipagem(mEscopo.getRefers());

            if (mIndex_Function.mesmoNome(eNome)) {


                mexecutarAST.mEnc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    mexecutarAST.mAlgum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);

                    if (contagem == mArgumentos.size()) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }

                        mexecutarAST.mIndexado = mIndex_Function;


                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }
                        mexecutarAST.mExato = true;

                        break;


                    }

                }


            }

        }


        return mexecutarAST;
    }

    public ProcuradorSemRetorno procurarSemRetornoArgumentado(ArrayList<Index_Action> mListaDeProcura, String eNome, Escopo mEscopo, ArrayList<Item> mArgumentos) {

        ProcuradorSemRetorno mexecutarAST = new ProcuradorSemRetorno();

        Run_Arguments mRunArguments = new Run_Arguments();

        for (Index_Action mIndex_Function : mListaDeProcura) {

            mIndex_Function.resolverTipagem(mEscopo.getRefers());

            if (mIndex_Function.mesmoNome(eNome)) {

                //       System.out.println("Conferindo SA : " +mIndex_Function.getNome() );


                mexecutarAST.mEnc = true;

                if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                    mexecutarAST.mAlgum = true;

                    int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);

                    //  System.out.println("Conferindo SA : " +mIndex_Function.getNome()  + " :: " + contagem);

                    if (contagem == mArgumentos.size()) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }

                        mexecutarAST.mIndexado = mIndex_Function;


                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }
                        mexecutarAST.mExato = true;

                        break;


                    }

                }


            }

        }


        return mexecutarAST;
    }


    public ProcuradorRetornavel procurarRetornavelArgumentadoGet(ArrayList<Index_Function> mListaDeProcura, Escopo mEscopo, ArrayList<Item> mArgumentos) {

        ProcuradorRetornavel mexecutarAST = new ProcuradorRetornavel();

        Run_Arguments mRunArguments = new Run_Arguments();

        for (Index_Function mIndex_Function : mListaDeProcura) {

            mIndex_Function.resolverTipagem(mEscopo.getRefers());


            mexecutarAST.mEnc = true;

            if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                mexecutarAST.mAlgum = true;

                int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);

                if (contagem == mArgumentos.size()) {

                    if (mRunTime.getErros().size() > 0) {
                        return null;
                    }

                    mexecutarAST.mIndexado = mIndex_Function;


                    if (mRunTime.getErros().size() > 0) {
                        return null;
                    }
                    mexecutarAST.mExato = true;

                    break;


                }

            }


        }


        return mexecutarAST;
    }


    public ProcuradorSemRetorno procurarSemRetornoArgumentadoSetter(ArrayList<Index_Action> mListaDeProcura, Escopo mEscopo, ArrayList<Item> mArgumentos) {

        ProcuradorSemRetorno mexecutarAST = new ProcuradorSemRetorno();


        Run_Arguments mRunArguments = new Run_Arguments();

        for (Index_Action mIndex_Function : mListaDeProcura) {

            mIndex_Function.resolverTipagem(mEscopo.getRefers());


            mexecutarAST.mEnc = true;

            if (mIndex_Function.getArgumentos().size() == mArgumentos.size()) {


                mexecutarAST.mAlgum = true;

                int contagem = mRunArguments.conferirArgumentos(mRunTime, mIndex_Function.getArgumentos(), mArgumentos);

               //  System.out.println("Conferindo SA : " +mIndex_Function.getNome()  + " :: " + contagem);

                if (contagem == mArgumentos.size()) {

                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }

                    mexecutarAST.mIndexado = mIndex_Function;


                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }

                    mexecutarAST.mExato = true;

                    break;


                }

            }


        }


        return mexecutarAST;
    }


}
