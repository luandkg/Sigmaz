package Sigmaz.Executor.Indexador;

import Sigmaz.Executor.Item;
import Sigmaz.Utils.AST;

import java.util.ArrayList;

public class Index_Action {

    private String mNome;

    private ArrayList<String> mNomeArgumentos;
    private ArrayList<String> mTipoArgumentos;

    private AST mPonteiro;
    private Argumentador mArgumentador;

    public Index_Action(AST ePonteiro) {

        mPonteiro = ePonteiro;
        mNome = mPonteiro.getNome();

        mNomeArgumentos = new ArrayList<String>();
        mTipoArgumentos = new ArrayList<String>();

        mArgumentador = new Argumentador();

        for (AST aAST : mPonteiro.getBranch("ARGUMENTS").getASTS()) {

            argumentar(aAST.getNome(), aAST.getValor());

        }

    }

    public void setNome(String eNome) {
        mNome = eNome;
    }


    public String getNome() {
        return mNome;
    }

    public boolean isExtern(){  return mPonteiro.getBranch("VISIBILITY").mesmoNome("EXTERN"); }
    public boolean isAll(){  return mPonteiro.getBranch("VISIBILITY").mesmoNome("ALL"); }
    public boolean isRestrict(){  return mPonteiro.getBranch("VISIBILITY").mesmoNome("RESTRICT"); }


    public void argumentar(String eNome, String eTipo) {
        mNomeArgumentos.add(eNome);
        mTipoArgumentos.add(eTipo);
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

    public boolean mesmoArgumentos(ArrayList<Item> eArgumentos) {
        return mArgumentador.mesmoArgumentos(mTipoArgumentos, eArgumentos);
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