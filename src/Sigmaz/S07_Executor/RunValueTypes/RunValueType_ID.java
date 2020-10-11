package Sigmaz.S07_Executor.RunValueTypes;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Item;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S07_Executor.Runners.Run_Value;
import Sigmaz.S00_Utilitarios.AST;

public class RunValueType_ID {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_ID(RunTime eRunTime, Escopo eEscopo){

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> ID";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {


        if (ASTCorrente.mesmoNome("true") || ASTCorrente.mesmoNome("false")) {

            //  System.out.println("Valorando  -> " + ASTCorrente.getNome());

            eRunValue.setNulo(false);
            eRunValue.setPrimitivo(true);
            eRunValue.setRetornoTipo("bool");
            eRunValue.setConteudo( ASTCorrente.getNome());

        } else if (ASTCorrente.mesmoNome("null")) {


            eRunValue.setNulo(true);
            eRunValue.setRetornoTipo(eRetorno);
            eRunValue.setPrimitivo(false);

            if (eRunValue.getRetornoTipo().contentEquals("num") ) {
                eRunValue.setPrimitivo(true);
            }else if(eRunValue.getRetornoTipo().contentEquals("int")){
                eRunValue.setPrimitivo(true);
            }else if(eRunValue.getRetornoTipo().contentEquals("bool")){
                eRunValue.setPrimitivo(true);
            }else if(eRunValue.getRetornoTipo().contentEquals("string")){
                eRunValue.setPrimitivo(true);
            }


        } else {


            Item mItem = mEscopo.getItem(ASTCorrente.getNome());

            //System.out.println("Valorando  -> Def " + mItem.getNome() + " : " + mItem.getTipo() + " = " + mItem.getValor());

            if (mItem != null) {


                eRunValue.setNulo(mItem.getNulo());
                eRunValue.setRetornoTipo(mItem.getTipo());
                eRunValue.setConteudo(mItem.getValor(mRunTime, mEscopo));

                eRunValue.setPrimitivo(mItem.getPrimitivo());
                eRunValue.setEstrutura(mItem.getIsEstrutura());


                eRunValue.setIsReferenciavel(true);
                eRunValue.setReferencia(mItem);


                if (eRunValue.getRetornoTipo().contentEquals("num") ) {
                    eRunValue.setPrimitivo(true);
                }else if(eRunValue.getRetornoTipo().contentEquals("int")){
                    eRunValue.setPrimitivo(true);
                }else if(eRunValue.getRetornoTipo().contentEquals("bool")){
                    eRunValue.setPrimitivo(true);
                }else if(eRunValue.getRetornoTipo().contentEquals("string")){
                    eRunValue.setPrimitivo(true);
                }


            }


            if (mRunTime.getErros().size() > 0) {
                return;
            }


        }

    }

}
