package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Argumentador;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Any {

    private RunTime mRunTime;
    private Argumentador mPreparadorDeArgumentos;

    public Run_Any(RunTime eRunTime) {

        mRunTime = eRunTime;
        mPreparadorDeArgumentos = new Argumentador();

    }

    public Item init_Function(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eRetorne, String eMensagem, ArrayList<Index_Function> eFunctions) {

        //System.out.println("Procurando FUNC " + this.getStructNome() + "." + ASTCorrente.getNome());

        Item mRet = null;

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //  System.out.println("\t - Argumentos :  " + mArgumentos.size());

        boolean enc = false;
        boolean algum = false;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        //   System.out.println(" STRUCT :: "  +this.getNome());

        for (Index_Function mIndex_Function : eFunctions) {
            // System.out.println("\t - " + mIndex_Function.getNome());
        }

        ArrayList<String> mRefers = new ArrayList<String>();
        mRefers.addAll(mEscopo.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefers());


        for (Index_Function mIndex_Function : eFunctions) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                mIndex_Function.resolverTipagem(mRefers);

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                // System.out.println("\t - ARGUMENTAR :  " + mIndex_Function.getNome());
                if (mIndex_Function.mesmoArgumentos(mEscopo,mArgumentos)) {

                    //  System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;

                    //if (mIndex_Function.mesmaTipagem(eRetorne) || eRetorne.contentEquals("<<ANY>>")) {

                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }


                    mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eRetorne);

                    //  } else {
                    //     mRunTime.getErros().add("Function " + eMensagem + " : Retorno incompativel !");
                    //  }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Function " + eMensagem + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Function  " + eMensagem + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }


        return mRet;
    }


    public void init_Action(AST ASTCorrente, Escopo BuscadorDeVariaveis, Escopo mEscopo, String eMensagem, ArrayList<Index_Action> eActions) {

        //   System.out.println(" -->> DENTRO : " + this.getStructNome() );
        //  System.out.println(" -->> Procurando ACTION " + this.getStructNome() + "." + ASTCorrente.getNome());

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, BuscadorDeVariaveis, ASTCorrente.getBranch("ARGUMENTS"));

        // System.out.println("\t - Action Teste :  " + ASTCorrente.getNome() + " Passando Args " + mArgumentos.size());

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;

        // System.out.println("\t - Executando Dentro :  " +this.getNome());

        ArrayList<String> mRefers = new ArrayList<String>();
        mRefers.addAll(mEscopo.getRefers());
        mRefers.addAll(BuscadorDeVariaveis.getRefers());

        for (Index_Action mIndex_Function : eActions) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }

            mIndex_Function.resolverTipagem(mRefers);



            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                //  System.out.println("\t - Action Teste :  " + mIndex_Function.getNome() + " -> " + mIndex_Function.getParametragem());

                if (mRunTime.getErros().size() > 0) {
                    break;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mEscopo,mArgumentos)) {

                    // System.out.println("\t - Executar :  " + mIndex_Function.getNome());

                    algum = true;


                    if (mRunTime.getErros().size() > 0) {
                        break;
                    }


                    // System.out.println("\t - Executando Dentro :  " +this.getNome());

                    //  mPreparadorDeArgumentos.executar_Action(mRunTime,  mEscopo, mIndex_Function, mArgumentos);



                    //   System.out.println(mEscopo.getNome() + " EA -> Structs : " + mEscopo.getStructs().size());


                    mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Action " + eMensagem + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Action  " + eMensagem + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }


    }

}
