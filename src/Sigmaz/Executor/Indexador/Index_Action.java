package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Context;
import Sigmaz.Executor.Runners.Run_GetType;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Index_Action {

    private String mNome;

    private ArrayList<String> mNomeArgumentos;
    private ArrayList<String> mTipoArgumentos;
    private ArrayList<String> mModoArgumentos;

    private AST mPonteiro;
    private Argumentador mArgumentador;

    private RunTime mRunTime;
    private Escopo mEscopo;

    public Index_Action(RunTime eRunTime, Escopo eEscopo, AST ePonteiro) {

        mPonteiro = ePonteiro;
        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mPonteiro = ePonteiro;
        mNome = mPonteiro.getNome();

        mNomeArgumentos = new ArrayList<String>();
        mTipoArgumentos = new ArrayList<String>();
        mModoArgumentos = new ArrayList<String>();

        mArgumentador = new Argumentador();

        Tipificador mTipificador = new Tipificador();

        for (AST aAST : mPonteiro.getBranch("ARGUMENTS").getASTS()) {

            mTipificador. argumentar(mRunTime,mEscopo,aAST,eEscopo.getRefers(),mNomeArgumentos,mTipoArgumentos,mModoArgumentos);

        }

    }


    public void setNome(String eNome) {
        mNome = eNome;
    }


    public String getNome() {
        return mNome;
    }

    public boolean isExtern() {
        return mPonteiro.getBranch("VISIBILITY").mesmoNome("EXTERN");
    }

    public boolean isAll() {
        return mPonteiro.getBranch("VISIBILITY").mesmoNome("ALL");
    }

    public boolean isRestrict() {
        return mPonteiro.getBranch("VISIBILITY").mesmoNome("RESTRICT");
    }

    public ArrayList<String> getParamentosModos() {
        return mModoArgumentos;
    }

    private boolean mResolvido = false;


    public boolean getEstaResolvido() {
        return mResolvido;
    }

    public void resolverTipagem(ArrayList<String> dRefers) {


            mNomeArgumentos.clear();
            mModoArgumentos.clear();
            mTipoArgumentos.clear();

        Tipificador mTipificador = new Tipificador();

        for (AST aAST : mPonteiro.getBranch("ARGUMENTS").getASTS()) {

            mTipificador. argumentar(mRunTime,mEscopo,aAST,dRefers,mNomeArgumentos,mTipoArgumentos,mModoArgumentos);

        }





        mResolvido = true;
    }

    public String getTipagem(AST eAST) {

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")) {

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" + getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }

    public boolean mesmoNome(String eNome) {
        return eNome.contentEquals(mNome);
    }

    public AST getPonteiro() {
        return mPonteiro;
    }

    public ArrayList<String> getParamentos() {
        return mNomeArgumentos;
    }

    public boolean mesmoArgumentos(Escopo gEscopo,ArrayList<Item> eArgumentos) {
        return mArgumentador.mesmoArgumentos(mRunTime,gEscopo,mTipoArgumentos, eArgumentos);
    }

    public String getDefinicao() {
        return this.getNome() + " ( " + this.getParametragem() + " ) ";
    }

    public String getParametros() {
        String ret = "";

        int total = mNomeArgumentos.size();


        for (int ii = 0; ii < total; ii++) {

            if (ii < total - 1) {
                ret += mNomeArgumentos.get(ii) + " : " + mTipoArgumentos.get(ii) + " , ";
            } else {
                ret += mNomeArgumentos.get(ii) + " : " + mTipoArgumentos.get(ii) + " ";
            }

        }

        return ret;
    }

    public String getParametragem() {
        String ret = "";

        int total = mTipoArgumentos.size();


        for (int ii = 0; ii < total; ii++) {

            if (ii < total - 1) {
                ret += mTipoArgumentos.get(ii) + " , ";
            } else {
                ret += mTipoArgumentos.get(ii) + " ";
            }

        }

        return ret;
    }

}