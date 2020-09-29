package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Cast {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private String mLocal;

    public Run_Cast(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mLocal = "Run_Cast";

    }


    public String realizarCast(String mSaida, Run_Value mAST) {

        //   mRunTime.errar(mLocal,"Realizar CAST GETTER : " + mCAST_NOME + " :: " + rRetorno);
        String ret = null;

        //  System.out.println("Casting -->> " + mEntrada + " para " + mSaida);

        //System.out.println(" :: " + mEntrada + " e "+mSaida);

        String mEntrada = mAST.getRetornoTipo();
        String eConteudo = mAST.getConteudo();



        if (mEntrada.contentEquals(mSaida)) {
            return eConteudo;
        } else if (mEntrada.contentEquals("num")) {
            ret = realizarPrimitivoCast(mEntrada, mSaida, eConteudo);
        } else if (mEntrada.contentEquals("string")) {
            ret = realizarPrimitivoCast(mEntrada, mSaida, eConteudo);
        } else if (mEntrada.contentEquals("bool")) {
            ret = realizarPrimitivoCast(mEntrada, mSaida, eConteudo);

        } else {


            boolean enc = false;


            Run_Context mRun_Context = new Run_Context(mRunTime);

            // TENTAR GETTER
            for (AST mCast : mRun_Context.getCastsContexto(mEscopo.getRefers())) {


                if (mCast.mesmoNome(mEntrada)) {


                    // System.out.println("\t Com Entrada -->> " + mCast.getNome());

                    for (AST mCasting : mCast.getASTS()) {
                        if (mCasting.mesmoTipo("SETTER") && mCasting.mesmoValor(mSaida)) {

                            ret = realizar(mCasting, eConteudo);

                            enc = true;
                            break;
                        }

                    }

                    if (enc) {
                        break;
                    }

                } else if (mCast.mesmoNome(mSaida)) {


                    //   System.out.println("\t Com Saida -->> " + mCast.getNome());

                    for (AST mCasting : mCast.getASTS()) {
                        if (mCasting.mesmoTipo("GETTER") && mCasting.mesmoValor(mEntrada)) {

                            ret = realizar(mCasting, eConteudo);

                            enc = true;
                            break;
                        }


                    }

                    if (enc) {
                        break;
                    }

                }
            }


            if (!enc) {

                mRunTime.errar(mLocal, "CASTING DESCONHECIDO " + mEntrada + " -> " + mSaida + " !");

            }


        }

        if (mRunTime.getErros().size() == 0) {
            mAST.setRetornoTipo(mSaida);
        }

        return ret;

    }

    public String realizarPrimitivoCast(String mEntrada, String mSaida, String eConteudo) {

        String ret = null;
        boolean enc = false;

        // System.out.println("\t Com Primitivo -->> " + mEntrada);

        Run_Context mRun_Context = new Run_Context(mRunTime);

        for (AST mCast : mRun_Context.getCastsContexto(mEscopo.getRefers())) {

            if (mCast.mesmoNome(mSaida)) {


                for (AST mCasting : mCast.getASTS()) {
                    if (mCasting.mesmoTipo("GETTER") && mCasting.mesmoValor(mEntrada)) {

                        ret = realizar(mCasting, eConteudo);

                        enc = true;
                        break;
                    }


                }
                break;

            }


        }

        if (!enc) {

                mRunTime.errar(mLocal,"CASTING DESCONHECIDO " + mEntrada + " -> " + mSaida + " !");

        }


        return ret;

    }

    public String realizar(AST mCasting, String eConteudo) {

        String ret = null;

        Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
        mEscopoInterno.criarParametro(mCasting.getNome(), mCasting.getValor(), eConteudo);

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mCasting);

        if (mAST.getRetorno().getNulo()) {
            ret = null;
        } else if (mAST.getRetorno().getPrimitivo()) {
            ret = mAST.getRetorno().getValor(mRunTime,mEscopo);
        }

        return ret;
    }

}
