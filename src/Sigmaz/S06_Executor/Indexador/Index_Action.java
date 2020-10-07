package Sigmaz.S06_Executor.Indexador;

import Sigmaz.S06_Executor.Escopo;
import Sigmaz.S06_Executor.Item;
import Sigmaz.S06_Executor.RunTime;
import Sigmaz.S06_Executor.Runners.Run_Arguments;
import Sigmaz.S06_Executor.Runners.Run_GetType;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;


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


        resolverTipagem(eEscopo.getRefersOcultas());

    }


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public String getNome() {
        return mNome;
    }

    public boolean isExplicit() {
        return mPonteiroVisibility.mesmoNome("EXPLICIT");
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


            Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo, dRefers);

            // String antes =mRun_GetType.getTipagemBruta(eArg.getBranch("TYPE"));

            String mTipagemAntes = mRun_GetType.getTipagemAntes(aAST.getBranch("TYPE"));
            String mTipado = mTipagemAntes;


           // System.out.println("Esta Empacotado : " + mTipagemAntes + " -->> " + estaEmPacotado(mTipagemAntes));

            if (!mRun_GetType.estaEmPacotado(mTipagemAntes)) {

                String mTipagem = mRun_GetType.getTipagem(aAST.getBranch("TYPE"));

              //  System.out.println("Resolvendo Tipo : " + mTipagemAntes + " -->> " + mTipagem);

                mTipado=mTipagem;

            }
            // for(String eref : dRefers){
            //   System.out.println("\t USOU REF :: " + eref);
            // }

            mArgumentos.add(new Index_Argument(aAST.getNome(), mTipado, aAST.getValor()));
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

    public ArrayList<Index_Argument> getArgumentos() {
        return mArgumentos;
    }


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