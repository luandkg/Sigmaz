package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_Arguments;
import Sigmaz.Executor.Runners.Run_GetType;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Index_Action {

    private String mNome;


    private Run_Arguments mRunArguments;

    private RunTime mRunTime;
    private Escopo mEscopo;
    private boolean mResolvido;

    private AST mPonteiro;
    private AST mPonteiroVisibility;
    private Tipificador mTipificador;
    private ArrayList<Index_Argument> mArgumentos;

    public Index_Action(RunTime eRunTime, Escopo eEscopo, AST ePonteiro) {

        mPonteiro = ePonteiro;
        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mPonteiro = ePonteiro;
        mNome = mPonteiro.getNome();


        mRunArguments = new Run_Arguments();
        mTipificador = new Tipificador();

        mPonteiroVisibility = mPonteiro.getBranch("VISIBILITY");

        mResolvido = false;
        mArgumentos = new ArrayList<Index_Argument>();


        resolverTipagem(eEscopo.getRefers());

    }


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public boolean isExtern() {
        return mPonteiroVisibility.mesmoNome("EXTERN");
    }

    public boolean isAll() {
        return mPonteiroVisibility.mesmoNome("ALL");
    }

    public boolean isRestrict() {
        return mPonteiroVisibility.mesmoNome("RESTRICT");
    }

    public ArrayList<String> getParamentosModos() {

        ArrayList<String> ret = new ArrayList<String>();
        for (Index_Argument ia : mArgumentos) {
            ret.add(ia.getModo());
        }
        return ret;

    }

    public boolean getEstaResolvido() {
        return mResolvido;
    }

    public void resolverTipagem(ArrayList<String> dRefers) {


        mArgumentos.clear();


        for (AST aAST : mPonteiro.getBranch("ARGUMENTS").getASTS()) {


            Run_GetType mRun_GetType = new Run_GetType(mRunTime,mEscopo,dRefers);

            // String antes =mRun_GetType.getTipagemBruta(eArg.getBranch("TYPE"));
            String mTipagem = mRun_GetType.getTipagem(aAST.getBranch("TYPE"));

            //   System.out.println("Tipando : " + antes + " -->> " + mTipagem);

            mArgumentos.add(new Index_Argument(aAST.getNome(),mTipagem,aAST.getValor()));
        }

    }

    public String getTipagem(AST eAST) {

        return mTipificador.getTipagem(eAST);


    }

    public boolean mesmoNome(String eNome) {
        return eNome.contentEquals(mNome);
    }

    public AST getPonteiro() {
        return mPonteiro;
    }

    public ArrayList<Index_Argument> getArgumentos(){return mArgumentos;}


    public ArrayList<String> getParamentos() {
        ArrayList<String> ret = new ArrayList<String>();
        for (Index_Argument ia : mArgumentos) {
            ret.add(ia.getNome());
        }

        return ret;
    }

    public boolean mesmoArgumentos(Escopo gEscopo, ArrayList<Item> eArgumentos) {
        return mRunArguments.mesmoArgumentos(mRunTime, gEscopo, mArgumentos, eArgumentos);
    }

    public String getDefinicao() {
        return this.getNome() + " ( " + this.getParametragem() + " ) ";
    }

    public String getParametros() {
        String ret = "";

        int total = mArgumentos.size();


        for (int ii = 0; ii < total; ii++) {

            if (ii < total - 1) {
                ret += mArgumentos.get(ii).getNome() + " : " + mArgumentos.get(ii).getTipo() + " , ";
            } else {
                ret += mArgumentos.get(ii).getNome() + " : " + mArgumentos.get(ii).getTipo() + " ";
            }

        }

        return ret;
    }

    public String getParametragem() {
        String ret = "";

        int total = mArgumentos.size();


        for (int ii = 0; ii < total; ii++) {

            if (ii < total - 1) {
                ret += mArgumentos.get(ii).getTipo() + " , ";
            } else {
                ret += mArgumentos.get(ii).getTipo() + " ";
            }

        }

        return ret;
    }

}