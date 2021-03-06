package Sigmaz.S05_Executor.Runners;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Escopos.Run_Struct;
import Sigmaz.S05_Executor.Escopos.Run_Type;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S08_Utilitarios.AST;

public class Run_Dot {

    private RunTime mRunTime;
    private String mLocal;

    public Run_Dot(RunTime eRunTime) {

        mRunTime = eRunTime;
        mLocal = "Run_Dot";

    }


    public Item operadorPonto(AST ASTCorrente, Escopo mEscopo, String eRetorno) {


        Item mItem = mEscopo.getItem(ASTCorrente.getNome());

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        Run_Context mRun_Context = new Run_Context(mRunTime);


        ArrayList<String> aRefers = mEscopo.getRefers();
        aRefers.addAll(mEscopo.getRefersOcultas());

        String eQualificador = mRun_Context.getQualificador(mItem.getTipo(), mEscopo);

        //  System.out.println("Qualificar " +mItem.getNome() + " : " + mItem.getTipo() + " -->> " +  eQualificador + " esperando RETORNO : " + eRetorno);

        Run_Internal mRun_Internal = new Run_Internal(mRunTime);

        if (eQualificador.contentEquals("STRUCT")) {


            if (ASTCorrente.existeBranch("INTERNAL")) {

                mItem = mRun_Internal.Struct_DentroStruct(mItem.getValor(mRunTime, mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

            } else if (ASTCorrente.existeBranch("INTERNAL_THIS")) {

                mItem = mRun_Internal.Struct_DentroDiretoStruct(mItem.getValor(mRunTime, mEscopo), ASTCorrente.getBranch("INTERNAL_THIS"), mEscopo, eRetorno);

            }

            //   System.out.println(" Qual : " + mItem.getNome() + ":" + mItem.getTipo() + " -> " + eQualificador);

        } else if (eQualificador.contentEquals("TYPE")) {

            mItem = mRun_Internal.Struct_DentroType(mItem.getTipo(), mItem.getValor(mRunTime, mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

        } else {

            //System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

            //   System.out.println(" Qual : " + mItem.getNome() + ":" + mItem.getTipo() + " -> " + eQualificador);

            //   mRun_Context.log(mEscopo);

            mRunTime.errar(mLocal, "CAST nao possui operador PONTO 1 :  " + eQualificador + " :: " + mItem.getTipo());
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

            eItem = mRun_Internal.Struct_DentroType(eItem.getTipo(), eItem.getValor(mRunTime, mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else {

            if (ASTCorrente.mesmoValor("STRUCT_OBJECT")) {
                //  System.out.println("OPERANTE EM TYPE : " + mEscopoType.getNome() + " -> " + ASTCorrente.getNome());

                eItem = mEscopoType.init_Object(ASTCorrente, mEscopo, eRetorno);

                //    System.out.println(" ->>  : " + eItem.getNome());

            }


        }


        return eItem;
    }


    public Item operadorPontoStruct(Run_Struct mEscopoType, Item eItem, AST ASTCorrente, Escopo mEscopo, String eRetorno) {

        Run_Context mRun_Context = new Run_Context(mRunTime);

        String eQualificador = mRun_Context.getQualificador(eItem.getTipo(), mEscopo);

        //  System.out.println("OPERANTE EM STRUCT : " + eItem.getNome() + " -> " + eQualificador);

        if (eQualificador.contentEquals("STRUCT")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroStruct(eItem.getValor(mRunTime, mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroType(eItem.getTipo(), eItem.getValor(mRunTime, mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else {

            if (ASTCorrente.mesmoValor("STRUCT_OBJECT")) {
                //   System.out.println("OPERANTE EM TYPE : " + mEscopoType.getNome() + " -> " + ASTCorrente.getNome());

                eItem = mEscopoType.init_Object(ASTCorrente, mEscopo, eRetorno);

                //   System.out.println(" ->>  : " + eItem.getNome());

            }


        }


        return eItem;
    }


    public Item operadorCol(String eNome, AST ASTCorrente, Escopo mEscopo, String eRetorno) {


        Item mItem = mEscopo.getItem(eNome);

        if (mRunTime.getErros().size() > 0) {
            return null;
        }

        Run_Context mRun_Context = new Run_Context(mRunTime);


        ArrayList<String> aRefers = mEscopo.getRefers();
        aRefers.addAll(mEscopo.getRefersOcultas());

        String eQualificador = mRun_Context.getQualificador(mItem.getTipo(), mEscopo);

        // System.out.println("Qualificar " + mItem.getNome() + " : " + mItem.getTipo() + " -->> " + eQualificador);


        if (eQualificador.contentEquals("STRUCT")) {


            //   System.out.println("STRUCT :: " + mItem.getValor(mRunTime, mEscopo));

            Run_Struct rs = mRunTime.getHeap().getRun_Struct(mItem.getValor(mRunTime, mEscopo));

            mItem = rs.init_Getter(ASTCorrente.getNome(), ASTCorrente, mEscopo, eRetorno);

            if (ASTCorrente.existeBranch("INTERNAL")) {
                Run_Internal mRun_Internal = new Run_Internal(mRunTime);

                mItem = mRun_Internal.Struct_DentroStruct(mItem.getValor(mRunTime, mEscopo), ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

            }


        } else {

            mRunTime.errar(mLocal, "CAST ou TYPE nao possui operador COL  :  " + eQualificador + " :: " + mItem.getTipo());
        }

        return mItem;
    }

    public Item operadorColDireto(String eNomeLocal, AST ASTCorrente, Escopo mEscopo, String eRetorno) {


        Item mItem = null;

        //   System.out.println("STRUCT :: " + mItem.getValor(mRunTime, mEscopo));

        Run_Struct rs = mRunTime.getHeap().getRun_Struct(eNomeLocal);

        mItem = rs.init_Getter(ASTCorrente.getNome(), ASTCorrente, mEscopo, eRetorno);

        if (ASTCorrente.existeBranch("INTERNAL")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            mItem = mRun_Internal.Struct_DentroStruct(eNomeLocal, ASTCorrente.getBranch("INTERNAL"), mEscopo, eRetorno);

        }


        return mItem;
    }


}
