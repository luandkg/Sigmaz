package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Run_Dot {

    private RunTime mRunTime;
    private String mLocal;

    public Run_Dot(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Run_Dot";

    }


    public Item operadorPonto(AST ASTCorrente, Escopo mEscopo, String eRetorno ) {


        Item mItem = mEscopo.getItem(ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return null;
        }


        ArrayList<String> aRefers = mEscopo.getRefers();
        aRefers.addAll(mEscopo.getRefersOcultas());

        String eQualificador = mRunTime.getQualificador(mItem.getTipo(), aRefers);


        if (eQualificador.contentEquals("STRUCT")) {


            if (ASTCorrente.existeBranch("INTERNAL")) {
                Run_Internal mRun_Internal = new Run_Internal(mRunTime);

                mItem = mRun_Internal.Struct_DentroStruct(mItem.getValor(mRunTime,mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

            } else if (ASTCorrente.existeBranch("INTERNAL_THIS")) {
                Run_Internal mRun_Internal = new Run_Internal(mRunTime);

                mItem = mRun_Internal.Struct_DentroDiretoStruct(mItem.getValor(mRunTime,mEscopo), ASTCorrente.getBranch("INTERNAL_THIS"), mEscopo, eRetorno);

            }

            // if (mItem.getNome().contentEquals("this")) {
            //     Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            //     mItem = mRun_Internal.Struct_DentroDiretoStruct(mItem.getValor(), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);
            //  } else {
            //     Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            //    mItem = mRun_Internal.Struct_DentroStruct(mItem.getValor(), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);
            //  }


        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            mItem = mRun_Internal.Struct_DentroType(mItem.getValor(mRunTime,mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

        } else {
            mRunTime.errar(mLocal, "CAST nao possui operador PONTO !" + eQualificador + " :: " + mItem.getTipo());
        }

        return mItem;
    }


    public Item operadorPontoType(Run_Type mEscopoType, Item eItem, AST ASTCorrente, Escopo mEscopo, String eRetorno) {


        String eQualificador = mRunTime.getQualificador(eItem.getTipo(), mEscopo.getRefers());


        if (eQualificador.contentEquals("STRUCT")) {

            // Struct_DentroStruct(mItem.getValor(), ASTCorrente, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroType(eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else {

            if (ASTCorrente.mesmoValor("STRUCT_OBJECT")) {
                //  System.out.println("OPERANTE EM TYPE : " + mEscopoType.getNome() + " -> " + ASTCorrente.getNome());

                eItem = mEscopoType.init_Object(ASTCorrente, mEscopo, eRetorno);

                //    System.out.println(" ->>  : " + eItem.getNome());

            }


        }


        return eItem;
    }


    public Item operadorPontoStruct(Run_Struct mEscopoType, Item eItem, AST ASTCorrente, Escopo mEscopo, String eRetorno ) {


        String eQualificador = mRunTime.getQualificador(eItem.getTipo(), mEscopo.getRefers());

        //  System.out.println("OPERANTE EM STRUCT : " + eItem.getNome() + " -> " + eQualificador);

        if (eQualificador.contentEquals("STRUCT")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroStruct(eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroType(eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else {

            if (ASTCorrente.mesmoValor("STRUCT_OBJECT")) {
                //   System.out.println("OPERANTE EM TYPE : " + mEscopoType.getNome() + " -> " + ASTCorrente.getNome());

                eItem = mEscopoType.init_Object(ASTCorrente, mEscopo, eRetorno);

                //   System.out.println(" ->>  : " + eItem.getNome());

            }


        }


        return eItem;
    }


}
