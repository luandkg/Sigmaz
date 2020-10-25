package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.Escopos.Run_Struct;
import Sigmaz.S07_Executor.Escopos.Run_Type;
import Sigmaz.S07_Executor.RunTime;
import Sigmaz.S00_Utilitarios.AST;

public class Run_Valoramento {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Valoramento(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        mLocal = "Run_Valoramento";


    }

    public Run_Value init(String eNome, AST mValor, String mTipagem) {


     //   System.out.println("\t\tValoramento : " + eNome);
     //   System.out.println("\t\tValoramento Inicio : " + mTipagem);

        Run_Context mRun_Context = new Run_Context(mRunTime);

        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, mTipagem);

      //  System.out.println("\t\tValoramento Fim : " + mAST.getRetornoTipo());

        if (mRunTime.getErros().size() > 0) {
            return mAST;
        }

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")) {
            mAST.setRetornoTipo(mTipagem);
        }

        if (mTipagem.contentEquals("<<ANY>>")) {
            mTipagem = mAST.getRetornoTipo();
        }

        if (mRunTime.getErros().size() > 0) {
            //    System.out.println(" -->> " + mRunTime.getErros().get(0));
            return mAST;
        }

        if (mAST.getIsNulo()) {

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {

            } else {
                mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x1 " + mAST.getRetornoTipo());
            }

        } else if (mAST.getIsPrimitivo()) {


            if (mTipagem.contentEquals(mAST.getRetornoTipo()) || mTipagem.contentEquals("<<ANY>>")) {


            } else {

                if (mRunTime.getErros().size() > 0) {
                    return mAST;
                }


                Run_Cast mCast = new Run_Cast(mRunTime, mEscopo);

                String res = mCast.realizarCast(mTipagem, mAST);

                if (mRunTime.getErros().size() > 0) {
                    return mAST;
                }

                if (mTipagem.contentEquals(mAST.getRetornoTipo())) {

                } else {


                    mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x1 " + mAST.getRetornoTipo());


                    return mAST;
                }

                if (res == null) {
                    mAST.setNulo(true);
                }


            }


        } else if (mAST.getIsStruct()) {


            if (mTipagem.contentEquals(mAST.getRetornoTipo()) || mTipagem.contentEquals("<<ANY>>")) {

            } else if (mRun_Context.getQualificador(mAST.getRetornoTipo(), mEscopo).contentEquals("CAST")) {

                mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x7 " + mAST.getRetornoTipo());

            } else if (mRun_Context.getQualificador(mAST.getRetornoTipo(), mEscopo).contentEquals("STRUCT")) {

                boolean temHeranca = false;

                //   System.out.println(" -->> RETORNANDO : " +mAST.getRetornoTipo() );
                //  System.out.println(" -->> PRECISAVA : " +mTipagem );
                //System.out.println(" -->> QUALIFICADOR : " + mRunTime.getQualificador(mAST.getRetornoTipo(),mEscopo.getRefers()) );

                // AST mBases = ASTC.getBranch("BASES");

                Run_Struct eRS = mRunTime.getHeap().getRun_Struct(mAST.getConteudo());

                if (eRS.getBases().size() > 0) {

                    for (String eBase : eRS.getBases()) {
                        if (eBase.contentEquals(mTipagem)) {
                            temHeranca = true;
                            break;
                        }
                        //  System.out.println("\t ->> " + eBase );
                    }

                    if (!temHeranca) {

                        // System.out.println(" -->> RETORNANDO : " + mAST.getRetornoTipo() );
                        //  System.out.println(" -->> RETORNANDO BASEADO : " + eRS.getBaseado() );
                        //   System.out.println(" -->> RETORNANDO TIPO REAL : " + eRS.getTipoCompleto() );

                        //  System.out.println(" -->> PRECISA : " + mTipagem );


                        if (eRS.getTipoCompleto().contentEquals(mTipagem)) {

                        } else {
                            mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x4 " + mAST.getRetornoTipo() + " e Subtipo : " + eRS.getTipoCompleto());
                        }


                    }


                } else {


                    mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x5 " + mAST.getRetornoTipo());

                    System.out.println(mValor.ImprimirArvoreDeInstrucoes());

                }


            } else if (mRun_Context.getQualificador(mAST.getRetornoTipo(), mEscopo).contentEquals("TYPE")) {


                boolean temHeranca = false;

                Run_Type eRS = mRunTime.getHeap().getRun_Type(mAST.getConteudo());

                for (String eBase : eRS.getBases()) {
                    if (eBase.contentEquals(mTipagem)) {
                        temHeranca = true;
                        break;
                    }
                    //  System.out.println("\t ->> " + eBase );
                }

                if (!temHeranca) {

                    // System.out.println(" -->> RETORNANDO : " + mAST.getRetornoTipo() );
                    //  System.out.println(" -->> RETORNANDO BASEADO : " + eRS.getBaseado() );
                    //   System.out.println(" -->> RETORNANDO TIPO REAL : " + eRS.getTipoCompleto() );

                    //  System.out.println(" -->> PRECISA : " + mTipagem );


                    if (eRS.getTipoCompleto().contentEquals(mTipagem)) {

                    } else {
                        mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x8 " + mAST.getRetornoTipo() + " e Subtipo : " + eRS.getTipoCompleto());
                    }


                }


            } else {


                mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " exc " + mAST.getRetornoTipo());
            }


        } else {

            mRunTime.errar(mLocal, "Retorno incompativel  f : " + mTipagem + " x " + mAST.getRetornoTipo());

        }
        if (mTipagem.contentEquals("<<ANY>>")) {

        } else {
            mAST.setRetornoTipo(mTipagem);
        }

        // System.out.println("Valoramento Type : " + mAST.getRetornoTipo());
        // System.out.println("Valoramento Primitivo : " + mAST.getIsPrimitivo());


        return mAST;

    }

    public Run_Value initSemCast(String eNome, AST mValor, String mTipagem, String mTipoAntepassado) {

        // System.out.println("Valorando  -> Def " + mValor.getValor());

        // System.out.println("Pas Retorno : " + mTipagem);


        Run_Value mAST = new Run_Value(mRunTime, mEscopo);
        mAST.init(mValor, mTipagem);

        // System.out.println("Def Retorno : " + mAST.getRetornoTipo());

        if (mRunTime.getErros().size() > 0) {
            //    System.out.println(" -->> " + mRunTime.getErros().get(0));
            return mAST;
        }

        if (mAST.getRetornoTipo().contentEquals("<<ANY>>")) {
            mAST.setRetornoTipo(mTipagem);
        }


        if (mRunTime.getErros().size() > 0) {
            //    System.out.println(" -->> " + mRunTime.getErros().get(0));
            return mAST;
        }

        if (mAST.getIsNulo()) {


        } else {

            if (mAST.getIsPrimitivo()) {

                if (mAST.getRetornoTipo().contentEquals("num")) {
                    if (!mAST.getConteudo().contains(".")) {
                        mAST.setRetornoTipo("int");
                    }
                }

            } else if (mAST.getIsStruct()) {


            }


        }


        return mAST;

    }

}
