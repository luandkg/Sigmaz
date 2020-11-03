package Sigmaz.S05_Executor.Indexador;

import Sigmaz.S05_Executor.Escopo;
import Sigmaz.S05_Executor.Item;
import Sigmaz.S05_Executor.RunTime;
import Sigmaz.S05_Executor.Runners.Run_Arguments;
import Sigmaz.S05_Executor.Runners.Run_GetType;

import java.util.ArrayList;

import Sigmaz.S00_Utilitarios.AST;

public class Index_Function {

    private String mNome;

    private ArrayList<Index_Argument> mArgumentos;
    private String mTipo;


    private Run_Arguments mRunArguments;


    private RunTime mRunTime;
    private Escopo mEscopo;

    private AST mPonteiro;
    private AST mPonteiroVisibility;

    private boolean mResolvido;
    private Tipificador mTipificador;

    private String mRetornoTipo;

    public Index_Function(RunTime eRunTime, Escopo eEscopo, AST ePonteiro) {

        mPonteiro = ePonteiro;
        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mNome = mPonteiro.getNome();
        mTipo = mPonteiro.getValor();

        mArgumentos = new ArrayList<Index_Argument>();


        mRunArguments = new Run_Arguments();

        // System.out.println(mPonteiro.ImprimirArvoreDeInstrucoes());

        mPonteiroVisibility = mPonteiro.getBranch("VISIBILITY");

        mTipificador = new Tipificador();

        mResolvido = false;

        mRetornoTipo = getTipagem(mPonteiro.getBranch("TYPE"));

        resolverTipagem(mEscopo.getRefers());

    }


    public String getTipagem(AST eAST) {

        return mTipificador.getTipagem(eAST);

    }

    public ArrayList<Index_Argument> getArgumentos() {
        return mArgumentos;
    }


    public String getTipo() {
        return mRetornoTipo;
    }

    public boolean isExplicit() {
        return mPonteiroVisibility.mesmoNome("EXPLICIT");
    }

    public boolean isInit() {
        return mPonteiroVisibility.mesmoNome("INIT");
    }


    public boolean isAll() {
        return mPonteiroVisibility.mesmoNome("ALL");
    }

    public boolean isRestrict() {
        return mPonteiroVisibility.mesmoNome("RESTRICT");
    }

    public String getModo() {
        return mPonteiroVisibility.getNome();
    }


    public void setNome(String eNome) {
        mNome = eNome;
    }

    public void setTipo(String eTipo) {
        mTipo = eTipo;
    }

    public String getNome() {
        return mNome;
    }


    public AST getPonteiro() {
        return mPonteiro;
    }

    public boolean mesmoNome(String eNome) {
        return eNome.contentEquals(mNome);
    }


    public ArrayList<String> getParamentos() {

        ArrayList<String> ret = new ArrayList<String>();
        for (Index_Argument ia : mArgumentos) {
            ret.add(ia.getNome());
        }

        return ret;
    }

    public ArrayList<String> getParamentosModos() {


        ArrayList<String> ret = new ArrayList<String>();
        for (Index_Argument ia : mArgumentos) {
            ret.add(ia.getModo());
        }
        return ret;

    }

    public boolean mesmoArgumentos(Escopo gEscopo, ArrayList<Item> eArgumentos) {
        return mRunArguments.conferirArgumentos(mRunTime, mArgumentos, eArgumentos) == eArgumentos.size();
    }

    public boolean getEstaResolvido() {
        return mResolvido;
    }

    public void resolverTipagem(ArrayList<String> dRefers) {

        mArgumentos.clear();

        Run_GetType mRun_GetType = new Run_GetType(mRunTime, mEscopo, dRefers);

        for (AST aAST : mPonteiro.getBranch("ARGUMENTS").getASTS()) {


            // String antes =mRun_GetType.getTipagemBruta(eArg.getBranch("TYPE"));
            String mTipagem = mRun_GetType.getTipagem(aAST.getBranch("TYPE"));

            //   System.out.println("Tipando : " + antes + " -->> " + mTipagem);

            mArgumentos.add(new Index_Argument(aAST.getNome(), mTipagem, aAST.getValor()));
        }


        //mRetornoTipo = mRun_GetType.getTipagem(mPonteiro.getBranch("TYPE"));

        //System.out.println("Resolvendo Tipagem : " + this.getDefinicao());

        mRetornoTipo = mRun_GetType.getTipagem(mPonteiro.getBranch("TYPE"));


        mResolvido = true;
    }


    public String getDefinicao() {
        return this.getNome() + " ( " + this.getParametragem() + " ) -> " + this.getTipo();
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