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

            if (mRunTime.getErros().size() > 0) {
                return;
            }


            Run_Struct mEscopoStruct = mRunTime.getRun_Struct(mItem.getValor());

            if (mRunTime.getErros().size() > 0) {
                return;
            }

            while (ASTCorrente.existeBranch("INTERNAL")) {

                AST eInternal = ASTCorrente.getBranch("INTERNAL");

                if (eInternal.mesmoValor("STRUCT_ACT")) {

                  //  System.out.println("STRUCT ACT Estou : " +mEscopo.getNome() );
                  //  System.out.println("STRUCT ACT Preciso : " +mEscopoStruct.getNome() );

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    mEscopoStruct.init_Action(eInternal, mEscopo);

                    break;
                } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {

                } else if (eInternal.mesmoValor("STRUCT_OBJECT")) {

                  //  System.out.println("STRUCT OBJECT : " +eInternal.getNome() );

                    if (mRunTime.getErros().size() > 0) {
                        return;
                    }

                    if(mEscopoStruct==null){
                        mRunTime.getErros().add("Estrutura" + " " + ASTCorrente.getNome() + " : Nula !");
                        return;
                    }

                    Item eItem = mEscopoStruct.init_Object(eInternal, mEscopo, "<<ANY>>");

                   // System.out.println("STRUCT OBJECT II : " +mEscopoStruct.getNome() );
                   // System.out.println("STRUCT OBJECT III : " +eItem.getValor() );

                   mEscopoStruct = mRunTime.getRun_Struct(eItem.getValor());

                  //  System.out.println("STRUCT OBJECT III : " +mEscopoStruct.getNome() );


                }

                ASTCorrente = eInternal;
            }


        } else {
            mRunTime.getErros().add("Problema na execucao : " + ASTCorrente.getNome());
        }


    }


}
