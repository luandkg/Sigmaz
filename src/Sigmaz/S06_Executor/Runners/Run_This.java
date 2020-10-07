package Sigmaz.S06_Executor.Runners;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Escopos.Run_Struct;
import Sigmaz.S06_Executor.Escopos.Run_Type;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_This {

    private RunTime mRunTime;
    private String mLocal;

    public Run_This(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Run_This";

    }


    public Item operadorPonto(AST ASTCorrente, Escopo mEscopo, String eRetorno ) {

        String eConstante = "this";


        Item mItem = mEscopo.getItem(eConstante);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }
        Run_Context mRun_Context = new Run_Context(mRunTime);


        String eQualificador = mRun_Context.getQualificador(mItem.getTipo(), mEscopo);


        if (eQualificador.contentEquals("STRUCT")) {


        //    System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());


            Run_Internal mRun_Internal = new Run_Internal(mRunTime);


            mItem = mRun_Internal.Struct_DentroDiretoStruct(mItem.getValor(mRunTime,mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

            if (mRunTime.getErros().size() > 0) {
                return null;
            }


        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            mItem = mRun_Internal.Struct_DentroType(mItem.getTipo(),mItem.getValor(mRunTime,mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

        } else {
            mRunTime.errar(mLocal, "CAST nao possui operador PONTO !");
        }

        return mItem;
    }


    public Item operadorPontoType(Run_Type mEscopoType, Item eItem, AST ASTCorrente, Escopo mEscopo, String eRetorno) {

        Run_Context mRun_Context = new Run_Context(mRunTime);

        String eQualificador = mRun_Context.getQualificador(eItem.getTipo(), mEscopo);


        if (eQualificador.contentEquals("STRUCT")) {

            // Struct_DentroStruct(mItem.getValor(), ASTCorrente, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroType(eItem.getTipo(),eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else {

            if (ASTCorrente.mesmoValor("STRUCT_OBJECT")) {
                //  System.out.println("OPERANTE EM TYPE : " + mEscopoType.getNome() + " -> " + ASTCorrente.getNome());

                eItem = mEscopoType.init_Object(ASTCorrente, mEscopo, eRetorno);

                //    System.out.println(" ->>  : " + eItem.getNome());

            }


        }


        return eItem;
    }


    public Item operadorPontoStruct(Run_Struct mEscopoType, Item eItem, AST ASTCorrente, Escopo mEscopo, String eRetorno, String mTipoAntepassado) {

        Run_Context mRun_Context = new Run_Context(mRunTime);

        String eQualificador = mRun_Context.getQualificador(eItem.getTipo(), mEscopo);

        //  System.out.println("OPERANTE EM STRUCT : " + eItem.getNome() + " -> " + eQualificador);

        if (eQualificador.contentEquals("STRUCT")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroStruct(eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroType(eItem.getTipo(),eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

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
