package Sigmaz.Executor;

import Sigmaz.Executor.Indexador.Index_Action;
import Sigmaz.Executor.Indexador.Index_Function;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Func {

    private RunTime mRunTime;
    private Escopo mEscopo;


    private PreparadorDeArgumentos mPreparadorDeArgumentos;

    public Run_Func(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


        mPreparadorDeArgumentos = new PreparadorDeArgumentos();

    }


    public Item init_Function(AST ASTCorrente, String eReturne) {

        Item mRet = null;

        // System.out.println("Procurando FUNC " + ASTCorrente.getNome());

        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));
        //    System.out.println("\t - Argumentos :  " + mArgumentos.size());


        boolean enc = false;
        boolean algum = false;


        for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //  System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
            //  for (AST ArgumentoC : mArgumentos) {
            //    System.out.println("\t\t - Arg :  " +ArgumentoC.getNome());
            // }


            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                if (mRunTime.getErros().size() > 0) {
                    return null;
                }
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getPonteiro().mesmoValor(eReturne) || eReturne.contentEquals("<<ANY>>")) {

                        if (mRunTime.getErros().size() > 0) {
                            return null;
                        }


                        //executar_Function(mIndex_Function, mArgumentos, eReturne);

                        //    mRet = mPreparadorDeArgumentos.executar_FunctionGlobal(mRunTime, mIndex_Function, mArgumentos, eReturne);

                        mRet = mPreparadorDeArgumentos.executar_Function(mRunTime, mEscopo, mIndex_Function, mArgumentos, eReturne);


                    } else {
                        mRunTime.getErros().add("Function " + ASTCorrente.getNome() + " : Retorno incompativel !");
                    }

                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Function " + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Function  " + ASTCorrente.getNome() + " : Nao Encontrada !");

            //    mRunTime.getErros().add("Escopo -> " + mEscopo.getNome());

            // for (Index_Function mIndex_Function : mEscopo.getFunctionsCompleto()) {

            //    System.out.println("\t - Funcao :  " + mIndex_Function.getNome());
            //    }

        }

        return mRet;
    }

    public void init_ActionFunction(AST ASTCorrente) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente.getBranch("ARGUMENTS"));

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;

        //  System.out.println("\t - ESCOPO :  " + mEscopo.getNome());

        for (Index_Action mIndex_Function : mEscopo.getActionFunctionsCompleto()) {

            //   System.out.println("\t\t - AF :  " + mIndex_Function.getNome());

        }

        for (Index_Action mIndex_Function : mEscopo.getActionFunctionsCompleto()) {

            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                //System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;


                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                    //executar_Action(mIndex_Function, mArgumentos);

                    mPreparadorDeArgumentos.executar_Action(mRunTime, mEscopo, mIndex_Function, mArgumentos);


                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Function x " + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Function  x " + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


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
        ve.setObjeto(Esquerda.getObjeto());
        ve.setTipo(Esquerda.getRetornoTipo());


        vd.setNulo(Direita.getIsNulo());
        vd.setPrimitivo(Direita.getIsPrimitivo());
        vd.setIsEstrutura(Direita.getIsStruct());
        vd.setValor(Direita.getConteudo());
        vd.setObjeto(Direita.getObjeto());
        vd.setTipo(Direita.getRetornoTipo());

        String mTipagem = ve.getValor() + " e " + vd.getValor();


        mArgumentos.add(ve);
        mArgumentos.add(vd);

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;


        for (Index_Function mIndex_Function : mEscopo.getOperationsCompleto()) {

            if (mIndex_Function.mesmoNome(eNome)) {

                // System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;

                    if (mIndex_Function.getPonteiro().mesmoValor(eReturne) || eReturne.contentEquals("<<ANY>>")) {

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

    public void init_Action(AST ASTCorrente) {


        ArrayList<Item> mArgumentos = mPreparadorDeArgumentos.preparar_argumentos(mRunTime, mEscopo, ASTCorrente);

        //  System.out.println("Procurando FUNC " + ASTCorrente.getNome());
        //System.out.println("\t - Argumentos :  " + argumentos);

        boolean enc = false;
        boolean algum = false;


        for (Index_Action mIndex_Function : mEscopo.getActionsCompleto()) {

            if (mIndex_Function.mesmoNome(ASTCorrente.getNome())) {

                // System.out.println("\t - Funcao :  " +mIndex_Function.getNome());
                enc = true;
                if (mIndex_Function.mesmoArgumentos(mArgumentos)) {

                    // System.out.println("\t - Executar :  " +mIndex_Function.getNome());

                    algum = true;


                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }


                    executar_Action(mIndex_Function, mArgumentos);


                    break;
                }


            }

        }


        if (enc) {
            if (!algum) {
                mRunTime.getErros().add("Action " + ASTCorrente.getNome() + " : Argumentos incompativeis !");
            }
        } else {
            mRunTime.getErros().add("Action  " + ASTCorrente.getNome() + " : Nao Encontrada !");
        }


    }


    public void executar_Action(Index_Action mFunction, ArrayList<Item> mArgumentos) {


        // System.out.println(" EXECUTAR Function " + mFunction.getNome() + " : " + mFunction.getValor());


        Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
        mEscopoInterno.setNome(mFunction.getNome());

        mPreparadorDeArgumentos.passarParametros(mEscopoInterno, mFunction.getParamentos(), mArgumentos);

        AST mASTBody = mFunction.getPonteiro().getBranch("BODY");

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mASTBody);


        // System.out.println("  Retornando -> " + mAST.getConteudo());


    }


}
