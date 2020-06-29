package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Indexador.Argumentador;
import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Func {

    private RunTime mRunTime;
    private Escopo mEscopo;


    private Argumentador mPreparadorDeArgumentos;

    public Run_Func(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mPreparadorDeArgumentos = new Argumentador();

    }


    public Item init_Function(AST ASTCorrente, String eReturne) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        return mRun_Any.init_Function(ASTCorrente, mEscopo, mEscopo, eReturne,  ASTCorrente.getNome(),mEscopo.getFunctionsCompleto());

    }

    public void init_Action(AST ASTCorrente) {

        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Action(ASTCorrente, mEscopo, mEscopo,  ASTCorrente.getNome(),mEscopo.getActionsCompleto());


    }

    public void init_ActionFunction(AST ASTCorrente) {


        Run_Any mRun_Any = new Run_Any(mRunTime);

        mRun_Any.init_Action(ASTCorrente, mEscopo, mEscopo,  ASTCorrente.getNome(),mEscopo.getActionFunctionsCompleto());


    }



    public Item init_Operation(String eNome, Run_Value Esquerda, Run_Value Direita, String eReturne) {

        Item mItem = null;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        Item ve = new Item("VALUE");
        Item vd = new Item("VALUE");


        ve.setNulo(Esquerda.getIsNulo());
        ve.setPrimitivo(Esquerda.getIsPrimitivo());
        ve.setIsEstrutura(Esquerda.getIsStruct());
        ve.setValor(Esquerda.getConteudo());
        ve.setTipo(Esquerda.getRetornoTipo());


        vd.setNulo(Direita.getIsNulo());
        vd.setPrimitivo(Direita.getIsPrimitivo());
        vd.setIsEstrutura(Direita.getIsStruct());
        vd.setValor(Direita.getConteudo());
        vd.setTipo(Direita.getRetornoTipo());

        String mTipagem = ve.getTipo() + " e " + vd.getTipo();

        //System.out.println("MATCH : " +mTipagem );

        mArgumentos.add(ve);
        mArgumentos.add(vd);

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;


        for (AST mAST : mRunTime.getGlobalOperations()) {

            Index_Function mIndex_Function = new Index_Function(mAST);

            if (mIndex_Function.mesmoNome(eNome)) {


                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {



                    //   System.out.println("\t - Funcao :  " +mIndex_Function.getNome() + " " + mIndex_Function.getParametragem());

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getTipo().contentEquals(eReturne) || eReturne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }


                        //  executar_Function(mIndex_Function, mArgumentos, eReturne);

                        mItem = mPreparadorDeArgumentos.executar_FunctionGlobal(mRunTime, mIndex_Function, mArgumentos, eReturne);


                    } else {
                        mRunTime.getErros().add("Operation " + eNome + " : Retorno incompativel !");
                    }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Operation " + eNome + " : Argumentos incompativeis : " + mTipagem);
            }
        } else {

            String mTipando = "";

            int i = 0;
            int t = mArgumentos.size();

            for (Item ArgumentoC : mArgumentos) {
                i += 1;

                if (i < t) {
                    mTipando += ArgumentoC.getTipo() + " x ";
                } else {
                    mTipando += ArgumentoC.getTipo() + " ";
                }


            }


            mRunTime.getErros().add("Operation  " + eNome + " -> " + mTipando + " : Nao Encontrada !");
        }

        return mItem;

    }

    public Item init_Unario(String eNome, Run_Value Esquerda,  String eReturne) {

        Item mItem = null;

        ArrayList<Item> mArgumentos = new ArrayList<Item>();

        Item ve = new Item("VALUE");


        ve.setNulo(Esquerda.getIsNulo());
        ve.setPrimitivo(Esquerda.getIsPrimitivo());
        ve.setIsEstrutura(Esquerda.getIsStruct());
        ve.setValor(Esquerda.getConteudo());
        ve.setTipo(Esquerda.getRetornoTipo());



        String mTipagem = ve.getTipo() ;

        //System.out.println("MATCH : " +mTipagem );

        mArgumentos.add(ve);

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;



        for (AST mAST : mRunTime.getGlobalUnarios()) {

            Index_Function mIndex_Function = new Index_Function(mAST);

            if (mIndex_Function.mesmoNome(eNome)) {


                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {



                    //   System.out.println("\t - Funcao :  " +mIndex_Function.getNome() + " " + mIndex_Function.getParametragem());

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getTipo().contentEquals(eReturne) || eReturne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }


                        //  executar_Function(mIndex_Function, mArgumentos, eReturne);

                        mItem = mPreparadorDeArgumentos.executar_FunctionGlobal(mRunTime, mIndex_Function, mArgumentos, eReturne);


                    } else {
                        mRunTime.getErros().add("Unary " + eNome + " : Retorno incompativel !");
                    }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Unary " + eNome + " : Argumentos incompativeis : " + mTipagem);
            }
        } else {

            String mTipando = "";

            int i = 0;
            int t = mArgumentos.size();

            for (Item ArgumentoC : mArgumentos) {
                i += 1;

                if (i < t) {
                    mTipando += ArgumentoC.getTipo() + " x ";
                } else {
                    mTipando += ArgumentoC.getTipo() + " ";
                }


            }


            mRunTime.getErros().add("Unary  " + eNome + " -> " + mTipando + " : Nao Encontrada !");
        }

        return mItem;

    }




}
