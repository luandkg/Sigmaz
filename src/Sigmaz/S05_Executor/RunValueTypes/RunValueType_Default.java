package Sigmaz.S05_Executor.RunValueTypes;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.*;
import Sigmaz.S08_Utilitarios.AST;

public class RunValueType_Default {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public RunValueType_Default(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Value -> Default";

    }

    public void init(Run_Value eRunValue, AST ASTCorrente, String eRetorno) {

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo);
        mRun_GetType.adicionarRefers(mEscopo.getRefersOcultas());


     //   System.out.println(" DEFAULT :: ESCOPO " + mEscopo.getNome());
      //  System.out.println(" DEFAULT :: " + eRetorno);

      //  for (AST eAST : mEscopo.getGuardadosCompleto()) {
       //     if (eAST.mesmoTipo("DEFAULT")) {
       //         String mTipo = mRun_GetType.getTipagem(ASTCorrente.getBranch("TYPE"));
      //          System.out.println(" \t -->> " + eAST.getTipo() + " :: " + mTipo);
        //    }
      //  }



        if (ASTCorrente.existeBranch("TYPE")) {

            String mTipo = mRun_GetType.getTipagem(ASTCorrente.getBranch("TYPE"));

            boolean senc = false;


            for (AST eAST : mEscopo.getGuardadosCompleto()) {

                if (eAST.mesmoTipo("DEFAULT")) {


                    String sTipo = mRun_GetType.getTipagem(eAST.getBranch("TYPE"));

                    if (mTipo.contentEquals(sTipo)) {

                        Run_Default mRun_Default = new Run_Default(mRunTime, mRunTime.getEscopoGlobal());
                        Item eItem = mRun_Default.init(eAST, sTipo);

                        eRunValue.setNulo(eItem.getNulo());
                        eRunValue.setRetornoTipo(eItem.getTipo());
                        eRunValue.setPrimitivo(eItem.getPrimitivo());
                        eRunValue.setEstrutura(eItem.getIsEstrutura());
                        eRunValue.setConteudo(eItem.getValor(mRunTime, mEscopo));

                        senc = true;
                        break;
                    }


                }
            }


           // System.out.println(" DEFAULT :: " + eRetorno + " -->> " + senc);


            if (!senc) {

                if (mTipo.contentEquals("int")) {

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else if (mTipo.contentEquals("num")) {

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else if (mTipo.contentEquals("string")) {

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else if (mTipo.contentEquals("bool")) {

                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o PRIMITIVO : " + eRetorno);

                } else {

                    Run_Context mRun_Context = new Run_Context(mRunTime);

                    String eQual = mRun_Context.getQualificador(eRetorno, mEscopo);
                    if (eQual.contentEquals("CAST")) {

                        boolean enc = false;
                        for (AST mCast : mRun_Context.getCastsContexto(mEscopo)) {
                            if (mCast.mesmoNome(eRetorno)) {

                                if (mCast.existeBranch("DEFAULT")) {

                                    Run_Body mRB = new Run_Body(mRunTime, mEscopo);
                                    mRB.init(mCast.getBranch("DEFAULT"));


                                    eRunValue.setNulo(mRB.getRetorno().getNulo());
                                    eRunValue.setRetornoTipo(mRB.getRetorno().getTipo());
                                    eRunValue.setPrimitivo(mRB.getRetorno().getPrimitivo());
                                    eRunValue.setEstrutura(mRB.getRetorno().getIsEstrutura());
                                    eRunValue.setConteudo(mRB.getRetorno().getValor(mRunTime, mEscopo));


                                } else {
                                    mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para a CAST : " + eRetorno);
                                }


                                enc = true;
                                break;
                            }

                        }
                        if (!enc) {
                            mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para a CAST : " + eRetorno);
                        }
                    } else if (eQual.contentEquals("STRUCT")) {
                        mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para a STRUCT : " + eRetorno);
                    } else {

                        mRunTime.errar(mLocal, "RETORNO DEFAULT DESCONHECIDO para o Tipo: " + eRetorno);

                    }


                }


            }


        } else {
            mRunTime.errar(mLocal, "DEFAULT precisa ser tipado !");
        }


    }


}
