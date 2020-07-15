package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Execute {

    private RunTime mRunTime;
    private Escopo mEscopo;


    public Run_Execute(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public void init(AST ASTCorrente) {

        if (ASTCorrente.mesmoValor("FUNCT")) {

            //  System.out.println("Execuntando em  : " + ASTCorrente.getNome());

            Run_Func mAST = new Run_Func(mRunTime, mEscopo);
            mAST.init_ActionFunction(ASTCorrente);

        } else if (ASTCorrente.getValor().contentEquals("STRUCT")) {

            Item mItem = mEscopo.getItem(ASTCorrente.getNome());

            //   System.out.println("Execuntando em  : " + ASTCorrente.getNome());

            if (ASTCorrente.mesmoNome("this")) {

            } else {
                if (mRunTime.getErros().size() > 0) {
                    return;
                }
                String eQualificador =mRunTime.getQualificador(mItem.getTipo(),mEscopo.getRefers());

               // System.out.println("Q -->> " + mItem.getTipo() + " :: " +eQualificador );

                if (eQualificador.contentEquals("STRUCT")) {

                } else {
                    mRunTime.getErros().add("Apenas struct possuem ACTIONS !");
                    return;

                }
            }


            // System.out.println("Tipo : " + mItem.getNome() + " : " + mItem.getTipo() + " -> " + eQualificador);


            struct_chamada(ASTCorrente, mItem);

        } else if (ASTCorrente.getValor().contentEquals("STRUCT_EXTERN")) {

        //    System.out.println("GET EXTERN CALL -> " + mEscopo.getNome() + " : " + ASTCorrente.getNome());

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            // Struct_Extern(ASTCorrente, eRetorno);
            Run_Extern Run_ExternC = new Run_Extern(mRunTime);

            Run_ExternC.Struct_Execute(ASTCorrente,mEscopo);


        } else {
            mRunTime.getErros().add("Problema na execucao : " + ASTCorrente.getNome());
        }


    }


    public void struct_chamada(AST ASTCorrente, Item mItem) {

        if (mRunTime.getErros().size() > 0) {
            return;
        }


        Run_Struct mEscopoStruct = mRunTime.getRun_Struct(mItem.getValor());

        if (mRunTime.getErros().size() > 0) {
            return;
        }

        while (ASTCorrente.existeBranch("INTERNAL")) {

            AST eInternal = ASTCorrente.getBranch("INTERNAL");

            // System.out.println("STRUCT FUNC Chamando : " + ASTCorrente.getBranch("INTERNAL").getNome());

            if (eInternal.mesmoValor("STRUCT_ACT")) {

                // System.out.println("STRUCT ACT Estou : " +mEscopo.getNome() );
                //  System.out.println("STRUCT ACT Preciso : " +mEscopoStruct.getNome() );

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                mEscopoStruct.init_ActionFunction(eInternal, mEscopo);

                break;
            } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {


                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                //System.out.println("STRUCT FUNCT Estou : " + eInternal.getNome());

                // if (!mItem.getTipo().contentEquals(eRetorno)) {
                mEscopoStruct = mRunTime.getRun_Struct(mItem.getValor());

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                //System.out.println("STRUCT Estou : " + mEscopoStruct.getNome());

                if (eInternal.existeBranch("INTERNAL")) {


                    mItem = mEscopoStruct.init_Function(eInternal, mEscopo, "<<ANY>>");
                } else {
                    mEscopoStruct.init_ActionFunction(eInternal, mEscopo);
                    break;
                }


                if (mRunTime.getErros().size() > 0) {
                    return;
                }



            } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

                //System.out.println("STRUCT OBJECT : " +eInternal.getNome() );

                if (mRunTime.getErros().size() > 0) {
                    return;
                }

                if (mEscopoStruct == null) {
                    mRunTime.getErros().add("Estrutura" + " " + ASTCorrente.getNome() + " : Nula !");
                    return;
                }

                mItem = mEscopoStruct.init_Object(eInternal, mEscopo, "<<ANY>>");

                mEscopoStruct = mRunTime.getRun_Struct(mItem.getValor());


            }

            ASTCorrente = eInternal;
        }

    }

}
