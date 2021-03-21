package Sigmaz.S05_Executor;

import Sigmaz.S08_Utilitarios.AST;

import java.util.ArrayList;

public class OMLRun {

    private ArrayList<AST> mCabecalho;
    private ArrayList<AST> mCodigo;
    private ArrayList<AST> mDebug;

    public OMLRun() {

        mCabecalho = new ArrayList<AST>();
        mCodigo = new ArrayList<AST>();
        mDebug = new ArrayList<AST>();

    }

    public void carregar(ArrayList<AST> eCabecalho, ArrayList<AST> eCodigo,ArrayList<AST> eDebug) {

        mCabecalho = eCabecalho;
        mCodigo = eCodigo;
        mDebug = eDebug;

    }


    public ArrayList<AST> getCabecalho() {
        return mCabecalho;
    }

    public ArrayList<AST> getCodigo() {
        return mCodigo;
    }

    public ArrayList<AST> getDebug() {
        return mDebug;
    }

}
