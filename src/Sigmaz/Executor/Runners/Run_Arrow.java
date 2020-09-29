package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Arrow {

    private RunTime mRunTime;
    private String mLocal;

    public Run_Arrow(RunTime eRunTime) {
        mRunTime = eRunTime;
        mLocal = "Run_Arrow";

    }

    public Item operadorSeta(AST ASTCorrente, Escopo mEscopo, String eRetorno ) {

        Item eItem = null;

        Run_Context mRun_Context = new Run_Context(mRunTime);

        Run_Extern mEscopoExtern = null;
        for (Run_Extern mRun_Struct : mRun_Context.getRunExternContexto(mEscopo.getRefers())) {
            if (mRun_Struct.getNome().contentEquals(ASTCorrente.getNome())) {
                mEscopoExtern = mRun_Struct;
                break;
            }
        }
        if (mEscopoExtern == null) {
            mRunTime.errar(mLocal, "STRUCT EXTERN " + ASTCorrente.getNome() + " : Nao encontrada !");
            return null;
        }
        //   System.out.println(" - STRUCT EXTERN : " + mEscopoExtern.getNome());

        AST eInternal = ASTCorrente.getBranch("INTERNAL");

        //   System.out.println(" - Chamar : " + eInternal.getNome() + " -> " + eInternal.getValor());


        if (eInternal.mesmoValor("STRUCT_OBJECT")) {

            //  System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome());


            eItem = mEscopoExtern.init_ObjectExtern(eInternal, mEscopo, "<<ANY>>");

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            //    System.out.println("\t - ESTRUTURA OBJECT : " + eInternal.getNome() + " = " + eItem.getValor());


            // if (eInternal.existeBranch("INTERNAL")) {
            //    eItem = operadorPontoStruct(mEstruturador, eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);
            // }

        } else if (eInternal.mesmoValor("STRUCT_FUNCT")) {

            /// System.out.println("\t - ESTRUTURA STRUCT_FUNCT : " + eInternal.getNome());


            eItem = mEscopoExtern.init_Function_Extern(eInternal, mEscopo, eRetorno);

            //  System.out.println("\t - RETORNO : " + eItem);


            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            //  if (eInternal.existeBranch("INTERNAL")) {
            //  System.out.println("PONTEIRO :: " + eItem.getValor() + " Dentro de Function -> " + eInternal.getBranch("INTERNAL").getNome());
            if (eInternal.existeBranch("INTERNAL")) {
                eItem = operadorPonto(eItem, eInternal.getBranch("INTERNAL"), mEscopo, eRetorno);
            }

            //  }


        } else {

            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !");

        }

        return eItem;
    }

    public Item operadorPonto(Item eItem, AST ASTCorrente, Escopo mEscopo, String eRetorno ) {

       // System.out.println(ASTCorrente.ImprimirArvoreDeInstrucoes());

        String eQualificador = mRunTime.getQualificador(eItem.getTipo(), mEscopo.getRefers());

        //  System.out.println("OPERANTE EM STRUCT : " + eItem.getNome() + " -> " + eQualificador);

        if (eQualificador.contentEquals("STRUCT")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            if (mRunTime.getErros().size() > 0) {
                return null;
            }

            eItem = mRun_Internal.Struct_DentroStruct(eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else if (eQualificador.contentEquals("TYPE")) {
            Run_Internal mRun_Internal = new Run_Internal(mRunTime);

            eItem = mRun_Internal.Struct_DentroType(eItem.getTipo(),eItem.getValor(mRunTime,mEscopo), ASTCorrente, mEscopo, eRetorno);

        } else {

            mRunTime.errar(mLocal, "AST_Value --> STRUCTURED VALUE !" + eItem.getTipo());

        }


        return eItem;
    }


}
