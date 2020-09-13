package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

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

            if (mTipagem.contentEquals(mAST.getRetornoTipo())) {

            } else {
                mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x1 " + mAST.getRetornoTipo());
            }

        } else if (mAST.getIsPrimitivo()) {


            if (mTipagem.contentEquals(mAST.getRetornoTipo()) || mTipagem.contentEquals("<<ANY>>")) {


                if (mAST.getRetornoTipo().contentEquals("auto")) {

                    Run_Anonymous mRun_Anonymous = new Run_Anonymous(mRunTime, mEscopo);
                    mRun_Anonymous.criarAuto(eNome, mAST, mValor);

                } else if (mAST.getRetornoTipo().contentEquals("functor")) {


                    Run_Anonymous mRun_Anonymous = new Run_Anonymous(mRunTime, mEscopo);
                    mRun_Anonymous.criarFunctor(eNome, mAST, mValor);

                }
            } else if (mTipagem.contentEquals("int") && mAST.getRetornoTipo().contentEquals("num")) {
                mAST.setRetornoTipo("int");

                String s = String.valueOf(mAST.getConteudo());
                String inteiro = "";

                int index = 0;
                int o = s.length();
                while (index < o) {
                    String v = s.charAt(index) + "";
                    if (v.contentEquals(".")) {
                        break;
                    } else {
                        inteiro += v;
                    }
                    index += 1;
                }

                if (inteiro.contentEquals("")) {
                    inteiro = "0";
                }

                mAST.setConteudo(inteiro);


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


            } else {

                if (mRunTime.getQualificador(mAST.getRetornoTipo(), mEscopo.getRefers()).contentEquals("STRUCT")) {

                    boolean temHeranca = false;

                    //System.out.println(" -->> RETORNANDO : " +mAST.getRetornoTipo() );
                    // System.out.println(" -->> PRECISAVA : " +mTipagem );
                    //System.out.println(" -->> QUALIFICADOR : " + mRunTime.getQualificador(mAST.getRetornoTipo(),mEscopo.getRefers()) );

                    // AST mBases = ASTC.getBranch("BASES");

                    Run_Struct eRS = mRunTime.getRun_Struct(mAST.getConteudo());


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
                    mRunTime.errar(mLocal, "Retorno incompativel  : " + mTipagem + " x3 " + mAST.getRetornoTipo());
                }

            }

        } else {

            mRunTime.errar(mLocal, "Retorno incompativel  f : " + mTipagem + " x " + mAST.getRetornoTipo());

        }
        if (mTipagem.contentEquals("<<ANY>>")) {

        } else {
            mAST.setRetornoTipo(mTipagem);
        }

        return mAST;

    }

    public Run_Value initSemCast(String eNome, AST mValor, String mTipagem) {

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
