package Sigmaz.Executor.Runners;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.RunTime;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

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

    public Run_GetType(RunTime eRunTime, Escopo eEscopo,ArrayList<String> mRefers) {

        mRunTime = eRunTime;
        mEscopo = eEscopo;
        dRefers=mRefers;

    }

    public void adicionarRefers(ArrayList<String> maisRefers){

        dRefers.addAll(maisRefers);

    }


    public String getTipagemSimples(String mTipagem) {


        ArrayList<String> maisRefers = new ArrayList<String>();
        maisRefers.addAll( dRefers);
        maisRefers.addAll( mEscopo.getRefers());


        int i = 0;
        int o = mTipagem.length();

        String mPrefixo = "";
        String mSufixo = "";

        while (i < o) {
            String l = mTipagem.charAt(i) + "";

            if (l.contentEquals("<")){
                break;
            }else{
                mPrefixo+=l;
            }

            i += 1;
        }

        while (i < o) {
            String l = mTipagem.charAt(i) + "";
            mSufixo+=l;
            i += 1;
        }



        if (mPrefixo.contentEquals("any")) {

        } else if (mPrefixo.contentEquals("bool")) {

        } else if (mPrefixo.contentEquals("string")) {

        } else if (mPrefixo.contentEquals("num")) {

        } else {

            Run_Context mRunCT = new Run_Context(mRunTime);

            boolean enc = false;

            for (AST rAST : mRunCT.getCastsContexto(maisRefers)) {
                if (rAST.mesmoNome(mPrefixo)) {
                    mPrefixo = rAST.getValor();
                    enc = true;
                    break;
                }
            }


            if (!enc) {
                for (AST rAST : mRunCT.getTypesContexto(maisRefers)) {
                    if (rAST.mesmoNome(mPrefixo)) {


                        mPrefixo = rAST.getValor();

                        enc = true;
                        break;
                    }
                }
            }


            if (!enc) {
                for (AST rAST : mRunCT.getStructsContexto(maisRefers)) {
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


        String mTipagem =(eAST.getNome());


        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagemBruta(eTipando) + ">";
            }

        }


        return mTipagem;

    }

}
