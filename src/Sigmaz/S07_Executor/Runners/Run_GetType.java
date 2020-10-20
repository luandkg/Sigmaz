package Sigmaz.S07_Executor.Runners;

import Sigmaz.S07_Executor.Escopo;
import Sigmaz.S07_Executor.RunTime;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Run_GetType {

    private RunTime mRunTime;
    private Escopo mEscopo;
    private ArrayList<String> dRefers;
    private String mLocal;

    public Run_GetType(RunTime eRunTime, Escopo eEscopo) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;

        dRefers = new ArrayList<>();
        mLocal = "Run_GetType";


    }

    public Run_GetType(RunTime eRunTime, Escopo eEscopo, ArrayList<String> mRefers) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        dRefers = mRefers;

    }

    public void adicionarRefers(ArrayList<String> maisRefers) {

        dRefers.addAll(maisRefers);

    }




    public String getTipagemSimples(String mTipagem) {


        ArrayList<String> maisRefers = new ArrayList<String>();
        maisRefers.addAll(dRefers);
        maisRefers.addAll(mEscopo.getRefers());


        int i = 0;
        int o = mTipagem.length();

        String mPrefixo = "";
        String mSufixo = "";

        while (i < o) {
            String l = mTipagem.charAt(i) + "";

            if (l.contentEquals("<")) {
                break;
            } else {
                mPrefixo += l;
            }

            i += 1;
        }

        while (i < o) {
            String l = mTipagem.charAt(i) + "";
            mSufixo += l;
            i += 1;
        }


        if (mPrefixo.contentEquals("any")) {

        } else if (mPrefixo.contentEquals("bool")) {

        } else if (mPrefixo.contentEquals("string")) {

        } else if (mPrefixo.contentEquals("num")) {

        } else {

            Run_Context mRunCT = new Run_Context(mRunTime);

            boolean enc = false;

            for (AST rAST : mRunCT.getCastsContexto(mEscopo)) {
                if (rAST.mesmoNome(mPrefixo)) {
                    mPrefixo = rAST.getValor();
                    enc = true;
                    break;
                }
            }

            for (AST rAST : mRunCT.getStagesContexto(mEscopo)) {
                if (rAST.mesmoNome(mPrefixo)) {
                    mPrefixo = rAST.getValor();
                    enc = true;
                    break;
                }
            }


            if (!enc) {
                for (AST rAST : mRunCT.getTypesContexto(mEscopo)) {
                    if (rAST.mesmoNome(mPrefixo)) {


                        mPrefixo = rAST.getValor();

                        enc = true;
                        break;
                    }
                }
            }


            if (!enc) {
                for (AST rAST : mRunCT.getStructsContexto(mEscopo)) {
                    if (rAST.mesmoNome(mPrefixo)) {
                        mPrefixo = rAST.getValor();

                        enc = true;
                        break;
                    }
                }
            }

            if (!enc) {
                //mRunTime.errar(mLocal,"Tipagem  " + mPrefixo + " : Deconhecido !");
            }
        }


        return mPrefixo + mSufixo;
    }

    public void tiparMultiplo(AST eAST) {

        for (AST ASTC : eAST.getASTS()) {
            getTipagem(ASTC) ;
        }

    }

        public String getTipagem(AST eAST) {


        String mTipagem = getTipagemSimples(eAST.getNome());


        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public String getTipagemAntes(AST eAST) {


        String mTipagem = (eAST.getNome());


        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagemAntes(eTipando) + ">";
            }

        }


        return mTipagem;

    }


    public String getTipagemBruta(AST eAST) {


        String mTipagem = (eAST.getNome());


        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagemBruta(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public String semTiparMultiplo(AST eAST){

        String mTipando = "";

        for (AST ASTC : eAST.getASTS()) {
            mTipando += "<" + getTipagemSemAlteracao(ASTC) + ">";
        }

        return mTipando;
    }

    public String getTipagemSemAlteracao(AST eAST) {


        String mTipagem =eAST.getNome();


        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagemSemAlteracao(eTipando) + ">";
            }

        }


        return mTipagem;

    }


    public boolean estaEmPacotado(String eTipagem) {
        boolean ret = false;

        int i = 0;
        int o = eTipagem.length();

        while (i < o) {
            String l = String.valueOf(eTipagem.charAt(i));

            if (l.contentEquals("<")) {

                int i2 = i += 1;
                if (i2 < o) {

                    String l2 = String.valueOf(eTipagem.charAt(i2));
                    if (l2.contentEquals(">")) {
                        ret = true;
                    }

                }


                break;
            }

            i += 1;
        }


        return ret;
    }


}
