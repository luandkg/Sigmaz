package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.Escopo;
import Sigmaz.Executor.Item;
import Sigmaz.Executor.RunTime;
import Sigmaz.Executor.Runners.Run_GetType;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Index_Function {

    private String mNome;

    private ArrayList<String> mNomeArgumentos;
    private ArrayList<String> mTipoArgumentos;
    private ArrayList<String> mModoArgumentos;

    private String mTipo;
    private AST mPonteiro;

    private Argumentador mArgumentador;


    private RunTime mRunTime;
    private Escopo mEscopo;




    public Index_Function(RunTime eRunTime, Escopo eEscopo,AST ePonteiro) {

        mPonteiro = ePonteiro;
        mRunTime = eRunTime;
        mEscopo = eEscopo;

        mNome = mPonteiro.getNome();
        mTipo = mPonteiro.getValor();

        mNomeArgumentos = new ArrayList<String>();
        mTipoArgumentos = new ArrayList<String>();
        mModoArgumentos = new ArrayList<String>();

        mArgumentador = new Argumentador();

       // System.out.println(mPonteiro.ImprimirArvoreDeInstrucoes());

        for (AST aAST : mPonteiro.getBranch("ARGUMENTS").getASTS()) {

            argumentar(aAST);

        }

    }



    public String getTipagem(AST eAST){

        String mTipagem = eAST.getNome();

        if (eAST.mesmoValor("GENERIC")){

            for (AST eTipando : eAST.getASTS()) {
                mTipagem += "<" +getTipagem(eTipando) + ">";
            }

        }


        return mTipagem;

    }



    public String getTipo() {
        return getTipagem(mPonteiro.getBranch("TYPE"));
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

    public String getModo() {
        return mPonteiro.getBranch("VISIBILITY").getNome();
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
        return mNomeArgumentos;
    }

    public ArrayList<String> getParamentosModos() {
        return mModoArgumentos;
    }

    public boolean mesmoArgumentos(Escopo gEscopo,ArrayList<Item> eArgumentos) {
        return mArgumentador.mesmoArgumentos(mRunTime,gEscopo,mTipoArgumentos, eArgumentos);
    }


    public void argumentar(AST eArg) {

        mNomeArgumentos.add(eArg.getNome());
        mModoArgumentos.add(eArg.getValor());

        Run_GetType mRun_GetType = new Run_GetType(mRunTime,mEscopo);

        String mTipagem = mRun_GetType.getTipagem(eArg.getBranch("TYPE"));

        mTipoArgumentos.add(mTipagem);

       // mTipoArgumentos.add(getTipagem(eArg.getBranch("TYPE")));
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


        mResolvido=true;
    }


    public String getDefinicao() {
        return this.getNome() + " ( " + this.getParametragem() + " ) -> " + this.getTipo();
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