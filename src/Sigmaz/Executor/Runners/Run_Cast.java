package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

public class Run_Cast {

    private RunTime mRunTime;
    private Escopo mEscopo;

    public Run_Cast(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;


    }


    public String realizarCast(String mSaida, String mEntrada, String eConteudo) {

        // mRunTime.getErros().add("Realizar CAST GETTER : " + mCAST_NOME + " :: " + rRetorno);
        String ret = null;

      //  System.out.println("Casting -->> " + mEntrada + " para " + mSaida);

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


            // TENTAR GETTER
            for (AST mCast : mEscopo.getCastsCompleto()) {


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

                mRunTime.getErros().add("CASTING DESCONHECIDO " + mEntrada + " -> " + mSaida + " !");

            }


        }


        return ret;

    }

    public String realizarPrimitivoCast(String mEntrada, String mSaida, String eConteudo) {

        String ret = null;
        boolean enc = false;

       // System.out.println("\t Com Primitivo -->> " + mEntrada);


        for (AST mCast : mEscopo.getCastsCompleto()) {

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

            mRunTime.getErros().add("CASTING DESCONHECIDO " + mEntrada + " -> " + mSaida + " !");

        }


        return ret;

    }

    public String realizar(AST mCasting, String eConteudo) {

        String ret = null;

        Escopo mEscopoInterno = new Escopo(mRunTime, mRunTime.getEscopoGlobal());
        mEscopoInterno.criarParametro(mCasting.getNome(), mCasting.getValor(), eConteudo);

        Run_Body mAST = new Run_Body(mRunTime, mEscopoInterno);
        mAST.init(mCasting);

        if (mAST.getIsNulo()) {
            ret = null;
        } else if (mAST.getIsPrimitivo()) {
            ret = mAST.getConteudo();
        }

        return ret;
    }

}
